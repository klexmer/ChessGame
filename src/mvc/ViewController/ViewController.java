package mvc.ViewController;


import java.util.List;
import java.util.Map;
import mvc.Model.*;


import java.util.Observable;
import java.util.Observer;
import javafx.application.Application;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author freder
 */
public class ViewController extends Application {
    
    private Game game;
    private GridPane gridPaneBoard;
    private TilePane informationsTilePane;
    private Text playerTurnText;
    private Text gameStateText;
    private Stage mainWindow;
    /*
        Si la pièce est sélectionnée, le prochain clic déterminera la case
        destination de la pièce sélectionnée.
        Sinon, il devra en sélectrionner une.
    */
    private Point selectedPoint = null;
    private int selectedPieceIndex = -1;
    
    @Override
    public void start(Stage primaryStage) {
        mainWindow = primaryStage;
        // Initialisation de la partie
        game = new Game();
        // Initialisation de la grille
        gridPaneBoard = new GridPane();
        
        //Observeur s'adaptant aux mises à jour du plateau
        game.getBoard().addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                updateGridPane();
                if(selectedPieceIndex != -1)
                unselectPiece();
            }
        });
        
        //Observeur regardant les informations du jeu
        game.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                changePlayerTurn();              
                if(game.getState() == "check"){
                    gameStateText.setText("Echec");
                    informationsTilePane.getChildren().remove(1);
                    informationsTilePane.getChildren().add(1, gameStateText);
                }
                else if(game.getState() == "normal"){
                    gameStateText.setText("");
                    informationsTilePane.getChildren().remove(1);
                    informationsTilePane.getChildren().add(1, gameStateText);
                }
                else{
                    showEndOfGameDialog();
                }
            }
        });
        
        updateGridPane();
        
        BorderPane border = new BorderPane();
        border.setCenter(gridPaneBoard);
        
        playerTurnText = new Text("Le tour est aux blancs");
        playerTurnText.setFont(Font.font ("Verdana", 20));
        informationsTilePane = new TilePane();
        informationsTilePane.getChildren().add(0, playerTurnText);
        informationsTilePane.setVgap(30);
        informationsTilePane.setPrefSize(250, 80);
        informationsTilePane.setPadding(new Insets(10, 10, 10, 10));
        informationsTilePane.setTileAlignment(Pos.TOP_CENTER);
        
        gameStateText = new Text("");
        gameStateText.setFont(Font.font ("Verdana", 20));
        informationsTilePane.getChildren().add(1, gameStateText);
        
        Text surrenderText = new Text("Déclarer forfait");
        surrenderText.setFont(Font.font ("Verdana", 15));
        HBox backgroundSurrenderHBox = new HBox();
        backgroundSurrenderHBox.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                game.endGame(true);
            }
        });
        backgroundSurrenderHBox.setAlignment(Pos.CENTER);
        backgroundSurrenderHBox.setBackground(Background.EMPTY);
        backgroundSurrenderHBox.setStyle("-fx-background-color: white");
        backgroundSurrenderHBox.getChildren().add(surrenderText);
        informationsTilePane.getChildren().add(2, backgroundSurrenderHBox);
        
        //javafx.scene.control.Button surrenderButton = new javafx.scene.control.Button();
        //informationsTilePane.getChildren().add(0, turnDisplay);
        
        border.setRight(informationsTilePane);
        
        Scene scene = new Scene(border, Color.LIGHTBLUE);
        
        mainWindow.setTitle("Jeu d'échec");
        mainWindow.getIcons().add(new Image(getClass().getResource("/resources/chessIcon.png").toString()));
        mainWindow.setScene(scene);
        mainWindow.show();
    }
    
    public void selectPiece(int x, int y){
        Piece p = game.getBoard().getPiece(new Point(x, y));
        if(p != null && p.getOwner() == game.getActivePlayer()){
            selectedPoint = new Point(x, y);
            selectedPieceIndex = x * 8 + y;
            SubScene box = (SubScene)gridPaneBoard.getChildren().get(selectedPieceIndex);
            box.setFill(Color.LIGHTBLUE);
            //Colorier les cases où la pièce peut aller
            changeColorOfPossibleDestinations(true, selectedPoint);
        }
    }
    
    public void unselectPiece(){
        SubScene box = (SubScene)gridPaneBoard.getChildren().get(selectedPieceIndex);
        Point p = new Point(selectedPieceIndex / 8, selectedPieceIndex % 8);
        uncolorBox(box, p);
        
        changeColorOfPossibleDestinations(false, p);
        selectedPoint = null;
        selectedPieceIndex = -1;
    }
    
    public void changeColorOfPossibleDestinations(boolean toggle, Point p){
        SubScene boxDest;
        if(toggle == true){
            int index;
            List<Map.Entry<Move, Boolean>> moves = game.getMoves(selectedPoint);
            Point destination;
            for(Map.Entry<Move, Boolean> m : moves){
                destination = new Point(m.getKey().getDestination().getX(),
                        m.getKey().getDestination().getY());
                index = (destination.getX() * 8) + destination.getY();
                boxDest = (SubScene)gridPaneBoard.getChildren().get(index);
                if(m.getValue() == true)
                    boxDest.setFill(Color.LIGHTGREEN);
                else
                    boxDest.setFill(Color.LIGHTSALMON);
            }
        }
        else{
            for(int i = 0; i < 64; i++){
                boxDest = (SubScene)gridPaneBoard.getChildren().get(i);
                uncolorBox(boxDest, new Point(i / 8, i % 8));
            }
        }
    }
    
    public void updateGridPane(){
        // Placement des pièces et création de leurs contrôleurs
        int column = 0;
        int row = 0;
        Piece p;
        SubScene box;
        BorderPane boxContent;
        ImageView pieceImg;
        String imagePath;
        if(gridPaneBoard.getChildren().size() > 0){
            gridPaneBoard.getChildren().clear();
        }
        for (int i = 0; i < 64; i++) {
            imagePath = "";
            gridPaneBoard.add(new SubScene(new BorderPane(), 50.0, 50.0),
                    column,
                    row);
            box = (SubScene)gridPaneBoard.getChildren().get(row * 8 + column);
            
            if((column + row) % 2 == 0)
                box.setFill(Color.BEIGE);
            else
                box.setFill(Color.SIENNA);
            if(game.getBoard().getPiece(new Point(row, column)) != null){
                p = game.getBoard().getPiece(new Point(row, column));
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
                pieceImg.setImage(new Image(getClass().getResource(
                                          imagePath).toString()));
                pieceImg.setFitHeight(50);
                pieceImg.setFitWidth(50);
                boxContent.setCenter(pieceImg);
            }
            else{
                boxContent = (BorderPane)box.getRoot();
                pieceImg = new ImageView();
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
                    if(selectedPoint == null){
                        selectPiece(x, y);
                    }
                    else{
                        /*Une pièce a été auparavant sélectionnée
                        * Si la nouvelle case sélectionnée est une destination
                        * valide pour la pièce sélectionnée, on la déplace à cet
                        * endroit. Sinon, on déselectionne la pièce.*/
                        //Vérification de la position de la case
                        Point startPoint = new Point(selectedPieceIndex / 8, selectedPieceIndex % 8);
                        List<Map.Entry<Move, Boolean>> moves = game.getMoves(selectedPoint);
                        Point destination;
                        int index;
                        int indexMoves = 0;
                        Map.Entry<Move, Boolean> tempMove = null;
                        boolean isAPossibleMove = false;
                        if(moves.size() != 0){
                            do{
                                tempMove = moves.get(indexMoves);
                                destination = new Point(tempMove.getKey().getDestination().getX(),
                                        tempMove.getKey().getDestination().getY());
                                index = destination.getX() * 8 + destination.getY();
                                if(x == destination.getX() && y == destination.getY()){
                                    isAPossibleMove = true;
                                }
                                indexMoves++;
                            }while(indexMoves < moves.size() && !isAPossibleMove);
                        }
                        if(isAPossibleMove){
                            //La case est un mouvement possible, mais il faut
                            //vérifier que cela ne provoque pas d'échec
                            if(tempMove.getValue() == true){
                                Point destinationPoint = new Point(x, y);
                                Move move = new Move(startPoint, destinationPoint,
                                        Move.Direction.NONE);
                                game.getBoard().movePiece(move, false);
                                game.nextPlayerTurn();
                            }
                        }
                        else{
                            //Déselection de la pièce
                            unselectPiece();
                        }
                    }
                }  
            });
        }
    }
    
    public void uncolorBox(SubScene box, Point p){
        if((p.getX() + p.getY()) % 2 == 0)
            box.setFill(Color.BEIGE);
        else
            box.setFill(Color.SIENNA);
    }
    
    public void changePlayerTurn(){
        String joueurActuel;
        if(game.getActivePlayer().isWhite())
            joueurActuel = "blancs";
        else
            joueurActuel = "noirs  ";
        playerTurnText.setText("Le tour est aux " + joueurActuel);
        informationsTilePane.getChildren().remove(0);
        informationsTilePane.getChildren().add(0, playerTurnText);
    }
    
    public void showEndOfGameDialog(){
        if(game.getState() == "checkmate")
            gameStateText.setText("Echec et mat");
        else
            gameStateText.setText("Abandon");
        informationsTilePane.getChildren().remove(1);
        informationsTilePane.getChildren().add(1, gameStateText);
        final Stage dialog = new Stage(StageStyle.TRANSPARENT);
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(mainWindow);
        TilePane dialogPane = new TilePane();
        dialogPane.setVgap(30);
        dialogPane.setPrefSize(250, 80);
        dialogPane.setPadding(new Insets(10, 10, 10, 10));
        dialogPane.setTileAlignment(Pos.TOP_CENTER);
        //Annonce de la fin de la partie
        String result = new String("Le joueur ");
        if(game.getActivePlayer().isWhite())
            result += "blanc";
        else
            result += "noir";
        result += " a ";
        if(game.getState() == "checkmate")
            result += "perdu";
        else
            result += "déclaré forfait";
        result += ". Que voulez-vous faire ?";
        Text informationText = new Text(result);
        Text restartGameText = new Text("Rejouer");
        Text quitGameText = new Text("Quitter le jeu");
        HBox backgroundRestartHBox = new HBox();
        backgroundRestartHBox.setAlignment(Pos.CENTER);
        backgroundRestartHBox.setStyle("-fx-background-color: white");
        backgroundRestartHBox.getChildren().add(restartGameText);
        backgroundRestartHBox.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                game.restartGame();
                gameStateText.setText("");
                informationsTilePane.getChildren().remove(1);
                informationsTilePane.getChildren().add(1, gameStateText);
                dialog.close();
            }
        });
        HBox backgroundQuitHBox = new HBox();
        backgroundQuitHBox.setAlignment(Pos.CENTER);
        backgroundQuitHBox.setStyle("-fx-background-color: white");
        backgroundQuitHBox.getChildren().add(quitGameText);
        backgroundQuitHBox.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                dialog.close();
                mainWindow.close();
            }
        });
        dialogPane.getChildren().add(informationText);
        dialogPane.getChildren().add(backgroundRestartHBox);
        dialogPane.getChildren().add(backgroundQuitHBox);
        Scene scene = new Scene(dialogPane);
        scene.setFill(Color.LIGHTGREY);
        dialog.setScene(scene);
        dialog.setTitle("Jeu d'échec");
        dialog.showAndWait();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
