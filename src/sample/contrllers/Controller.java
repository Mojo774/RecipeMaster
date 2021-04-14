package sample.contrllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import sample.Data.DatabaseHandler;
import sample.Main;
import sample.Recipe_Package.All_recipes;
import sample.Recipe_Package.Recipe;

public class Controller implements Controllers {
    private static ArrayList<Recipe> recipes;
    // Табличные сведения о рецепте
    private static ArrayList<Recipe.tableView> view = new ArrayList<>();


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
    private Button buttomApdate;

    @FXML
    private TableView<Recipe.tableView> table;

    @FXML
    private TableColumn<Recipe.tableView, Integer> tableNumber;

    @FXML
    private TableColumn<Recipe.tableView, String> tableName;

    @FXML
    private TableColumn<Recipe.tableView, String> tableIngredient;

    @FXML
    void initialize() {

        // Кнопки из панели
        buttomAddRecipe.setOnAction(actionEvent -> {
            buttomAddRecipe.getScene().getWindow().hide();
            Main.showWindow("fxml/add.fxml");
        });

        // Кнопка обновление
        buttomApdate.setOnAction(actionEvent -> {
            upDate(); // Обновление данных

            // Введение данных в таблицу
            ObservableList<Recipe.tableView> viewList = FXCollections.observableList(view);
            tableNumber.setCellValueFactory(
                    new PropertyValueFactory<Recipe.tableView, Integer>("number")
            );

            tableName.setCellValueFactory(
                    new PropertyValueFactory<Recipe.tableView, String>("name")
            );
            tableIngredient.setCellValueFactory(
                    new PropertyValueFactory<Recipe.tableView, String>("ingredientStr")
            );

            table.setItems(viewList);

        });

        // Запись в базу данных
        buttomAllRecipes.setOnAction(actionEvent -> {

            DatabaseHandler.signUpRecipes();
        });

        // Нажатие на строку в таблице
        table.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                TableRow row;
                // Проверяется что кликнул по столбцу, а потом что он не пустой
                Node node = ((Node) event.getTarget()).getParent();
                if (node instanceof TableRow && !((TableRow<?>) node).isEmpty()) {
                    row = (TableRow) node;
                    // Нажатие

                    // Получаем индекс строки, она равна номеру строки, который я задавал
                    int num = row.getIndex() +1; // +1 по

                    int id = -1;
                    // Получаем id по number
                    for (Recipe.tableView v : view) {
                        if (v.getNumber() == num)
                            id = v.getId();
                    }

                    // Получаем рецепт по id
                    Recipe recipe = All_recipes.searchId(id);

                    // Передаем его в окно (оно уже открыто в Main, поэтому берем его в windows)
                    recipeController cl = (recipeController) Main.getWindows().get("fxml/recipe.fxml").getController();
                    cl.setRecipe(recipe);
                    // Закрываем это окно и переходим в новое
                    table.getScene().getWindow().hide();
                    Main.showWindow("fxml/recipe.fxml");
                }
            }
        });

        // Кнопка назад
        buttomBack.setOnAction(actionEvent -> {
            if (Main.showIf()) {
                buttomBack.getScene().getWindow().hide();
                Main.showWindowBack();
            }
        });

    }

    // Обновляет список рецептов и создает их табличный список
    private static void upDate() {
        All_recipes.loudRecipes(); // загрузка рецептов из базы данных

        recipes = All_recipes.getRecipes();
        view = new ArrayList<>();

        recipes.forEach(x -> {
            view.add(x.getTableView());
        });

        int t = 1;
        for (Recipe.tableView v : view) {
            v.setNumber(t++);
        }

    }


}
