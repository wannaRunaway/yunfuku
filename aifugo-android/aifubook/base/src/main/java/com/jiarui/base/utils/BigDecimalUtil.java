package com.jiarui.base.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;

public class BigDecimalUtil {


    public static BigDecimal getBigDecimal(Object value) {
        BigDecimal ret = null;
        if (value != null) {
            if (value instanceof BigDecimal) {
                ret = (BigDecimal) value;
            } else if (value instanceof String) {
                ret = new BigDecimal((String) value);
            } else if (value instanceof BigInteger) {
                ret = new BigDecimal((BigInteger) value);
            } else if (value instanceof Number) {
                //转成String类型，然后new
                ret = new BigDecimal(((Number) value).toString());
            } else {
                throw new ClassCastException("Not possible to coerce [" + value + "] from class "
                        + value.getClass() + " into a BigDecimal.");
            }
        }
        return ret;
    }

    public static BigDecimal numberBg(String num, int pointNum){
        BigDecimal bigDecimal=null;
        if(!StringUtil.checkStr(num)){
            bigDecimal=new BigDecimal("0.0");
        }else{
            bigDecimal=new BigDecimal(num);
        }
        if(pointNum>0) {
            bigDecimal=bigDecimal.setScale(pointNum, BigDecimal.ROUND_DOWN);
        }
        return bigDecimal;
    }

    /*
     * 加法
     */
    public static BigDecimal add(String num1, String num2, int pointNum) {
        BigDecimal bigDecimal1 = null;
        if (!StringUtil.isNumeric(num1)) {
            bigDecimal1 = new BigDecimal("0.0");
        } else {
            bigDecimal1 = new BigDecimal(num1);
        }

        BigDecimal bigDecimal2 = null;
        if (!StringUtil.isNumeric(num2)) {
            bigDecimal2 = new BigDecimal("0.0");
        } else {
            bigDecimal2 = new BigDecimal(num2);
        }

        BigDecimal result = bigDecimal1.add(bigDecimal2);
        if (pointNum > 0) {
            result = result.setScale(pointNum, BigDecimal.ROUND_DOWN);
        }
        return result;
    }

    /*
     * 减法
     */
    public static BigDecimal subtract(String num1, String num2, int pointNum) {
        BigDecimal bigDecimal1 = null;
        if (!StringUtil.isNumeric(num1)) {
            bigDecimal1 = new BigDecimal("0.0");
        } else {
            bigDecimal1 = new BigDecimal(num1);
        }

        BigDecimal bigDecimal2 = null;
        if (!StringUtil.isNumeric(num2)) {
            bigDecimal2 = new BigDecimal("0.0");
        } else {
            bigDecimal2 = new BigDecimal(num2);
        }

        BigDecimal result = bigDecimal1.subtract(bigDecimal2);
        if (pointNum > 0) {
            result = result.setScale(pointNum, BigDecimal.ROUND_HALF_UP);
//            result = result.setScale(pointNum, BigDecimal.ROUND_DOWN);
        }
        return result;
    }

    /*
     * 乘法,
     * pointNum  小数点后保留几位 四舍五入
     */
    public static BigDecimal multiply(String num1, String num2, int pointNum) {
        BigDecimal bigDecimal1 = null;
        if (!StringUtil.isNumeric(num1)) {
            bigDecimal1 = new BigDecimal("0.0");
        } else {
            bigDecimal1 = new BigDecimal(num1);
        }

        BigDecimal bigDecimal2 = null;
        if (!StringUtil.isNumeric(num2)) {
            bigDecimal2 = new BigDecimal("0.0");
        } else {
            bigDecimal2 = new BigDecimal(num2);
        }

        BigDecimal result = bigDecimal1.multiply(bigDecimal2);
        if (pointNum > 0) {
            result = result.setScale(pointNum, BigDecimal.ROUND_HALF_UP);
        }
        return result;
    }

    /*
     * 乘法,
     * pointNum  小数点后保留几位 向下取整
     */
    public static BigDecimal multiply2(String num1, String num2, int pointNum) {
        BigDecimal bigDecimal1 = null;
        if (!StringUtil.isNumeric(num1)) {
            bigDecimal1 = new BigDecimal("0.0");
        } else {
            bigDecimal1 = new BigDecimal(num1);
        }

        BigDecimal bigDecimal2 = null;
        if (!StringUtil.isNumeric(num2)) {
            bigDecimal2 = new BigDecimal("0.0");
        } else {
            bigDecimal2 = new BigDecimal(num2);
        }

        BigDecimal result = bigDecimal1.multiply(bigDecimal2);
        if (pointNum > 0) {
            result = result.setScale(pointNum, BigDecimal.ROUND_DOWN);
        }
        return result;
    }

    /*
     * 除法
     */
    public static BigDecimal divide(String num1, String num2, int pointNum) {
        BigDecimal bigDecimal1 = null;
        if (!StringUtil.isNumeric(num1)) {
            bigDecimal1 = new BigDecimal("0.0");
        } else {
            bigDecimal1 = new BigDecimal(num1);
        }

        BigDecimal bigDecimal2 = null;
        if (!StringUtil.isNumeric(num2)) {
            bigDecimal2 = new BigDecimal("0.0");
        } else {
            bigDecimal2 = new BigDecimal(num2);
        }

        if (0 == bigDecimal2.compareTo(BigDecimal.ZERO)) {
            return bigDecimal2;
        }
        BigDecimal result = bigDecimal1.divide(bigDecimal2, BigDecimal.ROUND_HALF_UP);
        if (pointNum > 0) {
            result = result.setScale(pointNum, BigDecimal.ROUND_HALF_UP);
        }
        return result;
    }

    /**
     * 向下取整 保留pointNum小数
     *
     * @param num1
     * @param num2
     * @param pointNum
     * @return
     */
    public static BigDecimal divide2(String num1, String num2, int pointNum) {
        BigDecimal bigDecimal1 = null;
        if (!StringUtil.isNumeric(num1)) {
            bigDecimal1 = new BigDecimal("0.0");
        } else {
            bigDecimal1 = new BigDecimal(num1);
        }

        BigDecimal bigDecimal2 = null;
        if (!StringUtil.isNumeric(num2)) {
            bigDecimal2 = new BigDecimal("0.0");
        } else {
            bigDecimal2 = new BigDecimal(num2);
        }

        if (0 == bigDecimal2.compareTo(BigDecimal.ZERO)) {
            return bigDecimal2;
        }
        BigDecimal result = bigDecimal1.divide(bigDecimal2, BigDecimal.ROUND_DOWN);
        if (pointNum > 0) {
            result = result.setScale(pointNum, BigDecimal.ROUND_DOWN);
        }
        return result;
    }

    /*
     * 四舍五入小数点后保留几位小数
     */
    public static String getFixedPointNum(String num, int point) {
        BigDecimal bigDecimal = null;
        if (!StringUtil.isNumeric(num)) {
            bigDecimal = new BigDecimal("0.00");
        } else {
            bigDecimal = new BigDecimal(num);
        }
        if (point > 0) {
            return bigDecimal.setScale(point, BigDecimal.ROUND_HALF_UP).toPlainString();
        }
        return bigDecimal.toPlainString();
    }

    public static String getPrettyNumber(String num) {
        if (!StringUtil.isNumeric(num)) {
            return "0";
        }
        return BigDecimal.valueOf(Double.parseDouble(num)).stripTrailingZeros().toPlainString();
    }

    /*
     * 四舍五入小数点后保留两位小数,并每三位逗号分割,point为0则为整数
     */
    public static String getFixedPointNum2(String num, int point) {
        if (!StringUtil.isNumeric(num)) {
            return new BigDecimal("0.00").setScale(point).toPlainString();
        }
        BigDecimal bigDecimal = new BigDecimal(num);
        if (1 == compareSize(num, "-1000") && -1 == compareSize(num, "1000")) {
            return bigDecimal.setScale(point, BigDecimal.ROUND_HALF_UP).toPlainString();
        }

        bigDecimal = bigDecimal.setScale(point, BigDecimal.ROUND_HALF_UP);

        BigDecimal intBigDecimal = bigDecimal.setScale(0, BigDecimal.ROUND_DOWN);
        BigDecimal pointBigDecimal = bigDecimal.subtract(intBigDecimal);

        String format = new DecimalFormat("#,###").format(intBigDecimal.toBigIntegerExact());

        return format + pointBigDecimal.toPlainString().replace("0.", ".");//new BigDecimal().toPlainString();
    }

    /*
     * ROUND_DOWN 向下取整  小数点后保留几位小数
     */
    public static String getFixedPointNum3(String num, int point) {
        BigDecimal bigDecimal = null;
        if (!StringUtil.isNumeric(num)) {
            bigDecimal = new BigDecimal("0.00");
        } else {
            bigDecimal = new BigDecimal(num);
        }

        if (point > 0) {

            return bigDecimal.setScale(point, BigDecimal.ROUND_DOWN).toPlainString();
        }
        return bigDecimal.toPlainString();
    }

    /**
     * @param num1
     * @param num2
     * @return -1小于 0等于 1大于
     */
    public static int compareSize(String num1, String num2) {
        if (!StringUtil.isNumeric(num1)) {
            num1 = "0";
        }
        if (!StringUtil.isNumeric(num2)) {
            num2 = "0";
        }
        return new BigDecimal(num1).compareTo(new BigDecimal(num2));
    }


}
