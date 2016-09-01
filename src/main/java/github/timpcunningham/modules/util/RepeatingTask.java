package github.timpcunningham.modules.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value = RetentionPolicy.RUNTIME)
public @interface RepeatingTask {
    boolean async() default true;
    long delay() default 0L;
    long period() default 1L;
}
