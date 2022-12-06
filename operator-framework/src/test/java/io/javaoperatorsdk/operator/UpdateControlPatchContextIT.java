package io.javaoperatorsdk.operator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import io.javaoperatorsdk.operator.junit.LocallyRunOperatorExtension;
import io.javaoperatorsdk.operator.sample.customfilter.CustomFilteringTestReconciler;

public class UpdateControlPatchContextIT {

  @RegisterExtension
  LocallyRunOperatorExtension operator =
      LocallyRunOperatorExtension.builder().withReconciler(new CustomFilteringTestReconciler())
          .build();


  @Test
  void testJSONMergePatch() {

  }

}
