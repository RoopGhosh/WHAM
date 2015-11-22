package edu.neu.cs5500.jerks.TS1;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;
import org.junit.internal.matchers.CombinableMatcher;

import edu.neu.cs5500.jerks.*;
public class TestJUnit1 {

	@Test
	  public void testAssertArrayEquals() {
	    byte[] expected = "trial".getBytes();
	    byte[] actual = "trial".getBytes();
	    org.junit.Assert.assertArrayEquals("failure - byte arrays not same", expected, actual);
	  }
	@Test
	  public void testAssertEquals() {
	   assertEquals("failure - strings are not equal", "text", "text");
	  }

	  @Test
	  public void testAssertFalse() {
	    org.junit.Assert.assertFalse("failure - should be false", false);
	  }

	  @Test
	  public void testAssertNotNull() {
	    org.junit.Assert.assertNotNull("should not be null", new Object());
	  }

	  @Test
	  public void testAssertNotSame() {
	    org.junit.Assert.assertNotSame("should not be same Object", new Object(), new Object());
	  }

	  @Test
	  public void testAssertNull() {
	    org.junit.Assert.assertNull("should be null", null);
	  }

	  @Test
	  public void testAssertSame() {
	    Integer aNumber = Integer.valueOf(768);
	    org.junit.Assert.assertSame("should be same", aNumber, aNumber);
	  }

	 
	  @Test
	  public void testAssertTrue() {
		  assertTrue("failure - should be true", true);
	  }
}
