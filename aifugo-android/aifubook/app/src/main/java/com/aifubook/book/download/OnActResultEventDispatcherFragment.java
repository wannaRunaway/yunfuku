package com.aifubook.book.download;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;

public class OnActResultEventDispatcherFragment extends Fragment {


    public static final String TAG = "on_act_result_event_dispatcher";
    private SparseArray<ActForResultCallback> mCallbacks = new SparseArray();

    public OnActResultEventDispatcherFragment() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
    }

    public void startForResult(Intent intent, ActForResultCallback callback) {
        this.mCallbacks.put(callback.hashCode(), callback);
        this.startActivityForResult(intent, callback.hashCode());
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ActForResultCallback callback = (ActForResultCallback)this.mCallbacks.get(requestCode);
        this.mCallbacks.remove(requestCode);
        if (callback != null) {
            callback.onActivityResult(resultCode, data);
        }

    }
}
