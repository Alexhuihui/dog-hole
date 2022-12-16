package top.alexmmd.dog.controller;

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
@Slf4j
public class IndexController {

    @GetMapping("/name")
    public String name() {
        log.info(SecurityContextHolder.getContext().getAuthentication().getName());
        return "dog-hole";
    }
}
