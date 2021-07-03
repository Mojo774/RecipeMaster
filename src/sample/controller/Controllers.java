package sample.controller;

import sample.Main;
import sample.WindowClass;
import sample.model.MainProcess;

import java.util.*;
import java.util.logging.Logger;


public abstract class Controllers {

    // Хранение окон (всех)
    private static HashMap<String, WindowClass> windows = new HashMap<>();

    // очередь открытия окон для кнопки Back
    private static ArrayDeque<String> queue = new ArrayDeque<>();

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
    public static void showWindowBack() {
        /*queue.remove(queue.size() - 1);
        String str = queue.get(queue.size() - 1);
        queue.remove(queue.size() - 1);*/

        if (!queue.isEmpty()) {
            queue.removeLast();
        }

        String str = queue.pollLast();
        showWindow(str);


    }

    public static HashMap<String, WindowClass> getWindows() {
        return windows;
    }


    public abstract void setMainProcess(MainProcess mainProcess);
}
