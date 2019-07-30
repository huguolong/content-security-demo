package com.doyd.utils;

import com.doyd.vo.SignBaseVo;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Component
public class SignUtils {

    public static String genSignParam(Map<String, String> params){
        String[] keys = params.keySet().toArray(new String[0]);
        StringBuffer paramBuffer = new StringBuffer();
        for (String key : keys) {
            paramBuffer.append(key).append("=").append(params.get(key) == null ? "" : params.get(key)).append("&");
        }
        return paramBuffer.toString().substring(0,paramBuffer.length()-1);
    }

    /**
     * 生成签名信息
     * @param secret 产品私钥
     * @param params 接口请求参数名和参数值map，不包括signature参数名
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String genSign(String secret, Map<String, String> params) throws UnsupportedEncodingException {
        // 1. 参数名按照ASCII码表升序排序
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);

        // 2. 按照排序拼接参数名与参数值
        StringBuffer paramBuffer = new StringBuffer();
        for (String key : keys) {
            if("sign".equals(key) || "doydsecret".equals(key)){
                continue;
            }
            paramBuffer.append(key).append("=").append(params.get(key) == null ? "" : params.get(key)).append("&");
//            paramBuffer.append(key).append(params.get(key) == null ? "" : params.get(key));
        }
        // 3. 将secretKey拼接到最后
        paramBuffer.append("doydsecret=").append(secret);
//        paramBuffer.append(secret);
        System.out.println("paramBuffer:"+paramBuffer.toString());

        // 4. MD5是128位长度的摘要算法，用16进制表示，一个十六进制的字符能表示4个位，所以签名后的字符串长度固定为32个十六进制字符。
        return DigestUtils.md5Hex(paramBuffer.toString().getBytes("UTF-8"));
    }

    public static void main(String[] args) throws Exception{

        Map<String,String> map = new HashMap<>();
        long time = System.currentTimeMillis();
        System.out.println(time);
        map.put("nonce","856546251");
        map.put("timestamp",String.valueOf(time));
        map.put("doydkey","0d523a83621047b9a3df80a458753781");

        String sign = genSign("E6B3AEEF6BBF3EED757B2B8CB6F7F976",map);

        map.put("sign",sign);


        System.out.println(sign);



    }



}
