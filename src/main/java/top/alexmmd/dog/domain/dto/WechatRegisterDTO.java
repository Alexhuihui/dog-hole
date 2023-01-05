package top.alexmmd.dog.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wangyonghui
 * @date 2022年12月28日 09:24:00
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WechatRegisterDTO {

    private String code;

    private String nickName;

    private String avatarUrl;

}
