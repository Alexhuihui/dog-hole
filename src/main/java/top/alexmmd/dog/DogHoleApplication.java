package top.alexmmd.dog;

import java.util.Map;
import java.util.TreeMap;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import top.alexmmd.common.security.annos.EnableSecurity;
import top.alexmmd.dog.config.CopyFilterOrderRegistration;

/**
 * @author 汪永晖
 */
@SpringBootApplication
@MapperScan("top.alexmmd.dog.dao")
@EnableSecurity
public class DogHoleApplication {

    public static void main(String[] args) {
        SpringApplication.run(DogHoleApplication.class, args);

        CopyFilterOrderRegistration filterOrderRegistration = new
                CopyFilterOrderRegistration(); // 获取内置过滤器 此方法并未提供 Map<String, Integer> filterToOrder =
        Map<String, Integer> filterToOrder = filterOrderRegistration.getFilterToOrder();
        TreeMap<Integer, String> orderToFilter = new TreeMap<>();
        filterToOrder.forEach((name, order) -> orderToFilter.put(order, name));
        orderToFilter.forEach((order, name) -> System.out.println(" 顺序：" +
                order + " 类名：" + name));
    }

}
