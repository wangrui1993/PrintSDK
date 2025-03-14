package com.handset.printsdk;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handset.printsdk.base.BaseActivity;
import com.handset.printsdk.base.Config;
import com.handset.printsdk.base.SPConfig;
import com.handset.sdktool.bean.IpBean;
import com.handset.sdktool.data.DataUtil;
import com.handset.sdktool.dto.CompanyDTO;
import com.handset.sdktool.dto.ElementDTO;
import com.handset.sdktool.listener.GetAllCompanyListener;
import com.handset.sdktool.net.base.NetConfig;
import com.handset.sdktool.ui.AddCompanyActivity;
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
public class SetIpActivity extends BaseActivity {
    TextView tv_commit;
//    @BindView(R.id.tv_new_service)
//    TextView  tv_new_service;
    EditText et_input_ip_pda;
    ImageView img;
    ListView recycle_view;
    TextView tv_select;
    GridView gv;

    IpAdapter ipAdapter;
    UnindependentAdapter mUnindependentAdapter;
    private CompanyDTO selectCompany=null;
    private List<CompanyDTO> listIP = new ArrayList<>();
    private List<CompanyDTO> companyDTOList = new ArrayList<>();
    private List<CompanyDTO> companyDTOListTwo = new ArrayList<>();//已选独立ip下的非独立ip列表
    Gson gson = new Gson();
    Type type = new TypeToken<Map<String, String>>() {
    }.getType();


    @Override
    public int getLayoutId() {
        return R.layout.activity_config_ip;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
          tv_commit= (TextView) findViewById(R.id.tv_commit);
          et_input_ip_pda= (EditText) findViewById(R.id.et_input_ip_pda);
          img= (ImageView) findViewById(R.id.img);
          recycle_view= (ListView) findViewById(R.id.recycle_view);
          tv_select= (TextView) findViewById(R.id.tv_select);
          gv= (GridView) findViewById(R.id.gv);


        SPUtil.init(this);
        String ip = (String) SharedPreferenceUtil.get(mContext, SPConfig.IP, "");
        et_input_ip_pda.setText(ip);
        if (ip != null) {
            NetConfig.init(et_input_ip_pda.getText().toString());
        }
        tv_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (companyDTOList.size() > 0) {
                    //把公司名和输入内容存起来（如果不重复）
                    boolean isContentInCompany = false;
                    for (CompanyDTO bean : companyDTOList) {
                        Log.e("313cdczcd==", bean.getIp());
                        if (bean.getIp().equals(et_input_ip_pda.getText().toString())) {
                            isContentInCompany = true;
                        }
                    }
                    if (!isContentInCompany && ip != null) {
                        savePdaAPI(et_input_ip_pda.getText().toString());
                        NetConfig.init(et_input_ip_pda.getText().toString());
                        finish();
                        return;
                    }
                }
                if (et_input_ip_pda.getText().toString().length() > 0) {
                    savePdaAPI(et_input_ip_pda.getText().toString());
                    NetConfig.init(et_input_ip_pda.getText().toString());

                    //如果公司

                    CompanyDTO company = null;
                    //根据输入内容查公司名
                    if (companyDTOList.size() > 0) {
                        for (CompanyDTO bean : companyDTOList) {
                            Log.e("33cdczcd==", bean.getIp());
                            if (bean.getIp().contains(et_input_ip_pda.getText().toString()) || et_input_ip_pda.getText().toString().contains(bean.getIp())) {
                                if (bean.getDomain() != null) {
                                    Map<String, String> map = gson.fromJson(bean.getDomain(), type);
                                    if (map.get("independence") != null && map.get("independence").equals("0")) {//独立
                                        company = bean;
                                    } else if (map.get("independence") != null && map.get("independence").equals("1")) {//非独立
                                        companyDTOListTwo.add(bean);
                                    }
                                }
                            }
                        }
                    } else {
                        company = new CompanyDTO("", et_input_ip_pda.getText().toString().trim(), "{}");
                    }
//                    if (companyDTOListTwo.size() > 0&&selectCompany==null) {
//                        tv_select.setVisibility(View.VISIBLE);
//                        return;
//                    }
//                    tv_select.setVisibility(View.GONE);

                    boolean isContent = false;
                    for (CompanyDTO bean : listIP) {
                        if (bean.getIp().equals(et_input_ip_pda.getText().toString())) {
                            isContent = true;
                        }
                    }
                    if (!isContent) {
                        if (company == null) {
                            company = new CompanyDTO("", et_input_ip_pda.getText().toString().trim(), "{}");
                        }
                        listIP.add(company);
                        SPUtil.saveParam("companyIps", new Gson().toJson(listIP));
                    }


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
        tv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ipAdapter = new IpAdapter(listIP, this, new IpAdapter.OnItemClick() {
            @Override
            public void onItemClick(CompanyDTO companydto, int index) {
                et_input_ip_pda.setText(companydto.getIp());
//                setUnindependenceList();
            }
        });
        mUnindependentAdapter = new UnindependentAdapter(companyDTOListTwo, this, new UnindependentAdapter.OnItemClick() {
            @Override
            public void onItemClick(CompanyDTO companydto, int index) {
                selectCompany=companydto;
                tv_select.setText(companydto.getCompanyName());
            }
        });
        recycle_view.setAdapter(ipAdapter);
        ipAdapter.notifyDataSetChanged();
        gv.setAdapter(mUnindependentAdapter);
        initData();

        getCompanyList();

    }

    private void initData() {
        if (SPUtil.getStringParam("companyIps") == null) {
            return;
        }
        String companys = SPUtil.getStringParam("companyIps");
        if (companys == null) {
            return;
        } else {
            List<CompanyDTO> list = new Gson().fromJson(companys,
                    new TypeToken<List<CompanyDTO>>() {
                    }.getType());
            listIP.clear();
            listIP.addAll(list);
            ipAdapter.notifyDataSetChanged();
        }
    }

    private void setUnindependenceList() {
        companyDTOListTwo.clear();
        for (CompanyDTO bean : companyDTOList) {
            Log.e("33cdczcd==", bean.getIp());
            if (bean.getIp().contains(et_input_ip_pda.getText().toString()) || et_input_ip_pda.getText().toString().contains(bean.getIp())) {
                if (bean.getDomain() != null) {
                    Map<String, String> map = gson.fromJson(bean.getDomain(), type);
                    if (map.get("independence") != null && map.get("independence").equals("1")) {//独立
                        companyDTOListTwo.add(bean);
                    }
                }
            }
        }
        mUnindependentAdapter.notifyDataSetChanged();
        if (companyDTOListTwo.size() > 0) {
            tv_select.setVisibility(View.VISIBLE);
            return;
        }
        tv_select.setVisibility(View.GONE);

    }

    @Override
    public void initEvent() {
    }

    private void getCompanyList() {
        DataUtil.getInstance().getCompanyInfoDomain(new GetAllCompanyListener() {
            @Override
            public void onSuccess(List<CompanyDTO> listBaseBean) {

                companyDTOList.clear();
                companyDTOList.addAll(listBaseBean);
                Log.e("0cdczcd==", companyDTOList.size() + "");
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
        Config.BASE_IP = "https://" + ip;
        Config.IP = ip;
        Log.e("1cdczcd==", Config.BASE_IP);
        SharedPreferenceUtil.put(this, SPConfig.BASE_IP, Config.BASE_IP);
        SharedPreferenceUtil.put(this, SPConfig.IP, Config.IP);
    }
}
