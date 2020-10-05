package com.kaidin.biz.service.impl;

import com.kaidin.biz.common.constant.CommonErrEnum;
import com.kaidin.biz.common.constant.QrCodeStatusEnum;
import com.kaidin.biz.service.IQrCodeService;
import com.kaidin.common.util.DateTimeUtil;
import com.kaidin.common.util.StringUtil;
import com.kaidin.common.util.constant.ConstType;
import com.kaidin.common.util.exception.AssertUtil;
import com.kaidin.db.dao.IEntityQrCodeDao;
import com.kaidin.db.entity.EntityQrCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Base64;
import java.util.Date;
import java.util.Random;

/**
 * 二维码相关服务
 *
 * @author xiaobin
 * @date 2020-10-04 15:55
 */
@Service
public class QrCodeServiceImpl implements IQrCodeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(QrCodeServiceImpl.class);
    private static final long OFFSET_TIMES = (2021 - 1970) * ConstType.time.MS_OF_DAY * 365;
    private static final Base64.Encoder ENCODER = Base64.getUrlEncoder();
    private static final Base64.Decoder DECODER = Base64.getUrlDecoder();
    @Resource
    private IEntityQrCodeDao entityQrCodeDao;

    @Override
    public String initActiveCode(String uri) {
        String result = generateCode();

        EntityQrCode entityQrCode = new EntityQrCode();
        entityQrCode.setCreateTime(new Date());
        entityQrCode.setCode(result);
        entityQrCode.setStatus(QrCodeStatusEnum.INIT.getCode());
        entityQrCode.setType("ACTIVE");
        entityQrCode.setUri(uri);
        entityQrCodeDao.save(entityQrCode);

        return result;
    }

    /**
     * 重定向二维码
     * qrCode不能为空，uri不能为空
     * @param qrCode
     * @param uri
     * @return
     */
    @Override
    public boolean redirectCode(String qrCode, String uri) {
        AssertUtil.isNotBlank(qrCode, CommonErrEnum.PARAMETER_ILLEGAL, "qrCode不能为空");
        AssertUtil.isNotBlank(uri, CommonErrEnum.PARAMETER_ILLEGAL, "uri不能为空");

        String hql = EntityQrCode.P_Code + "=:qrCode";
        EntityQrCode entityQrCode = entityQrCodeDao.queryEntity(hql, "qrCode", qrCode);
        AssertUtil.isNotNull(entityQrCode, CommonErrEnum.PARAMETER_ILLEGAL, "二维码不存在");
        entityQrCode.setUri(uri);
        entityQrCodeDao.update(entityQrCode);

        return true;
    }

    /**
     * 启用二维码
     * qrCode不能为空，uri为空表示不修改uri
     * @param qrCode
     * @return
     */
    @Override
    public boolean enableCode(String qrCode, String uri) {
        AssertUtil.isNotBlank(qrCode, CommonErrEnum.PARAMETER_ILLEGAL, "qrCode不能为空");

        String hql = EntityQrCode.P_Code + "=:qrCode";
        EntityQrCode entityQrCode = entityQrCodeDao.queryEntity(hql, "qrCode", qrCode);
        AssertUtil.isNotNull(entityQrCode, CommonErrEnum.PARAMETER_ILLEGAL, "二维码不存在");
        if (StringUtil.isNotBlank(uri)) {
            entityQrCode.setUri(uri);
        }
        entityQrCode.setStatus(QrCodeStatusEnum.ENABLE.getCode());
        entityQrCodeDao.update(entityQrCode);

        return true;
    }

    /**
     * 失效二维码
     * qrCode不能为空
     * @param qrCode
     * @return
     */
    @Override
    public boolean disableCode(String qrCode) {
        AssertUtil.isNotBlank(qrCode, CommonErrEnum.PARAMETER_ILLEGAL, "qrCode不能为空");

        String hql = EntityQrCode.P_Code + "=:qrCode";
        EntityQrCode entityQrCode = entityQrCodeDao.queryEntity(hql, "qrCode", qrCode);
        AssertUtil.isNotNull(entityQrCode, CommonErrEnum.PARAMETER_ILLEGAL, "二维码不存在");
        entityQrCode.setStatus(QrCodeStatusEnum.DISABLE.getCode());
        entityQrCodeDao.update(entityQrCode);

        return true;
    }

    @Override
    public String decode(String qrCode) {
        String hql = EntityQrCode.P_Code + "=:qrCode";
        EntityQrCode entityQrCode = entityQrCodeDao.queryEntity(hql, "qrCode", qrCode);
        if (null == entityQrCode) {
            return null;
        }
        QrCodeStatusEnum statusEnum = QrCodeStatusEnum.codeOf(entityQrCode.getStatus());
        AssertUtil.isNotNull(statusEnum, CommonErrEnum.BIZ_ERROR, QrCodeStatusEnum.INIT.getDesc());
        AssertUtil.isEquals(QrCodeStatusEnum.ENABLE, statusEnum, CommonErrEnum.BIZ_ERROR, statusEnum.getDesc());

        return entityQrCode.getUri();
    }

    /**
     * 生成二维码短码
     * 40位时间毫秒数-偏移量，够用到2054-11-03 03:53:47
     *
     * @return
     */
    public static String generateCode() {
        long millis40 = System.currentTimeMillis() - OFFSET_TIMES;
        byte[] byteArray = new byte[6];
        for (int i = 0; i < 5; i++) {
            byteArray[i] = (byte)(millis40 & 0xFF);
            millis40 >>= 8;
        }
        byteArray[5] = (byte) new Random().nextInt(9);
        return ENCODER.encodeToString(byteArray);
    }

    /**
     * 把二维码反向解析，获取生成时间
     * @param qrCode
     * @return
     */
    public static String takeDateTime(String qrCode) {
        byte[] byteArray = DECODER.decode(qrCode);
        long millis40 = 0;
        for (int i = 4; i >= 0; i--) {
            millis40 <<= 8;
            millis40 |= (byteArray[i] & 0xFF);
        }
        long times = millis40 + OFFSET_TIMES;
        return DateTimeUtil.getDateToString(new Date(times), "yyyy-MM-dd HH:mm:ss.SSS");
    }
}
