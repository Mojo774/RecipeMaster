package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.data.DatabaseHandler;
import sample.data.IngredientHandler;

import java.io.IOException;
import java.util.*;

public class Main extends Application {
    // Хранение окон (всех)
    //private static HashMap<String, Stage> windows = new HashMap();
    private static HashMap<String, WindowClass> windows = new HashMap<>();
    // очередь открытия окон для кнопки Back
    private static ArrayList<String> queue = new ArrayList<>();


    @Override
    public void start(Stage primaryStage) throws Exception {

        createWindow("fxml/welcome.fxml");

        showWindow("fxml/welcome.fxml");

        createWindow("fxml/sample.fxml");
        createWindow("fxml/add.fxml");
        createWindow("fxml/recipe.fxml");


    }

    public static void main(String[] args) {

        launch(args);


        // to do
        DatabaseHandler.closeConnection();            // Закрытие потоков для работы с SQL
    }

    // Создание окон, и добавление их в HashMap
    private void createWindow(String fileName) throws IOException {
        Parent root;
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fileName));


        root = loader.load();
        stage.setTitle("Hello World");
        stage.setScene(new Scene(root, 1280, 768));

        windows.put(fileName, new WindowClass(stage, loader.getController()));
    }


    public static HashMap<String, WindowClass> getWindows() {
        return windows;
    }


    // Показать окно
    public static void showWindow(String nameFile) {
        try {

            windows.get(nameFile).getStage().show();
            queue.add(nameFile);

        } catch (Exception e) {
            System.out.println("wrong file name entered");
        }
    }

    // показать окно для кнопки Back
    public static boolean showIf() {
        return (queue.size()) > 1;
    }

    public static void showWindowBack() {
        queue.remove(queue.size() - 1);
        String str = queue.get(queue.size() - 1);
        queue.remove(queue.size() - 1);
        showWindow(str);


    }


}
// Ingredient_IngredientId
// Ingredient_IngredientId