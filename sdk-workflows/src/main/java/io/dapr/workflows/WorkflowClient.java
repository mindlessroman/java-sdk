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

package io.dapr.workflows;

/**
 * Holds a client for Dapr sidecar communication. WorkflowClient should be reused.
 */
public class WorkflowClient implements AutoCloseable {

  /**
   * Instantiates a Durable Task client to communicate with the Dapr sidecar.
   */
  public WorkflowClient() { };
}
