package com.blog.springbootinit.model.dto.picture;

import com.blog.springbootinit.common.PageRequest;
import lombok.Data;

@Data
public class PictureQueryRequest extends PageRequest {
    String searchText;
}
