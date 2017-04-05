package mvc.ViewController;


import mvc.Model.*;


import java.util.Observable;
import java.util.Observer;
import javafx.application.Application;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.effect.Blend;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author freder
 */
public class ViewController extends Application {
    
    private Game game;
    private GridPane gridPaneBoard;
    /*
        Si la pièce est sélectionnée, le prochain clic déterminera la case
        destination de la pièce sélectionnée.
        Sinon, il devra en sélectrionner une.
    */
    private Piece selectedPiece = null;
    private int selectedPieceIndex = -1;
    
    @Override
    public void start(Stage primaryStage) {
        
        // Initialisation de la partie
        game = new Game();
        // Initialisation de la grille
        gridPaneBoard = new GridPane();
        
        //Observeur s'adaptant aux mises à jour du plateau
        /*game.getBoard().addObserver()(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                
            }
        });*/
        
        // Placement des pièces et création de leurs contrôleurs
        int column = 0;
        int row = 0;
        Piece p;
        SubScene box;
        BorderPane boxContent;
        ImageView pieceImg;
        String imagePath;
        for (int i = 0; i < 64; i++) {
            imagePath = "";
            gridPaneBoard.add(new SubScene(new BorderPane(), 50.0, 50.0),
                    column,
                    row);
            box = (SubScene)gridPaneBoard.getChildren().get(row * 8 + column);
            
            if((column + row) % 2 == 0)
                box.setFill(Color.SIENNA);
            else
                box.setFill(Color.BEIGE);
            if(game.getBoard().getPiece(row, column) != null){
                p = game.getBoard().getPiece(row, column);
                //Couleur de la pièce
                if(p.getOwner().isWhite())
                    imagePath += "white_";
                else
                    imagePath += "black_";
                //Type de la pièce
                switch(p.getClass().toString()){
                    case "class mvc.Model.Pawn":
                        imagePath += "pawn";
                        break;
                    case "class mvc.Model.King":
                        imagePath += "king";
                        break;
                    case "class mvc.Model.Queen":
                        imagePath += "queen";
                        break;
                    case "class mvc.Model.Rook":
                        imagePath += "rook";
                        break;
                    case "class mvc.Model.Bishop":
                        imagePath += "bishop";
                        break;
                    case "class mvc.Model.Knight":
                        imagePath += "knight";
                        break;
                }
                boxContent = (BorderPane)box.getRoot();
                pieceImg = new ImageView();
                imagePath = "/resources/pieces/" + imagePath + ".png";
                System.out.println(imagePath);
                pieceImg.setImage(new Image(getClass().getResource(
                                          imagePath).toString()));
                pieceImg.setFitHeight(50);
                pieceImg.setFitWidth(50);
                boxContent.setCenter(pieceImg);

            }

            final int x = row;
            final int y = column;
            
            column++;
            if (column > 7) {
                column = 0;
                row++;
            }
            // Création d'un contrôleur par case
            box.setOnMouseClicked(new EventHandler<MouseEvent>() {                   
                @Override
                public void handle(MouseEvent event) {
                    if(selectedPiece == null){
                        selectPiece(x, y);
                    }
                    else{
                        /*Une pièce a été auparavant sélectionnée
                        * Si la nouvelle case sélectionnée est une destination
                        * valide pour la pièce sélectionnée, on la déplace à cet
                        * endroit. Sinon, on déselectionne la pièce.*/
                        //Vérification de la position de la case
                        int rowSelectedPiece = selectedPieceIndex / 8;
                        int columnSelectedPiece = selectedPieceIndex % 8;
                        Move moves[] = selectedPiece.getDeplacements(rowSelectedPiece, columnSelectedPiece);
                        int xDest, yDest, index;
                        int indexMoves = 0;
                        Move tempMove;
                        boolean isAPossibleMove = false;
                        do{
                            tempMove = moves[indexMoves];
                            xDest = tempMove.getDestination().getX();
                            yDest = tempMove.getDestination().getY();
                            index = xDest * 8 + yDest;
                            if(x == xDest && y == yDest){
                                isAPossibleMove = true;
                            }
                            indexMoves++;
                        }while(indexMoves <= moves.length && !isAPossibleMove);
                        if(isAPossibleMove){
                            //La case est un mouvement possible:
                            //déplacement de la pièce sur le plateau
                            Point startPoint = new Point(rowSelectedPiece, columnSelectedPiece);
                            Point destinationPoint = new Point(x, y);
                            System.out.println("x: " + x + "y: " + y);
                            Move move = new Move(startPoint, destinationPoint);
                            /*TODO : créer une fonction dans Board.java qui
                            déplace une pièce sur le plateau. Paramètres:
                            move : Move
                            */
                        }
                        else{
                            //Déselection de la pièce
                            unselectPiece();
                        }
                    }
                }  
            }); 
        }
        
        BorderPane border = new BorderPane();
        gridPaneBoard.setGridLinesVisible(true);
        border.setCenter(gridPaneBoard);
        
        Scene scene = new Scene(border, Color.LIGHTBLUE);
        
        primaryStage.setTitle("Jeu d'échec");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public void selectPiece(int x, int y){
        selectedPiece = game.getBoard().getPiece(x, y);
        if(selectedPiece != null){
            selectedPieceIndex = x * 8 + y;
            SubScene box = (SubScene)gridPaneBoard.getChildren().get(selectedPieceIndex);
            box.setFill(Color.LIGHTBLUE);
            //Colorier les cases où la pièce peut aller
            changeColorOfPossibleDestinations(true, x, y);
        }
    }
    
    public void unselectPiece(){
        SubScene box = (SubScene)gridPaneBoard.getChildren().get(selectedPieceIndex);
        int row = selectedPieceIndex / 8;
        int column = selectedPieceIndex % 8;
        uncolorBox(box, row, column);
        
        changeColorOfPossibleDestinations(false, row, column);
        selectedPiece = null;
        selectedPieceIndex = -1;
    }
    
    public void changeColorOfPossibleDestinations(boolean toggle, int x, int y){
        Move moves[] = selectedPiece.getDeplacements(x, y);
        int xDest, yDest, index;
        SubScene boxDest;
        for(Move m : moves){
            xDest = m.getDestination().getX();
            yDest = m.getDestination().getY();
            index = xDest * 8 + yDest;
            boxDest = (SubScene)gridPaneBoard.getChildren().get(index);
            if(toggle == true)
                boxDest.setFill(Color.LIGHTGREEN);
            else{
                uncolorBox(boxDest, xDest, yDest);
            }
        }
    }
    
    public void uncolorBox(SubScene box, int x, int y){
        if((x + y) % 2 == 0)
            box.setFill(Color.SIENNA);
        else
            box.setFill(Color.BEIGE);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
