package shell.security.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotated method with Secured annotation is executed only if a client session exists.
 * If a client session does not exist, {@link shell.security.NotAuthorizedException} exception is thrown.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Secured {
}
