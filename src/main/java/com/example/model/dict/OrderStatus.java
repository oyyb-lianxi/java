package com.example.model.dict;

public enum OrderStatus implements CommonEnum{
    // 基础流程
    PENDING_CONFIRMATION("1","待确认"),
    CONFIRMED("2","已确认"),
    PENDING_PAYMENT("3","待支付"), // 如果支付在确认之后
    PAID("4","已支付"),
    IN_PROGRESS("5","上课中"),
    COMPLETED("6","已完成"),
    CANCELED("7","已取消"),

    // 支付相关
    REFUNDING("8","退款中"),
    REFUNDED("9","已退款"),

    // 评价相关
    TO_BE_REVIEWED("10","待评价"),
    REVIEWED("11","已评价"),

    // 异常状态
    TEACHER_ABSENT("12","教师缺席"),
    STUDENT_ABSENT("13","学生缺席"),

    // 改期相关
    RESCHEDULING("14","改期待处理"),
    RESCHEDULED("15","已改期"),

    ;

    private final String code;
    private final String desc;

    OrderStatus(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
