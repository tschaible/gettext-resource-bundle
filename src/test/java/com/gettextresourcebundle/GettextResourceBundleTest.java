/**
 * Copyright (c) 2012 Tom Schaible
 * See the file license.txt for copying permission.
 */
package com.gettextresourcebundle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.gettextresourcebundle.GettextResourceBundle;

/**
 * @author Tom Schaible
 * 
 *         Unit tests for GettextResourceBundle
 */
public class GettextResourceBundleTest {

	@Test
	public void testNormalPOFile()
	{
		GettextResourceBundle rb = new GettextResourceBundle(Thread.currentThread().getContextClassLoader().getResourceAsStream("test.po"));
		
		assertEquals(rb.getString("key1"),"value1");
		assertEquals(rb.getString("key2"),"value2");
		assertFalse(rb.containsKey("key3\\nwith some other stuff"));		
		assertEquals(rb.getString("key3\nwith some other stuff"),"value3\nwith some different stuff");
		
		
	}
}
