package com.example.advtotal1_0.listener;

import com.example.advtotal1_0.util.StaticData;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class StaticDataListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // 获取 ServletContext 对象
        ServletContext context = sce.getServletContext();

        context.setAttribute("NEWS_SITE", StaticData.NEWS_SITE);
        context.setAttribute("BOOK_SITE", StaticData.BOOK_SITE);
        context.setAttribute("ROOT_SITE", StaticData.ROOT_SITE);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // 应用程序关闭时的清理操作
        ServletContext context = sce.getServletContext();

        context.removeAttribute("NEWS_SITE");
        context.removeAttribute("BOOK_SITE");
        context.removeAttribute("ROOT_SITE");

    }
}
