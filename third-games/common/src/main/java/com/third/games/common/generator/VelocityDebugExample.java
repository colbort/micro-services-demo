package com.third.games.common.generator;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.StringWriter;
import java.util.Properties;

public class VelocityDebugExample {

    public static void main(String[] args) {
        try {
            // 创建 Velocity 引擎
            Properties properties = new Properties();
            properties.setProperty("resource.loader", "class");
            properties.setProperty("class.resource.loader.class", ClasspathResourceLoader.class.getName());

            // 初始化 Velocity 引擎
            VelocityEngine velocityEngine = new VelocityEngine();
            velocityEngine.init(properties);

            // 使用类路径加载模板（确保模板在 resources 目录下）
            String templatePath = "templates/vo.java.vm";  // 在 src/main/resources 目录下
            Template template = velocityEngine.getTemplate(templatePath);

            // 设置 Velocity 上下文
            VelocityContext context = new VelocityContext();
            context.put("entityName", "UserInvitation");  // 模拟传递的 entityName
            context.put("table", new Object());  // 根据需要填充表数据

            // 渲染模板
            StringWriter writer = new StringWriter();
            template.merge(context, writer);

            // 输出渲染结果
            System.out.println(writer.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
