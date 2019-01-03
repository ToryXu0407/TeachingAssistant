package controller;

import annotation.PermissionOwn;
import com.jfinal.core.Controller;
import interceptor.PermissionChecker;
@PermissionOwn(name="student")
public class TestController extends Controller {
         
	public void index(){
		int userid = getSessionAttr(PermissionChecker.USER_ID);
		String username = getSessionAttr(PermissionChecker.USER_USERNAME);
		setAttr("userid", userid);
		setAttr("username", username);
		render("index.jsp");
	}

}
