/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.Model;

/**
 *
 * @author Loïc
 */
public class ModelTest {
    public static void main(String[] args){
        Player p = new Player(true);
        Queen rook = new Queen(null);
        for(Move m :rook.getMoves(new Point(2, 2))){
            System.out.println(m);
        }
    }
}