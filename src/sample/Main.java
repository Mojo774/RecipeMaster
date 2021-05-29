package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.controller.Controllers;
import sample.view.WindowsName;


import java.io.IOException;

public class Main extends Application {


    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        createWindow(WindowsName.WELCOME.getName());

        Controllers.showWindow(WindowsName.WELCOME.getName());

        createWindow(WindowsName.SAMPLE.getName());
        createWindow(WindowsName.ADD.getName());
        createWindow(WindowsName.RECIPE.getName());


    }

    @Override
    public void stop() throws Exception {
        Controllers.mainProcess.closeConnection();            // Закрытие потоков для работы с SQL

        super.stop();
    }

    public static void main(String[] args) {

        launch(args);

    }

    // Создание окон, и добавление их в HashMap
    private void createWindow(String fileName) throws IOException {
        Parent root;
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fileName));

        root = loader.load();
        stage.setTitle("Hello World");
        stage.setScene(new Scene(root, 1280, 768));

        Controllers.getWindows().put(fileName, new WindowClass(stage, loader.getController()));
    }


}