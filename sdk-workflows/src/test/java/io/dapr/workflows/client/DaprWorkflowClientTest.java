package io.dapr.workflows.client;

import com.microsoft.durabletask.DurableTaskClient;
import com.microsoft.durabletask.OrchestrationMetadata;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DaprWorkflowClientTest {
  private DaprWorkflowClient client;
  private String expectedScheduledNewInstanceId;
  private OrchestrationMetadata mockedMetadata;


  @Before
  public void setUp() throws Exception {
    expectedScheduledNewInstanceId = "TestWorkflowInstanceId";
    DurableTaskClient innerClient = mock(DurableTaskClient.class);
    mockedMetadata = mock(OrchestrationMetadata.class);

    when(innerClient.scheduleNewOrchestrationInstance(any())).thenReturn(expectedScheduledNewInstanceId);
    when(innerClient.getInstanceMetadata(any(), false)).thenReturn(mockedMetadata);
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

  @Test
  public void getWorkflowMetadata() {

    client.getWorkflowMetadata("TestWorkflow");
  }

  @Test
  public void terminateWorkflow() {
    when(mockedMetadata.isRunning()).thenReturn(false);
    client.terminateWorkflow("TestWorkflow");
    Assert.assertEquals(client.getWorkflowMetadata("TestWorkflow").isRunning(), false);
  }

  @Test
  public void close() {
  }
}
