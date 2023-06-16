/**
 * Info about this package doing something for package-info.java file.
 */
package com.mycompany.app;

/**
 * Hello world!
 */
public class App {
    /**
    * Gets the distance between tw points.
    */
    private final String message = "Hello World!";

    /**
    * Gets the distance between tw points.
    */
    public App() { }

    /**
    * Gets the distance between tw points.
    */
    public static void main(String[] args) {
        System.out.println(new App().getMessage());
    }

    private String getMessage() {
        return message;
    }

}
