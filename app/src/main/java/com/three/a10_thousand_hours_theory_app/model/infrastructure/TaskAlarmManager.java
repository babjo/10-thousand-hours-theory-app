package com.three.a10_thousand_hours_theory_app.model.infrastructure;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.three.a10_thousand_hours_theory_app.Const;
import com.three.a10_thousand_hours_theory_app.Utils;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.SystemService;

import java.util.Calendar;

import static android.content.ContentValues.TAG;

/**
 * Created by LCH on 2016. 9. 18..
 */

@EBean(scope = EBean.Scope.Singleton)
public class TaskAlarmManager {

    @SystemService
    AlarmManager mAlarmManager;

    private Context mContext;

    public TaskAlarmManager(Context mContext) {
        this.mContext = mContext;
    }

    public void setting(){
        Intent intent = new Intent(mContext, AlarmReceiver_.class);
        intent.setAction(Const.INTENT_ACTION_CREATE_NEW_TASKS);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, 0);

        Calendar cal = Calendar.getInstance();
        // 다음주 월요일 00:00 태스크 생성
        cal.add(Calendar.DATE, (Calendar.MONDAY - cal.get(Calendar.DAY_OF_WEEK))); // 이번주 월요일
        cal.add(Calendar.DATE, 7); // 다음주 월요일
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        long interval = AlarmManager.INTERVAL_DAY;

        // for test
        /*
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 5);
        long interval = 5000;*/

        Log.d(TAG, "처음 알람 예정 시간 : " + Utils.DATE_FORMAT_yyyy_MM_dd_hh_mm_ss.format(cal.getTime()));
        mAlarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), interval, pendingIntent);
        Log.d(TAG, "알람 매니저 설정 완료");
    }
}
