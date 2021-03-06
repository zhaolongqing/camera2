package com.blackuio.center.camera2api;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureRequest;
import android.os.Handler;


public class MySessionStateCallback extends CameraCaptureSession.StateCallback {

    private static final String TAG = "MySessionStateCallback";
    private CameraSet cameraSet;
    private final CaptureRequest.Builder builder;
    private final Handler handler;
    static final int CONFIG = 1234;

    MySessionStateCallback(CameraSet cameraSet, CaptureRequest.Builder builder, Handler handler) {
        Log.g(TAG,"MySessionStateCallback");
        this.cameraSet = cameraSet;
        this.builder = builder;
        this.handler = handler;
    }

    @Override
    public void onConfigured(CameraCaptureSession session) {
        Log.g(TAG,"onConfigured");
        try {
            session.setRepeatingRequest(builder.build(), null, handler);
            if (handler != null){
                Log.g(TAG,"sendEmptyMessage");
                handler.sendEmptyMessage(CONFIG);
            }
        } catch (CameraAccessException e) {
            Log.e(TAG, "onConfigured", e);
        }
        cameraSet.setSession(session);
    }

    @Override
    public void onConfigureFailed(CameraCaptureSession session) {
        Log.g(TAG, "onConfigureFailed-->session:" + session);
    }
}
