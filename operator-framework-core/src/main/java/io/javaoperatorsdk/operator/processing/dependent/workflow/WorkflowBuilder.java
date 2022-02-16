package io.javaoperatorsdk.operator.processing.dependent.workflow;

import io.javaoperatorsdk.operator.api.reconciler.dependent.DependentResource;

import java.util.List;

public class WorkflowBuilder {

    private List<DependentResource> dependentResources;

/*

    // todo
    res4.dependsOn(res3).

    reconcile(res4).after()

    reconcile(res).after(
                reconcileIf(res2,condition)
                        .after(reconcile(res4)),
                reconcile(res3)).afterIf(ConditionOn(resX),reconcile());


 */


}
