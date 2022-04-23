import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.List;

public class MissionExploration {

    /**
     * Given a Galaxy object, prints the solar systems within that galaxy.
     * Uses exploreSolarSystems() and printSolarSystems() methods of the Galaxy object.
     *
     * @param galaxy a Galaxy object
     */
    public void printSolarSystems(Galaxy galaxy) {
        // TODO: YOUR CODE HERE
        List<SolarSystem> solarSystems = new ArrayList<>();
        solarSystems = galaxy.exploreSolarSystems();
        galaxy.printSolarSystems(solarSystems);
    }

    /**
     * TODO: Parse the input XML file and return a list of Planet objects.
     *
     * @param filename the input XML file
     * @return a list of Planet objects
     */
    public Galaxy readXML(String filename) {
        List<Planet> planetList = new ArrayList<>();
        List<Element> elementList = new ArrayList<>();
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = dbFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(filename);
            Element documentElement = document.getDocumentElement();
            NodeList planets = documentElement.getElementsByTagName("Planet");
            elementList = getElementList(planets);
            for(Element elementPlanet: elementList){
                NodeList planetItems;
                planetItems = elementPlanet.getElementsByTagName("ID");
                String id = planetItems.item(0).getChildNodes().item(0).getNodeValue();
                planetItems = elementPlanet.getElementsByTagName("TechnologyLevel");
                int  technologyLevel = Integer.parseInt(planetItems.item(0).getChildNodes().item(0).getNodeValue());
                planetItems = elementPlanet.getElementsByTagName("PlanetID");
                List<String> neighbors = new ArrayList<>();
                for(int i=0; i<planetItems.getLength();i++){
                    try{
                        String neighborsID = planetItems.item(i).getChildNodes().item(0).getNodeValue();
                        neighbors.add(neighborsID);
                    }catch (Exception ignored){
                    }
                }
                Planet planet = new Planet(id,technologyLevel,neighbors);
                planetList.add(planet);

            }
        }

        catch (Exception e) {
            e.printStackTrace();
        }
        return new Galaxy(planetList);
    }

    public List<Element> getElementList(NodeList nodelist) {
        List<Element> elementList = new ArrayList<>();
        if (nodelist != null && nodelist.getLength() > 0) {
            for (int i = 0; i < nodelist.getLength(); i++) {
                Node node = nodelist.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    elementList.add(element);
                }
            }
            return elementList;
        }else{
            return null;
        }
    }
}
