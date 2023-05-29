package com.blog.springbootinit.datasource;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.springbootinit.common.ErrorCode;
import com.blog.springbootinit.exception.BusinessException;
import com.blog.springbootinit.model.entity.Picture;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PictureDataSource implements DataSource {
    @Override
    public Page<Picture> doSearch(String searchText, long pageNum, long pageSize) {
        long current = (pageNum - 1) * pageSize;
        if (StrUtil.isBlank(searchText)) {
            searchText = "天空";
        }
        String url = String.format("https://cn.bing.com/images/search?q=%s&first=%s", searchText, current);
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        Elements newsHeadlines = doc.select(".iuscp.isv");
        List<Picture> list = new ArrayList<Picture>();
        for (Element element : newsHeadlines) {
            Elements img = element.select(".iusc");
            Elements inflnk = element.select(".inflnk");
            String title = inflnk.attr("aria-label");
            String src = img.attr("m");
            JSONObject obj = JSONUtil.parseObj(src);
            String imgUrl = obj.getStr("murl");
            String source = obj.getStr("purl");
            Picture picture = new Picture();
            picture.setTitle(title);
            picture.setUrl(imgUrl);
            picture.setSource(source);
            list.add(picture);
            if (list.size() >= pageSize) {
                break;
            }
        }
        Page<Picture> page = new Page<>(pageNum, pageSize);
        page.setRecords(list);
        return page;
    }

}
