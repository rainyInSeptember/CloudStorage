package controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@PropertySource("classpath:jdbc.properties")
public class Test {

    @Autowired
    private static Environment env;
    public static void main(String[] args) {
        System.out.println(env.getProperty("hdfsdAddress"));
    }
}
