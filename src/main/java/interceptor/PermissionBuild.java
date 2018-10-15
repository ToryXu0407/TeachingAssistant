package interceptor;

import annotation.PermissionOwn;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Date;

import static util.DealStrSub.getSubUtilSimple;


//构建权限
/*
最后要获得的表数据
1 0 Admin
2 1 Teacher
3 2 Student
 */
public class PermissionBuild implements Interceptor {

	private static final Logger	LOG	= Logger.getLogger("PermissionBuild");

	@SuppressWarnings({ "rawtypes" })
	public void intercept(Invocation me) {
		try {
			Class controllerClass = me.getController().getClass();
			//类上
            //只需要关注类，因为分类就是按学生、教师和管理员
            //初始把这三种类型保存到数据库
			PermissionOwn controllerOwn = getPermissionOwn(controllerClass);
//			//方法上
//			PermissionOwn methodOwn = getControllerMethodPermissionOwn(controllerClass,
//				me.getMethodName());

			//构建权限的必要添加
			if (controllerOwn != null  && !StringUtils.isEmpty(me.getMethodName())) {
				String controllerName = controllerOwn.name();
				String controllerAlias = controllerOwn.alias();
				if (StringUtils.isEmpty(controllerAlias)) {
					controllerAlias = controllerClass.getSimpleName();
					int index = controllerAlias.indexOf(Controller.class.getSimpleName());
					if (index > 0) {
						controllerAlias = controllerAlias.substring(0, index).toLowerCase();
					} else {
						controllerAlias = controllerClass.getSimpleName();
					}
				}
				//permission是整个路径
                String permission = me.getController().getRequest().getRequestURI();

                    //检查(类)权限是否存在
                    boolean permIsExist = checkPermissionExist(controllerAlias);//使用方法名作为权限alias,使用URL作为权限值

                    if (!permIsExist) {//权限不存在,则构建权限
                        createPermission(controllerAlias, controllerName, null,null,permission);
                }
			}

		} catch (Exception e) {
			LOG.error("权限构建失败!无法完成权限初始化!", e);
		} finally {
			me.invoke();//不管怎么样,总要继续的,亲
		}
	}

	/**
	 * 创建权限
	 */
	private synchronized void createPermission(String controllerAlias, String controllerName,
                                               String methodAlias, String methodName,
                                               String permission) {

		LOG.info(String
			.format(
				"自动创建权限[controllerAlias=%s,controllerName=%s,methodAlias=%s,methodName=%s,permission=%s]",
				controllerAlias, controllerName, methodAlias, methodName, permission));

		if (StringUtils.isEmpty(controllerName)) {//在method指定的parent不存在的时候,默认使用alias作为权限名称
			controllerName = controllerAlias;
		}
        Record record = new Record();
        record.set("name", controllerName);
        Record record1 = Db.use("ta").findFirst("select * from ta_user_permission where user_type=0");
        if(record1==null){
            record.set("name","admin");
            record.set("user_type","0");
            Db.use("ta").save("ta_user_permission", record);
        }
        record1 = Db.use("ta").findFirst("select * from ta_user_permission where user_type=1");
        if(record1==null){
            record.set("name","teacher");
            record.set("user_type","1");
            Db.use("ta").save("ta_user_permission", record);
        }
        record1 = Db.use("ta").findFirst("select * from ta_user_permission where user_type=2");
        if(record1==null){
            record.set("name","student");
            record.set("user_type","2");
            Db.use("ta").save("ta_user_permission", record);
        }
//        if(controllerName.equals("Student"))
//            record.set("user_type","2");
//        else if(controllerName.equals("Teacher"))
//            record.set("user_type","1");
//        else if(controllerName.equals("Admin"))
//            record.set("user_type","0");
//        else if(!controllerName.equals("login"))
//        Db.use("ta").save("ta_user_permission", record);
	}

	/**
	 * 检查权限是否存在
	 */
	private boolean checkPermissionExist(String name) {
		if (StringUtils.isEmpty(name)) {
			return false;
		}
		Record permRecord = Db.use("ta").findFirst(
			"select * from ta_user_permission where name = ? ", name);
		if (permRecord != null) {
			return true;
		}
		return false;
	}


	//-------------------------------内部方法-----------------------------------------

	//判断Controller 是否有PermissionOwn注解
	@SuppressWarnings("rawtypes")
	private PermissionOwn getPermissionOwn(Class cls) {
		Annotation[] annotations = cls.getAnnotations();
		for (Annotation annt : annotations) {
			if (annt instanceof PermissionOwn) {
				PermissionOwn own = (PermissionOwn) annt;
				return own;
			}
		}
		return null;
	}

}
