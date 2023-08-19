package cn.topicstudy.statemachine.common.entity;

import cn.topicstudy.statemachine.common.enums.BaseStateEnum;
import lombok.Data;

import java.util.List;

@Data
public class StateFlowItem {
    private BaseStateEnum currentState;
    private BaseStateEnum nextState;

    // event = andEventList || orEventList
    private List<Event> andEventList;
    private List<Event> orEventList;

    private List<Action> actionList;

    public String getCurrentStateCode() {
        return currentState == null ? null : currentState.getStateCode();
    }

    public String getCurrentStatDesc() {
        return currentState == null ? null : currentState.getStateDesc();
    }

    public String getNextStateCode() {
        return nextState == null ? null : nextState.getStateCode();
    }

    public String getNextStatDesc() {
        return nextState == null ? null : nextState.getStateDesc();
    }
}
