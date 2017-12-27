/**
 * 
 */
package custom_components;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

import application_frames.Settings;


/**
 * Class for the creation of a CustomJButton
 * @author OlumideEnoch
 *
 */
public class CustomJToggle extends JButton {

	private static final long serialVersionUID = 1L;
	private boolean state = true;


	/**
	 * Creates the Custom JButton
	 */
	public CustomJToggle() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Creates the Custom JButton
	 */
	public CustomJToggle(boolean state) {
		// TODO Auto-generated constructor stub
		this.state = state;
		setForeground(Color.WHITE);
		setOpaque(true);
		setFocusPainted(false);
		setBorderPainted(false);
		setBorder(null);
		setState();
		
		
		addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				setState();
				
			}
		});
	}

	protected void setState() {
		if(state) {
			state = false;
			setText("OFF");
			setBackground(Color.BLACK);
		} else {
			state = true;
			setText("ON");
			setBackground(Settings.HIGHLIGHTED_STATE_COLOR);
		}
	}
	
	/**
	 * @return the state
	 */
	public boolean getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(boolean state) {
		this.state = state;
	}

	/**
	 * Creates the Custom JButton
	 * @param arg0 the Icon arg0 to set
	 */
	public CustomJToggle(Icon arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Creates the Custom JButton
	 * @param arg0 the String arg0 to set
	 */
	public CustomJToggle(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Creates the Custom JButton
	 * @param arg0 the Action arg0 to set
	 */
	public CustomJToggle(Action arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Creates the Custom JButton
	 * @param arg0 the String arg0 to set
	 * @param arg1 the Icon arg1 to set
	 */
	public CustomJToggle(String arg0, Icon arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

}
