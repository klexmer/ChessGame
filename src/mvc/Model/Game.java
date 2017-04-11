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
    
    private String state;
    private Player white;
    private Player black;
    private Board board;
    private Player activePlayer;
    public Game() {
        this.state = "normal";
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
    
    public Player getEnemyPlayer(){
        if(getActivePlayer().isWhite())
            return black;
        else
            return white;
    }
    
    public boolean playGame(){
        boolean possibleMoves = true;
        while(possibleMoves){
            
        }
        return false;
    }

    public void endGame(boolean hasSurrendered) {
        if(hasSurrendered)
            state = "surrender";
        else
            state = "checkmate";
        setChanged();
        notifyObservers();
    }

    public String getState() {
        return state;
    }

    public void restartGame() {
        this.state = "normal";
        activePlayer = white;
        board.initBoard(white, black);
        setChanged();
        notifyObservers();
    }
    
    public void checkCheck(boolean isATest){
        this.state = "normal";
        Point tempPoint;
        Piece tempPiece;
        Player enemyPlayer = getEnemyPlayer();
        int row = 0, column = 0;
        int x2, y2, y3;
        Point kingPosition = getBoard().getPiecePosition("King", getActivePlayer());
        if(!isATest){
            //Cas où tous les coups mènent à l'échec
            /*int countNbPossibleMoves = 0;
            for(int i = 0; i < 64; i++){
                tempPoint = new Point(row, column);
                tempPiece = getBoard().getPiece(tempPoint);
                if(tempPiece != null && tempPiece.getOwner() == getActivePlayer()){
                    for(Move m : getBoard().getPossibleMoves(tempPoint)){
                        System.out.println("oh yeaee");
                        if(this.checkIfMovePossible(m) == true){
                            countNbPossibleMoves++;
                            System.out.println("oh yea");
                        }
                    }
                }
                if (column > 7) {
                column = 0;
                row++;
                }
            }
            if(countNbPossibleMoves == 0){
                this.state = "checkmate";
                setChanged();
                notifyObservers();
                return;
            }*/
        }
        row = 0; column = 0;
        int x = kingPosition.getX();
        int y = kingPosition.getY();
        for(int i = 0; i < 64; i++){
            tempPoint = new Point(row, column);
            tempPiece = getBoard().getPiece(tempPoint);
            if(tempPiece != null && tempPiece.getOwner() == enemyPlayer){
                //Les pions agissent différemment que les autres pièces pour
                //capturer des pièces ennemies. Nous les traitons séparément.
                if(tempPiece.toString() != "Pawn"){
                    for(Move p : getBoard().getPossibleMoves(tempPoint)){
                        x2 = p.getDestination().getX();
                        y2 = p.getDestination().getY();
                        if(x == x2 && y == y2){
                            this.state = "check";
                        }
                    }
                }
                else{
                    if(enemyPlayer == black){
                        x2 = row + 1;
                    }
                    else{
                        x2 = row - 1;
                    }
                    y2 = column - 1;
                    y3 = column + 1;
                    if(x == x2 && y == y2){
                            this.state = "check";
                    }
                    else if(x == x2 && y == y3){
                            this.state = "check";
                    }
                }
            }
            column++;
            if (column > 7) {
                column = 0;
                row++;
            }
        }
        if(!isATest)
            setChanged();
            notifyObservers();
    }

    public boolean checkCheckmate(){
        //Tableau de booléens représentant la région accessible par le roi.
        //Une case jouable est représentée par true.
        boolean[][] gridKingMoves = new boolean[3][3];
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                gridKingMoves[i][j] = false;
            }
        }
        //On sait que la case où se trouve le roi provoque un échec
        gridKingMoves[1][1] = false;
        //Regarder où le roi peut se déplacer
        Point kingPosition = getBoard().getPiecePosition("King", getActivePlayer());
        int x = kingPosition.getX();
        int y = kingPosition.getY();
        int x2, bx, y2, by, y3;
        for(Move p : getBoard().getPossibleMoves(kingPosition)){
            x = kingPosition.getX();
            x2 = p.getDestination().getX();
            y = kingPosition.getY();
            y2 = p.getDestination().getY();
            bx = x2 - (x - 1);
            by = y2 - (y - 1);
            if(bx >= 0 && bx < 3 && by >= 0 && by < 3){
                gridKingMoves[bx][by] = true;
            }
        }
        //Vérifier pour toutes les pièces adverses les coups qu'elles ne permettent
        //pas au roi
        int row = 0, column = 0;
        Piece tempPiece;
        Point tempPoint;
        Player enemyPlayer = getEnemyPlayer();
        for(int i = 0; i < 64; i++){
            tempPoint = new Point(row, column);
            tempPiece = getBoard().getPiece(tempPoint);
            if(tempPiece != null && tempPiece.getOwner() == enemyPlayer){
                //Les pions agissent différemment que les autres pièces pour
                //capturer des pièces ennemies. Nous les traitons séparément.
                if(tempPiece.toString() != "Pawn"){
                    for(Move p : getBoard().getPossibleMoves(tempPoint)){
                        x2 = p.getDestination().getX();
                        y2 = p.getDestination().getY();
                        bx = x2 - (x - 1);
                        by = y2 - (y - 1);
                        if(bx >= 0 && bx < 3 && by >= 0 && by < 3){
                            gridKingMoves[bx][by] = false;
                        }
                    }
                }
                else{
                    if(enemyPlayer == black){
                        x2 = row + 1;
                    }
                    else{
                        x2 = row - 1;
                    }
                    y2 = column - 1;
                    y3 = column + 1;
                    bx = x2 - (x - 1);
                    by = y2 - (y - 1);
                    if(bx >= 0 && bx < 3 && by >= 0 && by < 3){
                        gridKingMoves[bx][by] = false;
                    }
                    bx = x2 - (x - 1);
                    by = y3 - (y - 1);
                    if(bx >= 0 && bx < 3 && by >= 0 && by < 3){
                        gridKingMoves[bx][by] = false;
                    }
                }
            }
            column++;
            if (column > 7) {
                column = 0;
                row++;
            }
        }
        boolean isCheckmate = true;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(gridKingMoves[i][j] == true)
                    isCheckmate = false;
            }
        }
        return isCheckmate;
    }
    
    public void movePiece(Move m, boolean isATest){
        getBoard().movePiece(m, isATest);
        checkCheck(isATest);
        if(!isATest){
            this.state = "normal";
        }
    }
    
    public boolean checkIfMovePossible(Move m){
        Board oldBoard = getBoard();
        board = (Board) getBoard().clone();
        String oldState = state;
        boolean isMovePossible;
        this.movePiece(m, true);
        if(state == "check" || state == "checkmate")
            isMovePossible = false;
        else
            isMovePossible = true;
        this.board = oldBoard;
        this.state = oldState;
        return isMovePossible;
    }
}
