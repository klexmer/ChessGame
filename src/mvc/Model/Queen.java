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
public class Queen extends Piece{

    public Queen(Player owner) {
        super(owner);
    }

    @Override
    public Move[] getDeplacements(int x, int y) {
        List<Move> moves = new ArrayList<>();
        
        for(int i = 1 ; i < 8 ; i++){
            if(x-i >0 && y-i>0)
                    moves.add(new Move(new Point(x, y),new Point(x-i, y-i)));
            if(x-i >0)
                    moves.add(new Move(new Point(x, y),new Point(x-i, y)));
            if(x-i >0 && y+i<8)
                    moves.add(new Move(new Point(x, y),new Point(x-i, y+i)));
            if(y-i>0)
                    moves.add(new Move(new Point(x, y),new Point(x, y-i)));
            if(y+i<8)
                    moves.add(new Move(new Point(x, y),new Point(x, y+i)));
            if(x+i <8 && y-i>0)
                    moves.add(new Move(new Point(x, y),new Point(x+i, y-i)));
            if(x+i <8)
                    moves.add(new Move(new Point(x, y),new Point(x+i, y)));
            if(x+i <8 && y+i<8)
                    moves.add(new Move(new Point(x, y),new Point(x+i, y+i)));
        }

        Move[] possibleMoves = new Move[moves.size()];
        possibleMoves = moves.toArray(possibleMoves);
        return possibleMoves;
    }

}
