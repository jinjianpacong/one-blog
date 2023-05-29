package com.blog.springbootinit.datasource;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.springbootinit.model.dto.post.PostQueryRequest;
import com.blog.springbootinit.model.entity.Post;
import com.blog.springbootinit.model.vo.PostVO;
import com.blog.springbootinit.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;

/**
 * 帖子服务实现
 *
 
 */
@Service
@Slf4j
public class PostDataSource implements DataSource {

    @Resource
    private PostService postService;

    @Override
    public Page<PostVO> doSearch(String searchText, long pageNum, long pageSize) {
        PostQueryRequest postQueryRequest = new PostQueryRequest();
        postQueryRequest.setSearchText(searchText);
        QueryWrapper<Post> postWrapper = postService.getQueryWrapper(postQueryRequest);
        Page<Post> postPage = new Page<>(pageNum, pageSize);
        postService.page(postPage, postWrapper);
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        PostQueryRequest request = new PostQueryRequest();
        request.setSearchText(searchText);
        request.setPageSize(pageSize);
        request.setCurrent(pageNum);
        Page<Post> page = postService.searchFromEs(request);
        Page<PostVO> voPage = postService.getPostVOPage(page, requestAttributes.getRequest());
//        Page<PostVO> postVOPage = postService.getPostVOPage(postPage, requestAttributes.getRequest());
        return voPage;
    }
}




