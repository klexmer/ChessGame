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
    private boolean enemyNeeded = false;

    public Move(Point start, Point destination,Direction d) {
        this.start = start;
        this.destination = destination;
        
        if(d != Direction.NONE){
        Point p;
        List<Point> inter = new ArrayList<>();
        switch(d){
            case LEFT_UP_DIAGONAL :
                p = new Point(start.getX()-1, start.getY()-1);
                while(p.getX() != destination.getX() || p.getY() != destination.getY()){
                    inter.add(p);
                    p = new Point(p.getX()-1, p.getY()-1);
                }
                break;
            case LEFT_DOWN_DIAGONAL:
                p = new Point(start.getX()+1, start.getY()-1);
                while(p.getX() != destination.getX() || p.getY() != destination.getY()){
                    inter.add(p);
                    p = new Point(p.getX()+1, p.getY()-1);
                }
                break;
            case RIGHT_UP_DIAGONAL:
                p = new Point(start.getX()-1, start.getY()+1);
                while(p.getX() != destination.getX() || p.getY() != destination.getY()){
                    inter.add(p);
                    p = new Point(p.getX()-1, p.getY()+1);
                }
                break;   
            case RIGHT_DOWN_DIAGONAL:
                p = new Point(start.getX()+1, start.getY()+1);
                while(p.getX() != destination.getX() || p.getY() != destination.getY()){
                    inter.add(p);
                    p = new Point(p.getX()+1, p.getY()+1);
                }
                break;
            case LEFT:
                p = new Point(start.getX(), start.getY()-1);
                while(p.getX() != destination.getX() || p.getY() != destination.getY()){
                    inter.add(p);
                    p = new Point(p.getX(), p.getY()-1);
                }                
                break;
            case RIGHT:
                p = new Point(start.getX(), start.getY()+1);
                while(p.getX() != destination.getX() || p.getY() != destination.getY()){
                    inter.add(p);
                    p = new Point(p.getX(), p.getY()+1);
                }
                break;
            case UP:
                //System.out.println("Couocu");
                p = new Point(start.getX()-1, start.getY());
                //System.out.println("p = " + p + " Destination = " + this.destination);
                while(p.getX() != destination.getX() || p.getY() != destination.getY()){
                    //System.out.println(p);
                    inter.add(p);
                    p = new Point(p.getX()-1, p.getY());
                }
                break;
            case DOWN:
                p = new Point(start.getX()+1, start.getY());
                while(p.getX() != destination.getX() || p.getY() != destination.getY()){
                    inter.add(p);
                    p = new Point(p.getX()+1, p.getY());
                }
                break;
        }
        
        Point[] intermediate = new Point[inter.size()];
        intermediatePoints = inter.toArray(intermediate);
        /*for(Point i : intermediatePoints)
                System.out.println(i);*/
        }else{
            //System.out.println("NONE");
            intermediatePoints = null;
        }
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

    public void setEnemyNeeded(boolean enemyNeeded) {
        this.enemyNeeded = enemyNeeded;
    }

    public boolean isEnemyNeeded() {
        return enemyNeeded;
    }

    @Override
    public String toString() {
        return "Move{" + "start=" + start + ", destination=" + destination + '}';
    }
    
    
    
}
