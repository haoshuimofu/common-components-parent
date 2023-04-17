package com.demo.components.desensitive.utils;

import com.demo.components.desensitive.DesensitiveValueType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author dewu.de
 * @date 2023-04-12 2:33 下午
 */
public class DesensitizedValueMaskingUtils {

    private static final Pattern NUMBER_PATTERN = Pattern.compile("\\d");

    private static final String COMMON_HIDDEN_VALUE = "***";
    private static final String NAME_HIDDEN_VALUE = "**";
    private static final String PHONE_HIDDEN_VALUE = "****";

    public static String handlePhone(String phone) {
        if (phone != null && phone.length() > 3) {
            int startIdx = 3;
            int endIdx = startIdx + (phone.length() - 3) / 2;
            return phone.substring(0, startIdx) + PHONE_HIDDEN_VALUE + phone.substring(endIdx);
        }
        return phone;
    }

    public static String handleEmail(String email) {
        if (DesensitizedCommonUtils.isNotEmptyString(email)) {
            int index = email.lastIndexOf("@");
            if (index != -1) {
                return email.charAt(0) + COMMON_HIDDEN_VALUE + email.substring(index);
            }
        }
        return email;
    }

    public static String handlePersonName(String name) {
        return DesensitizedCommonUtils.isNotEmptyString(name) ? name.charAt(0) + NAME_HIDDEN_VALUE : name;
    }

    public static String handleDetailedAddress(String detailedAddress) {
        if (DesensitizedCommonUtils.isNotEmptyString(detailedAddress)) {
            Matcher matcher = NUMBER_PATTERN.matcher(detailedAddress);
            if (matcher.find()) {
                int index = matcher.start();
                return detailedAddress.substring(0, index) + COMMON_HIDDEN_VALUE;
            }
        }
        return detailedAddress;
    }

    public static String hidden(String content) {
        return DesensitizedCommonUtils.isNotEmptyString(content) ? COMMON_HIDDEN_VALUE : content;
    }

    public static String handle(String value, DesensitiveValueType valueType) {
        switch (valueType) {
            case PERSON_NAME:
                return DesensitizedValueMaskingUtils.handlePersonName(value);
            case EMAIL:
                return DesensitizedValueMaskingUtils.handleEmail(value);
            case MOBILE_PHONE:
                return DesensitizedValueMaskingUtils.handlePhone(value);
            case DETAILED_ADDRESS:
                return DesensitizedValueMaskingUtils.handleDetailedAddress(value);
            default:
                return DesensitizedValueMaskingUtils.hidden(value);
        }
    }

    public static void main(String[] args) {
        System.out.println(handlePhone("17717929937"));
        System.out.println(handleEmail("dewu.de@alibaba-inc.com"));
        System.out.println(handleDetailedAddress("明日新苑12号楼602"));
        System.out.println(handleDetailedAddress("紫御豪庭A#1205"));

    }

}
