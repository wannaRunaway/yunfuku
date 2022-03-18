package com.jiarui.base.fcpermission.impl;

import java.util.List;

public interface OnPermissionsDeniedListener {

    void onPermissionsDenied(int requestCode, List<String> perms);
}
