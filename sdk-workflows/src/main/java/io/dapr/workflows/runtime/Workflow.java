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

import com.google.protobuf.Empty;
import reactor.core.publisher.Mono;

/**
 * Common interface for workflow implementations.
 */
public abstract class Workflow {
  public Workflow(){
  }

  /**
   * Executes the workflow logic.
   *
   * @return A WorkflowStub.
   */
  public abstract WorkflowStub create();

  /**
   * Executes the workflow logic.
   *
   * @param ctx provides access to methods for scheduling durable tasks and getting information about the current
   *            workflow instance.
   * @return A Mono Plan of type Void.
   */
  public Mono<Void> runAsync(WorkflowContext ctx) {
    return Mono.<Empty>create(it -> {
      this.create().run(ctx);
      it.success();
    }).then();
  }
}
