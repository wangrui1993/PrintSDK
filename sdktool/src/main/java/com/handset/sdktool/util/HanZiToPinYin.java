package com.handset.sdktool.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * @ClassName: HanZiToPinYin
 * @author: wr
 * @date: 2023/5/5 18:37
 * @Description:作用描述
 */
public class HanZiToPinYin {
    /**
     * 返回一个字的拼音
     *
     * @param hanzi
     * @return
     */
    public static String toPinYin(char hanzi) {
        HanyuPinyinOutputFormat hanyuPinyin = new HanyuPinyinOutputFormat();
        hanyuPinyin.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        hanyuPinyin.setToneType(HanyuPinyinToneType.WITHOUT_TONE);//没有声调
        hanyuPinyin.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
        String[] pinyinArray = null;
        try {
            //是否在汉字范围内
            if (hanzi >= 0x4e00 && hanzi <= 0x9fa5) {
                //pinyinArray = PinyinHelper.toHanyuPinyinStringArray(hanzi, hanyuPinyin);
                pinyinArray = PinyinHelper.toHanyuPinyinStringArray(hanzi, hanyuPinyin);
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        //将汉字返回
        return pinyinArray == null ? String.valueOf(hanzi) : pinyinArray[0];
    }

    public static String getPinyin(String hanzi) {
        String pinyin = "";
        for (int i = 0; i < hanzi.length(); i++) {
            pinyin += toPinYin(hanzi.charAt(i));

        }
        //后台限制了字符串位数
        if(pinyin.length()>10){
            pinyin=pinyin.substring(0,10);
        }
        return pinyin;
    }
}
