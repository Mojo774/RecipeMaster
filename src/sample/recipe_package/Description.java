package sample.recipe_package;

public class Description {
    private String text;
    private String name;

    public Description(String text, String name) {
        this.text = text;
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void Print(){
        System.out.println(String.format("%s:\n",this.name));

        System.out.println(text);
    }

}
