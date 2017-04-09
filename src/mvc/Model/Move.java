/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.Model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author p1401687
 */
public class Move {

    public enum Direction{
    LEFT_UP_DIAGONAL,
    LEFT_DOWN_DIAGONAL,
    RIGHT_UP_DIAGONAL,
    RIGHT_DOWN_DIAGONAL,
    LEFT,
    RIGHT,
    UP,
    DOWN,
    NONE};
    
    
    private Point start;
    private Point destination;
    private Point[] intermediatePoints;

    public Move(Point start, Point destination,Direction d) {
        this.start = start;
        this.destination = destination;
        
        Point p;
        List<Point> inter = new ArrayList<>();
        switch(d){
            case LEFT_UP_DIAGONAL :
                p = new Point(start.getX()-1, start.getY()-1);
                while(p != destination){
                    inter.add(p);
                    p = new Point(p.getX()-1, p.getY()-1);
                }
                break;
            case LEFT_DOWN_DIAGONAL:
                p = new Point(start.getX()+1, start.getY()-1);
                while(p != destination){
                    inter.add(p);
                    p = new Point(p.getX()+1, p.getY()-1);
                }
                break;
            case RIGHT_UP_DIAGONAL:
                p = new Point(start.getX()-1, start.getY()+1);
                while(p != destination){
                    inter.add(p);
                    p = new Point(p.getX()-1, p.getY()+1);
                }
                break;   
            case RIGHT_DOWN_DIAGONAL:
                p = new Point(start.getX()+1, start.getY()+1);
                while(p != destination){
                    inter.add(p);
                    p = new Point(p.getX()+1, p.getY()+1);
                }
                break;
            case LEFT:
                p = new Point(start.getX(), start.getY()-1);
                while(p != destination){
                    inter.add(p);
                    p = new Point(p.getX(), p.getY()-1);
                }                
                break;
            case RIGHT:
                p = new Point(start.getX(), start.getY()+1);
                while(p != destination){
                    inter.add(p);
                    p = new Point(p.getX(), p.getY()+1);
                }
                break;
            case UP:
                p = new Point(start.getX()-1, start.getY());
                while(p != destination){
                    inter.add(p);
                    p = new Point(p.getX()-1, p.getY());
                }
                break;
            case DOWN:
                p = new Point(start.getX()+1, start.getY());
                while(p != destination){
                    inter.add(p);
                    p = new Point(p.getX()+1, p.getY());
                }
                break;
            case NONE:
                break;
        }
        
        Point[] intermediatePoints = new Point[inter.size()];
        intermediatePoints = inter.toArray(intermediatePoints);
    }

    public Move(Point start, Point destination, Point[] intermediatePoints) {
        this.start = start;
        this.destination = destination;
        this.intermediatePoints = intermediatePoints;
    }
    
    

    public Point getStart() {
        return start;
    }

    public Point[] getIntermediatePoints() {
        return intermediatePoints;
    }

    public Point getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        return "Move{" + "start=" + start + ", destination=" + destination + '}';
    }
    
    
    
}
