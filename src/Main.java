import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the Billboard artist graph!");
        System.out.println("Please wait while we build the graph... " +
                "(this may take a little while)");
        // Code below needs to be run only if we have not yet generated .txt files to cache results
//        BillboardScraper scraper = new BillboardScraper();
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
        System.out.println("We are done building the graph!");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Please enter two artists you would like to see the link between");
            System.out.println("Artists should be given in the form \"Artist 1,Artist 2\"");
            System.out.println("Type in \"EXIT\" to exit the program.");
            String input = scanner.nextLine();
            if (input.equals("EXIT")) {
                break;
            }
            String[] split = input.split(",");
            List<String> bfsResult = g.shortestPath(split[0], split[1]);
            if (bfsResult.isEmpty()) {
                System.out.println("There is no link between these two artists on the Billboard!");
            } else {
                System.out.println("There is a path of length " +
                        bfsResult.size() + " between these two artists.");
                for (String x : bfsResult) {
                    System.out.println(x);
                }
            }
        }
    }
}
