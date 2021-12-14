package io.javaoperatorsdk.operator.managedresource;

import io.javaoperatorsdk.operator.processing.event.source.EventSource;

import java.util.List;
import java.util.Optional;

public interface Resource<I,O> {

     Optional<O> reconcile(I input);

     ResourceState getState();

     List<EventSource> getEventSources();
}
