package com.github.hcsp.io;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class Crawler {
    private static ArrayList pulls = new ArrayList<>(Arrays.asList("number,author,title"));
    // 给定一个仓库名，例如"golang/go"，或者"gradle/gradle"，读取前n个Pull request并保存至csvFile指定的文件中，格式如下：
    // number,author,title
    // 12345,blindpirate,这是一个标题
    // 12345,FrankFang,这是第二个标题

    public static void savePullRequestsToCSV(String repo, int n, File csvFile) throws IOException {
        int page = 0;
        while (pulls.size() < n + 1) {
            page += 1;
            getPullRequestsOfRepo(repo, page, n + 1);
        }
        System.out.println(pulls);
        FileUtils.writeLines(csvFile, pulls);
    }


    private static void getPullRequestsOfRepo(String repo, int page, int n) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet("https://api.github.com/repos/" + repo + "/pulls?page=" + page);
        CloseableHttpResponse response = httpClient.execute(get);
        String data = EntityUtils.toString(response.getEntity());
        JSONArray contents = JSON.parseArray(data);
        for (Object content : contents) {
            if (pulls.size() < n) {
                pulls.add(getPullString((Map) content));
            } else {
                return;
            }
        }
    }

    private static String getPullString(Map pull) {
        String author = (String) ((Map) pull.get("user")).get("login");
        String number = ((Integer) pull.get("number")).toString();
        String title = (String) pull.get("title");
        return number + "," + author + "," + title;
    }

    public static void main(String[] args) throws IOException {
        File tmp = File.createTempFile("csv", "");
        savePullRequestsToCSV("golang/go", 31, tmp);
    }
}
