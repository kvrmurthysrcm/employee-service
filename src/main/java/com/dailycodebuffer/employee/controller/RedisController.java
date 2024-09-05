package com.dailycodebuffer.employee.controller;

import com.dailycodebuffer.employee.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisService redisService;

    @GetMapping("/set")
    public String setValue(@RequestParam String key, @RequestParam String value) {
        redisService.setValue(key, value);
        return "Value set in Redis!";
    }


    @GetMapping("/get")
    public String getValue1(@RequestParam String key) {
        System.out.println("1. Value from Redis: for the key:" + key + ", " + redisService.getValue(key));
        return "Value from Redis: " + redisService.getValue(key);
    }

    @GetMapping("/get/{key}")
    public String getValue(@RequestParam String key) {
        System.out.println("2. Value from Redis: for the key:" + key + ", " + redisService.getValue(key));
        return "Value from Redis: " + redisService.getValue(key);
    }

    @GetMapping("/delete")
    public String deleteValue(@RequestParam String key) {

        redisService.deleteValue(key);
        return "Key deleted from Redis!";
    }

    @GetMapping("/addtolist")
    public String addToList(@RequestParam String key, @RequestParam String value) {
        redisService.addToList(key, value);
        return "Value added to Redis list!";
    }

    @GetMapping("/getlist")
    public Object getList(@RequestParam String key, @RequestParam long start, @RequestParam long end) {
        return redisService.getList(key, start, end);
    }
}