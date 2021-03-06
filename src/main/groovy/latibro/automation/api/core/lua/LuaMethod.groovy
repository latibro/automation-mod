package latibro.automation.api.core.lua

import java.lang.annotation.*

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target([ElementType.METHOD])
@interface LuaMethod {
    String name() default ""

    String doc() default ""

    String usage() default ""
}
