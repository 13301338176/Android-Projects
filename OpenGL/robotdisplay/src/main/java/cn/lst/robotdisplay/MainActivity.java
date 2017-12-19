package cn.lst.robotdisplay;

import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final String file = "base_link.STL";
    private GLSurfaceView glSurfaceView;
    private MyGLSurfaceView myGLSurfaceView;
    private float rotateDegree;
    private GLRenderer glRenderer;
    private ValueAnimator valueAnimator;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ConfigurationInfo configuration = activityManager.getDeviceConfigurationInfo();
        boolean supportEs2 = configuration.reqGlEsVersion >= 0x2000;
        if (supportEs2) {
            glRenderer = new GLRenderer(this);
//            glSurfaceView = new GLSurfaceView(this);
//            glSurfaceView.setRenderer(glRenderer);
//            setContentView(glSurfaceView);
            myGLSurfaceView = new MyGLSurfaceView(this);
            myGLSurfaceView.setRenderer(glRenderer);
            setContentView(myGLSurfaceView);
        } else {
            Toast.makeText(this, "不支持OpenGl2.0", Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_main);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (glSurfaceView != null) {
            glSurfaceView.onResume();
        }

//        valueAnimator = ValueAnimator.ofFloat(0,360);
//        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                float f = (float) animation.getAnimatedValue();
//                glRenderer.rotate(f);
//                glSurfaceView.invalidate();
//            }
//        });
//        valueAnimator.setInterpolator(new LinearInterpolator());
//        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
//        valueAnimator.setDuration(10000);
//        valueAnimator.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (glSurfaceView != null) {
            glSurfaceView.onPause();
        }
    }
}