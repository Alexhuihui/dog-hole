package top.alexmmd.dog.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import top.alexmmd.dog.dao.ProjectDao;
import top.alexmmd.dog.domain.entity.Project;
import top.alexmmd.dog.service.IProjectService;

/**
 * @author wangyonghui
 * @since 2023年07月25日 14:06:00
 */
@Service
public class ProjectServiceImpl implements IProjectService {

    @Resource
    private ProjectDao projectDao;

    public void saveProject(Project project) {
        projectDao.insert(project);
    }

    public Project findByOwnerIdAndName(int ownerId, String name) {
        return projectDao.queryProject(ownerId, name);
    }

}
