package ywq.exercise.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ywq.exercise.dbo.JDGoods;
import ywq.exercise.repository.JDGoodsRepository;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 描述:待描述.
 * <p>
 *
 * @author yanwenqiang.
 * @date 2019/6/19
 */
@Component
public class JDGoodsController {

    @Autowired
    private JDGoodsRepository jdGoodsRepository;

    /**
     * 爬取京东商品搜索列表中的商品数据
     *
     * @param keyword 搜索关键词
     * @param page    爬取到的最大页码
     */
    public void crawlerItem(String keyword, Integer page) throws Exception {

        //1 创建HttpClient
        CloseableHttpClient client = HttpClients.createDefault();
        //for循环,根据参数2翻页,重复执行2到5步
        for (int i = 1; i <= page; i += 2) {
            //2 创建get请求对象
            HttpGet request = new HttpGet("https://search.jd.com/Search?keyword=" + URLEncoder.encode(keyword, "UTF-8") + "&enc=utf-8&page=" + i);
            //将自己伪装成Chrome浏览器
            request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
            //3 发送请求,并获得响应
            CloseableHttpResponse response = client.execute(request);
            if (response.getStatusLine().getStatusCode() == 200) {
                //4 Jsoup解析响应数据 => List<JDGoods>
                Document document = Jsoup.parse(EntityUtils.toString(response.getEntity(), "UTF-8"));
                //获得页面中每个商品的li
                Elements lis = document.select("#J_goodsList > ul > li");

                //准备集合,用于存储解析出的每个item
                List<JDGoods> jdGoodsList = new ArrayList<>();

                for (Element li : lis) { //遍历取出每个li(商品)
                    JDGoods jdGoods = new JDGoods();
                    //sku
                    String sku = li.attr("data-sku");
                    if (StringUtils.isNotBlank(sku)) {
                        jdGoods.setSku(Long.parseLong(sku));
                    }
                    //spu
                    String spu = li.attr("data-spu");
                    if (StringUtils.isNotBlank(spu)) {
                        jdGoods.setSpu(Long.parseLong(spu));
                    }
                    //title
                    String title = li.select("div > div.p-name.p-name-type-2 > a > em").text();
                    jdGoods.setTitle(title);
                    //price
                    String price = li.select("div > div.p-price > strong > i").text();

                    if (StringUtils.isNotBlank(price)) {
                        jdGoods.setPrice(Double.parseDouble(price));
                    }

                    //url  div > div.p-img > a
                    String url = li.select("div > div.p-img > a ").attr("href");
                    jdGoods.setUrl(url);
                    //pic
                    String pic = li.select("div > div.p-img > a > img").attr("source-data-lazy-img");
                    jdGoods.setPic(pic);

                    jdGoods.setCreated(new Date());

                    jdGoodsList.add(jdGoods);
                }
                //5 调用dao保存Item
                jdGoodsRepository.saveAll(jdGoodsList);
            }

        }
    }
}
