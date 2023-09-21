package com.handset.printsdk;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.handset.printsdk.base.BaseActivity;
import com.handset.printsdk.base.Config;
import com.handset.printsdk.base.SPConfig;
import com.handset.sdktool.bean.IpBean;
import com.handset.sdktool.net.base.NetConfig;
import com.handset.sdktool.ui.adapter.IpAdapter;
import com.handset.sdktool.util.SharedPreferenceUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @ClassName: SetIpActivity
 * @author: wr
 * @date: 2022/11/10 18:34
 * @Description:作用描述
 */
public class SetIpActivity extends BaseActivity {
    @BindView(R.id.tv_commit)
    TextView tv_commit;
    @BindView(R.id.et_input_ip_pda)
    EditText et_input_ip_pda;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.recycle_view)
    ListView recycle_view;
    IpAdapter ipAdapter;
    private List<IpBean> listIP=new ArrayList<>();
    @Override
    public int getLayoutId() {
        return R.layout.activity_config_ip;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        String ip = (String) SharedPreferenceUtil.get(mContext, SPConfig.IP, "");
        et_input_ip_pda.setText(ip);
        tv_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_input_ip_pda.getText().toString().length() > 0) {
                    savePdaAPI(et_input_ip_pda.getText().toString());
                    Log.e("33cdczcd==", et_input_ip_pda.getText().toString());
                    NetConfig.init(et_input_ip_pda.getText().toString());
                    Toast.makeText(mContext, "保存成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(mContext, "请输入IP和端口", Toast.LENGTH_SHORT).show();
                }
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ipAdapter = new IpAdapter( listIP,this);
//        recycle_view.set(new LinearLayoutManager(this));
        recycle_view.setAdapter(ipAdapter);
        listIP.add(new IpBean("192.168.31.20","王睿"));
        listIP.add(new IpBean("192.168.31.98","惠东"));
        ipAdapter.notifyDataSetChanged();
    }

    @Override
    public void initEvent() {
    }


    public void savePdaAPI(String ip) {
        Log.e("0cdczcd==", ip);
        Log.e("0cdczcd==", ip);
        Config.BASE_IP = "http://" + ip;
        Config.IP = ip;
        Log.e("1cdczcd==", Config.BASE_IP);
        SharedPreferenceUtil.put(this, SPConfig.BASE_IP, Config.BASE_IP);
        SharedPreferenceUtil.put(this, SPConfig.IP, Config.IP);
    }
}
