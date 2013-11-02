package model;

import java.awt.Point;
import java.util.Comparator;


public class PolarPointComparator implements Comparator<Point> {
	private Point polarPoint;
	
	public PolarPointComparator(Point polarPoint) {
		this.polarPoint = polarPoint;
	}
	
	@Override
	public int compare(Point a, Point b) {
		
		double angA =(Math.atan2(a.y-polarPoint.y, a.x-polarPoint.x));
		double angB =(Math.atan2(b.y-polarPoint.y, b.x-polarPoint.x));
		
		if (angA < angB) return -1;
		else if (angA > angB) return 1;
		return 0;
	}

}
