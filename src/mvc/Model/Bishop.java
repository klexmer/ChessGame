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
public class Bishop extends Piece{

    public Bishop(Player owner) {
        super(owner);
    }

    @Override
    public Move[] getPossibleMoves(Point p){
        int x = p.getX(),y = p.getY();
        List<Move> moves = new ArrayList<>();
        
        for(int i = 1 ; i < 8 ; i++){
            if(x-i >=0 && y-i>=0)
                    moves.add(new Move(new Point(x, y),new Point(x-i, y-i),Move.Direction.LEFT_UP_DIAGONAL));
            if(x-i >=0 && y+i<8)
                    moves.add(new Move(new Point(x, y),new Point(x-i, y+i),Move.Direction.RIGHT_UP_DIAGONAL));
            if(x+i <8 && y-i>=0)
                    moves.add(new Move(new Point(x, y),new Point(x+i, y-i),Move.Direction.LEFT_DOWN_DIAGONAL));
            if(x+i <8 && y+i<8)
                    moves.add(new Move(new Point(x, y),new Point(x+i, y+i),Move.Direction.RIGHT_DOWN_DIAGONAL));
        }

        Move[] possibleMoves = new Move[moves.size()];
        possibleMoves = moves.toArray(possibleMoves);
        return possibleMoves;
    }

    
}
