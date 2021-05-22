package sample.contrllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
import sample.Main;
import sample.contrllers.views.RecipeView;
import sample.contrllers.views.RecipeViewHelper;
import sample.recipe_package.Recipe;

public class Controller implements Controllers {

    // Список рецептов для таблицы
    private List<RecipeView> views = new ArrayList<>();

    // Объект для вывода в таблицу ингредиентов
    private ObservableList<RecipeView> viewList = FXCollections.observableList(views);

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
    private TableView<RecipeView> table;

    @FXML
    private TableColumn<RecipeView, Integer> tableNumber;

    @FXML
    private TableColumn<RecipeView, String> tableName;

    @FXML
    private TableColumn<RecipeView, String> tableIngredient;

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
            viewList = FXCollections.observableList(views);
            tableNumber.setCellValueFactory(
                    new PropertyValueFactory<RecipeView, Integer>("number")
            );

            tableName.setCellValueFactory(
                    new PropertyValueFactory<RecipeView, String>("name")
            );
            tableIngredient.setCellValueFactory(
                    new PropertyValueFactory<RecipeView, String>("ingredientStr")
            );

            table.setItems(viewList);

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

                    // Получаем индекс строки
                    int num = row.getIndex() +1; // +1 потому что в javaFx первая строка = 0, а у меня она = 1

                    int idR = -1;
                    // Получаем id по number
                    for (RecipeView v : views) {
                        if (v.getNumber() == num)
                            idR = v.getIdR();
                    }

                    // Получаем рецепт по id
                    Recipe recipe = mainProcess.getRecipe(idR);

                    // Передаем его в окно (оно уже открыто в Main, поэтому берем его в windows)
                    RecipeController cl = (RecipeController) Main.getWindows().get("fxml/recipe.fxml").getController();
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
    private void upDate(){

        ArrayList<Recipe> recipes = mainProcess.getRecipesUser(mainProcess.getIdUser());

        views = new RecipeViewHelper().getViews(recipes);

    }


}
