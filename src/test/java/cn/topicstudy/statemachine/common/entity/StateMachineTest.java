package cn.topicstudy.statemachine.common.entity;

import cn.topicstudy.jutil.basic.error.BizException;
import cn.topicstudy.statemachine.common.enums.StateMachineErrorCodeEnum;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StateMachineTest {

    private StateMachine healthStateMachine;

    @Before
    public void setUp() throws Exception {
        /**
         * 健康-（t4）->健康；
         * 健康-（t1）->感冒；
         * 感冒-（t3）->健康；
         * 感冒-（t2）->康复中；
         * 康复中-（t4）->健康
         */
        healthStateMachine = new StateMachine();

        // 健康-（t4）->健康
        StateFlowItem stateFlowItem1 = new StateFlowItem();
        stateFlowItem1.setCurrentState(HealthStateEnum.HEALTH);
        stateFlowItem1.setNextState(HealthStateEnum.HEALTH);

        // 健康-（t1）->感冒
        StateFlowItem stateFlowItem2 = new StateFlowItem();
        stateFlowItem2.setCurrentState(HealthStateEnum.HEALTH);
        stateFlowItem2.setNextState(HealthStateEnum.CATCH_A_COLD);

        // 感冒-（t3）->健康；
        StateFlowItem stateFlowItem3 = new StateFlowItem();
        stateFlowItem3.setCurrentState(HealthStateEnum.CATCH_A_COLD);
        stateFlowItem3.setNextState(HealthStateEnum.HEALTH);

        // 感冒-（t2）->康复中
        StateFlowItem stateFlowItem4 = new StateFlowItem();
        stateFlowItem4.setCurrentState(HealthStateEnum.CATCH_A_COLD);
        stateFlowItem4.setNextState(HealthStateEnum.IN_RECOVERY);

        // 康复中-（t4）->健康
        StateFlowItem stateFlowItem5 = new StateFlowItem();
        stateFlowItem5.setCurrentState(HealthStateEnum.IN_RECOVERY);
        stateFlowItem5.setNextState(HealthStateEnum.HEALTH);

        healthStateMachine.addStateFlowItem(stateFlowItem1)
                .addStateFlowItem(stateFlowItem2)
                .addStateFlowItem(stateFlowItem3)
                .addStateFlowItem(stateFlowItem4)
                .addStateFlowItem(stateFlowItem5);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testNotInitStateMachine() {
        StateMachine stateMachine = new StateMachine();
        try {
            stateMachine.checkCanStateFlow("1", "2");
        } catch (BizException e) {
            System.out.println(e.getErrorCode() + "," + e.getErrorMsg());
            Assert.assertNotNull(e);
            Assert.assertEquals(e.getErrorCode(), StateMachineErrorCodeEnum.NOT_INIT_STATE_MACHINE.getErrorCode());
        }
    }

    @Test
    public void checkCanStateFlow() {
        // 可流转
        healthStateMachine.checkCanStateFlow(HealthStateEnum.HEALTH, HealthStateEnum.CATCH_A_COLD);

        // 不可流转
        try {
            healthStateMachine.checkCanStateFlow(HealthStateEnum.IN_RECOVERY, HealthStateEnum.CATCH_A_COLD);
        } catch (BizException e) {
            System.out.println(e.getErrorCode() + "," + e.getErrorMsg());
            Assert.assertNotNull(e);
            Assert.assertEquals(e.getErrorCode(), StateMachineErrorCodeEnum.STATE_CANT_FLOW.getErrorCode());
        }

//        Assert.assertThrows(BizException.class, () -> healthStateMachine.checkCanStateFlow(HealthStateEnum.IN_RECOVERY, HealthStateEnum.CATCH_A_COLD));
    }

}
