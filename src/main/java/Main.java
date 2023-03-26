//70371323024
//70582296722
import entities.User;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

public class Main {
    public static void main(String[] args){
        Map<String, User> users = new HashMap<>();
        try{
            users = CDRParser.parse(users);
            System.out.println("All users created");
            CDRReportGenerator.generateReport(users);
            System.out.println("Reports created");
        } catch (IOException ex){
            System.out.println(ex.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
