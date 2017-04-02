/*Contents of CalendarProgran.class */
package hataridonaplo;
//Import packages
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

//http://www.dreamincode.net/forums/topic/25042-creating-a-calendar-viewer-application/

public class MyDiary{
	static JLabel labelofMonth, labelofYear;
	static JButton previousButton, nextButton;
	static JTable tableCalendar;
	static JComboBox comboBoxforChoosingYear;
	static JFrame mainFrame;
	static Container Pane;
	static DefaultTableModel modellofTableCalendar; //Table model
	static JScrollPane stblCalendar; //The scrollpane
	static JPanel pnlCalendar;
	static int realYear, realMonth, realDay, currentYear, currentMonth;

	public static void main (String args[]){
		//Look and feel
		try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
		catch (ClassNotFoundException e) {}
		catch (InstantiationException e) {}
		catch (IllegalAccessException e) {}
		catch (UnsupportedLookAndFeelException e) {}

		//Prepare frame
		mainFrame = new JFrame ("Gestionnaire de clients"); //Create frame
		mainFrame.setSize(330, 375); //Set size to 400x400 pixels
		Pane = mainFrame.getContentPane(); //Get content pane
		Pane.setLayout(null); //Apply null layout
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Close when X is clicked

		//Create controls
		labelofMonth = new JLabel ("January");
		labelofYear = new JLabel ("Change year:");
		comboBoxforChoosingYear = new JComboBox();
		previousButton = new JButton ("<<");
		nextButton = new JButton (">>");
		modellofTableCalendar = new DefaultTableModel(){public boolean isCellEditable(int rowIndex, int mColIndex){return false;}};
		tableCalendar = new JTable(modellofTableCalendar);
		stblCalendar = new JScrollPane(tableCalendar);
		pnlCalendar = new JPanel(null);

		//Set border
		pnlCalendar.setBorder(BorderFactory.createTitledBorder("Calendar"));
		
		//Register action listeners
		previousButton.addActionListener(new btnPrev_Action());
		nextButton.addActionListener(new btnNext_Action());
		comboBoxforChoosingYear.addActionListener(new cmbYear_Action());
		
		//Add controls to pane
		Pane.add(pnlCalendar);
		pnlCalendar.add(labelofMonth);
		pnlCalendar.add(labelofYear);
		pnlCalendar.add(comboBoxforChoosingYear);
		pnlCalendar.add(previousButton);
		pnlCalendar.add(nextButton);
		pnlCalendar.add(stblCalendar);
		
		//Set bounds
		pnlCalendar.setBounds(0, 0, 320, 335);
		labelofMonth.setBounds(160-labelofMonth.getPreferredSize().width/2, 25, 100, 25);
		labelofYear.setBounds(10, 305, 80, 20);
		comboBoxforChoosingYear.setBounds(230, 305, 80, 20);
		previousButton.setBounds(10, 25, 50, 25);
		nextButton.setBounds(260, 25, 50, 25);
		stblCalendar.setBounds(10, 50, 300, 250);
		
		//Make frame visible
		mainFrame.setResizable(false);
		mainFrame.setVisible(true);
		
		//Get real month/year
		GregorianCalendar cal = new GregorianCalendar(); //Create calendar
		realDay = cal.get(GregorianCalendar.DAY_OF_MONTH); //Get day
		realMonth = cal.get(GregorianCalendar.MONTH); //Get month
		realYear = cal.get(GregorianCalendar.YEAR); //Get year
		currentMonth = realMonth; //Match month and year
		currentYear = realYear;
		
		//Add headers
		String[] headers = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}; //All headers
		for (int i=0; i<7; i++){
			modellofTableCalendar.addColumn(headers[i]);
		}
		
		tableCalendar.getParent().setBackground(tableCalendar.getBackground()); //Set background

		//No resize/reorder
		tableCalendar.getTableHeader().setResizingAllowed(false);
		tableCalendar.getTableHeader().setReorderingAllowed(false);

		//Single cell selection
		tableCalendar.setColumnSelectionAllowed(true);
		tableCalendar.setRowSelectionAllowed(true);
		tableCalendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		//Set row/column count
		tableCalendar.setRowHeight(38);
		modellofTableCalendar.setColumnCount(7);
		modellofTableCalendar.setRowCount(6);
		
		//Populate table
		for (int i=realYear-100; i<=realYear+100; i++){
			comboBoxforChoosingYear.addItem(String.valueOf(i));
		}
		
		//Refresh calendar
		refreshCalendar (realMonth, realYear); //Refresh calendar
	}
	
	public static void refreshCalendar(int month, int year){
		//Variables
		String[] months =  {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		int nod, som; //Number Of Days, Start Of Month
			
		//Allow/disallow buttons
		previousButton.setEnabled(true);
		nextButton.setEnabled(true);
		if (month == 0 && year <= realYear-10){previousButton.setEnabled(false);} //Too early
		if (month == 11 && year >= realYear+100){nextButton.setEnabled(false);} //Too late
		labelofMonth.setText(months[month]); //Refresh the month label (at the top)
		labelofMonth.setBounds(160-labelofMonth.getPreferredSize().width/2, 25, 180, 25); //Re-align label with calendar
		comboBoxforChoosingYear.setSelectedItem(String.valueOf(year)); //Select the correct year in the combo box
		
		//Clear table
		for (int i=0; i<6; i++){
			for (int j=0; j<7; j++){
				modellofTableCalendar.setValueAt(null, i, j);
			}
		}
		
		//Get first day of month and number of days
		GregorianCalendar cal = new GregorianCalendar(year, month, 1);
		nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		som = cal.get(GregorianCalendar.DAY_OF_WEEK);
		
		//Draw calendar
		for (int i=1; i<=nod; i++){
			int row = new Integer((i+som-2)/7);
			int column  =  (i+som-2)%7;
			modellofTableCalendar.setValueAt(i, row, column);
		}

		//Apply renderers
		tableCalendar.setDefaultRenderer(tableCalendar.getColumnClass(0), new tblCalendarRenderer());
	}

	static class tblCalendarRenderer extends DefaultTableCellRenderer{
		public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column){
			super.getTableCellRendererComponent(table, value, selected, focused, row, column);
			if (column == 0 || column == 6){ //Week-end
				setBackground(new Color(255, 220, 220));
			}
			else{ //Week
				setBackground(new Color(255, 255, 255));
			}
			if (value != null){
				if (Integer.parseInt(value.toString()) == realDay && currentMonth == realMonth && currentYear == realYear){ //Today
					setBackground(new Color(220, 220, 255));
				}
			}
			setBorder(null);
			setForeground(Color.black);
			return this;  
		}
	}

	static class btnPrev_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){
			if (currentMonth == 0){ //Back one year
				currentMonth = 11;
				currentYear -= 1;
			}
			else{ //Back one month
				currentMonth -= 1;
			}
			refreshCalendar(currentMonth, currentYear);
		}
	}
	static class btnNext_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){
			if (currentMonth == 11){ //Foward one year
				currentMonth = 0;
				currentYear += 1;
			}
			else{ //Foward one month
				currentMonth += 1;
			}
			refreshCalendar(currentMonth, currentYear);
		}
	}
	static class cmbYear_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){
			if (comboBoxforChoosingYear.getSelectedItem() != null){
				String b = comboBoxforChoosingYear.getSelectedItem().toString();
				currentYear = Integer.parseInt(b);
				refreshCalendar(currentMonth, currentYear);
			}
		}
	}
}