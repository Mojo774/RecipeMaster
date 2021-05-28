package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.contrllers.Controller;
import sample.contrllers.Controllers;
import sample.data.DatabaseConnector;

import java.io.IOException;
import java.util.*;

public class Main extends Application {


    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        createWindow("fxml/welcome.fxml");

        Controllers.showWindow("fxml/welcome.fxml");

        createWindow("fxml/sample.fxml");
        createWindow("fxml/add.fxml");
        createWindow("fxml/recipe.fxml");


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