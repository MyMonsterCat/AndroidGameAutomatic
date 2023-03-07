package com.monster.swing;

import org.springframework.stereotype.Component;

@Component
public class MySer {

    public void ss(String x, String y) {
        System.out.println("被调用啦" + x + "," + y);
    }
}
