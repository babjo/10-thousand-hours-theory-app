package com.three.a10_thousand_hours_theory_app.model.dto;

import com.three.a10_thousand_hours_theory_app.Const;
import com.three.a10_thousand_hours_theory_app.model.domain.GoalEntity;
import com.three.a10_thousand_hours_theory_app.model.domain.TaskRuleEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by LCH on 2016. 9. 11..
 */
@Getter
@Setter
public class CreateGoalRequestDTO {

    private GoalEntity goalEntity;
    private int goalId = 0;
    private int goalType;
    private Date deadLineDate;
    private int goalHours;
    private String title;
    private String description;
    private List<TaskRuleEntity> taskRuleEntities;

    public CreateGoalRequestDTO(){
        this.goalType = Const.GOAL_TYPE_DEADLINE;
        this.taskRuleEntities = new ArrayList();
    }
    public CreateGoalRequestDTO(GoalEntity goalEntity) {
        this.goalEntity = goalEntity;
        this.goalType = goalEntity.getType();
        this.deadLineDate = goalEntity.getDeadLineDate();
        this.goalHours = goalEntity.getGoalHours();
        this.goalId = goalEntity.getId();
        this.title = goalEntity.getTitle();
        this.description = goalEntity.getDescription();
        this.taskRuleEntities = goalEntity.getTaskRules();
    }
    public void addTaskRuleEntity(TaskRuleEntity newTaskRule) {
        this.taskRuleEntities.add(newTaskRule);
    }
    public UpdateGoalRequestDTO convert(){
        goalEntity.setTitle(title);
        goalEntity.setDescription(description);
        goalEntity.setDeadLineDate(deadLineDate);
        goalEntity.setType(goalType);
        goalEntity.setGoalHours(goalHours);

        List<Integer> colors = Arrays.asList(Const.LABEL_COLORS);
        Collections.shuffle(colors);

        // 새로이만들어진 Rule들
        for (int i=0; i<taskRuleEntities.size(); i++) {
            TaskRuleEntity t = taskRuleEntities.get(i);
            if(t.getId() == 0) {
                t.setGoal(goalEntity);
                t.setLabelColor(colors.get(i % Const.LABEL_COLORS.length));
                t.setStartDate(new Date());
            }
        }

        return new UpdateGoalRequestDTO(goalEntity);
    }
}
