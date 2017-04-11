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
public class Knight extends Piece{

    public Knight(Player owner) {
        super(owner);
    }

    @Override
    public Move[] getPossibleMoves(Point startPoint){
        int x = startPoint.getX(),y = startPoint.getY();
        
        List<Move> moves = new ArrayList<>();
        
        if(x-1 >=0 && y-2>=0)
            moves.add(new Move(startPoint,new Point(x-1, y-2),Move.Direction.NONE));
        if(x+1 < 8 && y-2>=0)
            moves.add(new Move(startPoint,new Point(x+1, y-2),Move.Direction.NONE));
        if(x-2 >=0 && y-1>=0)
            moves.add(new Move(startPoint,new Point(x-2, y-1),Move.Direction.NONE));
        if(x+2 < 8 && y-1>=0)
            moves.add(new Move(startPoint,new Point(x+2, y-1),Move.Direction.NONE));
        if(x-2 >=0 && y+1<8)
            moves.add(new Move(startPoint,new Point(x-2, y+1),Move.Direction.NONE));
        if(x+2<8 && y+1<8)
            moves.add(new Move(startPoint,new Point(x+2, y+1),Move.Direction.NONE));
        if(x-1>=0 && y+2<8)
            moves.add(new Move(startPoint,new Point(x-1, y+2),Move.Direction.NONE));
        if(x+1 <8 && y+2<8)
            moves.add(new Move(startPoint,new Point(x+1, y+2),Move.Direction.NONE));
        
        Move[] possibleMoves = new Move[moves.size()];
        possibleMoves = moves.toArray(possibleMoves);
        return possibleMoves;
    }

    @Override
    public String toString() {
        return "Knight";
    }

}
