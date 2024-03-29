package dev.game;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import dev.display.Display;

public class Game implements Runnable
{

	private Display display;
	public int width, height;
	public String title;
	private BufferStrategy bs; //no flickering
	private Graphics g;
	private Thread thread;
	private boolean running = false;
	public Game(String title, int width, int height)
	{
	this.width = width;
	this.height = height;
	this.title = title;
	
	}
	public void init()
	{
		display = new Display(title, width, height);
	}
	private void update(){
		
	}
	private void render()
	{
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null){
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		g.clearRect(0,0, width, height);
		bs.show();
		g.dispose();
	}
	public void run() 	
	{
		init();
		while(running)
		{
		update();
		render();
		
		}
		stop();
	}
	
	public synchronized void start()
	{
		if(running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	public synchronized void stop()
	{
		if(!running)
			return;
		running = false;
		try 
		{
			thread.join();
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}
}
