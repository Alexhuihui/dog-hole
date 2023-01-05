package top.alexmmd.dog.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wangyonghui
 * @date 2022年12月28日 10:21:00
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    private Integer id;

    private String name;

    private Long uid;

}
