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

package io.dapr.examples.workflows;

import com.microsoft.durabletask.TaskCanceledException;
import io.dapr.workflows.runtime.Workflow;
import io.dapr.workflows.runtime.WorkflowContext;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

/**
 * Implementation of the DemoWorkflow for the server side.
 */
public class DemoWorkflow extends Workflow {

  @Override
  public void run(WorkflowContext ctx) {
    ctx.getOut().println("Starting Workflow: " + ctx.getName());
    ctx.getOut().println("Instance ID: " + ctx.getInstanceId());
    ctx.getOut().println("Waiting for event: 'myEvent'...");
    try {
      ctx.waitForExternalEvent("myEvent", Duration.ofSeconds(10)).await();
      ctx.getOut().println("Received!");
    } catch (TaskCanceledException e) {
      ctx.getErr().println("Timed out");
      ctx.getErr().println(e.getMessage());
    }
    ctx.complete("finished");
  }
}