package com.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import gnu.io.SerialPort;
public class client {
    public static void main(String[] args) throws  Exception {
        ArrayList<String> findPort = SerialTool.findPort();
        System.out.println("���ô���");
        for(String f:findPort) {
            System.out.println(f);
        }       
        System.out.println();
        //�趨�����ַ���
        byte[] bs = ("ATD<15618710481>").getBytes();
        System.out.println(Arrays.toString(bs));//��ӡ�ַ���
        System.out.println(findPort.get(0));//�ҵ�COM1����
        SerialPort Port = SerialTool.openPort("COM3", 9600);//�򿪴���
        System.out.println(Port.getName());//��ȡ������
        SerialTool.addListener(Port, new SerialTool.DataAvailableListener() {
            @SuppressWarnings("unused")
            @Override
            public void dataAvailable() {
                byte[] data = null;
                try {
                    if (Port == null) {
                        System.out.println("���ڶ���Ϊ�գ�����ʧ�ܣ�");
                    } else {
                        // ��ȡ��������
                        data = SerialTool.readFromPort(Port);
                        /*System.out.println("��ӡ"+data);*/
                        String resultHEX = new String(data,"ascii");
                        System.out.println("������������"+resultHEX);
                    }
            } catch (Exception e) {
                System.out.println("����");
            }
            }
            });
        SerialTool.sendToPort(Port, bs);//д��,д��Ӧ���ڼ�������֮�������֮ǰ
        
        
    }
    
    public static byte[] hex2Bytes(String hex) {
        if (hex == null || hex.length() == 0) {
            return null;
        }

        char[] hexChars = hex.toCharArray();
        byte[] bytes = new byte[hexChars.length / 2];   // ��� hex �е��ַ�����ż����, ��������һ��

        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt("" + hexChars[i * 2] + hexChars[i * 2 + 1], 16);
        }

        return bytes;
    }
    /**
     * byte[]תʮ�������ַ���
     * 
     * @param array
     *            byte[]
     * @return ʮ�������ַ���
     */
    public static String byteArrayToHexString(byte[] array) {
        if (array == null) {
            return "";
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < array.length; i++) {
            buffer.append(byteToHex(array[i]));
        }
        return buffer.toString();
    }
    /**
     * byteתʮ�������ַ�
     * 
     * @param b
     *            byte
     * @return ʮ�������ַ�
     */
    public static String byteToHex(byte b) {
        String hex = Integer.toHexString(b & 0xFF);
        if (hex.length() == 1) {
            hex = '0' + hex;
        }
        return hex.toUpperCase(Locale.getDefault());
    }
}
