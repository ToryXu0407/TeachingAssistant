package controller;

import annotation.PermissionOwn;
import annotation.UnCheckLogin;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import interceptor.PermissionChecker;

import java.text.SimpleDateFormat;
import java.util.Date;

@PermissionOwn(name="register")
public class RegisterController extends BaseController {
    public static final String SALT="TeachingAssistant";

	public void index() {
		render("index.html");
	}
	@PermissionOwn(name="register")
	@UnCheckLogin
	public void register() {
		render("register.html");
	}


	@UnCheckLogin
	public void RegisterSubmit() {
		try {
			String username = getPara("username").trim();
			String password = getPara("password").trim();
			if (StrKit.isBlank(username)) {
				throw new RuntimeException("用户名不能为空");
			}
			if (StrKit.isBlank(password)) {
				throw new RuntimeException("密码不能为空");
			}
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = formatter.format(currentTime);
			Record record = new Record();
			record.set("username",username);
			record.set("pwd",password);
			record.set("type",2);
			record.set("create_time", dateString);
			Record record1 = Db.use("ta").findFirst("select * from ta_user where username= ? ",username);
			if(record1!=null)
			    throw new RuntimeException("用户名已存在!");
			Db.use("ta").save("ta_user", record);
			record = Db.use("ta").findFirst("select * from ta_user where username= ? ",username);
			setSessionAttr(PermissionChecker.USER_ID, record.get("id"));
			setSessionAttr(PermissionChecker.USER_USERNAME, username);
			String type ="2";
			if(!StrKit.isBlank(type)){
			    Record permission = Db.use("ta").findFirst("select * from ta_user_permission where user_type =? ",type);
				//将用户权限存入session
				setSessionAttr(PermissionChecker.USER_TYPE,permission.get("name"));
			}
			LOG.info("登录成功,username=" + username);
			redirect("/student");

		} catch (Exception e) {
			LOG.error("出错啦", e);
			setAttr("error", e.getMessage());
			renderVelocity("register.html");
		}
	}


}
