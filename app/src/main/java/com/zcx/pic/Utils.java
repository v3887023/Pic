package com.zcx.pic;

public class Utils {
    public static String getUrl(String keyword) {
        return getUrl(DeviceConfig.getWidthPixels() / 2, DeviceConfig.getHeightPixels() / 2, keyword);
    }

    public static String getUrl(int width, int height, String keyword) {
        return Api.BASE_URL + "/" + width + "x" + height + "/?" + keyword;
    }
}
