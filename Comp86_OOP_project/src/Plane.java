/*
 * The class that defines the properties of each plane that extends from it
 */
import java.awt.Graphics;

public abstract class Plane
{
	private int init_x;
	private int init_y;
	protected boolean isChange;
	public Main parent;
	private String color;
	protected int x1;
	protected int y1;
	protected int currentX1;
	protected int currentY1;
	
	public int getCurrentX1()
	{
		return currentX1;
	}	

	public boolean isChange() 
	{
		return isChange;
	}

	public void setChange(boolean isChange)
	{
		this.isChange = isChange;
	}

	public void setCurrentX1(int currentX1)
	{
		this.currentX1 = currentX1;
	}

	public int getCurrentY1() 
	{
		return currentY1;
	}

	public void setCurrentY1(int currentY1)
	{
		this.currentY1 = currentY1;
	}

	public int getX1()
	{
		return x1;
	}

	public void setX1(int x1)
	{
		this.x1 = x1;
	}

	public int getY1() 
	{
		return y1;
	}

	public void setY1(int y1)
	{
		this.y1 = y1;
	}

	public Plane(int init_x, int init_y, String color, Main parent)
	{
		this.init_x = init_x;
		this.init_y = init_y;
		this.color = color;
		this.parent = parent;	
	}	

	public void changeColor(String color)
	{
		this.color = color;
	}

	public int getInit_x()
	{
		return init_x;
	}

	public void setInit_x(int init_x) 
	{
		this.init_x = init_x;
	}

	public int getInit_y()
	{
		return init_y;
	}

	public void setInit_y(int init_y) 
	{
		this.init_y = init_y;
	}

	public abstract void draw(Graphics g);

	public int getInitX()
	{
		return init_x;
	}
	
	public int getInitY()
	{
		return init_y;
	}
}
