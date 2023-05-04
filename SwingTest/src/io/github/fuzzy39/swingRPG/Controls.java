package io.github.fuzzy39.swingRPG;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import io.github.fuzzy39.swingRPG.world.Player;

/**
 * Handles the keyboard controls. Not currently usable in user code.
 * @author fuzzy39
 *
 */
public class Controls 
{

	
	
	private boolean[] keysDown;
	
	
	public Controls()
	{
		
		
		keysDown = new boolean[KeyEvent.KEY_FIRST];
		for(boolean b : keysDown)
		{
			b=false;
		}
		
		
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() 
		{

            @Override
            public boolean dispatchKeyEvent(KeyEvent ke) 
            {
               
                switch (ke.getID()) {
                case KeyEvent.KEY_PRESSED:
                    keysDown[ke.getKeyCode()] = true;
                    break;

                case KeyEvent.KEY_RELEASED:
                	 keysDown[ke.getKeyCode()] = false;
                    break;
                }
                return false;
            }
            
        });
		
	}
	
	protected void update(Player p)	
	{
		int vel = Player.ACCELERATION;
		
		// player movement is hardcoded at the moment, sorry 
		if(keysDown[KeyEvent.VK_W] || keysDown[KeyEvent.VK_UP]){p.addVelocity(0, -vel);}
		if(keysDown[KeyEvent.VK_S] || keysDown[KeyEvent.VK_DOWN]){p.addVelocity(0, vel);}
		
		if(keysDown[KeyEvent.VK_A] || keysDown[KeyEvent.VK_LEFT]) {p.addVelocity(-vel, 0); }
		if(keysDown[KeyEvent.VK_D] || keysDown[KeyEvent.VK_RIGHT]) {p.addVelocity(vel, 0); }
		
		
	}
}