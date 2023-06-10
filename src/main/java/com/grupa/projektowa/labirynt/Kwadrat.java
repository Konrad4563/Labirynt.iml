package com.grupa.projektowa.labirynt;

import javafx.scene.shape.Rectangle;

public class Kwadrat extends Rectangle {
    public int x;
    public int y;
    public boolean colored;
    public boolean isStart;
    public boolean isEnd;

    public Kwadrat(int x, int y, double size) {
        this.x = x;
        this.y = y;
        this.colored=false;
        this.isStart=false;
        this.isEnd=false;
        this.setWidth(size);
        this.setHeight(size);
        this.setStyle("-fx-text-box-border: transparent;");
    }

    public int getXValue() {
        return x;
    }

    public void setXValue(int x) {
        this.x = x;
    }

    public int getYValue() {
        return y;
    }

    public void setYValue(int y) {
        this.y = y;
    }

    public boolean isColored() {
        return colored;
    }

    public void setColored(boolean colored) {
        this.colored = colored;
    }

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }
}
