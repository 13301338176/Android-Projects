package cn.ssdut.lst.remoteserviceuser;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private IMyAidlInterface binder;
    Button bt ;
    private ServiceConnection conn = new ServiceConnection(){
        public void onServiceConnected(ComponentName name,IBinder service) {
            Log.i("tag","与远程服务建立连接");
            try {
                binder = IMyAidlInterface.Stub.asInterface(service);
                String tmp = binder.getMessage();
                Toast.makeText(getApplicationContext(), "接收到内容：" + tmp, Toast.LENGTH_LONG);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        public void onServiceDisconnected(ComponentName nem) {
            Log.i("tag","与远程服务断开连接");
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt = (Button)findViewById(R.id.bt_remoteService);
        Log.i("tag", "当前进程的Id是：" + Process.myPid());
        bt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent t = new Intent();
                t.setAction("cn.lst.RemoteService");
                t.setPackage("cn.ssdut.lst.remoteservicecreater");
                bindService(t, conn, Context.BIND_AUTO_CREATE);
//                Intent intent = new Intent();
//                intent.setClassName(MainActivity.this,"cn.lst.RemoteService");
//                bindService(intent, conn, Context.BIND_AUTO_CREATE);
            }
        });
    }
}
