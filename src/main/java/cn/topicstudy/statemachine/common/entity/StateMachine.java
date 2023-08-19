package cn.topicstudy.statemachine.common.entity;

import cn.topicstudy.jutil.basic.error.CommonAssertUtil;
import cn.topicstudy.statemachine.common.enums.BaseStateEnum;
import cn.topicstudy.statemachine.common.enums.StateMachineErrorCodeEnum;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class StateMachine {
    private List<StateFlowItem> stateFlowItemList;
    private Map<String, List<String>> currentStateToNextStateMap;
    private Map<String, List<String>> nextStateToCurrentStateMap;


    public StateMachine() {
        this.stateFlowItemList = new ArrayList<>();
        this.currentStateToNextStateMap = new HashMap<>();
        this.nextStateToCurrentStateMap = new HashMap<>();
    }

    public StateMachine addStateFlowItem(StateFlowItem flowItem) {
        if (flowItem == null) {
            return this;
        }

        this.stateFlowItemList.add(flowItem);
        String currentStateCode = flowItem.getCurrentStateCode();
        String nextStateCode = flowItem.getNextStateCode();
        List<String> nextStateList = this.currentStateToNextStateMap.get(currentStateCode);
        if (nextStateList == null) {
            nextStateList = new ArrayList<>();
            this.currentStateToNextStateMap.put(currentStateCode, nextStateList);
        }
        nextStateList.add(nextStateCode);

        List<String> currentStateList = this.nextStateToCurrentStateMap.get(nextStateCode);
        if (currentStateList == null) {
            currentStateList = new ArrayList<>();
            this.nextStateToCurrentStateMap.put(nextStateCode, currentStateList);
        }
        currentStateList.add(currentStateCode);

        return this;
    }

    public boolean canStateFlow(String currentStateCode, String nextStateCode) {
        // 无状态时，不校验是否可流转
        if (currentStateCode == null && nextStateCode == null) {
            return true;
        }

        // 注意：currentState==nextState时状态是否可流转，看是否初始化了StateFlowItem

        CommonAssertUtil.throwException(this.currentStateToNextStateMap == null, StateMachineErrorCodeEnum.NOT_INIT_STATE_MACHINE);

        List<String> flowableNextStateList = this.currentStateToNextStateMap.get(currentStateCode);
        return flowableNextStateList != null && flowableNextStateList.contains(nextStateCode);
    }

    public boolean canStateFlow(BaseStateEnum currentStateEnum, BaseStateEnum nextStateEnum) {
        // 无状态时，不校验是否可流转
        if (currentStateEnum == null && nextStateEnum == null) {
            return true;
        }
        return canStateFlow(currentStateEnum.getStateCode(), nextStateEnum.getStateCode());
    }

    /**
     * @throws cn.topicstudy.jutil.basic.error.BizException if can't flow
     */
    public void checkCanStateFlow(String currentStateCode, String nextStateCode) {
        String currentStateInfo = currentStateCode;
        String nextStateInfo = nextStateCode;
        CommonAssertUtil.throwException(!canStateFlow(currentStateCode, nextStateCode),
                StateMachineErrorCodeEnum.STATE_CANT_FLOW,
                currentStateInfo, nextStateInfo);
    }

    public void checkCanStateFlow(BaseStateEnum currentStateEnum, BaseStateEnum nextStateEnum) {
        String currentStateInfo = currentStateEnum == null ? "" : currentStateEnum.getStateDesc() + "(" + currentStateEnum.getStateCode() + ")";
        String nextStateInfo = nextStateEnum == null ? "" : nextStateEnum.getStateDesc() + "(" + nextStateEnum.getStateCode() + ")";
        CommonAssertUtil.throwException(!canStateFlow(currentStateEnum, nextStateEnum),
                StateMachineErrorCodeEnum.STATE_CANT_FLOW,
                currentStateInfo, nextStateInfo);
    }

}
