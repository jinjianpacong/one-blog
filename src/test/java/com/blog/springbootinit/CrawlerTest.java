package com.blog.springbootinit;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.blog.springbootinit.model.entity.Picture;
import com.blog.springbootinit.model.entity.Post;
import com.blog.springbootinit.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Slf4j
public class CrawlerTest {
    @Resource
    PostService postService;
    @Test
    public void testGetBingPicture() throws IOException {
        int current=1;
        String url="https://cn.bing.com/images/search?q=%E5%8E%9F%E7%A5%9E&first="+current;
        Document doc = Jsoup.connect(url).get();
        log.info(doc.title());
        Elements newsHeadlines = doc.select(".iuscp.isv");
        List<Picture> list=new ArrayList<Picture>();
        for (Element element : newsHeadlines) {
            Elements img = element.select(".iusc");
            Elements inflnk = element.select(".inflnk");
            String title = inflnk.attr("aria-label");
            String src = img.attr("m");
            JSONObject obj = JSONUtil.parseObj(src);
            String imgUrl = obj.getStr("murl");
            String source = obj.getStr("purl");
            log.info("{}:{}:{}",title,imgUrl,source);
            Picture picture = new Picture();
            picture.setTitle(title);
            picture.setUrl(imgUrl);
            list.add(picture);
//            log.info("%s\n\t%s", element.attr("title"), element.absUrl("href"));
        }
    }
    @Test
    public void testGetPassage() throws InterruptedException {
//        String res = getPost();
//        Post post = new Post();
//        post.setTags("网易云");
//        post.setUserId(1L);
//        post.setContent(res);
//        post.setFavourNum(null);
//        post.setTitle("测试数据"+ RandomUtil.randomNumbers(6));
//        post.setThumbNum(RandomUtil.randomInt(100,1000));
//        postService.save(post);
        for(int i=0;i<100;i++){
            String res = getPost();
            Post post = new Post();
            post.setTags("网易云");
            post.setUserId(1L);
            post.setContent(res);
            post.setFavourNum(null);
            post.setTitle("测试数据"+ RandomUtil.randomNumbers(6));
            post.setThumbNum(RandomUtil.randomInt(100,1000));
            postService.save(post);
            Thread.sleep(200);
        }

    }

    @NotNull
    private static String getPost() {
        String json = "{\n" +
                "    \"current\": 1,\n" +
                "    \"pageSize\": 8,\n" +
                "    \"sortField\": \"createTime\",\n" +
                "    \"sortOrder\": \"descend\",\n" +
                "    \"category\": \"文章\",\n" +
                "    \"reviewStatus\": 1\n" +
                "}";
//        String cookie="SESSION=MzYwNDYzM2YtMzllYi00MGFjLTk2MDItMTViMzc1NDc1Mzc4; __51vcke__JtwgXxXhywMwFmhF=9b037673-75c1-5e4e-ac36-453e2b5cc5c4; __51vuft__JtwgXxXhywMwFmhF=1683684939035; __51huid__JyijP8zIhGUD9hWn=53ecf590-863b-5595-bb33-2f3c6e114cc4; __vtins__JtwgXxXhywMwFmhF=%7B%22sid%22%3A%20%22fa4bae9e-7b03-599e-828e-fe228bdfff55%22%2C%20%22vd%22%3A%201%2C%20%22stt%22%3A%200%2C%20%22dr%22%3A%200%2C%20%22expires%22%3A%201684048999904%2C%20%22ct%22%3A%201684047199904%7D; __51uvsct__JtwgXxXhywMwFmhF=12";
//        String userAgent="Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Safari/537.36 Edg/113.0.1774.42";
        String result2 = HttpRequest.post("https://v.api.aa1.cn/api/api-wenan-wangyiyunreping/index.php?aa1=text")
                .body(json)
                .execute().body();
        result2 = result2.replaceAll("<.?p>", "");
        return result2;
    }
}
