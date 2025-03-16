package com.github.vmaroli.y2p;

import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Yaml2Properties {

    public Properties doYaml2PropertiesConversion(String filename) {
        try(FileInputStream fis = new FileInputStream(new File(filename))) {
            LoaderOptions options = new LoaderOptions();
            Yaml yaml = new Yaml(options);
            Map<String,Object> m = yaml.load(fis);
            Properties properties = new Properties();
            if(m != null) {
                toProperties(m, "", properties);
            }
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void toProperties(Map<String, Object> map, String parentKey, Properties properties) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = parentKey.isEmpty() ? entry.getKey() : parentKey + "." + entry.getKey();
            if (entry.getValue() instanceof Map) {
                Map<String, Object> m = (Map<String, Object>) entry.getValue();
                toProperties(m, key, properties);
            } else if(entry.getValue() instanceof List<?> list) {
                int listSize = list.size();
                for(int i = 0; i < listSize; i++) {
                    String newKey = key + "[" + i + "]";
                    if (list.get(i) instanceof Map) {
                        Map<String, Object> m = (Map<String, Object>) list.get(i);
                        toProperties(m, newKey, properties);
                    } else {
                        properties.setProperty(newKey, list.get(i).toString());
                    }
                }
            } else {
                properties.setProperty(key, entry.getValue().toString());
            }
        }
    }
}