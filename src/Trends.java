import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Trends {
    private String baseURL = "https://trends.google.com/trends/explore?cat=35&date=now%207-d&geo=US&q=";
    private Document doc;
    private List<List<String>> songs;
    private List<List<Integer>> comparisons;

    public Trends(List<List<String>> songs) {
        this.songs = songs;
        this.comparisons = new ArrayList<>(this.songs.size());
        for (int i = 0; i < this.songs.size(); ++i) {
            List<Integer> x = new ArrayList<>(this.songs.size());
            for (int j = 0; j < this.songs.size(); ++j) {
                x.add(0);
            }
            this.comparisons.add(x);
        }
        compare();
    }

    private void compare() {
        for (int i = 0; i < this.songs.size(); ++i) {
            String firstTitle = this.songs.get(i).get(0);
            String firstArtist = this.songs.get(i).get(1);
            firstTitle = firstTitle.replaceAll("[^a-zA-Z0-9 ]", "").toLowerCase();
            firstArtist = firstArtist.replaceAll("[^a-zA-Z0-9 ]", "").toLowerCase();
            String[] titleWords1 = firstTitle.split(" ");
            String[] artistWords1 = firstArtist.split(" ");
            for (int j = i + 1; j < this.songs.size(); ++j) {
                StringBuilder url = new StringBuilder(this.baseURL);
                String secondTitle = this.songs.get(j).get(0);
                String secondArtist = this.songs.get(j).get(1);
                secondTitle = secondTitle.replaceAll("[^a-zA-Z0-9 ]", "").toLowerCase();
                secondArtist = secondArtist.replaceAll("[^a-zA-Z0-9 ]", "").toLowerCase();
                String[] titleWords2 = secondTitle.split(" ");
                String[] artistWords2 = secondArtist.split(" ");
                for (String word : titleWords1) {
                    url.append(word).append("%20");
                }
                for (int k = 0; i < artistWords1.length - 1; ++i) {
                    url.append(artistWords1[k]).append("%20");
                }
                url.append(artistWords1[artistWords1.length - 1]).append(",");
                for (String word : titleWords2) {
                    url.append(word).append("%20");
                }
                for (int k = 0; i < artistWords2.length - 1; ++i) {
                    url.append(artistWords2[k]).append("%20");
                }
                url.append(artistWords2[artistWords2.length - 1]);
                System.out.println(url.toString());
                try {
                    this.doc = Jsoup.connect(url.toString())
                            .userAgent("Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 " +
                                    "(KHTML, like Gecko) Chrome/32.0.1667.0 Safari/537.36").get();
                } catch (IOException e) {
                    System.out.println("Could not connect to the URL!");
                    continue;
                }
                Elements elements = this.doc.select("/html/body/div[2]/div[2]/div/md-content/div/div/div[1]/trends-widget/ng-include/widget/div/div/ng-include/div/ng-include/div/bar-chart/div/div/div[1]/div/div/table/tbody/tr");
                for (Element x : elements) {
                    System.out.println(x.text());
                }
            }
        }
    }
}
// /html/body/div[2]/div[2]/div/md-content/div/div/div[1]/trends-widget/ng-include/widget/div/div/ng-include/div/ng-include/div/bar-chart/div/div/div[1]/div/div/table/tbody/tr