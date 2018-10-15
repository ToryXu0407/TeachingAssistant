package util;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringUtil {
	
	public static String formatEmail(String email, Integer length) {
		if (StringUtil.isEmpty(email)) {
			return email;
		}
		String mail = email.length() >= length ? email.substring(0, length) : email;
		return mail + "***";
	}
	
	/**
	 * type=0:字符串，type=1:数字
	 * @param args
	 * @param type
	 * @return
	 */
	public static String nullToInteger(String args) {
		if (args != null) {
			args = args.trim();
			if (isInteger(args)) {
				return args;
			}
		}
		return "0";
		
	}
	
	/**
	 * 将为null的字符串转换为空字符串
	 * @param args
	 * @return
	 */
	public static String nullToEmpty(String args) {
		if (args == null) {
			args = "";
		}
		return args;
	}
	
	/**
	 * 整型判断
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isInteger(String str) {
		if (str == null)
			return false;
		Pattern pattern = Pattern.compile("[0-9]+");
		return pattern.matcher(str).matches();
	}
	
	/**
	 * 生成6位随即短信码
	 * @return
	 */
	public static int getRandomSMSCode() {
		Random rand = new Random();
		int tmp = Math.abs(rand.nextInt());
		return tmp % (999999 - 100000 + 1) + 100000;
	}
	
	public static <T> String listToString(List<T> list, String split) {
		StringBuffer buf = new StringBuffer();
		int size = list.size();
		for (T t : list) {
			if (--size != 0) {
				buf.append(t.toString()).append(split);
			} else {
				buf.append(t.toString());
			}
		}
		return buf.toString();
	}
	
	public static List<String> stringToList(String str, String split) {
		List<String> list = new ArrayList<String>();
		if (isEmpty(str))
			return list;
		for (String s : str.split(split)) {
			if (isEmpty(s))
				continue;
			list.add(s);
		}
		return list;
	}
	
	/**
	* 获取随机长度字符串
	* 
	* @param length
	*            字符串长度
	* @param includeLetter
	*            是否包含字母
	* @return 长度为length的字符串
	*/
	public static String getRandomString(int length, boolean includeLetter) {
		if (length < 1) {
			return "";
		}
		
		StringBuffer sb = new StringBuffer(length);
		sb.append(new Date().getTime());
		for (int i = 0; i < length; i++) {
			sb.append(includeLetter ? genRandomChar(i != 0) : genRandomDigit(i != 0));
		}
		return sb.toString();
	}
	
	/**
	* 获取随机字符
	* 
	* @param allowZero
	* @return 随机字符
	*/
	public static char genRandomChar(boolean allowZero) {
		int randomNumber = 0;
		
		do {
			randomNumber = (int) (Math.random() * 36);
		} while ((randomNumber == 0) && !allowZero);
		
		if (randomNumber < 10) {
			//			log4JUtils.info("获取随机字符:" + (randomNumber + '0'));
			return (char) (randomNumber + '0');
		} else {
			//			log4JUtils.info("获取随机字符:" + (randomNumber - 10 + 'a'));
			return (char) (randomNumber - 10 + 'a');
		}
	}
	
	/**
	* 获取随机数字
	* 
	* @param allowZero
	*            是否包含0
	* @return 随机数字
	*/
	public static char genRandomDigit(boolean allowZero) {
		int randomNumber = 0;
		
		do {
			randomNumber = (int) (Math.random() * 10);
		} while ((randomNumber == 0) && !allowZero);
		
		//		log4JUtils.info("获取随机数字:" + (randomNumber + '0'));
		return (char) (randomNumber + '0');
	}
	
	public static boolean isEmpty(String value, boolean trim, char... trimChars) {
		if (trim)
			return value == null || trim(value, trimChars).length() <= 0;
		return value == null || value.length() <= 0;
	}
	
	public static boolean isEmpty(String value, boolean trim) {
		return isEmpty(value, trim, ' ');
	}
	
	public static boolean isEmpty(String value) {
		return isEmpty(value, false);
	}
	
	public static String nullSafeString(String value) {
		return value == null ? "" : value;
	}
	
	public static String trim(String value) {
		return trim(3, value, ' ');
	}
	
	public static String trim(String value, char... chars) {
		return trim(3, value, chars);
	}
	
	public static String trimStart(String value, char... chars) {
		return trim(1, value, chars);
	}
	
	public static String trimEnd(String value, char... chars) {
		return trim(2, value, chars);
	}
	
	private static String trim(int mode, String value, char... chars) {
		if (value == null || value.length() <= 0)
			return value;
		
		int startIndex = 0, endIndex = value.length(), index = 0;
		if (mode == 1 || mode == 3) {
			while (index < endIndex) {
				if (contains(chars, value.charAt(index++))) {
					startIndex++;
					continue;
				}
				break;
			}
		}
		
		if (startIndex >= endIndex)
			return "";
		
		if (mode == 2 || mode == 3) {
			index = endIndex - 1;
			while (index >= 0) {
				if (contains(chars, value.charAt(index--))) {
					endIndex--;
					continue;
				}
				break;
			}
		}
		
		if (startIndex >= endIndex)
			return "";
		
		return value.substring(startIndex, endIndex);
	}
	
	private static boolean contains(char[] chars, char chr) {
		if (chars == null || chars.length <= 0)
			return false;
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] == chr)
				return true;
		}
		return false;
	}
	
	/**
	 * 验证邮箱
	 * 
	 * @param 待验证的字符串
	 * @return 如果是符合的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isEmail(String email) {
		
		boolean flag = false;
		
		try {
			if (email == null) {
				return flag;
			}
			
			email = email.replaceAll(" ", "");
			if ("".equals(email)) {
				return flag;
			}
			
			flag = match(
				"^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$",
				email);
			
		} catch (Exception e) {
			return false;
		}
		return flag;
		
	}
	
	/**
	 * 手机号码格式检查
	 * @param phone
	 * @return
	 */
	public static boolean isMobile(String phone) {
		boolean flag = false;
		
		try {
			if (phone == null) {
				return flag;
			}
			
			phone = phone.replaceAll(" ", "");
			if ("".equals(phone)) {
				return flag;
			}
			
			flag = match("^1[3|4|5|8|7]\\d{9}$", phone);
			
		} catch (Exception e) {
			return false;
		}
		return flag;
	}
	
	/**
	 * @param regex
	 *            正则表达式字符串
	 * @param str
	 *            要匹配的字符串
	 * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
	 */
	public static boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	
	public static String setDefaultValue(String obj, String defaultStr) {
		if (StringUtil.isEmpty(obj) || "null".equals(obj)) {
			return defaultStr;
		}
		return obj;
	}
	
	public static Object setDefaultValue(Object obj, Object defaultStr) {
		if (obj == null) {
			return defaultStr;
		}
		return obj.toString();
	}
	
	public static String getDatePoor(Date endDate, Date nowDate) {
		String text = "";
		long nd = 1000 * 24 * 60 * 60;
		long nh = 1000 * 60 * 60;
		long nm = 1000 * 60;
		long ns = 1000;
		// 获得两个时间的毫秒时间差异
		long diff = endDate.getTime() - nowDate.getTime();
		// 计算差多少天
		long day = diff / nd;
		// 计算差多少小时
		long hour = diff % nd / nh;
		// 计算差多少分钟
		long min = diff % nd % nh / nm;
		// 计算差多少秒//输出结果
		long sec = diff % nd % nh % nm / ns;
		
		//		return day + "天" + hour + "小时" + min + "分钟";
		if (day != 0) {
			text = day + "天前";
		}
		if (day == 0 && hour != 0) {
			text = hour + "小时前";
		}
		if (day == 0 && hour == 0 && min != 0) {
			text = min + "分钟前";
		}
		if (day == 0 && hour == 0 && min == 0 && sec != 0) {
			text = sec + "秒前";
		}
		return text;
	}
	
	
	public static Map<String, String> urlStringToMap(String urlString, Map<String, String> map) {
		String[] items = urlString.split("&");
		for (String item : items) {
			String[] pair = item.split("=");
			if (pair.length > 1) {
				String key = pair[0];
				String txt = pair[1];
				String value = urlDecode(txt);
				map.put(key, value);
			}
		}
		return map;
	}
	
	public static String mapToUrlString(Map<String, String> map) {
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			if (sb.length() != 0)
				sb.append("&");
			sb.append(entry.getKey());
			sb.append("=");
			if (isEmpty(entry.getValue())) {
				sb.append("");
			} else {
				sb.append(urlEncode(entry.getValue()));
			}
		}
		return sb.toString();
	}
	public static String urlDecode(String strIn) {
		String strOut = null;
		try {
			strOut = java.net.URLDecoder.decode(strIn, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return strOut;
	}
	
	
	/**
	 * URL
	 * 
	 * @param strIn
	 * @return
	 */
	public static String urlEncode(String strIn) {
		if (isEmpty(strIn)) {
			return "";
		}
		String strOut = null;
		try {
			strOut = java.net.URLEncoder.encode(strIn, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return strOut;
	}
	
}
