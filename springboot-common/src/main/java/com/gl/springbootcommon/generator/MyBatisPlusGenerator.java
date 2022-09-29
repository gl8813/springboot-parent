package com.gl.springbootcommon.generator;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.function.ConverterFileName;

import java.util.Collections;
import java.util.ResourceBundle;

public class MyBatisPlusGenerator {

    public static void main(String[] args) throws InterruptedException {
        /** 用来获取generator-config文件的配置信息 **/
        final ResourceBundle resourceBundle = ResourceBundle.getBundle("generator-config");
        String url = resourceBundle.getString("jdbc.url");
        String username = resourceBundle.getString("jdbc.user");
        String parent = resourceBundle.getString("parent");
        String password = resourceBundle.getString("jdbc.pwd");
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author(resourceBundle.getString("author")) // 设置作者
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(resourceBundle.getString("OutputDir")); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent(parent)
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, resourceBundle.getString("OutputDirXml"))); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("book") // 设置需要生成的表名
                            .addTablePrefix("") // 设置过滤表前缀
                            .entityBuilder()
                            .convertFileName(new ConverterFileName() {
                                @Override
                                public String convert(String entityName) {
                                    return entityName + "DO";
                                }
                            })
                            .enableChainModel()
                            .enableLombok()
                            .enableTableFieldAnnotation()
                            .enableActiveRecord()
                            .columnNaming(NamingStrategy.no_change)
                            .idType(IdType.AUTO)
                            .build()
                            .serviceBuilder().formatServiceFileName("%sService").build()
                            .mapperBuilder().enableBaseResultMap();
                })
                .injectionConfig(builder -> {
                    builder.beforeOutputFile((tableInfo, objectMap) -> {
                        /**
                         * 下方添加其他自定义模板文件，tableInfo可获取当前操作的实体类信息
                         */
                        builder.customFile(Collections.singletonMap("/" + tableInfo.getEntityName() + "OutputDTO.java", "/templates/query.java.vm")).build();
                    });
                })
                .templateConfig(builder -> {
                    /**
                     * 下方添加模板文件
                     */

                })
                .execute();
    }
}
