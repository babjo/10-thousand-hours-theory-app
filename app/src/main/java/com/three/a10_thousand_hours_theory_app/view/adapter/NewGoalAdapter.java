package com.three.a10_thousand_hours_theory_app.view.adapter;

import android.content.Context;
import android.text.InputType;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.three.a10_thousand_hours_theory_app.Const;
import com.three.a10_thousand_hours_theory_app.R;
import com.three.a10_thousand_hours_theory_app.Utils;
import com.three.a10_thousand_hours_theory_app.model.domain.TaskRuleEntity;
import com.three.a10_thousand_hours_theory_app.model.dto.CreateGoalRequestDTO;
import com.three.a10_thousand_hours_theory_app.presenter.NewGoalPresenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class NewGoalAdapter extends BaseAdapter {

    private final LayoutInflater mInflater;
    private CreateGoalRequestDTO createGoalRequestDTO;
    private Context mContext;

    private NewGoalPresenter mNewGoalPresenter;

    private List<Integer> types = new ArrayList(Arrays.asList(STEP_1, STEP_2, STEP_3));
    private NewTaskAdapter mNewTaskAdapter;

    private static final String TAG = NewGoalAdapter.class.getName();

    public NewGoalAdapter(Context mContext) {
        this(mContext, new CreateGoalRequestDTO());
    }

    public NewGoalAdapter(Context mContext, CreateGoalRequestDTO createGoalRequestDTO) {
        this.mContext = mContext;
        this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.createGoalRequestDTO = createGoalRequestDTO;
    }

    public void setNewGoalPresenter(NewGoalPresenter mNewGoalPresenter) {
        this.mNewGoalPresenter = mNewGoalPresenter;
    }

    @Override
    public int getCount() {
        return types.size();
    }

    @Override
    public Integer getItem(int position) {
        return types.get(position);
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

        int type = types.get(position);

        if(type == STEP_1){
            if(convertView == null)
            {
                convertView = mInflater.inflate(R.layout.new_goal_step1, parent, false);

                step1ViewHolder = new Step1ViewHolder();
                step1ViewHolder.mTitleEt = (MaterialEditText) convertView.findViewById(R.id.goal_title_et);
                step1ViewHolder.mDescriptionEt = (MaterialEditText) convertView.findViewById(R.id.goal_description_et);
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

        }else if (type == STEP_2){
            if(convertView == null)
            {
                convertView = mInflater.inflate(R.layout.new_goal_step2, parent, false);
                step2ViewHolder = new Step2ViewHolder();
                step2ViewHolder.mNextBtn = (Button) convertView.findViewById(R.id.step2_next_btn);
                step2ViewHolder.mNewGoalDeadLineTypeLy = (LinearLayout) convertView.findViewById(R.id.goal_dead_line_type_ly);
                step2ViewHolder.mNewGoalDeadLineEt = (MaterialEditText) convertView.findViewById(R.id.new_goal_dead_line_et);
                step2ViewHolder.mChangeGoalTypeTv = (TextView) convertView.findViewById(R.id.change_goal_type_tv);
                step2ViewHolder.mNewGoalHoursTypeLy = (LinearLayout) convertView.findViewById(R.id.goal_hours_type_ly);
                step2ViewHolder.mNewGoalHoursEt = (MaterialEditText) convertView.findViewById(R.id.new_goal_hours_et);
                disableEditText(step2ViewHolder.mNewGoalDeadLineEt);

                convertView.setTag(step2ViewHolder);
            }
            else {
                step2ViewHolder = (Step2ViewHolder) convertView.getTag();
            }

            // Set Event Listener
            step2ViewHolder.mNextBtn.setOnClickListener(v -> {
                if(createGoalRequestDTO.getGoalType() == Const.GOAL_TYPE_HOURS)
                    createGoalRequestDTO.setGoalHours(Integer.parseInt(step2ViewHolder.mNewGoalHoursEt.getText().toString()));
                mNewGoalPresenter.goNewGoalFormStep3();
            });
            step2ViewHolder.mNewGoalDeadLineEt.setOnClickListener(v -> mNewGoalPresenter.showDatePicker(createGoalRequestDTO.getDeadLineDate()));

            step2ViewHolder.mChangeGoalTypeTv.setOnClickListener(v -> {
                if (step2ViewHolder.mNewGoalDeadLineTypeLy.getVisibility() == View.VISIBLE){
                    showGoalHoursForm();
                    createGoalRequestDTO.setGoalType(Const.GOAL_TYPE_HOURS);
                }
                else {
                    showGoalDeadLineForm();
                    createGoalRequestDTO.setGoalType(Const.GOAL_TYPE_DEADLINE);
                }
            });

            // Set Values
            if(createGoalRequestDTO.getDeadLineDate()!=null)
                step2ViewHolder.mNewGoalDeadLineEt.setText(Utils.DATE_FORMAT_yyyy_MM_dd.format(createGoalRequestDTO.getDeadLineDate()));

            if(createGoalRequestDTO.getGoalHours()!=0)
                step2ViewHolder.mNewGoalHoursEt.setText(Integer.toString(createGoalRequestDTO.getGoalHours()));

            if(createGoalRequestDTO.getGoalType() == Const.GOAL_TYPE_DEADLINE)
                showGoalDeadLineForm();
            else
                showGoalHoursForm();

        }else{
            if(convertView == null){
                convertView = mInflater.inflate(R.layout.new_goal_step3, parent, false);
                step3ViewHolder = new Step3ViewHolder();
                step3ViewHolder.mBackBtn = (Button) convertView.findViewById(R.id.step3_back_btn);
                step3ViewHolder.mAddNewTaskIv = (LinearLayout) convertView.findViewById(R.id.add_new_task_iv);
                step3ViewHolder.mSubmitBtn = (Button) convertView.findViewById(R.id.step3_next_btn);
                step3ViewHolder.mTaskLv = (ListView) convertView.findViewById(R.id.step3_task_lv);

                step3ViewHolder.mBackBtn.setOnClickListener(view -> mNewGoalPresenter.goNewGoalFormStep1());
                step3ViewHolder.mAddNewTaskIv.setOnClickListener(v -> mNewGoalPresenter.showTaskDialog(null));
                step3ViewHolder.mSubmitBtn.setOnClickListener(v -> {
                    if(createGoalRequestDTO.getGoalId() == 0) {
                        mNewGoalPresenter.submitNewGoal(createGoalRequestDTO);
                    }else{
                        mNewGoalPresenter.updateGoal(createGoalRequestDTO.convert());
                    }
                });

                mNewTaskAdapter = new NewTaskAdapter(mContext, createGoalRequestDTO.getTaskRuleEntities());
                mNewTaskAdapter.setNewGoalPresenter(mNewGoalPresenter);
                step3ViewHolder.mTaskLv.setAdapter(mNewTaskAdapter);
                step3ViewHolder.mTaskLv.setSelection(mNewTaskAdapter.getCount()-1);
                convertView.setTag(step3ViewHolder);
            }else{
                step3ViewHolder = (Step3ViewHolder) convertView.getTag();
            }
        }

        return convertView;
    }

    private void showGoalDeadLineForm() {
        step2ViewHolder.mNewGoalDeadLineTypeLy.setVisibility(View.VISIBLE);
        step2ViewHolder.mNewGoalHoursTypeLy.setVisibility(View.INVISIBLE);
        SpannableString text = new SpannableString(Const.시간으로_설정할래요);
        text.setSpan(new UnderlineSpan(), 0, text.length(), 0);
        step2ViewHolder.mChangeGoalTypeTv.setText(text);
    }

    private void showGoalHoursForm() {
        step2ViewHolder.mNewGoalDeadLineTypeLy.setVisibility(View.INVISIBLE);
        step2ViewHolder.mNewGoalHoursTypeLy.setVisibility(View.VISIBLE);
        SpannableString text = new SpannableString(Const.날짜로_설정할래요);
        text.setSpan(new UnderlineSpan(), 0, text.length(), 0);
        step2ViewHolder.mChangeGoalTypeTv.setText(text);
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    public final static int STEP_1 = 0;
    public final static int STEP_2 = 1;
    public final static int STEP_3 = 2;

    public void saveInputs() {
        if(step1ViewHolder != null) {
            createGoalRequestDTO.setTitle(step1ViewHolder.mTitleEt.getText().toString());
            createGoalRequestDTO.setDescription(step1ViewHolder.mDescriptionEt.getText().toString());
        }
    }

    public void setNewGoalDeadLineDate(Date deadLine) {
        createGoalRequestDTO.setDeadLineDate(deadLine);
        step2ViewHolder.mNewGoalDeadLineEt.setText(Utils.DATE_FORMAT_yyyy_MM_dd.format(deadLine));
    }


    public void clearAndAdd(int type) {
        types.clear();
        types.add(type);
    }

    public void clearAndAddAll(List<Integer> targets) {
        types.clear();
        types.addAll(targets);
    }

    public void addTasks(TaskRuleEntity newTaskEntity) {
        createGoalRequestDTO.addTaskRuleEntity(newTaskEntity);
    }

    public class Step1ViewHolder {
        public MaterialEditText mTitleEt;
        public MaterialEditText mDescriptionEt;
        public Button mNextBtn;
    }

    public class Step2ViewHolder {

        //for goal dead line type
        public LinearLayout mNewGoalDeadLineTypeLy;
        public MaterialEditText mNewGoalDeadLineEt;

        //for goal hours type
        public LinearLayout mNewGoalHoursTypeLy;
        public MaterialEditText mNewGoalHoursEt;

        public TextView mChangeGoalTypeTv;
        public Button mNextBtn;
    }

    public class Step3ViewHolder{
        public Button mBackBtn;
        public Button mSubmitBtn;
        public ListView mTaskLv;
        public LinearLayout mAddNewTaskIv;
    }

    private void disableEditText(EditText editText) {
        editText.setInputType(InputType.TYPE_NULL);
    }
}
