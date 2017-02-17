package com.example.drawerlayout_fragment.config;

public class Conf {

    public static final String APP_ID = "56aae3341c7a621c6e4caa449082c323";
    //	public static String intentthis=null;
    private static String intentThis = null;//判断是接收广播过来的还是主动启动的
    private static String findTitle = null;//启动消息的详情页
    private static Integer toWhichPage = 0;//跳转的页面

    public static String getIntentThis() {
        return intentThis;
    }

    public static void setIntentThis(String intentThis) {
        Conf.intentThis = intentThis;
    }


    public static String getFindTitle() {
        return findTitle;
    }

    public static void setFindTitle(String findTitle) {
        Conf.findTitle = findTitle;
    }

    public static Integer getToWhichPage() {
        return toWhichPage;
    }

    public static void setToWhichPage(Integer toWhichPage) {
        Conf.toWhichPage = toWhichPage;
    }
}
