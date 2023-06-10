package com.grupa.projektowa.labirynt;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.IOException;

public class Labirynt extends Application {
    public Group maingroup = new Group();
    public Group menuGroup;

    private static Labirynt bk = new Labirynt();

    //Singleton
    public static Labirynt getLabirynt() {
        if (bk == null) {
            bk = new Labirynt();
        }
        return bk;
    }

    @Override
    public void start(Stage stage) throws IOException {

        Scene scene = new Scene(maingroup);
        stage.setTitle("Labirynt");
        menuGroup = new Menu().MakeMenu(stage,700,600);

        maingroup.getChildren().addAll(menuGroup);

        String currentDir = System.getProperty("user.dir");
        Image icon = new Image(currentDir+"\\app.png");
        stage.getIcons().add(icon);
        stage.setWidth(700);
        stage.setHeight(600);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}