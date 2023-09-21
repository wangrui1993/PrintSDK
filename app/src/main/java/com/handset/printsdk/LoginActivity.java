package com.handset.printsdk;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.handset.printsdk.base.BaseActivity;
import com.handset.printsdk.base.SPConfig;
import com.handset.sdktool.net.base.NetConfig;
import com.handset.sdktool.util.SharedPreferenceUtil;

import butterknife.BindView;

/**
 * @ClassName: PubuActivity
 * @author: wr
 * @date: 2022/11/10 18:34
 * @Description:作用描述
 */
public class LoginActivity extends BaseActivity {
    @BindView(R.id.tv_login)
    TextView tv_login;
    @BindView(R.id.tv_server_set)
    TextView tv_server_set;
    @BindView(R.id.tv_selectip)
    TextView  tv_selectip;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }



    @Override
    public void initView(Bundle savedInstanceState) {
        tv_selectip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(SetIpActivity.class);
            }
        });
        tv_server_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(SetIpActivity.class);
            }
        });
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip = (String) SharedPreferenceUtil.get(LoginActivity.this, SPConfig.BASE_IP, "");
                String ip2 = (String) SharedPreferenceUtil.get(LoginActivity.this, SPConfig.IP, "");
                if (ip == null || ip.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "请设置服务器IP和端口", Toast.LENGTH_SHORT).show();
                } else {
                    NetConfig.init(ip2);
                    goActivity(MainActivity.class);
                }
            }
        });
    }

    @Override
    public void initEvent() {
    }


}
