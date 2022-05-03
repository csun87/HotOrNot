import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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

    public Map<String, String> getAllSongs() {
        Map<String, String> ans = new HashMap<>();
        LocalDate date = LocalDate.now();
        LocalDate stopDate = LocalDate.of(2014, 7, 13);
        while (date.isAfter(stopDate)) {
            System.out.println("Looking at Hot 100 Chart for: " + date);
            String url = this.URL + date;
            try {
                this.doc = Jsoup.connect(url).get();
            } catch (IOException e) {
                System.out.println("Could not connect to the Billboard Hot 100 base URL!");
                return ans;
            }
            Elements songs = this.doc.select(".o-chart-results-list-row-container");
            for (Element song : songs) {
                Element title = song.getElementById("title-of-a-story");
                if (title == null) {
                    continue;
                }
                Element artist = title.nextElementSibling();
                if (artist == null) {
                    continue;
                }
                ans.put(title.text(), artist.text());
            }
            date = date.minus(1, ChronoUnit.WEEKS);
        }
        try {
            FileWriter fw = new FileWriter("songs.txt");
            BufferedWriter bw =  new BufferedWriter(fw);
            for (Map.Entry<String, String> song : ans.entrySet()) {
                bw.write(song.getKey() + "|" + song.getValue());
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("Could not write songs to songs.txt!");
        }
        return ans;
    }

    public Set<String> getArtists() {
        Set<String> ans = new HashSet<>();
        LocalDate date = LocalDate.now();
        LocalDate stopDate = LocalDate.of(2014, 7, 13);
        String baseArtistURL = "https://www.billboard.com/charts/artist-100/";
        while (date.isAfter(stopDate)) {
            System.out.println("Looking at Artist 100 for: " + date);
            String url = baseArtistURL + date;
            try {
                this.doc = Jsoup.connect(url).get();
            } catch (IOException e) {
                System.out.println("Could not connect to the Billboard Artist 100 URL!");
                return ans;
            }
            Elements artists = this.doc.select(".o-chart-results-list-row-container");
            for (Element artist : artists) {
                Element x = artist.getElementById("title-of-a-story");
                if (x == null) {
                    continue;
                }
                ans.add(x.text());
            }
            date = date.minus(1, ChronoUnit.WEEKS);
        }
        try {
            FileWriter fw = new FileWriter("artists.txt");
            BufferedWriter bw =  new BufferedWriter(fw);
            for (String artist : ans) {
                bw.write(artist);
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("Could not write songs to artists.txt!");
        }
        return ans;
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
