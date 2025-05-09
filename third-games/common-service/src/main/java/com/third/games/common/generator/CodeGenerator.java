package com.third.games.common.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class CodeGenerator {
    public static class TimerVelocityTemplateEngine extends AbstractTemplateEngine {
        private VelocityEngine velocityEngine;

        @Override
        public TimerVelocityTemplateEngine init(ConfigBuilder configBuilder) {
            if (null == this.velocityEngine) {
                Properties p = new Properties();
                p.setProperty("resource.loader.file.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
                p.setProperty("resource.loader.file.path", "");
                p.setProperty("UTF-8", ConstVal.UTF8);
                p.setProperty("resource.default_encoding", ConstVal.UTF8);
                p.setProperty("resource.loader.file.unicode", "true");
                this.velocityEngine = new VelocityEngine(p);
            }

            return this;
        }


        @Override
        public void writer(Map<String, Object> objectMap, String templatePath, File outputFile) throws Exception {
            Template template = velocityEngine.getTemplate(templatePath, ConstVal.UTF8);
            String entity = objectMap.get("entity").toString();
            String fileName = outputFile.getName();
            if ((entity + "VO.java").equals(fileName)) {
                String filePath = outputFile.getPath().replace(fileName, "") + "vo/" + fileName;
                outputFile = new File(filePath);
            }
            if ((entity + "BO.java").equals(fileName)) {
                String filePath = outputFile.getPath().replace(fileName, "") + "bo/" + fileName;
                outputFile = new File(filePath);
            }
            if ((entity + "DTO.java").equals(fileName)) {
                String filePath = outputFile.getPath().replace(fileName, "") + "dto/" + fileName;
                outputFile = new File(filePath);
            }
            if (!outputFile.getParentFile().exists()) {
                outputFile.getParentFile().mkdirs();
            }
            try (FileOutputStream fos = new FileOutputStream(outputFile);
                 OutputStreamWriter ow = new OutputStreamWriter(fos, ConstVal.UTF8);
                 BufferedWriter writer = new BufferedWriter(ow)) {
                template.merge(new VelocityContext(objectMap), writer);
            }
        }


        @Override
        public String templateFilePath(String filePath) {
            final String dotVm = ".vm";
            return filePath.endsWith(dotVm) ? filePath : filePath + dotVm;
        }
    }

    /**
     * 快速生成
     **/
    public void fastAutoGenerator(String... tables) {
        String outputDir = System.getProperty("user.dir") + "/common-service/src/main";
        String mapperXmlPath = outputDir + "/resources/com/third/games/common/mapper";

        FastAutoGenerator generator = FastAutoGenerator.create(
                "jdbc:mysql://192.168.10.138:3306/third_games?serverTimezone=UTC",
                "root",
                "123456"
        );
        generator.globalConfig(builder -> {
            builder.author("baomidou").outputDir(outputDir + "/java");
        });
        generator.packageConfig(builder -> {
            builder.parent("com.third.games.common")
                    .entity("entity")
                    .mapper("mapper")
                    .service("service")
                    .controller("controller")
                    .pathInfo(Collections.singletonMap(OutputFile.xml, mapperXmlPath));
        });
        generator.strategyConfig(builder -> {
            builder.addInclude(tables)
                    .addTablePrefix("t_")
                    .entityBuilder()
                    .enableFileOverride()
                    .enableLombok()
                    .enableTableFieldAnnotation()
                    .naming(NamingStrategy.underline_to_camel)
                    .columnNaming(NamingStrategy.underline_to_camel)
                    .controllerBuilder()
                    .enableFileOverride()
                    .enableRestStyle()
                    .mapperBuilder()
                    .enableFileOverride()
                    .serviceBuilder()
                    .enableFileOverride();
        });
        generator.injectionConfig(consumer -> {
            Map<String, String> customFiles = new HashMap<>();
            customFiles.put("VO.java", "/templates/vo.vm");
            customFiles.put("BO.java", "/templates/bo.vm");
            customFiles.put("DTO.java", "/templates/dto.vm");
            consumer.customFile(customFiles);
        });
        generator.templateConfig(builder -> {
//            builder.controller(null).serviceImpl(null);
        });
        generator.templateEngine(new TimerVelocityTemplateEngine());
        generator.execute();
    }

    public static void main(String[] args) {
        CodeGenerator daoCodeAutoGenerator = new CodeGenerator();
        daoCodeAutoGenerator.fastAutoGenerator("t_user", "t_user_private", "t_user_address");
    }
}
