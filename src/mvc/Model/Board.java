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
        pieces[0][4] = new Queen(white);
        pieces[0][0] = new Rook(white);
    }
    
    public Piece getPiece(Point p){
        int x = p.getX(),y = p.getY();

        return pieces[x][y];
    }
    
    public Move[] getPossibleMoves(Point p){
        int x = p.getX(),y = p.getY();
        
        List<Move> moves = new ArrayList<>();
        System.out.println("x: " + x + ", y : " + y);
        for(Move m : this.pieces[x][y].getPossibleMoves(p)){
            if(pieces[m.getDestination().getX()][m.getDestination().getY()] == null){
                moves.add(m);
            }else{
                if(pieces[m.getDestination().getX()][m.getDestination().getY()].owner != pieces[x][y].owner){
                    moves.add(m);
                }
            }
        }
        Move[] possibleMoves = new Move[moves.size()];
        possibleMoves = moves.toArray(possibleMoves);
        return possibleMoves;
    }
}
