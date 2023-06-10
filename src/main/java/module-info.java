module com.grupa.projektowa.labirynt {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.grupa.projektowa.labirynt to javafx.fxml;
    exports com.grupa.projektowa.labirynt;
}