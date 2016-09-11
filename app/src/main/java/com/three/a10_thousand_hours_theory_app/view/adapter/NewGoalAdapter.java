package com.three.a10_thousand_hours_theory_app.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.three.a10_thousand_hours_theory_app.R;
import com.three.a10_thousand_hours_theory_app.model.domain.Goal;
import com.three.a10_thousand_hours_theory_app.model.dto.CreateGoalRequestDTO;
import com.three.a10_thousand_hours_theory_app.presenter.NewGoalPresenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewGoalAdapter extends BaseAdapter {

    private final LayoutInflater mInflater;
    private CreateGoalRequestDTO createGoalRequestDTO;
    private Context mContext;

    private NewGoalPresenter mNewGoalPresenter;
    private List<String> unused = new ArrayList(Arrays.asList("step1","step2","step3"));

    public NewGoalAdapter(Context mContext) {
        this.mContext = mContext;
        this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.createGoalRequestDTO = new CreateGoalRequestDTO();
    }

    public void setNewGoalPresenter(NewGoalPresenter mNewGoalPresenter) {
        this.mNewGoalPresenter = mNewGoalPresenter;
    }

    @Override
    public int getCount() {
        return unused.size();
    }

    @Override
    public String getItem(int position) {
        return unused.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private Step1ViewHolder step1ViewHolder;
    private Step2ViewHolder step2ViewHolder;
    private Step3ViewHolder step3ViewHolder;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        int type = getItemViewType(position);

        if(type == STEP_1){
            if(convertView == null)
            {
                convertView = mInflater.inflate(R.layout.new_goal_step1, parent, false);

                step1ViewHolder = new Step1ViewHolder();
                step1ViewHolder.mTitleEt = (EditText) convertView.findViewById(R.id.goal_title_et);
                step1ViewHolder.mDescriptionEt = (EditText) convertView.findViewById(R.id.goal_description_et);
                step1ViewHolder.mNextBtn = (Button) convertView.findViewById(R.id.step1_next_btn);
                step1ViewHolder.mNextBtn.setOnClickListener(view -> {
                    createGoalRequestDTO.setTitle(step1ViewHolder.mTitleEt.getText().toString());
                    createGoalRequestDTO.setDescription(step1ViewHolder.mDescriptionEt.getText().toString());
                    mNewGoalPresenter.goNewGoalFormStep2();
                });

                convertView.setTag(step1ViewHolder);
            }
            else {
                step1ViewHolder = (Step1ViewHolder) convertView.getTag();
            }

            step1ViewHolder.mTitleEt.setText(createGoalRequestDTO.getTitle());
            step1ViewHolder.mDescriptionEt.setText(createGoalRequestDTO.getDescription());

        }else{
            if(convertView == null)
            {
                convertView = mInflater.inflate(R.layout.new_goal_step2, parent, false);
                step2ViewHolder = new Step2ViewHolder();
                step2ViewHolder.mNextBtn = (Button) convertView.findViewById(R.id.step2_next_btn);
                Goal goal = new Goal("", "");
                step2ViewHolder.mNextBtn.setOnClickListener(view -> {
                    mNewGoalPresenter.submitNewGoal(goal);
                });
                step2ViewHolder.mBackBtn = (Button) convertView.findViewById(R.id.step2_back_btn);
                step2ViewHolder.mBackBtn.setOnClickListener(view -> {
                    mNewGoalPresenter.goNewGoalFormStep1();
                });

                convertView.setTag(step2ViewHolder);
            }
            else {
                step2ViewHolder = (Step2ViewHolder) convertView.getTag();
            }

            //step2ViewHolder.mTv.setText(mData.get(position));
        }

        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    private final static int STEP_1 = 0;
    private final static int STEP_2 = 1;
    private final static int STEP_3 = 2;

    public void saveInputs() {
        if(step1ViewHolder != null) {
            createGoalRequestDTO.setTitle(step1ViewHolder.mTitleEt.getText().toString());
            createGoalRequestDTO.setDescription(step1ViewHolder.mDescriptionEt.getText().toString());
        }
    }

    public class Step1ViewHolder {
        public EditText mTitleEt;
        public EditText mDescriptionEt;
        public Button mNextBtn;
    }

    public class Step2ViewHolder {
        public Button mNextBtn;
        public Button mBackBtn;
    }

    public class Step3ViewHolder{

    }
}
