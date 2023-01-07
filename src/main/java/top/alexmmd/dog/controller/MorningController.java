package top.alexmmd.dog.controller;

import java.util.List;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.alexmmd.common.base.http.response.ObjectResponse;
import top.alexmmd.dog.service.IMorningService;
import top.alexmmd.dog.starter.authorization.AuthContext;

/**
 * @author wangyonghui
 * @date 2022年10月10日 11:26:00
 */
@RestController
@RequestMapping("/morning")
@Slf4j
public class MorningController {

    @Resource
    private IMorningService morningService;

    @GetMapping("/name")
    public String name() {
        log.info(AuthContext.getName());
        log.info(AuthContext.getUid().toString());
        AuthContext.getRoleList().forEach(role -> System.out.println("role = " + role));
        return AuthContext.getName();
    }

    @PostMapping("/addMorning")
    public ObjectResponse<String> addMorning(@RequestParam String morning) {
        morningService.addMorning(morning);
        return ObjectResponse.success();
    }

    @GetMapping("/queryMorning")
    public ObjectResponse<List<String>> queryMorning() {
        return ObjectResponse.success(morningService.queryMorning());
    }
}
