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
public class Rook extends Piece{

    public Rook(Player owner) {
        super(owner);
    }

    @Override
    public Move[] getDeplacements(int x, int y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
