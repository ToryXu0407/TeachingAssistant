package annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *                       
 * @Filename PermissionOwn.java
 *
 * @Description 权限辅助注解,用于简化权限的配置,如果木有,亲需要手动在数据库里面添加
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionOwn {
	
	String alias() default "";
	
	String parent() default "";
	
	String name();//权限名称
	
}
