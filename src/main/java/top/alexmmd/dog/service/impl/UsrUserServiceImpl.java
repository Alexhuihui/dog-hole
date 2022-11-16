package top.alexmmd.dog.service.impl;

import top.alexmmd.dog.entity.UsrUser;
import top.alexmmd.dog.dao.UsrUserDao;
import top.alexmmd.dog.service.UsrUserService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * (UsrUser)表服务实现类
 *
 * @author makejava
 * @since 2022-10-11 09:02:02
 */
@Service("usrUserService")
public class UsrUserServiceImpl implements UsrUserService {
    @Resource
    private UsrUserDao usrUserDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public UsrUser queryById(Integer id) {
        return this.usrUserDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param usrUser 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<UsrUser> queryByPage(UsrUser usrUser, PageRequest pageRequest) {
        long total = this.usrUserDao.count(usrUser);
        return new PageImpl<>(this.usrUserDao.queryAllByLimit(usrUser, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param usrUser 实例对象
     * @return 实例对象
     */
    @Override
    public UsrUser insert(UsrUser usrUser) {
        this.usrUserDao.insert(usrUser);
        return usrUser;
    }

    /**
     * 修改数据
     *
     * @param usrUser 实例对象
     * @return 实例对象
     */
    @Override
    public UsrUser update(UsrUser usrUser) {
        this.usrUserDao.update(usrUser);
        return this.queryById(usrUser.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.usrUserDao.deleteById(id) > 0;
    }
}
