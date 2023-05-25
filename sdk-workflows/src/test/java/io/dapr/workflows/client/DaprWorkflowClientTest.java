package io.dapr.workflows.client;

import com.microsoft.durabletask.DurableTaskClient;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.util.Arrays;

import static org.mockito.Mockito.*;

public class DaprWorkflowClientTest {
  private DaprWorkflowClient client;

  private static Constructor<DaprWorkflowClient> constructor;
  private DurableTaskClient mockInnerClient;

  @BeforeClass
  public static void beforeAll() {
        constructor =
        Constructor.class.cast(Arrays.stream(DaprWorkflowClient.class.getDeclaredConstructors())
            .filter(c -> c.getParameters().length == 1).map(c -> {
              c.setAccessible(true);
              return c;
            }).findFirst().get());
  }

  @Before
  public void setUp() throws Exception {
    mockInnerClient = mock(DurableTaskClient.class);
    client = constructor.newInstance(mockInnerClient);
  }

  @Test
  public void scheduleNewWorkflow() {
    String expectedArgument = "TestWorkflow";

    client.scheduleNewWorkflow(expectedArgument);
    verify(mockInnerClient, times(1)).scheduleNewOrchestrationInstance(expectedArgument);
  }

  @Test
  public void terminateWorkflow() {
    String expectedArgument = "TestWorkflow";

    client.terminateWorkflow(expectedArgument, null);
    verify(mockInnerClient, times(1)).terminate(expectedArgument, null);
  }

  @Test
  public void close() {
    client.close();
    verify(mockInnerClient, times(1)).close();
  }
}
