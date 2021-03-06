package com.view;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.TooManyListenersException;
import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
public class SerialTool {
    /**
     * 查找所有可用端口
     * @return 可用端口名称列表
     */
     public static final ArrayList<String> findPort() {
     
     //获得当前所有可用串口
     @SuppressWarnings("unchecked")
    Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers(); 
     ArrayList<String> portNameList = new ArrayList<String>(); 
     //将可用串口名添加到List并返回该List
     while (portList.hasMoreElements()) {        
      String portName = portList.nextElement().getName();
      portNameList.add(portName);
     }
     
     return portNameList;
     
     }
      
     /**
     * 打开串口
     * @param portName 端口名称
     * @param baudrate 波特率
     * @return 串口对象
     * @throws SerialPortParameterFailure 设置串口参数失败
     * @throws NotASerialPort 端口指向设备不是串口类型
     * @throws NoSuchPort 没有该端口对应的串口设备
     * @throws PortInUse 端口已被占用
     */
     public static final SerialPort openPort(String portName, int baudrate) throws Exception {
     
     try {
     
      //通过端口名识别端口
      CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
     
      //打开端口，并给端口名字和一个timeout（打开操作的超时时间）
      CommPort commPort = portIdentifier.open(portName, 2000);
     
      //判断是不是串口
      if (commPort instanceof SerialPort) {
       
      SerialPort serialPort = (SerialPort) commPort;
       
      try {   
       //设置一下串口的波特率等参数
       serialPort.setSerialPortParams(baudrate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);    
       /*serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);*/
      } catch (UnsupportedCommOperationException e) { 
       throw new Exception("波特率异常");
      }
       
      System.out.println("Open " + portName + " successfully !");
      return serialPort;
       
      } 
      else {
      //不是串口
      throw new Exception("不是串口");
      }
     } catch (NoSuchPortException e1) {
      throw new Exception("没有这个串口");
     } catch (PortInUseException e2) {
    	 throw new Exception("串口被占用");
     }
     }
      
     /**
     * 关闭串口
     * @param serialport 待关闭的串口对象
     */
     public static void closePort(SerialPort serialPort) {
     if (serialPort != null) {
      serialPort.close();
      serialPort = null;
      
     }
     }
      
     /**
     * 往串口发送数据
     * @param serialPort 串口对象
     * @param order 待发送数据
     * @throws SendDataToSerialPortFailure 向串口发送数据失败
     * @throws SerialPortOutputStreamCloseFailure 关闭串口对象的输出流出错
     */
     public static void sendToPort(SerialPort serialPort, byte[] order) throws Exception{
     
     OutputStream out = null;
      
     try {
       
      out = serialPort.getOutputStream();
      out.write(order);
      out.flush();
      System.out.println("写入成功");
       
     } catch (IOException e) {
      throw new Exception("信息写入失败");
     } finally {
      try {
      if (out != null) {
       out.close();
       out = null;
      }  
      } catch (IOException e) {
      throw new Exception("关闭流失败");
      }
     }
      
     }
      
     /**
     * 从串口读取数据
     * @param serialPort 当前已建立连接的SerialPort对象
     * @return 读取到的数据
     * @throws ReadDataFromSerialPortFailure 从串口读取数据时出错
     * @throws SerialPortInputStreamCloseFailure 关闭串口对象输入流出错
     */
     public static byte[] readFromPort(SerialPort serialPort) throws Exception {
     
     InputStream in = null;
     byte[] bytes = null;
	try {
			Thread.sleep(50);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
     try {
       
      in = serialPort.getInputStream();
      int bufflenth = in.available(); //获取buffer里的数据长度
       
      while (bufflenth != 0) {    
      bytes = new byte[bufflenth]; //初始化byte数组为buffer中数据的长度
      in.read(bytes);
      bufflenth = in.available();
      } 
     } catch (IOException e) {
      throw new Exception("读取失败");
     } finally {
      try {
      if (in != null) {
       in.close();
       in = null;
      }
      } catch(IOException e) {
    	  throw new Exception("读取关闭失败");
      }
     
     }
     System.out.println("读取成功");
     return bytes;
     
     }
      
     /**
     * 添加监听器
     * @param port 串口对象
     * @param listener 串口监听器
     * @throws TooManyListeners 监听类对象过多
     */
     public static void addListener(SerialPort port, DataAvailableListener listener) throws Exception  {
     
     try {
       
      //给串口添加监听器
      port.addEventListener(new SerialPortListener(listener));
      //设置当有数据到达时唤醒监听接收线程
      port.notifyOnDataAvailable(true);
      //设置当通信中断时唤醒中断线程
      port.notifyOnBreakInterrupt(true);
     
     } catch (TooManyListenersException e) {
      throw new Exception();
     }
     }
     /**
         * 串口监听
         */
        public static class SerialPortListener implements SerialPortEventListener {

            private DataAvailableListener mDataAvailableListener;

            public SerialPortListener(DataAvailableListener mDataAvailableListener) {
                this.mDataAvailableListener = mDataAvailableListener;
            }

            public void serialEvent(SerialPortEvent serialPortEvent) {
                switch (serialPortEvent.getEventType()) {
                case SerialPortEvent.DATA_AVAILABLE: // 1.串口存在有效数据
                    if (mDataAvailableListener != null) {
                        mDataAvailableListener.dataAvailable();
                    }
                    break;

                case SerialPortEvent.OUTPUT_BUFFER_EMPTY: // 2.输出缓冲区已清空
                    break;

                case SerialPortEvent.CTS: // 3.清除待发送数据
                    break;

                case SerialPortEvent.DSR: // 4.待发送数据准备好了
                    break;

                case SerialPortEvent.RI: // 5.振铃指示
                    break;

                case SerialPortEvent.CD: // 6.载波检测
                    break;

                case SerialPortEvent.OE: // 7.溢位（溢出）错误
                    break;

                case SerialPortEvent.PE: // 8.奇偶校验错误
                    break;

                case SerialPortEvent.FE: // 9.帧错误
                    break;

                case SerialPortEvent.BI: // 10.通讯中断
                    System.out.println("与串口设备通讯中断");
                    break;

                default:
                    break;
                }
            }
        }

        /**
         * 串口存在有效数据监听
         */
        public interface DataAvailableListener {
            /**
             * 串口存在有效数据
             */
            void dataAvailable();
        } 
    }
