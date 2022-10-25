package com.example.msi.model;

import com.example.msi.common.annotation.BaseMax;
import com.example.msi.common.annotation.BaseNullJudge;
import com.example.msi.common.annotation.BaseRestrict;
import com.example.msi.common.enums.FieldTypeEnum;
import com.example.msi.common.enums.RestrictPatternEnum;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class Order implements Serializable {

    private static final long serialVersionUID = 8173093096361538089L;
    private Long id;

    @BaseNullJudge(fieldName = "产品类型", type = FieldTypeEnum.STRING)
    @BaseMax(fieldName = "产品类型", type = FieldTypeEnum.STRING, value = 50)
    @BaseRestrict(fieldName = "产品类型", type = FieldTypeEnum.STRING,
            restrictPattern = {RestrictPatternEnum.REGULAR_EXPRESSION, RestrictPatternEnum.LENGTH},
            regularExpression = "^[a-zA-Z \\u4e00-\\u9fa5]+$",
            length = 50)
    private String productType;

    @BaseNullJudge(fieldName = "产品编码", type = FieldTypeEnum.OTHER)
    @BaseMax(fieldName = "产品编码", type = FieldTypeEnum.LONG, value = 50)
    @BaseRestrict(fieldName = "产品编码", type = FieldTypeEnum.LONG,
            restrictPattern = RestrictPatternEnum.LENGTH,
            length = 50)
    private Long productNo;

    @BaseNullJudge(fieldName = "价格", type = FieldTypeEnum.OTHER)
    @BaseMax(fieldName = "价格", type = FieldTypeEnum.LONG, value = 10000)
    private Long price;

    private String remark;

    @BaseNullJudge(fieldName = "有效时间", type = FieldTypeEnum.OTHER)
    @BaseMax(fieldName = "有效时间", type = FieldTypeEnum.DATETIME, value = 1)
    private LocalDateTime effectiveTime;

    @BaseNullJudge(fieldName = "有效时间", type = FieldTypeEnum.STRING)
    @BaseRestrict(fieldName = "有效时间", type = FieldTypeEnum.STRING,
            restrictPattern = RestrictPatternEnum.REGULAR_EXPRESSION,
            regularExpression = "^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))\\s+(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d$")
    private String effectiveTimeStr;

    @BaseNullJudge(fieldName = "生产日期", type = FieldTypeEnum.OTHER)
    private LocalDate productionDate;

    @BaseNullJudge(fieldName = "有效时间", type = FieldTypeEnum.OTHER)
    @BaseMax(fieldName = "有效时间", type = FieldTypeEnum.INTEGER, value = 50000)
    private Integer orderNumber;
}
