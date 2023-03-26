package entities.variousTariff;

import entities.Call;
import entities.Tariff;
import java.math.BigDecimal;
import java.util.List;

public class MinuteByMinuteTariff extends Tariff {
    private BigDecimal cost;
    private int limit;

    public MinuteByMinuteTariff(String name, BigDecimal cost, int limit, String index) {
        super(name,index);
        this.cost = cost;
        this.limit = limit;
    }

    @Override
    public boolean checkLimit(double totalTime) {
        if(limit < totalTime){
            return true;
        }
        return false;
    }

    @Override
    public BigDecimal countCost(List<Call> calls) {
        BigDecimal totalCost = BigDecimal.ZERO;
        for(Call call : calls){
            double duration = Math.ceil(call.getLength().getSeconds()/60 + 0.5);
            call.setCost(BigDecimal.valueOf(duration).multiply(cost));
            totalCost = totalCost.add(call.getCost());
        }
        return totalCost;
    }

}
