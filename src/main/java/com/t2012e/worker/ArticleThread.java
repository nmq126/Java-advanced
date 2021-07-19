package com.t2012e.worker;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Date;

public class ArticleThread extends Thread {
    private String url;
    private Article article;

    public ArticleThread(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Article getArticle() {
        return article;
    }

    @Override
    public void run() {
        // parse thông tin vào các trường.
        System.out.printf("Crawling data from url %s\n", url);
        crawlData();
    }

    private void crawlData() {
        try {
            article = new Article();
            article.setUrl(url);
            Document document = Jsoup.connect(url).get();
            Element titleNode = document.selectFirst("h1.title-detail");
            if (titleNode != null) {
                String title = titleNode.text();
                article.setTitle(title);
            }
            Element contentElement = document.selectFirst(".fck_detail p");
            if (contentElement != null) {
                String content = contentElement.text();
                article.setContent(content);
            }
            Element descriptionElement = document.selectFirst("p.description");
            if (descriptionElement != null) {
                String description = descriptionElement.text();
                article.setDescription(description);
            }
            Element thumbnailElement = document.selectFirst("div.fig-picture picture img");
            if (thumbnailElement != null) {
                String thumbnail = thumbnailElement.attr("data-src");
                article.setThumbnail(thumbnail);
            } else {
                article.setThumbnail("http://default.jpg");
            }
            article.setStatus(1);
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.err.printf("Error %s", ioException.getMessage());
        }
    }
}
