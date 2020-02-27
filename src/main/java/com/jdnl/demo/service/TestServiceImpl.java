package com.jdnl.demo.service;

import org.springframework.stereotype.Service;

@Service("testService")
public class TestServiceImpl implements TestService {
    @Override
    public int add(int num1, int num2) {
        return num1 + num2;
    }
}
