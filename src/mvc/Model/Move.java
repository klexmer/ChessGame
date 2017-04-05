/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.Model;

/**
 *
 * @author p1401687
 */
public class Move {

    private Point start;
    private Point destination;

    public Move(Point start, Point destination) {
        this.start = start;
        this.destination = destination;
    }

    public Point getStart() {
        return start;
    }

    public Point getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        return "Move{" + "start=" + start + ", destination=" + destination + '}';
    }
    
    
    
}
