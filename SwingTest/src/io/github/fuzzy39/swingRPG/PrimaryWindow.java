package io.github.fuzzy39.swingRPG;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * The primary window of the game. Right now this thing is super restrictive, but I'll try to fix that in the future. maybe.
 * @author Fuzzy39
 *
 */
public class PrimaryWindow 
{
	

	
	private JLabel text;
	private JButton interactButton;
	private Runnable onInteract;
	private JButton inventoryButton;
	private JButton unusedButton;
	private JFrame frame;
	
	private String status;
	
	
	protected PrimaryWindow(String name, GamePanel game)
	{
		status = "[ status text not set ]";
		setupUI(name, game);
		
		
		 
   
        
	}
	
	private void setupUI(String name, GamePanel game) 
    {
    	
    	
        JPanel HUD = setupStructure(name, game);
        
        setupHUD(HUD);
        
      
        

        
        //Display the window.
        frame.setSize(850, 700);
        frame.setVisible(true);
        frame.setResizable(false);
        
       
        
        
    }
	private JPanel setupStructure(String name, GamePanel game)
	{
		 //Create and set up the window.
        frame = new JFrame(name);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.PAGE_AXIS));
        //((BoxLayout)main.getLayout()).get
        frame.add(main);
        
        main.add(Box.createVerticalStrut(25));
        //Add the ubiquitous "Hello World" label.
        game.setMaximumSize(new Dimension(800,400));
        main.add(game);
        
        main.add(Box.createVerticalStrut(25));
        
        JPanel HUD = new JPanel();
        HUD.setBorder(BorderFactory.createEtchedBorder());
        HUD.setLayout(new FlowLayout(FlowLayout.LEADING, 20,0));
        HUD.setMaximumSize(new Dimension(800, 200));
        main.add(HUD);
        return HUD;
	}
	private void setupHUD(JPanel HUD)
	{
		text = new JLabel();
        text.setMaximumSize(new Dimension(600,200));
        text.setPreferredSize(new Dimension(600,200)); // 800-60 = 740 -600 = 140
        HUD.add(text);
        resetText();
        
        JPanel buttons = new JPanel();
        buttons.setMaximumSize(new Dimension(140,200));
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.PAGE_AXIS));
        HUD.add(buttons);
        
        
        interactButton = new JButton("interact");
        interactButton.addActionListener(new ActionListener() { 
        	  public void actionPerformed(ActionEvent e) 
        	  { 
        		    if(onInteract!=null)
        		    {
        		    	onInteract.run();
        		    }
        	  } 
        });
        
        
        inventoryButton = new JButton("coming soon");
        
        unusedButton = new JButton("coming soon");
        
        JButton[] button = {interactButton, inventoryButton, unusedButton};
        for(int i=0; i<3; i++)
        {
            
            button[i].setEnabled(false);
	        button[i].setPreferredSize(new Dimension(140,50));
	        button[i].setMaximumSize(new Dimension(140,50));
	        buttons.add(button[i]);
        }
	        
	}
	
	
	
	/**
	 * Set the default text placed below the game screen.
	 * @param s
	 */
	public void setStatusText(String s)
	{
		status = s;
		resetText();
	}
	
	/**
	 * Set the temporary text placed below the game screen. This text is often reverted back to the status text.
	 * @param s
	 */
	public void setText(String s)
    {
    	text.setText(s);
    }
    
	/**
	 * Reset the text below the game screen to the current status text.
	 */
    public void resetText()
    {
    	text.setText(status);
    }

    /**
     * set what happens when the 'interaction' button is pressed.
     * @param onClick
     * @param interactionName the text on the button while it remains enabled.
     */
    public void setOnInteract(Runnable onClick, String interactionName)
    {
    	onInteract = onClick;
    	interactButton.setEnabled(true);
    	interactButton.setText(interactionName);
    }
    
    /**
     * disables the interaction button. The button's text is reset.
     */
    public void disableInteraction()
    {
    	interactButton.setEnabled(false);
    	interactButton.setText("Interact");
    	onInteract = null;
    }

}
