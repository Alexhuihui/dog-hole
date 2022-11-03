package top.alexmmd.dog.service.impl;

import top.alexmmd.dog.entity.UsrLoginAccount;
import top.alexmmd.dog.dao.UsrLoginAccountDao;
import top.alexmmd.dog.service.UsrLoginAccountService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * (UsrLoginAccount)表服务实现类
 *
 * @author makejava
 * @since 2022-10-11 09:02:02
 */
@Service("usrLoginAccountService")
public class UsrLoginAccountServiceImpl implements UsrLoginAccountService {
    @Resource
    private UsrLoginAccountDao usrLoginAccountDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public UsrLoginAccount queryById(Integer id) {
        return this.usrLoginAccountDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param usrLoginAccount 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<UsrLoginAccount> queryByPage(UsrLoginAccount usrLoginAccount, PageRequest pageRequest) {
        long total = this.usrLoginAccountDao.count(usrLoginAccount);
        return new PageImpl<>(this.usrLoginAccountDao.queryAllByLimit(usrLoginAccount, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param usrLoginAccount 实例对象
     * @return 实例对象
     */
    @Override
    public UsrLoginAccount insert(UsrLoginAccount usrLoginAccount) {
        this.usrLoginAccountDao.insert(usrLoginAccount);
        return usrLoginAccount;
    }

    /**
     * 修改数据
     *
     * @param usrLoginAccount 实例对象
     * @return 实例对象
     */
    @Override
    public UsrLoginAccount update(UsrLoginAccount usrLoginAccount) {
        this.usrLoginAccountDao.update(usrLoginAccount);
        return this.queryById(usrLoginAccount.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.usrLoginAccountDao.deleteById(id) > 0;
    }
}
