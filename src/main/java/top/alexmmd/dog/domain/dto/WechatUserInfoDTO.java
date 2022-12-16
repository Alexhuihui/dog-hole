package top.alexmmd.dog.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wangyonghui
 * @date 2022年12月16日 10:20:00
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WechatUserInfoDTO {

    /**
     * 用户在开放平台的唯一标识符
     */
    private String unionId;

    /**
     * 用户唯一标识
     */
    private String openId;

}
