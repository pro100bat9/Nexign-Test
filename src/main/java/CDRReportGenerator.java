import entities.Call;
import entities.User;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class CDRReportGenerator {
    private static final String infoAboutCall =
            "|     %s    | %s | %s | %s |  %.2f\t|\n";
    private static final String stringTotalCostForReport =
            "|                                           Total Cost: |     %.2f rubles\t|\n";
    private static final String firstStringFromTable =
            "| Call Type |   Start Time        |     End Time        | Duration | Cost   |\n";
    private static final int valueForRepeat = 77;

    public static void generateReport(Map<String, User> users) {
        for(Map.Entry<String, User> user: users.entrySet()) {
            User userFromMap = user.getValue();
            String fileName = user.getKey() + ".txt";
            File file = new File("reports/"+ fileName);
            StringBuilder builder = new StringBuilder();
            try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))){
                createHeader(bw, userFromMap);
                createLinesWithCalls(userFromMap, builder);
                createStringWithTotalCost(userFromMap, builder, bw);
            } catch (IOException ex){
                ex.getStackTrace();
            }

        }

    }

    public static void createHeader(BufferedWriter bw, User userFromMap) throws IOException {
        bw.write("Tariff index: " + userFromMap.getTariff().getIndex() + "\n");
        bw.write("-".repeat(valueForRepeat) + "\n");
        bw.write("Report for phone number " + userFromMap.getPhoneNumber() + ":" + "\n");
        bw.write("-".repeat(valueForRepeat) + "\n");
        bw.write(firstStringFromTable);
        bw.write("-".repeat(valueForRepeat) + "\n");
    }

    public static void createLinesWithCalls(User userFromMap, StringBuilder builder){
        DateTimeFormatter formatterForString = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        DateTimeFormatter formatterForLocalDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for(Call call : userFromMap.getCalls()){
            String callType = call.getType();
            String startTime = LocalDateTime.parse(call.getStartTime(), formatterForString).format(formatterForLocalDateTime);
            String endTime = LocalDateTime.parse(call.getEndTime(), formatterForString).format(formatterForLocalDateTime);
            Duration duration = call.getLength();
            double cost;
            if(call.getCost() == null){
                cost = 0.00;
            } else{
                cost = call.getCost().setScale(2).doubleValue();
            }
            String durationString = String.format("%02d:%02d:%02d", duration.toHours(), duration.toMinutes() % 60,
                    duration.toSeconds() % 60);
            builder.append(String.format(infoAboutCall, callType, startTime, endTime, durationString, cost));
        }

    }

    public static void createStringWithTotalCost(User userFromMap, StringBuilder builder, BufferedWriter bw) throws IOException {
        double totalCost;
        if(userFromMap.getTotalCost() == null){
            totalCost = 0.00;
        } else {
            totalCost = userFromMap.getTotalCost().setScale(2).doubleValue();
        }
        builder.append("-".repeat(valueForRepeat) + "\n");
        builder.append(String.format(stringTotalCostForReport, totalCost));
        bw.write(builder.toString());
        bw.write("-".repeat(valueForRepeat) + "\n");
    }
}
