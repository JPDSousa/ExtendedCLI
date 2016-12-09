package org.extendedCLI.test.argument;

import static org.junit.Assert.*;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class ArgumentEnumTest {
	
	@Test
	public void testGetGroupID() {
		assertEquals("Group ip returned should match group id set on enum construction", EnumTest.GROUP_ID, EnumTest.ARG1.getGroupID());
		assertEquals("Group ip returned should match group id set on enum construction", EnumTest.GROUP_ID, EnumTest.ARG2.getGroupID());
	}
	
	@Test
	public void testGetArgument() {
		assertEquals("Argument returned should match argument set on enum construction", Args._ARG1, EnumTest.ARG1.getArgument());
		assertNotEquals("Argument returned should not match other arguments set on enum construction", Args._ARG1, EnumTest.ARG2.getArgument());
	}

}
