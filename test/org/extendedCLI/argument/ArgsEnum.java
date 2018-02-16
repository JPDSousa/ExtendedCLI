package org.extendedCLI.argument;

import org.extendedCLI.argument.Argument;
import org.extendedCLI.argument.ArgumentEnum;
import org.extendedCLI.argument.Requires;

interface Args {
	// Be careful when changing this. Some tests rely on the arguments' values
	static final Argument _ARG1 = Argument.create("A", Requires.FALSE);
	static final Argument _ARG2 = Argument.create("B", Requires.OPTIONAL, "just a normal description");
	static final Argument _ARG3 = Argument.create("C", Requires.TRUE, "A small and simple description", new String[]{"this", "that"}, "this");
	static final Argument _ARG4 = Argument.create("D", Requires.OPTIONAL);
}

enum ArgsEnum implements ArgumentEnum {
	
	ARG1(ArgsEnum.GROUP_ID_1, Args._ARG1),
	ARG2(ArgsEnum.GROUP_ID_1, Args._ARG2),
	ARG3(ArgsEnum.GROUP_ID_2, Args._ARG3),
	ARG4(ArgsEnum.GROUP_ID_3, Args._ARG4);
	
	private final int groupID;
	private final Argument argument;
	
	static final int GROUP_ID_1 = 1;
	static final int GROUP_ID_2 = 2;
	static final int GROUP_ID_3 = 3;
	
	private ArgsEnum(int groupID, Argument argument) {
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