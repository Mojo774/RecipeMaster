package sample.view;

public enum WindowsName {
    ADD("view/add.fxml"),RECIPE("view/recipe.fxml"),
    SAMPLE("view/sample.fxml"),WELCOME("view/welcome.fxml");

    private String name;
    WindowsName(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
