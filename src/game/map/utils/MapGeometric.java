package game.map.utils;

import game.map.Dimension;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * @author Noris
 * @date 2015/05/20
 */

public class MapGeometric {

	/**
	 * @param position
	 *            a position
	 * @param center
	 *            the point that must be checked
	 * @param radius
	 *            the tolerance radius
	 * 
	 * @return true if the area is already used, false otherwise
	 */
	public static boolean checkIfUsed(Dimension position, Dimension center, double radius) {

		double x = (position.getWidth() - center.getWidth());
		double x2 = x * x;

		double y = (position.getLength() - center.getLength());
		double y2 = y * y;

		if ((x2 + y2) < radius * radius)
			return true;

		return false;
	}

	/**
	 * 
	 * @param position
	 *            a position
	 * @param positions
	 *            the points that must be checked
	 * @param radius
	 *            the tolerance radius
	 * 
	 * @return true if the area of at least one circle's center of the array is
	 *         already used, false otherwise
	 */
	public static boolean checkIfUsed(Dimension position, ArrayList<Dimension> positions,
			double radius) {

		for (Dimension p : positions) {
			if (checkIfUsed(position, p, radius))
				return true;
		}

		return false;
	}

	/**
	 * @param position
	 *            of the wall
	 * @param size
	 *            of the wall
	 * 
	 * @return the extremes point of the wall segment
	 */
	public static ArrayList<Dimension> getWallPoints(Dimension position, Dimension size) {

		ArrayList<Dimension> points = new ArrayList<Dimension>(2);

		double length1 = position.getLength() + size.getLength() / 2;
		double length2 = position.getLength() - size.getLength() / 2;

		double width1 = position.getWidth() + size.getWidth() / 2;
		double width2 = position.getWidth() - size.getWidth() / 2;

		points.add(new Dimension(width1, 0, length1));
		points.add(new Dimension(width2, 0, length2));

		return points;

	}

	/**
	 * @param position
	 *            of the wall
	 * @param size
	 *            of the wall
	 * 
	 * @return the extremes point of the wall segment, without the wall size
	 */
	public static ArrayList<Dimension> getWallPointsOnLength(Dimension position, Dimension size) {

		ArrayList<Dimension> points = new ArrayList<Dimension>(2);

		double length1 = position.getLength() + size.getLength() / 2;
		double length2 = position.getLength() - size.getLength() / 2;

		double width1 = position.getWidth();
		double width2 = position.getWidth();

		points.add(new Dimension(width1, 0, length1));
		points.add(new Dimension(width2, 0, length2));

		return points;

	}

	/**
	 * @param position
	 *            of the wall
	 * @param size
	 *            of the wall
	 * 
	 * @return the extremes point of the wall segment
	 */
	public static ArrayList<Dimension> getWallPointsOnWidth(Dimension position, Dimension size) {

		ArrayList<Dimension> points = new ArrayList<Dimension>(2);

		double length1 = position.getLength();
		double length2 = position.getLength();

		double width1 = position.getWidth() + size.getWidth() / 2;
		double width2 = position.getWidth() - size.getWidth() / 2;

		points.add(new Dimension(width1, 0, length1));
		points.add(new Dimension(width2, 0, length2));

		return points;

	}

	/**
	 * @param p
	 *            point position
	 * @param l1
	 *            a point of the line
	 * @param l2
	 *            another point of the line
	 * 
	 * @return true if the point is on the line of the wall, false otherwise
	 */
	public static boolean isPointOnLine(Dimension p, Dimension l1, Dimension l2) {

		if (Line2D.ptLineDist(l1.getWidth(), l1.getLength(), l2.getWidth(), l2.getLength(),
				p.getWidth(), p.getLength()) == 0)
			return true;

		return false;
	}

	/**
	 * 
	 * @param points
	 *            an array of positions
	 * @param l1
	 *            a point of the line
	 * @param l2
	 *            another point of the line
	 * 
	 * @return true if at least one point of the array is on the line, false
	 *         otherwise
	 */
	public static boolean isPointOnLine(ArrayList<Dimension> points, Dimension l1, Dimension l2) {

		for (Dimension p : points) {
			if (isPointOnLine(p, l1, l2))
				return true;
		}

		return false;
	}

	/**
	 * @param p
	 *            point position
	 * @param l1
	 *            the first end of the segment
	 * @param l2
	 *            the second end of the segment
	 * 
	 * @return true if the point is on the segment, false otherwise
	 */
	public static boolean isPointOnSegment(Dimension p, Dimension l1, Dimension l2) {

		if (Line2D.ptSegDist(l1.getWidth(), l1.getLength(), l2.getWidth(), l2.getLength(),
				p.getWidth(), p.getLength()) == 0)
			return true;

		return false;
	}

	/**
	 * @param points
	 *            an array of positions
	 * @param l1
	 *            the first end of the segment
	 * @param l2
	 *            the second end of the segment
	 * 
	 * @return true if at least one point of the array is on the segment, false
	 *         otherwise
	 */
	public static boolean isPointOnSegment(ArrayList<Dimension> points, Dimension l1, Dimension l2) {

		for (Dimension p : points) {
			if (isPointOnSegment(p, l1, l2))
				return true;
		}

		return false;
	}

	/**
	 * @param center
	 *            the center position of the circle
	 * @param radius
	 *            the radius of the circle
	 * @param l1
	 *            the first end of the segment
	 * @param l2
	 *            the second end of the segment
	 * 
	 * @return true if the circle intersects the segment, false otherwise
	 */
	public static boolean isCircleOnSegment(Dimension center, double radius, Dimension l1,
			Dimension l2) {

		Line2D line = new Line2D.Double();
		line.setLine(l1.getWidth(), l1.getLength(), l2.getWidth(), l2.getLength());

		Point2D point = new Point2D.Double();
		point.setLocation(center.getWidth(), center.getLength());

		if (line.ptSegDist(point) <= radius)
			return true;

		return false;
	}

	/**
	 * @param centers
	 *            an array of circle's centers positions
	 * @param radius
	 *            the radius of every circle
	 * @param l1
	 *            the first end of the segment
	 * @param l2
	 *            the second end of the segment
	 * 
	 * @return true if at least one circle of the array intersects the segment,
	 *         false otherwise
	 */
	public static boolean isCircleOnSegment(ArrayList<Dimension> centers, double radius,
			Dimension l1, Dimension l2) {

		for (Dimension c : centers) {
			if (isCircleOnSegment(c, radius, l1, l2))
				return true;
		}

		return false;
	}

	/**
	 * @param p1
	 *            the first point
	 * @param p2
	 *            the second point
	 * 
	 * @return the distance between p1 and p2
	 */
	public static double getDistance(Dimension p1, Dimension p2) {
		return Point.distance(p1.getWidth(), p1.getLength(), p2.getWidth(), p2.getLength());
	}

	/**
	 * @param point1
	 *            the first point
	 * @param point2
	 *            the second point
	 * 
	 * @return the distance between p1 and p2
	 */
	public static double getDistance3D(Dimension point1, Dimension point2) {

		return Math.sqrt((point1.getWidth() - point2.getWidth())
				* (point1.getWidth() - point2.getWidth())
				+ (point1.getLength() - point2.getLength())
				* (point1.getLength() - point2.getLength())
				+ (point1.getHeight() - point2.getHeight())
				* (point1.getHeight() - point2.getHeight()));
	}

}