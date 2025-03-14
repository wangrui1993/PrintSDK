package com.handset.printsdk;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handset.printsdk.base.BaseActivity;
import com.handset.printsdk.base.SPConfig;
import com.handset.sdktool.businessdatautil.BusinessDataUtil;
import com.handset.sdktool.data.DataUtil;
import com.handset.sdktool.dto.CompanyDTO;
import com.handset.sdktool.listener.GetAllCompanyListener;
import com.handset.sdktool.listener.InitCompanyListener;
import com.handset.sdktool.net.base.NetConfig;
import com.handset.sdktool.ui.AddCompanyActivity;
import com.handset.sdktool.util.SPUtil;
import com.handset.sdktool.util.SharedPreferenceUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @ClassName: PubuActivity
 * @author: wr
 * @date: 2022/11/10 18:34
 * @Description:作用描述
 */
public class LoginActivity extends BaseActivity {
    TextView tv_login;
    TextView tv_server_set;
    TextView tv_selectip;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        Log.e("sdfcdcdfc","000");
        tv_login = (TextView) findViewById(R.id.tv_login);
        tv_server_set = (TextView) findViewById(R.id.tv_server_set);
        tv_selectip = (TextView) findViewById(R.id.tv_selectip);
        Log.e("sdfcdcdfc","001");
//        String ip = (String) SharedPreferenceUtil.get(mContext, SPConfig.IP, "");
////        NetConfig.init(ip,"uid2","ceshi2" );
//        SPUtil.init(this);
//        BusinessDataUtil.getInstance().initFirstCompany("1", new InitCompanyListener() {
//            @Override
//            public void onSuccess(String companyId) {
//                Log.e("chushuan===","4");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.e("chushuan===","5");
//            }
//        });
        tv_selectip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(SetIpActivity.class);
            }
        });
        tv_server_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(AddCompanyActivity.class);
            }
        });
        Log.e("sdfcdcdfc","0");
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip = (String) SharedPreferenceUtil.get(LoginActivity.this, SPConfig.BASE_IP, "");
                String ip2 = (String) SharedPreferenceUtil.get(LoginActivity.this, SPConfig.IP, "");
                Log.e("sdfcdcdfc11",ip);
                Log.e("sdfcdcdfc12",ip2);
                if (ip == null || ip.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "请设置服务器IP和端口", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("sdfcdcdfc","2");
                    //如果ip下有同ip公司需先选择
                    NetConfig.init(ip2);
                    getCompanyList(ip2);
                    Log.e("sdfcdcdfc","3");
                    goActivity(MainActivity.class);
                }
            }
        });
    }

    @Override
    public void initEvent() {
    }

    Gson gson = new Gson();
    Type type = new TypeToken<Map<String, String>>() {
    }.getType();
    private List<CompanyDTO> companyDTOList = new ArrayList<>();
    private List<CompanyDTO> companyDTOListTwo = new ArrayList<>();//已选独立ip下的非独立ip列表

    private void getCompanyList(String ip2) {
        DataUtil.getInstance().getCompanyInfoDomain(new GetAllCompanyListener() {
            @Override
            public void onSuccess(List<CompanyDTO> listBaseBean) {
                companyDTOList.clear();
                companyDTOList.addAll(listBaseBean);
                Log.e("0cdczcd==", companyDTOList.size() + "");
                if (companyDTOList.size() > 0) {
                    for (CompanyDTO bean : companyDTOList) {
                        Log.e("33cdczcd==", bean.getIp() + "==" + ip2);
                        if (bean.getIp().contains(ip2) || ip2.contains(bean.getIp())) {
                            if (bean.getDomain() != null) {
                                Map<String, String> map = gson.fromJson(bean.getDomain(), type);
                                if (map.get("independence") != null && map.get("independence").equals("0")) {//独立
                                } else if (map.get("independence") != null && map.get("independence").equals("1")) {//非独立
                                    companyDTOListTwo.add(bean);
                                }
                            }
                        }
                    }
                }
                if (companyDTOListTwo.size() > 0) {
                    Log.e("3336cdczcd==", gson.toJson(companyDTOListTwo));
                    goActivity(SelectIPActivity.class);
                } else {
                    goActivity(MainActivity.class);
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e("2cdczcd==", NetConfig.IP + "==" + e.getMessage());
            }
        });
    }

}
