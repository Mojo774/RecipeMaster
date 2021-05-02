package sample.contrllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import sample.data.RecipeHandler;
import sample.Main;
import sample.recipe_package.*;
import javafx.scene.text.Text;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;


import javafx.scene.control.TableColumn.CellEditEvent;

import javafx.scene.control.TextField;
import sample.recipe_service.RecipesHelper;


public class AddController implements Controllers {
    private int number = 1;
    private List<ingredientView> views = new ArrayList<>();

    ObservableList<AddController.ingredientView> viewList;
    private int changeId = -1; // Флаг для пометки редактируемого рецепта (если флаг поднят то надо удалить рецепт)
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
    private TextArea textAreaDescription;

    @FXML
    private Button buttomAdd;

    @FXML
    private TextField textFieldName;

    @FXML
    private TableView<ingredientView> tableIngredients;

    @FXML
    private TableColumn<ingredientView, Integer> columnNumber;

    @FXML
    private TableColumn<ingredientView, String> columnName;

    @FXML
    private TableColumn<ingredientView, String> columnSize;

    @FXML
    private Button buttomAddIngredient;

    @FXML
    private Text text;

    @FXML
    void initialize() {

        // Кнопки из панели
        // Кнопка allRecipes
        buttomAllRecipes.setOnAction(actionEvent -> {
            if (changeId != -1) {
                clear();
                changeId = -1;
            }


            buttomAllRecipes.getScene().getWindow().hide();
            Main.showWindow("fxml/sample.fxml");
        });

        // Кнопка назад
        buttomBack.setOnAction(actionEvent -> {
            if (changeId != -1) {
                clear();
                changeId = -1;
            }


            if (Main.showIf()) {
                buttomBack.getScene().getWindow().hide();
                Main.showWindowBack();
            }

        });


        // Кнопки страницы
        // Кнопка добавления рецепта
        buttomAdd.setOnAction(actionEvent -> {


            // Если рецепт создался, то добавляем его
            if (isCreateRecipe()) {

                // Добавление рецепта
                new RecipesHelper().addRecipe(recipe);

                // Очистка страницы
                clear();

                // Выход из окна в главное меню
                buttomAdd.getScene().getWindow().hide();
                Main.showWindow("fxml/sample.fxml");

            } else {
                text.setText("Fill in areas");
            }

        });

        // Добавить пустой ингередиент
        buttomAddIngredient.setOnAction(actionEvent -> {
            formatView();

            ingredientView ingredientView = new ingredientView(number++, "", "");
            views.add(ingredientView);

            viewRows();

        });


        // Редактирование полей в таблице
        columnName.setCellFactory(TextFieldTableCell.forTableColumn());
        columnName.setOnEditCommit(
                new EventHandler<CellEditEvent<AddController.ingredientView, String>>() {
                    @Override
                    public void handle(CellEditEvent<AddController.ingredientView, String> t) {
                        ((AddController.ingredientView) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setName(t.getNewValue());
                    }
                }

        );
        columnSize.setCellFactory(TextFieldTableCell.forTableColumn());
        columnSize.setOnEditCommit(
                new EventHandler<CellEditEvent<AddController.ingredientView, String>>() {
                    @Override
                    public void handle(CellEditEvent<AddController.ingredientView, String> t) {
                        ((AddController.ingredientView) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setSize(t.getNewValue());
                    }
                }
        );


    }

    // Ввод данных в таблицу
    private void viewRows() {
        viewList = FXCollections.observableList(views);

        columnNumber.setCellValueFactory(
                new PropertyValueFactory<AddController.ingredientView, Integer>("number")
        );

        columnName.setCellValueFactory(
                new PropertyValueFactory<AddController.ingredientView, String>("name")
        );
        columnSize.setCellValueFactory(
                new PropertyValueFactory<AddController.ingredientView, String>("size")
        );

        tableIngredients.setItems(viewList);
    }

    // Сбросить переменные для вывода в таблицу
    private void resetView() {
        number = 1;
        views = new ArrayList<>();
    }

    // Удаление пустых ингредиентов (у тех, у кого name пустое)
    // И изменеие порядка вывода
    private void formatView() {
        number = 1;

        views.removeIf(x -> x.getName().equals(""));

        views.forEach(x -> {
            x.setNumber(number++);
        });

    }

    public void clear() {
        resetView();
        deleteText();
        text.setText("");
    }

    // Удаление текста из TextArea
    public void deleteText() {
        textAreaDescription.clear();
        textFieldName.clear();

        viewList = FXCollections.observableList(views);
        tableIngredients.setItems(viewList);
    }


    // Создание id для рецепта
    private static int getIdR() {
        int idR = RecipeHandler.getLastId() + 1;

        // Если база данных пустая и мы начнем создавать новые рецепты
        // метод сверху будет всегда возвращать 0 пока мы не внесем
        // хотя бы один рецепт в базу данных
        while (new RecipesHelper().getRecipes(idR).size() > 0) {
            if (new RecipesHelper().getRecipes(idR).get(0) != null)
            idR++;
        }


        return idR;
    }

    // Создать рецепт если получится
    public boolean isCreateRecipe() {
        formatView();

        // Считывается текст из TextAreas
        String descriptionText = textAreaDescription.getText().trim();
        String name = textFieldName.getText().trim();

        ArrayList<Ingredient> ingredients = new ArrayList<>();
        Description description = new Description(descriptionText, name);

        views.forEach(x -> {
            if (x.getSize() != null) {
                if (x.getSize().equals(""))
                    x.setSize(null);
            }
            Ingredient ingredient = new Ingredient(x.name, x.size);
            ingredients.add(ingredient);
        });

        // Проверка на пустные строки
        if ((ingredients.size() != 0 &&
                !description.getText().equals("") &&
                !description.getName().equals(""))) {

            // Если рецепт отредактирован - удалить старый
            if (changeId != -1) {
                RecipeHandler.deleteRecipes(changeId);
                recipe = new Recipe(description, ingredients, changeId);
                changeId = -1;
            } else {
                recipe = new Recipe(description, ingredients, getIdR());
            }
            return true;

        } else
            return false;
    }

    // Изменить рецепт
    public void changeRecipe(Recipe recipe) {
        resetView();
        deleteText();

        ArrayList<Ingredient> ingredients = recipe.getIngredients();
        Description description = recipe.getDescription();
        int idR = recipe.getIdR();

        // Создание списка ингредиентов для ввода в таблицу
        for (Ingredient ingredient : ingredients) {

            views.add(new ingredientView(-1, ingredient.getName(), ingredient.getSize()));

        }
        formatView(); // Добавление номеров

        textAreaDescription.setText(description.getText());
        textFieldName.setText(description.getName());
        changeId = idR;

        viewRows();

    }

    // Нужен для отображения в таблице
    public class ingredientView {
        private int number;
        private String name;
        private String size;

        public ingredientView(int number, String name, String size) {
            this.number = number;
            this.name = name;
            this.size = size;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int id) {
            this.number = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }
    }
}
