package top.alexmmd.dog.starter.config;

import javax.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import top.alexmmd.dog.assembler.UserAssembler;
import top.alexmmd.dog.domain.entity.User;
import top.alexmmd.dog.service.IUserService;

/**
 * @author wangyonghui
 * @date 2022年12月12日 11:15:00
 */
@Component
public class UserDetailsRepository {

    @Resource
    private IUserService IUserService;

    public void createUser(UserDetails user) {
        IUserService.createUser(UserAssembler.transUserDetails2User(user));
    }

    public void updateUser(UserDetails user) {
        IUserService.updateUser(UserAssembler.transUserDetails2User(user));
    }

    public void deleteUser(String username) {
        IUserService.deleteUser(username);
    }

    public void changePassword(String oldPassword, String newPassword) {
        IUserService.changePassword(oldPassword, newPassword);
    }

    public boolean userExists(String username) {
        return IUserService.userExists(username);
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = IUserService.loadUserByUsername(username);
        return UserAssembler.transUser2UserDetails(user);
    }
}
