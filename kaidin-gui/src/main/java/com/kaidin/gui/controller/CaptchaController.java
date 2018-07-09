package com.kaidin.gui.controller;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kaidin.common.util.DataTypeUtil;
import com.kaidin.common.util.encrypt.EncryptUtil;
import com.kaidin.common.util.image.Captcha;
import com.kaidin.gui.common.constant.GuiConstType;
/**
 * 验证码应用服务控制器
 * @author xuxiaobin	kaidin@foxmail.com
 *
 */
@Controller
@RequestMapping("/captcha.html")
public class CaptchaController {
	private static final transient Logger logger = LoggerFactory.getLogger(CaptchaController.class);
	
	
	/**
	 * 检查验证码是否正确，用于服务器上需要的应用
	 * 使用前提是已经获取了验证码图片
	 * @param request
	 * @return
	 */
	public static boolean isCaptchaOk(HttpServletRequest request) {
		boolean isOk = false;
		
		String inputCaptcha = request.getParameter(GuiConstType.SessionKey.CAPTCHA);
		logger.debug("inputCaptcha:{}", inputCaptcha);
		if (null != inputCaptcha && Captcha.MIN_CODE_COUNT <= inputCaptcha.length()) {
			HttpSession session = request.getSession(false);
			if (null != session) {
				Object sessionObj = session.getAttribute(GuiConstType.SessionKey.CAPTCHA);
				String sessionCaptcha = DataTypeUtil.asString(sessionObj);
				session.removeAttribute(GuiConstType.SessionKey.CAPTCHA);
				logger.debug("sessionCaptcha:{}", sessionCaptcha);
				if (inputCaptcha.equalsIgnoreCase(sessionCaptcha)) {
					isOk = true;
				}
			}
		}
		
		return isOk;
	}
	
	/**
	 * 获取验证码图片
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=getCaptchaImage")
	public void getCaptchaImage(HttpServletRequest request, HttpServletResponse response) {
		try (ServletOutputStream outputStream = response.getOutputStream()) {
			// 生成随机验证码
			Captcha codeBuilder = new Captcha();
			char[] codeArray = codeBuilder.createCaptchaCode();
			String codeStr = String.valueOf(codeArray);
			logger.debug("sessionCaptcha:{}", codeStr);
			// 将验证码保存到Session中
			HttpSession session = request.getSession();
			session.setAttribute(GuiConstType.SessionKey.CAPTCHA, codeStr);
			
			// 将图像输出到Servlet输出流中
			// 禁止图像缓存。
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("image/jpeg");
			
			codeBuilder.createImage(codeArray, outputStream);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 获取验证码的md5值，用于验证码的前台校验
	 * 使用前提是已经获取了验证码图片
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=getCaptchaMd5")
	public void getCaptchaMd5(HttpServletRequest request, HttpServletResponse response) {
		try (ServletOutputStream outputStream = response.getOutputStream()) {
			byte[] result = null;
			// 将验证码字符串保存到Session中
			HttpSession session = request.getSession(false);
			if (null != session) {
				Object obj = session.getAttribute(GuiConstType.SessionKey.CAPTCHA);
				String codeStr = DataTypeUtil.asString(obj);
				String codeMd5Str = EncryptUtil.md5(codeStr);
				if (null != codeMd5Str) {
					result = codeMd5Str.getBytes();
				}
			}

			// 将验证码字符串輸出到Servlet输出流中
			outputStream.write(result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
