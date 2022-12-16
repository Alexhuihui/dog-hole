package top.alexmmd.dog.controller;

import javax.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.alexmmd.dog.domain.entity.Rest;
import top.alexmmd.dog.domain.entity.RestBody;
import top.alexmmd.dog.service.IUserService;

/**
 * @author wangyonghui
 * @date 2022年11月30日 17:09:00
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Resource
    private IUserService IUserService;

    /**
     * 登录失败返回 401 以及提示信息. * * @return the rest
     */
    @PostMapping("/failure")
    public Rest loginFailure() {
        return RestBody.failure(HttpStatus.UNAUTHORIZED.value(), "登录失败了，老哥");
    }

    /**
     * 登录成功后拿到个人信息.
     *
     * @return the rest
     */
    @PostMapping("/success")
    public Rest loginSuccess() {
        // 登录成功后用户的认证信息 UserDetails会存在 安全上下文寄存器 SecurityContextHolder 中
        User principal = (User) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = principal.getUsername();
        return RestBody.okData(username, "登录成功");
    }
}
