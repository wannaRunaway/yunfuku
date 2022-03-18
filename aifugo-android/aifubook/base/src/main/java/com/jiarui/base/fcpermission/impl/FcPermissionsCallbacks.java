package com.jiarui.base.fcpermission.impl;


import java.util.List;

import androidx.core.app.ActivityCompat;

public interface FcPermissionsCallbacks extends
        ActivityCompat.OnRequestPermissionsResultCallback {

    /**
     *用户授权后调用
     * @param requestCode
     * @param perms
     */
    void onPermissionsGranted(int requestCode, List<String> perms);

    /**
     * 用户禁用后调用
     * @param requestCode
     * @param perms
     */
    void onPermissionsDenied(int requestCode, List<String> perms);
}