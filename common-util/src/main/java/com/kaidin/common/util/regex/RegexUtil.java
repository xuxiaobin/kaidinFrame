package com.kaidin.common.util.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.kaidin.common.constant.ConstType;
import com.kaidin.common.util.StringUtil;
/**
 * 常用正则表达式匹配工具
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2015-6-23下午01:51:48
 */
public abstract class RegexUtil {
	private static final Pattern CONTAIN_NUMBER_PATTERN	= Pattern.compile("\\d");
	private static final Pattern ALL_NUMBERS_PATTERN	= Pattern.compile("^\\d+$");
	private static final Pattern CONTAIN_LETTER_PATTERN	= Pattern.compile("[a-zA-Z]");
	private static final Pattern ALL_LETTERS_PATTERN	= Pattern.compile("^[A-Za-z]+$");
	
	private static final Pattern MAIL_ADDR_PATTERN		= Pattern.compile(ConstType.regex.MAIL_ADDR);
	private static final Pattern IP_ADDR_PATTERN		= Pattern.compile(ConstType.regex.IP_ADDR);
	
	
    /**
     * 判断字符串中是否包含数字
     * @param str 源字符串
     * @return 是否包含数组的标志
     */
	public static boolean containNumber(String str) {
		if (StringUtil.isEmpty(str)) {
			return false;
        }
		
        Matcher m = CONTAIN_NUMBER_PATTERN.matcher(str);
        return m.find();
	}
    /**
     * 判断字符串是否由全部数字组成
     * @param str 源字符串
     * @return 是否是全部数字组成的标志
     */
	public static boolean isAllNumbers(String str) {
		if (StringUtil.isEmpty(str)) {
			return false;
        }
		
        Matcher m = ALL_NUMBERS_PATTERN.matcher(str);
        return m.matches();
	}
    
    /**
     * 判断字符串是否包含字母
     * @param str 源字符串
     * @return 是否包含字母的标志
     */
    public static boolean containLetter(String str) {
    	if (StringUtil.isEmpty(str)) {
			return false;
        }
        
        Matcher m = CONTAIN_LETTER_PATTERN.matcher(str);
        return m.find();
    }
    /**
     * 判断字符串是否由全部字母组成
     * @param str 源字符串
     * @return 是否纯字母组合的标志
     */
    public static boolean isAllLetters(String str) {
    	if (StringUtil.isEmpty(str)) {
			return false;
        }
        
        Matcher m = ALL_LETTERS_PATTERN.matcher(str);
        return m.matches();
    }
	
	/**
	 * 判断是否满足邮件地址规则
	 * @param mailAddr
	 * @return 是否满足邮箱地址规则
	 */
	public static boolean isMailAddr(String mailAddr) {
		if (null == mailAddr || 3 > mailAddr.length()) {
			return false;
		}
		
		return MAIL_ADDR_PATTERN.matcher(mailAddr).matches();
	}
	
	/**
	 * 判断是否满足ip规则
	 * @param ipAddr
	 * @return 是否满足ip规则
	 */
	public static boolean isIpAddr(String ipAddr) {
		if (null == ipAddr || 7 > ipAddr.length()) {
			return false;
		}
		
		if (ConstType.ip.LOCALHOST.equalsIgnoreCase(ipAddr)) {
			// localhost匹配
			return true;
		}
		
		return IP_ADDR_PATTERN.matcher(ipAddr).matches();
	}
}
