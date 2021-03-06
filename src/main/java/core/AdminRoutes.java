package core;

import com.jfinal.config.Routes;
import controller.*;

public class AdminRoutes extends Routes {
	
	@Override
	public void config() {
		add("/student", TestController.class, "/");
		add("/register", RegisterController.class, "/");
		add("/", LoginController.class);
		add("/article", ArticleController.class);
		add("/post", PostController.class);
		add("/course",CourseController.class);
		add("/notice",NoticeController.class);
		add("/courseware",CoursewareController.class);
		add("/chat",ChatController.class);
    }
}
