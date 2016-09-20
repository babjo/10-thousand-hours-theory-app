package com.three.a10_thousand_hours_theory_app.presenter;


import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.widget.ArrayAdapter;

import com.three.a10_thousand_hours_theory_app.model.domain.GoalEntity;
import com.three.a10_thousand_hours_theory_app.model.domain.SharedGoal;
import com.three.a10_thousand_hours_theory_app.model.domain.User;
import com.three.a10_thousand_hours_theory_app.model.domain.UserEntity;
import com.three.a10_thousand_hours_theory_app.model.dto.GetAllGoalResponseDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.GetUserResponseDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.UploadGoalRequestDTO;
import com.three.a10_thousand_hours_theory_app.model.service.AsyncService;
import com.three.a10_thousand_hours_theory_app.model.service.CreateUserService;
import com.three.a10_thousand_hours_theory_app.model.service.GetAllGoalService;
import com.three.a10_thousand_hours_theory_app.model.service.GetAllSharedGoalAsyncService;
import com.three.a10_thousand_hours_theory_app.model.service.GetUserService;
import com.three.a10_thousand_hours_theory_app.model.service.Service;
import com.three.a10_thousand_hours_theory_app.model.service.UploadGoalService;
import com.three.a10_thousand_hours_theory_app.view.BoardView;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.List;

/**
 * Created by LCH on 2016. 9. 20..
 */

@EBean
public class BoardPresenter {

    private BoardView mBoardView;

    @Bean(GetAllSharedGoalAsyncService.class)
    AsyncService mGetAllSharedGoalService;

    @Bean(GetUserService.class)
    Service mGetUserService;

    @Bean(CreateUserService.class)
    Service mCreateUserService;

    @Bean(GetAllGoalService.class)
    Service mGetAllGoalService;

    @Bean(UploadGoalService.class)
    Service mUploadGoalService;

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
    }

    public void unlikeSharedGoal(SharedGoal sharedGoal, User user) {
    }

    public void sharedGoalDetails(SharedGoal sharedGoal) {

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
                    mUploadGoalService.execute(new UploadGoalRequestDTO(userEntity, selectedGoal));
                    //uploadGoal
                    dialog.dismiss();
                });
        builder.show();
    }
}
