package com.example.playeroriginal;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.playeroriginal.jsonData.Video;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.media.Media;


import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class PlayerOriginalController implements Initializable {
    private static String pathPlayList = "src/main/java/com/example/playeroriginal/jsonData/playlist.json";
    public static String pathUsers = "src/main/java/com/example/playeroriginal/jsonData/users.json";
    private static ObservableList<VideoSimpleStringProperty> videos;
    private static int selectedId = 0;
    private static boolean isPlaying = false;
    private static Media mediaPlaying;
    private static MediaPlayer mediaPlayer;
    //private static MediaView mediaView;
    public  User user;


    @FXML
    private Label timeNow;
    @FXML
    private Label timeSave;
    @FXML
    public Label username;
    @FXML
    private MenuItem menuItemOpen;
    @FXML
    private MenuItem menuOpenRecent;
    @FXML
    private MenuItem menuItemfileManagement;
    @FXML
    private ImageView btPlay;
    @FXML
    private MediaView mediaView;
    @FXML
    private Slider volume_slider;
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

    public int i = -1, j = -1;
    public double volume;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        user = new User("child",1);
        updateFonction();
        timeNow.setText("-:-");
        timeSave.setText("-:-");
        username.setText(user.getUsername());
        videos = FXCollections.observableArrayList();
        loadPlayList();
        tableView.setVisible(true);
        tableView.setDisable(false);
        mediaView.setDisable(true);
        mediaView.setVisible(false);

    }


    public void actionQuit(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void actionManage(ActionEvent actionEvent) {
    }

    public void actionAbout(ActionEvent actionEvent) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("About OriginalPlayer");
        dialog.setHeaderText("Version Information");
        dialog.setContentText("Version 0.0.0 Made by Shutong Zheng & Lanshi FU");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        /*Image image = new Image("src/main/resources/icon/logo_com.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(50);
        imageView.setPreserveRatio(true);
        dialog.setGraphic(imageView);*/

        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.setPrefSize(500, 300);
        dialog.showAndWait();
    }

    public void actionPlayList(ActionEvent actionEvent) {
        if (isPlaying == true){
            Stage stage = (Stage)mediaView.getScene().getWindow();
            stage.setHeight(stage.getHeight()-100);
            playOrStop();
        }


        if (mediaPlayer != null)
            mediaPlayer.pause();
        if (mediaPlaying != null)
            mediaPlaying = null;


        tableView.setVisible(true);
        tableView.setDisable(false);
        mediaView.setDisable(true);
        mediaView.setVisible(false);
        isPlaying = false;
    }

    public void actionStart(ActionEvent actionEvent) {
        playOrStop();
    }

    public void actionStop(ActionEvent actionEvent) {
    }

    public void actionSkipPre(ActionEvent actionEvent) {


    }

    public void actionSkipNext(ActionEvent actionEvent) {


    }

    public void actionVolumeUp(ActionEvent actionEvent) {
        volume_slider.setValue((volume_slider.getValue() + 10));
    }

    public void actionVolumeDown(ActionEvent actionEvent) {
        volume_slider.setValue((volume_slider.getValue() - 10));
    }

    public void actionMute(ActionEvent actionEvent) {
        if (j == -1) {
            volume = volume_slider.getValue();
            volume_slider.setValue(0);
            j = j * -1;
        } else {
            volume_slider.setValue(volume);
            j = j * -1;
        }
    }

    public void actionEnglish(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Look, an Error Dialog");
        alert.setContentText("Heiheihei, English version is the only version!");

        alert.showAndWait();
    }

    public void actionFrench(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Look, an Error Dialog");
        alert.setContentText("Ooops, French version not ready yet!");

        alert.showAndWait();
    }

    public void actionChinese(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Look, an Error Dialog");
        alert.setContentText("Ooops, Chinese version not ready yet!");

        alert.showAndWait();
    }


    public void actionPreferences(ActionEvent actionEvent) {
    }

    public void actionAccount(ActionEvent actionEvent) {
        //登录
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(PlayerOriginal.class.getResource("connection.fxml"));
          //  Parent root = fxmlLoader.load();
            Scene scene = new Scene(fxmlLoader.load(), 400, 230);
            PlayerOriginalController p =this;
            scene.setUserData(p);
            stage.setTitle("Connection");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionPlayerHelp(ActionEvent actionEvent) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Help for OriginalPlayer");
        dialog.setHeaderText("Mode D'emploi");
        dialog.setContentText(
                "1.If you have an account, login in 'Setting', the username and password is needed." + "\n" +
                        "2.The files that you opened can be found in 'Playlist'." + "\n" +
                        "3.The permission of the files can be adjusted in 'File' -> 'File management'" + "\n" +
                        "4.You can adjust the volume in 'audio' or with the buttons." + "\n" +
                        "5.The version information in in 'Help'->'About Player'");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.setPrefSize(500, 300);
        dialog.showAndWait();
    }

    public void actionOpen(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter_mp4 = new FileChooser.ExtensionFilter("mp4 files", " *.mp4");
        fileChooser.getExtensionFilters().add(extFilter_mp4);
        FileChooser.ExtensionFilter extFilter_wav = new FileChooser.ExtensionFilter("wave files", "*.wav");
        fileChooser.getExtensionFilters().add(extFilter_wav);
        FileChooser.ExtensionFilter extFilter_avi = new FileChooser.ExtensionFilter("mp4 files", " *.avi");
        fileChooser.getExtensionFilters().add(extFilter_avi);
        Stage stage = new Stage();
        File file = fileChooser.showOpenDialog(stage);
        addVideoInJson(file);


    }

    public void actionPlay(MouseEvent mouseEvent) {
        playOrStop();
    }


    private void addVideoInJson(File file) {

        ArrayList<Video> playList = new ArrayList<>();
        playList = getListVideoFromJson();
        //如果playlist没有 path 一样的path 执行
        boolean isNotExsitFile = true;
        for (Video v : playList) {
            if (v.src.equals(file.getPath())) {
                isNotExsitFile = false;
            }

        }
        if (isNotExsitFile) {
            Media media = new Media(new File(file.getPath()).toURI().toString());
            Video video = new Video(file.getName(), file.getPath(), 7, getExtension(file.getPath()), media.getDuration().toString());
            playList.add(video);
            writeJsonFile(pathPlayList, JSON.toJSONString(playList));
            loadPlayList();
        }

    }

    public static String readJsonFile(String pathFile) {
        String jsonStr = "";
        try {
            File jsonFile = new File(pathFile);
            FileReader fileReader = new FileReader(jsonFile);

            Reader reader = new InputStreamReader(new FileInputStream(jsonFile), "utf-8");
            int charNumber = 0;
            StringBuffer stringBuffer = new StringBuffer();
            while ((charNumber = reader.read()) != -1) {
                stringBuffer.append((char) charNumber);
            }
            fileReader.close();
            reader.close();
            jsonStr = stringBuffer.toString();
            return jsonStr;
        } catch (IOException e) {
            System.out.println("Error: can't read json file");
            return null;
        }
    }

    private void writeJsonFile(String pathFile, String dataJson) {
        File file = new File(pathPlayList);
        if (file.exists())
            file.delete();
        try {
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("Error : can't createNewFile");
        }
        try {
            Writer write = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
            write.write(dataJson);
            write.flush();
            write.close();

        } catch (IOException e) {
            System.out.println("Error: can't write JsonFile ");
        }


    }

    private String getExtension(String string) {
        String res = "";
        if (string.contains(".")) {
            int i = string.lastIndexOf('.');
            res = i > 0 ? string.substring(i + 1) : "";
        }
        return res;
    }

    private ArrayList<Video> getListVideoFromJson() {
        String jsonData = readJsonFile(pathPlayList);
        List<Video> playList = new ArrayList<Video>();
        playList = JSON.parseObject(jsonData, new TypeReference<ArrayList<Video>>() {
        });
        return (ArrayList<Video>) playList;
    }

    public void loadPlayList() {
        tableView.getItems().clear();
        videos.clear();

        for (Video v : getListVideoFromJson()) {
            if (v.permission <= user.getPermission())
            videos.add(new VideoSimpleStringProperty(v.name, v.src, v.permission, v.type, v.duration));
        }
        tableView.getItems().addAll(videos);

    }

    private void playOrStop() {

        // 判断是否能能读文件
        // 若不能 则 isplaying =false； 能 isplaying  = true
        // isplaying 能用则可以使用 skip 和 pre
        if (isPlaying == false) {
            try {

                String videoFileURIStr = new File(getPathSelectedVideo()).toURI().toString();
                mediaPlaying = new Media(videoFileURIStr);
                mediaPlayer = new MediaPlayer(mediaPlaying);
                mediaPlayer.setAutoPlay(true);
                mediaView.setMediaPlayer(mediaPlayer);


                DoubleProperty mvw = mediaView.fitWidthProperty();
                DoubleProperty mvh = mediaView.fitHeightProperty();
                mvw.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
                mvh.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
                mediaView.setPreserveRatio(true);
                Stage stage = (Stage)mediaView.getScene().getWindow();
                    System.out.println("W: "+stage.getWidth() +"   H:"+stage.getHeight());
                    stage.setHeight(stage.getHeight()+100);
            //    setScene(new Scene(new Group(mediaView), size.getWidth(), size.getHeight()));



//                paneView.getChildren().clear();
//                paneView.getChildren().add(mediaView);

//                mediaView.fitHeightProperty().bind(paneView.heightProperty());
//                mediaView.fitWidthProperty().bind(paneView.widthProperty());
//                mediaView.setX((paneView.getWidth() - mediaView.getFitWidth())/2);
//                System.out.println("paneW: "+paneView.getWidth() +"mediaW: "+mediaView.getFitWidth());
//                mediaView.setY((paneView.getHeight() - mediaView.getFitHeight())/2);
                isPlaying = true;
                tableView.setVisible(false);
                tableView.setDisable(true);
                mediaView.setDisable(false);
                mediaView.setVisible(true);
            } catch (Exception e) {
                isPlaying = false;
                System.out.println("Error: can't play this video");
            }

        }

        if (i == -1) {
            Path imageFile = Paths.get("src/main/resources/icon/pause.png");
            try {
                btPlay.setImage(new Image(imageFile.toUri().toURL().toExternalForm()));
                i = i * -1;
            } catch (IOException e) {
                System.out.println("Error: can't change image pause");
            }

        } else {
            Path imageFile = Paths.get("src/main/resources/icon/play.png");
            try {
                btPlay.setImage(new Image(imageFile.toUri().toURL().toExternalForm()));
                i = i * -1;
            } catch (IOException e) {
                System.out.println("Error: can't change image play");
            }


        }
    }

    private String getPathSelectedVideo() {
        String res;
        try {
            VideoSimpleStringProperty video = tableView.getSelectionModel().getSelectedItem();
            selectedId = tableView.getSelectionModel().getSelectedIndex();
            res = video.getSrc().toString();

            System.out.println("ID: " + selectedId + "    Video name " + video.getName().toString());
        } catch (Exception e) {
            selectedId = 0;
            res = videos.get(0).getSrc().toString();
            System.out.println("Error: no selected video");
        }
        return res;
    }
    public void updateFonction(){
        if (user.permission == 7){
            menuItemOpen.setDisable(false);
            menuOpenRecent.setDisable(false);
            menuItemfileManagement.setDisable(false);
        }else {
            menuItemOpen.setDisable(true);
            menuOpenRecent.setDisable(true);
            menuItemfileManagement.setDisable(true);
        }
    }

}
