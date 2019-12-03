package com.llb.crawlcontent.content;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.llb.crawlcontent.utils.FileUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;

/**
 * 抓评论
 * @Author llb
 * Date on 2019/12/3
 */
public class BilibiliGrabContent {

    public static void main(String[] args) {
        HttpClient httpClient = new HttpClient();
//        设置HttpClient接收Cookie，用与浏览器一样的策略
//        httpClient.getParams().setCookiePolicy("CookiePolicy.BROWSER_COMPATIBILITY");
        try {
        for (int i = 0; i < 4; i++) {
            Thread.sleep(3000);
            System.out.println("开始爬取第"+ (1+i) +"页评论。");
            getMessage(httpClient, "77224508", i+1, 2);
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 抓数据，浏览器中找到bilibili评论接口为 https://api.bilibili.com/x/v2/reply
     * 几个主要请求参数：
     *          type:评论类型。这里固定值 1
     *          oid:哪个视频
     *          pn:第几页的评论
     *          sort:排序。0:按照时间排序。2：按照热度排序。默认2
     */
    public static void getMessage(HttpClient httpClient, String oid, int pn,
                                  int sort) throws IOException {
        //请求接口地址
        String biliUrl = "https://api.bilibili.com/x/v2/reply?&type=1&oid="+ oid +"&pn="+ pn +"&sort="+ sort;
        PostMethod postMethod = new PostMethod();
        GetMethod getMethod = new GetMethod(biliUrl);
        //如果网站必须要求登录才能查看评论时，这里要设置你登录后在浏览器生成的cookie值
        //我这里抓别人视频评论，bilibili不用登录也可以查看
//        getMethod.setRequestHeader("cookie", COOKIE);
        //设置请求头
        postMethod.setRequestHeader("user-agent", "postMethod.setRequestHeader(\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.70 Safari/537.36");
        httpClient.executeMethod(getMethod);
        //获取到接口返回值
        String text = getMethod.getResponseBodyAsString();
        JSONObject jsonObject = JSONObject.parseObject(text);
        for (int i = 0; i < 20; i++) {
            String name = (String) JSONPath.eval(jsonObject, "$.data.replies["+ i +"].member.uname");
            String sex = (String) JSONPath.eval(jsonObject, "$.data.replies["+ i +"].member.sex");
            String message = (String) JSONPath.eval(jsonObject, "$.data.replies["+ i +"].content.message");
            FileUtils.writeContent("用户："+ name + "，性别："+ sex + "，评论:" + message);
        }
    }
}
