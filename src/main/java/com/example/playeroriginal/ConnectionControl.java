package com.example.playeroriginal;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ConnectionControl extends Application {
    @FXML
    private TextField username_connection;
    @FXML
    private PasswordField password_connection;
    @FXML
    private Button btn_login;

    public void ActionCancel(){
        PlayerOriginalController.stage.close();
    }
    public void Login(){
        Scene scene = btn_login.getScene();
        PlayerOriginalController playerOriginalController =(PlayerOriginalController) scene.getUserData() ;
        String UserDataJson = PlayerOriginalController.readJsonFile(PlayerOriginalController.pathUsers);
        ArrayList<User> users = getListUserFromJson(UserDataJson);

        for (int i =0; i< users.size();i++){
            if (users.get(i).getUsername().equals(username_connection.getText() ) && users.get(i).getPasswords().equals(password_connection.getText())){

                playerOriginalController.username.setText(username_connection.getText());
                playerOriginalController.user = new User(username_connection.getText(),users.get(i).permission);
                playerOriginalController.loadPlayList();
                playerOriginalController.updateFonction();

            }

        }
        PlayerOriginalController.stage.close();
    }


    @Override
    public void start(Stage stage) throws Exception {

    }

    public ArrayList<User> getListUserFromJson(String str){
        List<User> users = new ArrayList<User>();
        users = JSON.parseObject(str, new TypeReference<ArrayList<User>>() {
        });
        return (ArrayList<User>) users;
    }


}
