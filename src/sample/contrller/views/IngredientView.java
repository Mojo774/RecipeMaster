package sample.contrller.views;

public class IngredientView {
    private int number;
    private String name;
    private String size;

    public IngredientView(int number, String name, String size) {
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
