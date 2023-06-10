package com.grupa.projektowa.labirynt;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ElementFormaters {
    public Font font1 = Font.font("Arial", FontWeight.NORMAL, 10);
    public Font font2 = Font.font("Arial", FontWeight.NORMAL, 16);
    public Font font3 = Font.font("Arial", FontWeight.NORMAL, 20);

    public Button ButtonEditor(String label,float buttonWidth,float buttonHeight,float buttonLayoutX,float buttonlayoutY,String backgroundColor){
        Button button = new Button(label);
        button.setPrefWidth(buttonWidth);
        button.setPrefHeight(buttonHeight);
        button.setLayoutX(buttonLayoutX);
        button.setLayoutY(buttonlayoutY);
        button.setWrapText(true);
        button.setFont(font1);
        button.setStyle("-fx-background-color:#"+backgroundColor+";");
        return button;
    }

    public Button ButtonEditorIMG(String imgpath,float buttonWidth,float buttonHeight,float buttonLayoutX,float buttonlayoutY,String backgroundColor){
        Button button = new Button();
        Image img = new Image(imgpath);
        ImageView icon = new ImageView(img);
        icon.setFitWidth(20);
        icon.setFitHeight(20);
        button.setPrefWidth(buttonWidth);
        button.setPrefHeight(buttonHeight);
        button.setLayoutX(buttonLayoutX);
        button.setLayoutY(buttonlayoutY);
        button.setGraphic(icon);
        button.setStyle("-fx-background-color:#"+backgroundColor+";");
        return button;
    }

    public Label LabelEditor(String label,float labelWidth,float labelHeight,float labelLayoutX,float labellayoutY ){
        Label newlabel = new Label(label);
        newlabel.setPrefWidth(labelWidth);
        newlabel.setPrefHeight(labelHeight);
        newlabel.setLayoutX(labelLayoutX);
        newlabel.setLayoutY(labellayoutY);
        newlabel.setFont(font1);
        newlabel.setWrapText(true);
        return newlabel;
    }

    public ChoiceBox ChoiceBoxEditor(float radiobuttonLayoutX, float radiobuttonLayoutY, float radiobuttonWidth){
        ChoiceBox choice = new ChoiceBox();
        choice.setLayoutX(radiobuttonLayoutX);
        choice.setLayoutY(radiobuttonLayoutY);
        choice.setPrefWidth(radiobuttonWidth);
        choice.setStyle("-fx-background-color:#ffcc00;");
        return choice;
    }

    public Spinner SpinnerEditor(int start,int end,int step,float radiobuttonLayoutX, float radiobuttonLayoutY, float radiobuttonWidth){
        Spinner spn = new Spinner(start,end,step);
        spn.setLayoutX(radiobuttonLayoutX);
        spn.setLayoutY(radiobuttonLayoutY);
        spn.setPrefWidth(radiobuttonWidth);
        return spn;
    }
}
