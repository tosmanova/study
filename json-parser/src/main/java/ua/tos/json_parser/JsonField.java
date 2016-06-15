package ua.tos.json_parser;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by tos on 6/12/16.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonField {

    String name();

}
