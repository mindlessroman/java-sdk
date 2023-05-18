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
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Constructor;

import static org.mockito.Mockito.mock;

public class WorkflowRuntimeTest {

  private static Constructor<WorkflowRuntime> constructor;
  private WorkflowRuntime runtime;

  @BeforeClass
  public static void beforeAll() throws Exception {
    constructor = WorkflowRuntime.class.getDeclaredConstructor(DurableTaskGrpcWorkerBuilder.class);
    constructor.setAccessible(true);
  }
  @Before
  public void setUp() throws Exception {
    //    DurableTaskGrpcWorkerBuilder mockWorkerBuilder =
    //        mock(DurableTaskGrpcWorkerBuilder.class);
    //
    //    this.runtime = constructor.newInstance(mockWorkerBuilder);
  }

  //  @Test
  //  public void getInstance() {
  //    Assert.assertEquals(this.runtime, WorkflowRuntime.getInstance());
  //  }
  //  @Test
  //  public void registerWorkflowClass() {
  //    this.runtime.registerWorkflow(null);
  //  }
}
