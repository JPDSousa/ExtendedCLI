package org.extendedCLI.test.argument;

import org.extendedCLI.argument.Argument;
import org.extendedCLI.argument.ArgumentEnum;
import org.extendedCLI.argument.Requires;

interface Args {
	static final Argument _ARG1 = Argument.create("This", Requires.FALSE);
	static final Argument _ARG2 = Argument.create("That", Requires.OPTIONAL, "just a normal description");
	static final Argument _ARG3 = Argument.create("Those", Requires.TRUE, "A small and simple description", new String[]{"this", "that"}, "this");
	static final Argument _ARG4 = Argument.create("These", Requires.OPTIONAL);
}

enum EnumTest implements ArgumentEnum {
	
	ARG1(EnumTest.GROUP_ID_1, Args._ARG1),
	ARG2(EnumTest.GROUP_ID_1, Args._ARG2),
	ARG3(EnumTest.GROUP_ID_2, Args._ARG3),
	ARG4(EnumTest.GROUP_ID_3, Args._ARG4);
	
	private final int groupID;
	private final Argument argument;
	
	static final int GROUP_ID_1 = 1;
	static final int GROUP_ID_2 = 2;
	static final int GROUP_ID_3 = 3;
	
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