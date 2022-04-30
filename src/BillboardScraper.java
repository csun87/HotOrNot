import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.*;

import java.io.IOException;

public class BillboardScraper {
    private final String URL = "https://www.billboard.com/charts/hot-100/";
    private Document doc;

    public BillboardScraper() {
        try {
            this.doc = Jsoup.connect(this.URL).get();
        } catch (IOException e) {
            System.out.println("Could not connect to the Billboard Hot 100 base URL!");
        }
    }

    public List<List<String>> getRanking() {
        List<List<String>> ans = new ArrayList<>();
        Elements songs = this.doc.select(".o-chart-results-list-row-container");
        for (Element song : songs) {
            List<String> pair = new ArrayList<>(2);
            Element title = song.getElementById("title-of-a-story");
            if (title == null) {
                continue;
            }
            Element artist = title.nextElementSibling();
            if (artist == null) {
                continue;
            }
            pair.add(title.text());
            pair.add(artist.text());
            ans.add(pair);
        }
        return ans;
    }
}
