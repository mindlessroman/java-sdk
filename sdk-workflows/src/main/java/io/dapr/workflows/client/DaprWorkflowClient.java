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
import com.microsoft.durabletask.OrchestrationMetadata;

public class DaprWorkflowClient implements AutoCloseable {

  private DurableTaskClient innerClient;

  /**
   * Constructor for DaprWorkflowClient.
   *
   * @param innerClient DurableTaskClient instance
   * @throws IllegalArgumentException if innerClient is null
   */
  public DaprWorkflowClient(DurableTaskClient innerClient) throws IllegalArgumentException {
    if (innerClient == null) {
      throw new IllegalArgumentException("Inner client cannot be null");
    } else {
      this.innerClient = innerClient;
    }
  }

  /**
   * Schedules a new workflow using DurableTask client.
   *
   * @param workflowName name of workflow to start
   * @return String for new Workflow instance Id
   */
  public String scheduleNewWorkflow(String workflowName) {
    return this.innerClient.scheduleNewOrchestrationInstance(workflowName);
  }

  //TODO: Overloaded scheduleNewWorkflow implementations

  public OrchestrationMetadata getWorkflowMetadata(String workflowName) {
    return this.innerClient.getInstanceMetadata(workflowName, false); //TODO: is false what we want?
  }

  /**
   * Terminates the workflow associated with the provided instance id.
   *
   * @param workflowName name of workflow to terminate
   */
  public void terminateWorkflow(String workflowName) {
    this.innerClient.terminate(workflowName, null); //TODO: should this be null here?
  }

  /**
   * Closes the inner DurableTask client.
   *
   */
  public void close() {
    if (this.innerClient != null) { //TODO: is there another condition worth checking?
      this.innerClient.close();
    }

  }
}
