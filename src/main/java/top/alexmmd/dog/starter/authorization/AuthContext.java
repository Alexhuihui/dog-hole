package top.alexmmd.dog.starter.authorization;

import cn.hutool.core.util.StrUtil;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import top.alexmmd.dog.domain.entity.Role;
import top.alexmmd.dog.domain.vo.UserVO;

/**
 * @author wangyonghui
 * @date 2023年01月05日 14:23:00
 */
public final class AuthContext {

    public static final String ANONYMOUS_USER = "anonymousUser";

    private static UserVO transAuthentication2UserVO(Authentication authentication) {
        return (UserVO) authentication.getPrincipal();
    }

    public static String getName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !StrUtil.equals(ANONYMOUS_USER, authentication.getName())
                ? transAuthentication2UserVO
                (authentication).getNickName() : authentication.getName();
    }

    public static Long getUid() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.isAuthenticated() ? transAuthentication2UserVO(
                authentication).getUid() : null;
    }

    public static List<Role> getRoleList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.isAuthenticated() ? transAuthentication2UserVO(
                authentication).getRoleList() : null;
    }

}
