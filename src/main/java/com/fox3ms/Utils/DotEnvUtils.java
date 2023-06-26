package com.fox3ms.Utils;

import io.github.cdimascio.dotenv.Dotenv;
public class DotEnvUtils {
    private static Dotenv environment;
    // CONSTANT values, that
    public static final String JDA_TOKEN_PROPERTY_NAME = "TOKEN";
    public static final String JDA_TOKEN_PROPERTY_DEFAULT = "123456789090abcdefghijklkmnop";
    // Static Initialization block to load the Dotenv.load() and ensure the `environment` is not null.
    // This block delgates the initialization of the environment to Dotenv.
    static {
        environment = Dotenv.load();
    }
    // Get a single property by string name as a string.
    public static String getProperty(String propertyName){
        return environment.get(propertyName);
    }


    // Get the Token property, or the default, based on if the value is in the environment.
    public static String getToken(){
        // TODO: Possible opportunity to inspect environment and determine host to choose which ENV variable to pull in.
        // Read the value from dotenv
        String value =  environment.get(JDA_TOKEN_PROPERTY_NAME);
        if(value != null) { // Nullcheck, and return.
            return value;
        }
        else { // Null check failed, return default.
            return JDA_TOKEN_PROPERTY_DEFAULT;
        }
    }

}
