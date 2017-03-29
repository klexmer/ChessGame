package mvc.ViewController;


import mvc.Model.*;


import java.util.Observable;
import java.util.Observer;
import javafx.application.Application;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.Blend;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Shadow;

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
    
    @Override
    public void start(Stage primaryStage) {
        
        // Initialisation de la partie
        game = new Game();
        // Initialisation de la grille
        gridPaneBoard = new GridPane();
        // Placement des pièces et création de leurs contrôleurs
        int column = 0;
        int row = 0;
        Piece p;
        Text box;
        for (int i = 0; i < 64; i++) {
            /*if((column + row) % 2 == 0)
                t = new Text("B");
            else
                t = new Text("W");*/
            box = new Text();
            if(game.getBoard().getPiece(row, column) != null){
                p = game.getBoard().getPiece(row, column);
                switch(p.getClass().toString()){
                    case "Pawn":
                        box.setText("P");
                        break;
                    case "King":
                        box.setText("K");
                        break;
                    case "class mvc.Model.Queen":
                        box.setText("Q");
                        break;
                    case "Rook":
                        box.setText("R");
                        break;
                    case "Bishop":
                        box.setText("P");
                        break;
                    case "Knight":
                        box.setText("P");
                        break;
                }
                /*switch(p.getOwner().getColor()){
                    case "Black":
                        box.setText(box.getText() + "-B");
                        break;
                    case "White":
                        box.setText(box.getText() + "-W");
                        break;
                }*/
            }
            else{
                box.setText("");
            }
            box.setWrappingWidth(30);
            box.setFont(Font.font ("Verdana", 20));
            box.setTextAlignment(TextAlignment.CENTER);
            
            gridPaneBoard.add(box, column, row);
            
            
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
                    selectedPiece = game.getBoard().getPiece(x, y);
                    if(selectedPiece != null){
                        Text box = (Text)gridPaneBoard.getChildren().get(x * 8 + y);
                        box.setText(box.getText() + "-S");
                        //Colorier les cases où la pièce peut aller
                        Move moves[] = selectedPiece.getDeplacements(x, y);
                        int xDest, yDest;
                        Text boxDest;
                        for(Move m : moves){
                            xDest = m.getDestination().getX();
                            yDest = m.getDestination().getY();
                        }
                    }
                    else{
                        // ...
                    }
                }
                
            });
            
            
            
        }
        
        // gestion du placement (permet de palcer le champ Text affichage en haut, et GridPane gPane au centre)
        BorderPane border = new BorderPane();
        
        // permet de placer les diffrents boutons dans une grille
        
        
        /*affichage = new Text("");
        affichage.setFont(Font.font ("Verdana", 20));
        affichage.setFill(Color.RED);*/
        //border.setTop(affichage);
        
        // la vue observe les "update" du modèle, et réalise les mises à jour graphiques
        /*m.addObserver(new Observer() {
            
            @Override
            public void update(Observable o, Object arg) {
                if (!m.getErr()) {
                    affichage.setText(m.getLastChange() + "");
                    Text t = (Text)gPane.getChildren().get(m.getLastY()*8 + m.getLastX());
                    t.setText(m.getLastChange());
                } else {
                    affichage.setText("Err");
                }
            }
        });
        
        // on efface affichage lors du clic
        affichage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            
            @Override
            public void handle(MouseEvent event) {
                affichage.setText("");
            }
            
        });*/
        
       
        
        
        
        /*final Text t = new Text("=");
        t.setWrappingWidth(30);
        gPane.add(t, column++, row);
        t.setTextAlignment(TextAlignment.CENTER);
        //t.setEffect(new Shadow());
        
        // un controleur écoute le bouton "=" et déclenche l'appel du modèle
        t.setOnMouseClicked(new EventHandler<MouseEvent>() {
            
            @Override
            public void handle(MouseEvent event) {
                m.calc(affichage.getText());
            }
        });*/
        
        gridPaneBoard.setGridLinesVisible(true);
        
        border.setCenter(gridPaneBoard);
        
        Scene scene = new Scene(border, Color.LIGHTGREEN);
        
        primaryStage.setTitle("Calc FX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
