package com.develop.frame.util.permission;

import java.util.List;

/**
 * Created by zengh on 2018/2/1.
 */

public interface PermissionCallback {

    void onSuccess(List<String> permissions);
    void onFail(List<String> permissions);

}
