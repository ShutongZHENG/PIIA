package com.example.playeroriginal;

import com.alibaba.fastjson.JSON;
import com.example.playeroriginal.jsonData.Video;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class VideoManagerController implements Initializable {

    @FXML
    private Button bt_ok;
    @FXML
    private Button bt_cancel;


    @FXML
    private TableColumn<VideoSimpleStringProperty, String> tableView_name;
    @FXML
    private TableColumn<VideoSimpleStringProperty, String> tableView_duration;
    @FXML
    private TableColumn<VideoSimpleStringProperty, String> tableView_type;
    @FXML
    private TableColumn<VideoSimpleStringProperty, String> tableView_permission;
    @FXML
    private TableView<VideoSimpleStringProperty> tableView;



     private ObservableList<VideoSimpleStringProperty> videos;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableView.setEditable(true);
        tableView_permission.setEditable(true);
        tableView_permission.setCellFactory(ChoiceBoxTableCell.forTableColumn("1", "3", "7"));
        tableView.getItems().clear();
        videos = FXCollections.observableArrayList();
        for (Video v : PlayerOriginalController.getListVideoFromJson()) {
            videos.add(new VideoSimpleStringProperty(v.name, v.src, v.permission, v.type, v.duration));
        }
        tableView.getItems().addAll(videos);

    }
    public void actionCancel(){
        System.out.println("cancel");

    }
    public void actionOK(){
        System.out.println("ok");
        for (VideoSimpleStringProperty v: videos) {
            System.out.println(v.permission.getValue());
        }
        Scene scene = bt_ok.getScene();
        PlayerOriginalController playerOriginalController =(PlayerOriginalController) scene.getUserData();
        ArrayList<Video> videosArraylist = new ArrayList<Video>();
        for (VideoSimpleStringProperty v : videos ){
            videosArraylist.add(new Video(v.getName(),v.getSrc(), Integer.parseInt(v.getPermission()),v.getType(),v.getDuration()));
        }
        playerOriginalController.writeJsonFile(PlayerOriginalController.pathPlayList , JSON.toJSONString(videosArraylist));
        playerOriginalController.loadPlayList();



    }
}
