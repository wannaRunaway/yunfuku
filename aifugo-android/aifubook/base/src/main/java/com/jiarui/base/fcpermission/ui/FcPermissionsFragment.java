package com.jiarui.base.fcpermission.ui;

import android.content.DialogInterface;
import android.text.TextUtils;

import com.jiarui.base.R;
import com.jiarui.base.fcpermission.FcPermissions;
import com.jiarui.base.fcpermission.impl.FcPermissionsCallbacks;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

public abstract class FcPermissionsFragment extends Fragment implements FcPermissionsCallbacks {

    private DialogInterface.OnClickListener mOnClickListener;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        FcPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    protected void requestPermissions(final Object object, String rationale,
                                      final int requestCode, final String... perms) {
        requestPermissions(object, rationale,
                android.R.string.ok,
                android.R.string.cancel,
                requestCode, perms);
    }

    protected void requestPermissions(final Object object, String rationale,
                                      @StringRes int positiveButton,
                                      @StringRes int negativeButton,
                                      final int requestCode, final String... perms) {
        FcPermissions.requestPermissions(object, rationale, positiveButton, negativeButton, requestCode, perms);
    }

    @NonNull
    protected abstract String getRationale4NeverAskAgain();

    public void getNegativeButtonOnClickListener(DialogInterface.OnClickListener listener) {
        mOnClickListener = listener;
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if(TextUtils.isEmpty(getRationale4NeverAskAgain())){
            throw new IllegalArgumentException("Rationale can not be empty .");
        }
        FcPermissions.checkDeniedPermissionsNeverAskAgain(this, getRationale4NeverAskAgain(),
                R.string.setting, android.R.string.cancel, mOnClickListener, perms);
        onPermissionDenied(requestCode , perms);
    }

    public abstract void onPermissionDenied(int requestCode, List<String> perms);
}
