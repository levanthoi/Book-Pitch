
package com.example.book_pitch.Utils;

import com.example.book_pitch.Models.New;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class  GetNews {

    public interface NewsCallback {
        void onSuccess(ArrayList<New> newsItems);
        void onError(String errorMessage);
    }

    public static void getNews(NewsCallback callback) {
        new Thread(() -> {
            ArrayList<New> newsItems = new ArrayList<>();
            try {
                Document doc = Jsoup.connect("https://vnexpress.net/rss/the-thao.rss").get();
                Elements items = doc.select("item");
                for (Element element : items) {
                    String title = element.select("title").text();
                    String link = element.select("link").text();

                    String description = element.select("description").toString();
                    String regex = "\\<[^>]*>";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(description);
                    String cleanedDescription = matcher.replaceAll("");
                    description = cleanedDescription;

                    String enclosure = element.select("enclosure").toString();
                    Document document = Jsoup.parse(enclosure, "", Parser.xmlParser());
                    Element enclosureElement = document.select("enclosure").first();
                    String imageUrl = null;
                    if (enclosureElement != null) {
                        imageUrl = enclosureElement.attr("url");
                    } else {
                        System.out.println("No enclosure element found.");
                    }
                    String pubDate = element.select("pubDate").text();
                    New newsItem = new New(title, link, description, imageUrl, pubDate);
                    newsItems.add(newsItem);
                }
                callback.onSuccess(newsItems);
            } catch (IOException e) {
                e.printStackTrace();
                callback.onError("Failed to fetch news");
            }
        }).start();
    }
}
