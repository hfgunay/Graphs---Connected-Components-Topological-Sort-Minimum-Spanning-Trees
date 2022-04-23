import java.util.*;

public class SubspaceCommunicationNetwork implements Constants{

    private List<SolarSystem> solarSystems;

    /**
     * Perform initializations regarding your implementation if necessary
     * @param solarSystems a list of SolarSystem objects
     */
    public SubspaceCommunicationNetwork(List<SolarSystem> solarSystems) {
        // TODO: YOUR CODE HERE
        this.solarSystems = solarSystems;
    }

    /**
     * Using the solar systems of the network, generates a list of HyperChannel objects that constitute the minimum cost communication network.
     * @return A list HyperChannel objects that constitute the minimum cost communication network.
     */
    public List<HyperChannel> getMinimumCostCommunicationNetwork() {
        // TODO: YOUR CODE HERE
        List<Planet> networkPlanets = new ArrayList<>();
        List<Mst.Edge> edgesForParameter = new ArrayList<>();
        for(SolarSystem solarSystem: solarSystems){
            Planet planet = solarSystem.getPlanets().stream().max(Comparator.comparing(Planet::getTechnologyLevel)).get();
            networkPlanets.add(planet);
        }
        Mst mst = new Mst(networkPlanets);
        for(Planet planet: networkPlanets){
            for (Planet planet1: networkPlanets){
                if(planet != planet1){
                    Double planetTechLevel = (double) planet.getTechnologyLevel();
                    Double planet1TechLevel = (double) planet1.getTechnologyLevel();
                    Double avg = (planetTechLevel + planet1TechLevel) / 2;
                    Double weight = SUBSPACE_COMMUNICATION_CONSTANT / avg;
                    Mst.Edge edge = new Mst.Edge(planet,planet1,weight);
                    edgesForParameter.add(edge);
                }

            }
        }


        return mst.Kruskal(edgesForParameter);
    }

    public void printMinimumCostCommunicationNetwork(List<HyperChannel> network) {
        double sum = 0;
        for (HyperChannel channel : network) {
            Planet[] planets = {channel.getFrom(), channel.getTo()};
            Arrays.sort(planets);
            System.out.printf(Locale.ENGLISH,"Hyperchannel between %s - %s with cost %f", planets[0], planets[1], channel.getWeight());
            System.out.println();
            sum += channel.getWeight();
        }
        System.out.printf(Locale.ENGLISH,"The total cost of the subspace communication network is %f.", sum);
        System.out.println();
    }
}
