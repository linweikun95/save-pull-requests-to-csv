package com.github.hcsp.io;

import com.csvreader.CsvWriter;
import org.kohsuke.github.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;


public class Crawler {
    public static void main(String[] args) throws IOException {
        savePullRequestsToCSV("golang/go", 2, File.createTempFile("csv", ""));
        System.out.println(File.createTempFile("csv", ""));
    }

    // 给定一个仓库名，例如"golang/go"，或者"gradle/gradle"，读取前n个Pull request并保存至csvFile指定的文件中，格式如下：
    // number,author,title
    // 12345,blindpirate,这是一个标题
    // 12345,FrankFang,这是第二个标题
    public static void savePullRequestsToCSV(String repo, int n, File csvFile) throws IOException {
        GitHub github = GitHub.connectAnonymously();
        GHRepository repository = github.getRepository(repo);
        List<GHPullRequest> pullRequests = repository.getPullRequests(GHIssueState.OPEN);
//        List<String> list=new ArrayList<>();
//        list.add("number,author,title");
//        for (int i=0;i<n;i++){
//            int number=pullRequests.get(i).getNumber();
//            String title=pullRequests.get(i).getTitle();
//            String author=pullRequests.get(i).getUser().getName();
//            list.add(number+","+author+","+title);
//        }
        CsvWriter csvWriter = new CsvWriter(csvFile.getPath(), ',', Charset.forName("UTF-8"));
        // 写表头
        String[] csvHeaders = {"number", "author", "title"};
        csvWriter.writeRecord(csvHeaders);
        // 写内容
        for (int i = 0; i < n; i++) {
            String[] csvContent = {String.valueOf(pullRequests.get(i).getNumber()), pullRequests.get(i).getUser().getName(), pullRequests.get(i).getTitle()};
            csvWriter.writeRecord(csvContent);
        }
        csvWriter.close();
    }

//        priate static String getLine()

}
