package interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Enumeration;


public class VisitLogInterceptor implements Interceptor {
	public static final Logger LOG=Logger.getLogger(VisitLogInterceptor.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public void intercept(Invocation inv) {
		try{
			Controller controller = inv.getController();
			StringBuilder sb = new StringBuilder();
			HttpServletRequest request = controller.getRequest();
			Enumeration<String> e = request.getParameterNames();
			if (e.hasMoreElements()) {
				while (e.hasMoreElements()) {
					String name = e.nextElement();
					String[] values = request.getParameterValues(name);
					if (values.length == 1) {
						sb.append(name).append("=").append(values[0]);
					}
					else {
						sb.append(name).append("[]={");
						for (int i=0; i<values.length; i++) {
							if (i > 0)
								sb.append(",");
							sb.append(values[i]);
						}
						sb.append("}");
					}
					sb.append("  ");
				}
			}
		
			HttpSession session = controller.getSession();
			String remoteIp=request.getRemoteAddr();
			String controllerKey=inv.getControllerKey();
			String action=inv.getActionKey();
			String username=(String) session.getAttribute(PermissionChecker.USER_USERNAME);
			if(username==null){
				return;
			}
			Record record=new Record();
			record.set("log_time", new Date());
			record.set("action", action);
			record.set("controller", controllerKey);
			record.set("username", username);
			record.set("remote_ip", remoteIp);
			record.set("filter",sb.toString());
			Db.use("ta_log").save("ta_visit_log",record);
		}catch(Exception e){
			LOG.error(e.getMessage(),e);
		}finally{
			 inv.invoke();
		}  
	}

}
