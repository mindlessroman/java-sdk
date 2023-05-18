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

import com.microsoft.durabletask.*;
import io.dapr.config.Properties;
import io.dapr.utils.Version;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

/**
 * Service for Workflow runtime.
 * 1. Build and install jars:
 * mvn clean install
 * 2. cd to [repo-root]/examples
 * 3. Run the server:
 * dapr run --app-id demoworkflowservice --dapr-grpc-port 4001
 * java -jar target/dapr-java-sdk-examples-exec.jar io.dapr.examples.workflows.DemoWorkflowService
 */
public class DurableDemo {

  /**
   * The main method of this app.
   *
   * @param args The port the app will listen on.
   * @throws Exception An Exception.
   */
  public static void main(String[] args) throws IOException, InterruptedException, TimeoutException {
    // The TaskHubServer listens over gRPC for new orchestration and activity execution requests
    final DurableTaskGrpcWorker worker = createWorker();

    // Start the server to begin processing orchestration and activity requests
    worker.start();

    // Start a new instance of the registered "ActivityChaining" orchestration
    final DurableTaskClient client = new DurableTaskGrpcClientBuilder().build();

    // The input is an arbitrary list of strings.
    List<String> listOfStrings =  Arrays.asList(
        "Hello, world!",
        "The quick brown fox jumps over the lazy dog.",
        "If a tree falls in the forest and there is no one there to hear it, does it make a sound?",
        "The greatest glory in living lies not in never falling, but in rising every time we fall.",
        "Always remember that you are absolutely unique. Just like everyone else.");

    // Schedule an orchestration which will reliably count the number of words in all the given sentences.
    String instanceId = client.scheduleNewOrchestrationInstance(
        "FanOutFanIn_WordCount",
        new NewOrchestrationInstanceOptions().setInput(listOfStrings));
    System.out.printf("Started new orchestration instance: %s%n", instanceId);

    // Block until the orchestration completes. Then print the final status, which includes the output.
    OrchestrationMetadata completedInstance = client.waitForInstanceCompletion(
        instanceId,
        Duration.ofSeconds(30),
        true);
    System.out.printf("Orchestration completed: %s%n", completedInstance);
    System.out.printf("Output: %d%n", completedInstance.readOutputAs(int.class));

    // Shutdown the server and exit
    worker.stop();
  }

  /**
   * Creates a GRPC managed channel (or null, if not applicable).
   *
   * @return GRPC managed channel or null.
   */
  private static ManagedChannel buildManagedChannel() {
    int port = Properties.GRPC_PORT.get();
    if (port <= 0) {
      throw new IllegalStateException("Invalid port.");
    }

    return ManagedChannelBuilder.forAddress("172.17.250.113", port)
        .usePlaintext()
        .userAgent(Version.getSdkVersion())
        .build();
  }

  private static DurableTaskGrpcWorker createWorker() {
    DurableTaskGrpcWorkerBuilder builder = new DurableTaskGrpcWorkerBuilder();
    builder.grpcChannel(buildManagedChannel());

    // Orchestrations can be defined inline as anonymous classes or as concrete classes
    builder.addOrchestration(new TaskOrchestrationFactory() {
      @Override
      public String getName() {
        return "FanOutFanIn_WordCount";
      }

      @Override
      public TaskOrchestration create() {
        return ctx -> {
          // The input is a list of objects that need to be operated on.
          // In this example, inputs are expected to be strings.
          List<?> inputs = ctx.getInput(List.class);

          // Fan-out to multiple concurrent activity invocations, each of which does a word count.
          List<Task<Integer>> tasks = inputs.stream()
              .map(input -> ctx.callActivity("CountWords", input.toString(), Integer.class))
              .collect(Collectors.toList());

          // Fan-in to get the total word count from all the individual activity results.
          List<Integer> allWordCountResults = ctx.allOf(tasks).await();
          int totalWordCount = allWordCountResults.stream().mapToInt(Integer::intValue).sum();

          // Save the final result as the orchestration output.
          ctx.complete(totalWordCount);
        };
      }
    });

    // Activities can be defined inline as anonymous classes or as concrete classes
    builder.addActivity(new TaskActivityFactory() {
      @Override
      public String getName() {
        return "CountWords";
      }

      @Override
      public TaskActivity create() {
        return ctx -> {
          // Take the string input and count the number of words (tokens) it contains.
          String input = ctx.getInput(String.class);
          StringTokenizer tokenizer = new StringTokenizer(input);
          return tokenizer.countTokens();
        };
      }
    });

    return builder.build();
  }
}