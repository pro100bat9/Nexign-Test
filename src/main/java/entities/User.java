package entities;

import java.math.BigDecimal;
import java.util.*;

public class User {
    private String phoneNumber;
    private List<Call> calls;
    private Tariff tariff;
    private BigDecimal totalCost;

    public User(String phoneNumber, Tariff tariff) {
        this.phoneNumber = phoneNumber;
        this.calls = new ArrayList<>();
        this.tariff = tariff;
        totalCost = BigDecimal.ZERO;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Call> getCalls() {
        return calls;
    }

    public void setCalls(List<Call> calls) {
        this.calls = calls;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    public void countTotalCost(){
        calls.sort(Comparator.comparing(Call::getStartTime));
        totalCost =  tariff.countCost(calls);
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }
}
