package sample.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import sample.model.MainProcess;
import sample.view.WindowsName;


public class WelcomeController extends Controllers {

    private MainProcess mainProcess;

    public void setMainProcess(MainProcess mainProcess) {
        this.mainProcess = mainProcess;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Text loginText;

    @FXML
    private Text passwordText;

    @FXML
    private Button buttonLogIn;

    @FXML
    private TextField registrLoginField;

    @FXML
    private PasswordField registrPasswordField;

    @FXML
    private Text registrLoginText;

    @FXML
    private Text registrPasswordText;

    @FXML
    private Button buttonSignUp;

    @FXML
    private PasswordField registrRepeatPasswordField;

    @FXML
    private Button buttonEnterWithoutLogin;

    @FXML
    void initialize() {


        buttonLogIn.setOnAction(actionEvent -> {
            String login = loginField.getText();
            String password = passwordField.getText();

            if (!isPasswordOrLoginOk(login))
                loginText.setText("Invalid login. Only 0-9a-zA-Z and length [4-10]");
            else
                loginText.setText("");

            if (!isPasswordOrLoginOk(password))
                passwordText.setText("Invalid password. Only 0-9a-zA-Z and length [4-10]");
            else
                passwordText.setText("");


            if (isPasswordOrLoginOk(login) && isPasswordOrLoginOk(password)) {
                if (!mainProcess.findUser(login, password)) {
                    loginText.setText("Wrong login or password");
                } else {
                    mainProcess.setUser(login, password);
                    buttonLogIn.getScene().getWindow().hide();
                    Controllers.showWindow(WindowsName.SAMPLE.getName());
                }
            }

        });

        buttonEnterWithoutLogin.setOnAction(actionEvent -> {
            if (!mainProcess.findUser("Default", "0000"))
                mainProcess.addUser("Default", "0000");

            mainProcess.setUser("Default", "0000");
            buttonEnterWithoutLogin.getScene().getWindow().hide();
            Controllers.showWindow(WindowsName.SAMPLE.getName());
        });

        buttonSignUp.setOnAction(actionEvent -> {
            String login = registrLoginField.getText();
            String password = registrPasswordField.getText();
            String repeatPassword = registrRepeatPasswordField.getText();

            if (!isPasswordOrLoginOk(login))
                registrLoginText.setText("Invalid login. Only 0-9a-zA-Z and length [4-10]");
            else
                registrLoginText.setText("");

            if (!isPasswordOrLoginOk(password))
                registrPasswordText.setText("Invalid password. Only 0-9a-zA-Z and length [4-10]");
            else
                registrPasswordText.setText("");

            if (!password.equals(repeatPassword))
                registrPasswordText.setText("Password mismatch");
            else
                registrPasswordText.setText("");

            if (isPasswordOrLoginOk(login) && isPasswordOrLoginOk(password) && password.equals(repeatPassword)) {
                if (mainProcess.findUser(login, password)) {
                    registrLoginText.setText("Login is used");
                } else {
                    mainProcess.addUser(login, password);
                    mainProcess.setUser(login, password);

                    buttonSignUp.getScene().getWindow().hide();
                    Controllers.showWindow(WindowsName.SAMPLE.getName());
                }
            }
        });
    }

    private boolean isPasswordOrLoginOk(String text) {
        if (text.equals(""))
            return false;

        if (text.length() < 4 || text.length() > 10)
            return false;

        if (text.matches("^[0-9a-zA-Z]*$"))
            return true;

        return false;
    }


}
