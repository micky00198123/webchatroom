package com.qklt.chatroom.util;

import java.security.MessageDigest;
import java.util.Random;

public class ServiceUtils {

    // MD5����
    // ����: http://www.360doc.com/content/18/1123/02/99071_796636661.shtml
    public static String getMD5String(String str) {
        try {

            // getInstance����MessageDigest����
            MessageDigest md = MessageDigest.getInstance("MD5");
            // getBytesʹ��ƽ̨��Ĭ���ַ������ַ���str����Ϊ byte����(�ַ���->�ֽ�����)
            byte[] strByte = str.getBytes();
            // ������Ҫ������ַ���(���ַ�����ת���ֽ�����)
            md.update(strByte);
            // digest��ɼ��㣬������ͨ���ֽ����鷵��(result����md5���ܺ������)
            byte[] result = md.digest();
            // ��������һ���ֽ����飬�ٽ��ж��δ���ת��Ϊ�ַ���
            return byteArrayToHex(result);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // �ֽ�����ת��ʮ�����Ʊ������ַ���
    private static String byteArrayToHex(byte[] byteArray) {

        // ���16�����ַ�
        char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F' };

        char[] resultCharArray = new char[byteArray.length * 2];

        // �����ֽ����飬ͨ��λ���㣨λ����Ч�ʸߣ���ת�����ַ��ŵ��ַ�������
        int index = 0;

        for (byte b : byteArray) {
            resultCharArray[index ++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index ++] = hexDigits[b & 0xf];
        }

        return new String(resultCharArray);
    }

    // ��������ַ���
    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }


}
