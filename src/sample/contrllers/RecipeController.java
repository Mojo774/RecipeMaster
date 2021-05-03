package sample.contrllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.data.RecipeHandler;
import sample.Main;
import sample.recipe_package.Description;
import sample.recipe_package.Ingredient;
import sample.recipe_package.Recipe;


public class RecipeController implements Controllers {
    private Recipe recipe;

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
            Main.showWindow("fxml/add.fxml");
        });

        buttomAllRecipes.setOnAction(actionEvent -> {

            buttomAllRecipes.getScene().getWindow().hide();
            Main.showWindow("fxml/sample.fxml");
        });

        // Кнопка назад
        buttomBack.setOnAction(actionEvent -> {

            if (Main.showIf()) {
                buttomBack.getScene().getWindow().hide();
                Main.showWindowBack();
            }
        });

        // Кнопка изменить рецепт
        buttomChange.setOnAction(actionEvent -> {
            AddController cl = (AddController) Main.getWindows().get("fxml/add.fxml").getController();
            cl.changeRecipe(recipe);

            buttomChange.getScene().getWindow().hide();
            Main.showWindow("fxml/add.fxml");
        });

        // Кнопка удалить рецепт
        buttonDelete.setOnAction(actionEvent -> {
            RecipeHandler.deleteRecipes(recipe.getIdR());

            buttonDelete.getScene().getWindow().hide();
            Main.showWindow("fxml/sample.fxml");
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
