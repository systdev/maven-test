package org.example.moduleD;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Test class demonstrating PowerMock usage for singleton pattern
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(SingletonClass.class)
public class SingletonClassTest {

    @Test
    public void testSingletonMocking() {
        SingletonClass mockSingleton = PowerMockito.mock(SingletonClass.class);

        PowerMockito.mockStatic(SingletonClass.class);
        when(SingletonClass.getInstance()).thenReturn(mockSingleton);

        when(mockSingleton.getCounter()).thenReturn(42);
        when(mockSingleton.getMessage()).thenReturn("Mocked");

        SingletonClass instance1 = SingletonClass.getInstance();
        SingletonClass instance2 = SingletonClass.getInstance();

        assertEquals(42, instance1.getCounter());
        assertEquals("Mocked", instance2.getMessage());
        assertEquals(instance1, instance2); // Both are the same mock

        PowerMockito.verifyStatic(SingletonClass.class, Mockito.times(2));
        SingletonClass.getInstance();
    }

    @Test
    public void testSingletonBehaviorWithSpy() {
        // Use spy to partially mock the singleton
        SingletonClass spy = PowerMockito.spy(SingletonClass.getInstance());

        when(spy.getCounter()).thenReturn(100);

        spy.increment();
        spy.increment();

        // Our spy overrides the method, so it returns 100 regardless
        assertEquals(100, spy.getCounter());

        Mockito.verify(spy, Mockito.times(2)).increment();
    }

    @Test
    public void testSingletonMethodCalls() {
        SingletonClass mock = PowerMockito.mock(SingletonClass.class);

        PowerMockito.mockStatic(SingletonClass.class);
        when(SingletonClass.getInstance()).thenReturn(mock);

        when(mock.getCounter()).thenReturn(5);
        when(mock.getMessage()).thenReturn("Test");

        SingletonClass instance = SingletonClass.getInstance();

        instance.increment();
        instance.setMessage("New Message");
        instance.reset();

        PowerMockito.verifyStatic(SingletonClass.class);
        SingletonClass.getInstance();

        Mockito.verify(mock).increment();
        Mockito.verify(mock).setMessage("New Message");
        Mockito.verify(mock).reset();
    }

    @Test
    public void testSingletonResetBehavior() {
        PowerMockito.mockStatic(SingletonClass.class);

        SingletonClass mock1 = PowerMockito.mock(SingletonClass.class);
        SingletonClass mock2 = PowerMockito.mock(SingletonClass.class);

        when(SingletonClass.getInstance()).thenReturn(mock1);
        when(mock1.getCounter()).thenReturn(10);

        assertEquals(10, SingletonClass.getInstance().getCounter());

        // Change the mock
        when(SingletonClass.getInstance()).thenReturn(mock2);
        when(mock2.getCounter()).thenReturn(20);

        assertEquals(20, SingletonClass.getInstance().getCounter());
    }
}
