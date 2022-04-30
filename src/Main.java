import java.util.List;

public class Main {
    public static void main(String[] args) {
        BillboardScraper scraper = new BillboardScraper();
        List<List<String>> ranking = scraper.getRanking();
        int i = 1;
        for (List<String> l : ranking) {
            System.out.println(i + ": " + l.get(0) + ", by: " + l.get(1));
            ++i;
        }
    }
}
