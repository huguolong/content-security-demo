package com.doyd.vo;

import lombok.Data;

@Data
public class SignBaseVo {

    /**
     * 标识
     */
    private String token;
    /**
     * 密钥
     */
    private String secret;
    /**
     * 随机数据
     */
    private String nonce;

    /**
     * 时间戳
     */
    private String timestamp;

    /**
     *  签名串
     */
    private String sign;

}
