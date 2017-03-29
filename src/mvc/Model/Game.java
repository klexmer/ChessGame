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
public class Game {
    public static final boolean WHITE = true;
    public static final boolean BLACK = false;
    
    
    private Player white;
    private Player black;
    private Board board;
    private Player activePlayer;
    public Game() {
        this.white = new Player(WHITE);
        this.black = new Player(BLACK);
        this.board = new Board();
        activePlayer = white;
    }

    public Board getBoard() {
        return board;
    }
    
    public void nextPlayerTurn(){
        if(activePlayer == white)
            activePlayer = black;
        else 
            activePlayer = white;
    }
    
    public Player getAcivePlayer(){
        return activePlayer;
    }
    
}
