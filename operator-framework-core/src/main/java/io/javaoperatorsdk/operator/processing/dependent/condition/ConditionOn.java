package io.javaoperatorsdk.operator.processing.dependent.condition;

import io.fabric8.kubernetes.api.model.HasMetadata;
import io.javaoperatorsdk.operator.api.reconciler.dependent.DependentResource;

public abstract class ConditionOn<R,P extends HasMetadata> {


     DependentResource<R,P,?> resource;
     Condition<R> condition;


}
