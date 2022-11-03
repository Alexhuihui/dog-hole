package top.alexmmd.dog.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.security.PermitAll;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangyonghui
 * @date 2022年10月10日 11:26:00
 */
@RestController
@RequestMapping("/index")
@Api(value = "IndexController", tags = "首页控制器")
@Slf4j
public class IndexController {

    @GetMapping("/name")
    @ApiOperation(value = "获取名称")
    @PermitAll
    public String name() {
        log.info(SecurityContextHolder.getContext().getAuthentication().getName());
        return "dog-hole";
    }
}
