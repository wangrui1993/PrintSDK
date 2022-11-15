package com.handset.printsdk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.handset.sdktool.data.DataUtil;
import com.handset.sdktool.dto.PrinterDTO;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<PrinterDTO> list =  DataUtil.getInstance().getPrits(this);
        Log.e("sdfdfd===",list.toString());
    }
}