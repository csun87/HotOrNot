import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        // Code below needs to be run only if we have not yet generated .txt files to cache results
        BillboardScraper scraper = new BillboardScraper();
//        List<List<String>> billboardRanking = scraper.getRanking();
//        for (int i = 0; i < billboardRanking.size(); ++i) {
//            System.out.println((i + 1) + ": " + billboardRanking.get(i).get(0) + ", by: " +
//                    billboardRanking.get(i).get(1));
//        }
//        Set<String> artists = scraper.getArtists();
//        Map<String, String> songs = scraper.getAllSongs();

        // Code below can be run if we have generated files to cache results
        Set<String> artists = new HashSet<>();
        Map<String, String> songs = new HashMap<>();
        try {
            FileReader fr = new FileReader("songs.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    String[] x = line.split("\\|");
                    songs.put(x[0], x[1]);
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            FileReader fr = new FileReader("artists.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    artists.add(line);
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] artistsList = new String[artists.size()];
        artistsList = artists.toArray(artistsList);
        Graph g = new Graph(songs, artistsList);
        Map<String, List<String[]>> adjList = g.getAdjList();
        for (Map.Entry<String, List<String[]>> e : adjList.entrySet()) {
            System.out.println("==========================");
            System.out.println(e.getKey());
            for (String[] x : e.getValue()) {
                System.out.println(x[0] + " || " + x[1]);
            }
        }
        List<String> bfsResult = g.shortestPath("Migos", "JAY-Z");
        System.out.println(bfsResult.size());
        for (String x : bfsResult) {
            System.out.println(x);
        }
    }
}
