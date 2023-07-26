package top.alexmmd.dog.service;

import top.alexmmd.dog.domain.entity.Project;

/**
 * @author wangyonghui
 * @since 2023年07月25日 14:06:00
 */
public interface IProjectService {

    void saveProject(Project project);

    Project findByOwnerIdAndName(int ownerId, String name);

}
