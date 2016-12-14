package by.prakapienka.annotationlog;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Log {

    enum Level {
        DEBUG, ERROR, INFO, TRACE, WARN
    }

    Level level() default Level.INFO;
}
