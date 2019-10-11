# IO实战: 爬取GitHub的Pull request并存储为CSV文件

请完成[`Crawler`](https://github.com/hcsp/save-pull-requests-to-csv/blob/master/src/main/java/com/github/hcsp/io/Crawler.java)中的程序，爬取前N个Pull request并保存成一个CSV文件，格式如下：

```
number,author,title
12345,blindpirate,这是一个标题
12345,FrankFang,这是第二个标题
```
*提示：当 N 大于一定数值时需要考虑分页*
- GitHub pulls API `https://api.github.com/repos/{repo}/pulls?page=1`
- GitHub pulls page `https://github.com/{repo}/pulls?page=1`

在提交Pull Request之前，你应当在本地确保所有代码已经编译通过，并且通过了测试(`mvn clean test`)

-----
注意！我们只允许你修改以下文件，对其他文件的修改会被拒绝：
- [src/main/java/com/github/hcsp/io/Crawler.java](https://github.com/hcsp/save-pull-requests-to-csv/blob/master/src/main/java/com/github/hcsp/io/Crawler.java)
- [pom.xml](https://github.com/hcsp/save-pull-requests-to-csv/blob/master/pom.xml)
-----


完成题目有困难？不妨来看看[写代码啦的相应课程](https://xiedaimala.com/tasks/661cd7ab-7fea-47d0-8e11-555d6fca751d)吧！

回到[写代码啦的题目](https://xiedaimala.com/tasks/661cd7ab-7fea-47d0-8e11-555d6fca751d/quizzes/6c87ef57-7f06-4af2-9112-86dd27ff099d)，继续挑战！
