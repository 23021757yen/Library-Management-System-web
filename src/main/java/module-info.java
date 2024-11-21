module com.example.my_group_project {
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
    requires java.desktop;

    requires java.sql;
    requires mysql.connector.j;

    requires google.api.client;
    requires google.http.client;
    requires google.http.client.jackson2;
    requires google.api.services.books.v1.rev114;
    requires java.prefs;

    opens com.example.my_group_project to javafx.fxml;
    exports com.example.my_group_project;
}