package com.example.unamedgame;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;



public class Main extends Application {
    long lastAttack = 0;

    //maps keycode to boolean - keycode is the javafx enumeration
    private HashMap<KeyCode, Boolean> keys = new HashMap<KeyCode, Boolean>();
    private ArrayList<Node> platforms = new ArrayList<>();
    private Pane appRoot = new Pane();
    private Pane gameRoot = new Pane();
    private Pane uiRoot = new Pane();

    private Node player;
    private Point2D playerVelocity = new Point2D(0, 0);
    private boolean canJump = true;
    private int levelWidth;
    private void initContent(){
        Rectangle bg = new Rectangle(1920,1080);
        levelWidth = LevelData.Level1[0].length()*60;

        for (int i=0; i< LevelData.Level1.length; i++){
            String line = LevelData.Level1[i];
            for (int j=0; j <line.length();j++){
                switch (line.charAt(j)){
                    case '0':
                        break;
                    case '1':
                        Node platform = createEntity(j*60, i *60, 60, 60, Color.GRAY);
                        platforms.add(platform);
                        break;
                }
            }
        }
        player = createEntity(80, 950, 40, 40, Color.BLUE);
        player.translateXProperty().addListener((obs, old, newValue) -> {
        });
        appRoot.getChildren().addAll(bg, gameRoot, uiRoot);
    }
    private void update(){

        long blinkcd = 500; // 2000 milliseconds
        long time = System.currentTimeMillis();
        if (isPressed(KeyCode.W) && player.getTranslateY() >= 5){
            jumpPlayer();
        }
        if (isPressed(KeyCode.A) && player.getTranslateX() >=5){
            movePlayerX(-5);
        }
        if (isPressed(KeyCode.LEFT) && player.getTranslateX() >=5){
            if (time > lastAttack + blinkcd) {
                dash(-75);
                lastAttack = time;
            }
        }
        if (isPressed(KeyCode.RIGHT) && player.getTranslateX() >=5){
            if (time > lastAttack + blinkcd) {
                dash(75);
                lastAttack = time;
            }
        }
        if (isPressed(KeyCode.D) && player.getTranslateX() + 40 <=levelWidth-5){
            movePlayerX(5);
        }
        if (playerVelocity.getY() < 10){
            playerVelocity = playerVelocity.add(0, 1);
        }
        movePlayerY((int)playerVelocity.getY());
    }


    private void movePlayerX(int value){
        boolean movingRight = value > 0;
        for (int i=0; i < Math.abs(value);i++){
            for (Node platform : platforms){
                if(player.getBoundsInParent().intersects(platform.getBoundsInParent())){
                    if(movingRight){
                        if (player.getTranslateX() + 40 == platform.getTranslateX()&&(player.getTranslateY()+40!=platform.getTranslateY())){
                            return;
                        }
                    }else {
                        if ((player.getTranslateX() == platform.getTranslateX() + 60)&&(player.getTranslateY()+40!=platform.getTranslateY())) {
                            return;
                        }
                    }
                }
            }
            player.setTranslateX(player.getTranslateX() + (movingRight ? 1 : -1));
        }
    }
    private void dash(int value){
        boolean movingRight = value > 0;
        for (int i=0; i < Math.abs(value);i++){
            for (Node platform : platforms){
                if(player.getBoundsInParent().intersects(platform.getBoundsInParent())){
                    if(movingRight){
                        if (player.getTranslateX() + 40 == platform.getTranslateX()&&(player.getTranslateY()+40!=platform.getTranslateY())){
                            return;
                        }
                    }else {
                        if ((player.getTranslateX() == platform.getTranslateX() + 60)&&(player.getTranslateY()+40!=platform.getTranslateY())) {
                            return;
                        }
                    }
                }
            }
            player.setTranslateX(player.getTranslateX() + (movingRight ? 1 : -1));
        }
    }
    private void movePlayerY(int value){
        boolean movingDown = value > 0;
        for (int i=0; i < Math.abs(value);i++){
            for (Node platform : platforms){
                if(player.getBoundsInParent().intersects(platform.getBoundsInParent())){
                    if(movingDown){
                        if (player.getTranslateY() + 40 == (platform.getTranslateY())){
                            canJump = true;
                            return;
                        }
                    }else {
                        if (player.getTranslateY() == platform.getTranslateY() + 60) {
                            return;
                        }
                    }
                }
            }
            player.setTranslateY(player.getTranslateY() + (movingDown ? 1 : -1));
        }
    }

    private void jumpPlayer(){
        if(canJump){
            playerVelocity = playerVelocity.add(0, -30);
            canJump = false;
        }
    }
    private Node createEntity(int x, int y, int w, int h, Color color){
        Rectangle entity = new Rectangle(w, h);
        entity.setTranslateX(x);
        entity.setTranslateY(y);
        entity.setFill(color);
        gameRoot.getChildren().add(entity);
        return entity;

    }
    private boolean isPressed(KeyCode key){
        return keys.getOrDefault(key, false);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        initContent();
        Scene scene = new Scene(appRoot);
        scene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
        scene.setOnKeyReleased(event -> keys.put(event.getCode(), false));
        primaryStage.setTitle("unamed game");
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        timer.start();
    }
    public static void main(String[] args) {

        launch(args);
    }
}
