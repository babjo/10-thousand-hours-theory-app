package com.three.a10_thousand_hours_theory_app.view.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import com.three.a10_thousand_hours_theory_app.R;
import com.three.a10_thousand_hours_theory_app.view.BoardView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by LCH on 2016. 9. 19..
 */

@EFragment(R.layout.fragment_board)
public class BoardFragment extends Fragment implements BoardView{

    @ViewById(R.id.shared_goal_list)
    RecyclerView mSharedGoalListView;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
