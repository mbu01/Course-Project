/*
 * The class that control the score of the game 
 * Keep tracking of the number of rest planes
 */

import java.awt.*;
import javax.swing.*;

public class StatePanel extends JPanel 
{
	protected JLabel flightRemain;
	protected JLabel score;
	protected JLabel flightNumber;
	protected JLabel scoreNumber;
	public Main parent;
	public JLabel getFlightRemain() 
	{
		return flightRemain;
	}
	
	public void setFlightRemain(JLabel flightRemain)
	{
		this.flightRemain = flightRemain;
	}
	
	public JLabel getScore() 
	{
		return score;
	}
	
	public void setScore(JLabel score)
	{
		this.score = score;
	}
	
	public JLabel getFlightNumber() 
	{
		return flightNumber;
	}
	public void setFlightNumber(JLabel flightNumber) 
	{
		this.flightNumber = flightNumber;
	}
	
	public JLabel getScoreNumber()
	{
		return scoreNumber;
	}
	
	public void setScoreNumber(JLabel scoreNumber)
	{
		this.scoreNumber = scoreNumber;
	}
	
	public StatePanel(Main parent) 
	{
		setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
		flightRemain = new JLabel("Flight remain:");
		flightRemain.setFont(new Font("Serif", Font.PLAIN, 20));
		score = new JLabel("Score:");
		score.setFont(new Font("Serif", Font.PLAIN, 20));
		flightNumber = new JLabel("2");
		flightNumber.setFont(new Font("Serif", Font.BOLD, 30));
		scoreNumber = new JLabel("0");
		scoreNumber.setFont(new Font("Serif", Font.BOLD, 30));
		add(flightRemain);
		add(flightNumber);
		add(score);
		add(scoreNumber);
		this.parent = parent;
	}  
}
