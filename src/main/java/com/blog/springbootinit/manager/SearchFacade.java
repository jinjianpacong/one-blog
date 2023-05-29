package com.blog.springbootinit.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.springbootinit.common.ErrorCode;
import com.blog.springbootinit.datasource.*;
import com.blog.springbootinit.exception.BusinessException;
import com.blog.springbootinit.exception.ThrowUtils;
import com.blog.springbootinit.model.dto.search.SearchRequest;
import com.blog.springbootinit.model.entity.Picture;
import com.blog.springbootinit.model.enums.SearchEnum;
import com.blog.springbootinit.model.vo.PostVO;
import com.blog.springbootinit.model.vo.SearchVO;
import com.blog.springbootinit.model.vo.UserVO;
import com.blog.springbootinit.datasource.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class SearchFacade {
    @Resource
    DataSourceRegistry dataSourceRegistry;
    @Resource
    private PostDataSource postDataSource;

    @Resource
    private UserDataSource userDataSource;

    @Resource
    private PictureDataSource pictureDataSource;


    public SearchVO doSearch(@RequestBody SearchRequest searchRequest,
                             HttpServletRequest request) {
        long current = searchRequest.getCurrent();
        long size = searchRequest.getPageSize();
        String searchText = searchRequest.getSearchText();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        String type = searchRequest.getType();
        SearchEnum searchEnum = SearchEnum.getEnumByValue(type);
        if (searchEnum == null) {
            CompletableFuture<List<Picture>> pictureTask = CompletableFuture.supplyAsync(() -> {
                Page<Picture> picturePage = pictureDataSource.doSearch(searchText, current, size);
                return picturePage.getRecords();
            });
            CompletableFuture<List<UserVO>> userTask = CompletableFuture.supplyAsync(() -> {
                Page<UserVO> userVOPage = userDataSource.doSearch(searchText, current, size);
                return userVOPage.getRecords();
            });
            CompletableFuture<List<PostVO>> postTask = CompletableFuture.supplyAsync(() -> {
                Page<PostVO> postVOPage = postDataSource.doSearch(searchText, current, size);
                return postVOPage.getRecords();
            });
            CompletableFuture.allOf(postTask, userTask, pictureTask).join();
            SearchVO searchVO = null;
            try {
                searchVO = new SearchVO();
                searchVO.setPictureList(pictureTask.get());
                searchVO.setPostList(postTask.get());
                searchVO.setUserList(userTask.get());
            } catch (Exception e) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "查询异常");
            }

            return searchVO;
        } else {
            SearchVO searchVO = new SearchVO();
            DataSource<?> dataSource = dataSourceRegistry.getDataSourceByType(type);
            Page<?> page = dataSource.doSearch(searchText, current, size);
            searchVO.setDataList(page.getRecords());
            return searchVO;
        }
    }
}
