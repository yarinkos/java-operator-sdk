package io.javaoperatorsdk.operator.processing.dependent.workflow;

import io.javaoperatorsdk.operator.api.reconciler.dependent.DependentResource;

import java.util.concurrent.locks.Condition;

public class Dependency {

    private DependentResource<?,?,?> depending;
    private DependentResource<?,?,?> target;
    private Condition condition;

}
