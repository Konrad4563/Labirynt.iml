package com.grupa.projektowa.labirynt;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import static com.grupa.projektowa.labirynt.Menu.blockSpinner;


public class LabyrinthDrawer{
    public GridPane grid = new GridPane();
    private boolean start = false;
    private boolean end = false;


    private void setKwadratAction(Kwadrat kwadrat,int sizeH,int sizeW) {
        kwadrat.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if(e.getButton().equals(MouseButton.PRIMARY) && !kwadrat.isColored()) {
                    if (start == false) {
                        if ( ((kwadrat.getYValue() == 0 || kwadrat.getYValue() == (sizeH - 1)) || (kwadrat.getXValue() == 0 || kwadrat.getXValue() == (sizeW - 1)))) {
                            kwadrat.setFill(Color.web("#99ff33"));
                            start = true;
                            blockSpinner();
                            kwadrat.setColored(true);
                            kwadrat.setStart(true);
                            neighborDisable(kwadrat.getXValue(),kwadrat.getYValue());
                        }
                    } else if (start == true && end == false) {
                        if (((kwadrat.getYValue() == 0 || kwadrat.getYValue() == (sizeH - 1)) || (kwadrat.getXValue() == 0 || kwadrat.getXValue() == (sizeW - 1)))) {
                            kwadrat.setFill(Color.web("#ff6600"));
                            end = true;
                            kwadrat.setEnd(true);
                            kwadrat.setColored(true);
                        }
                    } else {
                        if (kwadrat.getYValue() != 0 && kwadrat.getYValue() != (sizeH - 1) && kwadrat.getXValue() != 0 && kwadrat.getXValue() != (sizeW - 1)) {
                            kwadrat.setFill(Color.web("#3399ff"));
                            kwadrat.setColored(true);
                        }
                    }
                }
                if (e.getButton().equals(MouseButton.SECONDARY) && kwadrat.isColored() && kwadrat.getYValue() != 0 && kwadrat.getYValue() != (sizeW - 1) && kwadrat.getXValue() != 0 && kwadrat.getXValue() != (sizeW - 1)) {
                    kwadrat.setFill(Color.web("#d6d6c2"));
                    kwadrat.setColored(false);
                }
            }
        });
    }

    //Stworzenie pierwszego labiryntu który widzimy
    public GridPane makeLabyrinth(float squareSize,int sizeW,int sizeH) {
        grid=new GridPane();
        grid.setLayoutX(10);
        grid.setLayoutY(10);

        for (int i = 0; i < sizeW; i++) {
            for (int j = 0; j < sizeH; j++) {
                Kwadrat kwadrat = new Kwadrat(i, j,squareSize);

                if (i == 0 || j == 0 || i == (sizeW - 1) || j == (sizeH - 1)) {
                    kwadrat.setFill(Color.web("#000000"));
                } else {
                    kwadrat.setFill(Color.web("#d6d6c2"));
                }

                if((i==0 && j==0)||(i==0 && j==sizeH-1)||(i==sizeW-1 && j==0)||(i==sizeW-1 && j==sizeH-1)){
                    kwadrat.setVisible(false);
                }
                grid.add(kwadrat, i, j);

            }

        }
        setActionToAllKwadrat(sizeH,sizeW);
        return grid;
    }

    //Wyłączenie kwadratów sąsiadujących to miejsca startowego
    public void neighborDisable(int x,int y){
        int x1,x2,y1,y2;
        if(x==0 || x==grid.getColumnCount()-1){

            x1=x;
            x2=x;
            y1=y-1;
            y2=y+1;
        }
        else{
            x1=x-1;
            x2=x+1;
            y1=y;
            y2=y;
        }

        Kwadrat node = (Kwadrat) grid.getChildren().stream()
                .filter(n -> GridPane.getColumnIndex(n) == x1  && GridPane.getRowIndex(n) == y1)
                .findFirst()
                .orElse(null);
        Kwadrat node2 = (Kwadrat) grid.getChildren().stream()
                .filter(n -> GridPane.getColumnIndex(n) == x2  && GridPane.getRowIndex(n) == y2)
                .findFirst()
                .orElse(null);
        node.setVisible(false);
        node2.setVisible(false);
    }

    //Przypisanie właściwości do wszystkich kwadratów
    public void setActionToAllKwadrat(int sizeH,int sizeW){
        for (Node node : grid.getChildren()) {
            setKwadratAction((Kwadrat) node,sizeH,sizeW);
        }
    }


    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }
}