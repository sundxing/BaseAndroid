package com.sundxing.android.baseandroid.service;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;


/**
 * Created by sundxing on 17/10/16.
 * 7.0 以下 没有触发最小时间限制。
 * 所以一旦条件满足就是执行Service.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class DJobService extends JobService {
    private static int periodicTime = 1;
    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d("DJobService", "start job");
        startJobService(this.getApplicationContext());
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d("DJobService", "stop job");

        return true;
    }

    public static void startJobService (Context context) {
        periodicTime++;
        JobScheduler scheduler = (JobScheduler) context.getApplicationContext().getSystemService(Context.JOB_SCHEDULER_SERVICE);
        JobInfo.Builder builder = new JobInfo.Builder(3, new ComponentName(context, DJobService.class));
        if (periodicTime > 3) {
            periodicTime = 1000;
        }
        builder.setPeriodic(periodicTime * 1000);
//        builder.setMinimumLatency(periodicTime * 1000);

        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        int result = scheduler.schedule(builder.build());
        Log.d("DJobService", "schedule job success = " + (result == JobScheduler.RESULT_SUCCESS));

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void startJobServiceTrigger (Context context) {
//        JobScheduler scheduler = (JobScheduler) context.getApplicationContext().getSystemService(Context.JOB_SCHEDULER_SERVICE);
//        JobInfo.Builder builder = new JobInfo.Builder(2, new ComponentName(context, DJobService.class));
//        builder.addTriggerContentUri(new JobInfo.TriggerContentUri(Telephony.Sms.CONTENT_URI, JobInfo.TriggerContentUri.FLAG_NOTIFY_FOR_DESCENDANTS));
//        builder.addTriggerContentUri(new JobInfo.TriggerContentUri(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, JobInfo.TriggerContentUri.FLAG_NOTIFY_FOR_DESCENDANTS));
//        scheduler.schedule(builder.build());
    }
}
