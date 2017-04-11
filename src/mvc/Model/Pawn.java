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
public class Pawn extends Piece{

    
    public Pawn(Player owner) {
        super(owner);
    }

    @Override
    public Move[] getPossibleMoves(Point p){
        int x = p.getX(),y = p.getY();
        List<Move> moves = new ArrayList<>();
        if(owner.isWhite()){
            if(x==6){
                Point[] t = {new Point(x-1,y),new Point(x-2, y)};
                moves.add(new Move(p,new Point(x-2,y),t));
            }
            if(x-1>=0){
                Point[] t = {new Point(x-1,y)};
                moves.add(new Move(p,new Point(x-1,y),t));
            }
            if(x-1>=0 && y-1>=0){
                Move m = new Move(p,new Point(x-1,y-1),Move.Direction.NONE);
                m.setEnemyNeeded(true);
                moves.add(m);
            }
            if(x-1>=0 && y+1<8){
                Move m = new Move(p,new Point(x-1,y+1),Move.Direction.NONE);
                m.setEnemyNeeded(true);
                moves.add(m);
            }
        }else{
            if(x==1){
                Point[] t = {new Point(x+1,y),new Point(x+2, y)};
                moves.add(new Move(p,new Point(x+2,y),t));
            }
            if(x+1<8){
                Point[] t = {new Point(x+1,y)};
                moves.add(new Move(p,new Point(x+1,y),t));            
            }
            if(x+1<8 && y-1>=0){
                Move m = new Move(p,new Point(x+1,y-1),Move.Direction.NONE);
                m.setEnemyNeeded(true);
                moves.add(m);
            }
            if(x+1<8 && y+1<8){
                Move m = new Move(p,new Point(x+1,y+1),Move.Direction.NONE);
                m.setEnemyNeeded(true);
                moves.add(m);
            }
        }
        
        Move[] possibleMoves = new Move[moves.size()];
        possibleMoves = moves.toArray(possibleMoves);
        return possibleMoves;
    }

    @Override
    public String toString() {
        return "Pawn";
    }


    
}
