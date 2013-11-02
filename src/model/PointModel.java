package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PointModel {
	private HashMap<String, Point> pointList;
	private List<Point> hullPointList;
	private List<Point> pointsFarthestApart;
	private double maxDistance;
	
	
	public PointModel() {
		this.pointList = new HashMap<String, Point>();
		this.hullPointList = new ArrayList<Point>();
		this.pointsFarthestApart = new ArrayList<Point>();
	}
	
	
	public void addPoint(Point p) {
		this.pointList.put(p.x + p.y +"", p);
		this.hullPointList.add(p);
		
		calculateHull();
		calculateMaxDistance();
	}
	
	
	public void removePoint(int x, int y) {
		this.pointList.remove(x+y+"");
		
		calculateHull();
		calculateMaxDistance();
	}
	
	public boolean isNotOccupied(int x, int y) {
		return !this.pointList.containsKey(x+y+"");
	}
	
	private void calculateHull() {
		List<Point> hull = ConvexHull.grahamScan(this.pointList.values());
		if(hull!= null) {
			this.hullPointList = hull;
		}
		else {
			this.hullPointList = new ArrayList<Point>(pointList.values());	
		}
	}
	
	
	public List<Point> getMaxDistancePoints() {
		return this.pointsFarthestApart;
	}
	
	
	public double getMaxDistance() {
		return this.maxDistance;
	}
	
	
	private void calculateMaxDistance() {
		ArrayList<Point> distPoints = new ArrayList<Point>();
		
		double distance = 0;
		for (int i = 0; i < hullPointList.size(); i++) {
			Point a = hullPointList.get(i);
			for (int j = i + 1; j < hullPointList.size(); j++) {
				Point b = hullPointList.get(j);
				double pointDistance = Math.sqrt(Math.pow((a.x - b.x), 2) + Math.pow((a.y - b.y), 2));
				if(pointDistance > distance) {
					distance = pointDistance;
					distPoints  = new ArrayList<Point>();
					distPoints.add(new Point(a));
					distPoints.add(new Point(b));
				}
			}
		}
		this.maxDistance = distance;
		this.pointsFarthestApart = distPoints;
	}
	
	
	public int getHullSize() {
		return this.hullPointList.size();
	}
	
	
	public Point getHullPoint(int i) {
		if(i<getHullSize()) {
			return this.hullPointList.get(i);
		}
		else {
			return null;
		}
	}
}
