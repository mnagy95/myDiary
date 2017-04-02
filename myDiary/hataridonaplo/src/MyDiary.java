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
	static JComboBox<String> comboBoxforChoosingYear;
	static JFrame mainFrame;
	static Container Pane;
	static DefaultTableModel modellofTableCalendar; //Table model
	static JScrollPane scrollTableCalendar; //The scrollpane
	static JPanel panelCalendar;
	static int realYear, realMonth, realDay, currentYear, currentMonth;
	
	static JLabel newEvent, yearOfEvent, monthOfEvent, dayOfEvent;
	static Choice choiceOfYear, choiceOfMonth, choiceOfDay;

	public static void main (String args[]){
		//Look and feel
		try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
		catch (ClassNotFoundException e) {}
		catch (InstantiationException e) {}
		catch (IllegalAccessException e) {}
		catch (UnsupportedLookAndFeelException e) {}

		//Prepare frame
		mainFrame = new JFrame ("My Diary"); //Create frame
		mainFrame.setSize(600, 400); //Set size 
		Pane = mainFrame.getContentPane(); //Get content pane
		Pane.setLayout(null); //Apply null layout
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Close when X is clicked

		//Create controls
		labelofMonth = new JLabel ("January");
		labelofYear = new JLabel ("Change year:");
		comboBoxforChoosingYear = new JComboBox<>();
		previousButton = new JButton ("<<");
		nextButton = new JButton (">>");
		modellofTableCalendar = new DefaultTableModel(){public boolean isCellEditable(int rowIndex, int mColIndex){return false;}};
		tableCalendar = new JTable(modellofTableCalendar);
		scrollTableCalendar = new JScrollPane(tableCalendar);
		panelCalendar = new JPanel(null);

		//Set border
		panelCalendar.setBorder(BorderFactory.createTitledBorder("Calendar"));
		
		//Register action listeners
		previousButton.addActionListener(new PreviousButtonAction());
		nextButton.addActionListener(new NextButtonAction());
		comboBoxforChoosingYear.addActionListener(new ComboBoxYearAction());
		
		//Add controls to pane
		Pane.add(panelCalendar);
		panelCalendar.add(labelofMonth);
		panelCalendar.add(labelofYear);
		panelCalendar.add(comboBoxforChoosingYear);
		panelCalendar.add(previousButton);
		panelCalendar.add(nextButton);
		panelCalendar.add(scrollTableCalendar);
		
		//Set bounds
		panelCalendar.setBounds(0, 0, 500, 335);
		labelofMonth.setBounds(160-labelofMonth.getPreferredSize().width/2, 20, 100, 25);
		labelofYear.setBounds(10, 305, 80, 20);
		comboBoxforChoosingYear.setBounds(230, 305, 80, 20);
		previousButton.setBounds(10, 25, 50, 25);
		nextButton.setBounds(260, 25, 50, 25);
		scrollTableCalendar.setBounds(10, 50, 300, 250);
		
		//Make frame visible
		mainFrame.setResizable(true);
		mainFrame.setVisible(true);
		
		//Get real month/year
		GregorianCalendar calendar = new GregorianCalendar(); //Create calendar
		realDay = calendar.get(GregorianCalendar.DAY_OF_MONTH); //Get day
		realMonth = calendar.get(GregorianCalendar.MONTH); //Get month
		realYear = calendar.get(GregorianCalendar.YEAR); //Get year
		currentMonth = realMonth; //Match month and year
		currentYear = realYear;
		
		//Add headers
		String[] headers = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun",}; //All headers
		for (int i=0; i<7; i++){
			modellofTableCalendar.addColumn(headers[i]);
		}
		
		tableCalendar.getParent().setBackground(tableCalendar.getBackground()); //Set background

		//No resize/reorder
		tableCalendar.getTableHeader().setResizingAllowed(true);
		tableCalendar.getTableHeader().setReorderingAllowed(true);

		//Single cell selection
		tableCalendar.setColumnSelectionAllowed(true);
		tableCalendar.setRowSelectionAllowed(true);
		tableCalendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		//Set row/column count
		tableCalendar.setRowHeight(38);
		modellofTableCalendar.setColumnCount(7);
		modellofTableCalendar.setRowCount(6);
		
		//Populate table
		for (int i=realYear-20; i<=realYear+80; i++){
			comboBoxforChoosingYear.addItem(String.valueOf(i));
		}
		
		//Refresh calendar
		refreshCalendar (realMonth, realYear); //Refresh calendar
		
		
		
		
		//EventsHandler event = new EventsHandler();
		newEvent = new JLabel ("Add Information");
		panelCalendar.add(newEvent);
		newEvent.setBounds(360, 20, 200, 20);
		
		yearOfEvent = new JLabel ("Year:");
		panelCalendar.add(yearOfEvent);
		yearOfEvent.setBounds(320, 100, 60, 20);
		
		monthOfEvent = new JLabel ("Month:");
		panelCalendar.add(monthOfEvent);
		monthOfEvent.setBounds(320, 180, 200, 20);
		
		dayOfEvent = new JLabel ("Day:");
		panelCalendar.add(dayOfEvent);
		dayOfEvent.setBounds(320, 260, 200, 20);
		
		choiceOfYear = new Choice();
		for (int i=realYear-20; i<=realYear+80; i++){
			choiceOfYear.addItem(String.valueOf(i));
		}
		panelCalendar.add(choiceOfYear);
		choiceOfYear.setBounds(400, 100, 80, 20);		
	}
	
	public static void refreshCalendar(int month, int year){
		String[] months =  {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		int numberOfDays, startOfMonth; 
			
		previousButton.setEnabled(true);
		nextButton.setEnabled(true);
		if (month == 0 && year <= realYear-10){previousButton.setEnabled(false);}			 	//Too early
		if (month == 11 && year >= realYear+100){nextButton.setEnabled(false);} 				//Too late
		labelofMonth.setText(months[month]); 													//Refresh the month label (at the top)
		labelofMonth.setBounds(160-labelofMonth.getPreferredSize().width/2, 25, 180, 25); 		//Re-align label with calendar
		comboBoxforChoosingYear.setSelectedItem(String.valueOf(year)); 							//Select the correct year in the combo box
		
		//Clear table
		for (int i=0; i<6; i++){
			for (int j=0; j<7; j++){
				modellofTableCalendar.setValueAt(null, i, j);
			}
		}
		
		//Get first day of month and number of days
		GregorianCalendar calendar = new GregorianCalendar(year, month, 0);
		numberOfDays = calendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		startOfMonth = calendar.get(GregorianCalendar.DAY_OF_WEEK);
		
		//Draw calendar
		for (int i=1; i<=numberOfDays; i++){
			int row = new Integer((i+startOfMonth-2)/7);
			int column  =  (i+startOfMonth-2)%7;
			modellofTableCalendar.setValueAt(i, row, column);
		}

		//Apply renderer
		tableCalendar.setDefaultRenderer(tableCalendar.getColumnClass(0), new tableCalendarRenderer());
	}

	static class tableCalendarRenderer extends DefaultTableCellRenderer{
		public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column){
			super.getTableCellRendererComponent(table, value, selected, focused, row, column);
			if (column == 5 || column == 6){ 																				//Week-end
				setBackground(new Color(255, 220, 220));
			}
			else{ 																											//Week
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

	static class PreviousButtonAction implements ActionListener{
		public void actionPerformed (ActionEvent e){
			if (currentMonth == 0){ 					//Back one year
				currentMonth = 11;
				currentYear -= 1;
			}
			else{ 										//Back one month
				currentMonth -= 1;
			}
			refreshCalendar(currentMonth, currentYear);
		}
	}
	
	static class NextButtonAction implements ActionListener{
		public void actionPerformed (ActionEvent e){
			if (currentMonth == 11){ 					//Forward one year
				currentMonth = 0;
				currentYear += 1;
			}
			else{ 										//Forward one month
				currentMonth += 1;
			}
			refreshCalendar(currentMonth, currentYear);
		}
	}
	
	static class ComboBoxYearAction implements ActionListener{
		public void actionPerformed (ActionEvent e){
			if (comboBoxforChoosingYear.getSelectedItem() != null){
				String b = comboBoxforChoosingYear.getSelectedItem().toString();
				currentYear = Integer.parseInt(b);
				refreshCalendar(currentMonth, currentYear);
			}
		}
	}
}