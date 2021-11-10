package io.javaoperatorsdk.operator.processing.event.internal;

import io.javaoperatorsdk.operator.OperatorException;
import io.javaoperatorsdk.operator.processing.event.AbstractEventSource;

public abstract class LifecycleAwareEventSource extends AbstractEventSource {

  protected volatile boolean started = false;

  @Override
  public void start() throws OperatorException {
    started = true;
  }

  @Override
  public void stop() throws OperatorException {
    started = false;
  }
}
