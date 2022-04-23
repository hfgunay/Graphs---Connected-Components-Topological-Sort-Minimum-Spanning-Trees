import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Project {
    private final String name;
    private final List<Task> tasks;

    public Project(String name, List<Task> tasks) {
        this.name = name;
        this.tasks = tasks;
    }

    /**
     * Schedule all tasks within this project such that they will be completed as early as possible.
     *
     * @return An integer array consisting of the earliest start days for each task.
     */



    public int[] getEarliestSchedule() {
        // TODO: YOUR CODE HERE
        int[] startDays = new int[tasks.size()];
        LinkedHashMap<Task, Integer> tasksDone = new LinkedHashMap<Task, Integer>();
        while(tasksDone.size() < tasks.size()){
            for (int i=0; i < tasks.size(); i++){
                if(tasks.get(i).getDependencies().size() > 0){
                    int startDay = 0;
                    boolean ifContainsAllDependencies = false;
                    for(int d =0; d<tasks.get(i).getDependencies().size(); d++){
                        int finishDayOfFinishedTask = 0;
                        ifContainsAllDependencies = false;
                        int keyTaskID = tasks.get(i).getDependencies().get(d);
                        for (Map.Entry<Task,Integer> entry : tasksDone.entrySet()){
                            if(entry.getKey().getTaskID() == keyTaskID){
                                finishDayOfFinishedTask = entry.getValue() + entry.getKey().getDuration();
                                ifContainsAllDependencies = true;
                            }
                            if (finishDayOfFinishedTask > startDay){
                                startDay = finishDayOfFinishedTask;
                            }
                        }

                    }
                    if(ifContainsAllDependencies){
                        tasksDone.put(tasks.get(i),startDay);
                    }

                }else{
                    tasksDone.put(tasks.get(i), 0);
                }
            }
        }
        for (Map.Entry<Task,Integer> entry : tasksDone.entrySet()){
            for(int i=0; i<tasks.size(); i++){
                if(tasks.get(i).getTaskID()==entry.getKey().getTaskID()){
                    startDays[i] = entry.getValue();
                }
            }
        }


        return startDays;
    }

    /**
     * @return the total duration of the project in days
     */
    public int getProjectDuration() {
        int projectDuration = 0;
        // TODO: YOUR CODE HERE
        int[] startDays = new int[tasks.size()];
        startDays = getEarliestSchedule();
        projectDuration += startDays[tasks.size()-1] + tasks.get(tasks.size()-1).getDuration();
        return projectDuration;
    }

    public static void printlnDash(int limit, char symbol) {
        for (int i = 0; i < limit; i++) System.out.print(symbol);
        System.out.println();
    }

    public void printSchedule(int[] schedule) {
        int limit = 65;
        char symbol = '-';
        printlnDash(limit, symbol);
        System.out.println(String.format("Project name: %s", name));
        printlnDash(limit, symbol);

        // Print header
        System.out.println(String.format("%-10s%-45s%-7s%-5s", "Task ID", "Description", "Start", "End"));
        printlnDash(limit, symbol);
        for (int i = 0; i < schedule.length; i++) {
            Task t = tasks.get(i);
            System.out.println(String.format("%-10d%-45s%-7d%-5d", i, t.getDescription(), schedule[i], schedule[i] + t.getDuration()));
        }
        printlnDash(limit, symbol);
        System.out.println(String.format("Project will be completed in %d days.", tasks.get(schedule.length - 1).getDuration() + schedule[schedule.length - 1]));
        printlnDash(limit, symbol);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;

        int equal = 0;

        for (Task otherTask : ((Project) o).tasks) {
            if (tasks.stream().anyMatch(t -> t.equals(otherTask))) {
                equal++;
            }
        }

        return name.equals(project.name) && equal == tasks.size();
    }

}
