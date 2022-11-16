package com.handset.printsdk.base;

import android.os.Bundle;

/**
 * @ClassName: BaseViewInterface
 * @Author: WR
 * @CreateDate: 2020/9/18 8:45
 * @Description:
 */
public interface BaseViewInterface {

    /**
     * description: 绑定布局
     */
    int getLayoutId();

    /**
     * description: 初始化控件
     */
    void initView(Bundle savedInstanceState);

    /**
     * description: 绑定事件
     */
    void initEvent();
}
