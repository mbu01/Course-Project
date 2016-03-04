/*
 * The implementation of Radar view
 * Set the views for radar view, which is similar to the map class
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

public class MapZoom extends JComponent implements MouseListener
{
	private ArrayList<Plane> planes;
	private Plane plane;
	private Main parent;
	private Point init_Plane1;
	private Point init_Plane2;
	private Point init_Plane3;
	private int red = 255;
	private int green = 0;
	private int blue = 0;


	// Add planes to arrayList
	public MapZoom(Main parent)
	{
		addMouseListener (this);
		this.parent = parent;
		this.setSize(200,200);
	}

	public Point getInit_Plane1()
	{
		return init_Plane1;
	}
	
	public void setInit_Plane1(Point init_Plane1)
	{
		this.init_Plane1 = init_Plane1;
	}
	
	public Point getInit_Plane2()
	{
		return init_Plane2;
	}
	
	public void setInit_Plane2(Point init_Plane2) 
	{
		this.init_Plane2 = init_Plane2;
	}
	
	public Point getInit_Plane3()
	{
		return init_Plane3;
	}
	public void setInit_Plane3(Point init_Plane3)
	{
		this.init_Plane3 = init_Plane3;
	}

	// Paint the plane by oval, the user's plane is painted with red color, which is different from the original view
@Override
	public void paintComponent (Graphics g)
	{
		Dimension size = getSize();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, size.width, size.height);
		g.setColor(Color.GREEN);
		g.drawOval(0, 0, 650, 650);
		g.drawOval(50, 50, 550, 550);
		g.drawOval(100, 100, 450, 450);
		g.drawOval(150, 150, 350, 350);
		g.drawOval(200, 200, 250, 250);
		g.drawOval(250, 250, 150, 150);
		g.drawOval(300, 300, 50, 50);
		double x1 = (double)(parent.getZoomFrame()/10);
		g.drawLine(325, 325, (int)(325+325*Math.sin(x1)),(int)(325-325*Math.cos(x1)));

		// Set the location for planes on canvasZoom
		if(parent.zoom)
		{	
			// If there's a collision in the don't enter area, "boom" sound pop up
			if(parent.canvas.checkCollisionsGDArea() == true )
			{
				parent.canvas.p1Disappear = true;
				try 
				{
					AudioInputStream audioIn = AudioSystem.getAudioInputStream (new File("boom.wav"));
					Clip clip = AudioSystem.getClip();
					clip.open (audioIn);
					clip.start();
				} catch (Exception exception) { exception.printStackTrace(); }
				parent.timer.stop();
				JOptionPane.showMessageDialog(parent,"You fail!");
			}
			
			// If there's a collision in the mountain area, "boom" sound pop up
			else if(parent.canvas.checkCollisionsMTArea() == true )
			{
				parent.canvas.p1Disappear = true;
				try 
				{
					AudioInputStream audioIn = AudioSystem.getAudioInputStream (new File("boom.wav"));
					Clip clip = AudioSystem.getClip();
					clip.open (audioIn);
					clip.start();
					} catch (Exception exception) { exception.printStackTrace(); }
				parent.timer.stop();
				JOptionPane.showMessageDialog(parent,"You fail!");
			}
			
			// If collision between plane1, plane2, delete plane2
			if(parent.canvas.checkCollisions12() == true && parent.canvas.checkCollisions13() == false && parent.canvas.checkCollisions23() == false) 
			{
				parent.canvas.p2Disappear = true;
				try {
					AudioInputStream audioIn = AudioSystem.getAudioInputStream (new File("win.wav"));
					Clip clip = AudioSystem.getClip();
					clip.open (audioIn);
					clip.start();
				} catch (Exception exception) { exception.printStackTrace(); }
				if (parent.canvas.p1Disappear == false) parent.canvas.getOne().draw(g);
				if (parent.canvas.p3Disappear == false) {
					parent.canvas.getThree().draw(g);
					parent.state.getFlightNumber().setText("1");
					parent.state.getScoreNumber().setText("1");
				}
				else if(parent.canvas.p3Disappear == true)
				{
					parent.timer.stop();
					parent.state.getFlightNumber().setText("0");
					parent.state.getScoreNumber().setText("2");
					JOptionPane.showMessageDialog(parent,"You win!");	
				}	
			}
			
			// If collision between plane1, plane3, delete plane3
			else if(parent.canvas.checkCollisions12() == false && parent.canvas.checkCollisions13() == true && parent.canvas.checkCollisions23() == false)
			{
				parent.canvas.p3Disappear = true;
				try {
					AudioInputStream audioIn = AudioSystem.getAudioInputStream (new File("win.wav"));
					Clip clip = AudioSystem.getClip();
					clip.open (audioIn);
					clip.start();
					} catch (Exception exception) { exception.printStackTrace(); }
				if (parent.canvas.p1Disappear == false) parent.canvas.getOne().draw(g);
				if (parent.canvas.p2Disappear == false) 
				{
					parent.canvas.getTwo().draw(g);
					parent.state.getFlightNumber().setText("1");
					parent.state.getScoreNumber().setText("1");
				}
				else if(parent.canvas.p2Disappear == true)
				{
					parent.timer.stop();
					parent.state.getFlightNumber().setText("0");
					parent.state.getScoreNumber().setText("2");
					JOptionPane.showMessageDialog(parent,"You win!"); 
				}
			}
		
			// If collision between plane2, plane3, delete plane2
			else if(parent.canvas.checkCollisions12() == false && parent.canvas.checkCollisions13() == false && parent.canvas.checkCollisions23() == true) 
			{
				if (parent.canvas.p2Disappear == false) parent.canvas.getTwo().draw(g);
				if (parent.canvas.p1Disappear == false) parent.canvas.getOne().draw(g);
				if (parent.canvas.p3Disappear == false) parent.canvas.getThree().draw(g);
			}
			else{
				if (parent.canvas.p1Disappear == false) parent.canvas.getOne().draw(g);
				if (parent.canvas.p2Disappear == false) parent.canvas.getTwo().draw(g);
				if (parent.canvas.p3Disappear == false) parent.canvas.getThree().draw(g);
				}
		}
	}
	
	// Called from the ColorScrollbar's
	public void setColor (String whichColor, int newValue)
	{
		if ( whichColor.equals("Red")) red = newValue;
		else if ( whichColor.equals("Green")) green = newValue;
		else if ( whichColor.equals("Blue")) blue = newValue;
		repaint();
	}

	/* MouseListener callbacks */
	public void mousePressed (MouseEvent event) {}
	public void mouseClicked (MouseEvent event) {}
	public void mouseReleased (MouseEvent event) {}
	public void mouseEntered (MouseEvent event) {}
	public void mouseExited (MouseEvent event) {}

}



