package top.alexmmd.dog.service.impl;

import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.alexmmd.dog.dao.MorningDao;
import top.alexmmd.dog.service.IMorningService;

/**
 * @author wangyonghui
 * @date 2023年01月06日 16:36:00
 */
@Service
@Slf4j
public class MorningServiceImpl implements IMorningService {

    @Resource
    private MorningDao morningDao;

    @Override
    public void addMorning(String morning) {
        morningDao.insert(morning);
    }

    @Override
    public String queryMorning() {
        return morningDao.queryMorning();
    }
}
