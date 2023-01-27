package com.example.unamedgame;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;


public class Game extends Application {
    long toggle = 0;//variable used to allow abilities to be toggled
    long lastBlink = 0;

    //maps keycode to boolean - keycode is the javafx enumeration
    private HashMap<KeyCode, Boolean> keys = new HashMap<KeyCode, Boolean>();
    private ArrayList<Node> platforms = new ArrayList<>();
    private ArrayList<Node> goal = new ArrayList<>();
    private ArrayList<Node> kill = new ArrayList<>();

    private Pane appRoot = new Pane();
    private Pane gameRoot = new Pane();
    private Pane uiRoot = new Pane();

    private boolean death = false;
    private Node player;
    private Node box;
    private Point2D playerVelocity = new Point2D(0, 0);
    private boolean canJump = true;
    private boolean timeStop = false;
    private int levelWidth;

    private int levelno;
    private String line;

    private void levelcheck(int i){
        if (levelno == 1){
            line = LevelData.Level1[i];
        }
       if (levelno == 2){
            line = LevelData.Level2[i];
            box.setTranslateX(90);
            box.setTranslateY(950);

       }
        if (levelno == 3){
            line = LevelData.Level3[i];
            box.setTranslateX(900);
            box.setTranslateY(50);
        }
    }
    private void refresh(){
        for (Node platform : platforms) {
            gameRoot.getChildren().remove(platform);
        }
        for (Node win : goal) {
            gameRoot.getChildren().remove(win);
        }
        for (Node lose : kill) {
            gameRoot.getChildren().remove(lose);
        }
        platforms.clear();
        goal.clear();
        kill.clear();
        levelcreate();


    }
    private void levelcreate(){
        levelWidth = LevelData.Level1[0].length() * 60;
        for (int i = 0; i < LevelData.Level1.length; i++) {
            levelcheck(i);
            for (int j = 0; j < line.length(); j++) {
                switch (line.charAt(j)) {
                    case '0':
                        break;
                    case '1':
                        Node platform = createEntity(j * 60, i * 60, 60, 60, Color.GRAY);
                        platforms.add(platform);
                        break;
                    case '2':
                        Node win = createEntity(j * 60, i * 60, 60, 60, Color.GREEN);
                        goal.add(win);
                        break;
                    case '3':
                        Node lose = createEntity(j * 60, i * 60, 60, 60, Color.RED);
                        kill.add(lose);
                }
            }
        }
    }
    private void initContent() {
        levelno=1;
        Rectangle bg = new Rectangle(1920, 1080);
        levelcreate();
        player = createEntity(80, 950, 40, 40, Color.YELLOW);
        box = createEntity(900, 800, 40, 40, Color.DARKGRAY);
        appRoot.getChildren().addAll(bg, gameRoot, uiRoot);
    }



    private void update() {
        long blinkcd = 500; // 500 milliseconds
        long time = System.currentTimeMillis();
        if (isPressed(KeyCode.W) && player.getTranslateY() >= 5) {
            jumpPlayer();
        }
        if (isPressed(KeyCode.A) && player.getTranslateX() >= 5) {
            movePlayerX(-5);
        }
        if (isPressed(KeyCode.LEFT) && player.getTranslateX() >= 5) {
            if (time > lastBlink + blinkcd) {
                blink(-75);
                lastBlink = time;
            }
        }
        if (isPressed(KeyCode.RIGHT) && player.getTranslateX() >= 5) {
            if (time > lastBlink + blinkcd) {
                blink(75);
                lastBlink = time;
            }
        }
        if (isPressed(KeyCode.D) && player.getTranslateX() + 40 <= levelWidth - 5) {
            movePlayerX(5);
        }
        if (isPressed(KeyCode.H)) {
            if (time > toggle + 500) {
                if (timeStop == true){
                    timeStop=false;
                } else {timeStop=true;}
                toggle = time;
            }
        }
        if (playerVelocity.getY() < 10) {
            playerVelocity = playerVelocity.add(0, 1);
        }
        movePlayerY((int) playerVelocity.getY());
        MoveboxY();
        for (Node win : goal) {
            if (player.getBoundsInParent().intersects(win.getBoundsInParent())) {
                levelno = (levelno+1);
                refresh();
            }
        }
        for (Node lose : kill) {
            if (player.getBoundsInParent().intersects(lose.getBoundsInParent())) {
                death= true;
            }
        }
        for (Node platform : platforms) {
            if (player.getTranslateX() + 35 == box.getTranslateX() && (player.getTranslateY() <= box.getTranslateY() + 40) && ((player.getTranslateY() >= box.getTranslateY() - 40))) {
                MoveboxX(5);
            }
            if ((player.getTranslateX() == box.getTranslateX() + 35) && (player.getTranslateY() <= box.getTranslateY() + 40) && ((player.getTranslateY() >= box.getTranslateY() - 40))) {
                MoveboxX(-5);
            }
        }
        if (death==true) {System.exit(1);}
        if (player.getTranslateY()>=1080){death=true;}
    }


    private void movePlayerX(int value) {
        boolean movingRight = value > 0;
        for (int i = 0; i < Math.abs(value); i++) {
            for (Node platform : platforms) {
                if (player.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                    if (movingRight) {
                        if (player.getTranslateX() + 40 == platform.getTranslateX() && (player.getTranslateY() + 40 != platform.getTranslateY())) {
                            return;
                        }
                    } else {
                        if ((player.getTranslateX() == platform.getTranslateX() + 60) && (player.getTranslateY() + 40 != platform.getTranslateY())) {
                            return;
                        }
                    }
                }
            }
            player.setTranslateX(player.getTranslateX() + (movingRight ? 1 : -1));
        }
    }

    private void MoveboxX(int value) {
        boolean movingRight = value > 0;
        for (int i = 0; i < Math.abs(5); i++)
            box.setTranslateX(box.getTranslateX() + (movingRight ? 1 : -1));
    }

    private void blink(int value) {
        boolean movingRight = value > 0;
        for (int i = 0; i < Math.abs(value); i++) {
            for (Node platform : platforms) {
                if (player.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                    if (movingRight) {
                        if (player.getTranslateX() + 40 == platform.getTranslateX() && (player.getTranslateY() + 40 != platform.getTranslateY()) || ((player.getTranslateX() + 40 == box.getTranslateX()) && (player.getTranslateY() == box.getTranslateY()))) {
                            return;
                        }
                    } else {
                        if ((player.getTranslateX() == platform.getTranslateX() + 40) && (player.getTranslateY() + 40 != platform.getTranslateY()) || ((player.getTranslateX() - 40 == box.getTranslateX()) && (player.getTranslateY() == box.getTranslateY()))) {
                            return;
                        }
                    }
                }
            }
            player.setTranslateX(player.getTranslateX() + (movingRight ? 1 : -1));
        }
    }

    private void movePlayerY(int value) {
        boolean movingDown = value > 0;
        for (int i = 0; i < Math.abs(value); i++) {
            for (Node platform : platforms) {
                if (player.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                    if (movingDown) {
                        if (player.getTranslateY() + 40 == (platform.getTranslateY())) {
                            canJump = true;
                            return;
                        }
                    } else {
                        if (player.getTranslateY() == platform.getTranslateY() + 60) {
                            return;
                        }
                    }
                } else {
                    if (((player.getTranslateY() + 40 == (box.getTranslateY()) && (player.getTranslateX() - 36 <= box.getTranslateX()) && (player.getTranslateX() + 36 >= box.getTranslateX())))) {
                        player.setTranslateY(player.getTranslateY() - 300);
                        return;
                    }else {
                        if (((player.getTranslateY() - 40 == (box.getTranslateY()) && (player.getTranslateX() - 36 <= box.getTranslateX()) && (player.getTranslateX() + 36 >= box.getTranslateX()&&(player.getTranslateY() +40 !=platform.getTranslateY()))))) {
                            player.setTranslateY(player.getTranslateY() +1);
                           return;
                        }
                    }
                }
            }
            player.setTranslateY(player.getTranslateY() + (movingDown ? 1 : -1));
        }
    }

    private void MoveboxY() {
        if (timeStop == false) {
            for (int i = 0; i < (10); i++) {
                for (Node platform : platforms) {
                    if (box.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                        if (box.getTranslateY() + 40 == (platform.getTranslateY())) {
                            return;
                        }
                    }
                }
                box.setTranslateY(box.getTranslateY() + 1);
            }
        }
    }

    private void jumpPlayer() {
        if (canJump) {
            playerVelocity = playerVelocity.add(0, -30);
            canJump = false;
        }
    }

    private Node createEntity(int x, int y, int w, int h, Color color) {
        Rectangle entity = new Rectangle(w, h);
        entity.setTranslateX(x);
        entity.setTranslateY(y);
        entity.setFill(color);
        gameRoot.getChildren().add(entity);
        return entity;

    }

    private boolean isPressed(KeyCode key) {
        return keys.getOrDefault(key, false);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
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

    public static void main(String[] game) {
        launch(game);
    }
}
