package com.blog.springbootinit.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.springbootinit.common.BaseResponse;
import com.blog.springbootinit.common.ErrorCode;
import com.blog.springbootinit.common.ResultUtils;
import com.blog.springbootinit.exception.BusinessException;
import com.blog.springbootinit.exception.ThrowUtils;
import com.blog.springbootinit.model.dto.post.PostQueryRequest;
import com.blog.springbootinit.model.dto.user.UserQueryRequest;
import com.blog.springbootinit.model.entity.Picture;
import com.blog.springbootinit.model.entity.Post;
import com.blog.springbootinit.model.entity.User;
import com.blog.springbootinit.model.vo.PostVO;
import com.blog.springbootinit.model.vo.UserVO;
import com.blog.springbootinit.service.PictureService;
import com.blog.springbootinit.service.PostService;
import com.blog.springbootinit.service.UserService;
import com.blog.springbootinit.datasource.DataSource;
import com.blog.springbootinit.datasource.DataSourceRegistry;
import com.blog.springbootinit.model.dto.search.SearchRequest;
import com.blog.springbootinit.model.enums.SearchEnum;
import com.blog.springbootinit.model.vo.SearchVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 图片接口
 *
 
 */
@RestController
@RequestMapping("/search")
@Slf4j
public class SearchController {

    @Resource
    PictureService pictureService;
    @Resource
    UserService userService;
    @Resource
    PostService postService;
    @Resource
    DataSourceRegistry dataSourceRegistry;
    @PostMapping("/all")
    public BaseResponse<SearchVO> listPictureVOByPage(@RequestBody SearchRequest searchRequest,
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
                Page<Picture> picturePage = pictureService.searchPicture(searchText, current, size);
                return picturePage.getRecords();
            });

            CompletableFuture<List<UserVO>> userTask = CompletableFuture.supplyAsync(() -> {
                UserQueryRequest userQueryRequest = new UserQueryRequest();
                userQueryRequest.setUserName(searchText);
                QueryWrapper<User> userWrapper = userService.getQueryWrapper(userQueryRequest);
                Page<User> userPage = new Page<>(current, size);
                userService.page(userPage, userWrapper);
                List<UserVO> userVOList = userService.getUserVO(userPage.getRecords());
                return userVOList;
            });

            CompletableFuture<List<PostVO>> postTask = CompletableFuture.supplyAsync(() -> {
                PostQueryRequest postQueryRequest = new PostQueryRequest();
                postQueryRequest.setSearchText(searchText);
                QueryWrapper<Post> postWrapper = postService.getQueryWrapper(postQueryRequest);
                Page<Post> postPage = new Page<>(current, size);
                postService.page(postPage, postWrapper);
                Page<PostVO> postVOPage = postService.getPostVOPage(postPage, request);
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

            return ResultUtils.success(searchVO);
        } else {
            SearchVO searchVO = new SearchVO();
            DataSource<?> sourceByType = dataSourceRegistry.getDataSourceByType(searchEnum.getValue());
            Page<?> page = sourceByType.doSearch(searchText, current, size);
            searchVO.setDataList(page.getRecords());
            return ResultUtils.success(searchVO);
        }


    }

}
