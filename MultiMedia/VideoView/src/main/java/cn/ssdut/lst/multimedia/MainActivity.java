package cn.ssdut.lst.multimedia;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

/**
 * 用VideoView来播放sd卡视频
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //使窗口支持透明化，这样就可以调用setAlpha,drawColor等函数
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(R.layout.activity_main);
        VideoView videoView = (VideoView) findViewById(R.id.video);
        MediaController controller = new MediaController(this);
        File video = new File("/mnt/sdcard/data/videoTest.mp4");
        if (video.exists()) {
            videoView.setVideoPath(video.getAbsolutePath());
            videoView.setMediaController(controller);
            controller.setMediaPlayer(videoView);
            //让VideoView获得焦点
            videoView.requestFocus();
        }else{
            Toast.makeText(MainActivity.this, "Video找不到", Toast.LENGTH_SHORT).show();
        }

    }
}
