package controller;

import annotation.PermissionOwn;
import annotation.UnCheckLogin;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
import interceptor.PermissionChecker;
import model.Result;
import model.ResultFactory;
import model.UserInfo;
import util.MD5Util;
import util.qiniuyun;
import org.apache.log4j.Logger;


@PermissionOwn(name="login")
public class LoginController extends BaseController {
    public static final Logger LOG=Logger.getLogger(LoginController.class);
    public static final String SALT="TeachingAssistant";

	@UnCheckLogin
	public void login() {
		render("login.html");
	}


	@UnCheckLogin
	public void Toregister() {
        redirect("/register/register");
	}

	public void updateUser(){
		Result result;
		try{
			UploadFile file = getFile("file");
			String fileName = file.getFileName();
			String path = file.getUploadPath();
			String nickname = getPara("nickname");
			int id = getParaToInt("id");
			String imageUrl = qiniuyun.upload(path+'/'+fileName,fileName);
			if(imageUrl==null){
				throw new Exception("上传失败！");
			}
			LOG.debug("addCourseWare上传 文件默认本地URL："+path);
			Record record = Db.findById("ta_user",id);
			record.set("nickname",nickname);
			record.set("head_image",imageUrl);
			Db.use("ta").update("ta_user",record);
			result = ResultFactory.buildSuccessResult(imageUrl);
		}catch(Exception e){
			e.printStackTrace();
			LOG.error(e.getMessage(),e);
			result = ResultFactory.buildFailResult(e.getMessage());
		}
		renderJson(result);
	}
	@UnCheckLogin
	public void loginSubmit() {
		Result result;
		try {
			UserInfo UserInfo = getBean(UserInfo.class,"");
			String username = UserInfo.getUsername();
			String password = UserInfo.getPassword();
			if (StrKit.isBlank(username)) {
				throw new RuntimeException("用户名不能为空");
			}
			if (StrKit.isBlank(password)) {
				throw new RuntimeException("密码不能为空");
			}
			password = MD5Util.encryptPassword(password,SALT);
			//登录检测
			Record userRecord = Db.use("ta").findFirst("select * from ta_user where username = ? and password = ?", username,password);
			if (userRecord == null) {
				throw new RuntimeException("用户名/密码输入错误");
			}
			UserInfo userInfo = new UserInfo();
			userInfo.setNickname(userRecord.getStr("nickname"));
			userInfo.setUsername(userRecord.getStr("username"));
			userInfo.setType(userRecord.getInt("type"));
			userInfo.setCreateTime(userRecord.get("create_time").toString());
			userInfo.setHeadImage(userRecord.getStr("head_image"));
			userInfo.setUserid(userRecord.getInt("id"));
			setSessionAttr(PermissionChecker.USER,userInfo);
			setSessionAttr(PermissionChecker.USER_ID, userRecord.get("id"));
			setSessionAttr(PermissionChecker.USER_USERNAME, username);
			setSessionAttr(PermissionChecker.USER_TYPE,userRecord.get("type"));
			LOG.info("登录成功,username=" + username);
			result = ResultFactory.buildSuccessResult(UserInfo);
			//redirect("/student");
		} catch (Exception e) {
			LOG.error("出错啦", e);
			setAttr("error", e.getMessage());
			result = ResultFactory.buildFailResult(e.getMessage());
			//renderVelocity("login.html");
		}
		renderJson(result);
	}
	@UnCheckLogin
	public void getLoggedInfo() {
		Result result;
		if(getSessionAttr(PermissionChecker.USER_ID)!=null&&getSessionAttr(PermissionChecker.USER_USERNAME)!=null){
			int  id = getSessionAttr(PermissionChecker.USER_ID);
			UserInfo userInfo = new UserInfo();
			Record userRecord = Db.use("ta").findById("ta_user",id);
			userInfo.setNickname(userRecord.getStr("nickname"));
			userInfo.setUsername(userRecord.getStr("username"));
			userInfo.setType(userRecord.getInt("type"));
			userInfo.setCreateTime(userRecord.get("create_time").toString());
			userInfo.setHeadImage(userRecord.getStr("head_image"));
			userInfo.setUserid(userRecord.getInt("id"));
			result = ResultFactory.buildSuccessResult(userInfo);
		}
		else{
            result = ResultFactory.buildFailResult("无登陆信息");
        }
        renderJson(result);
	}
	@UnCheckLogin
	public void logout(){
		removeSessionAttr(PermissionChecker.USER_ID);
		removeSessionAttr(PermissionChecker.USER_USERNAME);
        Result result = ResultFactory.buildSuccessResult("注销成功!");
        renderJson(result);
	}

	public static void main(String[] args) {
		String password ="321";
		password = MD5Util.encryptPassword(password,SALT);
		System.out.println(password);
	}



}
