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

import io.dapr.actors.ActorId;
import io.dapr.actors.client.ActorClient;
import io.dapr.actors.client.ActorProxyBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Client for Actor runtime to invoke actor methods.
 * 1. Build and install jars:
 * mvn clean install
 * 2. cd to [repo-root]/examples
 * 3. Run the client:
 * dapr run --components-path ./components/actors --app-id demoactorclient -- java -jar \
 * target/dapr-java-sdk-examples-exec.jar io.dapr.examples.actors.DemoActorClient
 */
public class DemoWorkflowClient {

  /**
   * The main method.
   * @param args Input arguments (unused).
   * @throws InterruptedException If program has been interrupted.
   */
  public static void main(String[] args) throws InterruptedException {
    System.out.println("Done.");
  }
}
