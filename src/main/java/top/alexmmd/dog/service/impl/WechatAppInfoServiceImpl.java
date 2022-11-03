package top.alexmmd.dog.service.impl;

import top.alexmmd.dog.entity.WechatAppInfo;
import top.alexmmd.dog.dao.WechatAppInfoDao;
import top.alexmmd.dog.service.WechatAppInfoService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * 微信应用信息管理(WechatAppInfo)表服务实现类
 *
 * @author makejava
 * @since 2022-10-11 09:01:59
 */
@Service("wechatAppInfoService")
public class WechatAppInfoServiceImpl implements WechatAppInfoService {
    @Resource
    private WechatAppInfoDao wechatAppInfoDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public WechatAppInfo queryById(Integer id) {
        return this.wechatAppInfoDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param wechatAppInfo 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<WechatAppInfo> queryByPage(WechatAppInfo wechatAppInfo, PageRequest pageRequest) {
        long total = this.wechatAppInfoDao.count(wechatAppInfo);
        return new PageImpl<>(this.wechatAppInfoDao.queryAllByLimit(wechatAppInfo, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param wechatAppInfo 实例对象
     * @return 实例对象
     */
    @Override
    public WechatAppInfo insert(WechatAppInfo wechatAppInfo) {
        this.wechatAppInfoDao.insert(wechatAppInfo);
        return wechatAppInfo;
    }

    /**
     * 修改数据
     *
     * @param wechatAppInfo 实例对象
     * @return 实例对象
     */
    @Override
    public WechatAppInfo update(WechatAppInfo wechatAppInfo) {
        this.wechatAppInfoDao.update(wechatAppInfo);
        return this.queryById(wechatAppInfo.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.wechatAppInfoDao.deleteById(id) > 0;
    }
}
