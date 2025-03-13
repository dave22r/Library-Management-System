package model;

import model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Event class
 */
public class EventTest {
    private Event e;
    private Date d;
    private Event event1;
    private Event event2;
    private Object object;

    //NOTE: these tests might fail if time at which line (2) below is executed
    //is different from time that line (1) is executed.  Lines (1) and (2) must
    //run in same millisecond for this test to make sense and pass.

    @BeforeEach
    public void runBefore() {
        e = new Event("Added Book");   // (1)
        d = Calendar.getInstance().getTime();   // (2)
        event1 = new Event("Test Event");
        event2 = new Event("Test Event");
        object = new Object();
    }

    @Test
    public void testEvent() {
        assertEquals("Added Book", e.getDescription());
        assertEquals(d.getDay(), e.getDate().getDay());
        assertEquals(d.getMonth(), e.getDate().getMonth());
        assertEquals(d.getYear(), e.getDate().getYear());
        assertEquals(d.getHours(), e.getDate().getHours());
    }

    @Test
    public void testToString() {
        assertEquals(d.toString() + "\n" + "Added Book", e.toString());
    }



    @Test
    public void testEquals() {
        // Check if event1 is equal to itself
        assertTrue(e.equals(e));

        // Check if event1 is equal to event2 (same description and close creation times)
        assertTrue(event1.equals(event2));

        // Check if event1 is not equal to event3 (different description)
        assertFalse(event1.equals(e));

        // Check if event1 is not equal to null
        assertFalse(event1.equals(null));

        // Check if event1 is not equal to a different type of object
        assertFalse(event1.equals(object));
    }


}