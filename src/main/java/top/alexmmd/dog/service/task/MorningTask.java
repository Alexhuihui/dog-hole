package top.alexmmd.dog.service.task;

import javax.annotation.Resource;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.alexmmd.dog.service.IMorningService;

/**
 * @author wangyonghui
 * @date 2023年01月06日 17:30:00
 */
@Component
@Profile("prod")
public class MorningTask {

    @Resource
    private IMorningService morningService;

    @Scheduled(cron ="0 0 7 * * ?")
    public void dailyMorning() {
        String morning = morningService.generate();
        morningService.addMorning(morning);
    }
}
