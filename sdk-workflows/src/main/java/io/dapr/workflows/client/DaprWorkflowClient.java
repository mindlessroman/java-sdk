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
import io.dapr.config.Properties;
import io.dapr.utils.Version;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import javax.annotation.Nullable;

public class DaprWorkflowClient implements AutoCloseable {

  private DurableTaskClient innerClient;
  private ManagedChannel grpcInnerChannel;

  /**
   * Public constructor for DaprWorkflowClient.
   */
  public DaprWorkflowClient() {
    this(setGrpcChannel());
  }

  private DaprWorkflowClient(ManagedChannel grpcChannel) {
    this(createDurableTaskClient(grpcChannel), grpcChannel);
  }

  /**
   * Private Constructor for DaprWorkflowClient.
   *
   * @param innerClient DurableTaskGrpcClient with gprc Channel set up.
   */
  private DaprWorkflowClient(DurableTaskClient innerClient, ManagedChannel grpcChannel) {
    this.innerClient = innerClient;
    this.grpcInnerChannel = grpcChannel;
  }

  private static DurableTaskClient createDurableTaskClient(ManagedChannel grpcChannel) {
    return new DurableTaskGrpcClientBuilder()
        .grpcChannel(grpcChannel)
        .build();
  }

  private static ManagedChannel setGrpcChannel() throws IllegalStateException {
    int port = Properties.GRPC_PORT.get();
    if (port <= 0) {
      throw new IllegalStateException("Invalid port.");
    }

    ManagedChannel channel =  ManagedChannelBuilder.forAddress(Properties.SIDECAR_IP.get(), port)
        .usePlaintext()
        .userAgent(Version.getSdkVersion())
        .build();
    return channel;
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
    if (this.innerClient != null && !this.grpcInnerChannel.isShutdown()) {
      this.innerClient.close();
      this.grpcInnerChannel.shutdown();
    }
  }
}
