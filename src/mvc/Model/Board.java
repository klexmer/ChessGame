/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 *
 * @author p1401687
 */
public class Board extends Observable{
    private Piece[][] pieces;

    public Board() {
        this.pieces = new Piece[8][8];
    }
    
    public void initBoard(Player white,Player black){
        /* 
            Appeler constructeur des differentes pieces
                pieces[i][j] = new King(white);
        */
        pieces[0][0] = new Rook(black);
        pieces[0][1] = new Knight(black);
        pieces[0][2] = new Bishop(black);
        pieces[0][3] = new Queen(black);
        pieces[0][4] = new King(black);
        pieces[0][5] = new Bishop(black);
        pieces[0][6] = new Knight(black);
        pieces[0][7] = new Rook(black);
        for(int i =0;i<8;i++){
            pieces[1][i] = new Pawn(black);
        }
        for(int i =2;i<7;i++){
            for(int j =0;j<8;j++){
            pieces[i][j] = null;
            }
        }
        
        pieces[7][0] = new Rook(white);
        pieces[7][1] = new Knight(white);
        pieces[7][2] = new Bishop(white);
        pieces[7][4] = new Queen(white);
        pieces[7][3] = new King(white);
        pieces[7][5] = new Bishop(white);
        pieces[7][6] = new Knight(white);
        pieces[7][7] = new Rook(white);
        for(int i =0;i<8;i++){
            pieces[6][i] = new Pawn(white);
        }
        setChanged();
        notifyObservers();
    }
    
    public Piece getPiece(Point p){
        int x = p.getX(),y = p.getY();

        return pieces[x][y];
    }
    
    public Move[] getPossibleMoves(Point p){
        int x = p.getX(),y = p.getY();
        
        List<Move> moves = new ArrayList<>();
        for(Move m : this.pieces[x][y].getPossibleMoves(p)){
            boolean isPossible = true;
            //System.out.println(m);
            System.out.println("enemyNeeded : " + m.isEnemyNeeded() +" && " + pieces[m.getDestination().getX()][m.getDestination().getY()] == null);
            if(m.isEnemyNeeded() && pieces[m.getDestination().getX()][m.getDestination().getY()] == null){
                isPossible = false;
            }
            if(m.getIntermediatePoints() != null){
                for(Point i : m.getIntermediatePoints()){
                    //System.out.println(i);
                    if(pieces[i.getX()][i.getY()] != null){
                        isPossible = false;
                    }
                }
            }else{
                //System.out.println("Est null");
            }
            if(isPossible){
                if(pieces[m.getDestination().getX()][m.getDestination().getY()] == null){
                    moves.add(m);
                }else{
                    if(pieces[m.getDestination().getX()][m.getDestination().getY()].owner != pieces[x][y].owner){
                        moves.add(m);
                    }
                }
            }
        }
        Move[] possibleMoves = new Move[moves.size()];
        possibleMoves = moves.toArray(possibleMoves);
        return possibleMoves;
    }
    
    public void movePiece(Move m){
        this.pieces[m.getDestination().getX()][m.getDestination().getY()] = this.pieces[m.getStart().getX()][m.getStart().getY()];
        this.pieces[m.getStart().getX()][m.getStart().getY()] = null;
        setChanged();
        notifyObservers();
    }
}
