package com.lank.config;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lank
 * @since 2020/11/4 10:57
 */
public class MyGenerator {

    /**
     * 项目路径
     */
    private static final String PROJECT_PATH = "D:\\ideaworkspace\\demo\\spring\\spring-transaction";
    /**
     * 创建者
     */
    private static final String AUTHOR = "lank";
    /**
     * 数据库连接url
     */
    private static final String DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/test?" +
            "useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=Asia/Shanghai";
    /**
     * 数据库用户名
     */
    private static final String DATABASE_USERNAME = "root";
    /**
     * 数据库密码
     */
    private static final String DATABASE_PASSWORD = "root";
    /**
     * 项目包名
     */
    private static final String PACKAGE_NAME = "com.lank";
    /**
     * 要生成的表名
     */
    private static final String[] TABLE_NAMES = new String[]{"user"};
    /**
     * 表名前缀
     */
    private static final String TABLE_PREFIX = "";

    public static void main(String[] args) {

        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        String projectPath = StringUtils.isBlank(PROJECT_PATH) ? System.getProperty("user.dir") : PROJECT_PATH;
        globalConfig.setOutputDir(projectPath + "/src/main/java");
        globalConfig.setAuthor(AUTHOR);
        globalConfig.setOpen(false);
        globalConfig.setServiceName("%sService");
        globalConfig.setEntityName("%sModel");
        // 使用 LocalDateTime
        globalConfig.setDateType(DateType.TIME_PACK);

        // 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl(DATABASE_URL);
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSourceConfig.setUsername(DATABASE_USERNAME);
        dataSourceConfig.setPassword(DATABASE_PASSWORD);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(PACKAGE_NAME);
        pc.setEntity("model");
        pc.setXml(null);
        pc.setMapper("mapper");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setController("controller");

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setEntityColumnConstant(true);
        strategy.setRestControllerStyle(true);
        // 表名，多个英文逗号分割
        strategy.setInclude(TABLE_NAMES);
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(TABLE_PREFIX);


        // 自定义xml输出配置（输出到/resources/mapper下）
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/"
                        + "/" + tableInfo.getMapperName() + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);

        // 关闭默认 xml 生成，调整生成 至 根目录
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);

        // 代码生成器
        AutoGenerator mybatisPlusGenetor = new AutoGenerator();
        // 加载上面配置
        mybatisPlusGenetor.setGlobalConfig(globalConfig);
        mybatisPlusGenetor.setTemplate(templateConfig);
        mybatisPlusGenetor.setDataSource(dataSourceConfig);
        mybatisPlusGenetor.setPackageInfo(pc);
        mybatisPlusGenetor.setCfg(cfg);
        mybatisPlusGenetor.setStrategy(strategy);
        // 执行
        mybatisPlusGenetor.execute();
    }
}

