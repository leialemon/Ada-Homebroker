package tech.ada.jjh.homebroker.model;

import java.math.BigDecimal;
//Retorna o valor já acrescido da taxa; Trocar para retornar apenas o valor da taxa e deixar Order fazer a soma?
public class FeeFixedCalculationRule implements FeeCalculationRule{
    @Override
    public BigDecimal calculate(BigDecimal orderPrice, Double feeAmount) {
        return orderPrice.add(BigDecimal.valueOf(feeAmount));
    }
}