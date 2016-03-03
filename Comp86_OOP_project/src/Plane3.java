/*
 * Plane3 that extends from class Plane
 * The method to draw Plane3 from initial position
 */

import java.awt.Color;
import java.awt.Graphics;

public class Plane3 extends Plane
{
	public Plane3(int init_x, int init_y, String color,Main parent)
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
		else{
			g.drawRect(x1, y1, 50, 50);
			g.setColor(Color.RED);
		}
		g.drawString("Plane3"+"Position_x:"+x1+"      Position_y:"+y1, 10, 55);
		if(y1-30 <= 0)
		{
			currentX1 = x1;
			currentY1 = y1+5;
			isChange = false;
			parent.setFrame_3(0);
		}
		else if(y1+50 >= 400)
		{
			currentX1 = x1;
			currentY1 = y1-5;
			isChange = true;
			parent.setFrame_3(0);
		}
		if (parent.canvas.p3Pressed == true) 
		{
			g.setColor(Color.RED);
			g.fillRect(x1, y1, 50, 50);
		}		 
	}
	
}

