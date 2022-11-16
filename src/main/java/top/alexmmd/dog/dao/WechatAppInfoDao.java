package top.alexmmd.dog.dao;

import top.alexmmd.dog.entity.WechatAppInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 微信应用信息管理(WechatAppInfo)表数据库访问层
 *
 * @author makejava
 * @since 2022-10-11 08:50:43
 */
public interface WechatAppInfoDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    WechatAppInfo queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param wechatAppInfo 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<WechatAppInfo> queryAllByLimit(WechatAppInfo wechatAppInfo, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param wechatAppInfo 查询条件
     * @return 总行数
     */
    long count(WechatAppInfo wechatAppInfo);

    /**
     * 新增数据
     *
     * @param wechatAppInfo 实例对象
     * @return 影响行数
     */
    int insert(WechatAppInfo wechatAppInfo);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<WechatAppInfo> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<WechatAppInfo> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<WechatAppInfo> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<WechatAppInfo> entities);

    /**
     * 修改数据
     *
     * @param wechatAppInfo 实例对象
     * @return 影响行数
     */
    int update(WechatAppInfo wechatAppInfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

