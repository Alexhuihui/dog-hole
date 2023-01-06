package top.alexmmd.dog.dao;


/**
 * (LoginAccount)表数据库访问层
 *
 * @author makejava
 * @since 2022-10-11 08:50:43
 */
public interface MorningDao {

    int insert(String morning);

    String queryMorning();
}

