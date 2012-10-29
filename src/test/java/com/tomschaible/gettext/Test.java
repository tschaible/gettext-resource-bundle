package com.tomschaible.gettext;

import java.util.Locale;
import java.util.ResourceBundle;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ResourceBundle rb = ResourceBundle.getBundle("locale", Locale.US, GettextResourceBundleControl.getControl("test"));

		System.exit(0);
	}

}
