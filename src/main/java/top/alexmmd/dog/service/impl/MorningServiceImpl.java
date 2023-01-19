package top.alexmmd.dog.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import java.util.Collections;
import java.util.List;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import top.alexmmd.dog.dao.MorningDao;
import top.alexmmd.dog.service.IMorningService;
import top.alexmmd.dog.starter.authorization.AuthContext;

/**
 * @author wangyonghui
 * @date 2023年01月06日 16:36:00
 */
@Service
@Slf4j
public class MorningServiceImpl implements IMorningService {

    public static final String FORMAT = "亲爱的虎仔，早安***";
    public static final String NICK_NAME = "劳伦兹";
    @Resource
    private MorningDao morningDao;

    @Resource
    private RestTemplate restTemplate;

    @Override
    public void addMorning(String morning) {
        morningDao.insert(morning);
    }

    @Override
    public List<String> queryMorning() {
        if (!StrUtil.containsAny(AuthContext.getName(), NICK_NAME)) {
            return Collections.singletonList("您还没有和您的男/女朋友绑定，请先联系管理员进行绑定");
        }
        String morning = morningDao.queryMorning();
        return StrUtil.split(morning, "***");
    }

    @Override
    public String generate() {
        StringBuilder morning = new StringBuilder(FORMAT);
        morning.append(DateUtil.formatChineseDate(new DateTime(), false, false) + "***");
        this.getWeather(morning);
        this.getCount(morning);
        this.getEngaged(morning);
        this.getCHP(morning);
        return morning.toString();
    }

    private void getCHP(StringBuilder morning) {
        String url = "https://api.shadiao.pro/chp";
        JSONObject result = restTemplate.getForObject(url, JSONObject.class);
        String text = result.getJSONObject("data").getStr("text");
        morning.append(text);
    }

    private void getWeather(StringBuilder morning) {
        String url = "http://autodev.openspeech.cn/csp/api/v2.1/weather?openId=aiuicus&clientType=android&sign=android&city=深圳";
        JSONObject result = restTemplate.getForObject(url, JSONObject.class);
        JSONObject object = result.getJSONObject("data").getJSONArray("list").getJSONObject(0);
        morning.append("今日天气: " + object.getStr("weather") + "***");
        morning.append("当前温度: " + object.getDouble("temp").intValue() + "***");
    }

    private void getCount(StringBuilder morning) {
        long betweenDay = DateUtil.betweenDay(DateUtil.parseDate("2022-03-11"), DateUtil.date(),
                true);
        morning.append("今日是我们的第 " + betweenDay + " 天***");
    }

    private void getEngaged(StringBuilder morning) {
        long betweenDay = DateUtil.betweenDay(DateUtil.date(), DateUtil.parseDate("2023-02-01"),
                true);
        morning.append("距离我们订婚还有 " + betweenDay + " 天***");
    }

}
