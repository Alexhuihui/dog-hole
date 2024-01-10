package top.alexmmd.dog.domain.entity;


import java.util.Date;
import lombok.Data;

/**
 * @author wangyonghui
 * @since 2024年01月10日 17:22:00
 */
@Data
public class Reminder {

    private Integer id;
    private Integer userId;
    private Date reminderTime;
    private String content;

}

