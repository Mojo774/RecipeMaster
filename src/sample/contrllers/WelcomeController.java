package sample.contrllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import sample.data.UserHandler;
import sample.Main;

public class WelcomeController implements Controllers {

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
                if (! new UserHandler().setUser(login, password)) {
                    loginText.setText("Wrong login or password");
                } else {
                    buttonLogIn.getScene().getWindow().hide();
                    Main.showWindow("fxml/sample.fxml");
                }
            }

        });

        buttonEnterWithoutLogin.setOnAction(actionEvent -> {
            if (! new UserHandler().findUser("Default", "0000"))
                new UserHandler().addUser("Default", "0000");

            new UserHandler().setUser("Default", "0000");
            buttonEnterWithoutLogin.getScene().getWindow().hide();
            Main.showWindow("fxml/sample.fxml");
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
                if (new UserHandler().findUser(login, password)) {
                    registrLoginText.setText("Login is used");
                } else {
                    new UserHandler().addUser(login, password);
                    new UserHandler().setUser(login, password);

                    buttonSignUp.getScene().getWindow().hide();
                    Main.showWindow("fxml/sample.fxml");
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
