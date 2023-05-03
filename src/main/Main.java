package main;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Main 
{

	
		private static GamePanel game;
		private static JLabel text;
		
	   
	    public static void main(String[] args) 
	    {
	      setupUI();
	      
	    }
	    
	    /**
	     * Create the GUI and show it.  For thread safety,
	     * this method should be invoked from the
	     * event-dispatching thread.
	     */
	    private static void setupUI() 
	    {
	    	Textures.init();
	    	
	        //Create and set up the window.
	        JFrame frame = new JFrame("Swing can do games?");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	        JPanel main = new JPanel();
	        main.setLayout(new BoxLayout(main, BoxLayout.PAGE_AXIS));
	        //((BoxLayout)main.getLayout()).get
	        frame.add(main);
	        
	        main.add(Box.createVerticalStrut(25));
	        //Add the ubiquitous "Hello World" label.
	        game = new GamePanel();
	        game.setMaximumSize(new Dimension(800,400));
	        main.add(game);
	        
	        main.add(Box.createVerticalStrut(25));
	        
	        JPanel HUD = new JPanel();
	        HUD.setBorder(BorderFactory.createEtchedBorder());
	        HUD.setLayout(new FlowLayout(FlowLayout.LEADING, 20,0));
	        HUD.setMaximumSize(new Dimension(800, 200));
	        main.add(HUD);
	        
	        text = new JLabel();
	        text.setMaximumSize(new Dimension(600,200));
	        text.setPreferredSize(new Dimension(600,200)); // 800-60 = 740 -600 = 140
	        HUD.add(text);
	        resetText();
	        
	        JPanel buttons = new JPanel();
	        buttons.setMaximumSize(new Dimension(140,200));
	        buttons.setLayout(new BoxLayout(buttons, BoxLayout.PAGE_AXIS));
	        HUD.add(buttons);
	        
	        
	       
	        
	        for(int i=0; i<3; i++)
	        {
	            JButton button = new JButton("Action "+(i+1));
	            button.setEnabled(false);
		        button.setPreferredSize(new Dimension(140,50));
		        button.setMaximumSize(new Dimension(140,50));
		        buttons.add(button);
	        }
	        
	        
	
	        
	        //Display the window.
	        frame.setSize(850, 700);
	        frame.setVisible(true);
	        frame.setResizable(false);
	        
	        
	        Timer t = new Timer(1000/60, Main::update );
	        t.start();
	        
	        
	        
	    }
	    
	    private static void update(ActionEvent e)
	    {
	    	game.update();
	    }
	    
	    public static void setText(String s)
	    {
	    	text.setText(s);
	    }
	    
	    public static void resetText()
	    {
	    	text.setText("Welcome to String City Casino (do not steal)!");
	    }

	}
