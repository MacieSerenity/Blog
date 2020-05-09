package com.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created with IntelliJ IDEA.
 * User: MacieSerenity
 * Date: 2020/05/08 12:18
 * Description:
 * Version: V1.0
 */
public class MD5Utils {

    public static String code(String string){
        String newCode=string+"macie";
//        在原密码后面增加一个字符来确保再简单的密码也不会被反破译
        try {
            MessageDigest messageDigest=MessageDigest.getInstance("MD5");
            //拿到一个MD5转换器（如果想要SHA1参数换成”SHA1”）

            messageDigest.update(newCode.getBytes());
            //此处传入要加密的byte类型值

            byte[] bytes=messageDigest.digest();
            //输入的字符串转换成字节数组

            int i;
            StringBuffer stringBuffer=new StringBuffer("");
            for (int offset=0;offset<bytes.length;offset++){
                i=bytes[offset];
                if (i<0)
                    i+=256;
                if (i<16)
                    stringBuffer.append("0");
                stringBuffer.append(Integer.toHexString(i));//通过Integer.toHexString方法把值变为16进制
            }

            //32位
            return stringBuffer.toString();

            //16位
            //return stringBuffer.toString().substring(8,24);//从下标8开始，length目的是截取多少长度的值
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
