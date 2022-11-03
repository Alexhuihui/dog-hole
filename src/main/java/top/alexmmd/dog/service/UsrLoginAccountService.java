package top.alexmmd.dog.service;

import top.alexmmd.dog.entity.UsrLoginAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (UsrLoginAccount)表服务接口
 *
 * @author makejava
 * @since 2022-10-11 09:02:02
 */
public interface UsrLoginAccountService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    UsrLoginAccount queryById(Integer id);

    /**
     * 分页查询
     *
     * @param usrLoginAccount 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<UsrLoginAccount> queryByPage(UsrLoginAccount usrLoginAccount, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param usrLoginAccount 实例对象
     * @return 实例对象
     */
    UsrLoginAccount insert(UsrLoginAccount usrLoginAccount);

    /**
     * 修改数据
     *
     * @param usrLoginAccount 实例对象
     * @return 实例对象
     */
    UsrLoginAccount update(UsrLoginAccount usrLoginAccount);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
