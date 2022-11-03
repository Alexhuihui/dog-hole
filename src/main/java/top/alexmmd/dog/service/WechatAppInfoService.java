package top.alexmmd.dog.service;

import top.alexmmd.dog.entity.WechatAppInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 微信应用信息管理(WechatAppInfo)表服务接口
 *
 * @author makejava
 * @since 2022-10-11 09:01:59
 */
public interface WechatAppInfoService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    WechatAppInfo queryById(Integer id);

    /**
     * 分页查询
     *
     * @param wechatAppInfo 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<WechatAppInfo> queryByPage(WechatAppInfo wechatAppInfo, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param wechatAppInfo 实例对象
     * @return 实例对象
     */
    WechatAppInfo insert(WechatAppInfo wechatAppInfo);

    /**
     * 修改数据
     *
     * @param wechatAppInfo 实例对象
     * @return 实例对象
     */
    WechatAppInfo update(WechatAppInfo wechatAppInfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
