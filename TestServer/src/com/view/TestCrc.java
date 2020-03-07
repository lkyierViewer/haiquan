package com.view;

public class TestCrc {
	public static void main(String[] args) {
	byte[] te =	new byte[]{0x01, 0x04, 0x00, 0x01, 0x00, 0x0B};	
	
		int[] data = new int[10];
		data[0] = 0xAE;
		data[1] = 0xDF;
		int j = (data[0]&0xff)*256+(data[1]&0xff);
		int k = 0;
		StringBuilder str = new StringBuilder();
        for(int m=0;m<16;m++){
            k =((j>>m)&1);
            System.out.println(k);
        }
	}
	
/*	public static String getCRC2(byte[] bytes) {
	    int CRC = 0x0000ffff;
	    int POLYNOMIAL = 0x0000a001;

	    int i, j;
	    for (i = 0; i < bytes.length; i++) {
	        CRC ^= (int) bytes[i];
	        for (j = 0; j < 8; j++) {
	            if ((CRC & 0x00000001) == 1) {
	                CRC >>= 1;
	                CRC ^= POLYNOMIAL;
	            } else {
	                CRC >>= 1;
	            }
	        }
	    }


	    // 交换高低位，低位在前高位在后
	    CRC = ((CRC & 0x0000FF00) >> 8) | ((CRC & 0x000000FF) << 8);
	    String result = Integer.toHexString(CRC);
	   // return result.substring(0, 2) + " " + result.substring(2, 4);
	    // 交换高低位，高位在前地位在后
	    return result.substring(2, 4) + " " + result.substring(0, 2);
	}*/
}
