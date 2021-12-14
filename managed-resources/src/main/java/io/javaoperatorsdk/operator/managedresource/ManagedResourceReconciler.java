package io.javaoperatorsdk.operator.managedresource;

import io.fabric8.kubernetes.api.model.HasMetadata;
import io.javaoperatorsdk.operator.api.reconciler.*;
import io.javaoperatorsdk.operator.processing.event.source.EventSourceRegistry;

import java.util.Optional;

public class ManagedResourceReconciler<T extends HasMetadata> implements Reconciler<T>, EventSourceInitializer<T>,
        ErrorStatusHandler<T> {

    private final ResourceManager<T> resourceManager;

    public ManagedResourceReconciler(ResourceManager<T> resourceManager) {
        this.resourceManager = resourceManager;
    }

    @Override
    public void prepareEventSources(EventSourceRegistry<T> eventSourceRegistry) {
        resourceManager.getEventSources().forEach(eventSourceRegistry::registerEventSource);
    }

    @Override
    public UpdateControl<T> reconcile(T resource, Context context) {
        return null;
    }


    @Override
    public Optional<T> updateErrorStatus(T resource, RetryInfo retryInfo, RuntimeException e) {
        return Optional.empty();
    }
}
