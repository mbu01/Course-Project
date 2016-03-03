/*
 * The implementation of the 'up/down/left/right' buttons that can control the direction of planes
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class directionPanel extends JPanel implements ActionListener 
{
	protected JButton up;
	protected JButton down;
	protected JButton left;
	protected JButton right;	
	private String currentButton;
	public Map canvas;
	public Main parent;
	int i;

	public String getCurrentButton() 
	{
		return currentButton;
	}

	public void setCurrentButton(String currentButton) 
	{
		this.currentButton = currentButton;
	}

	public directionPanel(Map canvas,Main parent) 
	{
		// Put a border on our JPanel
		// Set default color which user can modify later
		setLayout(new BorderLayout());
		up = new JButton ("up");
		up.addActionListener (this);
		down = new JButton ("down");
		down.addActionListener (this);
		left = new JButton ("left");
		left.addActionListener (this);
		right = new JButton ("right");
		right.addActionListener (this);
		add(up, BorderLayout.NORTH);
		add(down,BorderLayout.SOUTH);
		add(left, BorderLayout.WEST);
		add(right,BorderLayout.EAST);
		this.canvas = canvas;
		this.parent = parent;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		canvas.getOne().setCurrentX1(canvas.getOne().getX1());
		canvas.getOne().setCurrentY1(canvas.getOne().getY1());
		currentButton = (e.getSource() == up)? "up" : (e.getSource() == down) ? "down" : (e.getSource() == left) ? "left" :"right";
		parent.setFrame(0);		
	}

}
