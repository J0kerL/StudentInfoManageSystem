package com.sims;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Administrator
 */
@SpringBootApplication
@MapperScan("com.sims.mapper")
public class StudentInfoManageSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentInfoManageSystemApplication.class, args);
        System.out.println("启动成功");
    }

}
