package entities;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Call {
    private String startTime;
    private String endTime;
    private Duration length;
    private String type;
    private BigDecimal cost;

    public Call(String startTime, String endTime, String type) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.length = countMinutes(startTime, endTime);
        this.type = type;
        this.cost = BigDecimal.ZERO;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setLength(Duration length) {
        this.length = length;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public Duration getLength() {
        return length;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Duration countMinutes(String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime startTime = LocalDateTime.parse(startDate, formatter);
        LocalDateTime endTime = LocalDateTime.parse(endDate, formatter);
        Duration duration = Duration.between(startTime, endTime);
        return duration;
    }
}
