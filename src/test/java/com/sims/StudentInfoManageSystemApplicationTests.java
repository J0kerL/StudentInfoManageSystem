package com.sims;

import cn.hutool.crypto.digest.BCrypt;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StudentInfoManageSystemApplicationTests {


    @Test
    void encrypt() {
        String rawPassword = "123456";
        String newPassword = BCrypt.hashpw(rawPassword);
        System.out.println("密码加密得到：" + newPassword);
    }

    @Test
    void Verify() {
        String rawPassword = "123456";
        String encryptedPassword = "$2a$12$N7RJajdRNh5sLl99tuB18euJhpiLmkDsUEdmcTBuYcovSWpSdfsu.";
        boolean checkpw = BCrypt.checkpw(rawPassword, encryptedPassword);
        System.out.println(checkpw ? "密码正确" : "密码错误");
    }

}