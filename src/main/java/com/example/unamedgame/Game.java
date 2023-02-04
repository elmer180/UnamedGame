package com.example.unamedgame;

import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;


public class Game {
    long toggle = 0;//variable used to allow abilities to be toggled
    long lastBlink = 0;

    public long starttime = System.currentTimeMillis();
    public long points = 0;

    //maps keycode to boolean - keycode is the javafx enumeration
    private HashMap<KeyCode, Boolean> keys = new HashMap<KeyCode, Boolean>();
    private ArrayList<Node> platforms = new ArrayList<>();
    private ArrayList<Node> goal = new ArrayList<>();
    private ArrayList<Node> kill = new ArrayList<>();
    private ArrayList<Node> item = new ArrayList<>();
    private ArrayList<Node> button = new ArrayList<>();
    private ArrayList<Node> door = new ArrayList<>();
    private ArrayList<Node> door2 = new ArrayList<>();
    private ArrayList<Node> portal1 = new ArrayList<>();
    private ArrayList<Node> portal2 = new ArrayList<>();
    private Pane appRoot = new Pane();
    private Pane gameRoot = new Pane();
    private Pane uiRoot = new Pane();
    private Node player;
    private Node box;
    private Point2D playerVelocity = new Point2D(0, 0);
    private boolean canJump = true;
    private boolean timeStop = false;
    private boolean dooropen = false;
    private boolean walking = false;
    private boolean right = false;
    private int levelWidth;
    private int levelno;
    private String line;

    private void levelcheck(int i) {
        if (levelno == 0) {
            levelno=1;
            line = LevelData.Level1[i];
            player.setTranslateX(80);
            player.setTranslateY(980);
            box.setTranslateX(900);
            box.setTranslateY(800);
        }
        if (levelno == 1) {
            line = LevelData.Level1[i];
            try {
                player.setTranslateX(80);
                player.setTranslateY(980);
                box.setTranslateX(900);
                box.setTranslateY(800);
            }catch (Exception e){
            }
        }
        if (levelno == 2) {
            line = LevelData.Level2[i];
            player.setTranslateX(1805);
            player.setTranslateY(980);
            box.setTranslateX(120);
            box.setTranslateY(980);

        }
        if (levelno == 3) {
            line = LevelData.Level3[i];
            player.setTranslateX(90);
            player.setTranslateY(260);
            box.setTranslateX(900);
            box.setTranslateY(50);
        }
        if (levelno == 4) {
            line = LevelData.Level4[i];
            player.setTranslateX(1800);
            player.setTranslateY(980);
            box.setTranslateX(1305);
            box.setTranslateY(800);

        }
        if (levelno == 5) {
            line = LevelData.Level5[i];
            player.setTranslateX(90);
            player.setTranslateY(950);
            box.setTranslateX(600);
            box.setTranslateY(950);

        }
        if (levelno == 6) {
            line = LevelData.Level6[i];
            player.setTranslateX(90);
            player.setTranslateY(800);
            box.setTranslateX(200);
            box.setTranslateY(800);


        }
        if (levelno == 7) {
            line = LevelData.Level7[i];
            walking = true;
            player.setTranslateX(90);
            player.setTranslateY(740);
            box.setTranslateX(1080);
            box.setTranslateY(950);

        }
        if (levelno == 8) {
            line = LevelData.Level8[i];
            walking = false;
            player.setTranslateX(90);
            player.setTranslateY(950);
            box.setTranslateX(300);
            box.setTranslateY(950);
        }
        if (levelno == 9) {
            line = LevelData.Level9[i];
            walking = true;
            player.setTranslateX(370);
            player.setTranslateY(950);
            box.setTranslateX(930);
            box.setTranslateY(620);

        }
        if (levelno == 10) {
            line = LevelData.Level10[i];
            player.setTranslateX(370);
            player.setTranslateY(950);
            box.setTranslateX(1705);
            box.setTranslateY(140);

        }
        if (levelno == 11) {
            points= (points+((starttime-System.currentTimeMillis()+1000000)/10));
            System.out.println(points);

        }
    }

    private void refresh() {
        for (Node platform : platforms) {
            gameRoot.getChildren().remove(platform);
        }
        for (Node win : goal) {
            gameRoot.getChildren().remove(win);
        }
        for (Node lose : kill) {
            gameRoot.getChildren().remove(lose);
        }
        for (Node collect : item) {
            gameRoot.getChildren().remove(collect);
        }
        for (Node gate : door) {
            gameRoot.getChildren().remove(gate);
        }
        for (Node press : button) {
            gameRoot.getChildren().remove(press);
        }
        for (Node agate : door2) {
            gameRoot.getChildren().remove(agate);
        }
        for (Node portB : portal1) {
            gameRoot.getChildren().remove(portB);
        }
        for (Node portO : portal2) {
            gameRoot.getChildren().remove(portO);
        }
        right = false;
        button.clear();
        platforms.clear();
        goal.clear();
        item.clear();
        kill.clear();
        door.clear();
        door2.clear();
        portal1.clear();
        portal2.clear();
        levelcreate();


    }

    private void levelcreate() {
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
                        break;
                    case '4':
                        Node collect = createEntity(j * 60, i * 60, 50, 50, Color.GOLD);
                        item.add(collect);
                        break;
                    case '5':
                        Node press = createEntity(j * 60, i * 63, 60, 30, Color.DARKRED);
                        button.add(press);
                        break;
                    case '6':
                        Node gate = createEntity(j * 60, i * 60, 60, 60, Color.INDIANRED);
                        door.add(gate);
                        break;
                    case '7':
                        Node agate = createEntity(j * 60, i * 60, 60, 60, Color.INDIANRED);
                        door2.add(agate);
                        break;
                    case '8':
                        Node portB = createEntity(j * 60, i * 60, 60, 60, Color.DARKBLUE);
                        portal1.add(portB);
                        break;
                    case '9':
                        Node porto = createEntity(j * 60, i * 60, 60, 60, Color.ORANGE);
                        portal2.add(porto);
                        break;
                }
            }
        }
    }

    private void initContent() {
        levelno = 1;
        Rectangle bg = new Rectangle(1920, 1080);
        levelcreate();
        player = createEntity(80, 980, 40, 40, Color.YELLOW);
        box = createEntity(900, 800, 40, 40, Color.DARKGRAY);
        appRoot.getChildren().addAll(bg, gameRoot, uiRoot);
    }


    private void update() {


        long blinkcd = 500; // 500 milliseconds
        long time = System.currentTimeMillis();
        if (isPressed(KeyCode.F3)) {
            System.out.println(player.getTranslateX());
            System.out.println(player.getTranslateY());
        }
        if (isPressed(KeyCode.PAGE_UP)) {
            if (time > toggle + 1000) {
                levelno = (levelno + 1);
                refresh();
                toggle = time;
            }
        }
        if (isPressed(KeyCode.PAGE_DOWN)) {
            if (time > toggle + 1000) {
                levelno = (levelno - 1);
                refresh();
                toggle = time;
            }
        }
        if (isPressed(KeyCode.R)) {
            if (time > toggle + 100) {
                refresh();
                toggle = time;
            }
        }
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
            if (time > toggle + 300) {
                if (timeStop == true) {
                    timeStop = false;
                } else {
                    if (levelno != 9) {
                        timeStop = true;
                    }
                }
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
                levelno = (levelno + 1);
                refresh();
            }
        }
        for (Node lose : kill) {
            if (player.getBoundsInParent().intersects(lose.getBoundsInParent())) {
                refresh();
            }
        }
        for (Node platform : platforms) {
            if (player.getTranslateX() + 35 == box.getTranslateX() && (player.getTranslateY() <= box.getTranslateY() + 40) && ((player.getTranslateY() >= box.getTranslateY() - 40))) {
                if (walking == false) {
                    MoveboxX(5);
                } else {
                    player.setTranslateY(player.getTranslateY() - 5);
                }

            }
            if ((player.getTranslateX() == box.getTranslateX() + 35) && (player.getTranslateY() <= box.getTranslateY() + 40) && ((player.getTranslateY() >= box.getTranslateY() - 40))) {
                if (walking == false) {
                    MoveboxX(-5);
                } else {
                    player.setTranslateY(player.getTranslateY() - 5);
                }
            }
        }
        if (player.getTranslateY() >= 1080) {
            refresh();
        }
        for (Node press : button) {
            if (box.getBoundsInParent().intersects(press.getBoundsInParent()) || (player.getBoundsInParent().intersects(press.getBoundsInParent()))) {
                dooropen = true;
            } else {
                dooropen = false;
            }
        }
        for (Node gate : door) {
            if (dooropen == true) {
                gate.setVisible(false);
            } else {
                gate.setVisible(true);
            }
            if (player.getBoundsInParent().intersects(gate.getBoundsInParent()) && (dooropen == false)) {
                refresh();
            }
            for (Node agate : door2) {
                if (dooropen == false) {
                    agate.setVisible(false);
                } else {
                    agate.setVisible(true);
                }
                if (player.getBoundsInParent().intersects(agate.getBoundsInParent()) && (dooropen == true)) {
                    refresh();
                }
            }
        }
        for (Node portB : portal1) {
            if (box.getBoundsInParent().intersects(portB.getBoundsInParent())) {
                for (Node portO : portal2) {
                    box.setTranslateX(portO.getTranslateX() + 110);
                    box.setTranslateY(portO.getTranslateY());
                }
            }
            if (player.getBoundsInParent().intersects(portB.getBoundsInParent())) {
                for (Node portO : portal2) {
                    player.setTranslateX(portO.getTranslateX() + 65);
                    player.setTranslateY(portO.getTranslateY());
                }
            }

        }
        for (Node portO : portal2) {
            if (box.getBoundsInParent().intersects(portO.getBoundsInParent())) {
                for (Node portB : portal1) {
                    box.setTranslateX(portB.getTranslateX() - 110);
                    box.setTranslateY(portB.getTranslateY());
                }
            }
            if (player.getBoundsInParent().intersects(portO.getBoundsInParent())) {
                for (Node portB : portal1) {
                    player.setTranslateX(portB.getTranslateX() - 60);
                    player.setTranslateY(portB.getTranslateY());
                }
            }

        }

        for (Node collect:item){
            if(player.getBoundsInParent().intersects(collect.getBoundsInParent())){
                points= points+1000;
                gameRoot.getChildren().remove(collect);
                item.clear();
            }
        }
        if (walking == true) {
            for (Node platform : platforms) {
                if (box.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                    if (box.getTranslateX() + 40 == platform.getTranslateX() && (box.getTranslateY() + 40 != platform.getTranslateY())) {
                        right = false;
                    }
                    if (box.getTranslateX() - 60 == platform.getTranslateX() && (box.getTranslateY() + 40 != platform.getTranslateY())) {
                        right = true;
                    }
                }
            }
            if (timeStop == false) {
                if (right) {
                    MoveboxX(1);
                } else {
                    MoveboxX(-1);
                }
            }
        }
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
                        if ((player.getTranslateX() == platform.getTranslateX() + 60) && (player.getTranslateY() + 40 != platform.getTranslateY()) || ((player.getTranslateX() - 40 == box.getTranslateX()) && (player.getTranslateY() == box.getTranslateY()))) {
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
                    } else {
                        if (((player.getTranslateY() - 40 == (box.getTranslateY()) && (player.getTranslateX() - 36 <= box.getTranslateX()) && (player.getTranslateX() + 36 >= box.getTranslateX() && (player.getTranslateY() + 40 != platform.getTranslateY()))))) {
                            player.setTranslateY(player.getTranslateY() + 1);
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
    public void load(){
       try {
            initContent();
            Scene scene = new Scene(appRoot);
            scene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
            scene.setOnKeyReleased(event -> keys.put(event.getCode(), false));
            Stage stage = new Stage();
            stage.setFullScreen(true);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();

           AnimationTimer timer = new AnimationTimer() {
               @Override
               public void handle(long now) {
                   update();
               }
           };
           timer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
