package sample;

public class User {

    private static int id;
    private static String name;
    private static String password;

    private User(){

    }
    // Pizza
    public static void setUser(int id, String name, String password){
        User.id = id;
        User.name = name;
        User.password = password;
    }

    public static void print(){
        System.out.println(id + " " + name + " " + password);
    }

    public static int getId() {
        return id;
    }

    public static String getName() {
        return name;
    }

    public static String getPassword() {
        return password;
    }
}
