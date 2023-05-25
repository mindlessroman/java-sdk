/*
 * Copyright 2023 The Dapr Authors
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
limitations under the License.
*/

package io.dapr.workflows.client;

import com.microsoft.durabletask.DurableTaskClient;
import com.microsoft.durabletask.DurableTaskGrpcClientBuilder;

import javax.annotation.Nullable;

public class DaprWorkflowClient implements AutoCloseable {

  private DurableTaskClient innerClient;

  /**
   * Constructor for DaprWorkflowClient.
   */
  public DaprWorkflowClient() throws IllegalArgumentException {
    DurableTaskClient innerClient = new DurableTaskGrpcClientBuilder().build();
    this.innerClient = innerClient;
  }

  /**
   * Schedules a new workflow using DurableTask client.
   *
   * @param workflowName name of workflow to start
   * @return String for new Workflow instance id
   */
  public String scheduleNewWorkflow(String workflowName) {
    return this.innerClient.scheduleNewOrchestrationInstance(workflowName);
  }

  /**
   * Terminates the workflow associated with the provided instance id.
   *
   * @param workflowInstanceId Workflow instance id to terminate
   * @param output the optional output to set for the terminated orchestration instance
   */
  public void terminateWorkflow(String workflowInstanceId, @Nullable Object output) {
    this.innerClient.terminate(workflowInstanceId, output);
  }

  /**
   * Closes the inner DurableTask client.
   *
   */
  public void close() {
    if (this.innerClient != null) {
      this.innerClient.close();
    }

  }
}
