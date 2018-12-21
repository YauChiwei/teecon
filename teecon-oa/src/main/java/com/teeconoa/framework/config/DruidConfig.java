package com.teeconoa.framework.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "myProps")
//@PropertySource("classpath:application.yml")
public class DruidConfig {

	@Value("${myProps.simpleProp}")
    private String simpleProp;
	
	@Value("${myProps.arrayProps}")
    private String[] arrayProps;
	
//	@Value("${myProps.listProp1}")
    private List<Map<String, String>> listProp1 = new ArrayList<>();
	
//	@Value("${myProps.listProp2}")
    private List<String> listProp2 = new ArrayList<>();
	
	@Value("${myProps.mapProps}")
    private Map<String, String> mapProps = new HashMap<>();
//    @Bean
//    @ConfigurationProperties("spring.datasource.druid.master")
//    public DataSource masterDataSource(){
//        return DruidDataSourceBuilder.create().build();
//    }


    public String getSimpleProp() {
        return simpleProp;
    }

    public void setSimpleProp(String simpleProp) {
        this.simpleProp = simpleProp;
    }

    public String[] getArrayProps() {
        return arrayProps;
    }

    public List<Map<String, String>> getListProp1() {
        return listProp1;
    }

    public List<String> getListProp2() {
        return listProp2;
    }

    public Map<String, String> getMapProps() {
        return mapProps;
    }
}
