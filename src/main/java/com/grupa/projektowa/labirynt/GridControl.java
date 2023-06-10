package com.grupa.projektowa.labirynt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class GridControl {

    //Zmiana wielkości siatki labiryntu
    public void SetNewGridSize(float elementWidth, float stageWidth, float stageHigth, int amountOfRectangleW, int amountOfRectangleH, GridPane grid){
        int newSize=0;
        float widthWitoutMenu= stageWidth-(elementWidth+55);

        do{
            newSize++;
        }while(isFitOnWindow(newSize,widthWitoutMenu,(stageHigth-80),amountOfRectangleW,amountOfRectangleH));

        for (Node node : grid.getChildren()) {
            if (node instanceof Kwadrat) {
                Kwadrat n = (Kwadrat) node;
                n.setHeight(newSize-1);
                n.setWidth(newSize-1);
            }
        }
    }

    //Wyszukanie maksymalnego rozmiaru kwadratu
    public boolean isFitOnWindow(int size,float width,float heigth,int amountOfRectangleW,int amountOfRectangleH){
        boolean fit;
        if((size*amountOfRectangleW)<width && (size*amountOfRectangleH)<heigth){
            fit=true;
        }
        else{

            fit=false;
        }

        return fit;
    }

    //Dodanie nowej kolumy do siatki
    public void AddColumnToGrid(GridPane grid){
        int columns=grid.getColumnCount();
        int rows= grid.getRowCount();

        Kwadrat k = (Kwadrat) grid.getChildren().get(0);
        double size= k.getWidth();

        for(int i=0;i<rows;i++){
            Kwadrat kwadrat=new Kwadrat(columns,i,size);
            if(i==rows-1 || i==0){
                kwadrat.setVisible(false);
                Kwadrat node = getKwadrat(grid,columns-1,i);
                node.setVisible(true);
            }
            grid.add(kwadrat,columns,i);
        }
        ColorColumn(grid,columns,rows,true);
    }

    //Dodanie nowego wiersza do siatki
    public void AddRowToGrid(GridPane grid){
        int columns=grid.getColumnCount();
        int rows= grid.getRowCount();

        Kwadrat k = (Kwadrat) grid.getChildren().get(0);
        double size= k.getWidth();

        for(int i=0;i<columns;i++){
            Kwadrat kwadrat=new Kwadrat(i,rows,size);
            if(i==columns-1 || i==0){
                kwadrat.setVisible(false);
                Kwadrat node = getKwadrat(grid,i,rows-1);
                node.setVisible(true);
            }
            grid.add(kwadrat,i,rows);
        }

        ColorRow(grid,columns,rows,true);
    }

    //Usuniecie kolumby z siatki
    public void RemoveColumnToGrid(GridPane grid){
        int rows= grid.getRowCount();
        int columns= (grid.getColumnCount()-1);

        ObservableList<Node> nodesToRemove = FXCollections.observableArrayList();
        for (Node node : grid.getChildren()) {
            Integer columnIndex = GridPane.getColumnIndex(node);
            if (columnIndex != null && columnIndex == columns) {
                nodesToRemove.add(node);
            }
        }
        grid.getChildren().removeAll(nodesToRemove);
        Kwadrat node = getKwadrat(grid,columns-1,0);
        Kwadrat node2 = getKwadrat(grid,columns-1,rows-1);
        node.setVisible(false);
        node2.setVisible(false);

        ColorColumn(grid,columns,rows,false);
    }

    //Usuniecie kolumby z siatki
    public void RemoveRowToGrid(GridPane grid){
        int columns= grid.getColumnCount();
        int rows= (grid.getRowCount()-1);


        ObservableList<Node> nodesToRemove = FXCollections.observableArrayList();
        for (Node node : grid.getChildren()) {
            Integer rowIndex = GridPane.getRowIndex(node);
            if (rowIndex != null && rowIndex == rows) {
                nodesToRemove.add(node);
            }
        }
        grid.getChildren().removeAll(nodesToRemove);
        Kwadrat node = getKwadrat(grid,0,rows-1);
        Kwadrat node2 = getKwadrat(grid,columns-1,rows-1);
        node.setVisible(false);
        node2.setVisible(false);

        ColorRow(grid,columns,rows,false);
    }

    //Kolorowanie kolumny
    //Jesli color=true to malujemy wewnetrzne elementy na bialo
    //Jesli false to na czarno
    public void ColorColumn(GridPane grid, int column,int rows,boolean color){
        for(int i=1;i<(rows-1);i++){
            int finalI = i;

            Node node = grid.getChildren().stream()
                    .filter(n -> GridPane.getColumnIndex(n) == (column-1) && GridPane.getRowIndex(n) == finalI)
                    .findFirst()
                    .orElse(null);
            if (node instanceof Kwadrat) {
                Kwadrat n = (Kwadrat) node;
                if(color) {
                    n.setFill(Color.web("#d6d6c2"));
                }
                else{
                    n.setFill(Color.web("#000000"));
                }
            }

        }
    }

    //Kolorowanie wiersza
    //Jesli color=true to malujemy wewnetrzne elementy na bialo
    //Jesli false to na czarno
    public void ColorRow(GridPane grid, int column,int rows,boolean color){
        for(int i=1;i<(column-1);i++){
            int finalI = i;

            Node node = grid.getChildren().stream()
                    .filter(n -> GridPane.getColumnIndex(n) == finalI && GridPane.getRowIndex(n) == (rows-1))
                    .findFirst()
                    .orElse(null);
            if (node instanceof Kwadrat) {
                Kwadrat n = (Kwadrat) node;
                if(color) {
                    n.setFill(Color.web("#d6d6c2"));
                }
                else{
                    n.setFill(Color.web("#000000"));
                }
            }

        }
    }

    public Kwadrat getKwadrat(GridPane grid,int x,int y){
        Kwadrat node = (Kwadrat) grid.getChildren().stream()
                .filter(n -> GridPane.getColumnIndex(n) == x  && GridPane.getRowIndex(n) == y)
                .findFirst()
                .orElse(null);
        return node;
    }

    public void setNewParametersToKwadrat(GridPane grid,int[][] intArray){
        for(int i=0;i<grid.getRowCount();i++){
            for(int j=0;j<grid.getColumnCount();j++){
                if(intArray[i][j]==2){
                    Kwadrat kw = getKwadrat(grid,j,i);
                    kw.setStart(true);
                    kw.setColored(true);
                    kw.setFill(Color.web("#99ff33"));
                }
                if(intArray[i][j]==3){
                    Kwadrat kw = getKwadrat(grid,j,i);
                    kw.setEnd(true);
                    kw.setColored(true);
                    kw.setFill(Color.web("#ff6600"));
                }
                if(intArray[i][j]==1){
                    Kwadrat kw = getKwadrat(grid,j,i);
                    kw.setColored(true);
                    kw.setFill(Color.web("#3399ff"));
                }
            }
        }

    }

}
