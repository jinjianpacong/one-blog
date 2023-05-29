package com.blog.springbootinit.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.springbootinit.model.entity.Picture;
import com.blog.springbootinit.common.BaseResponse;
import com.blog.springbootinit.common.ErrorCode;
import com.blog.springbootinit.common.ResultUtils;
import com.blog.springbootinit.exception.ThrowUtils;
import com.blog.springbootinit.model.dto.picture.PictureQueryRequest;
import com.blog.springbootinit.service.PictureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 图片接口
 *
 
 */
@RestController
@RequestMapping("/picture")
@Slf4j
public class PictureController {

    @Resource
    PictureService pictureService;


    @PostMapping("/list/page/vo")
    public BaseResponse<Page<Picture>> listPictureVOByPage(@RequestBody PictureQueryRequest pictureQueryRequest,
                                                           HttpServletRequest request) {
        long current = pictureQueryRequest.getCurrent();
        long size = pictureQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        String searchText = pictureQueryRequest.getSearchText();
        Page<Picture> page = pictureService.searchPicture(searchText, current, size);
        return ResultUtils.success(page);
    }

}
