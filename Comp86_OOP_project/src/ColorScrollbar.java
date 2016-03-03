/*
 * The implementation of Scrollbar that controls the color of the plane
 * It's been deleted in the last assignment
 */

import java.awt.Dimension;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import javax.swing.JScrollBar;

public class ColorScrollbar extends JScrollBar implements AdjustmentListener
{
	private String whichColor;
	private Map canvas;
	
	// Create the ColorScrollbar
	public ColorScrollbar (String whichColor, Map canvas) 
	{
		super(JScrollBar.HORIZONTAL, 100, 10, 0, 255);
		this.whichColor = whichColor;
		this.canvas = canvas;
		addAdjustmentListener (this);
	}
	
	public Dimension getPreferredSize() 
	{
		return new Dimension(125,20);
	}
	
	public void adjustmentValueChanged(AdjustmentEvent arg0) 
	{
		canvas.setColor (whichColor, getValue());	
	}	
}
