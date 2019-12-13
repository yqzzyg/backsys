package com.dr.backsys.utils.idnoToArea;

import java.math.BigDecimal;

/**
 * @ Description   :
 * @ Author        :  yqz
 * @ CreateDate    :  2019/12/13 10:25
 */
public class TestPlace {
    public static void main(String[] args) {

        Double num1 =100.0;
        Integer num2 =4;



        BigDecimal v1 = BigDecimal.valueOf(num1 / 1000000000);
        System.out.println(v1);

        String nativePlace = NativePlace.getNativePlace(412727);

//        double v = v1 / num2;
//        System.out.println(v);

        System.out.println("您所在的地区为：\n" + nativePlace);
    }
}
