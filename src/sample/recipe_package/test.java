package sample.recipe_package;


import java.util.ArrayDeque;
import java.util.Queue;

public class test {
    public static void main(String[] args) {
        //new UserHandler().addUser("Default","0000");

        ArrayDeque<String> q = new ArrayDeque<>();

        q.add("1");
        q.add("2");
        q.add("3");

        System.out.println(q);

        System.out.println("q = " + q.pollLast());

        System.out.println(q);
    }
}
