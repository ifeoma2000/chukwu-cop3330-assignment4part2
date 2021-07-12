package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;




    public class ListController implements Initializable {

        // GUI controls defined in FXML and used by the controller's code
        @FXML
        public TextField Type_Date;

        @FXML
        private Button clear;

        @FXML
        private Button remove;

        @FXML
        private Button add_item;

        @FXML
        public TextField Type_Description;

        @FXML
        private Button load;

        @FXML
        private Button save;

        @FXML
        private javafx.scene.control.CheckBox toggle_completed_items;

        @FXML
        private Button edit;
        @FXML
        private ListView ToDo_List;
        final ObservableList<ListItem> listItems = FXCollections.observableArrayList();
        ObservableList<ListItem> incompletedItems = FXCollections.observableArrayList();
        // called by FXMLLoader to initialize the controller
        public void initialize(URL location, ResourceBundle resources) {
            ToDo_List.setItems(listItems);

        }

        @FXML
        //method that clears all the items on the list
        private void clear(){
            listItems.clear();
            incompletedItems.clear();
        }

        @FXML
        //method that edits each item on the list
        public void edit(){
            //allows the user to edit the toggled list
            int selectedItem = ToDo_List.getSelectionModel().getSelectedIndex();
            if (toggle_completed_items.isSelected()) {
                incompletedItems.get(selectedItem).description = Type_Description.getText();
                incompletedItems.get(selectedItem).date = Type_Date.getText();
            }//allows the user to edit the original list
            else {
                listItems.get(selectedItem).description = Type_Description.getText();
                listItems.get(selectedItem).date = Type_Date.getText();
            }
        }

        @FXML
        //method that allows the user to add an item to the list
        public void addAction() {
            listItems.add(new ListItem(Type_Description.getText(), Type_Date.getText(), false));
        }

        @FXML
        //method that alows the user to delete an item from the list
        public void deleteAction() {
            //can remove an item from the toggled list
            int selectedItem = ToDo_List.getSelectionModel().getSelectedIndex();
            if (toggle_completed_items.isSelected()){
                incompletedItems.remove(selectedItem);
            }//can remove an item from the original list
            else{
                listItems.remove(selectedItem);
            }
        }

        @FXML
        //method that allows the user to change the status of complete from its default status to its new status or from false to true
        private void toggleComplete(MouseEvent mouseEvent){//not working correctly
            int selectedItem = ToDo_List.getSelectionModel().getSelectedIndex();
            //if the user left clicks the item twice the status of complete changes from false to true
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                if(mouseEvent.getClickCount() == 2){
                    if (toggle_completed_items.isSelected())
                        incompletedItems.get(selectedItem).complete = !incompletedItems.get(selectedItem).complete;
                    else
                        listItems.get(selectedItem).complete = !listItems.get(selectedItem).complete;
                }
            }


        }

        @FXML
        //method that saves the list
        private void save(){
            //initilaize an array list of array lists of strings
            ArrayList<ArrayList<String>> saveArray = new ArrayList<>();
            //for loop that iterates through the entire array list
            for (int i = 0; i < listItems.size(); i++){
                ArrayList<String> temp = new ArrayList<>();
                temp.add(listItems.get(i).description);
                temp.add(listItems.get(i).date);
                temp.add(String.valueOf(listItems.get(i).complete));
                saveArray.add(temp);
            }
            try {
                //writes the list to a text file
                File saveFile = new File("src/main/resources/ucf/assignments/save.txt");
                FileWriter writer = new FileWriter("src/main/resources/ucf/assignments/save.txt");
                writer.write(saveArray.toString());
                writer.close();
            }catch(IOException e){
                ;
            }
        }

        @FXML
        //method that loads an existing list
        private void load(){
            try {
                //calls the file that was made
                File saveFile = new File("src/main/resources/ucf/assignments/save.txt");
                //reads from the file
                Scanner reader = new Scanner(saveFile);
                String loadRaw = "";
                //while loop that reads the entire file
                while(reader.hasNextLine()){
                    loadRaw += reader.nextLine();
                }
                ArrayList<String[]> loadFile = new ArrayList<>();
                //for loop that reads the entire file starting from the left hand bracket to the right hand bracket
                for (int i = 1; i < loadRaw.length(); i++){
                    if (loadRaw.charAt(i) == '['){
                        String temp = "";
                        i++;
                        while(loadRaw.charAt(i) != ']'){
                            temp += loadRaw.charAt(i);
                            i++;
                        }
                        //uses the .split method to distinguish each list item which is seperated by a comma and a space
                        String[] tArray = temp.split(", ");
                        loadFile.add(tArray);
                    }
                }
                listItems.clear();
                //reads through each item in the file by its description, date, and completeness
                for (int i = 0; i < loadFile.size(); i++){
                    listItems.add(new ListItem(loadFile.get(i)[0], loadFile.get(i)[1], Boolean.parseBoolean(loadFile.get(i)[2])));
                }

            }catch(IOException e){
                ;
            }
        }


        @FXML
        //method used to toggle completed list items
        private void hideCompletedItems() {
            //if statement saying when an item is selected its completed status is now true and it is no longer on the incomplete toggled list
            if (toggle_completed_items.isSelected()) {
                incompletedItems = FXCollections.observableArrayList();
                for (int i = 0; i < listItems.size(); i++) {
                    //if the item is not complete, it stays on the incompleted items list
                    if (!listItems.get(i).complete) {
                        incompletedItems.add(listItems.get(i));
                    }
                }//when toggled, the completed items go away and only the incomplete items are shown
                ToDo_List.setItems(incompletedItems);
            } //makes a list of completed items
            else {
                ObservableList<ListItem> temp = FXCollections.observableArrayList();
                for (int i = 0; i < listItems.size(); i++) {
                    if (listItems.get(i).complete)
                        temp.add(listItems.get(i));
                }//makes a list of incompleted items
                temp.addAll(incompletedItems);
                listItems.clear();
                listItems.addAll(temp);
                ToDo_List.setItems(listItems);
            }




        }





    }

