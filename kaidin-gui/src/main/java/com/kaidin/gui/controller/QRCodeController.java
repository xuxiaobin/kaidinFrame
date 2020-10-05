package com.kaidin.gui.controller;

import com.kaidin.biz.service.IQrCodeService;
import com.kaidin.common.util.log.LoggerUtil;
import com.kaidin.common.util.query.PageData;
import com.kaidin.common.util.query.PageDataBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 二维码
 * @author xuxiaobin	kaidin@foxmail.com
 *
 */
@Controller
@RequestMapping
public class QRCodeController {
	private static final transient Logger LOGGER = LoggerFactory.getLogger(QRCodeController.class);

	@Resource
	private IQrCodeService qrCodeService;

	@RequestMapping("/r/{code}")
	public void redirect(@PathVariable String code, HttpServletResponse resp) throws IOException {
		LoggerUtil.debug(LOGGER, "/r/{0}", code);
		String uri = qrCodeService.decode(code);
		resp.sendRedirect(uri);
	}

	@RequestMapping("/f/{code}")
	public void forward(@PathVariable String code, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		LoggerUtil.debug(LOGGER, "/f/{0}", code);
		String uri = qrCodeService.decode(code);
		req.getRequestDispatcher(uri).forward(req, resp);
	}

	/**
	 * 初始化二维码
	 * uri可以为空
	 * @param uri
	 * @return
	 */
	@RequestMapping("/qrCode/initActiveCode.json")
	@ResponseBody
	public PageData<String> initActiveCode(String uri) {
		LoggerUtil.debug(LOGGER, "/qrCode/initActiveCode.json orgReq={0}", uri);
		return PageDataBuilder.success(qrCodeService.initActiveCode(uri));
	}

	/**
	 * 重定向二维码
	 * qrCode不能为空，uri不能为空
	 * @param qrCode
	 * @param uri
	 * @return
	 */
	@RequestMapping("/qrCode/redirectCode.json")
	@ResponseBody
	public PageData<Void> redirectCode(String qrCode, String uri) {
		LoggerUtil.debug(LOGGER, "/qrCode/redirectCode.json orgReq={0},{1}", qrCode, uri);
		qrCodeService.redirectCode(qrCode, uri);
		return PageDataBuilder.success();
	}

	/**
	 * 启用二维码
	 * qrCode不能为空，uri为空表示不修改uri
	 * @param qrCode
	 * @param uri
	 * @return
	 */
	@RequestMapping("/qrCode/enableCode.json")
	@ResponseBody
	public PageData<Void> enableCode(String qrCode, String uri) {
		LoggerUtil.debug(LOGGER, "/qrCode/enableCode.json orgReq={0},{1}", qrCode, uri);
		qrCodeService.enableCode(qrCode, uri);
		return PageDataBuilder.success();
	}

	/**
	 * 失效二维码
	 * qrCode不能为空
	 * @param qrCode
	 * @return
	 */
	@RequestMapping("/qrCode/disableCode.json")
	@ResponseBody
	public PageData<Void> disableCode(String qrCode) {
		LoggerUtil.debug(LOGGER, "/qrCode/disableCode.json orgReq={0}", qrCode);
		qrCodeService.disableCode(qrCode);
		return PageDataBuilder.success();
	}
}
