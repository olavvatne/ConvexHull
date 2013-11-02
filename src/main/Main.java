package main;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;


import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


import view.PointCanvas;
import model.PointModel;

public class Main {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() 
			{
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Main.run();
			}
		});
	}

	public static void run() {
		JFrame frame = new JFrame();
		PointModel model = new PointModel();
		PointCanvas canvas = new PointCanvas(model);
		Dimension sz = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setMinimumSize(new Dimension((int)(sz.getWidth()/2), (int)(sz.getHeight()/2)));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(sz.width/10 , sz.height/10);
		frame.setTitle("Gramham scan");
		frame.add(canvas);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
		
	}
}
