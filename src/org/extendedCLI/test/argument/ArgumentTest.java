package org.extendedCLI.test.argument;

import static org.junit.Assert.*;

import org.extendedCLI.main.argument.Argument;
import org.extendedCLI.main.argument.Requires;
import org.junit.Test;
import org.junit.Test.None;

@SuppressWarnings("javadoc")
public class ArgumentTest {

	@Test(expected = None.class)
	public void testConstructorNameRequires() {
		assertNotNull(Argument.create("thisName", Requires.FALSE));
		assertNotNull(Argument.create("a", Requires.OPTIONAL));
		assertNotNull(Argument.create("JustANormalLongExtendedNoSpacedArgument", Requires.TRUE));
	}
	
	@Test(expected = None.class)
	public void testConstructorNameRequiresDescription() {
		assertNotNull(Argument.create("name", Requires.FALSE, "Desc"));
	}
	
	@Test(expected = None.class)
	public void testConstructorNameRequiresValidValues() {
		assertNotNull(Argument.create("Name", Requires.OPTIONAL, new String[]{"Aa", "BAs"}));
	}
	
	@Test(expected = None.class)
	public void testConstructorNameRequiresDescriptionValidValues() {
		assertNotNull(Argument.create("Name", Requires.FALSE, "Desc", new String[]{"Valid", "Values"}));
	}
	
	@Test(expected = None.class)
	public void testConstructorNameRequiresDescriptionValidValuesDefaultValue() {
		assertNotNull(Argument.create("Name", Requires.FALSE, "Desc", new String[]{"Valid", "Values"}, "Values"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNoName() {
		Argument.create("", Requires.FALSE);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNullName() {
		Argument.create(null, Requires.OPTIONAL);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorSpacedName() {
		Argument.create("This Space", Requires.FALSE);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNullRequires() {
		Argument.create("Name", null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNullDescription(){
		Argument.create("Name", Requires.TRUE, String.class.cast(null));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorInvalidDefaultValue() {
		Argument.create("Name", Requires.TRUE, "Desc", new String[]{"Valid"}, "Value");
	}
	
	@Test
	public void testGetName() {
		final String name = "match";
		final Argument argument = Argument.create(name, Requires.TRUE);
		assertEquals("Name set on constructor and returned be getName should match.", name, argument.getName());
	}

	@Test
	public void testGetFormattedName() {
		final String name = "match";
		final Argument argument = Argument.create(name, Requires.TRUE);
		assertEquals("getFormattedName should return a syntax version of the name set on constructor.", "-"+name, argument.getFormattedName());
	}
	
	@Test
	public void testRequiresValue() {
		Argument argument;
		for(Requires requires : Requires.values()) {
			argument = Argument.create("Name", requires);
			assertEquals("RequiresValue should have returned: " + requires, requires, argument.requiresValue());
		}
	}
	
	
}
