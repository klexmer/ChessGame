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
public class Board {
    private Piece[][] pieces;

    public Board() {
        this.pieces = new Piece[8][8];
    }
    
    public void initBoard(Player white,Player black){
        /* 
            Appeler constructeur des differentes pieces
                pieces[i][j] = new King(white);
        */
    }
    
    public Piece getPiece(int x,int y){
        return pieces[x][y];
    }
}
