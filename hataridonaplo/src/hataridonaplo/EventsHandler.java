package hataridonaplo;

import java.awt.Container;
import java.awt.Graphics;

import javax.swing.JFrame;

import java.awt.*;
import javax.swing.*;

//http://stackoverflow.com/questions/15986677/drawing-an-object-using-getgraphics-without-extending-jframe

public class EventsHandler extends JComponent{

	private JFrame mainFrame;
	private Container Pane;
	
	private int heightOfTheWindow = 650;

	private static final long serialVersionUID = 1L;

	EventsHandler() {
		// Prepare frame
		mainFrame = new JFrame("My Events"); // Create frame
		mainFrame.setSize(400, heightOfTheWindow); // Set size

		//mainFrame.setResizable(false);
		mainFrame.setVisible(true);
		
		JPanel panel = new JPanel(){
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                
                g.setColor(Color.RED);
                paintLines(g);
                g.drawLine(50, 0, 50, 650);
            }

			public void paintLines(Graphics g){
				int i = 0;
            	for(int linePosition = 10; linePosition <= heightOfTheWindow - 40 ; linePosition += 25){
            		g.drawLine(0, linePosition, 400, linePosition);
            		g.drawString(String.valueOf(i) + ". ora", 10, linePosition);
            		i += 1;
            	}
            }
        };
        
        mainFrame.add(panel);
        mainFrame.validate(); // because you added panel after setVisible was called
        mainFrame.repaint(); // because you added panel after setVisible was called

        setBackGroundColor(new Color(100,100,100), panel);
	}
	
	public void setBackGroundColor(Color color, JPanel panel){
		Pane = mainFrame.getContentPane();
        panel.setBackground(color);
        Pane.add(panel);
	}
}

//g.setColor(Color.BLUE);
//g.fillRect(200, 200, 100, 100);
