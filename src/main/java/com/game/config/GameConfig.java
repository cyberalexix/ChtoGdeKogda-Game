package com.game.config;

import org.springframework.context.annotation.Configuration;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

@Configuration
public class GameConfig {
    ResourceBundle resourceBundle;
    Map<String, String> resources = new HashMap<>();

    public GameConfig() {
        resourceBundle = ResourceBundle.getBundle("configuration");
        Enumeration<String> keys = resourceBundle.getKeys();
        String element;
        while(keys.hasMoreElements()) {
            element = keys.nextElement();
            resources.put(element, resourceBundle.getString(element));
        }
    }

    public String getValue(String key) {
        return resources.get(key);
    }
}
