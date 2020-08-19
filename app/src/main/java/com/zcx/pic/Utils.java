package com.zcx.pic;

public class Utils {
    public static String getUrl(String keyword) {
        return getUrl(DeviceConfig.widthPixels / 2, DeviceConfig.heightPixels / 2, keyword);
    }

    public static String getUrl(int width, int height, String keyword) {
        return Api.BASE_URL + "/" + width + "x" + height + "/?" + keyword;
    }
}
