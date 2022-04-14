module com.example.playeroriginal {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires fastjson;
    requires java.sql;
    requires javafx.media;

    opens com.example.playeroriginal to javafx.fxml;
    exports com.example.playeroriginal;
    exports com.example.playeroriginal.jsonData;
    opens com.example.playeroriginal.jsonData to javafx.fxml;
}