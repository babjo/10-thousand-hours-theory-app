package com.three.a10_thousand_hours_theory_app.presenter;


import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.widget.ArrayAdapter;

import com.three.a10_thousand_hours_theory_app.Const;
import com.three.a10_thousand_hours_theory_app.model.domain.GoalEntity;
import com.three.a10_thousand_hours_theory_app.model.domain.SharedGoal;
import com.three.a10_thousand_hours_theory_app.model.domain.TaskRuleEntity;
import com.three.a10_thousand_hours_theory_app.model.domain.User;
import com.three.a10_thousand_hours_theory_app.model.domain.UserEntity;
import com.three.a10_thousand_hours_theory_app.model.dto.CreateGoalRequestDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.CreateGoalResponseDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.GetAllGoalResponseDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.GetUserResponseDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.UpdateSharedGoalRequestDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.UploadGoalRequestDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.UploadGoalResponseDTO;
import com.three.a10_thousand_hours_theory_app.model.service.CreateGoalService;
import com.three.a10_thousand_hours_theory_app.model.service.CreateUserService;
import com.three.a10_thousand_hours_theory_app.model.service.GetAllGoalService;
import com.three.a10_thousand_hours_theory_app.model.service.GetUserService;
import com.three.a10_thousand_hours_theory_app.model.service.Service;
import com.three.a10_thousand_hours_theory_app.model.service.UpdateSharedGoalService;
import com.three.a10_thousand_hours_theory_app.model.service.UploadGoalService;
import com.three.a10_thousand_hours_theory_app.view.BoardView;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by LCH on 2016. 9. 20..
 */

@EBean
public class BoardPresenter {

    private BoardView mBoardView;

    @Bean(GetUserService.class)
    Service mGetUserService;

    @Bean(CreateUserService.class)
    Service mCreateUserService;

    @Bean(GetAllGoalService.class)
    Service mGetAllGoalService;

    @Bean(UploadGoalService.class)
    Service mUploadGoalService;

    @Bean(UpdateSharedGoalService.class)
    Service mUpdateSharedGoalService;

    @Bean(CreateGoalService.class)
    Service mCreateGoalService;

    private Context mContext;

    public BoardPresenter(Context mContext) {
        this.mContext = mContext;
    }

    public void setBoardView(BoardView mBoardView) {
        this.mBoardView = mBoardView;
    }

    public void loadSharedGoals() {
        User user = getUser();
        if(user == null){
            mCreateUserService.execute(null);
        }
    }

    public UserEntity getUser() {
        GetUserResponseDTO getUserResponseDTO = (GetUserResponseDTO) mGetUserService.execute(null);
        return getUserResponseDTO.getUserEntity();
    }

    public void likeSharedGoal(SharedGoal sharedGoal, User user) {
        String userKey = user.getKey();
        if(sharedGoal.getLikeUserKeys() == null){
            sharedGoal.setLikeUserKeys(new ArrayList(Arrays.asList(userKey)));
        }else{
            sharedGoal.getLikeUserKeys().add(userKey);
        }
        sharedGoal.setLike(sharedGoal.getLike()+1);
        mUpdateSharedGoalService.execute(new UpdateSharedGoalRequestDTO(sharedGoal));
    }

    public void unlikeSharedGoal(SharedGoal sharedGoal, User user) {
        String userKey = user.getKey();
        if(sharedGoal.getLikeUserKeys()!=null){
            sharedGoal.getLikeUserKeys().remove(userKey);
        }
        sharedGoal.setLike(sharedGoal.getLike()-1);
        mUpdateSharedGoalService.execute(new UpdateSharedGoalRequestDTO(sharedGoal));
    }

    public void sharedGoalDetails(SharedGoal sharedGoal) {
        mBoardView.onSharedGoalDetails(sharedGoal);
    }

    public void showGoalsDialog() {
        GetAllGoalResponseDTO g =(GetAllGoalResponseDTO) mGetAllGoalService.execute(null);
        UserEntity userEntity = getUser();
        List<GoalEntity> goals = g.getGoals();

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("내 목표 올리기");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(mContext, android.R.layout.select_dialog_item);
        for(GoalEntity goal : goals)
            arrayAdapter.add(goal.getTitle());

        builder.setNegativeButton("취소", (dialog, which) -> dialog.dismiss());
        builder.setAdapter(
                arrayAdapter, (dialog, which) -> {
                    GoalEntity selectedGoal = goals.get(which);
                    UploadGoalResponseDTO uploadGoalResponseDTO = (UploadGoalResponseDTO) mUploadGoalService.execute(new UploadGoalRequestDTO(userEntity, selectedGoal));
                    dialog.dismiss();
                    mBoardView.onUploadGoal(uploadGoalResponseDTO.getSharedGoal());
                });
        builder.show();
    }

    public void download(SharedGoal sharedGoal) {
        try {
            CreateGoalRequestDTO createGoalRequestDTO = new CreateGoalRequestDTO();

            createGoalRequestDTO.setGoalType(sharedGoal.getType());
            if (sharedGoal.getType() == Const.GOAL_TYPE_HOURS)
                createGoalRequestDTO.setGoalHours(sharedGoal.getGoalHours());
            else {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.add(Calendar.DATE, sharedGoal.getGoalDays());
                createGoalRequestDTO.setDeadLineDate(calendar.getTime());
            }
            createGoalRequestDTO.setDescription(sharedGoal.getDescription());
            createGoalRequestDTO.setTitle(sharedGoal.getTitle());

            for (SharedGoal.TaskRule r : sharedGoal.getTaskRules()) {
                TaskRuleEntity taskRuleEntity = new TaskRuleEntity();
                taskRuleEntity.setHours(r.getHours());
                taskRuleEntity.setLabelColor(r.getLabelColor());
                taskRuleEntity.setTitle(r.getTitle());
                taskRuleEntity.setStartDate(new Date());
                taskRuleEntity.setTimes(r.getTimes());
                createGoalRequestDTO.addTaskRuleEntity(taskRuleEntity);
            }

            CreateGoalResponseDTO r = (CreateGoalResponseDTO) mCreateGoalService.execute(createGoalRequestDTO);
            mBoardView.onDownloadGoal(r.getNewGoalEntity());
        }catch (Exception e){
            mBoardView.onFailToDownloadGoal();
        }
    }
}
