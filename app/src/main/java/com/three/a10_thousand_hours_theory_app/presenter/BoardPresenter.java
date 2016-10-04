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
import com.three.a10_thousand_hours_theory_app.model.dto.GetAllGoalRequestDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.GetAllGoalResponseDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.GetUserRequestDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.GetUserResponseDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.UpdateSharedGoalRequestDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.UpdateSharedGoalResponseDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.UploadGoalRequestDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.UploadGoalResponseDTO;
import com.three.a10_thousand_hours_theory_app.model.usecase.CreateGoalUseCase;
import com.three.a10_thousand_hours_theory_app.model.usecase.DefaultSubscriber;
import com.three.a10_thousand_hours_theory_app.model.usecase.GetAllGoalSyncUseCase;
import com.three.a10_thousand_hours_theory_app.model.usecase.GetUserSyncUseCase;
import com.three.a10_thousand_hours_theory_app.model.usecase.SyncUseCase;
import com.three.a10_thousand_hours_theory_app.model.usecase.UpdateSharedGoalUseCase;
import com.three.a10_thousand_hours_theory_app.model.usecase.UploadGoalUseCase;
import com.three.a10_thousand_hours_theory_app.model.usecase.UseCase;
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
public class BoardPresenter implements Presenter{

    private BoardView mBoardView;

    @Bean(GetUserSyncUseCase.class)
    SyncUseCase<GetUserRequestDTO, GetUserResponseDTO> mGetUserSyncUseCase;

    @Bean(GetAllGoalSyncUseCase.class)
    SyncUseCase<GetAllGoalRequestDTO, GetAllGoalResponseDTO> mGetAllGoalSyncUseCase;

    @Bean(UploadGoalUseCase.class)
    UseCase<UploadGoalRequestDTO> mUploadGoalUseCase;

    @Bean(UpdateSharedGoalUseCase.class)
    UseCase<UpdateSharedGoalRequestDTO> mUpdateSharedGoalUseCase;

    @Bean(CreateGoalUseCase.class)
    UseCase<CreateGoalRequestDTO> mCreateGoalUseCase;

    private Context mContext;

    public BoardPresenter(Context mContext) {
        this.mContext = mContext;
    }

    public void setBoardView(BoardView mBoardView) {
        this.mBoardView = mBoardView;
    }

    public void loadUser() {
        mGetUserSyncUseCase.execute(new GetUserRequestDTO());
    }

    public void likeSharedGoal(SharedGoal sharedGoal, User user) {
        String userKey = user.getKey();
        if(sharedGoal.getLikeUserKeys() == null){
            sharedGoal.setLikeUserKeys(new ArrayList(Arrays.asList(userKey)));
        }else{
            sharedGoal.getLikeUserKeys().add(userKey);
        }
        sharedGoal.setLike(sharedGoal.getLike()+1);
        mUpdateSharedGoalUseCase.execute(new UpdateSharedGoalRequestDTO(sharedGoal), new DefaultSubscriber<UpdateSharedGoalResponseDTO>());
    }

    public void unlikeSharedGoal(SharedGoal sharedGoal, User user) {
        String userKey = user.getKey();
        if(sharedGoal.getLikeUserKeys()!=null){
            sharedGoal.getLikeUserKeys().remove(userKey);
        }
        sharedGoal.setLike(sharedGoal.getLike()-1);
        mUpdateSharedGoalUseCase.execute(new UpdateSharedGoalRequestDTO(sharedGoal), new DefaultSubscriber<UpdateSharedGoalResponseDTO>());
    }

    public void sharedGoalDetails(SharedGoal sharedGoal) {
        mBoardView.onSharedGoalDetails(sharedGoal);
    }

    public void showGoalsDialog() {
        GetAllGoalResponseDTO g = mGetAllGoalSyncUseCase.execute(new GetAllGoalRequestDTO());
        UserEntity userEntity = getUser();
        List<GoalEntity> goals = g.getGoals();

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("내 목표 올리기");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(mContext, android.R.layout.select_dialog_item);
        for(GoalEntity goal : goals) arrayAdapter.add(goal.getTitle());

        builder.setNegativeButton("취소", (dialog, which) -> dialog.dismiss());
        builder.setAdapter(
                arrayAdapter, (dialog, which) -> {
                    GoalEntity selectedGoal = goals.get(which);
                    mUploadGoalUseCase.execute(new UploadGoalRequestDTO(userEntity, selectedGoal), new DefaultSubscriber<UploadGoalResponseDTO>(){
                        @Override
                        public void onNext(UploadGoalResponseDTO o) {
                            mBoardView.onUploadGoal(o.getSharedGoal());
                            dialog.dismiss();
                        }
                    });
                });

        builder.show();
    }

    public UserEntity getUser() {
        return mGetUserSyncUseCase.execute(new GetUserRequestDTO()).getUserEntity();
    }

    public void download(SharedGoal sharedGoal) {

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

        mCreateGoalUseCase.execute(createGoalRequestDTO, new DefaultSubscriber<CreateGoalResponseDTO>(){
            @Override
            public void onNext(CreateGoalResponseDTO o) {
                mBoardView.onDownloadGoal(o.getNewGoalEntity());
            }

            @Override
            public void onError(Throwable e) {
                mBoardView.onFailToDownloadGoal();
            }
        });
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        mBoardView = null;
        mCreateGoalUseCase.unsubscribe();
        mUploadGoalUseCase.unsubscribe();
    }
}
