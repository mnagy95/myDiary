package main.java.com.hataridonaplo;

import java.applet.Applet;
import java.awt.Choice;

@SuppressWarnings("serial")
public class SwingInterface extends Applet {

	public void init() {

		/*
		 * To create a AWT choice control or a combobox, use Choice()
		 * constructor of AWT Choice class.
		 */
		Choice year = new Choice();
		
		year.add("1995");
		year.add("1996");
		year.add("1997");
		year.add("1998");
		year.add("1999");
		year.add("2000");
		year.add("2001");
		year.add("2002");
		year.add("2003");
		year.add("2004");
		year.add("2005");
		year.add("2006");

		Choice month = new Choice();

		/*
		 * To add items in a choice control or a combobox, use void add(String
		 * item) method of AWT Choice class.
		 */
		month.add("January");
		month.add("February");
		month.add("March");
		month.add("April");
		month.add("May");
		month.add("June");
		month.add("July");
		month.add("August");
		month.add("September");
		month.add("October");
		month.add("November");
		month.add("December");

		// add choice or combobox
		add(year);
		add(month);
	}
}
