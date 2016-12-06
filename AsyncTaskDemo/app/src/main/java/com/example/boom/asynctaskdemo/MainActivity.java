package com.example.boom.asynctaskdemo;

import android.app.Activity;
import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button bt=(Button)findViewById(R.id.bt);
        bt.setText("30 s");
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddStringTask(bt).execute();
            }
        });


    }

    /**
     * AsyncTask中定义了三种泛型类型的的参数
     * Params  启动任务执行的输入参数
     * Progress  后台任务执行的百分比
     * Result   后台执行任务最终返回的结果
     */
    //自定义一个AsyncTask
    public class AddStringTask extends AsyncTask<String, Integer, String> {
        Button bt;
        public AddStringTask(Button bt){
            this.bt=bt;
        }

        @Override
        protected void onPreExecute() {
            bt.setEnabled(false);
            super.onPreExecute();
        }

        //处理耗时操作
        @Override
        protected String doInBackground(String... params) {
            int i = 30;
            while(true)
            {
                publishProgress((int)i);
                if(i == 0 ) break;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                i--;
            }
            return null;
        }

        //Integer...相当于Integer[]   可以使用进度条提升用户的体验度
        @Override
        protected void onProgressUpdate(Integer... values) {
            if (values[0]!=0){
                bt.setText("" + values[0] + " s");
            }
            super.onProgressUpdate(values);
        }

        //相当于handler和message  更新UI
        @Override
        protected void onPostExecute(String values) {
                bt.setText("30 s");
                bt.setEnabled(true);
            super.onPostExecute(values);
        }

    }

}
