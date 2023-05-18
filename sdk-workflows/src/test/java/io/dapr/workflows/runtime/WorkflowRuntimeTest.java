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


import com.microsoft.durabletask.DurableTaskGrpcWorkerBuilder;
import org.junit.Test;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class WorkflowRuntimeTest {

  private static Constructor<WorkflowRuntime> constructor;
  private DurableTaskGrpcWorkerBuilder mockWorkerBuilder;

  public static class TestWorkflow implements Workflow{

    public TestWorkflow(){}

    @Override
    public void run(WorkflowContext ctx) {

    }
  }

  @Test
  public void registerWorkflowClass() {
    assertDoesNotThrow(() -> WorkflowRuntime.getInstance().registerWorkflow(TestWorkflow.class));
  }

  @Test
  public void startAndClose() {
    assertDoesNotThrow(() -> {
      WorkflowRuntime.getInstance().start();
      WorkflowRuntime.getInstance().close();
    });
  }
}
