package com.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import gnu.io.SerialPort;
public class client {
    public static void main(String[] args) throws  Exception {
        ArrayList<String> findPort = SerialTool.findPort();
        System.out.println("可用串口");
        for(String f:findPort) {
            System.out.println(f);
        }       
        System.out.println();
        //设定发送字符串
        byte[] bs = ("ATD<15618710481>").getBytes();
        System.out.println(Arrays.toString(bs));//打印字符串
        System.out.println(findPort.get(0));//找到COM1串口
        SerialPort Port = SerialTool.openPort("COM3", 9600);//打开串口
        System.out.println(Port.getName());//获取串口名
        SerialTool.addListener(Port, new SerialTool.DataAvailableListener() {
            @SuppressWarnings("unused")
            @Override
            public void dataAvailable() {
                byte[] data = null;
                try {
                    if (Port == null) {
                        System.out.println("串口对象为空，监听失败！");
                    } else {
                        // 读取串口数据
                        data = SerialTool.readFromPort(Port);
                        /*System.out.println("打印"+data);*/
                        String resultHEX = new String(data,"ascii");
                        System.out.println("监听读到数据"+resultHEX);
                    }
            } catch (Exception e) {
                System.out.println("错误");
            }
            }
            });
        SerialTool.sendToPort(Port, bs);//写入,写入应该在监听器打开之后而不是之前
        
        
    }
    
    public static byte[] hex2Bytes(String hex) {
        if (hex == null || hex.length() == 0) {
            return null;
        }

        char[] hexChars = hex.toCharArray();
        byte[] bytes = new byte[hexChars.length / 2];   // 如果 hex 中的字符不是偶数个, 则忽略最后一个

        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt("" + hexChars[i * 2] + hexChars[i * 2 + 1], 16);
        }

        return bytes;
    }
    /**
     * byte[]转十六进制字符串
     * 
     * @param array
     *            byte[]
     * @return 十六进制字符串
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
     * byte转十六进制字符
     * 
     * @param b
     *            byte
     * @return 十六进制字符
     */
    public static String byteToHex(byte b) {
        String hex = Integer.toHexString(b & 0xFF);
        if (hex.length() == 1) {
            hex = '0' + hex;
        }
        return hex.toUpperCase(Locale.getDefault());
    }
}
