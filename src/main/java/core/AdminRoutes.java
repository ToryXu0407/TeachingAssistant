package core;

import com.jfinal.config.Routes;
import controller.LoginController;
import controller.TestController;

public class AdminRoutes extends Routes {
	
	@Override
	public void config() {
		add("/student", TestController.class, "/");//测试路径
		add("/", LoginController.class);
    }
}
