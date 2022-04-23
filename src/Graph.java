import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {

    private final List<Planet> planets;
    Map<String, ArrayList<String>> adjacentMap = new HashMap<>();



    Graph(List<Planet> planets){
        this.planets = planets;
        adjacentMap = new HashMap<>();

        for(Planet planet: planets){
            adjacentMap.put(planet.getId(), new ArrayList<String>());
        }
    }

    void addEdge(String source, String destination){
        adjacentMap.get(source).add(destination);
        adjacentMap.get(destination).add(source);
    }

    void depthFirstSearch(String planetID,Map<String, Boolean> visited, SolarSystem solarSystem){
        visited.put(planetID,true);
        for(Planet planet: planets){
            if(planetID.equals(planet.getId())){
                solarSystem.addPlanet(planet);
            }
        }
        for(String i: adjacentMap.get(planetID)){
            if(!visited.get(i)){
                depthFirstSearch(i, visited, solarSystem);

            }
        }

    }

    public List<SolarSystem> connectedComponents(){
        Map<String, Boolean> visited = new HashMap<>();
        List<SolarSystem> solarSystems = new ArrayList<>();
        for(Planet planet: planets){
            visited.put(planet.getId(),false);
        }
        for(Planet planet: planets){
            if(!visited.get(planet.getId())){
                SolarSystem solarSystem = new SolarSystem();
                depthFirstSearch(planet.getId(),visited, solarSystem);
                solarSystems.add(solarSystem);
            }
        }
        return solarSystems;
    }


}
