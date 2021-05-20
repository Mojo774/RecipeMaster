package sample;

public class User {

    private int id;
    private String name;
    private String password;

    public User() {
    }

    public User(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public void setUser(int id, String name, String password){
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public void print(){
        System.out.println(id + " " + name + " " + password);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
