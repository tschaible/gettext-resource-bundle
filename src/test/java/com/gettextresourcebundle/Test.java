package com.gettextresourcebundle;

import java.util.Locale;
import java.util.ResourceBundle;

import com.gettextresourcebundle.GettextResourceBundleControl;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ResourceBundle rb = ResourceBundle.getBundle("locale", Locale.US, GettextResourceBundleControl.getControl("test"));

		System.exit(0);
	}

}
