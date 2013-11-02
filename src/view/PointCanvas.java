package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import model.PointModel;

public class PointCanvas extends JPanel{
	public static final int CIRCLE_DIAMETER= 12;
	public static final int PADDING = 15;
	private PointModel model;
	private List<Shape> shapes;
	
	
	public PointCanvas(PointModel model) {
		this.model = model;
		this.shapes = new ArrayList<Shape>();
		setBackground(new Color(237,235,230));
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON3) {
					removePointInCanvas(e.getPoint());
				}
				else {
					addPointToCanvas(e.getPoint());
				}
			}
		});
	}
	
	
	public void addPointToCanvas(Point p) {
		if(this.model.isNotOccupied(p.x, p.y)) {
			Shape oval = new Ellipse2D.Double(p.x - CIRCLE_DIAMETER/2, p.y - CIRCLE_DIAMETER/2 ,
					CIRCLE_DIAMETER, CIRCLE_DIAMETER);
			this.model.addPoint(p);
			this.shapes.add(oval);
		}
		
		repaint();
	}
	
	
	public void removePointInCanvas(Point p) {
		
		Shape r = null;
		for(Shape s: this.shapes) {
			if(s.contains(p)) r = s;
		}
		
		if(r != null) {
		this.shapes.remove(r);
		int x = r.getBounds().x + CIRCLE_DIAMETER/2;
		int y = r.getBounds().y + CIRCLE_DIAMETER/2;
		this.model.removePoint(x, y);
		}
		repaint();
	}

	
	public void paintComponent(Graphics g) {
        super.paintComponent(g);       
        Graphics2D g2d = (Graphics2D)g;
        
        g2d.setRenderingHint(
        	    RenderingHints.KEY_ANTIALIASING,
        	    RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new BasicStroke(4,
        		 BasicStroke.CAP_ROUND, 
                 BasicStroke.JOIN_ROUND));
        
        if(model.getHullSize()>1) {
        	g2d.setColor(new Color(148,199,182));
        	
        	Polygon p = new Polygon();
        	for(int i = 0; i< model.getHullSize(); i++) {
        		Point po = this.model.getHullPoint(i);
        		p.addPoint(po.x, po.y);
            }
        	g2d.drawPolygon(p);
        }
        
        g2d.setColor(new Color(64,59,51));
        for (Shape s : this.shapes) {
            g2d.draw(s);
        }
        
        g2d.setColor(new Color(211,100,59));
        List<Point> max = this.model.getMaxDistancePoints();
        for (Point p : max) {
        	g2d.fillOval(p.x - CIRCLE_DIAMETER/2, p.y - CIRCLE_DIAMETER/2, 
        				CIRCLE_DIAMETER, CIRCLE_DIAMETER);
        }
        
        g2d.setFont(new Font(g.getFont().getName(), Font.PLAIN, 18));
        g2d.drawString("Distance: " + String.format("%.2f",this.model.getMaxDistance()),
        				PADDING, PADDING*2);
        g2d.dispose();
    }  

}
