package io.dapr.workflows.client;

import com.microsoft.durabletask.DurableTaskClient;
import com.microsoft.durabletask.Task;

public class DaprWorkflowClient {

  private DurableTaskClient innerClient;

  public DaprWorkflowClient(DurableTaskClient innerClient){
    this.innerClient = innerClient;
  }

  public Task<String> ScheduleNewWorkflow(){

  }

}
