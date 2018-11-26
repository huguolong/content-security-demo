package com.doyd.demo;

import com.alibaba.fastjson.JSONObject;
import com.doyd.remote.DemoRemote;
import com.doyd.utils.SignUtils;
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

    String token = "0d523a83621047b9a3df80a458753782";
    String secret = "E6B3AEEF6BBF3EED757B2B8CB6F7F975";

    @RequestMapping("/check")
    public String testCheck(Integer type)throws Exception{
        Map<String,String> map = new HashMap<>(8);
        String content = "";
        switch (type){
            case 1: content = "测试内容安全-文本内容,肉体";break;
            case 2: content = "https://gw.alicdn.com/imgextra/i4/59/O1CN01jCj0DG1CJ3qMoUtJv_!!59-0-lubanu.jpg"; break;
        }

        //签名开始
        long timestamp = System.currentTimeMillis();
        map.put("token",token);
        map.put("nonce",String.valueOf(Math.abs(new Random().nextInt())));
        map.put("timestamp",String.valueOf(timestamp));
        String sign = SignUtils.genSign(secret,map);
        System.out.println("sign:"+sign);
        map.put("sign",sign);
        //签名结束
        map.put("type",String.valueOf(type));
        map.put("content",content);
        JSONObject res = demoRemote.check(map);
        return res.toJSONString();
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
