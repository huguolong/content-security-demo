package com.doyd.demo;

import com.alibaba.fastjson.JSONObject;
import com.doyd.remote.DemoRemote;
import com.doyd.utils.HttpClient4Utils;
import com.doyd.utils.SignUtils;
import org.apache.http.Consts;
import org.apache.http.client.HttpClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
public class SignDemo {

    @Resource
    private DemoRemote demoRemote;

    /** 实例化HttpClient，发送http请求使用，可根据需要自行调参 */
    private static HttpClient httpClient = HttpClient4Utils.createHttpClient(100, 20, 10000, 2000, 2000);

    String token = "0d523a83621047b9a3df80a458753781";
    String secret = "E6B3AEEF6BBF3EED757B2B8CB6F7F976";

    @RequestMapping("/check")
    public String testCheck(Integer type)throws Exception{
        Map<String,String> map = new HashMap<>(8);
        String content = "";
        switch (type){
            case 1: content = "啤酒厂旧址重现石桥铺夜市我闯进了";break;
            case 2: content = "https://gw.alicdn.com/imgextra/i4/59/O1CN01jCj0DG1CJ3qMoUtJv_!!59-0-lubanu.jpg"; break;
            case 3: content = "https://doyd-test.oss-cn-shenzhen.aliyuncs.com/mgcTestFile.docx"; break;
        }

        //签名开始
        long timestamp = System.currentTimeMillis();
        map.put("doydkey",token);
//        map.put("nonce",String.valueOf(Math.abs(new Random().nextInt())));、
        map.put("nonce","437667500");
        map.put("timestamp",String.valueOf(timestamp));
//        map.put("timestamp","1563932805397");
        String sign = SignUtils.genSign(secret,map);
        System.out.println("sign:"+sign);
        map.put("sign",sign);

        String signParam = SignUtils.genSignParam(map);

        //签名结束
        map.put("type",String.valueOf(type));
        map.put("content",content);
        String response = HttpClient4Utils.sendPost(httpClient,"http://192.168.4.226:8089/content-security/check?"+signParam,map);

        return response;
    }

    @RequestMapping("/text/check")
    public String testTextCheck() throws Exception{
        Map<String,String> map = new HashMap<>();

        //签名开始
        long timestamp = System.currentTimeMillis();
        map.put("token",token);
        map.put("nonce",String.valueOf(Math.abs(new Random().nextInt())));
        map.put("timestamp",String.valueOf(timestamp));
        String sign = SignUtils.genSign(secret,map);
        System.out.println("sign:"+sign);
        map.put("sign",sign);
        //签名结束

        //请求参数
        map.put("content","测试内容安全-文本内容");
        JSONObject res = demoRemote.textCheck(map);

        return res.toJSONString();
    }
    @RequestMapping("/image/check")
    public String testImageCheck() throws Exception{

        Map<String,String> map = new HashMap<>();

        //签名开始
        long timestamp = System.currentTimeMillis();
        map.put("token",token);
        map.put("nonce",String.valueOf(Math.abs(new Random().nextInt())));
        map.put("timestamp",String.valueOf(timestamp));
        String sign = SignUtils.genSign(secret,map);
        System.out.println("sign:"+sign);
        map.put("sign",sign);
        //签名结束

        //请求参数
        map.put("images","https://gw.alicdn.com/imgextra/i4/59/O1CN01jCj0DG1CJ3qMoUtJv_!!59-0-lubanu.jpg");
        JSONObject res = demoRemote.imageCheck(map);
        return res.toJSONString();

    }

}
