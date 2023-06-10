package com.grupa.projektowa.labirynt;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.List;
import java.util.function.UnaryOperator;

public class SaveWindow {
    private FileHandler fileHandler = new FileHandler();
    public Label errors = new ElementFormaters().LabelEditor("",100,30,30,0);
    public Label name = new ElementFormaters().LabelEditor("Nazwa pliku",100,30,30,30);
    public TextField namet = new TextField();

    public void confirmTheSave(int[][] intArray,int numCols,int numRow){
        Stage st=new Stage();
        Group group = new Group();
        Font font2 = Font.font("Arial", FontWeight.NORMAL, 18);
        st.initModality(Modality.APPLICATION_MODAL);
        st.setTitle("Dane pliku");
        st.setHeight(200);
        st.setWidth(250);


        name.setFont(font2);

        namet.setLayoutY(70);
        namet.setLayoutX(30);
        namet.setTextFormatter(fileName());

        Button save = new ElementFormaters().ButtonEditor("Zapisz",80,30,30,110,"66ff33");
        save.setFont(font2);
        setSaveAction(save,intArray,numCols,numRow);

        Button closeButton = new ElementFormaters().ButtonEditor("Anuluj",80,30,120,110,"ff1a1a");
        closeButton.setFont(font2);
        closeButton.setOnAction(e->st.close());



        group.getChildren().addAll(name,namet,save,closeButton,errors);
        Scene sc=new Scene(group);
        st.setScene(sc);
        st.initStyle(StageStyle.UTILITY);
        st.showAndWait();
    }

    //Przycisk zapis
    private void setSaveAction(Button button,int[][] array,int numCols,int numRow) {
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!namet.getText().isEmpty()){
                    int result = fileHandler.CreateDescriptionFile(arrayToString(array,numCols,numRow),namet.getText());
                    if(result==0){
                        errors.setText("Zapisane pomyślnie");
                        errors.setTextFill(Color.web("#66ff33"));
                        button.setDisable(true);
                        namet.setDisable(true);
                    }
                    else if(result==2){
                        errors.setText("Coś poszło nie tak");
                        errors.setTextFill(Color.web("#ff1a1a"));
                    }
                    else{
                        errors.setText("Plik o tej nazwie istnieje");
                        errors.setTextFill(Color.web("#ff1a1a"));
                    }
                }
                else{
                    errors.setText("Podaj nazwe pliku");
                    errors.setTextFill(Color.web("#ff1a1a"));
                }
            }
        });
    }

    //Zamiana tablicy z planszą w stringa którego można zapisać w txt
    public String arrayToString(int[][] array,int numCols,int numRow){
        String arrayString = ".-.. .- -... .. .-. -.-- -. - "+"\n";
        arrayString+=numCols+"x"+numRow+"\n";

        for(int i=0;i<numRow;i++){
            for(int j=0;j<numCols;j++){
                arrayString+=array[i][j]+" ";
            }
            arrayString+="\n";
        }

        return arrayString;
    }

    //Blokowanie wpisaywania znaków innych niż litery i cyfry
    public  TextFormatter<TextFormatter.Change> fileName()
    {
        UnaryOperator<TextFormatter.Change> fileName = change -> {
            String input = change.getText();

            if ((input.matches("[a-zA-Z0-9]*")) || change.isDeleted()) {
                return change;
            }
            return null;
        };
        return new TextFormatter<>(fileName);
    }
}
