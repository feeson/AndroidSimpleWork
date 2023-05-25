package cloud.dicsfeesono.androidsimplework;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import cloud.dicsfeesono.androidsimplework.databinding.ActivityCdplayerBinding;

import java.io.IOException;

import java.io.File;
import java.io.IOException;

import android.R.integer;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class CDPlayer extends Activity implements OnClickListener,OnSeekBarChangeListener,SurfaceHolder.Callback, MediaPlayer.OnPreparedListener{
    private Button play, pause, stop;
    private SurfaceView surfaceView;
    private MediaPlayer mediaPlayer;
    private Uri uri=null;
    private CDPlayer root=this;
    private SurfaceHolder surfaceHolder;
    private SeekBar mSeekBar;
    private TextView tv, tv2;
    private boolean hadDestroy = false;
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {

            switch (msg.what) {
                case 0x01:

                    break;

                default:
                    break;
            }
        };
    };
    Runnable runnable = new Runnable() {

        @Override
        public void run() {

            if (!hadDestroy) {
                mHandler.postDelayed(this, 1000);
                int currentTime = Math
                        .round(mediaPlayer.getCurrentPosition() / 1000);
                String currentStr = String.format("%s%02d:%02d", "当前时间 ",
                                                  currentTime / 60, currentTime % 60);
                tv.setText(currentStr);
                mSeekBar.setProgress(mediaPlayer.getCurrentPosition());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cdplayer);
        play = (Button) findViewById(R.id.play);
        pause = (Button) findViewById(R.id.pause);
        stop = (Button) findViewById(R.id.stop);
        mSeekBar = (SeekBar) findViewById(R.id.seekbar);
        tv = (TextView) findViewById(R.id.tv);
        tv2 = (TextView) findViewById(R.id.tv2);
        mSeekBar.setOnSeekBarChangeListener(this);
        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        stop.setOnClickListener(this);
        surfaceView = findViewById(R.id.surface_view);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);

        mediaPlayer = new MediaPlayer();
    }
    private void play(){
        try {
            surfaceView=findViewById(R.id.surface_view);
            surfaceHolder=surfaceView.getHolder();
            surfaceView.getHolder().addCallback(root);
            mediaPlayer=new MediaPlayer();
            mediaPlayer.setDataSource(getApplicationContext(),uri);
            mediaPlayer.setDisplay(surfaceHolder);
            mediaPlayer.setAudioAttributes(new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MOVIE).build());
            mediaPlayer.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 如果权限尚未授予，则请求权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
        } else {
            // 如果权限已经授予，则执行你的文件操作逻辑
            if (uri==null){
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*"); // 设置文件类型，此处为任意类型的文件
                startActivityForResult(intent, 1);
            }else {
                play();
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                this.uri=uri;
                play();
            }
        }
    }


// 在合适的位置请求读取外部存储权限

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 如果权限被授予，则执行你的文件操作逻辑
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*"); // 设置文件类型，此处为任意类型的文件
                startActivityForResult(intent, 1);
            } else {
                // 如果权限被拒绝，则可以采取适当的措施，如显示一个提示或禁用相关功能
                Toast.makeText(this, "Permission denied. Cannot access files.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    /**
     * 初始化播放器
     */

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play:
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    int totalTime = Math.round(mediaPlayer.getDuration() / 1000);
                    @SuppressLint("DefaultLocale") String str = String.format("%02d:%02d", totalTime / 60,
                                                                              totalTime % 60);
                    tv2.setText(str);
                    mSeekBar.setMax(mediaPlayer.getDuration());
                    mHandler.postDelayed(runnable, 1000);
                }

                break;
            case R.id.pause:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
                break;
            case R.id.stop:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                break;

            default:
                break;
        }
    }
    private static final int REQUEST_CODE_PERMISSION = 1;
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        init();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format,
                               int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        mediaPlayer.release();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,
                                  boolean fromUser) {
        if (mediaPlayer != null) {
            if (Math.abs(progress-mediaPlayer.getCurrentPosition())>2000){
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            hadDestroy = true;
            mediaPlayer.release();
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaPlayer.start();
    }
}
