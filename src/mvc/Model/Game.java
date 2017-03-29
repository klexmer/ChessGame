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
    private Player white;
    private Player black;
    private Board board;

    public Game(Player white, Player black) {
        this.white = white;
        this.black = black;
        this.board = new Board();
    }
    
    
}
