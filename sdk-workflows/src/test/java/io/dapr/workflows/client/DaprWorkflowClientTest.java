package io.dapr.workflows.client;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DaprWorkflowClientTest {
  private DaprWorkflowClient client;
  private String expectedScheduledNewInstanceId;

  @Before
  public void setUp() throws Exception {
    expectedScheduledNewInstanceId = "TestWorkflowInstanceId";
    client = mock(DaprWorkflowClient.class);
    when(client.scheduleNewWorkflow(any(String.class))).thenReturn(expectedScheduledNewInstanceId);
  }

  @Test
  public void scheduleNewWorkflow() {

    Assert.assertEquals(expectedScheduledNewInstanceId, client.scheduleNewWorkflow("TestWorkflow"));
    Assert.assertNotNull(client.scheduleNewWorkflow("TestWorkflow"));

  }

  @Test
  public void terminateWorkflow() {
    client.terminateWorkflow("TestWorkflow", null);
    verify(client, times(1)).terminateWorkflow("TestWorkflow", null);
  }

  @Test
  public void close() {
    client.close();
    verify(client, times(1)).close();
  }
}
