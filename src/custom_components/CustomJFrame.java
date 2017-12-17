package custom_components;

import java.awt.*;

import javax.swing.JFrame;

import application_frames.Settings;

public class CustomJFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int[] windowSize;
	
	

	public CustomJFrame() throws HeadlessException {
		
		// Find task bar height
		Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
		int taskBarSize = scnMax.bottom;
		
		// Set frame size and remove the task bar size from it
		windowSize = Settings.getDefaultWindowSize();

		setBounds(0, 0, windowSize[0], windowSize[1] - taskBarSize);
		setResizable(false);
	}

	public CustomJFrame(GraphicsConfiguration arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public CustomJFrame(String arg0) throws HeadlessException {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public CustomJFrame(String arg0, GraphicsConfiguration arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

}