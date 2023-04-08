package com.example.unamedgame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Label;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LBController extends Game {
    List<String> scores = new ArrayList<>();
    boolean swapped;
    int sort = 0;
    public String temp;
    public String tempu;
    @FXML
    public Label user1;
    @FXML
    public Label score1;
    @FXML
    public Label user2;
    @FXML
    public Label score2;
    @FXML
    public Label user3;
    @FXML
    public Label score3;
    @FXML
    public Label user4;
    @FXML
    public Label score4;
    @FXML
    public Label user5;
    @FXML
    public Label score5;
    @FXML
    public Label playeru;
    @FXML
    public Label scorep;

    public void leader(ActionEvent event) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("scorebord.csv"));
        scanner.useDelimiter(",");
        while (scanner.hasNext()) {
            scores.add(scanner.next());
        }
        //Bubble sort
            for (int i = 0; i < scores.size(); i++) {
                swapped = false;
                for (int j = scores.size() ; j > i ; j--) {
                    try {
                        if (Integer.parseInt(scores.get(j)) >= Integer.parseInt(scores.get(j - 2))) {
                            temp = (scores.get(j));
                            tempu = (scores.get(j - 1));
                            scores.set(j, scores.get(j - 2));
                            scores.set(j - 1, scores.get(j - 3));
                            scores.set(j - 2, (temp));
                            scores.set(j - 3, (tempu));
                            System.out.println(scores);
                            swapped = true;
                        }
                    } catch (Exception e) {
                    }

                }
                if (swapped==false){
                    break;
                }
            }






        if (user1 != null) {
            user1.setText(scores.get(2));
        }
        if (score1 != null) {
            score1.setText(scores.get(3));
        }
        if (user2 != null) {
            user2.setText(scores.get(4));
        }
        if (score2 != null) {
            score2.setText(scores.get(5));
        }
        if (user3 != null) {
            user3.setText(scores.get(6));
        }
        if (score3 != null) {
            score3.setText(scores.get(7));
        }
        if (user4 != null) {
            user4.setText(scores.get(8));
        }
        if (score4 != null) {
            score4.setText(scores.get(9));
        }
        if (user5 != null) {
            user5.setText(scores.get(10));
        }
        if (score5 != null) {
            score5.setText(scores.get(11));
        }
        LoginController log = new LoginController();


        if (log.username != null) {
            playeru.setText(log.username);
        } else {
            if (playeru != null) {
                playeru.setText("login to set a username");
            }
        }
        if (scorep != null) {
            scorep.setText(String.valueOf(points));
        }
    }
}
