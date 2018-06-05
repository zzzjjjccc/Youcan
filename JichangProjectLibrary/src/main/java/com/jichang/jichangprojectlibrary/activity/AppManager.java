package com.jichang.jichangprojectlibrary.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;

/**
 * @author jichang     on 2018/1/18.
 *         email:   2218982471@qq.com
 *         describ: Manager this project
 */

public class AppManager {

    private static Stack<BaseActivity> activityStack;

    private static AppManager instance;

    /**
     * Singleton implements
     *
     * @return Instance of AppManager
     */
    public static AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    /**
     * Add the activity to activityStack
     *
     * @param activity What activity will be add
     */
    public void addActivity(BaseActivity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * Get current activity
     *
     * @return current activity
     */
    public BaseActivity currentActivty() {
        if (activityStack == null || activityStack.isEmpty()) {
            return null;
        }
        BaseActivity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * Find activity which in activityStack
     *
     * @param cls Which activity will be find
     * @return Null, activityStack not contains activity you want; Otherwise, you will get a activity you want
     */
    public BaseActivity findActivity(Class<?> cls) {
        BaseActivity activity = null;
        for (BaseActivity aty : activityStack) {
            if (aty.getClass().equals(cls)) {
                activity = aty;
                break;
            }
        }
        return activity;
    }

    /**
     * Finish many activities
     *
     * @param cls
     */
    public void finishActivity(Class<?> cls) {
        for (BaseActivity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * Finish single top activity in activity stack
     */
    public void finishActivity() {
        BaseActivity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * Real finish
     *
     * @param activity You want to finish
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * Finish all activity of this project
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * When this app will exit, you'll call this method to kill current processes
     *
     * @param context
     */
    public void appExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
            System.exit(0);
        }
    }

    /**
     * Require activity stack of this project
     *
     * @return
     */
    public static Stack<BaseActivity> getActivityStack() {
        return activityStack;
    }

}
