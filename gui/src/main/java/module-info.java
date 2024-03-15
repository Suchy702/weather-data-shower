module gui {
    requires client;

    requires javafx.controls;
    requires javafx.fxml;


    opens pl.edu.pwr.tkubik.jp.lab04.gui to javafx.fxml;
    exports pl.edu.pwr.tkubik.jp.lab04.gui;
}