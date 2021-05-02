package sample.recipe_package;

public class Ingredient {
    private String name;
    private String size = null;

    public Ingredient(String name, String size) {
        this.name = name;
        this.size = size;
    }

    public Ingredient(String name) {
        this.name = name;
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

    public String getString(){
        String str;

        if (size != null)
        str = name + " Кол-во " + size;
        else str = name;

        return str;
    }
}
