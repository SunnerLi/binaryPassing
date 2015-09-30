package com.example.sunner.d930_passing;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    int bytesRead;
    Socket sock = null;
    Button bt_connect, bt_send;
    EditText ipAddress;
    TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set View物件
        bt_connect = (Button) findViewById(R.id.bt_connect);
        bt_send = (Button) findViewById(R.id.bt_send);
        ipAddress = (EditText) findViewById(R.id.ip);
        status = (TextView) findViewById(R.id.textView1);

        bt_connect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                connectDevice();
            }

        });

        bt_send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                SendFile();
            }

        });
    }


    private void connectDevice() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();

                //連接
                try {
                    Log.i(Constants.LLOG, "嘗試連接...");
                    sock = new Socket(ipAddress.getText().toString(), Constants.PORT_NUMBER);
                } catch (UnknownHostException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                if (sock == null)
                    Log.i(Constants.LLOG, "socket為空");
                else
                    Log.i(Constants.LLOG, "已連接");
            }
        };
        thread.start();
    }

    private void SendFile() {
        File myFile = new File(Constants.FILE_ADDR);
        byte[] mybytearray = new byte[(int) myFile.length()];
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            fis = new FileInputStream(myFile);
            bis = new BufferedInputStream(fis);

            //讀取檔案內容
            try {
                bytesRead = bis.read(mybytearray, 0, mybytearray.length);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //獲取輸出串流
            try {
                os = sock.getOutputStream();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

            //向輸出串流寫入資訊
            try {
                os.write(mybytearray, 0, mybytearray.length);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            status.setText("File was sent!");

            //回收記憶體
            try {
                os.flush();
                sock.close();
                bis.close();
                sock = null;
                status.setText("Socket is closed!");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
