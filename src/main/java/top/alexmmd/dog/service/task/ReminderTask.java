package top.alexmmd.dog.service.task;

import javax.annotation.Resource;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.alexmmd.dog.service.IMorningService;
import top.alexmmd.dog.service.IReminderService;

/**
 * @author wangyonghui
 * @date 2023年01月06日 17:30:00
 */
@Component
@Profile("prod")
public class ReminderTask {

    @Resource
    private IReminderService reminderService;

    @Scheduled(cron = "0 0 7 * * ?")
    public void reminders() {
        reminderService.reminders();
    }

}
