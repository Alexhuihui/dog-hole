package top.alexmmd.dog.service;

import java.util.List;

/**
 * @author wangyonghui
 * @date 2023年01月06日 16:35:00
 */
public interface IMorningService {

    void addMorning(String morning);

    List<String> queryMorning();

    String generate();

}
