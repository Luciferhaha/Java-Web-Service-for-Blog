package dubbo.demo.impl;

import com.alibaba.dubbo.demo.DemoService;
import controller.MainController;

import java.util.Map;

public class DemoServiceImpl implements DemoService {

    @Override
    public String login(Map<String, String> map) {
        MainController m = new MainController();
        return m.login(map);
    }
}
