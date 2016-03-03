/*
 * Plane1 that extends from class Plane
 * The method to draw Plane1 from initial position
 */

import java.awt.Color;
import java.awt.Graphics;

public class Plane1 extends Plane
{
	public Plane1(int init_x, int init_y, String color,Main parent)
	{
		super(init_x, init_y, color, parent);	
	}
	@Override
	public void draw(Graphics g)
	{
		if(parent.zoom)
		{
			g.setColor(Color.RED);
			g.drawOval(x1, y1, 20, 20);
			g.fillOval(x1, y1, 20, 20);
		}
		else
		{
			g.drawLine(x1, y1, x1+25, y1-50);
			g.drawLine(x1+25, y1-50,x1+50, y1);
			g.drawLine(x1, y1,x1+50, y1);
			g.setColor(Color.RED);
			g.fillPolygon(new int[]{x1, x1+25, x1+50},new int[]{y1, y1-50, y1}, 3);	
		}

	 g.drawString("Plane1"+"Position_x:"+x1+"      Position_y:"+y1, 10, 15);
	}
}
