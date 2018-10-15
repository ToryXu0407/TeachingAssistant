package controller;

import com.jfinal.core.Controller;
import com.jfinal.log.Log4jLog;
import interceptor.PermissionChecker;
import org.apache.commons.lang.StringUtils;
import util.MD5Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BaseController extends Controller {

	protected Log4jLog LOG= Log4jLog.getLog(BaseController.class);
	protected static final String MESSAGE				= "message";
	protected static final String RESULT				= "result";
	protected static final String RESULT_SUCCESS		= "success";
	protected static final String RESULT_FAIL			= "fail";
	
	/**
	 * 加密密码,返回密文(md(password))
	 * @param password 待加密的明文
	 * @return
	 */
	public String encryptPassword(String password) {
		if (StringUtils.isEmpty(password)) {
			throw new RuntimeException("密码不能为空");
		}
		
		return MD5Util.encryptPassword(password,"JUJIBAO");
	}
	
	protected Integer getAdminID() {
		String adminId = getSession().getAttribute(PermissionChecker.USER_ID).toString();
		return Integer.parseInt(adminId);
	}
	
	/**
	 * 获取管理员名
	 * @return
	 */
	protected String getAdminName() {
		String username = getSession().getAttribute(PermissionChecker.USER_USERNAME).toString();
		return username;
	}
	
	@SuppressWarnings("rawtypes")
	protected Map<String, Object> buildPagination(List list, Integer count) {
		return buildPagination(list, count, null);
	
	}
	
	@SuppressWarnings("rawtypes")
	protected Map<String, Object> buildPagination(List list, Integer count,
                                                  List<Map<String, Object>> footer) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", count);
		map.put("rows", list);
		if (footer != null)
			map.put("footer", footer);
		return map;
	}
}
