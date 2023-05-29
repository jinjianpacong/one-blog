package com.blog.springbootinit.esdao;

import com.blog.springbootinit.model.dto.post.PostEsDTO;
import com.blog.springbootinit.model.dto.post.PostQueryRequest;
import com.blog.springbootinit.model.entity.Post;
import com.blog.springbootinit.service.PostService;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;

/**
 * 帖子 ES 操作测试
 *
 
 */
@SpringBootTest
public class PostEsDaoTest {

//    @Resource
    private PostEsDao postEsDao;

    @Resource
    private PostService postService;
    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Test
    void test() {
        PostQueryRequest postQueryRequest = new PostQueryRequest();
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<Post> page =
                postService.searchFromEs(postQueryRequest);
        Optional<PostEsDTO> esDTO = postEsDao.findById(1L);
        System.out.println(page);
    }

    @Test
    void testSelect() {
        System.out.println(postEsDao.count());
        Page<PostEsDTO> PostPage = postEsDao.findAll(
                PageRequest.of(0, 5, Sort.by("createTime")));
        List<PostEsDTO> postList = PostPage.getContent();

        System.out.println(postList);

    }

    @Test
    void testAdd() {
        PostEsDTO postEsDTO = new PostEsDTO();
        postEsDTO.setId(1L);
        postEsDTO.setTitle("test");
        postEsDTO.setContent("test");
        postEsDTO.setTags(Arrays.asList("java", "python"));
        postEsDTO.setUserId(1L);
        postEsDTO.setCreateTime(new Date());
        postEsDTO.setUpdateTime(new Date());
        postEsDTO.setIsDelete(0);
        postEsDao.save(postEsDTO);
        System.out.println(postEsDTO.getId());
    }

    @Test
    void testFindById() {
        Optional<PostEsDTO> postEsDTO = postEsDao.findById(1L);
        System.out.println(postEsDTO);
    }

    @Test
    void testCount() {
        System.out.println(postEsDao.count());
    }

    @Test
    void testFindByCategory() {
        List<PostEsDTO> postEsDaoTestList = postEsDao.findByUserId(1L);
        System.out.println(postEsDaoTestList);
    }
    @Test
    void testFindByTitle() {
        PostEsDTO postEsDTO = elasticsearchRestTemplate.get("1", PostEsDTO.class);
        SearchHits<PostEsDTO> search = elasticsearchRestTemplate.search(Query.findAll(), PostEsDTO.class);
        System.out.println(search);
    }
}
