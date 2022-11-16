package top.alexmmd.dog.dao;

import top.alexmmd.dog.entity.UsrUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (UsrUser)表数据库访问层
 *
 * @author makejava
 * @since 2022-10-11 08:50:43
 */
public interface UsrUserDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    UsrUser queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param usrUser 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<UsrUser> queryAllByLimit(UsrUser usrUser, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param usrUser 查询条件
     * @return 总行数
     */
    long count(UsrUser usrUser);

    /**
     * 新增数据
     *
     * @param usrUser 实例对象
     * @return 影响行数
     */
    int insert(UsrUser usrUser);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<UsrUser> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<UsrUser> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<UsrUser> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<UsrUser> entities);

    /**
     * 修改数据
     *
     * @param usrUser 实例对象
     * @return 影响行数
     */
    int update(UsrUser usrUser);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

