package io.javaoperatorsdk.operator.api.reconciler;

import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;

import io.fabric8.kubernetes.api.model.HasMetadata;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.OwnerReference;
import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.client.KubernetesClient;

import com.fasterxml.jackson.core.JsonProcessingException;

// todo unit tests
import static io.javaoperatorsdk.operator.api.reconciler.Constants.OBJECT_MAPPER;

public class ReconcileHelper {

  public static BinaryOperator<? extends CustomResource> VOID_MAPPER =
      (oldValue, newValue) -> newValue;

  public static BinaryOperator<? extends CustomResource> METADATA_PRESERVING_MAPPER =
      (oldValue, newValue) -> {
        oldValue
            .getMetadata()
            .getLabels()
            .forEach((k, v) -> newValue.getMetadata().getLabels().putIfAbsent(k, v));
        oldValue
            .getMetadata()
            .getAnnotations()
            .forEach((k, v) -> newValue.getMetadata().getLabels().putIfAbsent(k, v));

        oldValue
            .getMetadata()
            .getFinalizers()
            .forEach(
                f -> {
                  if (!newValue.getMetadata().getFinalizers().contains(f)) {
                    newValue.getFinalizers().add(f);
                  }
                });
        return newValue;
      };

  public static Comparator<? extends CustomResource> EQUALS_SPEC_COMPARATOR =
      (c1, c2) -> c1.getSpec().equals(c2.getSpec()) ? 0 : -1;

  public static Comparator<? extends CustomResource> JSON_COMPARE_SPEC_COMPARATOR =
      (c1, c2) -> {
        try {
          var c1json = OBJECT_MAPPER.writeValueAsString(c1.getSpec());
          var c2json = OBJECT_MAPPER.writeValueAsString(c2.getSpec());
          return OBJECT_MAPPER.readTree(c1json)
              .equals(OBJECT_MAPPER.readTree(c2json)) ? 0 : -1;
        } catch (JsonProcessingException e) {
          throw new IllegalStateException(e);
        }
      };

  public BiFunction<? extends HasMetadata,? extends HasMetadata,Supplier<? extends HasMetadata>>
          OWNER_REFERENCE_SETTER_SUPPLIER =
          (dependentResource,primaryResource) -> {
            if (dependentResource.getMetadata() == null) {
                dependentResource.setMetadata(new ObjectMeta());
            }
            var ownerReference = new OwnerReference();
            ownerReference.setName(primaryResource.getMetadata().getName());
            ownerReference.setKind(primaryResource.getKind());
            dependentResource.getMetadata().getOwnerReferences().add(ownerReference);
            return () -> dependentResource;
          };


  // we might consider adding the client to the context to simplify this interface
  public <T extends CustomResource> T reconcileK8SResource(
      KubernetesClient client, Context context, Supplier<T> targetResource) {
    return reconcileK8SResource(
        client,
        context,
        targetResource,
        (Comparator<T>) JSON_COMPARE_SPEC_COMPARATOR,
        (BinaryOperator<T>) METADATA_PRESERVING_MAPPER);
  }

  public <T extends CustomResource> T reconcileK8SResource(
      KubernetesClient client,
      // the contex can be a supplier too or a cache interface, maybe something above? (receives ResourceID?)
      Context context,
      Supplier<T> targetResource,
      Comparator<T> comparator,
      BinaryOperator<T> resourceMapper) {

    T target = targetResource.get();
    var actualResource = context.getSecondaryResource((Class<T>) target.getClass());
    if (actualResource.isEmpty()) {
      return client.resources((Class<T>) target.getClass()).create(target);
    } else {
      if (comparator.compare(target, actualResource.get()) != 0) {
        return client
            .resources((Class<T>) target.getClass())
            .replace(resourceMapper.apply(actualResource.get(), target));
      } else {
        return actualResource.get();
      }
    }
  }

}
