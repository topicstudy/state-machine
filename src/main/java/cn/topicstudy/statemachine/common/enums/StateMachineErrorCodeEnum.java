package cn.topicstudy.statemachine.common.enums;

import cn.topicstudy.jutil.basic.error.BaseErrorCodeEnum;

public enum StateMachineErrorCodeEnum implements BaseErrorCodeEnum {
    NOT_INIT_STATE_MACHINE("TS-STATE_MACHINE-1-1", "未初始化状态机"),
    STATE_CANT_FLOW("TS-STATE_MACHINE-1-2", "状态无法流转，当前状态：{0}，目标状态：{1}"),
    ;

    private String errorCode;
    private String errorMsg;

    StateMachineErrorCodeEnum(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    @Override
    public String getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String getErrorMsg() {
        return this.errorMsg;
    }


}
