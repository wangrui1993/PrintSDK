package com.handset.sdktool.printutil;

import android.os.Build;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handset.sdktool.bean.PrintPaperBean;
import com.handset.sdktool.data.BusinessData;
import com.handset.sdktool.dto.ModleDTO;
import com.handset.sdktool.event.Label;
import com.handset.sdktool.event.LabelBoard;
import com.handset.sdktool.util.LabelBoardAnalysisUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: aaa
 * @author: wr
 * @date: 2022/12/3 19:38
 * @Description:作用描述
 */
public class aaa {
    public static void paixu(List<ModleDTO.ComponentsBean> l) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            l.sort(Comparator.comparing(ModleDTO.ComponentsBean::getCoordY));
        }

    }

    private static Gson gson = new Gson();

    public static void setElement(List<ModleDTO.ComponentsBean> l) {
        for (ModleDTO.ComponentsBean componentsBean : l) {
            if (componentsBean.getComponentTypeId().equals("5")) {
                LabelBoard lb = new Gson().fromJson(componentsBean.getComponentContent(), LabelBoard.class);
                List<ModleDTO.ComponentsBean> list = LabelBoardAnalysisUtil.setDataToModle(lb, componentsBean);
                componentsBean.setChlieComponentList(list);
                ModleDTO.ComponentsBean.ElementsBean elementsBean = new ModleDTO.ComponentsBean.ElementsBean();//这两行代码 是因为模板的element存不上 我存在了LabelBoard中
                elementsBean.setElementCode(lb.getElementCode());
                componentsBean.setElement(elementsBean);

            }
        }
    }

    public static void creatNenData(ModleDTO.ComponentsBean componentsBean, ModleDTO.ComponentsBean componentsBean_ku_item, List<ModleDTO.ComponentsBean> list) {
        List<ModleDTO.ComponentsBean> businessElementBeanList = gson.fromJson(gson.toJson(list),
                new TypeToken<List<ModleDTO.ComponentsBean>>() {
                }.getType());
        Log.e("1sssccc===", businessElementBeanList.size() + "");
        componentsBean_ku_item.setId(componentsBean.getId());
        componentsBean_ku_item.setComponentContent(componentsBean.getComponentContent());
        componentsBean_ku_item.setComponentTypeId(componentsBean.getComponentTypeId());
        componentsBean_ku_item.setCoordX(componentsBean.getCoordX());
        componentsBean_ku_item.setAlignType(componentsBean.getAlignType());
        componentsBean_ku_item.setAboveComponentId(componentsBean.getAboveComponentId());
    }

    public static void creatNenData2(ModleDTO.ComponentsBean componentsBean, ModleDTO.ComponentsBean componentsBean_ku_item, List<ModleDTO.ComponentsBean> list) {
        List<ModleDTO.ComponentsBean> businessElementBeanList = gson.fromJson(gson.toJson(list),
                new TypeToken<List<ModleDTO.ComponentsBean>>() {
                }.getType());
        Log.e("2sssccc===", businessElementBeanList.size() + "");
//        componentsBean_ku_item.setChlieComponentLists(businessElementBeanList);
        componentsBean_ku_item.setId(componentsBean.getId());
        componentsBean_ku_item.setComponentContent(componentsBean.getComponentContent());
        componentsBean_ku_item.setComponentTypeId(componentsBean.getComponentTypeId());
        componentsBean_ku_item.setCoordX(componentsBean.getCoordX());
        componentsBean_ku_item.setAlignType(componentsBean.getAlignType());
        componentsBean_ku_item.setAboveComponentId(componentsBean.getAboveComponentId());
    }

    private static void creatNenData3(ModleDTO.ComponentsBean componentsBean, ModleDTO.ComponentsBean componentsBean_ku_item, List<ModleDTO.ComponentsBean> list) {
        List<ModleDTO.ComponentsBean> businessElementBeanList = gson.fromJson(gson.toJson(list),
                new TypeToken<List<ModleDTO.ComponentsBean>>() {
                }.getType());
        Log.e("3sssccc===", businessElementBeanList.size() + "");
        Log.e("sdfsdfsdf===", gson.toJson(businessElementBeanList));
//        componentsBean_ku_item.setChlieComponentLists(businessElementBeanList);
        componentsBean_ku_item.setId(componentsBean.getId());
        componentsBean_ku_item.setComponentContent(componentsBean.getComponentContent());
        componentsBean_ku_item.setComponentTypeId(componentsBean.getComponentTypeId());
        componentsBean_ku_item.setCoordX(componentsBean.getCoordX());
        componentsBean_ku_item.setAlignType(componentsBean.getAlignType());
        componentsBean_ku_item.setAboveComponentId(componentsBean.getAboveComponentId());
    }

    static int leiji = 0;

    /**
     * 根据嵌套关系重新给组件Y轴赋值
     *
     * @param l           组件集合
     * @param map_in      业务数据用于 根据实际数量计算高度
     * @param base_height 层级 最外层的Y轴往往很大 不能直接用于惩罚计算 只能作为基础高度最后加上
     * @return 返回的是item这一项有多高
     */
    public static int calculationHeight1(List<ModleDTO.ComponentsBean> l, Map<String, Object> map_in, int base_height) {

        // 先把所有同级item的高都算出来 赋值给下个item的Y 算出同级item总高 循环item的列表所在模板 给模板判断y轴给内其他组件重新赋Y值 给外层更新高度  赋值到Height上


        paixu(l);
        setElement(l);
        int ku_Y = -1;
        int item_totle_h = 0;
        int ku_item_h_inmodle = 0;
        int ku_list_height = 0;//库列表高
        //循环中累计的库item的高，就是当前库上面的 库的总高
        //整体组件（其中的一项 是一个component   如：天应泰发货通知单   时间   库列表）
        for (int j = 0; j < l.size(); j++) {
            ModleDTO.ComponentsBean componentsBean = l.get(j);
            ku_Y = componentsBean.getCoordY();
            if (componentsBean.getComponentTypeId().equals("5")) {
                ku_item_h_inmodle = componentsBean.getComponentHeight();
                //模板（其中的一项 是一个component   如：仓库名   规格   合同列表）
                List<ModleDTO.ComponentsBean> list = componentsBean.getChlieComponentList();
                //模板列表的数据列表
                List<Map<String, Object>> list_map_ku = (List<Map<String, Object>>) map_in.get(componentsBean.getElement().getElementCode());
                if (list_map_ku != null) {
                    componentsBean.getChlieComponentLists().clear();
                    for (int i = 0; i < list_map_ku.size(); i++) {
                        Map<String, Object> map = list_map_ku.get(i);//代表其中的一个仓库
                        ModleDTO.ComponentsBean componentsBean_ku_item = new ModleDTO.ComponentsBean();
                        componentsBean_ku_item.setComponentHeight(componentsBean.getComponentHeight());
                        componentsBean_ku_item.setElement(componentsBean.getElement());
                        componentsBean_ku_item.setId(componentsBean.getId());
                        //设置数据
                        creatNenData(componentsBean, componentsBean_ku_item, list);
                        componentsBean_ku_item.setCoordY(0);

                        //=====================================================================================================合同


                        if (i == 0) {
                            //coordY+外层item上方的item累计的高
                            componentsBean_ku_item.setCoordY(componentsBean_ku_item.getCoordY() + leiji);
                            //本高+列表高-一项列表的高 //TODO 这里只考虑了一个列表，没考虑多个同级别列表 需要优化
                            componentsBean_ku_item.setComponentHeight(componentsBean_ku_item.getComponentHeight() +setYH1(list,map ,ku_list_height  ,componentsBean_ku_item));
                        } else {
                            //上一个的Y+上一个的高
                            componentsBean_ku_item.setCoordY(componentsBean.getChlieComponentLists().get(i - 1).getCoordY() + componentsBean.getChlieComponentLists().get(i - 1).getComponentHeight());
                            //本高+列表高-一项列表的高 //TODO 这里只考虑了一个列表，没考虑多个同级别列表 需要优化
                            componentsBean_ku_item.setComponentHeight(componentsBean_ku_item.getComponentHeight() + setYH1(list,map ,ku_list_height  ,componentsBean_ku_item));
                        }
                        //此列表总高 最后一个item的高加最后一个item 的Y轴
                        ku_list_height = ku_list_height + componentsBean_ku_item.getComponentHeight();
                        Log.e("1级检验-H=", "===" + componentsBean_ku_item.getComponentHeight());
                        Log.e("1级检验-Y=", "===" + componentsBean_ku_item.getCoordY());

                        //TODO 这里应该给仓库bean完善组件信息
                        componentsBean.getChlieComponentLists().add(componentsBean_ku_item);
                        Log.e("1whatpp=", componentsBean.getChlieComponentLists().size() + "");
                    }

                }
            } else {
                //是组件下面的 就把列表的高加上
                if (componentsBean.getCoordY() > ku_Y && ku_Y > 0) {
                    componentsBean.setCoordY(componentsBean.getCoordY() + ku_list_height - ku_item_h_inmodle);
                }
                componentsBean.setComponentContent(getShowContent(componentsBean, map_in, 0));
            }
        }
        kanshuju(l);
//        kanshuju1(l, map_in);
        return item_totle_h;

    }

    private static int setYH1(List<ModleDTO.ComponentsBean> list, Map<String, Object> map, int above_list_h, ModleDTO.ComponentsBean componentsBean_ku_item){
        setElement(list);
        paixu(list);
        //合同模板Y
        int hetong_Y = -1;
        //合同列表的高
        int hetong_list_h = 0;
        Log.e("00hetong_list_hkk=", hetong_list_h + "===" + map.get("stockName"));
        //合同列表在模板中的高
        int hetong_item_h_inmodle = 0;
        for (int j2 = 0; j2 < list.size(); j2++) {
            ModleDTO.ComponentsBean componentsBean2 = list.get(j2);
            if(componentsBean2.getComponentTypeId().equals("5")){
                componentsBean2.setComponentHeight(600);//TODO 这里模板传入的数据有问题，需要改高
                componentsBean2.setCoordY(140);//TODO 这里模板传入的数据有问题，需要改Y
            }else {
                if(componentsBean2.getElement()==null||componentsBean2.getElement().getElementCode()==null){
                    Log.e("kanjieguoba===", "===" + componentsBean2.getCoordY()+ "==="+componentsBean2.getComponentContent());
                }else {
                    Log.e("kanjieguoba===", "===" + componentsBean2.getCoordY()+"==="+componentsBean2.getElement().getElementCode()+"==="+componentsBean2.getComponentContent());
                }
            }

            if (componentsBean2.getComponentTypeId().equals("5")) {
                hetong_item_h_inmodle = componentsBean2.getComponentHeight();
                hetong_Y = componentsBean2.getCoordY();
                List<ModleDTO.ComponentsBean> list_hetong = componentsBean2.getChlieComponentList();
                List<Map<String, Object>> listmap_hetong = (List<Map<String, Object>>) map.get(componentsBean2.getElement().getElementCode());
                if (listmap_hetong != null) {
                    componentsBean2.getChlieComponentLists().clear();
                    for (int i2 = 0; i2 < listmap_hetong.size(); i2++) {
                        Map<String, Object> map_hetong = listmap_hetong.get(i2);//代表其中的一个合同
                        ModleDTO.ComponentsBean componentsBean_hetong_item = new ModleDTO.ComponentsBean();
                        componentsBean_hetong_item.setComponentHeight(componentsBean2.getComponentHeight());
                        componentsBean_hetong_item.setElement(componentsBean2.getElement());
                        componentsBean_hetong_item.setId(componentsBean2.getId());
                        //设置数据
                        creatNenData2(componentsBean2, componentsBean_hetong_item, list_hetong);
                        componentsBean_hetong_item.setCoordY(hetong_Y);

                        if (i2 == 0) {
                            //coordY+外层item上方的item累计的高
                            componentsBean_hetong_item.setCoordY(componentsBean_hetong_item.getCoordY() + above_list_h);
                            //本高+列表高-一项列表的高 //TODO 这里只考虑了一个列表，没考虑多个同级别列表 需要优化
                            componentsBean_hetong_item.setComponentHeight(componentsBean_hetong_item.getComponentHeight() + setYH1(list_hetong,map_hetong,hetong_list_h + above_list_h,componentsBean_hetong_item));
                        } else {
                            //上一个的Y+上一个的高+外层item上方的item累计的高
                            componentsBean_hetong_item.setCoordY(componentsBean_hetong_item.getCoordY() + above_list_h + hetong_list_h);
                            //本高+列表高-一项列表的高 //TODO 这里只考虑了一个列表，没考虑多个同级别列表 需要优化
                            componentsBean_hetong_item.setComponentHeight(componentsBean_hetong_item.getComponentHeight() + setYH1(list_hetong,map_hetong,hetong_list_h + above_list_h,componentsBean_hetong_item));

                        }
                        Log.e("2级检验-H=", "===" + componentsBean_hetong_item.getComponentHeight());
                        Log.e("2级检验-Y=", "===" + componentsBean_hetong_item.getCoordY());
                        hetong_list_h = hetong_list_h + componentsBean_hetong_item.getComponentHeight();

                        //TODO 这里应该给仓库bean完善组件信息
                        componentsBean_ku_item.getChlieComponentLists().add(componentsBean_hetong_item);
                        componentsBean2.getChlieComponentLists().add(componentsBean_hetong_item);
                        Log.e("2whatpp=", componentsBean2.getChlieComponentLists().size() + "");
                    }
                }
            } else {

                //是组件下面的 就把列表的高加上
//                if (componentsBean2.getCoordY() > hetong_Y && hetong_Y > 0) {
                    componentsBean2.setCoordY(componentsBean2.getCoordY() +  above_list_h + hetong_list_h);
//                }
                Log.e("scxxxc===", componentsBean2.getCoordY() +"===" + above_list_h+ "==="+hetong_list_h);


                componentsBean2.setComponentContent(getShowContent(componentsBean2, map, 0));
                componentsBean_ku_item.getChlieComponentLists().add(componentsBean2);


            }
        }
        return hetong_list_h - hetong_item_h_inmodle;
    }








//    public static int calculationHeight1(List<ModleDTO.ComponentsBean> l, Map<String, Object> map_in, int base_height) {
//
//        // 先把所有同级item的高都算出来 赋值给下个item的Y 算出同级item总高 循环item的列表所在模板 给模板判断y轴给内其他组件重新赋Y值 给外层更新高度  赋值到Height上
//
//
//        paixu(l);
//        setElement(l);
//        int ku_Y = -1;
//        int item_totle_h = 0;
//        int ku_item_h_inmodle = 0;
//        int ku_list_height = 0;//库列表高
//        //循环中累计的库item的高，就是当前库上面的 库的总高
//        //整体组件（其中的一项 是一个component   如：天应泰发货通知单   时间   库列表）
//        for (int j = 0; j < l.size(); j++) {
//            ModleDTO.ComponentsBean componentsBean = l.get(j);
//            ku_Y = componentsBean.getCoordY();
//            if (componentsBean.getComponentTypeId().equals("5")) {
//                ku_item_h_inmodle = componentsBean.getComponentHeight();
//                //模板（其中的一项 是一个component   如：仓库名   规格   合同列表）
//                List<ModleDTO.ComponentsBean> list = componentsBean.getChlieComponentList();
//                //模板列表的数据列表
//                List<Map<String, Object>> list_map_ku = (List<Map<String, Object>>) map_in.get(componentsBean.getElement().getElementCode());
//                if (list_map_ku != null) {
//                    componentsBean.getChlieComponentLists().clear();
//                    for (int i = 0; i < list_map_ku.size(); i++) {
//                        Map<String, Object> map = list_map_ku.get(i);//代表其中的一个仓库
//                        ModleDTO.ComponentsBean componentsBean_ku_item = new ModleDTO.ComponentsBean();
//                        componentsBean_ku_item.setComponentHeight(componentsBean.getComponentHeight());
//                        componentsBean_ku_item.setElement(componentsBean.getElement());
//                        componentsBean_ku_item.setId(componentsBean.getId());
//                        //设置数据
//                        creatNenData(componentsBean, componentsBean_ku_item, list);
//                        componentsBean_ku_item.setCoordY(0);
//
//                        //=====================================================================================================合同
//                        setElement(list);
//                        paixu(list);
//                        //合同模板Y
//                        int hetong_Y = -1;
//                        //合同列表的高
//                        int hetong_list_h = 0;
//                        Log.e("00hetong_list_hkk=", hetong_list_h + "===" + map.get("stockName"));
//                        //合同列表在模板中的高
//                        int hetong_item_h_inmodle = 0;
//                        for (int j2 = 0; j2 < list.size(); j2++) {
//                            ModleDTO.ComponentsBean componentsBean2 = list.get(j2);
//                            componentsBean2.setComponentHeight(600);//TODO 这里模板传入的数据有问题，需要改高
//                            componentsBean2.setCoordY(140);//TODO 这里模板传入的数据有问题，需要改Y
//                            if (componentsBean2.getComponentTypeId().equals("5")) {
//                                hetong_item_h_inmodle = componentsBean2.getComponentHeight();
//                                hetong_Y = componentsBean2.getCoordY();
//                                List<ModleDTO.ComponentsBean> list_hetong = componentsBean2.getChlieComponentList();
//                                List<Map<String, Object>> listmap_hetong = (List<Map<String, Object>>) map.get(componentsBean2.getElement().getElementCode());
//                                if (listmap_hetong != null) {
//                                    componentsBean2.getChlieComponentLists().clear();
//                                    for (int i2 = 0; i2 < listmap_hetong.size(); i2++) {
//                                        Map<String, Object> map_hetong = listmap_hetong.get(i2);//代表其中的一个合同
//                                        ModleDTO.ComponentsBean componentsBean_hetong_item = new ModleDTO.ComponentsBean();
//                                        componentsBean_hetong_item.setComponentHeight(componentsBean2.getComponentHeight());
//                                        componentsBean_hetong_item.setElement(componentsBean2.getElement());
//                                        componentsBean_hetong_item.setId(componentsBean2.getId());
//                                        //设置数据
//                                        creatNenData2(componentsBean2, componentsBean_hetong_item, list_hetong);
//                                        componentsBean_hetong_item.setCoordY(hetong_Y);
//
//                                        //=====================================================================================================物料
//                                        setElement(list_hetong);
//                                        paixu(list_hetong);
//                                        //物料模板的Y
//                                        int wuliao_Y = -1;
//                                        //物料列表高
//                                        int wuliao_list_h = 0;
//                                        //物料列表在模板中的高
//                                        int wuliao_item_h_inmodle = 0;
//                                        Log.e("00wuliao_list_hkk=", wuliao_list_h + "===" + map_hetong.get("batchno"));
//                                        for (int j3 = 0; j3 < list_hetong.size(); j3++) {
//                                            ModleDTO.ComponentsBean componentsBean3 = list_hetong.get(j3);
//                                            if (componentsBean3.getComponentTypeId().equals("5")) {//物料 (模板)
//                                                componentsBean3.setCoordY(150);//TODO 这里模板传入的数据有问题，需要改高 其他类型Component  Y轴也有问题需要改动
//                                                wuliao_Y = componentsBean3.getCoordY();
//                                                wuliao_item_h_inmodle = componentsBean3.getComponentHeight();
//                                                List<ModleDTO.ComponentsBean> list_wuliao = componentsBean3.getChlieComponentList();
//                                                List<Map<String, Object>> listmap_wuliao = (List<Map<String, Object>>) map_hetong.get(componentsBean3.getElement().getElementCode());
//
//                                                if (listmap_wuliao != null) {
//                                                    componentsBean3.getChlieComponentLists().clear();
//                                                    for (int i3 = 0; i3 < listmap_wuliao.size(); i3++) {
//                                                        Map<String, Object> map_wuliao = listmap_wuliao.get(i3);//代表其中的一个合同
//                                                        ModleDTO.ComponentsBean componentsBean_wuliao_item = new ModleDTO.ComponentsBean();
//                                                        componentsBean_wuliao_item.setComponentHeight(componentsBean3.getComponentHeight());
//                                                        componentsBean_wuliao_item.setElement(componentsBean3.getElement());
//                                                        componentsBean_wuliao_item.setId(componentsBean3.getId());
//                                                        //设置数据
//                                                        creatNenData3(componentsBean3, componentsBean_wuliao_item, list_wuliao);
//                                                        componentsBean_wuliao_item.setCoordY(wuliao_Y);
//
//                                                        //=====================================================================================================下级
//                                                        setElement(list_wuliao);
//                                                        paixu(list_wuliao);
//                                                        //物料模板的Y
//                                                        int four_Y = -1;
//                                                        //物料列表高
//                                                        int four_h = 0;
//                                                        //物料列表在模板中的高
//                                                        int four_item_h_inmodle = 0;
//
//                                                        for (int j4 = 0; j4 < list_wuliao.size(); j4++) {
//                                                            ModleDTO.ComponentsBean componentsBean4 = list_wuliao.get(j4);
//                                                            if (componentsBean4.getComponentTypeId().equals("5")) {//物料 (模板)
//
//                                                            }else {
//                                                                //是组件下面的 就把列表的高加上
////                                                                if (componentsBean4.getCoordY() > wuliao_Y && wuliao_Y > 0) {
////                                                                    componentsBean4.setCoordY(componentsBean4.getCoordY() + wuliao_list_h - wuliao_item_h_inmodle);
////                                                                }
//                                                                componentsBean4.setComponentContent(getShowContent(componentsBean4, map_wuliao, i3));
//                                                                componentsBean_wuliao_item.getChlieComponentLists().add(componentsBean4);
//                                                            }
//                                                        }
//
//
//                                                        if (i3 == 0) {
//                                                            //coordY+外层item上方的item累计的高
//                                                            componentsBean_wuliao_item.setCoordY(componentsBean_wuliao_item.getCoordY() + wuliao_list_h + hetong_list_h + ku_list_height);
//                                                            //本高+列表高-一项列表的高 //TODO 这里只考虑了一个列表，没考虑多个同级别列表 需要优化
//                                                            componentsBean_wuliao_item.setComponentHeight(componentsBean_wuliao_item.getComponentHeight());
//                                                        } else {
//                                                            //上一个的Y+上一个的高+外层item上方的item累计的高
//                                                            componentsBean_wuliao_item.setCoordY(componentsBean_wuliao_item.getCoordY() + wuliao_list_h + hetong_list_h + ku_list_height);
//                                                            //本高+列表高-一项列表的高 //TODO 这里只考虑了一个列表，没考虑多个同级别列表 需要优化
//                                                            componentsBean_wuliao_item.setComponentHeight(componentsBean_wuliao_item.getComponentHeight());
//                                                        }
//                                                        Log.e("3级检验-H=", "===" + componentsBean_wuliao_item.getComponentHeight());
//                                                        Log.e("3级检验-Y=", "===" + componentsBean_wuliao_item.getCoordY());
//                                                        //设置物料列表的高度
//                                                        wuliao_list_h = wuliao_list_h + componentsBean_wuliao_item.getComponentHeight();
//                                                        //TODO 这里应该给仓库bean完善组件信息
//                                                        componentsBean_hetong_item.getChlieComponentLists().add(componentsBean_wuliao_item);
//                                                        componentsBean3.getChlieComponentLists().add(componentsBean_wuliao_item);
//                                                        Log.e("3whatpp=", componentsBean3.getChlieComponentLists().size() + "");
//                                                    }
//                                                }
//                                            } else {
//                                                //是组件下面的 就把列表的高加上
//                                                if (componentsBean3.getCoordY() > wuliao_Y && wuliao_Y > 0) {
//                                                    componentsBean3.setCoordY(componentsBean3.getCoordY() + wuliao_list_h - wuliao_item_h_inmodle);
//                                                }
//                                                componentsBean3.setComponentContent(getShowContent(componentsBean3, map_hetong, i2));
//                                                componentsBean_hetong_item.getChlieComponentLists().add(componentsBean3);
//                                            }
//                                        }
//
//                                        if (i2 == 0) {
//                                            //coordY+外层item上方的item累计的高
//                                            componentsBean_hetong_item.setCoordY(componentsBean_hetong_item.getCoordY() + ku_list_height);
//                                            //本高+列表高-一项列表的高 //TODO 这里只考虑了一个列表，没考虑多个同级别列表 需要优化
//                                            componentsBean_hetong_item.setComponentHeight(componentsBean_hetong_item.getComponentHeight() + wuliao_list_h - wuliao_item_h_inmodle);
//                                        } else {
//                                            //上一个的Y+上一个的高+外层item上方的item累计的高
//                                            componentsBean_hetong_item.setCoordY(componentsBean_hetong_item.getCoordY() + ku_list_height + hetong_list_h);
//                                            //本高+列表高-一项列表的高 //TODO 这里只考虑了一个列表，没考虑多个同级别列表 需要优化
//                                            componentsBean_hetong_item.setComponentHeight(componentsBean_hetong_item.getComponentHeight() + wuliao_list_h - wuliao_item_h_inmodle);
//
//                                        }
//                                        Log.e("2级检验-H=", "===" + componentsBean_hetong_item.getComponentHeight());
//                                        Log.e("2级检验-Y=", "===" + componentsBean_hetong_item.getCoordY());
//                                        hetong_list_h = hetong_list_h + componentsBean_hetong_item.getComponentHeight();
//
//                                        //TODO 这里应该给仓库bean完善组件信息
//                                        componentsBean_ku_item.getChlieComponentLists().add(componentsBean_hetong_item);
//                                        componentsBean2.getChlieComponentLists().add(componentsBean_hetong_item);
//                                        Log.e("2whatpp=", componentsBean2.getChlieComponentLists().size() + "");
//                                    }
//                                }
//                            } else {
//                                //是组件下面的 就把列表的高加上
//                                if (componentsBean2.getCoordY() > hetong_Y && hetong_Y > 0) {
//                                    componentsBean2.setCoordY(componentsBean2.getCoordY() + hetong_list_h - hetong_item_h_inmodle);
//                                }
//                                componentsBean2.setComponentContent(getShowContent(componentsBean2, map, i));
//                                Log.e("1whatpp=", componentsBean.getChlieComponentLists().size() + "");
//                                componentsBean_ku_item.getChlieComponentLists().add(componentsBean2);
//                            }
//                        }
//
//                        if (i == 0) {
//                            //coordY+外层item上方的item累计的高
//                            componentsBean_ku_item.setCoordY(componentsBean_ku_item.getCoordY() + leiji);
//                            //本高+列表高-一项列表的高 //TODO 这里只考虑了一个列表，没考虑多个同级别列表 需要优化
//                            componentsBean_ku_item.setComponentHeight(componentsBean_ku_item.getComponentHeight() + hetong_list_h - hetong_item_h_inmodle);//第一个仓库组件的高 =本高+列表高-一项列表的高
//                        } else {
//                            //上一个的Y+上一个的高
//                            componentsBean_ku_item.setCoordY(componentsBean.getChlieComponentLists().get(i - 1).getCoordY() + componentsBean.getChlieComponentLists().get(i - 1).getComponentHeight());
//                            //本高+列表高-一项列表的高 //TODO 这里只考虑了一个列表，没考虑多个同级别列表 需要优化
//                            componentsBean_ku_item.setComponentHeight(componentsBean_ku_item.getComponentHeight() + hetong_list_h - hetong_item_h_inmodle);//第二个仓库组件的高 =本高+列表高-一项列表的高
//                        }
//                        //此列表总高 最后一个item的高加最后一个item 的Y轴
////                        if (i == listmap_ku.size() - 1) {
////                            ku_list_height = componentsBean_ku_item.getCoordY() + componentsBean_ku_item.getComponentHeight();
////                        }
//                        ku_list_height = ku_list_height + componentsBean_ku_item.getComponentHeight();
//                        Log.e("1级检验-H=", "===" + componentsBean_ku_item.getComponentHeight());
//                        Log.e("1级检验-Y=", "===" + componentsBean_ku_item.getCoordY());
//
//                        //TODO 这里应该给仓库bean完善组件信息
//                        componentsBean.getChlieComponentLists().add(componentsBean_ku_item);
//                        Log.e("1whatpp=", componentsBean.getChlieComponentLists().size() + "");
//                    }
//
//                }
//            } else {
//                //是组件下面的 就把列表的高加上
//                if (componentsBean.getCoordY() > ku_Y && ku_Y > 0) {
//                    componentsBean.setCoordY(componentsBean.getCoordY() + ku_list_height - ku_item_h_inmodle);
//                }
//                componentsBean.setComponentContent(getShowContent(componentsBean, map_in, 0));
//            }
//        }
////        kanshuju(l);
////        kanshuju1(l, map_in);
//        return item_totle_h;
//
//    }

    public static void kanshuju(List<ModleDTO.ComponentsBean> l) {

        for (int j = 0; j < l.size(); j++) {
            ModleDTO.ComponentsBean componentsBean = l.get(j);
            if (componentsBean.getComponentTypeId().equals("5")) {
                Log.e("1我就不信=", componentsBean.getElement().getElementCode() + "==" + componentsBean.getChlieComponentLists().size());
                Log.e("11我就不信=", componentsBean.getCoordY() + "==" + componentsBean.getComponentHeight());

                for (ModleDTO.ComponentsBean componentsBeans2 : componentsBean.getChlieComponentLists()) {//componentsBeans2  库模板 或者其他组件
                    Log.e("2我就不信=", componentsBeans2.getComponentTypeId());
                    Log.e("22我就不信=", componentsBeans2.getCoordY() + "==" + componentsBeans2.getComponentHeight() + "==" + componentsBeans2.getComponentContent());
                    if (componentsBeans2.getComponentTypeId().equals("5")) {//componentsBeans2  库模板
                        Log.e("3我就不信=", componentsBeans2.getElement().getElementCode() + "==" + componentsBeans2.getChlieComponentLists().size());
                        Log.e("33我就不信=", componentsBeans2.getCoordY() + "==" + componentsBeans2.getComponentHeight() + "==" + componentsBeans2.getComponentContent());
                        for (ModleDTO.ComponentsBean componentsBeans3 : componentsBeans2.getChlieComponentLists()) {//componentsBeans3  合同板 或者其他组件
                            Log.e("4我就不信=", componentsBeans3.getElement().getElementCode() + "==" + componentsBeans3.getChlieComponentLists().size() + "==" + componentsBeans3.getComponentTypeId());
                            Log.e("44我就不信=", componentsBeans3.getCoordY() + "==" + componentsBeans3.getComponentHeight() + "==" + componentsBeans3.getComponentContent());
                            if (componentsBeans3.getComponentTypeId().equals("5")) {
                                Log.e("5我就不信=", componentsBeans3.getElement().getElementCode() + "==" + componentsBeans3.getChlieComponentLists().size());
                                Log.e("55我就不信=", componentsBeans3.getCoordY() + "==" + componentsBeans3.getComponentHeight() + "==" + componentsBeans3.getComponentContent());
                                for (ModleDTO.ComponentsBean componentsBeans4 : componentsBeans3.getChlieComponentLists()) {
                                    if (componentsBeans4.getComponentTypeId().equals("5")) {
                                        Log.e("6我就不信=", componentsBeans4.getElement().getElementCode() + "==" + componentsBeans4.getChlieComponentLists().size());
                                        Log.e("66我就不信=", componentsBeans4.getCoordY() + "==" + componentsBeans4.getComponentHeight() + "==" + componentsBeans4.getComponentContent());
                                    } else {
                                        //是组件下面的 就把列表的高加上
                                        if (componentsBeans3.getElement() != null && componentsBeans3.getElement().getElementCode() != null) {
                                            Log.e("333我就不信=", componentsBeans3.getElement().getElementCode()+ "==" + componentsBeans3.getComponentContent() );
                                        } else {
                                            Log.e("333我就不信=", "null" + componentsBeans3.getComponentTypeId()+ "==" +componentsBeans3.getComponentContent()  );
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        //是组件下面的 就把列表的高加上
                        if (componentsBeans2.getElement() != null && componentsBeans2.getElement().getElementCode() != null) {
                            Log.e("222我就不信=", componentsBeans2.getComponentContent() + "==" + componentsBeans2.getElement().getElementCode());
                        } else {
                            Log.e("222我就不信=", componentsBeans2.getComponentContent() + "==" + "null" + componentsBeans2.getComponentTypeId());
                        }
                        Log.e("2222我就不信=", componentsBeans2.getComponentContent() + "==" + "null" + componentsBeans2.getComponentTypeId());
                    }

                }

            } else {
                //是组件下面的 就把列表的高加上
                if (componentsBean.getElement() != null && componentsBean.getElement().getElementCode() != null) {
                    Log.e("111我就不信=", componentsBean.getComponentContent() + "==" + componentsBean.getElement().getElementCode());
                } else {
                    Log.e("111我就不信=", componentsBean.getComponentContent() + "==" + "null" + componentsBean.getComponentTypeId());
                }
            }
        }


    }


    /**
     * 根据数据打印
     *
     * @throws Exception
     */
    public static String getShowContent(ModleDTO.ComponentsBean componentsBean, Map<String, Object> map, int position) {
        String result = componentsBean.getComponentContent()==null?"":componentsBean.getComponentContent();
        Log.e("===sdfsd===2", result);
        if (componentsBean.getComponentTypeId().equals("1") || componentsBean.getComponentTypeId().equals("2") || componentsBean.getComponentTypeId().equals("3")) {
            Log.e("===sdfsd===3", componentsBean.getComponentTypeId());
            if (componentsBean.getContentSource().equals(Label.CONTENTSOURCE_TEXT)) {
                Log.e("===sdfsd===4", componentsBean.getComponentContent());
                result = componentsBean.getComponentContent();
                return result;
            }
            if (componentsBean.getContentSource().equals(Label.CONTENTSOURCE_S)) {
                return String.valueOf(position + 1) + ".";
            }
            if (componentsBean.getContentSource().equals(Label.CONTENTSOURCE_E)) {
                if (componentsBean.getElement() != null && componentsBean.getElement().getElementCode() != null) {
                    result = (String) map.get(componentsBean.getElement().getElementCode());
                    if (result == null) {
                        result = componentsBean.getComponentContent() == null ? "" : componentsBean.getComponentContent();
                    }
                    return result;
                } else {
                    Log.e("===sdfsd=null==4", componentsBean.getComponentContent());
                }
                result = componentsBean.getComponentContent();
                return result;
            }
        }
        return result;
    }
}
