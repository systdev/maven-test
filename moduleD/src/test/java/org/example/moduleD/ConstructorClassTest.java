package org.example.moduleD;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Test class demonstrating PowerMock usage for constructors
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ConstructorClass.class)
public class ConstructorClassTest {

    @Test
    public void testConstructorMocking() throws Exception {
        ConstructorClass mock = mock(ConstructorClass.class);

        PowerMockito.whenNew(ConstructorClass.class)
                .withArguments("John")
                .thenReturn(mock);

        Mockito.when(mock.getName()).thenReturn("Mocked Name");
        Mockito.when(mock.getAge()).thenReturn(25);
        Mockito.when(mock.getDescription()).thenReturn("Mocked Description");

        ConstructorClass instance = new ConstructorClass("John");

        assertEquals("Mocked Name", instance.getName());
        assertEquals(25, instance.getAge());
        assertEquals("Mocked Description", instance.getDescription());

        verify(mock).getName();
        verify(mock).getAge();

        PowerMockito.verifyNew(ConstructorClass.class).withArguments("John");
    }

    @Test
    public void testConstructorWithTwoArguments() throws Exception {
        ConstructorClass mock = mock(ConstructorClass.class);

        PowerMockito.whenNew(ConstructorClass.class)
                .withArguments("Jane", 30)
                .thenReturn(mock);

        Mockito.when(mock.getName()).thenReturn("Jane");
        Mockito.when(mock.getAge()).thenReturn(30);

        ConstructorClass instance = new ConstructorClass("Jane", 30);

        assertEquals("Jane", instance.getName());
        assertEquals(30, instance.getAge());

        PowerMockito.verifyNew(ConstructorClass.class).withArguments("Jane", 30);
    }

    @Test
    public void testConstructorWithNoArguments() throws Exception {
        ConstructorClass mock = mock(ConstructorClass.class);

        PowerMockito.whenNew(ConstructorClass.class)
                .withNoArguments()
                .thenReturn(mock);

        Mockito.when(mock.getName()).thenReturn("Unknown");
        Mockito.when(mock.getAge()).thenReturn(0);

        ConstructorClass instance = new ConstructorClass();

        assertEquals("Unknown", instance.getName());
        assertEquals(0, instance.getAge());

        PowerMockito.verifyNew(ConstructorClass.class).withNoArguments();
    }

    @Test
    public void testConstructorRealBehavior() throws Exception {
        // Test real constructor behavior without mocking
        ConstructorClass instance = new ConstructorClass("John", 25);

        assertEquals("John", instance.getName());
        assertEquals(25, instance.getAge());
        assertEquals(true, instance.isInitialized());
        assertEquals("Name: John, Age: 25", instance.getDescription());
    }

    @Test
    public void testMultipleConstructorCalls() throws Exception {
        ConstructorClass mock1 = PowerMockito.mock(ConstructorClass.class);
        ConstructorClass mock2 = PowerMockito.mock(ConstructorClass.class);

        PowerMockito.whenNew(ConstructorClass.class)
                .withArguments("First")
                .thenReturn(mock1);

        PowerMockito.whenNew(ConstructorClass.class)
                .withArguments("Second")
                .thenReturn(mock2);

        Mockito.when(mock1.getName()).thenReturn("First");
        Mockito.when(mock2.getName()).thenReturn("Second");

        ConstructorClass instance1 = new ConstructorClass("First");
        ConstructorClass instance2 = new ConstructorClass("Second");

        assertEquals("First", instance1.getName());
        assertEquals("Second", instance2.getName());

        PowerMockito.verifyNew(ConstructorClass.class, Mockito.times(2))
                .withArguments(org.mockito.Matchers.anyString());
    }

    @Test
    public void testConstructorException() throws Exception {
        ConstructorClass mock = mock(ConstructorClass.class);

        PowerMockito.whenNew(ConstructorClass.class)
                .withArguments("Error")
                .thenThrow(new RuntimeException("Constructor Error"));

        try {
            new ConstructorClass("Error");
        } catch (RuntimeException e) {
            assertEquals("Constructor Error", e.getMessage());
        }

        PowerMockito.verifyNew(ConstructorClass.class).withArguments("Error");
    }
}
