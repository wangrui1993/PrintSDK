package com.handset.sdktool.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.handset.sdktool.R;
import com.handset.sdktool.businessdatautil.BusinessDataUtil;
import com.handset.sdktool.data.DataUtil;
import com.handset.sdktool.dto.BusinessDTO;
import com.handset.sdktool.dto.CompanyAssociationDTO;
import com.handset.sdktool.dto.CompanyDTO;
import com.handset.sdktool.listener.AddCompanyListener;
import com.handset.sdktool.listener.CompanyAsListener;
import com.handset.sdktool.listener.GetAllBusinessListener;
import com.handset.sdktool.listener.GetBusinessServiceByCompanyIdListener;
import com.handset.sdktool.listener.OnRecycleViewItemClickListener;
import com.handset.sdktool.net.base.BaseBean;
import com.handset.sdktool.ui.adapter.BusinessAdapter;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;

import java.util.ArrayList;
import java.util.List;

public class CompanyAssociationBusinessActivity extends BaseActivity {
    private RecyclerView recycle_view_all, recycle_view;
    private List<BusinessDTO> mListBusinessAll = new ArrayList<>();
    private List<BusinessDTO> mListBusiness2 = new ArrayList<>();
    private TextView save, close,title;
    private BusinessAdapter mBusinessAdapterAll;
    private BusinessAdapter mBusinessAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_a_business);
        recycle_view = (RecyclerView) findViewById(R.id.recycle_view2);
        recycle_view_all = (RecyclerView) findViewById(R.id.recycle_view);
        save = (TextView) findViewById(R.id.save);
        close = (TextView) findViewById(R.id.close);
        title= (TextView) findViewById(R.id.title);

        title.setText(getIntent().getStringExtra("companyName"));

        mBusinessAdapterAll = new BusinessAdapter(this, mListBusinessAll);
        recycle_view_all.setLayoutManager(new GridLayoutManager(this, 3));
        mBusinessAdapterAll.setType(1);
        recycle_view_all.setAdapter(mBusinessAdapterAll);

        mBusinessAdapter2 = new BusinessAdapter(this, mListBusiness2);
        recycle_view.setLayoutManager(new GridLayoutManager(this, 3));
        mBusinessAdapter2.setType(2);
        recycle_view.setAdapter(mBusinessAdapter2);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new XPopup.Builder(CompanyAssociationBusinessActivity.this).asConfirm("确认保存", "是否确认保存", (OnConfirmListener) () -> {

//                    if(){
//
//                    }


                    List<String> listids = new ArrayList<>();
                    for (BusinessDTO businessDTO : mListBusiness2) {
                        listids.add(businessDTO.getServicetypeNo());
                    }
                    BusinessDataUtil.getInstance().updateCompanyTemplRel(new CompanyAssociationDTO(getIntent().getStringExtra("companyId"), listids
                    ), new CompanyAsListener() {
                        @Override
                        public void onSuccess(BaseBean listBaseBean) {
                            Toast.makeText(CompanyAssociationBusinessActivity.this, "关联成功", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onError(Throwable e) {

                        }
                    });
                }).show();
            }
        });
        recycle_view_all.addOnItemTouchListener(new OnRecycleViewItemClickListener(this, recycle_view_all) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, int i) {
                boolean isAdd = true;
                for (BusinessDTO businessDTO : mListBusiness2) {
                    if (businessDTO.getServicetypeNo().equals(mListBusinessAll.get(i).getServicetypeNo())) {
                        isAdd = false;
                    }
                }
                if (isAdd) {
                    mListBusiness2.add(mListBusinessAll.get(i));
                    mBusinessAdapter2.notifyDataSetChanged();
                }
            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder viewHolder, int i) {
            }
        });
        recycle_view.addOnItemTouchListener(new OnRecycleViewItemClickListener(this, recycle_view_all) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, int i) {
                mListBusiness2.remove(i);
                mBusinessAdapter2.notifyDataSetChanged();
            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder viewHolder, int i) {
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getList();
    }

    private void getList() {
        DataUtil.getInstance().getProfessionalWork(new GetAllBusinessListener() {
            @Override
            public void onSuccess(List<BusinessDTO> listBaseBean) {
                mListBusinessAll.clear();
                mListBusinessAll.addAll(listBaseBean);
                mBusinessAdapterAll.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(CompanyAssociationBusinessActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        DataUtil.getInstance().getBusinessServiceByCompanyId(getIntent().getStringExtra("companyId"), new GetBusinessServiceByCompanyIdListener() {
            @Override
            public void onSuccess(List<BusinessDTO> listBaseBean) {
                mListBusiness2.clear();
                mListBusiness2.addAll(listBaseBean);
                mBusinessAdapter2.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(CompanyAssociationBusinessActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}