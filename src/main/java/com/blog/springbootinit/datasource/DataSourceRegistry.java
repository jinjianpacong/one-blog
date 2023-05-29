package com.blog.springbootinit.datasource;

import com.blog.springbootinit.model.enums.SearchEnum;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;

@Component
public class DataSourceRegistry {
    @Resource
    PictureDataSource pictureDataSource;
    @Resource
    UserDataSource userDataSource;
    @Resource
    PostDataSource postDataSource;
    HashMap<String,DataSource<?>> dataSourceHashMap;
    @PostConstruct
    public void doInit(){
        dataSourceHashMap=new HashMap<String,DataSource<?>>(){{
            put(SearchEnum.PICTURE.getValue(),pictureDataSource);
            put(SearchEnum.USER.getValue(),userDataSource);
            put(SearchEnum.POST.getValue(),postDataSource);
        }};
    }
   public DataSource<?> getDataSourceByType(String type){
        return dataSourceHashMap.get(type);
    }


}
