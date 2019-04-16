package com.window;

import java.awt.Canvas;
import java.awt.FlowLayout;

import javax.swing.JFrame;

public class Display extends JFrame {
	private static final long serialVersionUID = 1L;
	
	JFrame jframe = new JFrame();
	Canvas canvas = new Canvas();
	
	//public Image guy = new ImageIcon("guy.png").getImage();
	
	public Display(int width, int height) {
		setLayout(new FlowLayout());
		
		jframe.setSize(width,height);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setTitle("The Quest for the Holy Cream Betweens");
		jframe.setVisible(true);
		jframe.setResizable(false);
		//jframe.setLocation(400,150);
		jframe.setLocation(330,150);
		canvas.setSize(width,height);
		canvas.setFocusable(false);
		
		jframe.add(canvas);
		jframe.pack();
	}
	
	public Canvas getCanvas() {
		return canvas;
	}
	
	public JFrame getJFrame() {
		return jframe;
	}
}
