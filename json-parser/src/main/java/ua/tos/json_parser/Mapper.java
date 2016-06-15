package ua.tos.json_parser;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by tos on 6/12/16.
 */
public class Mapper {

    Glossary map(Map<String, Object> json) throws IllegalAccessException {
        Glossary glossary = new Glossary();

        Field[] declaredFields = Glossary.class.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            for (Annotation annotation : declaredField.getAnnotations()) {
                if (annotation.annotationType().equals(JsonField.class)) {
                    declaredField.setAccessible(true);
                    String name = ((JsonField) annotation).name();
                    declaredField.set(glossary, json.get(name));
                }
            }

        }

        return glossary;

    }
}
