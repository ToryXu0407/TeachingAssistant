package core;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.render.ViewType;
import interceptor.PermissionBuild;
import interceptor.PermissionChecker;
import interceptor.VisitLogInterceptor;
import model.Module;

/**
 * 
 * @author shiyehui
 * 
 */

public class CoreConfig extends JFinalConfig {

    static {
        PropKit.use("properties/appconfig.properties");
    }

    private static final String TA_URL = PropKit.get("jdbc.ta.url");
    private static final String TA_USERNAME = PropKit.get("jdbc.ta.username");
    private static final String TA_PASSWORD = PropKit.get("jdbc.ta.password");
    private static final String TA_LOG_URL = PropKit.get("jdbc.ta_log.url");
    private static final String TA_LOG_USERNAME = PropKit.get("jdbc.ta_log.username");
    private static final String TA_LOG_PASSWORD = PropKit.get("jdbc.ta_log.password");
    private static final boolean deMode = Boolean.valueOf(PropKit.get("deMode"));
	@Override
	public void configConstant(Constants me) {

		me.setDevMode(true);
		me.setViewType(ViewType.VELOCITY);
		me.setMaxPostSize(50 * 1024 * 1024);// 上传的文件的最大50M
		me.setBaseViewPath("/WEB-INF/templates");
		me.setEncoding("UTF-8");
	}

	@Override
	public void configRoute(Routes me) {
		me.add(new core.AdminRoutes());
	}

	@Override
	public void configPlugin(Plugins me) {
		DruidPlugin db_ta = new DruidPlugin(TA_URL, TA_USERNAME,TA_PASSWORD);// 数据库连接插件
		db_ta.addFilter(new StatFilter());// sql 监控
		WallFilter ta_wall = new WallFilter();// 防止sql 注入
		ta_wall.setDbType("mysql");
		db_ta.addFilter(ta_wall);
		me.add(db_ta);
		ActiveRecordPlugin arp_ta = new ActiveRecordPlugin("ta", db_ta);
		arp_ta.setDialect(new MysqlDialect());
		arp_ta.setShowSql(deMode);// 开发者模式下开启SQL
		me.add(arp_ta);

        DruidPlugin db_ta_log = new DruidPlugin(TA_LOG_URL, TA_LOG_USERNAME,TA_LOG_PASSWORD);// 数据库连接插件
        db_ta_log.addFilter(new StatFilter());// sql 监控
        WallFilter ta_log_wall = new WallFilter();// 防止sql 注入
        ta_log_wall.setDbType("mysql");
        db_ta_log.addFilter(ta_log_wall);
        me.add(db_ta_log);
        ActiveRecordPlugin arp_ta_log = new ActiveRecordPlugin("ta_log", db_ta_log);
        arp_ta_log.setDialect(new MysqlDialect());
        arp_ta_log.setShowSql(deMode);// 开发者模式下开启SQL
        me.add(arp_ta_log);
	}

	@Override
	public void configInterceptor(Interceptors me) {
		me.add(new VisitLogInterceptor());
		me.add(new PermissionChecker());
		me.add(new PermissionBuild());
	}

	@Override
	public void configHandler(Handlers me) {
		//me.add(new UrlSkipHandler("^/websocket", false));
	}

	public void afterJFinalStart() {

		System.out.println("JFinal启动了");

	}

	public void beforJFinalStop() {
		System.out.println("JFinal停止了");
	}
}
