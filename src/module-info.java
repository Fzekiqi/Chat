module FxTest {

    requires javafx.fxml;
    requires javafx.controls;
    requires java.xml;

    opens sample;
    opens sample.chat;

}