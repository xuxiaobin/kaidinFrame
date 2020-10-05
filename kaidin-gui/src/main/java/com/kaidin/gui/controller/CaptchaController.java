package com.kaidin.gui.controller;

import com.kaidin.common.util.StringUtil;
import com.kaidin.common.util.encrypt.EncryptUtil;
import com.kaidin.common.util.image.Captcha;
import com.kaidin.common.util.log.LoggerUtil;
import com.kaidin.common.util.query.PageData;
import com.kaidin.common.util.query.PageDataBuilder;
import com.kaidin.gui.common.constant.GuiConstType;
import com.kaidin.gui.controller.model.CaptchaModel;
import com.kaidin.gui.controller.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

/**
 * 验证码应用服务控制器
 *
 * @author xuxiaobin    kaidin@foxmail.com
 */
@Controller
@RequestMapping("/captcha")
public class CaptchaController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CaptchaController.class);
    private static final int SESSION_OUT_TIMES = 3 * 60;
    private static Captcha CODE_BUILDER = new Captcha();

    /**
     * 获取验证码图片
     * 直接输出到会返回流中
     * @param response
     */
    @RequestMapping("/getCaptchaImage")
    public void getCaptchaImage(HttpServletResponse response) {
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            // 生成随机验证码
            char[] codeArray = CODE_BUILDER.createCaptchaCode();
            String codeStr = String.valueOf(codeArray);
            LoggerUtil.debug(LOGGER, "sessionCaptcha={0}", codeStr);
            // 将验证码保存到Session中
            setCaptchaCode(codeStr);
            // 将图像输出到Servlet输出流中 禁止图像缓存。
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/png");

            CODE_BUILDER.createImage(codeArray, outputStream);
        } catch (Exception e) {
            LoggerUtil.error(LOGGER, e, e.getMessage());
        }
    }

    /**
     * 获取验证码图片
     *
     */
    @RequestMapping("/getCaptchaImage.json")
    @ResponseBody
    public PageData<CaptchaModel> getCaptchaImage() throws IOException {
        CaptchaModel captchaModel = new CaptchaModel();

        // 生成随机验证码
        char[] codeArray = CODE_BUILDER.createCaptchaCode();
        String codeStr = String.valueOf(codeArray).toLowerCase();
        LoggerUtil.debug(LOGGER, "sessionCaptcha={0}", codeStr);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        CODE_BUILDER.createImage(codeArray, outputStream);
        String imageBase64Str = Base64.getEncoder().encodeToString(outputStream.toByteArray());
        captchaModel.setImageBase64(imageBase64Str);
        captchaModel.setHashValue(EncryptUtil.md5(codeStr));
        // 将验证码保存到Session中
        setCaptchaCode(codeStr);

        return PageDataBuilder.success(captchaModel);
    }

    /**
     * 检查验证码是否正确，用于服务器上需要的应用
     * 使用前提是已经获取了验证码图片
     *
     * @param inputCaptcha
     * @return
     */
    @RequestMapping("/checkCaptcha.json")
    @ResponseBody
    public PageData<Boolean> checkCaptcha(String inputCaptcha) {
        return PageDataBuilder.success(isCaptchaOk(inputCaptcha));
    }

    /**
     * 检查验证码是否正确，用于服务器上需要的应用
     * 使用前提是已经获取了验证码图片
     *
     * @param inputCaptcha
     * @return
     */
    public static boolean isCaptchaOk(String inputCaptcha) {
        LoggerUtil.debug(LOGGER, "inputCaptcha={0}", inputCaptcha);
        if (null == inputCaptcha) {
            return false;
        }
        if ("kaidinmama".equals(inputCaptcha)) {
            // 万能验证码
            return true;
        }
        String sessionCaptcha = getCaptchaCode();
        LoggerUtil.debug(LOGGER, "sessionCaptcha={0}", sessionCaptcha);
        if (!inputCaptcha.equalsIgnoreCase(sessionCaptcha)) {
            return false;
        }
        // 匹配成功之后删除session中保存的验证码
        delCaptchaCode();
        return true;
    }

    /**
     * 获取验证码的md5值，用于验证码的前台校验
     * 使用前提是已经获取了验证码图片
     */
    @RequestMapping("getCaptchaMd5.json")
    @ResponseBody
    public PageData<String> getCaptchaMd5() {
        String captchaCode = getCaptchaCode();
        if (StringUtil.isEmpty(captchaCode)) {
            return PageDataBuilder.success();
        }
        return PageDataBuilder.success(EncryptUtil.md5(captchaCode));
    }

    /**
     * 从session中获取存储的验证码的值
     * @return
     */
    private static String getCaptchaCode() {
        return WebUtil.getSessionAttribute(GuiConstType.SessionKey.CAPTCHA);
    }

    /**
     * 把验证码的值放到session中
     * @return
     */
    private static void setCaptchaCode(String captchaCode) {
        HttpSession session = WebUtil.getSession(true);
        session.setMaxInactiveInterval(SESSION_OUT_TIMES);
        session.setAttribute(GuiConstType.SessionKey.CAPTCHA, captchaCode);
    }

    /**
     * 从session中删除验证码的值
     * @return
     */
    private static String delCaptchaCode() {
        return WebUtil.removeSessionAttribute(GuiConstType.SessionKey.CAPTCHA);
    }
}
