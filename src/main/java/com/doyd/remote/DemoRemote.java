package com.doyd.remote;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
@FeignClient(name = "demoRemote" ,url = "http://192.168.4.42:8089")
//@FeignClient(name = "demoRemote" ,url = "http://134.175.170.169:8089")
//@FeignClient(value = "content-security")
public interface DemoRemote {

    @PostMapping(value = "/check",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    JSONObject check(@RequestBody Map<String, String> map);
    /**
     * 文本检查
     */
    @PostMapping(value = "/text/check",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    JSONObject textCheck(@RequestBody Map<String, String> map);

    /**
     *  图片检测
     */
    @PostMapping(value = "/image/check",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    JSONObject imageCheck(@RequestBody Map<String, String> map);
}
