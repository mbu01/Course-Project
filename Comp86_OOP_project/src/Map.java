/*
 * This class is used to build the canvas
 * Set the basic views of the canvas
 * Set the collision rules for planes and initial positions for planes
 */

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.sound.sampled.*;
import java.io.*;

public class Map extends JComponent implements MouseListener
{
	private ArrayList<Plane> planes;
	private Plane plane;
	// Set the original color of the plane
	private int red = 255;
	private int green = 0;
	private int blue = 0;
	private Plane1 one;
	private Plane2 two;
	private Plane3 three;
	public boolean isAll = true;
	private Main parent;
	public boolean p1Pressed;
	public boolean p2Pressed;
	public boolean p3Pressed;
	public boolean p1Disappear;
	public boolean p2Disappear;
	public boolean p3Disappear;
	
	// Create and add planes to arrayList
	public Map(Main parent)
	{
		addMouseListener (this);
		this.parent = parent;
		one = new Plane1(50,100,"BLACK", parent); 
		two = new Plane2(50,350,"BLACK", parent);
		three = new Plane3(500,50,"BLACK", parent);
		planes = new ArrayList<Plane>();
		planes.add(one);
		planes.add(two);
		planes.add(three);	
		this.setSize(200,200);
	}
	
	// Paint the plane
@Override
	public void paintComponent (Graphics g)
	{
		Dimension size = getSize();
		g.setColor(Color.YELLOW);
		g.fillRect(0, 0, size.width, size.height);
		if(plane != null && isAll == false)
		{
			g.setColor(new Color (red, green, blue));
			plane.draw(g);
		}
		else if (isAll == true)
		{
			g.setColor(new Color (red, green, blue));
			// Add stop zone and mountain
			g.drawRect(275, 80, 100, 100);
			g.setColor(Color.BLACK);
			g.fillRect(275, 80, 100, 100);
			g.setColor(new Color (red, green, blue));
			g.drawString("DON'T ENTER",285, 130);
			// Add mountain
			g.drawRect(280, 320, 100, 50);
			g.setColor(Color.BLACK);
			g.fillRect(280, 320, 100, 50);
			g.setColor(new Color (red, green, blue));
			g.drawString("MOUNTAIN",300, 345);
			// If collision between plane1, plane2, delete plane2
			if(checkCollisionsGDArea() == true )
			{
				p1Disappear = true;
				try {
					AudioInputStream audioIn = AudioSystem.getAudioInputStream (new File("boom.wav"));
					Clip clip = AudioSystem.getClip();
					clip.open (audioIn);
					clip.start();
			    	} catch (Exception exception) { exception.printStackTrace(); }
				parent.timer.stop();
				JOptionPane.showMessageDialog(parent,"You fail!");
			}
			else if(checkCollisionsMTArea() == true )
			{
				p1Disappear = true;
				try {
					AudioInputStream audioIn = AudioSystem.getAudioInputStream (new File("boom.wav"));
					Clip clip = AudioSystem.getClip();
					clip.open (audioIn);
					clip.start();
			    	} catch (Exception exception) { exception.printStackTrace(); }
				parent.timer.stop();
				JOptionPane.showMessageDialog(parent,"You fail!");
			}
			if(checkCollisions12() == true && checkCollisions13() == false && checkCollisions23() == false)
			{
				p2Disappear = true;
				try
				{
					AudioInputStream audioIn = AudioSystem.getAudioInputStream (new File("win.wav"));
					Clip clip = AudioSystem.getClip();
					clip.open (audioIn);
					clip.start();
			    	} catch (Exception exception) { exception.printStackTrace(); }
				if (p1Disappear == false) one.draw(g);
				if (p3Disappear == false) 
				{
					three.draw(g);
					parent.state.getFlightNumber().setText("1");
					parent.state.getScoreNumber().setText("1");
				}
				else if(p3Disappear == true)
				{
					parent.timer.stop();
					parent.state.getFlightNumber().setText("0");
					parent.state.getScoreNumber().setText("2");
					JOptionPane.showMessageDialog(parent,"You win!");
				}
			}
			// If collision between plane1, plane3, delete plane3
			else if(checkCollisions12() == false && checkCollisions13() == true && checkCollisions23() == false) 
			{
				p3Disappear = true;
				try 
				{
					AudioInputStream audioIn = AudioSystem.getAudioInputStream (new File("win.wav"));
					Clip clip = AudioSystem.getClip();
					clip.open (audioIn);
					clip.start();
					} catch (Exception exception) { exception.printStackTrace(); }
				if (p1Disappear == false) one.draw(g);
				if (p2Disappear == false) 
				{
					two.draw(g);
					parent.state.getFlightNumber().setText("1");
					parent.state.getScoreNumber().setText("1");
				}
				else if(p2Disappear == true)
				{
					parent.timer.stop();
					parent.state.getFlightNumber().setText("0");
					parent.state.getScoreNumber().setText("2");
					JOptionPane.showMessageDialog(parent,"You win!");
			   }
			}
			// If collision between plane2, plane3, delete plane2
			else if(checkCollisions12() == false && checkCollisions13() == false && checkCollisions23() == true)
			{
				if (p2Disappear == false) two.draw(g);
				if (p1Disappear == false) one.draw(g);
				if (p3Disappear == false) three.draw(g);
			}
			else
			{
				if (p1Disappear == false) one.draw(g);
				if (p2Disappear == false) two.draw(g);
				if (p3Disappear == false) three.draw(g);}
		}	
	}

	public Plane1 getOne()
	{
		return one;
	}
	
	public Plane2 getTwo() 
	{
		return two;
	}
	
	public Plane3 getThree()
	{
		return three;
	}
	
	public void setPlane(Plane plane)
	{
		this.plane = plane;
		parent.setFrame(0);
		repaint();
	}
	// Called from the ColorScrollbar's
	public void setColor (String whichColor, int newValue)
	{
		if ( whichColor.equals("Red")) red = newValue;
		else if ( whichColor.equals("Green")) green = newValue;
		else if ( whichColor.equals("Blue")) blue = newValue;
		repaint();
	}

	public ArrayList<Plane> getPlanes()
	{
		return planes;
	}

	public Plane getPlane() 
	{
		return plane;
	}
	
	/* MouseListener callbacks */
	public void mousePressed (MouseEvent event)
	{
		p1Pressed = false;
		p2Pressed = false;
		p3Pressed = false;
		int point_X = event.getX();
		int point_Y = event.getY();
		if((event.getPoint().x > (one.getInitX()+this.parent.getFrame()))&&(event.getPoint().x < (one.getInitX()+this.parent.getFrame()+50))
			&&(event.getPoint().y < one.getInitY()) && (event.getPoint().y > one.getInitY() - 50))
		{
			System.out.println ("Mouse down at " + event.getPoint().x + ", " + event.getPoint().y + "Plane 1 was selected");
			p1Pressed = true;
			}
		if ((event.getPoint().x > (two.getInitX()+2*this.parent.getFrame()))&&(event.getPoint().x < (two.getInitX()+2*this.parent.getFrame()+50))
			&&(event.getPoint().y < two.getInitY()+2*this.parent.getFrame()+50) && (event.getPoint().y > two.getInitY()+2*this.parent.getFrame() ))
		{
			System.out.println ("Mouse down at " + event.getPoint().x + ", " + event.getPoint().y + "Plane 2 was selected");
			p2Pressed = true;
			}
		if ((event.getPoint().x > (three.getInitX()))&&(event.getPoint().x < (three.getInitX()+50))
			&&(event.getPoint().y < three.getInitY()+this.parent.getFrame()+50) && (event.getPoint().y > three.getInitY()+this.parent.getFrame() ))
		{
			System.out.println ("Mouse down at " + event.getPoint().x + ", " + event.getPoint().y + "Plane 3 was selected");
			p3Pressed = true;
		}
		else
			System.out.println ("Mouse down at " + event.getPoint().x + ", " + event.getPoint().y);
		repaint();	
	}
	
	// Set the collision rules
	public boolean checkCollisions12 () 
	{
		int x1 = one.getX1();
		int y1 = one.getY1();
		int x2 = two.getX1();
		int y2 = two.getY1();
		if ((x2 > x1 - 50) && (x2 < x1 + 50) && (y2 > y1 - 100) && (y2 < y1))
		{
			return true;
		}	
		else return false;
	}

	public boolean checkCollisions13 ()
	{
		int x1 = one.getX1();
		int y1 = one.getY1();
		int x3 = three.getX1();
		int y3 = three.getY1();
		if ((x3 > x1 - 50) && (x3 < x1 + 50) && (y3 > y1 - 100) && (y3 < y1))
		{
			return true;
		}	
		else return false;
	}

	public boolean checkCollisions23 () 
	{
		int x2 = two.getX1();
		int y2 = two.getY1();
		int x3 = three.getX1();
		int y3 = three.getY1();
		if ((x3 > x2 - 50) && (x3 < x2 + 50) && (y3 > y2- 50) && (y3 < y2 + 50))
		{
			return true;
		}	
		else return false;	
	}

	// Check if my plane has enter "don't enter area"
	public boolean checkCollisionsGDArea () 
	{
		int x1 = one.getX1();
		int y1 = one.getY1();
		int x3 = 275;
		int y3 = 80;
		if ((x1 > x3 - 50) && (x1 < x3 + 100) && (y1 > y3) && (y1 < y3 + 150))
		{
			return true;
		}	
		else return false;	
	}
	// Check if my plane has meet mountain area
	public boolean checkCollisionsMTArea () 
	{
		int x1 = one.getX1();
		int y1 = one.getY1();
		int x3 = 280;
		int y3 = 320;
		if ((x1 > x3 - 50) && (x1 < x3 + 100) && (y1 > y3) && (y1 < y3 + 100))
		{
			return true;
		}	
		else return false;	
	}

	public void mouseClicked (MouseEvent event) {}
	public void mouseReleased (MouseEvent event) {}
	public void mouseEntered (MouseEvent event) {}
	public void mouseExited (MouseEvent event) {}
}


