package com.cloudchou.robolectrictest;

/**
 * date 2016/7/3
 *
 * @author Cloud
 * @version 1.1
 * @since Ver 1.1
 */
public class HelloThread {

    private InitStatus mInitStatus = InitStatus.INIT;
    private static final String TAG = HelloThread.class.getSimpleName();

    public enum InitStatus {
        INIT,
        INITING,
        OK,
        FAILED
    }

    private HelloThread() {
    }

    private static class HelloThreadHolder {
        private static HelloThread sInstance = new HelloThread();
    }

    public static HelloThread getInstance() {
        return HelloThreadHolder.sInstance;
    }

    public synchronized void init() {
        if (mInitStatus == InitStatus.INIT) {
            mInitStatus = InitStatus.INITING;
            new Thread() {
                @Override
                public void run() {
                    internalInit();
                }
            }.start();
        }
    }

    private void internalInit() {
        System.out.println("========> start init ===<<<");
        try {
            doSomething();
            setInitStatus(InitStatus.OK);
        } catch (Throwable e) {
            System.out.println("========> init failed ===<<<");
            SLog.e(TAG, "init failed");
            setInitStatus(InitStatus.FAILED);
        }
        System.out.println("========> finish init ===<<<");
    }

    private synchronized void setInitStatus(InitStatus status) {
        mInitStatus = status;
        notifyAll();
    }

    public synchronized void waitForInitFinished() throws InterruptedException {
        while (mInitStatus == InitStatus.INITING) {
            wait();
        }
    }

    private void doSomething() throws InterruptedException {
        Thread.sleep(3500);
        throw new RuntimeException("som exception");
    }

}
