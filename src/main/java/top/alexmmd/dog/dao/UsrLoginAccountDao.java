package top.alexmmd.dog.dao;

import top.alexmmd.dog.entity.UsrLoginAccount;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (UsrLoginAccount)表数据库访问层
 *
 * @author makejava
 * @since 2022-10-11 08:50:43
 */
public interface UsrLoginAccountDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    UsrLoginAccount queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param usrLoginAccount 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<UsrLoginAccount> queryAllByLimit(UsrLoginAccount usrLoginAccount, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param usrLoginAccount 查询条件
     * @return 总行数
     */
    long count(UsrLoginAccount usrLoginAccount);

    /**
     * 新增数据
     *
     * @param usrLoginAccount 实例对象
     * @return 影响行数
     */
    int insert(UsrLoginAccount usrLoginAccount);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<UsrLoginAccount> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<UsrLoginAccount> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<UsrLoginAccount> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<UsrLoginAccount> entities);

    /**
     * 修改数据
     *
     * @param usrLoginAccount 实例对象
     * @return 影响行数
     */
    int update(UsrLoginAccount usrLoginAccount);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

