/*
 * Plane2 that extends from class Plane
 * The method to draw Plane2 from initial position
 */

import java.awt.Color;
import java.awt.Graphics;

public class Plane2 extends Plane 
{
	public Plane2(int init_x, int init_y, String color,Main parent)
	{
		super(init_x, init_y, color, parent);	
	}
	public void draw(Graphics g)
	{
		if(parent.zoom)
		{
			g.drawOval(x1, y1, 20, 20);
			g.setColor(Color.RED);
		}
		else 
		{
			g.drawOval(x1, y1, 50, 50);
			g.setColor(Color.RED);
		}
		g.drawString("Plane2"+"Position_x:"+x1+"      Position_y:"+y1,10, 35);
		if(y1-50 <= 0)
		{
			currentX1 = x1;
			currentY1 = y1+2;
			isChange = true;
			parent.setFrame_2(0);	
		}
		else if(y1+50 >= 400)
		{
			currentX1 = x1;
			currentY1 = y1-2;
			isChange = false;
			parent.setFrame_2(0);
		}
		if (parent.canvas.p2Pressed == true)
		{
			g.setColor(Color.RED);
			g.fillOval(x1, y1, 50, 50);
		}
	}
}