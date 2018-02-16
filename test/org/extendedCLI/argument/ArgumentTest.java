package org.extendedCLI.argument;

import static org.junit.Assert.*;

import org.extendedCLI.argument.Argument;
import org.extendedCLI.argument.Requires;
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
	public void testConstructorNameRequiresValidValuesDefaultValue() {
		assertNotNull(Argument.create("Name", Requires.OPTIONAL, new String[]{"default"}, "default"));
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
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNoValidValuesButDefaultValue() {
		Argument.create("Name", Requires.OPTIONAL, "Desc", null, "Something");
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
	
	@Test
	public void testGetValidValuesConstructor() {
		final String[] validValues = new String[]{"valid", "values"};
		final Argument argument = Argument.create("Name", Requires.FALSE, validValues, validValues[0]);
		
		assertArrayEquals("Value passed on constructor should be returned by getter", validValues, argument.getValidValues());
	}
	
	@Test
	public void testGetValidValues() {
		final String[] validValues = new String[]{"valid", "values"};
		final Argument argument = Argument.create("Name", Requires.OPTIONAL, new String[]{"this", "that"}, "this");
		argument.setValidValues(validValues);
		
		assertArrayEquals("Value passed on setter should be returned by getter", validValues, argument.getValidValues());
	}
	
	@Test
	public void testIsValid() {
		final String valid = "valid";
		final String[] validValues = new String[]{valid, "values"};
		final Argument argument = Argument.create("Name", Requires.TRUE, validValues, validValues[0]);
		
		assertFalse("Null is not valid", argument.isValid(null));
		assertFalse("Empty string is not valid", argument.isValid(""));
		assertTrue("This value is in the validValues array, therefore it should be valid", argument.isValid(valid));
		assertFalse("This value is not in the validValues array, therefore it should not be valid.", argument.isValid("invalid"));
	}
	
	@Test
	public void testGetDescriptionConstructor() {
		final String description = "Just a normal description";
		final Argument argument = Argument.create("A", Requires.OPTIONAL, description);
		
		assertEquals("Value passed on constructor should be returned by getter", description, argument.getDescription());
	}
	
	@Test
	public void testGetLongName() {
		final String longName = "This is a perfectly valid long name";
		final Argument argument = Argument.create("Name", Requires.FALSE);
		argument.setLongName(longName);
		
		assertEquals("get should return set value", longName, argument.getLongName());
	}
	
	@Test
	public void testGetDefaultValueConstructor() {
		final String defaultValue = "default";
		final Argument argument = Argument.create("Name", Requires.OPTIONAL, "Desc", new String[]{defaultValue}, defaultValue);
		
		assertEquals("Value passed on constructor should be returned by getter", defaultValue, argument.getDefaultValue());
	}
	
	@Test
	public void testGetDefaultValue() {
		final String defaultValue = "default";
		final Argument argument = Argument.create("Name", Requires.OPTIONAL, "Desc", new String[]{"this", defaultValue}, "this");
		argument.setDefaultValue(defaultValue);
		
		assertEquals("Get should return set value", defaultValue, argument.getDefaultValue());
	}

	@Test
	public void testEqualsWithNullObject() {
		final Argument argument = Argument.create("Name", Requires.OPTIONAL, "Desc", new String[]{"this", "default"}, "this");

		assertFalse("Equals should return false when compared to a null", argument.equals(null));
	}

	@Test
	public void testEqualsWithDifferentClass() {
		final Argument argument = Argument.create("Name", Requires.OPTIONAL, "Desc", new String[]{"this", "default"}, "this");

		assertFalse("Equals should return false when compared instance of a different class", argument.equals("test"));
	}

	@Test
	public void testEqualsWhenOnlyNamesAreEqual() {
		final Argument argument = Argument.create("Name", Requires.TRUE, "Description", new String[]{"this1", "default1"}, "this1");
		final Argument testArgument = Argument.create("Name", Requires.OPTIONAL, "Desc", new String[]{"this2", "default2"}, "this2");

		assertTrue("Equals should return true when the names are the same", argument.equals(testArgument));
	}

	@Test
	public void testToStringContainsNameAndRequired() {
		final Argument argument = Argument.create("Name", Requires.TRUE, "Description", new String[]{"this1", "default1"}, "this1");

		assertEquals("Name{TRUE}", argument.toString());
	}
}
