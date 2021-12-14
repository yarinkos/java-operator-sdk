package io.javaoperatorsdk.operator.managedresource;

import io.fabric8.kubernetes.api.model.HasMetadata;
import io.javaoperatorsdk.operator.managedresource.box.ResourceBox;
import io.javaoperatorsdk.operator.processing.event.source.EventSource;

import java.util.List;
import java.util.stream.Collectors;

public class ResourceManager<T extends HasMetadata> {

    private List<Resource<?,?>> resources;

    public List<EventSource> getEventSources() {
        return null;
    }

}
