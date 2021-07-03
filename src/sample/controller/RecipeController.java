package sample.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.model.MainProcess;
import sample.recipe_package.Description;
import sample.recipe_package.Ingredient;
import sample.recipe_package.Recipe;
import sample.view.WindowsName;


public class RecipeController extends Controllers {
    private Recipe recipe;

    private MainProcess mainProcess;

    public void setMainProcess(MainProcess mainProcess) {
        this.mainProcess = mainProcess;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button buttomAddRecipe;

    @FXML
    private Button buttomAllRecipes;

    @FXML
    private Button buttomBack;

    @FXML
    private TextArea ingredientsArea;

    @FXML
    private TextField nameArea;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private Button buttomChange;

    @FXML
    private Button buttonDelete;

    @FXML
    void initialize() {

        // Кнопки из панели
        buttomAddRecipe.setOnAction(actionEvent -> {

            buttomAddRecipe.getScene().getWindow().hide();
            Controllers.showWindow(WindowsName.ADD.getName());
        });

        buttomAllRecipes.setOnAction(actionEvent -> {

            buttomAllRecipes.getScene().getWindow().hide();
            Controllers.showWindow(WindowsName.SAMPLE.getName());
        });

        // Кнопка назад
        buttomBack.setOnAction(actionEvent -> {


            buttomBack.getScene().getWindow().hide();
            Controllers.showWindowBack();

        });

        // Кнопка изменить рецепт
        buttomChange.setOnAction(actionEvent -> {
            AddController cl = (AddController) Controllers.getWindows().get(WindowsName.ADD.getName()).getController();
            cl.changeRecipe(recipe);

            buttomChange.getScene().getWindow().hide();
            Controllers.showWindow(WindowsName.ADD.getName());
        });

        // Кнопка удалить рецепт
        buttonDelete.setOnAction(actionEvent -> {
            mainProcess.deleteRecipe(recipe.getIdR());

            buttonDelete.getScene().getWindow().hide();
            Controllers.showWindow(WindowsName.SAMPLE.getName());
        });
    }

    // установка рецепта для окна и его вывод в поля Area
    public void setRecipe(Recipe recipe) {

        this.recipe = recipe;
        setText();
    }

    private void setText() {
        deleteText();

        ArrayList<Ingredient> ingredients = recipe.getIngredients();
        Description description = recipe.getDescription();
        String text = description.getText();
        String name = description.getName();

        // Заполнение текстовых полей информацией рецепта

        int t = 1;
        for (Ingredient ingredient : ingredients) {
            ingredientsArea.appendText(String.valueOf(t) + ". ");
            ingredientsArea.appendText(ingredient.getString() + "\n");
            t++;
        }


        descriptionArea.appendText(text);
        nameArea.appendText(name);
    }

    private void deleteText() {
        ingredientsArea.clear();
        descriptionArea.clear();
        nameArea.clear();
    }
}
