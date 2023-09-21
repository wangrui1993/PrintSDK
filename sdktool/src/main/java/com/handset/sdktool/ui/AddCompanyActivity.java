package com.handset.sdktool.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.handset.sdktool.R;
import com.handset.sdktool.data.DataUtil;
import com.handset.sdktool.dto.CompanyDTO;
import com.handset.sdktool.dto.DelCompanyDTO;
import com.handset.sdktool.listener.AddCompanyListener;
import com.handset.sdktool.listener.DeleteCompanyListener;
import com.handset.sdktool.listener.GetAllCompanyListener;
import com.handset.sdktool.listener.OnRecycleViewItemClickListener;
import com.handset.sdktool.net.base.BaseBean;
import com.handset.sdktool.ui.adapter.CompanyAdapter;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.interfaces.OnSelectListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddCompanyActivity extends BaseActivity {
    private RecyclerView recycle_view;
    private ImageView tv_nodata;
    private EditText et_ip;
    private EditText et_name;
    private List<CompanyDTO> mListBusiness = new ArrayList<>();
    private TextView add;
    private TextView save, tv_independence, tv_unindependence;
    private CompanyAdapter mBusinessAdapter;
    private Map<String, String> companyInfo = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        companyInfo.put("independence","0");//0-独立 1-非独立
        setContentView(R.layout.activity_set_company);
        mBusinessAdapter = new CompanyAdapter(this, mListBusiness);
        recycle_view = (RecyclerView) findViewById(R.id.recycle_view);
        tv_nodata = (ImageView) findViewById(R.id.tv_nodata);
        add = (TextView) findViewById(R.id.add);
        save = (TextView) findViewById(R.id.save);
        et_ip = (EditText) findViewById(R.id.et_ip);
        et_name = (EditText) findViewById(R.id.et_name);

        tv_independence = (TextView) findViewById(R.id.tv_independence);
        tv_unindependence = (TextView) findViewById(R.id.tv_unindependence);

        recycle_view.setLayoutManager(new LinearLayoutManager(this));
        recycle_view.setAdapter(mBusinessAdapter);
        tv_independence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_independence.setTextColor(getResources().getColor(R.color.theme_200));
                tv_unindependence.setTextColor(getResources().getColor(R.color.theme_bbbbbb));
                companyInfo.put("independence","0");
            }
        });
        tv_unindependence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_unindependence.setTextColor(getResources().getColor(R.color.theme_200));
                tv_independence.setTextColor(getResources().getColor(R.color.theme_bbbbbb));
                companyInfo.put("independence","1");
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("companyInfo=",new Gson().toJson(companyInfo));
                if(et_ip.getText().toString().trim().length()==0){
                    Toast.makeText(AddCompanyActivity.this,"请填写IP",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(et_name.getText().toString().trim().length()==0){
                    Toast.makeText(AddCompanyActivity.this,"请填写公司名称",Toast.LENGTH_SHORT).show();
                    return;
                }
                new XPopup.Builder(AddCompanyActivity.this).asConfirm("确认添加", "是否添加该公司", (OnConfirmListener) () -> {
                    CompanyDTO companyDTO = new CompanyDTO("",
                            et_name.getText().toString().trim(), et_ip.getText().toString().trim(), new Gson().toJson(companyInfo));
                    DataUtil.getInstance().saveCompanyInfoDomain(companyDTO
                            , new AddCompanyListener() {
                                @Override
                                public void onSuccess(BaseBean listBaseBean) {
                                    mListBusiness.add(companyDTO);
                                    mBusinessAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Toast.makeText(AddCompanyActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });


                }).show();
            }
        });
        recycle_view.addOnItemTouchListener(new OnRecycleViewItemClickListener(this, recycle_view) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, int i) {
                Intent intent = new Intent(AddCompanyActivity.this, SynchronizeBusinessActivity.class);
                intent.putExtra("companyId", mListBusiness.get(i).getId());
                intent.putExtra("ip", mListBusiness.get(i).getIp());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder viewHolder, int i) {
                String[] strings = new String[2];
                strings[0] = "编辑";
                strings[1] = "删除";
                new XPopup.Builder(AddCompanyActivity.this)
                        .asCenterList("操作", strings, new OnSelectListener() {
                            @Override
                            public void onSelect(int position, String text) {
                                if (position == 0) {
                                    Intent intent = new Intent(AddCompanyActivity.this, CompanyAssociationBusinessActivity.class);
                                    intent.putExtra("companyName", mListBusiness.get(i).getCompanyName());
                                    intent.putExtra("companyId", mListBusiness.get(i).getId());
                                    intent.putExtra("ip", mListBusiness.get(i).getIp());
                                    startActivity(intent);
                                } else {
                                    List<String> ids = new ArrayList<>();
                                    ids.add(mListBusiness.get(i).getId());
                                    DataUtil.getInstance().deleteCompanyInfoDomain(new DelCompanyDTO(ids)
                                            , new DeleteCompanyListener() {
                                                @Override
                                                public void onSuccess(BaseBean listBaseBean) {
                                                    getList();
                                                }

                                                @Override
                                                public void onError(Throwable e) {
                                                    Toast.makeText(AddCompanyActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            }
                        }).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getList();
    }

    private void getList() {
        DataUtil.getInstance().getCompanyInfoDomain(new GetAllCompanyListener() {
            @Override
            public void onSuccess(List<CompanyDTO> listBaseBean) {
                mListBusiness.clear();
                mListBusiness.addAll(listBaseBean);
                mBusinessAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(AddCompanyActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}