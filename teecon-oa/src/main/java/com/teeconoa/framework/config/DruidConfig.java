package com.teeconoa.framework.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.teeconoa.framework.aspectj.lang.enums.DataSourceType;
import com.teeconoa.framework.datasource.DynamicDataSource;

@Component
//@ConfigurationProperties(prefix = "myProps")
//@PropertySource("classpath:application.yml")
public class DruidConfig {

//	@Value("${myProps.simpleProp}")
//    private String simpleProp;
//	
//	@Value("${myProps.arrayProps}")
//    private String[] arrayProps;
//	
//	@Value("${myProps.listProp1}")
//    private List<Map<String, String>> listProp1 = new ArrayList<>();
//	
//	@Value("${myProps.listProp2}")
//    private List<String> listProp2 = new ArrayList<>();
//	
//	@Value("${myProps.mapProps}")
//    private Map<String, String> mapProps = new HashMap<>();
//    @Bean
//    @ConfigurationProperties("spring.datasource.druid.master")
//    public DataSource masterDataSource(){
//        return DruidDataSourceBuilder.create().build();
//    }


//    public String getSimpleProp() {
//        return simpleProp;
//    }
//
//    public void setSimpleProp(String simpleProp) {
//        this.simpleProp = simpleProp;
//    }
//
//    public String[] getArrayProps() {
//        return arrayProps;
//    }
//
//    public List<Map<String, String>> getListProp1() {
//        return listProp1;
//    }
//
//    public List<String> getListProp2() {
//        return listProp2;
//    }
//
//    public Map<String, String> getMapProps() {
//        return mapProps;
//    }
	
	   @Bean
	    @ConfigurationProperties("spring.datasource.druid.master")
	    public DataSource masterDataSource()
	    {
	        return DruidDataSourceBuilder.create().build();
	    }

	    @Bean
	    @ConfigurationProperties("spring.datasource.druid.slave")
	    @ConditionalOnProperty(prefix = "spring.datasource.druid.slave", name = "enabled", havingValue = "true")
	    public DataSource slaveDataSource()
	    {
	        return DruidDataSourceBuilder.create().build();
	    }

	    @Bean(name = "dynamicDataSource")
	    @Primary
	    public DynamicDataSource dataSource(DataSource masterDataSource, DataSource slaveDataSource)
	    {
	        Map<Object, Object> targetDataSources = new HashMap<>();
	        targetDataSources.put(DataSourceType.MASTER.name(), masterDataSource);
	        targetDataSources.put(DataSourceType.SLAVE.name(), slaveDataSource);
	        return new DynamicDataSource(masterDataSource, targetDataSources);
	    }
}
