package sample.controller;

import sample.WindowClass;
import sample.model.MainProcess;

import java.util.ArrayList;
import java.util.HashMap;

// Интерфейс-флаг всех контроллеров, нужен для хранения данных о окнах
public interface Controllers {

    // Окна
    String addWindow = "view/add.fxml";
    String recipeWindow = "view/recipe.fxml";
    String sampleWindow = "view/sample.fxml";
    String welcomeWindow = "view/welcome.fxml";

    // addscoped()
    MainProcess mainProcess = new MainProcess();

    // Хранение окон (всех)
    HashMap<String, WindowClass> windows = new HashMap<>();
    // очередь открытия окон для кнопки Back
    ArrayList<String> queue = new ArrayList<>();


    // Показать окно
    static void showWindow(String nameFile) {
        try {

            getWindows().get(nameFile).getStage().show();
            queue.add(nameFile);

        } catch (Exception e) {
            System.out.println("wrong file name entered");
        }
    }

    // показать окно для кнопки Back
    static boolean showIf() {
        return (queue.size()) > 1;
    }

    static void showWindowBack() {
        queue.remove(queue.size() - 1);
        String str = queue.get(queue.size() - 1);
        queue.remove(queue.size() - 1);
        showWindow(str);


    }

    static HashMap<String, WindowClass> getWindows() {
        return windows;
    }
}
