package org.extendedCLI.test.argument;

import static org.junit.Assert.*;

import org.extendedCLI.main.argument.Argument;
import org.extendedCLI.main.argument.ArgumentEnum;
import org.extendedCLI.main.argument.Requires;
import org.junit.Test;

@SuppressWarnings("javadoc")
public class ArgumentEnumTest {
	
	private static final int GROUP_ID = 1;
	private static final Argument FIRST_ARG = Argument.create("This", Requires.FALSE);
	private static final Argument SECOND_ARG = Argument.create("That", Requires.OPTIONAL, "just a normal description");
	
	private enum enumTest implements ArgumentEnum {
		ARG1(GROUP_ID, FIRST_ARG),
		ARG2(GROUP_ID, SECOND_ARG);
		
		private final int groupID;
		private final Argument argument;
		
		private enumTest(int groupID, Argument argument) {
			this.groupID = groupID;
			this.argument = argument;
		}

		@Override
		public int getGroupID() {
			return groupID;
		}

		@Override
		public Argument getArgument() {
			return argument;
		}
		
	}

	@Test
	public void testGetGroupID() {
		assertEquals("Group ip returned should match group id set on enum construction", GROUP_ID, enumTest.ARG1.getGroupID());
		assertEquals("Group ip returned should match group id set on enum construction", GROUP_ID, enumTest.ARG2.getGroupID());
	}
	
	@Test
	public void testGetArgument() {
		assertEquals("Argument returned should match argument set on enum construction", FIRST_ARG, enumTest.ARG1.getArgument());
		assertNotEquals("Argument returned should not match other arguments set on enum construction", FIRST_ARG, enumTest.ARG2.getArgument());
	}

}
