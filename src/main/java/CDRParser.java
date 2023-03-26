import entities.Call;
import entities.Tariff;
import entities.User;
import entities.variousTariff.MinuteByMinuteTariff;
import entities.variousTariff.OrdinaryTariff;
import entities.variousTariff.UnlimitedTariff;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Map;
import java.util.Scanner;

public class CDRParser {

    public static Map<String, User> parse(Map<String, User> users) throws IOException, ParseException {
        File fileCdr = new File("src/main/resources/cdr.txt");
        if (!fileCdr.exists()){
            throw new FileNotFoundException("File " + fileCdr.getAbsolutePath() + " does not exist");
        }
        Scanner scanner = new Scanner(fileCdr);
        while (scanner.hasNext()){
            String nextLine = scanner.nextLine();
            String[] cdrFromFile = nextLine.split(", ");
            if(!users.containsKey(cdrFromFile[1])){
                User user = new User(cdrFromFile[1], createTariff(cdrFromFile[4]));
                users.put(cdrFromFile[1], user);
                addCall(cdrFromFile, users.get(cdrFromFile[1]));
            }
            else{
                User user = users.get(cdrFromFile[1]);
                addCall(cdrFromFile, user);
            }
        }

        countTotalCost(users);
        return users;

    }

    public static Tariff createTariff(String num){
        Tariff minuteByMinuteTariff = new MinuteByMinuteTariff("Поминутный", BigDecimal.valueOf(1.5), 100, "03");
        Tariff ordinaryTariff = new OrdinaryTariff("Обычный", BigDecimal.valueOf(0.5), 100, "11");
        Tariff unlimitedTariff = new UnlimitedTariff("Безлимит 300", BigDecimal.valueOf(100), 300, "06");
        switch(num){
            case "03":
                return minuteByMinuteTariff;
            case "06":
                return unlimitedTariff;
            case "11":
                return ordinaryTariff;
        }

        return null;
    }

    public static User addCall(String[] cdrFile, User user) throws ParseException {
        Call call = new Call(cdrFile[2], cdrFile[3], cdrFile[0]);
        user.getCalls().add(call);
        return user;
    }

    public static void countTotalCost(Map<String, User> users){
        for(Map.Entry<String, User> user: users.entrySet()){
            User userFromMap = user.getValue();
            userFromMap.countTotalCost();
        }
    }
}
