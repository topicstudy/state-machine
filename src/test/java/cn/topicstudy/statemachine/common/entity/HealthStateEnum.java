package cn.topicstudy.statemachine.common.entity;

import cn.topicstudy.statemachine.common.enums.BaseStateEnum;


public enum HealthStateEnum implements BaseStateEnum {
    HEALTH("HEALTH", "健康"),
    CATCH_A_COLD("CATCH_A_COLD", "感冒"),
    IN_RECOVERY("IN_RECOVERY", "康复中"),
    ;

    private String stateCode;
    private String stateDesc;

    HealthStateEnum(String stateCode, String stateDesc) {
        this.stateCode = stateCode;
        this.stateDesc = stateDesc;
    }

    @Override
    public String getStateCode() {
        return stateCode;
    }

    @Override
    public String getStateDesc() {
        return stateDesc;
    }
}
