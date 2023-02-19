package com.example.unamedgame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class LoginController {
    @FXML
    private Label fail;
    @FXML
    private Label SignUP;
    @FXML
    private TextField user;
    @FXML
    private PasswordField pass;
    public String username;
    public String password;

    private static File logons = new File("logins.txt");

    public void signUp(MouseEvent action) {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("signUp-view.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setFullScreen(true);
            stage.setResizable(false);

            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //logs player in if conditions are met
    public void login(ActionEvent event) throws Exception {
        Scanner myReader = null;
        try {
            myReader = new Scanner(logons);
            signUpController hasher = new signUpController();
            username = (user.getText());
            //hashes password imput and compares it to the already hashed stored password
            password = (hasher.hash(pass.getText()));
            String account = username+ " " +password;
            String check = ("");
            boolean valid = (false);
            while (myReader.hasNextLine() && (!valid)) {
                if (check.equals(account)) {
                    valid = (true);
                    Game g = new Game();
                    g.load();
                }
                else {
                    check = myReader.nextLine();
                    if (!myReader.hasNextLine()) {
                        fail.setText("incorrect username or password");
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void highlight(MouseEvent action) {
        SignUP.setUnderline(true);
    }

    public void unhighlight(MouseEvent action) {
        SignUP.setUnderline(false);
    }


}
