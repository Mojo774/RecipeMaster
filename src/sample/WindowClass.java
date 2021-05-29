package sample;

import javafx.stage.Stage;
import sample.contrller.Controllers;


// Класс окна
// Содержит stage и controller
public class WindowClass {

    private Stage stage = new Stage();
    private Controllers controller;

    public WindowClass(Stage stage, Controllers controller) {
        this.stage = stage;
        this.controller = controller;
    }

    public Stage getStage() {
        return stage;
    }

    public Controllers getController() {
        return controller;
    }
}
