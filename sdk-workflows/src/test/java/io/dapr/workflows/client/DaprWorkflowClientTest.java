package io.dapr.workflows.client;

import com.microsoft.durabletask.DurableTaskClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DaprWorkflowClientTest {
  private DaprWorkflowClient client;
  private String expectedScheduledNewInstanceId;

  @Before
  public void setUp() throws Exception {
    expectedScheduledNewInstanceId = "TestWorkflowInstanceId";
    DurableTaskClient innerClient = mock(DurableTaskClient.class);
    when(innerClient.scheduleNewOrchestrationInstance(any())).thenReturn(expectedScheduledNewInstanceId);

    client = new DaprWorkflowClient(innerClient);
  }

  @Test
  public void scheduleNewWorkflow() {

    Assert.assertEquals(expectedScheduledNewInstanceId, client.scheduleNewWorkflow("TestWorkflow"));
    Assert.assertNotNull(client.scheduleNewWorkflow("TestWorkflow"));

  }

  @Test(expected = IllegalArgumentException.class)
  public void scheduleNewWorkflowEmptyContext() throws IllegalArgumentException {
    client = new DaprWorkflowClient(null);
  }
}
