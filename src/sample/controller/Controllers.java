package sample.controller;

import sample.Main;
import sample.WindowClass;
import sample.model.MainProcess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

// Интерфейс-флаг всех контроллеров, нужен для хранения данных о окнах
public abstract class Controllers {


    // addscoped()
    public static MainProcess mainProcess = new MainProcess();

    // Хранение окон (всех)
    static HashMap<String, WindowClass> windows = new HashMap<>();
    // очередь открытия окон для кнопки Back
    private static ArrayList<String> queue = new ArrayList<>();

    // Logger
    protected static final Logger logger = Logger.getLogger(Controllers.class.getName());
    static {
        logger.addHandler(Main.fileHandler);
    }


    // Показать окно
    public static void showWindow(String nameFile) {
        try {

            getWindows().get(nameFile).getStage().show();
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

    public static HashMap<String, WindowClass> getWindows() {
        return windows;
    }
}
