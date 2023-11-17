package christmas;

import christmas.policy.input.CheckCondition;
import christmas.service.PlanService;
import christmas.view.InputView;
import christmas.view.OutputView;

public class Application {


    public static void main(String[] args) {
        // TODO: 프로그램 구현
        PlanService planService = new PlanService(new InputView(), new OutputView(), new CheckCondition());
        planService.doPlan();

    }


}
