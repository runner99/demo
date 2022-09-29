package com.runner.springbeantest.config;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

public class Serverconfig {
    static {
        InputStream in = Serverconfig.class.getClassLoader().getResourceAsStream("local.properties");
        Properties properties = new Properties ();
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Field[] fields = Serverconfig.class.getDeclaredFields ();
        Serverconfig obj = new Serverconfig ();
        for(Field field :fields){
            field.setAccessible (true);

            Object value = properties.getProperty (field.getName ());

            try {
                field.set (obj,value);
            } catch (IllegalAccessException e) {
                e.printStackTrace ();
            }
            field.setAccessible (false);
        }

    }

    public static String LOCAL;

    public static String location;

    public static String getLOCAL(){

        return LOCAL;
    }
}
