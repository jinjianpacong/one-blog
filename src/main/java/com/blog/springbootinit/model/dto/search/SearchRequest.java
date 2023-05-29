package com.blog.springbootinit.model.dto.search;

import com.blog.springbootinit.common.PageRequest;
import lombok.Data;

@Data
public class SearchRequest extends PageRequest {
    String searchText;
    String type;
}
