package com.aifubook.book.download;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;

public class ActResultRequest {

    private OnActResultEventDispatcherFragment fragment;

    public ActResultRequest(Activity activity) {
        this.fragment = this.getEventDispatchFragment(activity);
    }

    private OnActResultEventDispatcherFragment getEventDispatchFragment(Activity activity) {
        FragmentManager fragmentManager = activity.getFragmentManager();
        OnActResultEventDispatcherFragment fragment = this.findEventDispatchFragment(fragmentManager);
        if (fragment == null) {
            fragment = new OnActResultEventDispatcherFragment();
            fragmentManager.beginTransaction().add(fragment, "on_act_result_event_dispatcher").commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }

        return fragment;
    }

    private OnActResultEventDispatcherFragment findEventDispatchFragment(FragmentManager manager) {
        return (OnActResultEventDispatcherFragment)manager.findFragmentByTag("on_act_result_event_dispatcher");
    }

    public void startForResult(Intent intent, ActForResultCallback callback) {
        this.fragment.startForResult(intent, callback);
    }
}
