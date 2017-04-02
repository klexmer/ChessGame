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

    private boolean firstMove = true;
    
    public Pawn(Player owner) {
        super(owner);
    }

    @Override
    public Move[] getDeplacements(int x, int y) {
        List moves = new ArrayList();
        if(owner.isWhite()){
            
        }else{
            
        }
        
        return (Move[]) moves.toArray();
    }


    
}
