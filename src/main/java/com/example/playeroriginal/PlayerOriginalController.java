package com.example.playeroriginal;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class PlayerOriginalController {
    @FXML
    private Label timeNow;
    @FXML
    private Label timeSave;
    @FXML
    private Label username;
    @FXML
    private MenuItem menuItem_quit;


    public void initialize() {
        timeNow.setText("-:-");
        timeSave.setText("-:-");
        username.setText("aaaabbbbcccc");
    }

    public void actionQuit(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void actionManage(ActionEvent actionEvent) {
    }

    public void actionAbout(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("About Player Original");
      //`  Image image= new Image("/Users/shutong/IdeaProjects/playerOriginal/src/main/resources/icon/logo_com.png");


        alert.showAndWait();
    }

    public void actionPlayList(ActionEvent actionEvent) {
    }

    public void actionStart(ActionEvent actionEvent) {
    }

    public void actionStop(ActionEvent actionEvent) {
    }

    public void actionSkipPre(ActionEvent actionEvent) {
    }

    public void actionSkipNext(ActionEvent actionEvent) {
    }

    public void actionVolumeUp(ActionEvent actionEvent) {
    }

    public void actionVolumeDown(ActionEvent actionEvent) {
    }

    public void actionMute(ActionEvent actionEvent) {
    }

    public void actionPreferences(ActionEvent actionEvent) {
    }

    public void actionAccount(ActionEvent actionEvent) {
    }

    public void actionPlayerHelp(ActionEvent actionEvent) {
    }

    public void actionOpen(ActionEvent actionEvent) {
    }

    public void actionPlay(MouseEvent mouseEvent) {
    }
}
