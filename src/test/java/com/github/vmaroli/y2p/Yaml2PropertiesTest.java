package com.github.vmaroli.y2p;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Properties;

class Yaml2PropertiesTest {

    @Test
    public void testDoYaml2PropertiesConversionEmptyYamlFile() {
        Yaml2Properties y2p = new Yaml2Properties();
        Properties properties = y2p.doYaml2PropertiesConversion("src/test/resources/empty.yaml");
        properties.stringPropertyNames().forEach(key -> System.out.println(key+"="+properties.getProperty(key)));
    }

    @Test
    public void testDoYaml2PropertiesConversionNonExistingFile() {
        Yaml2Properties y2p = new Yaml2Properties();
        Assertions.assertThrows(RuntimeException.class, () -> y2p.doYaml2PropertiesConversion("src/test/resources/invalid.yaml"));
    }

    @Test
    public void testDoYaml2PropertiesConversion() {
        Yaml2Properties y2p = new Yaml2Properties();
        Properties properties = y2p.doYaml2PropertiesConversion("src/test/resources/sample.yaml");
        print(properties);
        Assertions.assertEquals(getExpectedProperties(),properties);
    }

    private void print(Properties properties) {
        List<String> list = properties.stringPropertyNames().stream()
                .map(key -> key + "=" + properties.getProperty(key))
                .sorted()
                .toList();
        list.forEach(System.out::println);
    }

    private Properties getExpectedProperties() {
        Properties p = new Properties();
        p.setProperty("database.name","db1");
        p.setProperty("database.url","localhost:5432");
        p.setProperty("database.user","user1");
        p.setProperty("database.memory.sharedBuffers","524288");
        p.setProperty("database.memory.tempBuffers","65536");
        p.setProperty("database.memory.workMem","32868");
        p.setProperty("database.disk.tempFileLimit","-1");
        p.setProperty("database.disk.maxNotifyQueuePages","1048576");
        p.setProperty("database.disk.files[0]","file1");
        p.setProperty("database.disk.files[1]","file2");
        p.setProperty("database.schemas[0].schemaName","public");
        p.setProperty("database.schemas[0].tables[0].tableName","employee");
        p.setProperty("database.schemas[0].tables[0].columns[0].columnName","employee_id");
        p.setProperty("database.schemas[0].tables[0].columns[0].columnType","integer");
        p.setProperty("database.schemas[0].tables[0].columns[1].columnName","employee_name");
        p.setProperty("database.schemas[0].tables[0].columns[1].columnType","string");
        p.setProperty("database.schemas[0].tables[0].columns[2].columnName","dept_id");
        p.setProperty("database.schemas[0].tables[0].columns[2].columnType","integer");
        p.setProperty("database.schemas[0].tables[1].tableName","department");
        p.setProperty("database.schemas[0].tables[1].columns[0].columnName","dept_id");
        p.setProperty("database.schemas[0].tables[1].columns[0].columnType","integer");
        p.setProperty("database.schemas[0].tables[1].columns[1].columnName","dept_name");
        p.setProperty("database.schemas[0].tables[1].columns[1].columnType","string");
        p.setProperty("database.schemas[0].views[0].viewName","it_dept_employees");
        p.setProperty("database.schemas[0].views[0].viewSqlText","SELECT * FROM public.employee WHERE dept_id = 'IT'");
        return p;
    }
}
