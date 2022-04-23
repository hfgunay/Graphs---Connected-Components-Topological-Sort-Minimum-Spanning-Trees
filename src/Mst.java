import java.util.*;

public class Mst {

    static Map<Planet, Planet> parent = new HashMap<>();
    private final List<Planet> planets;


    public Mst(List<Planet> planets) {
        this.planets = planets;

        for(Planet planet: planets){
            parent.put(planet,planet);
        }
    }

    static class Edge {
        Planet src, dest;
        double weight;

        public Edge(Planet src, Planet dest, double weight){
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }
    }

    Planet find(Planet k){
        if(parent.get(k) == k){
            return k;
        }
        return find(parent.get(k));
    }


    public List<HyperChannel> Kruskal(List<Edge> edges){
        List<HyperChannel> mstKruskal = new ArrayList<>();
        edges.sort(Comparator.comparingDouble(e -> e.weight));
        int index =0;
        while (mstKruskal.size() != planets.size()-1){

            Edge next_edge = edges.get(index++);
            Planet x = find(next_edge.src);
            Planet y = find(next_edge.dest);

            if(!x.equals(y)){
                HyperChannel hyperChannel = new HyperChannel(next_edge.src,next_edge.dest,next_edge.weight);
                mstKruskal.add(hyperChannel);
                parent.put(x,y);
            }
        }
        return mstKruskal;

    }

}
