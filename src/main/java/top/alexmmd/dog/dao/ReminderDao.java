package top.alexmmd.dog.dao;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.alexmmd.dog.domain.entity.Reminder;

/**
 * @author wangyonghui
 * @since 2024年01月10日 17:23:00
 */
@Mapper
public interface ReminderDao {

    List<Reminder> getRemindersWithin7Days(@Param("startDate") Date startDate,
            @Param("endDate") Date endDate);

}
