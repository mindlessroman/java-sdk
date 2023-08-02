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


import org.junit.Test;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class WorkflowRuntimeTest {
    public static class TestWorkflow extends Workflow {
      @Override
      public Mono<Void> run(WorkflowContext ctx) {
        return Mono.empty().then();
      }
    }

  @Test
  public void registerValidWorkflowClass() {
    assertDoesNotThrow(() -> WorkflowRuntime.getInstance().registerWorkflow(TestWorkflow.class));
  }

  @Test
  public void startTest() {
    assertDoesNotThrow(() -> {
      WorkflowRuntime.getInstance().start();
      WorkflowRuntime.getInstance().close();
    });
  }

  @Test
  public void closeWithoutStarting() {
    assertDoesNotThrow(() -> {
      WorkflowRuntime.getInstance().close();
    });
  }
}
