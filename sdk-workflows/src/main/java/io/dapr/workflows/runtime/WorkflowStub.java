package io.dapr.workflows.runtime;

@FunctionalInterface
public interface WorkflowStub {
  void run(WorkflowContext ctx);
}
