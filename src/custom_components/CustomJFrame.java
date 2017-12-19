package custom_components;

import java.awt.*;

import javax.swing.JFrame;

import application_frames.Settings;
import toolset.Tools;

public class CustomJFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	

	public CustomJFrame() throws HeadlessException {
		
		setIconImage(Tools.getIconImage("/images/logo.png").getImage());
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