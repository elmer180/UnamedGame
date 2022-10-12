package com.example.unamedgame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class signUpController {
    @FXML
    private Label Login;
    @FXML
    private TextField user;
    @FXML
    private PasswordField pass;
    @FXML
    private PasswordField confirm;
    @FXML
    private Label fail;

    public static File logons = new File("logins.txt");

    public void login(MouseEvent action){
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Login-view.fxml"));
            Parent root2 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setFullScreen(true);
            stage.setResizable(false);

            stage.setScene(new Scene(root2));
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void signup(ActionEvent action){
        try {
            String username = user.getText();
            String password = pass.getText();
            String conf = confirm.getText();
            if (conf.equals(password)) {
                FileWriter myWriter = new FileWriter(logons.getName(), true);
                myWriter.write("\n"+username + " " + password+"\n,");
                myWriter.close();
                fail.setText("new account created");
            }
            else{
                fail.setText("both passwords do not match");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void highlight(MouseEvent action) {
        Login.setUnderline(true);
    }
    public void dehighlight(MouseEvent action){
        Login.setUnderline(false);
    }
}
