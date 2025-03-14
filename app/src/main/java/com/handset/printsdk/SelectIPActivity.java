package com.handset.printsdk;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handset.printsdk.base.BaseActivity;
import com.handset.printsdk.base.Config;
import com.handset.printsdk.base.SPConfig;
import com.handset.sdktool.data.DataUtil;
import com.handset.sdktool.dto.CompanyDTO;
import com.handset.sdktool.listener.GetAllCompanyListener;
import com.handset.sdktool.net.base.NetConfig;
import com.handset.sdktool.ui.adapter.IpAdapter;
import com.handset.sdktool.ui.adapter.UnindependentAdapter;
import com.handset.sdktool.util.SPUtil;
import com.handset.sdktool.util.SharedPreferenceUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



/**
 * @ClassName: SetIpActivity
 * @author: wr
 * @date: 2022/11/10 18:34
 * @Description:作用描述
 */
public class SelectIPActivity extends BaseActivity {
    EditText et_input_ip_pda;
    ImageView img;
    ListView recycle_view;

    IpAdapter ipAdapter;
    private List<CompanyDTO> companyDTOList = new ArrayList<>();
    Gson gson = new Gson();
    Type type = new TypeToken<Map<String, String>>() {
    }.getType();


    @Override
    public int getLayoutId() {
        return R.layout.activity_select_ip;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        et_input_ip_pda = (EditText) findViewById(R.id.et_input_ip_pda);
        img = (ImageView) findViewById(R.id.img);
        recycle_view = (ListView) findViewById(R.id.recycle_view);

        SPUtil.init(this);
        String ip = (String) SharedPreferenceUtil.get(mContext, SPConfig.IP, "");
        et_input_ip_pda.setText(ip);
        if (ip != null) {
            NetConfig.init(et_input_ip_pda.getText().toString());
        }
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ipAdapter = new IpAdapter(companyDTOList, this, new IpAdapter.OnItemClick() {
            @Override
            public void onItemClick(CompanyDTO companydto, int index) {
                et_input_ip_pda.setText(companydto.getIp());
                savePdaAPI(companydto.getIp());
                goActivity(MainActivity.class);
                finish();
            }
        });

        recycle_view.setAdapter(ipAdapter);
        ipAdapter.notifyDataSetChanged();
        initData();


    }

    private void initData() {
        getCompanyList();
    }


    @Override
    public void initEvent() {
    }

    private void getCompanyList() {
        DataUtil.getInstance().getCompanyInfoDomain(new GetAllCompanyListener() {
            @Override
            public void onSuccess(List<CompanyDTO> listBaseBean) {
                String ip2 = (String) SharedPreferenceUtil.get(SelectIPActivity.this, SPConfig.IP, "");
                if (listBaseBean.size() > 0) {
                    for (CompanyDTO bean : listBaseBean) {
                        Log.e("33cdczcd==", bean.getIp() + "==" + ip2);
                        if (bean.getIp().contains(ip2) || ip2.contains(bean.getIp())) {
                            if (bean.getDomain() != null) {
                                Map<String, String> map = gson.fromJson(bean.getDomain(), type);
                                if (map.get("independence") != null && map.get("independence").equals("0")) {//独立
                                } else if (map.get("independence") != null && map.get("independence").equals("1")) {//非独立
                                    companyDTOList.add(bean);
                                }
                            }
                        }
                    }
                }
                ipAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable e) {
                Log.e("2cdczcd==", NetConfig.IP + "==" + e.getMessage());
            }
        });
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
