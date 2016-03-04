/*
 * This project is an implementation of a plane GUI like the “Anaconda Game” 
 * The user can control their own plane by clicking "up/down/left/right" buttons
 * There are both original view and radar view for the GUI
 * The user should also be careful of the mountain area and do not enter area
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Main extends JFrame 
implements ActionListener, KeyListener
{
	// Initiate new class
    Map canvas = new Map(this);
    MapZoom  zoomCanvas = new MapZoom(this);
    directionPanel  directionControl = new directionPanel(canvas,this);
    StatePanel   state = new StatePanel(this);
    
    // Initiate the counter
    private int frame = 0;
    private int frame_2 = 0;
    private int frame_3 = 0;
    private int zoomFrame = 0;
    public boolean zoom;
    
    // Return the original/radar view the user are currently using
    public int getZoomFrame()
    {
		return zoomFrame;
	}
    
	public void setZoom(boolean zoom) 
	{
		this.zoom = zoom;
	}
	
	public int getFrame_2()
	{
		return frame_2;
	}
	
	public void setFrame_2(int frame_2) 
	{
		this.frame_2 = frame_2;
	}
	
	public int getFrame_3() 
	{
		return frame_3;
	}
	
	public void setFrame_3(int frame_3) 
	{
		this.frame_3 = frame_3;
	}
	
	// Set the timer
	Timer timer = new Timer (100, new CustomActionListener()); // 100 milliseconds

	public static void main (String [] args)
	{
		new Main();
	}
	public Main ()
	{
		// Window setup
		setLocation(100,100);
		setSize (650,650);
		setDefaultCloseOperation (EXIT_ON_CLOSE);
		Container content = getContentPane();
		content.setLayout (new BorderLayout());
		addKeyListener (this);
	  
		// Put the panel to choose color from R, G, B
	    JPanel scrollBarPanel = new JPanel();
		scrollBarPanel.setLayout(new GridLayout(3,2));
		JLabel label1 = new JLabel ("Red:");
		scrollBarPanel.add(label1);
		scrollBarPanel.add(new ColorScrollbar("Red", canvas));
		JLabel label2 = new JLabel ("Green:");
		scrollBarPanel.add(label2);
		scrollBarPanel.add(new ColorScrollbar("Green", canvas));
		JLabel label3 = new JLabel ("Blue:");
		scrollBarPanel.add(label3);
		scrollBarPanel.add(new ColorScrollbar("Blue", canvas));
	    
		// Initialize the original position selected by the user
		canvas.getOne().setCurrentX1(canvas.getOne().getInit_x());
		canvas.getOne().setCurrentY1(canvas.getOne().getInit_y());
		canvas.getTwo().setCurrentX1(canvas.getTwo().getInit_x());
		canvas.getTwo().setCurrentY1(canvas.getTwo().getInit_y());
		canvas.getThree().setCurrentX1(canvas.getThree().getInit_x());
		canvas.getThree().setCurrentY1(canvas.getThree().getInit_y());
		canvas.getOne().setX1(canvas.getOne().getCurrentX1());
		canvas.getOne().setY1(canvas.getOne().getCurrentY1());
		canvas.getTwo().setX1(canvas.getTwo().getCurrentX1());
		canvas.getTwo().setY1(canvas.getTwo().getCurrentY1());
		canvas.getThree().setX1(canvas.getThree().getCurrentX1());
		canvas.getThree().setY1(canvas.getThree().getCurrentY1());
	    
		// Set the layout of the canvas
		JPanel controls = new JPanel();
		controls.setLayout(new FlowLayout());
		JPanel button_control = new JPanel();
		button_control.setLayout(new FlowLayout());

		// Add three buttons start, stop, reset
		JButton button_1 = new JButton ("Start");
		button_control.add(button_1);
		button_1.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				timer.start();
				try 
				{
					AudioInputStream audioIn = AudioSystem.getAudioInputStream (new File("begin.wav"));
					Clip clip = AudioSystem.getClip();
					clip.open (audioIn);
					clip.start();
			    	} catch (Exception exception) { exception.printStackTrace(); }
		}});
		
		JButton button_2 = new JButton ("Stop");
		button_control.add(button_2);
		button_2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				timer.stop();	
		}});
		
		JButton button_3 = new JButton ("Reset");
		button_control.add(button_3);
		button_3.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				canvas.p1Disappear = false;
				canvas.p2Disappear = false;
				canvas.p3Disappear = false;
				frame=0;
				frame_2 = 0;
				frame_3 = 0;
				canvas.getOne().setCurrentX1(canvas.getOne().getInit_x());
				canvas.getOne().setCurrentY1(canvas.getOne().getInit_y());
				canvas.getTwo().setCurrentX1(canvas.getTwo().getInit_x());
				canvas.getTwo().setCurrentY1(canvas.getTwo().getInit_y());
				canvas.getThree().setCurrentX1(canvas.getThree().getInit_x());
				canvas.getThree().setCurrentY1(canvas.getThree().getInit_y());
				canvas.getOne().setX1(canvas.getOne().getCurrentX1());
				canvas.getOne().setY1(canvas.getOne().getCurrentY1());
				canvas.getTwo().setX1(canvas.getTwo().getCurrentX1());
				canvas.getTwo().setY1(canvas.getTwo().getCurrentY1());
				canvas.getThree().setX1(canvas.getThree().getCurrentX1());
				canvas.getThree().setY1(canvas.getThree().getCurrentY1());
				state.getFlightNumber().setText("2");
				state.getScoreNumber().setText("0");
				zoomFrame = 0;
				timer.stop();
				canvas.repaint ();	
				//If reset is pressed when zoom, give it's original position
				if(zoom)
				{
					zoomCanvas.setInit_Plane1(new Point(canvas.getOne().getInit_x(), canvas.getOne().getInit_y()));
					zoomCanvas.setInit_Plane2(new Point(canvas.getTwo().getInit_x(), canvas.getTwo().getInit_y()));
					zoomCanvas.setInit_Plane3(new Point(canvas.getThree().getInit_x(), canvas.getThree().getInit_y()));
					zoomCanvas.repaint();	
				}
		}});
		
		JButton button_4 = new JButton ("Radar View");
		button_control.add(button_4);
		button_4.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				canvas.setVisible(false);
			    if(!zoom)
			    {
                zoom = true;
                zoomCanvas.setInit_Plane1(new Point(canvas.getOne().getX1(), canvas.getOne().getY1()));
                zoomCanvas.setInit_Plane2(new Point(canvas.getTwo().getX1(), canvas.getTwo().getY1()));
                zoomCanvas.setInit_Plane3(new Point(canvas.getThree().getX1(), canvas.getThree().getX1()));
                zoomCanvas.repaint();
			    }
		}});
		
		JButton button_5 = new JButton ("Original View");
		button_control.add(button_5);
		button_5.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				canvas.setVisible(true);
				zoom = false;
		}});
		 
		button_control.add(directionControl);
		JPanel control_together = new JPanel(); 
		control_together.setLayout(new BoxLayout(control_together,BoxLayout.Y_AXIS));
		control_together.add(state);
		control_together.add(button_control);
		control_together.add(controls);
		
		canvas.setBorder (new LineBorder(Color.RED,2));
		JPanel canvasPanel = new JPanel();		
		canvasPanel.setLayout(new FlowLayout());
		canvasPanel.setLayout(new GridLayout(1,1));
		LayoutManager overlay = new OverlayLayout(canvasPanel);
		canvasPanel.setLayout(overlay);
		canvasPanel.add(canvas);
		canvasPanel.add(zoomCanvas);
		content.add(canvasPanel, BorderLayout.CENTER);
		content.add(control_together, BorderLayout.SOUTH);
		setVisible (true);	
  }
  
public int getFrame ()
{ 
	return frame; 
}

/* A class to get and set the position of each plane
* The system will call this function every second
* It only shows the planes that are alive
*/
class CustomActionListener implements ActionListener
{
	public void actionPerformed(ActionEvent e) 
	{
		if(directionControl.getCurrentButton() == "up")
		{
			canvas.getOne().setX1(canvas.getOne().getCurrentX1());
			canvas.getOne().setY1(canvas.getOne().getCurrentY1()-5*frame);
		}
		else if(directionControl.getCurrentButton() == "down")
		{
			canvas.getOne().setX1(canvas.getOne().getCurrentX1());
			canvas.getOne().setY1(canvas.getOne().getCurrentY1()+5*frame);
		}
		else if(directionControl.getCurrentButton() == "left")
		{
			canvas.getOne().setX1(canvas.getOne().getCurrentX1()-5*frame);
			canvas.getOne().setY1(canvas.getOne().getCurrentY1());
		}
		else
		{
			canvas.getOne().setX1(canvas.getOne().getCurrentX1()+5*frame);
			canvas.getOne().setY1(canvas.getOne().getCurrentY1());
		}

		if(canvas.getTwo().isChange() == false)
		{
			canvas.getTwo().setX1(canvas.getTwo().getCurrentX1()+5*frame_2);
			canvas.getTwo().setY1(canvas.getTwo().getCurrentY1()-5*frame_2);
	    }
		else if(canvas.getTwo().isChange() == true)
		{
			canvas.getTwo().setX1(canvas.getTwo().getCurrentX1()-5*frame_2);
			canvas.getTwo().setY1(canvas.getTwo().getCurrentY1()+5*frame_2);
		}
	
		if(canvas.getThree().isChange() == false)
		{
			canvas.getThree().setX1(canvas.getThree().getCurrentX1());
			canvas.getThree().setY1(canvas.getThree().getCurrentY1()+5*frame_3);
		}
		else if(canvas.getThree().isChange() == true)
		{
			canvas.getThree().setX1(canvas.getThree().getCurrentX1());
			canvas.getThree().setY1(canvas.getThree().getCurrentY1()-5*frame_3);
		}
		frame++;
		frame_2++;
		frame_3++;
		
		// Repaint the GUI
		canvas.repaint ();	
		
		// Check if the user use the radar view
		if(zoom){
			zoomFrame++;
			zoomCanvas.repaint();
		}
}}

@Override
public void keyPressed(KeyEvent e) {
	//if (e.getKeyCode()==KeyEvent.VK_ESCAPE) System.exit (0);
}
@Override
public void keyReleased(KeyEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void keyTyped(KeyEvent e) {
	// TODO Auto-generated method stub	
}
@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	
}
public void setFrame(int frame) {
	this.frame = frame;
}
}