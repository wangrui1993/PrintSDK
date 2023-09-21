package com.handset.sdktool.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.io.UnsupportedEncodingException;

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
        if (pinyin.length() > 10) {
            pinyin = pinyin.substring(0, 10);
        }
        return pinyin;
    }

    public static String getIPSample(String hanzi) {
        hanzi = hanzi.substring(7, hanzi.length()  );
        hanzi = hanzi.replace(".", "");

        return hanzi;
    }


    private static final int GB_SP_DIFF = 160;
    // 存放国标一级汉字不同读音的起始区位码
    private static final int[] secPosValueList = {1601, 1637, 1833, 2078, 2274, 2302,
            2433, 2594, 2787, 3106, 3212, 3472, 3635, 3722, 3730, 3858, 4027,
            4086, 4390, 4558, 4684, 4925, 5249, 5600};
    private final static String[] lc_FirstLetter = {"a", "b", "c", "d", "e",
            "f", "g", "h", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "w", "x", "y", "z"};

    /**
     * 获取一个汉字的拼音首字母。 GB码两个字节分别减去160，转换成10进制码组合就可以得到区位码
     * 例如汉字“你”的GB码是0xC4/0xE3，分别减去0xA0（160）就是0x24/0x43
     * 0x24转成10进制就是36，0x43是67，那么它的区位码就是3667，在对照表中读音为‘n’
     */
//    private static char convert(byte[] bytes) {
//        char result = '-';
//        int secPosValue = 0;
//        int i;
//        for (i = 0; i < bytes.length; i++) {
//            bytes[i] -= GB_SP_DIFF;
//        }
//        secPosValue = bytes[0] * 100 + bytes[1];
//        for (i = 0; i < 23; i++) {
//            if (secPosValue >= secPosValueList[i]
//                    && secPosValue < secPosValueList[i + 1]) {
//                result = firstLetter[i];
//                break;
//            }
//        }
//        return result;
//    }

    /**
     * 调用方法
     * @param str 中文串
     * @return 声母串
     */
    public static String getAllFirstLetter(String str) {
        if (str == null || str.trim().length() == 0) {
            return "";
        }
        String _str = "";
        for (int i = 0; i < str.length(); i++) {
            _str = _str + getFirstLetter(str.substring(i, i + 1));
        }
        return _str;
    }

    /**
     * 每个字的首字母
     *
     * @param chinese 汉字
     * @return 返回声母
     */
    public static String getFirstLetter(String chinese) {
        if (chinese == null || chinese.trim().length() == 0) {
            return "";
        }
        chinese = conversionStr(chinese, "GB2312", "ISO8859-1");

        if (chinese.length() > 1){ // 判断是不是汉字
            int li_SectorCode = (int) chinese.charAt(0); // 汉字区码
            int li_PositionCode = (int) chinese.charAt(1); // 汉字位码
            li_SectorCode = li_SectorCode - 160;
            li_PositionCode = li_PositionCode - 160;
            int li_SecPosCode = li_SectorCode * 100 + li_PositionCode; // 汉字区位码
            if (li_SecPosCode > 1600 && li_SecPosCode < 5590) {
                for (int i = 0; i < 23; i++) {
                    if (li_SecPosCode >= secPosValueList[i]
                            && li_SecPosCode < secPosValueList[i + 1]) {
                        chinese = lc_FirstLetter[i];
                        break;
                    }
                }
            } else {// 非汉字字符,如图形符号或ASCII码
                chinese = conversionStr(chinese, "ISO8859-1", "GB2312");
                chinese = chinese.substring(0, 1);
            }
        }
        return chinese;
    }

    /**
     * 字符串编码转换
     * @param str           要转换编码的字符串
     * @param charsetName   原来的编码
     * @param toCharsetName 转换后的编码
     * @return 经过编码转换后的字符串
     */
    private static String conversionStr(String str, String charsetName, String toCharsetName) {
        try {
            str = new String(str.getBytes(charsetName), toCharsetName);
        } catch (UnsupportedEncodingException ex) {
            System.out.println("字符串编码转换异常：" + ex.getMessage());
        }
        return str;
    }

}
