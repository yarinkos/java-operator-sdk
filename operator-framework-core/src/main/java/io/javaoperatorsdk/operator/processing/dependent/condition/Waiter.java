package io.javaoperatorsdk.operator.processing.dependent.condition;

import io.fabric8.kubernetes.api.model.HasMetadata;

public class Waiter {


    public <T extends HasMetadata> void waitUntil(Condition<T> condition, T resource) {
        boolean res = condition.isInState(resource);
        // todo awaitility?
    }

}
