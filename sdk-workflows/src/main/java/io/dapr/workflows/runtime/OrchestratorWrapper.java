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

import com.microsoft.durabletask.TaskOrchestration;
import com.microsoft.durabletask.TaskOrchestrationFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Wrapper for Durable Task Framework orchestration factory.
 */
class OrchestratorWrapper<T extends Workflow> implements TaskOrchestrationFactory {
  private final Constructor<T> workflowConstructor;
  private final String name;

  public OrchestratorWrapper(Class<T> clazz) throws NoSuchMethodException {
    this.name = clazz.getCanonicalName();
    this.workflowConstructor = clazz.getDeclaredConstructor();
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public TaskOrchestration create() {
    return ctx -> {
      System.out.println("Running!");
      ctx.complete(null);
    };
//    return ctx -> {
//      try {
//        T workflow = this.workflowConstructor.newInstance();
//        workflow.run(new DaprWorkflowContextImpl(ctx));
//      } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
//        throw new RuntimeException(e);
//      }
//    };

  }
}
