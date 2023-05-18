/*
 * Copyright 2021 The Dapr Authors
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

import com.microsoft.durabletask.DurableTaskClient;
import com.microsoft.durabletask.DurableTaskGrpcClientBuilder;
import com.microsoft.durabletask.NewOrchestrationInstanceOptions;
import com.microsoft.durabletask.OrchestrationMetadata;
import io.dapr.workflows.runtime.WorkflowRuntime;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * Service for Workflow runtime.
 * 1. Build and install jars:
 * mvn clean install
 * 2. cd to [repo-root]/examples
 * 3. Run the server:
 * dapr run --app-id demoworkflowservice --dapr-grpc-port 4001
 * java -jar target/dapr-java-sdk-examples-exec.jar io.dapr.examples.workflows.DemoWorkflowService
 */
public class DemoWorkflowService {

  /**
   * The main method of this app.
   *
   * @param args The port the app will listen on.
   * @throws Exception An Exception.
   */
  public static void main(String[] args) throws Exception {
    // Register the Workflow with the runtime.
    WorkflowRuntime.getInstance().registerWorkflow(DemoWorkflow.class);
    WorkflowRuntime.getInstance().start(4001);
    System.out.println("Start workflow runtime");

    final DurableTaskClient client = new DurableTaskGrpcClientBuilder()
        .port(4001)
        .build();

    TimeUnit.SECONDS.sleep(2);
    String name = DemoWorkflow.class.getCanonicalName();
    NewOrchestrationInstanceOptions options = new NewOrchestrationInstanceOptions();
    // TODO: figure out why this causes a seg violation within dapr runtime
    String instanceId = client.scheduleNewOrchestrationInstance(name);
    // String instanceId = client.scheduleNewOrchestrationInstance(name, options);
    System.out.printf("Started new orchestration instance: %s%n", instanceId);

    TimeUnit.SECONDS.sleep(10);

    WorkflowRuntime.getInstance().close();
    System.out.println("Done");
    System.exit(0);
  }
}
