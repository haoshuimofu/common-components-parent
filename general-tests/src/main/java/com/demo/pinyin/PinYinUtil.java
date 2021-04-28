package com.demo.pinyin;

import com.alibaba.fastjson.JSON;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author wude
 * @date 2021/4/22 11:57
 */
public class PinYinUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(PinYinUtil.class);

    static class StationOpenCityDTO {
        private String cityCode;
        private String cityName;
        private String firstLetter;
        private Integer count; // 该城市下开站数量, 不对外暴露

        public String getCityCode() {
            return cityCode;
        }

        public void setCityCode(String cityCode) {
            this.cityCode = cityCode;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getFirstLetter() {
            return firstLetter;
        }

        public void setFirstLetter(String firstLetter) {
            this.firstLetter = firstLetter;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }
    }

    public static void main(String[] args) {
        System.out.println(getFirstLetter("中国红123")); //--zhongguohong123

        String data = "[\n" +
                "{\n" +
                "city_name: \"上海\",\n" +
                "first_letter: \"S\",\n" +
                "city_code: \"0101\"\n" +
                "},\n" +
                "{\n" +
                "city_name: \"苏州市\",\n" +
                "first_letter: \"S\",\n" +
                "city_code: \"1001\"\n" +
                "},\n" +
                "{\n" +
                "city_name: \"昆山\",\n" +
                "first_letter: \"K\",\n" +
                "city_code: \"1004\"\n" +
                "},\n" +
                "{\n" +
                "city_name: \"北京市\",\n" +
                "first_letter: \"B\",\n" +
                "city_code: \"0201\"\n" +
                "},\n" +
                "{\n" +
                "city_name: \"杭州市\",\n" +
                "first_letter: \"H\",\n" +
                "city_code: \"0901\"\n" +
                "}\n" +
                "]";

        List<StationOpenCityDTO> cityList = JSON.parseArray(data).toJavaList(StationOpenCityDTO.class);
        for (StationOpenCityDTO dto : cityList) {
            dto.setCount((int) (Math.random() * 100));
        }

        for (StationOpenCityDTO dto : cityList) {
            System.err.println(JSON.toJSONString(dto));
        }
        System.err.println();
        cityList.sort((o1, o2) -> {
            int result = o1.getFirstLetter().compareTo(o2.getFirstLetter());
            if (result == 0) {
                result = o2.getCount().compareTo(o1.getCount());
            }
            return result;
        });
        for (StationOpenCityDTO dto : cityList) {
            System.err.println(JSON.toJSONString(dto));
        }
        System.err.println("B".compareTo("H"));
        System.err.println("H".compareTo("B"));
        System.err.println(Integer.compare(1, 3));


    }

    public static String getFirstLetter(String text) {
        if (text == null || (text = text.trim()).length() == 0) {
            return "";
        }
        try {
            char firstLetter = text.charAt(0);
            if (Character.toString(firstLetter).matches("[\\u4e00-\\u9fa5]")) {
                HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
                format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
                format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
                format.setVCharType(HanyuPinyinVCharType.WITH_V);
                return String.valueOf(PinyinHelper.toHanyuPinyinStringArray(text.charAt(0), format)[0].charAt(0));
            } else {
                return String.valueOf(firstLetter).toUpperCase();
            }
        } catch (Exception e) {
            LOGGER.error("### 汉字拼音首字母转大写出错了! text=[{}].", text, e);
        }
        return "";
    }

}