package com.example.msi.common;

import com.example.msi.common.utils.BaseJudgeUtil;
import com.example.msi.model.Order;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DemoRun {

    public static void main(String[] args) {
        Order order = Order.builder()
                .productType("产品类型")
                .productNo(50L)
                .price(10000L)
                .productionDate(LocalDate.now())
                .effectiveTime(LocalDateTime.now().plusMinutes(-1L))
                .effectiveTimeStr("2022-10-25 15:09:00")
                .orderNumber(50000)
                .remark(null)
                .build();

        System.out.println(BaseJudgeUtil.judgeNull(order));
        BaseJudgeUtil.judgeNullE(order);
        BaseJudgeUtil.judgeMax(order);
        BaseJudgeUtil.judgeRestrict(order);
    }
}
