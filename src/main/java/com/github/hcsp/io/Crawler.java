package com.github.hcsp.io;

import com.google.gson.Gson;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class Crawler {

    static class GithubPullRequest {

        static class User {
            String login;

            User(String login) {
                this.login = login;
            }
        }

        int number;
        String title;
        User user;

        GithubPullRequest(int number, String title, User user) {
            this.number = number;
            this.title = title;
            this.user = user;
        }
    }

    static CloseableHttpClient httpClient = HttpClients.createDefault();

    // 给定一个仓库名，例如"golang/go"，或者"gradle/gradle"，读取前n个Pull request并保存至csvFile指定的文件中，格式如下：
    // number,author,title
    // 12345,blindpirate,这是一个标题
    // 12345,FrankFang,这是第二个标题
    public static void savePullRequestsToCSV(String repo, int n, File csvFile) throws Exception {
        GithubPullRequest[] res = getPullsByRepo(repo, n);
        String csvString = "number,author,title";

        for (int i = 0; i < res.length; i++) {
            GithubPullRequest node = res[i];
            csvString += "\n";
            csvString += node.number + "," + node.user.login + "," + node.title;
        }

        OutputStream os = new FileOutputStream(csvFile);

        byte[] bs = csvString.getBytes();
        os.write(bs);
        os.close();
    }

    // GET /repos/:owner/:repo/pulls
    // https://api.github.com/repos/facebook/react/pulls
    public static GithubPullRequest[] getPullsByRepo(String repo, int count) throws Exception {
        HttpGet request = new HttpGet("https://api.github.com/repos/" + repo + "/pulls?per_page=" + count);

        request.setHeader("Accept", "application/vnd.github.v3+json");

        try (CloseableHttpResponse response = httpClient.execute(request)) {

            // Get HttpResponse Status
            System.out.println(response.getStatusLine().toString());

            HttpEntity entity = response.getEntity();
            Header headers = entity.getContentType();

            String result = EntityUtils.toString(entity);

            Gson gson = new Gson();

            GithubPullRequest[] parsedRes = gson.fromJson(result, GithubPullRequest[].class);

            return parsedRes;
        }
    }
}
