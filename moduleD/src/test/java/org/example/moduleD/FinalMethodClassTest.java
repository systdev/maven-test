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
 * Test class demonstrating PowerMock usage for final methods
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(FinalMethodClass.class)
public class FinalMethodClassTest {

    @Test
    public void testFinalMethodMocking() {
        FinalMethodClass mock = PowerMockito.mock(FinalMethodClass.class);

        when(mock.getName()).thenReturn("Mocked Name");
        when(mock.calculate(5)).thenReturn(100);

        assertEquals("Mocked Name", mock.getName());
        assertEquals(100, mock.calculate(5));

        Mockito.verify(mock).getName();
        Mockito.verify(mock).calculate(5);
    }

    @Test
    public void testFinalMethodSpy() {
        FinalMethodClass spy = PowerMockito.spy(new FinalMethodClass());

        // Spy on final methods
        when(spy.getName()).thenReturn("Spy Name");
        when(spy.calculate(4)).thenReturn(50);

        assertEquals("Spy Name", spy.getName());
        assertEquals(50, spy.calculate(4));
        assertEquals("[ TEST ]", spy.formatText("TEST")); // Real method call
    }

    @Test
    public void testFinalMethodWithArguments() {
        FinalMethodClass mock = PowerMockito.mock(FinalMethodClass.class);

        when(mock.formatText("hello")).thenReturn("FORMATTED");
        when(mock.formatText(null)).thenReturn("NULL_INPUT");

        assertEquals("FORMATTED", mock.formatText("hello"));
        assertEquals("NULL_INPUT", mock.formatText(null));

        Mockito.verify(mock, Mockito.times(1)).formatText("hello");
        Mockito.verify(mock, Mockito.times(1)).formatText(null);
    }

    @Test
    public void testFinalMethodPartialMock() {
        FinalMethodClass mock = PowerMockito.mock(FinalMethodClass.class);

        // Mock some methods, leave others to return defaults
        when(mock.getName()).thenReturn("Partial Mock");
        when(mock.multiply(2.0, 3.0)).thenReturn(10.0);

        assertEquals("Partial Mock", mock.getName());
        assertEquals(0.0, mock.calculate(5), 0.0); // Default value
        assertEquals(10.0, mock.multiply(2.0, 3.0), 0.0);
    }

    @Test
    public void testFinalMethodVerifyMultipleCalls() {
        FinalMethodClass mock = PowerMockito.mock(FinalMethodClass.class);

        when(mock.getName()).thenReturn("Test");

        mock.getName();
        mock.getName();
        mock.getName();

        Mockito.verify(mock, Mockito.times(3)).getName();
    }
}
