package com.iurac.crm.settings.web.listener;

import com.iurac.crm.settings.domain.DicValue;
import com.iurac.crm.settings.service.DicService;
import com.iurac.crm.settings.service.impl.DicServiceImpl;
import com.iurac.crm.utils.ServiceFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.*;

/**
 * BelongsProject: workspaceForCrm
 * BelongsPackage: com.iurac.crm.settings.web.listener
 * Author: IuRac
 * CreateTime: 2020-11-12 15:26
 * Description:
 */
public class SystemInitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("加载数据字典......");

        ServletContext application = event.getServletContext();
        DicService dicService = (DicService) ServiceFactory.getService(new DicServiceImpl());

        Map<String, List<DicValue>> map = dicService.getAll();

        Set<String> set = map.keySet();
        for(String code : set){

            List<DicValue> dicValueList = map.get(code);

            application.setAttribute(code+"List",dicValueList);
        }

        System.out.println("数据字典加载完毕。");
        System.out.println("加载阶段转可能性配置文件......");

        Map<String, String> pMap = new HashMap<>();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Stage2Possibility");
        Enumeration<String> enumeration = resourceBundle.getKeys();

        while (enumeration.hasMoreElements()){
            String key = enumeration.nextElement();
            String value = resourceBundle.getString(key);
            pMap.put(key,value);
        }
        application.setAttribute("stage2Possibility",pMap);

        System.out.println("阶段转可能性配置文件加载完毕。");
    }
}
