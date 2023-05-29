package com.blog.springbootinit.model.vo;

import com.blog.springbootinit.model.entity.Picture;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class SearchVO implements Serializable {
    List<Picture> pictureList;
    List<PostVO> postList;
    List<UserVO> userList;
    List<?> dataList;
}
