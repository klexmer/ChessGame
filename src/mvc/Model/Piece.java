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
public abstract class Piece {
    protected boolean living;
    protected Player owner;

    public Piece(Player owner) {
        this.owner = owner;
    }

    public boolean isLiving() {
        return living;
    }

    public void setLiving(boolean living) {
        this.living = living;
    }

    public Player getOwner() {
        return owner;
    }
        
    public abstract Move[] getPossibleMoves(Point p);
}
