package top.alexmmd.dog.dao;

import org.apache.ibatis.annotations.Param;
import top.alexmmd.dog.domain.entity.Project;

/**
 * @author wangyonghui
 * @since 2023年07月25日 13:59:00
 */
public interface ProjectDao {

    int insert(Project project);

    Project queryProject(@Param("ownerId") Integer ownerId, @Param("name") String name);

}
