package core_components;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.metal.MetalToggleButtonUI;

import application_frames.MainFrame;
import application_frames.SettingsFrame;
import toolset.Tools;

/**
 * Class for the creation of the DrawIconButton
 *
 */
public class DrawIconButton extends JToggleButton {

	private static final long serialVersionUID = 1L;
	
	private String featureType ;
	private String geometryFamily;
	
	/**
	 * Creates the DrawIconButton
	 * @param name the name of the DrawIconButton to set
	 * @param geometryFamily the geometryFamily of the DrawIconButton to set
	 * @param iconPath the iconPath of the DrawIconButton to set
	 * @param x the x position of the DrawIconButton to set
	 * @param y the y position of the DrawIconButton to set
	 */
	
	public DrawIconButton(String name, String geometryFamily, String iconPath, int x, int y) {
		super();
		this.featureType = name;
		this.geometryFamily = geometryFamily;
		setIcon(Tools.getIconImage(iconPath, x, y));
		setBorderPainted(false);
		setFocusPainted(false);
		setActionCommand(name);
		setBackground(SettingsFrame.DEFAULT_STATE_COLOR);
		
		addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
               // ButtonModel model = (ButtonModel) e.getSource();
                if (getModel().isPressed()) {
                    setBackground(Color.RED);
                }
            }
        });
		
		addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				
				if(isEnabled()) {
					if(e.getStateChange() == ItemEvent.SELECTED) {
						
						MainFrame.log( featureType +  " selected. (" + getActionCommand() + " family)" );
						setUI(new MetalToggleButtonUI() {
						    @Override
						    protected Color getSelectColor() {
						        return SettingsFrame.HIGHLIGHTED_STATE_COLOR;
						    }
						});
					}
					
					else  {
						MainFrame.log( featureType +  " deselected. (" + getActionCommand() + " family)");
						setBackground(SettingsFrame.DEFAULT_STATE_COLOR);
					}
				} else
					setBackground(SettingsFrame.DEFAULT_STATE_COLOR);
				
			}
		});
		
		MainFrame.drawButtonGroup.add(this);
	}

	/**
	 * Returns the featureType
	 * @return the featureType
	 */
	public String getFeatureType() {
		return featureType;
	}

	/**
	 * Returns the geometryFamily
	 * @return the geometryFamily
	 */
	public String getGeometryFamily() {
		return geometryFamily;
	}
}
