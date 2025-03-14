package com.handset.sdktool.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.handset.sdktool.R;
import com.handset.sdktool.businessdatautil.BusinessDataUtil;
import com.handset.sdktool.data.DataUtil;
import com.handset.sdktool.dto.BusinessDTO;
import com.handset.sdktool.dto.CompanyAssociationDTO;
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

public class AllAssociationBusinessActivity extends BaseActivity {
    private RecyclerView recycle_view_all;
    private List<BusinessDTO> mListBusinessAll = new ArrayList<>();
    private TextView  close,title;
    private BusinessAdapter mBusinessAdapterAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_all_business);
        recycle_view_all = (RecyclerView) findViewById(R.id.recycle_view);
        close = (TextView) findViewById(R.id.close);
        title= (TextView) findViewById(R.id.title);

        mBusinessAdapterAll = new BusinessAdapter(this, mListBusinessAll);
        recycle_view_all.setLayoutManager(new GridLayoutManager(this, 3));
        mBusinessAdapterAll.setType(1);
        recycle_view_all.setAdapter(mBusinessAdapterAll);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        recycle_view_all.addOnItemTouchListener(new OnRecycleViewItemClickListener(this, recycle_view_all) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, int i) {
                Intent intent = new Intent(AllAssociationBusinessActivity.this, EditBusinessActivity.class);
                intent.putExtra("title", mListBusinessAll.get(i).getServicetype());
                Log.e("pageh==sss=", mListBusinessAll.get(i).toString());
                intent.putExtra("servicetypeNo", mListBusinessAll.get(i).getServicetypeNo());
                intent.putExtra("servicetype", mListBusinessAll.get(i).getServicetype());
                startActivity(intent);
//                boolean isAdd = true;
//                for (BusinessDTO businessDTO : mListBusiness2) {
//                    if (businessDTO.getServicetypeNo().equals(mListBusinessAll.get(i).getServicetypeNo())) {
//                        isAdd = false;
//                    }
//                }
//                if (isAdd) {
//                    mListBusiness2.add(mListBusinessAll.get(i));
//                    mBusinessAdapter2.notifyDataSetChanged();
//                }
            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder viewHolder, int i) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getList();
    }

    @Override
    protected void onStart() {
        super.onStart();
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
                Toast.makeText(AllAssociationBusinessActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}