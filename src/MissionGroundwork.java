import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.List;

public class MissionGroundwork {

    /**
     * Given a list of Project objects, prints the schedule of each of them.
     * Uses getEarliestSchedule() and printSchedule() methods of the current project to print its schedule.
     * @param projectList a list of Project objects
     */
    public void printSchedule(List<Project> projectList) {
        // TODO: YOUR CODE HERE
        for(Project project : projectList){
            project.printSchedule(project.getEarliestSchedule());
        }
    }

    /**
     * TODO: Parse the input XML file and return a list of Project objects
     *
     * @param filename the input XML file
     * @return a list of Project objects
     */
    public List<Project> readXML(String filename) {
        List<Project> projectList = new ArrayList<>();
        List<Element> elementList = new ArrayList<>();
        List<Element> elementTasksList = new ArrayList<>();
        List<Element> elementTask = new ArrayList<>();
        List<Element> elementDependencies = new ArrayList<>();
        // TODO: YOUR CODE HERE
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = dbFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(filename);
            Element documentElement = document.getDocumentElement();
            NodeList projects = documentElement.getElementsByTagName("Project");
            elementList = getElementList(projects);
            for(Element elementProject: elementList){
                List<Task> taskList = new ArrayList<>();
                NodeList nodes;
                nodes = elementProject.getElementsByTagName("Name");
                String name = nodes.item(0).getChildNodes().item(0).getNodeValue();
                nodes = elementProject.getElementsByTagName("Tasks");
                elementTasksList = getElementList(nodes);
                for(Element elementTasks: elementTasksList){
                    NodeList nodesTasks;
                    nodesTasks = elementTasks.getElementsByTagName("Task");
                    elementTask = getElementList(nodesTasks);
                    for(Element elementForTask: elementTask){
                        NodeList nodesForTask;
                        nodesForTask = elementForTask.getElementsByTagName("TaskID");
                        int taskID = Integer.parseInt(nodesForTask.item(0).getChildNodes().item(0).getNodeValue());
                        nodesForTask = elementForTask.getElementsByTagName("Description");
                        String description = nodesForTask.item(0).getChildNodes().item(0).getNodeValue();
                        nodesForTask = elementForTask.getElementsByTagName("Duration");
                        int duration = Integer.parseInt(nodesForTask.item(0).getChildNodes().item(0).getNodeValue());
                        List<Integer> dependsNumber = new ArrayList<>();
                        nodesForTask = elementForTask.getElementsByTagName("Dependencies");
                        elementDependencies = getElementList(nodesForTask);
                        if(elementDependencies != null){
                            for (Element elementDependsNumber: elementDependencies){
                                NodeList nodeDepends;
                                nodeDepends = elementDependsNumber.getElementsByTagName("DependsOnTaskID");
                                for(int i=0; i < nodeDepends.getLength(); i++){
                                    try{
                                        int dependsTaskID = Integer.parseInt(nodeDepends.item(i).getChildNodes().item(0).getNodeValue());
                                        dependsNumber.add(dependsTaskID);
                                    }catch (Exception e){
                                    }
                                }

                            }
                        }
                        Task task = new Task(taskID,description,duration,dependsNumber);
                        taskList.add(task);
                    }

                }
                Project project = new Project(name,taskList);
                projectList.add(project);
            }


        }

        catch (Exception e) {
            e.printStackTrace();
        }


        return projectList;
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
