import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class BillboardScraper {
    private String URL = "https://www.billboard.com/charts/hot-100/";
    private Document doc;

    public BillboardScraper() {
        try {
            this.doc = Jsoup.connect(this.URL).get();
        } catch (IOException e) {
            System.out.println("Could not connect to the Billboard Hot 100 base URL!");
        }
    }
}
