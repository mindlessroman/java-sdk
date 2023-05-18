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

package io.dapr.workflows.runtime;

import com.microsoft.durabletask.DurableTaskGrpcWorker;
import com.microsoft.durabletask.DurableTaskGrpcWorkerBuilder;
import com.microsoft.durabletask.TaskOrchestration;
import com.microsoft.durabletask.TaskOrchestrationFactory;

/**
 * Contains methods to register workflows and activities.
 */
public class WorkflowRuntime implements AutoCloseable {

  private static volatile WorkflowRuntime instance;
  private DurableTaskGrpcWorkerBuilder builder;
  private DurableTaskGrpcWorker client;

  private WorkflowRuntime(DurableTaskGrpcWorkerBuilder builder) throws IllegalStateException {

    if (instance != null) {
      throw new IllegalStateException("WorkflowRuntime should only be constructed once");
    }

    if (builder == null) {
      builder = new DurableTaskGrpcWorkerBuilder();
    }
    this.builder = builder;
  }

  /**
   * Returns an WorkflowRuntime object.
   *
   * @return An WorkflowRuntime object.
   */
  public static WorkflowRuntime getInstance() {
    if (instance == null) {
      synchronized (WorkflowRuntime.class) {
        if (instance == null) {
          instance = new WorkflowRuntime(null);
        }
      }
    }
    return instance;
  }

  /**
   * Registers a Workflow object.
   *
   * @param <T> any Workflow type
   * @param clazz the class being registered
   */
  public <T extends Workflow> void registerWorkflow(Class<T> clazz) {
    try {
      this.builder = this.builder.addOrchestration(
          new OrchestratorWrapper<>(clazz)
      );
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Start the Workflow runtime.
   *
   * @param port to be opened
   */
  public void start(int port) {
    this.client = this.builder.port(port).build();
    this.client.start();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void close() {
    if (this.client != null) {
      this.client.close();
    }
  }
}