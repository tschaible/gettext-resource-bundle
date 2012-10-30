/**
 * Copyright (c) 2012 Tom Schaible
 * See the file license.txt for copying permission.
 */
package com.gettextresourcebundle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.gettextresourcebundle.GettextResourceBundleControl;

/**
 * @author Tom Schaible
 * 
 *         Unit tests for GettextResourceBundleControl
 */
public class GettextResourceBundleControlTest {

	/**
	 * test that the controls picks up PO files by locale
	 * from the classpath
	 */
	@Test
	public void testClasspathReads(){
		ResourceBundle en_US = ResourceBundle.getBundle("locale", Locale.US, GettextResourceBundleControl.getControl("test"));
		assertEquals("The locale key should be en_US for the en_US locale", en_US.getString("locale"),"en_US");
		
		ResourceBundle en_CA = ResourceBundle.getBundle("locale", Locale.CANADA, GettextResourceBundleControl.getControl("test"));
		assertEquals("The locale key should be en_CA for the en_CA locale", en_CA.getString("locale"),"en_CA");		
	}
	
	/**
	 * test that the controls picks up PO files by locale
	 * from the filesystem
	 */
	@Test
	public void testFileReads(){
		ResourceBundle en_US = ResourceBundle.getBundle("./src/test/resources/locale", Locale.US, GettextResourceBundleControl.getControl("test"));
		assertEquals("The locale key should be en_US for the en_US locale", en_US.getString("locale"),"en_US");
		
		ResourceBundle en_CA = ResourceBundle.getBundle("./src/test/resources/locale", Locale.CANADA, GettextResourceBundleControl.getControl("test"));
		assertEquals("The locale key should be en_CA for the en_CA locale", en_CA.getString("locale"),"en_CA");		
	}
	
	/**
	 * test that PO files loaded from the file system will reload after 
	 * cache timeout occurs and modified dates are different
	 * @throws IOException
	 */
	@Test
	public void testFileReloads() throws IOException{
		File localeDirectory = new File("./src/test/resources/locale");
		File localeNewDirectory = new File("./src/test/resources/localeNew");
		File targetDirectory = new File("./target/testLocales");
		targetDirectory.mkdirs();
		
		//unit test with 5 second cache time
		final long cacheTTL = 5*1000l;
		GettextResourceBundleControl.setCacheTTL(cacheTTL);
		
		FileUtils.copyDirectory(localeDirectory, targetDirectory, false);
		
		ResourceBundle en_US = ResourceBundle.getBundle(targetDirectory.getAbsolutePath(), Locale.US, GettextResourceBundleControl.getControl("test"));
		assertEquals("The locale key should be en_US for the en_US locale", "en_US",en_US.getString("locale"));
		
		ResourceBundle en_CA = ResourceBundle.getBundle(targetDirectory.getAbsolutePath(), Locale.CANADA, GettextResourceBundleControl.getControl("test"));
		assertEquals("The locale key should be en_CA for the en_CA locale", "en_CA",en_CA.getString("locale"));
		
		FileUtils.copyDirectory(localeNewDirectory, targetDirectory, false);
		
		en_US = ResourceBundle.getBundle(targetDirectory.getAbsolutePath(), Locale.US, GettextResourceBundleControl.getControl("test"));
		assertEquals("The locale key should be en_US for the en_US locale before cache expiry", "en_US",en_US.getString("locale"));
		
		en_CA = ResourceBundle.getBundle(targetDirectory.getAbsolutePath(), Locale.CANADA, GettextResourceBundleControl.getControl("test"));
		assertEquals("The locale key should be en_CA for the en_CA locale before cache expiry", "en_CA",en_CA.getString("locale"));
		
		//wait until cacheTTL passes so that cache expires
		long end = System.currentTimeMillis()+(cacheTTL);
		while ( end > System.currentTimeMillis()  ) {
			try {
				Thread.sleep(end-System.currentTimeMillis());
			} catch (InterruptedException e) {

			}
		}
		
		en_US = ResourceBundle.getBundle(targetDirectory.getAbsolutePath(), Locale.US, GettextResourceBundleControl.getControl("test"));
		assertEquals("The locale key should be en_US #2 for the en_US locale before cache expiry", "en_US #2", en_US.getString("locale"));
		
		en_CA = ResourceBundle.getBundle(targetDirectory.getAbsolutePath(), Locale.CANADA, GettextResourceBundleControl.getControl("test"));
		assertEquals("The locale key should be en_CA #2 for the en_CA locale before cache expiry", "en_CA #2",en_CA.getString("locale"));
	}
}
