package io.javaoperatorsdk.operator.processing.dependent.condition;

import io.fabric8.kubernetes.api.model.HasMetadata;

public interface Condition<T> {

   boolean isInState(T resource);


}
