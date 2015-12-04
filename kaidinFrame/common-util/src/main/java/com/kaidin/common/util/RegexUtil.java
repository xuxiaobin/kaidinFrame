package com.kaidin.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.kaidin.common.constant.ConstType;
/**
 * 常用正则表达式匹配工具
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2015-6-23下午01:51:48
 */
public class RegexUtil {
	private static final Pattern CONTAIN_NUMBER_PATTERN	= Pattern.compile("\\d");
	private static final Pattern ALL_NUMBERS_PATTERN	= Pattern.compile("^\\d+$");
	private static final Pattern CONTAIN_LETTER_PATTERN	= Pattern.compile("[a-zA-Z]");
	private static final Pattern ALL_LETTERS_PATTERN	= Pattern.compile("^[A-Za-z]+$");
	
	private static final Pattern MAIL_ADDR_PATTERN		= Pattern.compile(ConstType.REGEX.MAIL_ADDR);
	private static final Pattern IP_ADDR_PATTERN		= Pattern.compile(ConstType.REGEX.IP_ADDR);
	
	
    /**
     * 判断字符串中是否包含数字
     * @param str 源字符串
     * @return 是否包含数组的标志
     */
	public static boolean containNumber(String str) {
		boolean result = false;
		
        if (null != str && !str.isEmpty()) {
            Matcher m = CONTAIN_NUMBER_PATTERN.matcher(str);
            if (m.find()) {
            	result = true;
            }
        }
        
        return result;
	}
    /**
     * 判断字符串是否由全部数字组成
     * @param str 源字符串
     * @return 是否是全部数字组成的标志
     */
	public static boolean isAllNumbers(String str) {
		boolean result = false;
		
        if (null != str && !str.isEmpty()) {
            Matcher m = ALL_NUMBERS_PATTERN.matcher(str);
            if (m.matches()) {
            	result = true;
            }
        }
        
        return result;
	}
    
    /**
     * 判断字符串是否包含字母
     * @param str 源字符串
     * @return 是否包含字母的标志
     */
    public static boolean containLetter(String str) {
        boolean result = false;
        
        if (null != str && !str.isEmpty()) {
            Matcher m = CONTAIN_LETTER_PATTERN.matcher(str);
            if (m.find()) {
            	result = true;
            }
        }
        
        return result;
    }
    /**
     * 判断字符串是否由全部字母组成
     * @param str 源字符串
     * @return 是否纯字母组合的标志
     */
    public static boolean isAllLetters(String str) {
        boolean result = false;
        
        if (null != str && !str.isEmpty()) {
            Matcher m = ALL_LETTERS_PATTERN.matcher(str);
            if (m.matches()) {
            	result = true;
            }
        }
        
        return result;
    }
	
	/**
	 * 判断是否满足邮件地址规则
	 * @param mailAddr
	 * @return 是否满足邮箱地址规则
	 */
	public static boolean isMailAddr(String mailAddr) {
		boolean result = false;
		
		if (null != mailAddr && 3 <= mailAddr.length()) {
			result = MAIL_ADDR_PATTERN.matcher(mailAddr).matches();
		}
		
		return result;
	}
	
	/**
	 * 判断是否满足ip规则
	 * @param ipAddr
	 * @return 是否满足ip规则
	 */
	public static boolean isIpAddr(String ipAddr) {
		boolean result = false;
		
		if (null != ipAddr && 7 <= ipAddr.length()) {
			if (ConstType.IP.LOCALHOST.equalsIgnoreCase(ipAddr)) {
				// localhost匹配
				result = true;
			} else {
				result = IP_ADDR_PATTERN.matcher(ipAddr).matches();
			}
		}
		
		return result;
	}
}
