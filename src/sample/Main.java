package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Data.DatabaseHandler;
import sample.Data.IngredientHandler;
import sample.Data.UserHandler;

import java.util.*;

public class Main extends Application {
    // Хранение окон (всех)
    //private static HashMap<String, Stage> windows = new HashMap();
    private static HashMap<String,windowClass> windows = new HashMap<>();
    // очередь открытия окон для кнопки Back
    private static ArrayList<String> queue = new ArrayList<>();


    @Override
    public void start(Stage primaryStage) throws Exception {
        // Создание окон, и добавление их в HashMap
        String fileName;
        Parent root;
        Stage stage;
        FXMLLoader loader;

        stage = primaryStage;
        fileName = "fxml/sample.fxml";
        loader = new FXMLLoader(getClass().getResource(fileName));
        root = loader.load();
        stage.setTitle("Hello World");
        stage.setScene(new Scene(root, 1280, 768));
        windows.put(fileName, new windowClass(stage,loader.getController()));

        showWindow("fxml/sample.fxml");


        stage = new Stage();
        fileName = "fxml/add.fxml";
        loader = new FXMLLoader(getClass().getResource(fileName));
        root = loader.load();
        stage.setTitle("Hello World");
        stage.setScene(new Scene(root, 1280, 768));
        windows.put(fileName, new windowClass(stage,loader.getController()));



        stage = new Stage();
        fileName = "fxml/recipe.fxml";
        loader = new FXMLLoader(getClass().getResource(fileName));
        root = loader.load();
        stage.setTitle("Hello World");
        stage.setScene(new Scene(root, 1280, 768));
        windows.put(fileName, new windowClass(stage,loader.getController()));
    }

    public static HashMap<String,windowClass> getWindows() {
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

    public static void main(String[] args) {
        UserHandler.setUser("Biba Boba","14881488"); // Установка юзера

        launch(args);

        IngredientHandler.deleteUselessIngredients(); // Удаление ненужных ингредиентов из БД
        DatabaseHandler.closeConnection();            // Закрытие потоков для работы с SQL
    }
}
// Ingredient_IngredientId
// Ingredient_IngredientId