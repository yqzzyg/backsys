package com.neusoft.mid.ec.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Profile("dev")
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {


        List<Parameter> pars = new ArrayList<Parameter>();
        ParameterBuilder idno = new ParameterBuilder();
        idno.name("idno").description("身份证号")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build();

        ParameterBuilder name = new ParameterBuilder();
        name.name("name").description("姓名")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build();

        //根据每个方法名也知道当前方法在设置什么参数
        pars.add(idno.build());
        pars.add(name.build());


        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 自行修改为自己的包路径
                .paths(PathSelectors.any())
                .build().globalOperationParameters(pars);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("豫事办")
                .description("豫事办 API 1.0 操作文档")
                //服务条款网址
                .version("1.0")
                .build();
    }

}
