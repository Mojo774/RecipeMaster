package sample;
import javafx.stage.Stage;
import sample.contrllers.Controllers;


// Класс окна
// Содержит stage и controller
public class windowClass {

    private Stage stage = new Stage();
    private Controllers controller;

    public windowClass(Stage stage, Controllers controller) {
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
