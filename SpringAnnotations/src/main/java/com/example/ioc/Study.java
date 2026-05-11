package com.example.ioc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Study {
    public void read(){
        System.out.println("Student is reading");
    }
}
