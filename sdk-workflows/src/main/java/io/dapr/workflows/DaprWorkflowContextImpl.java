package io.dapr.workflows;

import java.util.UUID;

public class DaprWorkflowContextImpl implements WorkflowContext {
    /* TODO: pull in durable tast framework etc */

    private String name;
    private String instanceId;
    private UUID uuid;

    public DaprWorkflowContextImpl (String name) {
        this.name = name;
        this.instanceId = uuid.randomUUID().toString();
    }

    public String getName() {
        return this.name;
    }

    public String getInstanceId() {
        return this.instanceId;
    }
}