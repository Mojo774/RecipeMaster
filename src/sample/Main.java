package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.controller.Controllers;
import sample.model.MainProcess;
import sample.view.WindowsName;


import java.io.IOException;
import java.util.logging.*;

public class Main extends Application {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    public static Handler fileHandler;
    private MainProcess mainProcess;

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        mainProcess = new MainProcess();

        createWindow(WindowsName.WELCOME.getName(),mainProcess);

        Controllers.showWindow(WindowsName.WELCOME.getName());

        createWindow(WindowsName.SAMPLE.getName(),mainProcess);
        createWindow(WindowsName.ADD.getName(),mainProcess);
        createWindow(WindowsName.RECIPE.getName(), mainProcess);


    }

    @Override
    public void stop() throws Exception {

        logger.info("END program");
        mainProcess.close();
        super.stop();
    }

    public static void main(String[] args) {
        // Настройка логера
        try {
            // Указывается куда записываются логи
            fileHandler = new FileHandler("%h/IdeaProjects/RecipeMaster_1.1/src/sample/assets/recipeMasterLog.log");
            fileHandler.setFormatter(new MyFormatter());
            logger.addHandler(fileHandler);


            // Запрет вывода в консоль
            logger.setUseParentHandlers(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("Start program");


        launch(args);
    }

    // Создание окон, и добавление их в HashMap
    private void createWindow(String fileName,MainProcess mainProcess) throws IOException {
        Parent root;
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fileName));

        root = loader.load();
        stage.setTitle("Hello World");
        stage.setScene(new Scene(root, 1280, 768));

        Controllers controller = loader.getController();
        controller.setMainProcess(mainProcess);

        Controllers.getWindows().put(fileName, new WindowClass(stage, loader.getController()));
    }

    private static class MyFormatter extends Formatter {
        @Override
        public String format(LogRecord record) {
            String result = String.format("%s\n%s : %s\n",
                    record.getLoggerName(), record.getLevel(), record.getMessage());

            return result;
        }
    }

}