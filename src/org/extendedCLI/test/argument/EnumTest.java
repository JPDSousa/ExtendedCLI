package org.extendedCLI.test.argument;

import org.extendedCLI.main.argument.Argument;
import org.extendedCLI.main.argument.ArgumentEnum;
import org.extendedCLI.main.argument.Requires;

interface Args {
	static final Argument _ARG1 = Argument.create("This", Requires.FALSE);
	static final Argument _ARG2 = Argument.create("That", Requires.OPTIONAL, "just a normal description");	
}

enum EnumTest implements ArgumentEnum {
	
	ARG1(EnumTest.GROUP_ID, Args._ARG1),
	ARG2(EnumTest.GROUP_ID, Args._ARG2);
	
	private final int groupID;
	private final Argument argument;
	
	static final int GROUP_ID = 1;
	
	private EnumTest(int groupID, Argument argument) {
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