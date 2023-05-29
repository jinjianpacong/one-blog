package com.blog.springbootinit.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface DataSource<T> {
    Page<T> doSearch(String searchText, long pageNum, long pageSize);
}
