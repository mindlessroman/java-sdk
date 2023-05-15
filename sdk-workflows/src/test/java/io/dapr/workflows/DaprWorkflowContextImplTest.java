package io.dapr.workflows;

import org.junit.Test;
import org.junit.Assert;
import org.junit.Before;

public class DaprWorkflowContextImplTest {
    private DaprWorkflowContextImpl context;

    @Before
    public void setUp() {
        context = new DaprWorkflowContextImpl("TestContext");
    }


    @Test
    public void getName() {
        Assert.assertEquals(context.getName(), "TestContext");
    }

    @Test
    public void getInstanceId() {
        Assert.assertNotNull(context.getInstanceId());
    }
}