package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Stack;




public class ConvexHull {
	public final static int  MINIMUM_POINT_IN_CONVEX = 3;
	static enum Turn { CLOCKWISE, COUNTER_CLOCKWISE, COLLINEAR };
	
	public static List<Point> grahamScan(Collection<Point> points) {
		ArrayList<Point> list = new ArrayList<Point>(points);
		
		if(list.size() < MINIMUM_POINT_IN_CONVEX) {
			return null;
		}
		
		Point minimumPoint = list.get(0);
		for (Point point: list) {
			
			if(minimumPoint.y > point.y) {
				minimumPoint = point;
			}
			else if(minimumPoint.y == point.y) {
				if(minimumPoint.x > point.x) {
					minimumPoint = point;
				}
			}
			
		}
	
		Collections.sort(list, new PolarPointComparator(minimumPoint));
		Stack<Point> convexHull = new Stack<Point>();
		

		convexHull.push(list.get(0));
		convexHull.push(list.get(1));

		for( int i = 2; i<list.size(); i++) {
			
			Point head = list.get(i);
			Point middle = convexHull.pop();
			Point tail = convexHull.peek();
			
			Turn turn = getTurn(tail, middle, head);
			
			switch (turn) {
			case COUNTER_CLOCKWISE:
				convexHull.push(middle);
				convexHull.push(head);
				break;
			case CLOCKWISE:
				i --;
				break;
			case COLLINEAR:
				convexHull.push(head);
				break;
				
			}
		}
		return new ArrayList(convexHull);
	}
	
	private static Turn getTurn(Point p1, Point p2, Point p3) {
		long turn =  (p2.x - p1.x)*(p3.y - p1.y) - (p2.y - p1.y)*(p3.x - p1.x);
		
		if(turn> 0) {
			return Turn.COUNTER_CLOCKWISE;
		}
		else if(turn< 0) {
			return Turn.CLOCKWISE;
		}
		else {
			return Turn.COLLINEAR;
		}
	}	
}
