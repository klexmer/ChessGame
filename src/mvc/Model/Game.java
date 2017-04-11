/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.Model;

import java.util.Observable;

/**
 *
 * @author p1401687
 */
public class Game extends Observable{
    public static final boolean WHITE = true;
    public static final boolean BLACK = false;
    
    private String endOfGame;
    private Player white;
    private Player black;
    private Board board;
    private Player activePlayer;
    public Game() {
        this.endOfGame = "false";
        this.white = new Player(WHITE);
        this.black = new Player(BLACK);
        this.board = new Board();
        activePlayer = white;
        board.initBoard(white, black);
    }

    public Board getBoard() {
        return board;
    }
    
    public void nextPlayerTurn(){
        if(activePlayer == white)
            activePlayer = black;
        else 
            activePlayer = white;
        setChanged();
        notifyObservers();
    }
    
    public Player getActivePlayer(){
        return activePlayer;
    }
    
    public boolean playGame(){
        boolean possibleMoves = true;
        while(possibleMoves){
            
        }
        return false;
    }

    public void endGame(boolean hasSurrendered) {
        if(hasSurrendered)
            endOfGame = "surrender";
        else
            endOfGame = "checkmate";
        setChanged();
        notifyObservers();
    }

    public String getEndOfGame() {
        return endOfGame;
    }

    public void restartGame() {
        this.endOfGame = "false";
        activePlayer = white;
        board.initBoard(white, black);
        setChanged();
        notifyObservers();
    }

    
}
