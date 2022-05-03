import java.util.*;

public class Graph {
    private Map<String, List<String[]>> adjList;

    public Graph(Map<String, String> songs, String[] artists) {
        this.adjList = new HashMap<>();
        for (String artist : artists) {
            this.adjList.put(artist, new ArrayList<>());
        }
        for (Map.Entry<String, String> entry : songs.entrySet()) {
            String title = entry.getKey();
            String people = entry.getValue();
            for (int i = 0; i < artists.length; ++i) {
                for (int j = i + 1; j < artists.length; ++j) {
                    if (people.contains(artists[i]) && people.contains(artists[j])
                            && !edgeAlready(artists[i], artists[j])) {
                        this.adjList.get(artists[i]).add(new String[] {artists[j], title});
                        this.adjList.get(artists[j]).add(new String[] {artists[i], title});
                    }
                }
            }
        }
    }

    private boolean edgeAlready(String artist1, String artist2) {
        for (String[] x : this.adjList.get(artist1)) {
            if (x[0].equals(artist2)) {
                return true;
            }
        }
        for (String[] x : this.adjList.get(artist2)) {
            if (x[0].equals(artist1)) {
                return true;
            }
        }
        return false;
    }

    public List<String> shortestPath(String artist1, String artist2) {
        List<String> ans = new ArrayList<>();
        if (!this.adjList.containsKey(artist1) || !this.adjList.containsKey(artist2)) {
            return ans;
        }
        Set<String> discovered = new HashSet<>();
        Queue<List<String>> queue = new ArrayDeque<>();
        queue.offer(Collections.singletonList(artist1));
        while (!queue.isEmpty()) {
            List<String> u = queue.poll();
            String artist = u.get(0);
            if (artist.equals(artist2)) {
                for (int i = 1; i < u.size(); ++i) {
                    ans.add(u.get(i));
                }
                return ans;
            }
            for (String[] v : this.adjList.get(artist)) {
                if (!discovered.contains(v[0])) {
                    discovered.add(v[0]);
                    List<String> toAdd = new ArrayList<>();
                    toAdd.add(v[0]);
                    for (int i = 1; i < u.size(); ++i) {
                        toAdd.add(u.get(i));
                    }
                    toAdd.add(v[1]);
                    queue.offer(toAdd);
                }
            }
        }
        return ans;
    }

    public Map<String, List<String[]>> getAdjList() {
        return this.adjList;
    }
}
