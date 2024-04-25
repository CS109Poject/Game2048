module com.CS109.game2048.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires javafx.media;
    requires mysql.connector.j;

    //opens com.CS109.game2048 to javafx.fxml;
    //exports com.CS109.game2048;
    exports com.CS109.game2048.main;
    opens com.CS109.game2048.main to javafx.fxml;
    exports com.CS109.game2048.controller;
    opens com.CS109.game2048.controller to javafx.fxml;
}