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
     * �������п��ö˿�
     * @return ���ö˿������б�
     */
     public static final ArrayList<String> findPort() {
     
     //��õ�ǰ���п��ô���
     @SuppressWarnings("unchecked")
    Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers(); 
     ArrayList<String> portNameList = new ArrayList<String>(); 
     //�����ô�������ӵ�List�����ظ�List
     while (portList.hasMoreElements()) {        
      String portName = portList.nextElement().getName();
      portNameList.add(portName);
     }
     
     return portNameList;
     
     }
      
     /**
     * �򿪴���
     * @param portName �˿�����
     * @param baudrate ������
     * @return ���ڶ���
     * @throws SerialPortParameterFailure ���ô��ڲ���ʧ��
     * @throws NotASerialPort �˿�ָ���豸���Ǵ�������
     * @throws NoSuchPort û�иö˿ڶ�Ӧ�Ĵ����豸
     * @throws PortInUse �˿��ѱ�ռ��
     */
     public static final SerialPort openPort(String portName, int baudrate) throws Exception {
     
     try {
     
      //ͨ���˿���ʶ��˿�
      CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
     
      //�򿪶˿ڣ������˿����ֺ�һ��timeout���򿪲����ĳ�ʱʱ�䣩
      CommPort commPort = portIdentifier.open(portName, 2000);
     
      //�ж��ǲ��Ǵ���
      if (commPort instanceof SerialPort) {
       
      SerialPort serialPort = (SerialPort) commPort;
       
      try {   
       //����һ�´��ڵĲ����ʵȲ���
       serialPort.setSerialPortParams(baudrate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);    
       /*serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);*/
      } catch (UnsupportedCommOperationException e) { 
       throw new Exception("�������쳣");
      }
       
      System.out.println("Open " + portName + " successfully !");
      return serialPort;
       
      } 
      else {
      //���Ǵ���
      throw new Exception("���Ǵ���");
      }
     } catch (NoSuchPortException e1) {
      throw new Exception("û���������");
     } catch (PortInUseException e2) {
    	 throw new Exception("���ڱ�ռ��");
     }
     }
      
     /**
     * �رմ���
     * @param serialport ���رյĴ��ڶ���
     */
     public static void closePort(SerialPort serialPort) {
     if (serialPort != null) {
      serialPort.close();
      serialPort = null;
      
     }
     }
      
     /**
     * �����ڷ�������
     * @param serialPort ���ڶ���
     * @param order ����������
     * @throws SendDataToSerialPortFailure �򴮿ڷ�������ʧ��
     * @throws SerialPortOutputStreamCloseFailure �رմ��ڶ�������������
     */
     public static void sendToPort(SerialPort serialPort, byte[] order) throws Exception{
     
     OutputStream out = null;
      
     try {
       
      out = serialPort.getOutputStream();
      out.write(order);
      out.flush();
      System.out.println("д��ɹ�");
       
     } catch (IOException e) {
      throw new Exception("��Ϣд��ʧ��");
     } finally {
      try {
      if (out != null) {
       out.close();
       out = null;
      }  
      } catch (IOException e) {
      throw new Exception("�ر���ʧ��");
      }
     }
      
     }
      
     /**
     * �Ӵ��ڶ�ȡ����
     * @param serialPort ��ǰ�ѽ������ӵ�SerialPort����
     * @return ��ȡ��������
     * @throws ReadDataFromSerialPortFailure �Ӵ��ڶ�ȡ����ʱ����
     * @throws SerialPortInputStreamCloseFailure �رմ��ڶ�������������
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
      int bufflenth = in.available(); //��ȡbuffer������ݳ���
       
      while (bufflenth != 0) {    
      bytes = new byte[bufflenth]; //��ʼ��byte����Ϊbuffer�����ݵĳ���
      in.read(bytes);
      bufflenth = in.available();
      } 
     } catch (IOException e) {
      throw new Exception("��ȡʧ��");
     } finally {
      try {
      if (in != null) {
       in.close();
       in = null;
      }
      } catch(IOException e) {
    	  throw new Exception("��ȡ�ر�ʧ��");
      }
     
     }
     System.out.println("��ȡ�ɹ�");
     return bytes;
     
     }
      
     /**
     * ��Ӽ�����
     * @param port ���ڶ���
     * @param listener ���ڼ�����
     * @throws TooManyListeners ������������
     */
     public static void addListener(SerialPort port, DataAvailableListener listener) throws Exception  {
     
     try {
       
      //��������Ӽ�����
      port.addEventListener(new SerialPortListener(listener));
      //���õ������ݵ���ʱ���Ѽ��������߳�
      port.notifyOnDataAvailable(true);
      //���õ�ͨ���ж�ʱ�����ж��߳�
      port.notifyOnBreakInterrupt(true);
     
     } catch (TooManyListenersException e) {
      throw new Exception();
     }
     }
     /**
         * ���ڼ���
         */
        public static class SerialPortListener implements SerialPortEventListener {

            private DataAvailableListener mDataAvailableListener;

            public SerialPortListener(DataAvailableListener mDataAvailableListener) {
                this.mDataAvailableListener = mDataAvailableListener;
            }

            public void serialEvent(SerialPortEvent serialPortEvent) {
                switch (serialPortEvent.getEventType()) {
                case SerialPortEvent.DATA_AVAILABLE: // 1.���ڴ�����Ч����
                    if (mDataAvailableListener != null) {
                        mDataAvailableListener.dataAvailable();
                    }
                    break;

                case SerialPortEvent.OUTPUT_BUFFER_EMPTY: // 2.��������������
                    break;

                case SerialPortEvent.CTS: // 3.�������������
                    break;

                case SerialPortEvent.DSR: // 4.����������׼������
                    break;

                case SerialPortEvent.RI: // 5.����ָʾ
                    break;

                case SerialPortEvent.CD: // 6.�ز����
                    break;

                case SerialPortEvent.OE: // 7.��λ�����������
                    break;

                case SerialPortEvent.PE: // 8.��żУ�����
                    break;

                case SerialPortEvent.FE: // 9.֡����
                    break;

                case SerialPortEvent.BI: // 10.ͨѶ�ж�
                    System.out.println("�봮���豸ͨѶ�ж�");
                    break;

                default:
                    break;
                }
            }
        }

        /**
         * ���ڴ�����Ч���ݼ���
         */
        public interface DataAvailableListener {
            /**
             * ���ڴ�����Ч����
             */
            void dataAvailable();
        } 
    }
