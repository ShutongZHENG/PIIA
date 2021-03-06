package com.example.playeroriginal;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.playeroriginal.jsonData.Video;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.util.Duration;


import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class PlayerOriginalController implements Initializable {
    public static String pathPlayList = "src/main/java/com/example/playeroriginal/jsonData/playlist.json";
    public static String pathUsers = "src/main/java/com/example/playeroriginal/jsonData/users.json";
    private static ObservableList<VideoSimpleStringProperty> videos;
    private static int selectedId = 0;
    private static boolean isPlaying = false;
    private static Media mediaPlaying;
    private static MediaPlayer mediaPlayer;
    private static boolean signalPre = false;
    private static boolean signalNext = false;
    public javafx.util.Duration totalDuration;
    public User user;

    @FXML
    private Slider timeBar;
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
    private TableColumn<VideoSimpleStringProperty, String> tableView_genre;
    @FXML
    private TableColumn<VideoSimpleStringProperty, String> tableView_type;
    @FXML
    private TableColumn<VideoSimpleStringProperty, String> tableView_permission;
    @FXML
    private TableView<VideoSimpleStringProperty> tableView;
    @FXML
    private ImageView preVideo;
    @FXML
    private  ImageView nextVideo;
    public int i = -1, j = -1;
    public double volume;
    public static Stage stage = new Stage();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        user = new User("child", 1);
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

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(PlayerOriginal.class.getResource("videoManager.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            PlayerOriginalController p = this;
            scene.setUserData(p);
            stage.setTitle("Manager");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }


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
        if (isPlaying == true) {
            Stage stage = (Stage) mediaView.getScene().getWindow();
            stage.setHeight(487);
            stage.setWidth(801);
            Path imageFile = Paths.get("src/main/resources/icon/play.png");
            try {
                btPlay.setImage(new Image(imageFile.toUri().toURL().toExternalForm()));
                i =  -1;
                mediaPlayer.pause();
            } catch (IOException e) {
                System.out.println("Error: can't change image play");
            }
        }

        timeBar.setValue(0);
        if (mediaPlayer != null)
            mediaPlayer.pause();
        if (mediaPlaying != null)
            mediaPlaying = null;
        timeNow.setText("-:-");
        timeSave.setText("-:-");
        mediaPlayer.stop();
        mediaPlayer.dispose();
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
        if (isPlaying) {
            Stage stage = (Stage) mediaView.getScene().getWindow();
            stage.setHeight(487);
            stage.setWidth(801);
            Path imageFile = Paths.get("src/main/resources/icon/play.png");
            try {
                btPlay.setImage(new Image(imageFile.toUri().toURL().toExternalForm()));
                i =  -1;
                mediaPlayer.pause();
            } catch (IOException e) {
                System.out.println("Error: can't change image play");
            }
        }

        timeBar.setValue(0);
        if (mediaPlayer != null)
            mediaPlayer.pause();
        if (mediaPlaying != null)
            mediaPlaying = null;
        timeNow.setText("-:-");
        timeSave.setText("-:-");
        mediaPlayer.stop();
        mediaPlayer.dispose();

        tableView.setVisible(true);
        tableView.setDisable(false);
        mediaView.setDisable(true);
        mediaView.setVisible(false);
        isPlaying = false;
    }

    public void actionSkipPre(ActionEvent actionEvent) {
        if (selectedId >= 0 && isPlaying) {
            mediaPlayer.seek(new Duration(0));
        }

    }

    public void actionSkipNext(ActionEvent actionEvent) {
        if (selectedId >= 0 && isPlaying) {
            mediaPlayer.seek(new Duration((mediaPlayer.getTotalDuration().toSeconds() - 2.0) * 1000));

        }

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
        //??????
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(PlayerOriginal.class.getResource("connection.fxml"));
            //  Parent root = fxmlLoader.load();
            Scene scene = new Scene(fxmlLoader.load(), 400, 230);
            PlayerOriginalController p = this;
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
        //??????playlist?????? path ?????????path ??????
        boolean isNotExsitFile = true;
        for (Video v : playList) {
            if (v.src.equals(file.getPath())) {
                isNotExsitFile = false;
            }

        }
        if (isNotExsitFile) {
            Media media = new Media(new File(file.getPath()).toURI().toString());
            Video video = new Video(file.getName(), file.getPath(), 7, getExtension(file.getPath()), "");
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

    public void writeJsonFile(String pathFile, String dataJson) {
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

    public static ArrayList<Video> getListVideoFromJson() {
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
                videos.add(new VideoSimpleStringProperty(v.name, v.src, v.permission, v.type, v.genre));
        }
        tableView.getItems().addAll(videos);

    }

    private void playOrStop() {

        // ???????????????????????????
        // ????????? ??? isplaying =false??? ??? isplaying  = true
        // isplaying ????????????????????? skip ??? pre
        if (!isPlaying) {
            try {

                String videoFileURIStr = new File(getPathSelectedVideo()).toURI().toString();
                mediaPlaying = new Media(videoFileURIStr);
                mediaPlayer = new MediaPlayer(mediaPlaying);
                mediaPlayer.setAutoPlay(true);
                mediaView.setMediaPlayer(mediaPlayer);
                mediaPlayer.setOnEndOfMedia(new Runnable() {
                    @Override
                    public void run() {
                        if (isPlaying) {
                            Stage stage = (Stage) mediaView.getScene().getWindow();
                            stage.setHeight(stage.getHeight()-100);
                            playOrStop();
                        }
                        timeBar.setValue(0);
                        if (mediaPlayer != null)
                            mediaPlayer.pause();
                        if (mediaPlaying != null)
                            mediaPlaying = null;
                        mediaPlayer.dispose();
                        timeNow.setText("-:-");
                        timeSave.setText("-:-");


                        tableView.setVisible(true);
                        tableView.setDisable(false);
                        mediaView.setDisable(true);
                        mediaView.setVisible(false);
                        isPlaying = false;
                    }
                });
                totalDuration = mediaPlayer.getTotalDuration();
                String totalString = DurationToString(totalDuration);
                mediaPlayer.volumeProperty().bindBidirectional(volume_slider.valueProperty());
                mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                    @Override
                    public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                        if (!timeBar.isValueChanging()){
                            timeBar.setValue(newValue.toSeconds());
                        }
                        timeNow.setText(DurationToString(newValue));
                    }
                });

                mediaPlayer.totalDurationProperty().addListener(new ChangeListener<Duration>() {
                    @Override
                    public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration t1) {
                        timeBar.setMax(t1.toSeconds());
                        timeSave.setText(String.valueOf(DurationToString(mediaPlayer.getTotalDuration())));
                    }
                });

                volume_slider.valueProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(Observable observable) {
                        mediaPlayer.setVolume(volume_slider.getValue());
                        System.out.println(mediaPlayer.getVolume()+"==="+volume_slider.getValue());
                    }
                });

                timeBar.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                                    if (!t1){
                                        mediaPlayer.seek(Duration.seconds(timeBar.getValue()));
                                    }
                    }
                });

                timeBar.valueProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                            double currenTime = mediaPlayer.getCurrentTime().toSeconds();
                            if (Math.abs(currenTime-newValue.doubleValue())>0.5){
                                mediaPlayer.seek(Duration.seconds(newValue.doubleValue()));
                            }

                    }
                });
             //   mediaView.setFitWidth(stage.getWidth());
                mediaView.fitWidthProperty().bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
                mediaView.fitHeightProperty().bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
                mediaView.setPreserveRatio(true);

                Stage stage = (Stage) mediaView.getScene().getWindow();
                //  mediaView.setFitWidth(stage.getWidth());

                System.out.println("W: " + stage.getWidth() + "   H:" + stage.getHeight());
                stage.setHeight(stage.getHeight() + 100);
//                stage.setWidth(mediaView.getFitWidth());
//                stage.setHeight(mediaView.getFitHeight()+130);
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
                mediaPlayer.play();
            } catch (IOException e) {
                System.out.println("Error: can't change image pause");
            }

        } else {
            Path imageFile = Paths.get("src/main/resources/icon/play.png");
            try {
                btPlay.setImage(new Image(imageFile.toUri().toURL().toExternalForm()));
                i = i * -1;
                mediaPlayer.pause();
            } catch (IOException e) {
                System.out.println("Error: can't change image play");
            }


        }
    }

    private String getPathSelectedVideo() {
        String res;
        try {

            if (signalNext){
                selectedId +=1;
                if (selectedId >= videos.size())
                    selectedId = videos.size()-1;
                signalNext = false;
            }
            else if (signalPre){
                selectedId -=1;
                if (selectedId<0){
                    selectedId = 0;
                }
                signalPre = false;
            }else {
                selectedId = tableView.getSelectionModel().getSelectedIndex();
            }

            res = videos.get(selectedId).getSrc();

            System.out.println("ID: " + selectedId + "    Video name " + videos.get(selectedId).getName());
        } catch (Exception e) {
            selectedId = 0;
            res = videos.get(0).getSrc().toString();
            System.out.println("Error: no selected video");
        }
        return res;
    }

    public void updateFonction() {
        if (user.permission == 7) {
            menuItemOpen.setDisable(false);
            menuOpenRecent.setDisable(false);
            menuItemfileManagement.setDisable(false);
        } else {
            menuItemOpen.setDisable(true);
            menuOpenRecent.setDisable(true);
            menuItemfileManagement.setDisable(true);
        }
    }

    public String DurationToString(javafx.util.Duration duration) {
        int time = (int) duration.toSeconds();
        int hour = time / 3600;
        int minute = (time - hour * 3600) / 60;
        int second = time % 60;
        return hour + ":" + minute + ":" + second;
    }


    public void actionBtNextVideo(MouseEvent mouseEvent) {
        if (isPlaying) {
            Stage stage = (Stage) mediaView.getScene().getWindow();
            stage.setHeight(stage.getHeight()-100);
            playOrStop();
            timeBar.setValue(0);
            if (mediaPlayer != null)
                mediaPlayer.pause();
            if (mediaPlaying != null)
                mediaPlaying = null;
            timeNow.setText("-:-");
            timeSave.setText("-:-");
            mediaPlayer.stop();
            mediaPlayer.dispose();
            isPlaying = false;
            signalNext = true;
            playOrStop();

        }


    }

    public void actionBtPreVideo(MouseEvent mouseEvent) {
        if (isPlaying) {
            Stage stage = (Stage) mediaView.getScene().getWindow();
            stage.setHeight(stage.getHeight()-100);
            System.out.println("Stage H "+ stage.getHeight());
            playOrStop();
            timeBar.setValue(0);
            if (mediaPlayer != null)
                mediaPlayer.pause();
            if (mediaPlaying != null)
                mediaPlaying = null;
            timeNow.setText("-:-");
            timeSave.setText("-:-");
            mediaPlayer.stop();
            mediaPlayer.dispose();
            isPlaying = false;
            signalPre = true;
            playOrStop();

        }
    }
}
