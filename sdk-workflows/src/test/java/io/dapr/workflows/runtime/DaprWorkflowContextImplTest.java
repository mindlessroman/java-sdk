package io.dapr.workflows.runtime;

import com.microsoft.durabletask.Task;
import com.microsoft.durabletask.TaskOrchestrationContext;
import org.junit.Test;
import org.junit.Assert;
import org.junit.Before;

import java.time.Duration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DaprWorkflowContextImplTest {
  private DaprWorkflowContextImpl context;

  @Before
  public void setUp() {
    TaskOrchestrationContext mockedInnerContext = mock(TaskOrchestrationContext.class);
    Task<Void> mockedExternalTask = mock(Task.class);

    when(mockedInnerContext.getInstanceId()).thenReturn("TestInstance");
    when(mockedInnerContext.getName()).thenReturn("TestName");
    when(mockedInnerContext.waitForExternalEvent(any(), any(Duration.class))).thenReturn(mockedExternalTask);

    context = new DaprWorkflowContextImpl(mockedInnerContext);
  }

  @Test
  public void getNameTest() {
    Assert.assertNotNull(context.getName());
  }

  @Test
  public void getInstanceIdTest() {
    Assert.assertNotNull(context.getInstanceId());
  }

  @Test
  public void waitForExternalEventAsyncTest() {
    Assert.assertNotNull(
        context.waitForExternalEventAsync("TestEvent", Duration.ofSeconds(1)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void DaprWorkflowContextWithEmptyInnerContext() {
    context = new DaprWorkflowContextImpl(null);
  }
}
