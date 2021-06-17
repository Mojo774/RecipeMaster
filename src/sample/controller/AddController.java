package sample.controller;

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
import sample.recipe_package.*;
import javafx.scene.text.Text;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;


import javafx.scene.control.TableColumn.CellEditEvent;

import javafx.scene.control.TextField;
import sample.controller.views.IngredientView;
import sample.controller.views.IngredientViewHelper;
import sample.view.WindowsName;


public class AddController extends Controllers {
    // Список ингредиентов для таблицы
    private List<IngredientView> views = new ArrayList<>();

    // Объект для вывода в таблицу ингредиентов
    private ObservableList<IngredientView> viewList = FXCollections.observableList(views);

    // Флаг для пометки редактируемого рецепта (если рецепт уже существует, то надо заменить его)
    private int changeId = -1;


    public AddController() {
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
    private TextArea textAreaDescription;

    @FXML
    private Button buttomAdd;

    @FXML
    private TextField textFieldName;

    @FXML
    private TableView<IngredientView> tableIngredients;

    @FXML
    private TableColumn<IngredientView, Integer> columnNumber;

    @FXML
    private TableColumn<IngredientView, String> columnName;

    @FXML
    private TableColumn<IngredientView, String> columnSize;

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
            Controllers.showWindow(WindowsName.SAMPLE.getName());
        });

        // Кнопка назад
        buttomBack.setOnAction(actionEvent -> {
            if (changeId != -1) {
                clear();
                changeId = -1;
            }


            if (Controllers.showIf()) {
                buttomBack.getScene().getWindow().hide();
                Controllers.showWindowBack();
            }

        });


        // Кнопки страницы
        // Кнопка добавления рецепта
        buttomAdd.setOnAction(actionEvent -> {

            String textDescription = textAreaDescription.getText().trim();
            String textName = textFieldName.getText().trim();


            // Присвоение текущего views (которое ввел пользователь)
            views = viewList;
            // Создание рецепта
            if (!textDescription.equals("") && !textName.equals("") && isOkViews(views)) {

                mainProcess.createRecipe(textName, textDescription, views, changeId);

                // Очистка страницы
                clear();

                // Выход из окна в главное меню
                buttomAdd.getScene().getWindow().hide();
                Controllers.showWindow(WindowsName.SAMPLE.getName());

            } else {
                text.setText("Fill in areas");
            }

        });

        // Добавить пустой ингередиент
        buttomAddIngredient.setOnAction(actionEvent -> {
            // Создается пустой views и в него добавляются ингредиенты из viewList (то что ввел юзер)
            // Потом добавляется новый пустой ингредиент
            // viewList присваивает значение views и выводится на в таблицу

            views = new IngredientViewHelper().addEmptyIngredient(viewList);
            setViews(views);
            setViewTable();
        });


        // Редактирование полей в таблице
        columnName.setCellFactory(TextFieldTableCell.forTableColumn());
        columnName.setOnEditCommit(
                new EventHandler<CellEditEvent<IngredientView, String>>() {
                    @Override
                    public void handle(CellEditEvent<IngredientView, String> t) {
                        ((IngredientView) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setName(t.getNewValue());
                    }
                }

        );
        columnSize.setCellFactory(TextFieldTableCell.forTableColumn());
        columnSize.setOnEditCommit(
                new EventHandler<CellEditEvent<IngredientView, String>>() {
                    @Override
                    public void handle(CellEditEvent<IngredientView, String> t) {
                        ((IngredientView) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setSize(t.getNewValue());
                    }
                }
        );


    }

    // Проверяет правильно ли записаны ингредиенты в таблице
    private boolean isOkViews(List<IngredientView> views) {
        if (views.size() > 0) {

            for (IngredientView ingredient : views) {
                if (ingredient.getName().equals("") && !ingredient.getSize().equals(""))
                    return false;
            }

            return true;

        } else
            return false;
    }

    // Ввод данных в таблицу
    private void setViewTable() {

        columnNumber.setCellValueFactory(
                new PropertyValueFactory<IngredientView, Integer>("Number")
        );

        columnName.setCellValueFactory(
                new PropertyValueFactory<IngredientView, String>("Name")
        );
        columnSize.setCellValueFactory(
                new PropertyValueFactory<IngredientView, String>("Size")
        );

        tableIngredients.setItems(viewList);
    }


    // Устанавливает новый список ингредиентов для вывода в таблицу
    public void setViews(List<IngredientView> views) {
        viewList = FXCollections.observableList(views);
    }

    // Очистка страницы
    public void clear() {
        deleteText();
        text.setText("");
        changeId = -1;

        var views = new ArrayList<IngredientView>();
        setViews(views);
        setViewTable();
    }

    // Удаление текста из TextArea
    public void deleteText() {
        textAreaDescription.clear();
        textFieldName.clear();
    }


    // Изменить рецепт
    public void changeRecipe(Recipe recipe) {
        deleteText();

        // Создание списка ингредиентов для ввода в таблицу
        var views = new IngredientViewHelper(recipe).getViews();
        setViews(views);
        setViewTable();

        // Заполнение текстовых полей
        textAreaDescription.setText(recipe.getDescription().getText());
        textFieldName.setText(recipe.getDescription().getName());

        // Установление id рецепта
        changeId = recipe.getIdR();
    }


}
