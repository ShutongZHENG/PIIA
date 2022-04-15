package com.example.playeroriginal;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.playeroriginal.jsonData.Video;
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
   @FXML
   private Button btn_cancel;

   public void ActionCancel(){

   }
   public void Login(){
       Scene scene = btn_login.getScene();
       PlayerOriginalController playerOriginalController =(PlayerOriginalController) scene.getUserData() ;
       String UserDataJson = PlayerOriginalController.readJsonFile(PlayerOriginalController.pathUsers);
       ArrayList<User> users = getListUserFromJson(UserDataJson);
       for (int i =0; i< users.size();i++){
           System.out.println(users.size());
           System.out.println("USername: "+username_connection.getText() +"  Pwds: "+ password_connection.getText());
           if (users.get(i).getUsername() == username_connection.getText() && users.get(i).getPasswords() == password_connection.getText()){
               PlayerOriginalController.loginSuccess =  true;
               playerOriginalController.username.setText(username_connection.getText());
           }

       }
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
