package com.third.games.common.generator;


import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *
 */
public class CodeGenerator {
    /**
     * 快速生成
     **/
    public void fastAutoGenerator(String... tables) {
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://192.168.10.138:3306/stock?serverTimezone=UTC");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123456");

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        mpg.setDataSource(dsc);

        String outputDir = System.getProperty("user.dir") + "/common/src/main";

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(outputDir + "/java");
        gc.setAuthor("baomidou");
        gc.setOpen(false);
        gc.setFileOverride(true);
        mpg.setGlobalConfig(gc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.third.games.common");
        pc.setEntity("entity");
        pc.setMapper("mapper");
        pc.setService("service");
        pc.setController("controller");
        mpg.setPackageInfo(pc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setInclude(tables); // 表名
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityTableFieldAnnotationEnable(true);
        strategy.setEntityLombokModel(true);
        mpg.setStrategy(strategy);

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return outputDir + "/resources/mapper/" + tableInfo.getEntityName() + "Mapper.xml";
            }
        });

        // 生成 VO 类
        focList.add(new FileOutConfig("/templates/vo.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return outputDir + "/java/com/third/games/common/vo/" + tableInfo.getEntityName() + "VO.java";
            }
        });

        // 生成 BO 类
        focList.add(new FileOutConfig("/templates/bo.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return outputDir + "/java/com/third/games/common/bo/" + tableInfo.getEntityName() + "BO.java";
            }
        });

        TemplateConfig template = new TemplateConfig();
        template.setXml(null);
        template.setController(null);
        template.setServiceImpl(null);

        mpg.setTemplate(template);
        mpg.setCfg(new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("table", mpg.getConfig().getTableInfoList().get(0));
                this.setMap(map);
            }
        }.setFileOutConfigList(focList));

        // 执行生成
        mpg.execute();
    }

    public static void main(String[] args) {
        CodeGenerator daoCodeAutoGenerator = new CodeGenerator();
        daoCodeAutoGenerator.fastAutoGenerator("user_follow_order");
    }
}
