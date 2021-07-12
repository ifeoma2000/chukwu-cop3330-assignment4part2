package ucf.assignments;

public class ListItem {
    public String description;
    public String date;
    public boolean complete;

    ListItem(String description, String date, boolean complete)
    {
        //constructor ListItem(parameters) assigns the parameters to the classwide variables description, date, complete
        this.description = description;
        this.date = date;
        this.complete = complete;
    }

    @Override
    //method that formats the list items in the application
    public String toString(){
        return "Description: " + description + "     Due Date: " + date +  "     Completed: " + complete;
    }
}
