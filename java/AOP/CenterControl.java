
/**
 * Title:
 * Project: 
 * Description:
 * Date: 2019-11-20
 * Copyright: Copyright (c) 2020
 * Company: 
 *
 * @author zwx001
 * @version 1.0
 */
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface CenterControl {

    String value() default "";

}
