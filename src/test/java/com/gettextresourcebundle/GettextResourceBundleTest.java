/**
 * Copyright (c) 2012 Tom Schaible
 * See the file license.txt for copying permission.
 */
package com.gettextresourcebundle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.gettextresourcebundle.GettextResourceBundle;

/**
 * @author Tom Schaible
 * 
 *         Unit tests for GettextResourceBundle
 */
public class GettextResourceBundleTest {

	/**
	 * test a PO file containing normal text
	 * specifically test
	 *  - a normal key
	 *  - keys spread across multiple values
	 *  - keys containing the newline string
	 */
	@Test
	public void testNormalPOFile()
	{
		GettextResourceBundle rb = new GettextResourceBundle(Thread.currentThread().getContextClassLoader().getResourceAsStream("test.po"));
		
		assertEquals("Testing fetch of normal key/value pair",rb.getString("key1"),"value1");
		assertEquals("Testing fetch of key/values extended over multiple lines", rb.getString("key2"),"value2");
		assertTrue("Testing containsKey returns true for present keys", rb.containsKey("key3\nwith some other stuff"));
		assertFalse("Testing containsKey returns false for missing keys", rb.containsKey("key3\\nwith some other stuff"));		
		assertEquals("Testing fetch of key/values extended over multiple lines", rb.getString("key3\nwith some other stuff"),"value3\nwith some different stuff");
		
		
	}
	
	@Test
	public void testSpecialCharactersPOFile()
	{
		GettextResourceBundle rb = new GettextResourceBundle(Thread.currentThread().getContextClassLoader().getResourceAsStream("special-chars.po"));
		
		assertEquals("Testing escaped characters are properly parsed", rb.getString("allspecialchars"),"\t\b\n\r\f\'\"\\");
		assertEquals("Testing escaping of escaped characters are properly parsed", rb.getString("escapedspecialchars"),"\\t\\b\\n\\r\\f\\'\\\"");
		assertEquals("Testing escaped unicode characters are properly parsed", rb.getString("escapedunicode"),"π");
		assertEquals("Testing escaped octal characters are properly parsed", rb.getString("escapedoctal"),"aBc^");

	}
}
