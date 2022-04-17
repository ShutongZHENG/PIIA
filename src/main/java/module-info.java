module com.example.playeroriginal {
    requires javafx.controls;
    requires javafx.fxml;
    requires fastjson;
    requires javafx.media;
    requires java.sql;


    opens com.example.playeroriginal to javafx.fxml;
    exports com.example.playeroriginal;

    exports com.example.playeroriginal.jsonData;
    opens com.example.playeroriginal.jsonData to javafx.fxml;
}