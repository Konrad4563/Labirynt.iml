package com.grupa.projektowa.labirynt;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;


public class Menu{
    private ElementFormaters editor = new ElementFormaters();
    private FileHandler fileHandler = new FileHandler();
    private Group menuGroup = new Group();
    private final LabyrinthDrawer labyrinthDrawer = new LabyrinthDrawer();
    public GridPane labyrinth;
    private GridControl control = new GridControl();
    public static Spinner<Integer> widthSpinner = new Spinner<>();
    public static Spinner<Integer> heightSpinner = new Spinner<>();
    public static Spinner<Integer> antSpinner = new Spinner<>();
    public static Spinner<Integer> pheromoneSpinner = new Spinner<>();
    public static ChoiceBox<String> algorithmChoice = new ChoiceBox<>();

    public Group MakeMenu(Stage stage, float stageWidth,float stageHeigth){

        labyrinth= labyrinthDrawer.makeLabyrinth(17,30,30);
        menuGroup.getChildren().add(labyrinth);

        float Xposition=(stageWidth-(120));

        Button start = editor.ButtonEditor("Start", 100, 30, Xposition, 10, "66ff33");
        setStartAction(start);
        Button zapisz = editor.ButtonEditorIMG("C:\\Users\\48513\\Desktop\\Labirynt\\save.png", 100, 30, Xposition, 50, "66ccff");
        setSaveAction(zapisz);
        Button wgraj = editor.ButtonEditorIMG("C:\\Users\\48513\\Desktop\\Labirynt\\upload.png", 100, 30, Xposition, 90, "66ccff");
        setUploadAction(wgraj,stage);
        Label sizeLabel = editor.LabelEditor("Rozmiar okna", 100, 30, Xposition, 130);

        ChoiceBox<String> sizeChoice = editor.ChoiceBoxEditor(Xposition,160,100);
        String[] sizes= new String[]{"700x600","800x700","900x800"};
        sizeChoice.getItems().addAll(sizes);
        sizeChoice.setValue(sizes[0]);

        Label algorithmLabel = editor.LabelEditor("Algorytm", 100, 30, Xposition, 200);

        algorithmChoice = editor.ChoiceBoxEditor(Xposition,230,100);
        String[] algorithms= new String[]{"Mrówkowy","Rekurencja"};
        algorithmChoice.getItems().addAll(algorithms);
        algorithmChoice.setValue(algorithms[0]);

        Label labyrinthAmountOfElements = editor.LabelEditor("Labirynt", 100, 30, Xposition, 260);

        Label widthLabel = editor.LabelEditor("Szerokość", 100, 30, Xposition, 290);
        widthSpinner = editor.SpinnerEditor(30,50,1,Xposition,320,100);
        setWidthSpinnerAction(stage);
        Label heightLabel = editor.LabelEditor("Wysokość", 100, 30, Xposition, 340);
        heightSpinner = editor.SpinnerEditor(30,50,1,Xposition,370,100);
        setHeigthSpinnerAction(stage);

        Label antAmount = editor.LabelEditor("Ilość mrówek", 100, 30, Xposition, 400);
        antSpinner = editor.SpinnerEditor(10,300,2,Xposition,430,100);
        Label pheromoneLabel = editor.LabelEditor("Waga feromonu", 100, 30, Xposition, 460);
        pheromoneSpinner = editor.SpinnerEditor(0,100,1,Xposition,490,100);

        Button reset = editor.ButtonEditor("Reset", 100, 30, Xposition, 520, "66ff33");
        setResetAction(reset,stage);

        sizeChoice.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                int amountH = labyrinth.getRowCount();
                int amountW = labyrinth.getColumnCount();
                if(sizes[t1.intValue()].equals("700x600")){
                    StageNewSize(stage,700,600);
                    control.SetNewGridSize(100,700,600,amountW,amountH,labyrinth);
                    SetNewMenuSize(700,100,editor.font1);
                    sizeChoice.setValue(sizes[0]);
                }
                if(sizes[t1.intValue()].equals("800x700")){
                    StageNewSize(stage,800,700);
                    control.SetNewGridSize(120,800,700,amountW,amountH,labyrinth);
                    SetNewMenuSize(800,120,editor.font2);
                    sizeChoice.setValue(sizes[1]);
                }
                if(sizes[t1.intValue()].equals("900x800")){
                    StageNewSize(stage,900,800);
                    control.SetNewGridSize(150,900,800,amountW,amountH,labyrinth);
                    SetNewMenuSize(900,150,editor.font3);
                    sizeChoice.setValue(sizes[2]);
                }
            }
        });



        menuGroup.getChildren().addAll(start,wgraj,zapisz,
                sizeLabel,sizeChoice,
                algorithmLabel,algorithmChoice,
                labyrinthAmountOfElements,widthLabel,
                widthSpinner,heightLabel,heightSpinner,
                antAmount,antSpinner,
                pheromoneLabel,pheromoneSpinner,reset);
        return menuGroup;
    }


    //Zmiana rozmiaru okna
    public void StageNewSize(Stage stage,int width,int height){
        stage.setHeight(height);
        stage.setWidth(width);
    }

    //Zmiana rozmiaru elementów menu
    public void SetNewMenuSize(double StageWidth, double elementWidth, Font font){
        for (Node node : menuGroup.getChildren()) {
            if (node instanceof Button) {
                Button btn = (Button) node;
                btn.setLayoutX(StageWidth-(elementWidth+20));
                btn.setPrefWidth(elementWidth);
                btn.setFont(font);
            }
            if (node instanceof Label) {
                Label lbl = (Label) node;
                lbl.setLayoutX(StageWidth-(elementWidth+20));
                lbl.setPrefWidth(elementWidth);
                lbl.setFont(font);
            }
            if (node instanceof Spinner<?>) {
                Spinner spn = (Spinner) node;
                spn.setPrefWidth(elementWidth);
                spn.setLayoutX(StageWidth-(elementWidth+20));
            }
            if (node instanceof ChoiceBox<?>) {
                ChoiceBox rbt = (ChoiceBox) node;
                rbt.setPrefWidth(elementWidth);
                rbt.setLayoutX(StageWidth-(elementWidth+20));
            }
        }
    }

    //Ustawienia spinnera odpowiedzialnego za szerokosc siatki
    private void setWidthSpinnerAction(Stage stage) {
        widthSpinner.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldvalue, Number newvalue) {
                    int amountW = (int) newvalue;
                    int amountH = labyrinth.getRowCount();
                    float widthS = (float) stage.getWidth();
                    float heigthS = (float) stage.getHeight();
                    float elementWidth = (float) widthSpinner.getWidth();

                    if (newvalue.doubleValue() > oldvalue.doubleValue()) {
                        control.AddColumnToGrid(labyrinth);
                        control.SetNewGridSize(elementWidth, widthS, heigthS, amountW, amountH, labyrinth);
                        labyrinthDrawer.setActionToAllKwadrat(amountH,amountW);
                    } else {
                        control.RemoveColumnToGrid(labyrinth);
                        control.SetNewGridSize(elementWidth, widthS, heigthS, amountW, amountH, labyrinth);
                        labyrinthDrawer.setActionToAllKwadrat(amountH,amountW);
                    }
            }
        });
    }

    //Ustawienia spinnera odpowiedzialnego za wysokosc siatki
    private void setHeigthSpinnerAction(Stage stage) {
        heightSpinner.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldvalue, Number newvalue) {
                    int amountH = (int) newvalue;
                    int amountW = labyrinth.getColumnCount();
                    float widthS = (float) stage.getWidth();
                    float heigthS = (float) stage.getHeight();
                    float elementWidth = (float) widthSpinner.getWidth();

                    if (newvalue.doubleValue() > oldvalue.doubleValue()) {
                        control.AddRowToGrid(labyrinth);
                        control.SetNewGridSize(elementWidth, widthS, heigthS, amountW, amountH, labyrinth);
                        labyrinthDrawer.setActionToAllKwadrat(amountH,amountW);
                    } else {
                        control.RemoveRowToGrid(labyrinth);
                        control.SetNewGridSize(elementWidth, widthS, heigthS, amountW, amountH, labyrinth);
                        labyrinthDrawer.setActionToAllKwadrat(amountH,amountW);
                    }
            }
        });
    }

    //Przycisk start
    private void setStartAction(Button button) {
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(labyrinthDrawer.isEnd()){
                int numRows = labyrinth.getRowCount();
                int numCols = labyrinth.getColumnCount();
                int startx=0;
                int starty=0;
                int[][] intArray = new int[numRows][numCols];
                for (int row = 0; row < numRows; row++) {
                    for (int col = 0; col < numCols; col++) {
                        int finalCol = col;
                        int finalRow = row;
                        Kwadrat kw = (Kwadrat) labyrinth.getChildren().stream()
                                .filter(n -> GridPane.getColumnIndex(n) == finalCol && GridPane.getRowIndex(n) == finalRow)
                                .findFirst()
                                .orElse(null);

                            if (kw.isColored()) {
                                if(kw.isStart){
                                    startx=row;
                                    starty=col;
                                    intArray[row][col] = 2;
                                }
                                else if(kw.isEnd){
                                    intArray[row][col] = 3;
                                }
                                else{
                                    intArray[row][col] = 1;
                                }

                            } else {
                                intArray[row][col] = 0;
                            }

                    }
                }

                if(algorithmChoice.getValue().equals("Mrówkowy")){
                    int antCount = antSpinner.getValue();
                    double pheromoneWeight = pheromoneSpinner.getValue();
                    AntAlgorithm algorithm = new AntAlgorithm(intArray,antCount,pheromoneWeight/100,0.5);
                    int[][] optimalPath = algorithm.findShortestPath();
                    ColorPath(optimalPath);
                }
                else{
                    Rekurencja rek = new Rekurencja(intArray,startx,starty);
                    ColorPath(rek.solve());
                }

            }}
        });
    }

    public static void blockSpinner(){
        widthSpinner.setDisable(true);
        heightSpinner.setDisable(true);
    }

    //Kolorowanie poprawnej ścieżki
    public void ColorPath(int[][] mazeWithPath){
        for (int i = 0; i < mazeWithPath.length; i++) {
            for (int j = 0; j < mazeWithPath[0].length; j++) {
                if(mazeWithPath[i][j]==4){
                    int finalI = i;
                    int finalJ = j;
                    Kwadrat kw = (Kwadrat) labyrinth.getChildren().stream()
                            .filter(n -> GridPane.getColumnIndex(n) == finalJ && GridPane.getRowIndex(n) == finalI)
                            .findFirst()
                            .orElse(null);
                    if(!kw.isStart && !kw.isEnd ) {
                        kw.setFill(Color.web("#ffff00"));
                    }
                }
            }
        }

    }

    //Przycisk zapisu planszy
    private void setSaveAction(Button button) {
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int numRows = labyrinth.getRowCount();
                int numCols = labyrinth.getColumnCount();
                if(labyrinthDrawer.isEnd()){
                    new SaveWindow().confirmTheSave(getGrid(),numCols,numRows);
                }
            }
        });
    }

    //Przycisk wgrania labiryntu
    private void setUploadAction(Button button,Stage stage) {
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    UploadLabyrinth upload=fileHandler.ChooseLab(stage);
                    if(upload!=null) {
                        resetToStartParameters();
                        float widthS = (float) stage.getWidth(), heigthS = (float) stage.getHeight();
                        float elementWidth = (float) widthSpinner.getWidth();
                        labyrinth = labyrinthDrawer.makeLabyrinth(0, upload.rows, upload.columns);
                        control.SetNewGridSize(elementWidth, widthS, heigthS, upload.rows, upload.columns, labyrinth);
                        menuGroup.getChildren().add(labyrinth);
                        blockSpinner();
                        labyrinthDrawer.setStart(true);
                        labyrinthDrawer.setEnd(true);
                        control.setNewParametersToKwadrat(labyrinth, upload.board);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }});
    }

    //Przycisk resetu
    private void setResetAction(Button button,Stage stage) {
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                float size = 0;
                if(stage.getWidth()>800){
                    size=23;
                }
                else if(stage.getWidth()==800){
                    size=20;
                }
                else{
                    size=17;
                }

                resetToStartParameters();
                labyrinth= labyrinthDrawer.makeLabyrinth(size,30,30);
                menuGroup.getChildren().add(labyrinth);

            }
        });
    }

    public void resetToStartParameters(){
        menuGroup.getChildren().remove(labyrinth);
        labyrinthDrawer.setEnd(false);
        labyrinthDrawer.setStart(false);
        widthSpinner.getValueFactory().setValue(30);
        widthSpinner.setDisable(false);
        heightSpinner.setDisable(false);
        heightSpinner.getValueFactory().setValue(30);
    }


    //Zwraca nam tablicę reprezentującą naszą plansze
    public int[][] getGrid(){
        int numRows = labyrinth.getRowCount(),numCols = labyrinth.getColumnCount();

        int[][] intArray = new int[numRows][numCols];
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                int finalCol = col,finalRow = row;
                Kwadrat kw = (Kwadrat) labyrinth.getChildren().stream()
                        .filter(n -> GridPane.getColumnIndex(n) == finalCol && GridPane.getRowIndex(n) == finalRow)
                        .findFirst()
                        .orElse(null);
                if (kw.isColored()) {
                    if(kw.isStart){
                        intArray[row][col] = 2;
                    }
                    else if(kw.isEnd){
                        intArray[row][col] = 3;
                    }
                    else{
                        intArray[row][col] = 1;
                    }

                } else {
                    intArray[row][col] = 0;
                }
            }
        }
        return intArray;
    }


}
