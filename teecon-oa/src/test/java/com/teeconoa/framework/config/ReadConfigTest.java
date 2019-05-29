package com.teeconoa.framework.config;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DruidConfig.class)
public class ReadConfigTest {

    @Autowired
    public DruidConfig druidConfig;

//    @Test
    public void testReadDruidConfig() throws JsonProcessingException {

//        ObjectMapper objectMapper = new ObjectMapper();
//        System.out.println("simpleProp: " + objectMapper.writeValueAsString(druidConfig.getSimpleProp()));
//        System.out.println("arrayProps: " + objectMapper.writeValueAsString(druidConfig.getArrayProps()));
//        System.out.println("listProp1: " + objectMapper.writeValueAsString(druidConfig.getListProp1()));
//        System.out.println("listProp2: " + objectMapper.writeValueAsString(druidConfig.getListProp2()));
//        System.out.println("mapProps: " + objectMapper.writeValueAsString(druidConfig.getMapProps()));
    }

}
