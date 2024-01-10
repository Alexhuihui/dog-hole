package top.alexmmd.dog.service.impl;

import static cn.hutool.core.date.DatePattern.NORM_DATETIME_PATTERN;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.alexmmd.dog.dao.ReminderDao;
import top.alexmmd.dog.domain.entity.Reminder;
import top.alexmmd.dog.service.IEmailService;
import top.alexmmd.dog.service.IReminderService;

/**
 * @author wangyonghui
 * @since 2024年01月10日 17:15:00
 */
@Service
@Slf4j
public class ReminderServiceImpl implements IReminderService {

    @Resource
    private ReminderDao reminderDao;

    @Resource
    private IEmailService emailService;

    @Override
    public void reminders() {
        // 获取当前日期
        Date currentDate = new Date();

        // 获取7天后的日期
        Date endDate = DateUtil.offsetDay(currentDate, 7);

        // 查询提醒表中距离当前日期7天内的数据
        List<Reminder> remindersWithin7Days = reminderDao.getRemindersWithin7Days(currentDate,
                endDate);

        // 遍历提醒列表，发送邮件提醒
        for (Reminder reminder : remindersWithin7Days) {
            if (DateUtil.isIn(reminder.getReminderTime(), currentDate, endDate)) {
                sendReminderEmail(reminder);
                // 比较当前日期等于提醒日期时，新增一条数据，提醒日期增加一年
                if (DateUtil.isSameDay(currentDate, reminder.getReminderTime())) {
                    addReminderOneYearLater(reminder);
                }
            }
        }
    }

    private void addReminderOneYearLater(Reminder reminder) {
        // 提醒日期增加一年
        Date newReminderDate = DateUtil.offset(reminder.getReminderTime(), DateField.YEAR, 1);

        // 创建新的Reminder对象
        Reminder newReminder = new Reminder();
        newReminder.setUserId(reminder.getUserId());
        newReminder.setReminderTime(newReminderDate);
        newReminder.setContent(reminder.getContent());

        // 插入新的提醒数据到数据库
        reminderDao.insertReminder(newReminder);
    }

    private void sendReminderEmail(Reminder reminder) {
        // 构建邮件内容
        String toEmail = "201520180114@ecut.edu.cn";
        String subject = "Reminder Notification";
        String body = "Reminder for: " + reminder.getContent() + " at " + DateUtil.format(
                reminder.getReminderTime(), NORM_DATETIME_PATTERN);

        // 发送邮件
        emailService.sendEmail(toEmail, subject, body);
        // 记录日志
        log.info("邮件已发送，提醒内容：{}", reminder.getContent());
    }
}
