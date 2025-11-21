package com.sims.controller.user;

import com.sims.model.dto.user.LoginRequest;
import com.sims.model.dto.user.PageQueryDTO;
import com.sims.model.dto.user.RegisterRequest;
import com.sims.model.vo.UserVO;
import com.sims.result.PageResult;
import com.sims.result.Result;
import com.sims.service.UserService;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

/**
 * @author Diamond
 * @create 2025-11-12 16:13
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户注册
     *
     * @param registerRequest
     * @return
     */
    @PostMapping("/register")
    public Result<String> register(@RequestBody RegisterRequest registerRequest) {
        userService.register(registerRequest);
        return Result.success("注册成功");
    }

    /**
     * 登录
     *
     * @param loginRequest
     * @return
     */
    @PostMapping("/login")
    public Result<String> login(@RequestBody LoginRequest loginRequest) {
        String token = userService.login(loginRequest);
        return Result.success(token);
    }

    /**
     * 分页查询
     *
     * @param pageQueryDTO
     * @return
     */
    @GetMapping("/page")
    public Result<PageResult> page(PageQueryDTO pageQueryDTO) {
        PageResult pageResult = userService.page(pageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 退出登录
     *
     * @param request HTTP请求对象
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout(HttpServletRequest request) {
        // 从request header中获取token
        String token = request.getHeader("Authorization");
        if (StringUtils.isNotBlank(token) && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        userService.logout(token);
        return Result.success("退出登录成功", null);
    }

    /**
     * 查看个人信息
     *
     * @return
     */
    @GetMapping("/profile")
    public Result<UserVO> profile() {
        return Result.success(userService.profile());
    }

}