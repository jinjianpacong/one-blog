package com.blog.springbootinit.esdao;

import com.blog.springbootinit.model.dto.post.PostEsDTO;

import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 帖子 ES 操作
 */
public interface PostEsDao {

    List<PostEsDTO> findByUserId(Long userId);

}
//public interface PostEsDao extends ElasticsearchRepository<PostEsDTO, Long> {
//
//    List<PostEsDTO> findByUserId(Long userId);
//
//}