package ucf.assignments;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ListItemTest {

    @Test
    void testToString() {
        //makes a new listitem object and asserts that to the actual object with the correct list item headings: description, date, and completeness 
            ListItem testItem = new ListItem("Homework", "Today", true);
            String expected = "Description: Homework" +  "     Due Date: Today" +  "     Completed: true";
            String actual = testItem.toString();
            assertEquals(expected, actual);

        }
    }