package com.aifubook.book.mine.order;

public class Refundstatus {
    public static String getstatus(Integer status) {
        String textstring = "";
        if (status == null) {
            textstring = "退款";
        } else {
            switch (status) {
                case 1:
                    textstring = "退款中";
                    break;
                case 2:
                    textstring = "退款成功";
                    break;
                case 3:
                    textstring = "退款中";
                    break;
                case 4:
                    textstring = "退款中";
                    break;
                case 5:
                    textstring = "退款中";
                    break;
                case 6:
                    textstring = "退款关闭";
                    break;
                case 7:
                    textstring = "退款成功";
                    break;
                case 8:
                    textstring = "";
                    break;
            }
        }
        return textstring;
    }
}
