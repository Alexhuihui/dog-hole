package top.alexmmd.dog.controller;

import cn.hutool.json.JSONUtil;
import javax.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.alexmmd.common.base.http.response.BaseResponse;
import top.alexmmd.common.base.http.response.ObjectResponse;
import top.alexmmd.common.security.jwt.JwtHelper;
import top.alexmmd.dog.domain.dto.WechatRegisterDTO;
import top.alexmmd.dog.domain.vo.UserVO;
import top.alexmmd.dog.service.IUserService;

/**
 * @author wangyonghui
 * @date 2022年11月30日 17:09:00
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Resource
    private IUserService userService;

    @PostMapping("/emailRegister")
    public ObjectResponse<Long> emailRegister(@RequestParam String email,
            @RequestParam String code) {
        return ObjectResponse.success(userService.emailRegister(email), "注册成功");
    }

    @PostMapping("/wechatRegister")
    public ObjectResponse<Long> wechatRegister(@RequestBody WechatRegisterDTO wechatRegisterDTO) {
        return ObjectResponse.success(userService.wechatRegister(wechatRegisterDTO), "注册成功");
    }

    /**
     * 登录失败返回 401 以及提示信息. * * @return the rest
     */
    @PostMapping("/failure")
    public BaseResponse loginFailure() {
        return new BaseResponse(HttpStatus.UNAUTHORIZED.value(), "登录失败了，老哥");
    }

    /**
     * 登录成功后拿到个人信息.
     *
     * @return the rest
     */
    @PostMapping("/success")
    public ObjectResponse<String> loginSuccess() {
        // 登录成功后用户的认证信息 UserDetails会存在 安全上下文寄存器 SecurityContextHolder 中
        UserVO principal = (UserVO) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return ObjectResponse.success(JwtHelper.encode(JSONUtil.toJsonStr(principal)), "登录成功");
    }
}
