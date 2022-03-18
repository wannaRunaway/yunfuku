package com.jiarui.base.fcpermission.impl;

import java.util.List;

public interface OnPermissionsGrantedListener {

    void onPermissionsGranted(int requestCode, List<String> perms);
}
