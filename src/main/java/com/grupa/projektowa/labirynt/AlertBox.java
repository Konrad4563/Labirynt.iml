package com.grupa.projektowa.labirynt;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AlertBox {
    public  void wyswietl(String title, String message,boolean o){
        Stage stage=new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setMinHeight(150);
        stage.setMinWidth(150);

        Font serif = Font.font("Serif", 30);
        Label label=new Label();
        label.setFont(serif);
        label.setText(message);
        label.setAlignment(Pos.CENTER);
        if(o==true){
            label.setStyle("-fx-font-size: 20px; -fx-text-fill: black -fx-background-color: white; -fx-padding: 10px;");
        }
        else{
            label.setStyle("-fx-font-size: 20px; -fx-text-fill: black; -fx-background-color: #C0C0C0; -fx-padding: 10px;");
        }


        Button closeButton=new Button("Zamknij okno");
        closeButton.setOnAction(e->stage.close());

        VBox vBox=new VBox();
        vBox.getChildren().addAll(label,closeButton);
        vBox.setAlignment(Pos.CENTER);

        Scene scene=new Scene(vBox);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UTILITY);
        stage.showAndWait();

    }

}