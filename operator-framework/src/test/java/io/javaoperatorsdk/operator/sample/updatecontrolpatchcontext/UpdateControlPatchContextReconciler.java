package io.javaoperatorsdk.operator.sample.updatecontrolpatchcontext;

import io.fabric8.kubernetes.client.dsl.base.PatchContext;
import io.fabric8.kubernetes.client.dsl.base.PatchType;
import io.javaoperatorsdk.operator.api.reconciler.*;

@ControllerConfiguration
public class UpdateControlPatchContextReconciler
    implements Reconciler<UpdateControlPatchContextCustomResource> {

  @Override
  public UpdateControl<UpdateControlPatchContextCustomResource> reconcile(
      UpdateControlPatchContextCustomResource resource,
      Context<UpdateControlPatchContextCustomResource> context) {

    resource.setStatus(new UpdateControlPatchContextStatus());
    resource.getStatus().setReady(true);

    return UpdateControl.patchResource(resource, PatchContext.of(PatchType.JSON_MERGE));
  }

}
