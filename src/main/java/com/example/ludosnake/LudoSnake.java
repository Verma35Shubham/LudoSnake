package com.example.ludosnake;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class LudoSnake extends Application {

    Group titleGroup = new Group();

    public static final int tileSize = 40;

    int height = 10;
    int width = 10;

    int yLine = 430;
    int xLine = 40;
    int diceValue = 1;

    Label randResult;
    Button startButton;

    Player playerOne, playerTwo;
    boolean gameStart = false, playerOneTurn = true, playerTwoTurn = false;
    public Pane createContenet(){

        Pane root = new Pane();
        root.setPrefSize(width*tileSize, height*tileSize+80);
        root.getChildren().addAll(titleGroup);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Tile tile = new Tile(tileSize, tileSize);
                tile.setTranslateX(j*tileSize);
                tile.setTranslateY(i*tileSize);
                titleGroup.getChildren().addAll(tile);
            }
        }


        playerOne = new Player(tileSize, Color.WHITE);
        playerTwo = new Player(tileSize, Color.BLACK);

        randResult = new Label("Game not Started");
        randResult.setTranslateX(150);
        randResult.setTranslateY(410);

        Button player1Button = new Button("First Player");
        player1Button.setTranslateX(10);
        player1Button.setTranslateY(yLine);
        player1Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (gameStart){
                    if(playerOneTurn){
                        getDiceValue();
                        randResult.setText("First Player - " + String.valueOf(diceValue));
                        playerOne.movePlayer(diceValue);
                        playerOneTurn = false;
                        playerTwoTurn = true;
//                        playerOne.playerAtSnakeOrLadder();
                        gameOver();
                    }
                }
            }
        });

        Button player2Button = new Button("Second Player");
        player2Button.setTranslateX(300);
        player2Button.setTranslateY(yLine);
        player2Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (gameStart){
                    if(playerTwoTurn){
                        getDiceValue();
                        randResult.setText("Second Player - " + String.valueOf(diceValue));
                        playerTwo.movePlayer(diceValue);
                        playerOneTurn = true;
                        playerTwoTurn = false;
//                        playerOne.playerAtSnakeOrLadder();
                        gameOver();
                    }
                }
            }
        });

        startButton = new Button(" Start Game");
        startButton.setTranslateX(150);
        startButton.setTranslateY(yLine);
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                randResult.setText("Started");
                gameStart = true;
                startButton.setText("Game Going");
            }
        });

        Image img = new Image("C:\\Users\\hp\\IdeaProjects\\LudoSnake\\src\\snakeLadder1.jpg");

        ImageView boardImage = new ImageView();
        boardImage.setImage(img);
        boardImage.setFitHeight(tileSize*height);
        boardImage.setFitWidth(tileSize*width);

        titleGroup.getChildren().addAll(player1Button, player2Button, startButton,boardImage, randResult, playerOne.getGamePiece(), playerTwo.getGamePiece());
        return root;
    }

    void gameOver(){
        if (playerOne.getWinningStatus() == true){
            randResult.setText("First Player Won Game");
            startButton.setText("Start Again");
            gameStart = false;
        } else if (playerTwo.getWinningStatus() == true) {
            randResult.setText("Second Player Won Game");
            startButton.setText("Start Again");
            gameStart = false;
        }
    }

    private void getDiceValue() {
        diceValue = (int)(Math.random()*6+1);
    }

    @Override
    public void start(Stage stage) throws IOException {

        Scene scene = new Scene(createContenet());
        stage.setTitle("Ludo Sanke Game");
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                long currentTime = System.currentTimeMillis();
                long dt = currentTime - Player.lastMovementTime;
//                System.out.printf(currentTime + " " + Player.lastMovementTime + " " + dt);
                if (dt > 1e3){
                    Player.lastMovementTime = currentTime;
//                    System.out.printf(currentTime + " " + Player.lastMovementTime);
                    playerOne.playerAtSnakeOrLadder();
                    playerTwo.playerAtSnakeOrLadder();
                }
            }
        };
        timer.start();
    }

    public static void main(String[] args) {
        launch();
    }
}