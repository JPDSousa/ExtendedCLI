package org.extendedCLI.test.argument;

import static org.junit.Assert.*;

import org.extendedCLI.main.argument.Argument;
import org.extendedCLI.main.argument.Requires;
import org.junit.Test;
import org.junit.Test.None;

@SuppressWarnings("javadoc")
public class ArgumentTest {

	@Test(expected = None.class)
	public void testArgumentConstructorNameRequires() {
		Argument.create("thisName", Requires.FALSE);
		Argument.create("a", Requires.OPTIONAL);
		Argument.create("JustANormalLongExtendedNoSpacedArgument", Requires.TRUE);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testArgumentConstructorNameRequiresNoName() {
		Argument.create("", Requires.FALSE);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testArgumentConstructorNameRequiresNullName() {
		Argument.create(null, Requires.OPTIONAL);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testArgumentConstructorNameRequiresSpacedName() {
		Argument.create("This Space", Requires.FALSE);
	}

}
