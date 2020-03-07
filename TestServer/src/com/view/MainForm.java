package com.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


import com.view.MainForm.ServerThread;

public class MainForm extends JFrame {

	private JButton btn_Start;
	private JPanel pan_main;
	private JFrame jf_main;
	private boolean btnState = true;
	private String btn_Text = "启动";
	private Thread t1 = null;
	private boolean system_status = false;
	private ConcurrentLinkedQueue<Integer> TestQueue = new ConcurrentLinkedQueue<>();
	private ArrayList<Thread> threadList = new ArrayList<>();
	private ArrayList<Thread> threadList2 = new ArrayList<>();
	private List<Student> studentList = Collections.synchronizedList(new ArrayList<>());


	public MainForm() {
		this.setTitle("模拟服务端");
		/*
		 * for (int ddddd = 0; ddddd < 10; ddddd++) { Thread t1 = new Thread(new
		 * Runnable() {
		 * 
		 * @Override public void run() { // TODO Auto-generated method stub
		 * TestQueue.add(1); } }); threadList.add(t1); } for (int dd = 0; dd < 13; dd++)
		 * { Thread t1 = new Thread(new Runnable() {
		 * 
		 * @Override public void run() { // TODO Auto-generated method stub if
		 * (!TestQueue.isEmpty())
		 * System.out.println(Thread.currentThread().getName()+":" + TestQueue.poll());
		 * } },"t"+dd); threadList2.add(t1); }
		 * 
		 * 
		 * for (int dd = 0; dd < threadList.size(); dd++) { threadList.get(dd).start();
		 * } for (int dd = 0; dd < threadList2.size(); dd++) {
		 * threadList2.get(dd).start(); }
		 */
		// int aaa = Integer.parseInt("71", 16);
		// String aaa=Integer.toHexString(71);
		// int bbb = 0x4d & 0xff;
		this.setBounds(700, 300, 500, 300);
		this.btn_Start = new JButton("启动");
		this.btn_Start.addActionListener(new BtnStartAction());
		this.pan_main = new JPanel();
		pan_main.setLayout(null);
		this.pan_main.setSize(500, 300);
		this.pan_main.add(this.btn_Start);
		this.btn_Start.setBounds(this.pan_main.getWidth() / 2 - 60, this.pan_main.getHeight() / 2 - 40, 100, 40);
		this.add(this.pan_main);
//		Student s=new Student();
//		s.setName("gjw");
//		//studentList.add(s);
//		Student s1=new Student();
//		s1=s;
//		s.setName("diao");
	}

	public class BtnStartAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			SetBtnState();
		}
	}

	private void SetBtnState() {
		// FormatStringToDate("2018/4/20 17:14:31",new SimpleDateFormat("yyyy/MM/dd
		// HH:mm:ss")); 日期
		// FormatStringToDate();
		// DateToWeek();
		/*
		 * for (Student s : studentList) { s.setName("你亲爹"); }
		 */
		/*
		 * Student s1=new Student(); s1.setName("gjw"); SetStuName(s1);
		 */
		if (btnState == true) {
			this.btn_Start.setText("停止");
			system_status = true;
			btnState = false;
			ServerListener sl = new ServerListener();
			t1 = new Thread(sl);
			t1.start();
			Thread t2 = new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						ServerSocket serverSocket = new ServerSocket(8000);
						// byte aaa = (byte) 0xBD;
						// int bbbb = aaa & 0xff;
						Socket socket = null;
						Integer count = 0;
						while (true) {
							System.out.println("[公务电话模拟线程]: 等待连接...");
							socket = serverSocket.accept();
							ServerThread serverThread = new ServerThread(socket);
							serverThread.start();
							count++;
							System.out.println("[公务电话模拟线程]: 有客户端连接：" + count.toString());
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			t2.start();
			Thread t3 = new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						ServerSocket serverSocket = new ServerSocket(5555);
						// byte aaa = (byte) 0xBD;
						// int bbbb = aaa & 0xff;
						Socket socket = null;
						Integer count = 0;
						while (true) {
							System.out.println("[时钟系统模拟线程]: 等待连接...");
							socket = serverSocket.accept();
							ServerThread serverThread = new ServerThread(socket);
							serverThread.start();
							count++;
							System.out.println("[时钟系统模拟线程]: 有客户端连接：" + count.toString());
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			t3.start();
			Thread t4 = new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						ServerSocket serverSocket = new ServerSocket(5100);
						// byte aaa = (byte) 0xBD;
						// int bbbb = aaa & 0xff;
						Socket socket = null;
						Integer count = 0;
						while (true) {
							System.out.println("[录音仪模拟线程]: 等待连接...");
							socket = serverSocket.accept();
							ServerThread serverThread = new ServerThread(socket);
							serverThread.start();
							count++;
							System.out.println("[录音仪模拟线程]: 有客户端连接：" + count.toString());
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			t4.start();
			Thread t5 = new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						ServerSocket serverSocket = new ServerSocket(8001);
						// byte aaa = (byte) 0xBD;
						// int bbbb = aaa & 0xff;
						Socket socket = null;
						Integer count = 0;
						while (true) {
							System.out.println("[网同步一期模拟线程]: 等待连接...");
							socket = serverSocket.accept();
							ServerThread serverThread = new ServerThread(socket);
							serverThread.start();
							count++;
							System.out.println("[网同步一期模拟线程]: 有客户端连接：" + count.toString());
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			t5.start();
			Thread t6 = new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						ServerSocket serverSocket = new ServerSocket(8888);
						// byte aaa = (byte) 0xBD;
						// int bbbb = aaa & 0xff;
						Socket socket = null;
						Integer count = 0;
						while (true) {
							System.out.println("[光缆配线架模拟线程]: 等待连接...");
							socket = serverSocket.accept();
							ServerThread serverThread = new ServerThread(socket);
							serverThread.start();
							count++;
							System.out.println("[光缆配线架模拟线程]: 有客户端连接：" + count.toString());
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			t6.start();
			Thread t7 = new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						ServerSocket serverSocket = new ServerSocket(9601);
						// byte aaa = (byte) 0xBD;
						// int bbbb = aaa & 0xff;
						Socket socket = null;
						Integer count = 0;
						while (true) {
							System.out.println("[PCM二期模拟线程]: 等待连接...");
							socket = serverSocket.accept();
							ServerThread serverThread = new ServerThread(socket);
							serverThread.start();
							count++;
							System.out.println("[PCM二期模拟线程]: 有客户端连接：" + count.toString());
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			t7.start();
			Thread t8 = new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						ServerSocket serverSocket = new ServerSocket(8040);
						// byte aaa = (byte) 0xBD;
						// int bbbb = aaa & 0xff;
						Socket socket = null;
						Integer count = 0;
						while (true) {
							System.out.println("[网同步二期模拟线程]: 等待连接...");
							socket = serverSocket.accept();
							ServerThread serverThread = new ServerThread(socket);
							serverThread.start();
							count++;
							System.out.println("[网同步二期模拟线程]: 有客户端连接：" + count.toString());
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			t8.start();
			Thread t9 = new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						ServerSocket serverSocket = new ServerSocket(3355);
						// byte aaa = (byte) 0xBD;
						// int bbbb = aaa & 0xff;
						Socket socket = null;
						Integer count = 0;
						while (true) {
							System.out.println("[光路检测模拟线程]: 等待连接...");
							socket = serverSocket.accept();
							ServerThread serverThread = new ServerThread(socket);
							serverThread.start();
							count++;
							System.out.println("[光路检测模拟线程]: 有客户端连接：" + count.toString());
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			t9.start();
			Thread t10 = new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						ServerSocket serverSocket = new ServerSocket(502);
						// byte aaa = (byte) 0xBD;
						// int bbbb = aaa & 0xff;
						Socket socket = null;
						Integer count = 0;
						while (true) {
							System.out.println("[调度二期模拟线程]: 等待连接...");
							socket = serverSocket.accept();
							ServerThread serverThread = new ServerThread(socket);
							serverThread.start();
							count++;
							System.out.println("[调度二期模拟线程]: 有客户端连接：" + count.toString());
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			t10.start();
			
			Thread t11 = new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						ServerSocket serverSocket = new ServerSocket(10000);
						// byte aaa = (byte) 0xBD;
						// int bbbb = aaa & 0xff;
						Socket socket = null;
						Integer count = 0;
						while (true) {
							System.out.println("[调度一期模拟线程]: 等待连接...");
							socket = serverSocket.accept();
							ServerThread serverThread = new ServerThread(socket);
							serverThread.start();
							count++;
							System.out.println("[调度一期模拟线程]: 有客户端连接：" + count.toString());
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			t11.start();
			Thread t12 = new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						ServerSocket serverSocket = new ServerSocket(20002);
						// byte aaa = (byte) 0xBD;
						// int bbbb = aaa & 0xff;
						Socket socket = null;
						Integer count = 0;
						while (true) {
							System.out.println("[模拟线程]: 等待连接...");
							socket = serverSocket.accept();
							ServerThread serverThread = new ServerThread(socket);
							serverThread.start();
							count++;
							System.out.println("[调度一期模拟线程]: 有客户端连接：" + count.toString());
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			t12.start();
		} else {
			this.btn_Start.setText("启动");
			system_status = false;
			btnState = true;
		}
	}

	private void SetStuName(Student s) {
		s.setName("zyz");
	}

	public void ConnectHttpWeb() {
		try {
			URL url = new URL(
					"https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=0&rsv_idx=1&tn=monline_3_dg&wd=1&rsv_pq=eae2398f0003e766&rsv_t=1abejPNn7twFBEwhD%2Bwbjc%2BHSlOn0%2BSBgIanc1Me%2B%2FxpWmVQNRp9MhnwRNE1SQUp6ZrE&rqlang=cn&rsv_enter=0&rsv_sug3=2&rsv_sug1=2&rsv_sug7=101&inputT=1136&rsv_sug4=1769");
			HttpURLConnection httpuconn = (HttpURLConnection) url.openConnection();
			httpuconn.setRequestMethod("GET");
			httpuconn.setDoInput(true);
			httpuconn.setUseCaches(true);
			httpuconn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			httpuconn.connect();
			if (httpuconn.getResponseCode() == 200) {
				InputStream is = httpuconn.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				// 存放数据
				StringBuffer sbf = new StringBuffer();
				String temp = null;
				while ((temp = br.readLine()) != null) {
					sbf.append(temp);
					sbf.append("\r\n");
				}
				String result = sbf.toString();
				int i = 0;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public class ServerListener implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				ServerSocket serverSocket = new ServerSocket(11000);
				// byte aaa = (byte) 0xBD;
				// int bbbb = aaa & 0xff;
				Socket socket = null;
				Integer count = 0;
				while (true) {
					System.out.println("[传输系统模拟线程]: 等待连接...");
					socket = serverSocket.accept();
					ServerThread serverThread = new ServerThread(socket);
					serverThread.start();
					count++;
					System.out.println("[传输系统模拟线程]: 有客户端连接：" + count.toString());
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public class ServerThread extends Thread {
		private Socket socket = null;
		private InputStream ips = null;
		private OutputStream ops = null;

		/**
		 * 传输系统返回告警信息
		 */
		private String outputStr =
				"ALARM_NOTIF[notificationType=alarmList|currentAlarmId=314|friendlyName=1642EM_C11/r01sr1sl04/port#03-P|eventTime=20120220050236|eventType=communicationsAlarm|probableCause=Underlying Resource Unavailable|perceivedSeverity=major|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#3/0.4.0.371.0.7.1#11040301}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=313|friendlyName=1642EM_C11/r01sr1sl04/port#02-P|eventTime=20120220015419|eventType=communicationsAlarm|probableCause=Underlying Resource Unavailable|perceivedSeverity=major|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#3/0.4.0.371.0.7.1#11040201}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=310|friendlyName=1642EMC_C41/r01sr1sl01/port#14-#01-Vc12|eventTime=20120216024502|eventType=communicationsAlarm|probableCause=Server Signal Failure|perceivedSeverity=major|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#4/0.0.7.774.0.7.39#11011401}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=309|friendlyName=1642EM_C11/r01sr1sl04/port#08-P|eventTime=20120216024500|eventType=communicationsAlarm|probableCause=Underlying Resource Unavailable|perceivedSeverity=major|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#3/0.4.0.371.0.7.1#11040801}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=307|friendlyName=1642EM_C11|eventTime=20120214092845|eventType=equipmentAlarm|probableCause=MIB backup misaligned|perceivedSeverity=warning|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#3/0.3.0.2.7.9#1}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=306|friendlyName=1642EM_C11/r01sr1sl01/port#01-MsTTP-PMNE1d|eventTime=20120130052609|eventType=communicationsAlarm|probableCause=Unavailable Time|perceivedSeverity=major|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#3/0.0.7.774.0.7.16#11010101/2.9.2.11.7.25#8}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=305|friendlyName=1642EM_C11/r01sr1sl01/port#01-RsTTP-PMTR15|eventTime=20120114030217|eventType=communicationsAlarm|probableCause=Unavailable Time|perceivedSeverity=major|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#3/0.0.7.774.0.7.25#11010101/2.9.2.11.7.25#3}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=304|friendlyName=1642EM_C11/r01sr1sl01/port#01-RsTTP-PM1d|eventTime=20120114030217|eventType=communicationsAlarm|probableCause=Unavailable Time|perceivedSeverity=major|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#3/0.0.7.774.0.7.25#11010101/2.9.2.11.7.25#2}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=303|friendlyName=1642EM_C11/r01sr1sl04/port#05-P|eventTime=20120207020845|eventType=communicationsAlarm|probableCause=Underlying Resource Unavailable|perceivedSeverity=major|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#3/0.4.0.371.0.7.1#11040501}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=302|friendlyName=1642EM_C11/r01sr1sl05/port#01-OpS|eventTime=20120130024122|eventType=communicationsAlarm|probableCause=Loss Of Signal|perceivedSeverity=major|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#3/0.0.7.774.0.7.18#11050101}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=301|friendlyName=1642EM_C11/r01sr1sl01/port#01-OpS|eventTime=20120119025106|eventType=communicationsAlarm|probableCause=Loss Of Signal|perceivedSeverity=major|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#3/0.0.7.774.0.7.18#11010101}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=300|friendlyName=1642EM_C11/timingGenerator|eventTime=20120114025820|eventType=communicationsAlarm|probableCause=Loss Of Timing Sources|perceivedSeverity=minor|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#3/0.4.0.304.0.7.7#1}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=299|friendlyName=1642EM_C11/r01sr1/board#04|eventTime=20120130065651|eventType=equipmentAlarm|probableCause=Replaceable Unit Missing|perceivedSeverity=major|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#3/0.0.13.3100.0.7.20#1/0.0.13.3100.0.7.20#1/0.0.13.3100.0.7.20#4}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=298|friendlyName=1642EM_C11/r01sr1/board#07|eventTime=20120114030039|eventType=equipmentAlarm|probableCause=Battery Failure|perceivedSeverity=minor|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#3/0.0.13.3100.0.7.20#1/0.0.13.3100.0.7.20#1/0.0.13.3100.0.7.20#7}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=297|friendlyName=1642EM_C11/r01sr1sl01/daughter#02|eventTime=20120114030037|eventType=equipmentAlarm|probableCause=Replaceable Unit Missing|perceivedSeverity=major|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#3/0.0.13.3100.0.7.20#1/0.0.13.3100.0.7.20#1/0.0.13.3100.0.7.20#1/0.0.13.3100.0.7.20#2}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=296|friendlyName=1642EM_C11/r01sr1sl01/port#01-#01-Vc4|eventTime=20120130070522|eventType=communicationsAlarm|probableCause=Server Signal Failure|perceivedSeverity=major|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#3/0.0.7.774.0.7.42#11010101}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=295|friendlyName=1642EM_C11|eventTime=20120114030216|eventType=equipmentAlarm|probableCause=Cooling Fan Failure|perceivedSeverity=minor|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#3}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=294|friendlyName=1642EM_C11/r01sr1sl01/port#02-OpS|eventTime=20120203075631|eventType=communicationsAlarm|probableCause=Underlying Resource Unavailable|perceivedSeverity=major|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#3/0.0.7.774.0.7.18#11010201}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=289|friendlyName=62C62/r01sr1sl06/port#03-#01-Vc4|eventTime=20120214082342|eventType=communicationsAlarm|probableCause=Unequipped|perceivedSeverity=major|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#6/0.0.7.774.0.7.42#11060301}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=288|friendlyName=62C62|eventTime=20120118031107|eventType=equipmentAlarm|probableCause=And Battery Failure|perceivedSeverity=minor|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#6}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=287|friendlyName=62C62|eventTime=20120214081442|eventType=equipmentAlarm|probableCause=MIB backup misaligned|perceivedSeverity=warning|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#6/0.3.0.2.7.9#1}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=286|friendlyName=62C62/r01sr1sl06/port#01-MsTTP|eventTime=20120118031818|eventType=communicationsAlarm|probableCause=Communications Protocol Error|perceivedSeverity=major|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#6/0.0.7.774.0.7.16#11060101}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=285|friendlyName=62C62/r01sr1sl08/port#01-OpS|eventTime=20120118031447|eventType=communicationsAlarm|probableCause=Underlying Resource Unavailable|perceivedSeverity=major|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#6/0.0.7.774.0.7.18#11080101}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=284|friendlyName=62C62/r01sr1sl15/port#01-OpS|eventTime=20120118031503|eventType=communicationsAlarm|probableCause=Underlying Resource Unavailable|perceivedSeverity=major|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#6/0.0.7.774.0.7.18#11150101}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=283|friendlyName=62C62/r01sr1sl08/port#01-RsTTP|eventTime=20120118031447|eventType=communicationsAlarm|probableCause=Underlying Resource Unavailable|perceivedSeverity=major|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#6/0.0.7.774.0.7.25#11080101}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=282|friendlyName=62C62/r01sr1sl15/port#01-RsTTP|eventTime=20120118031456|eventType=communicationsAlarm|probableCause=Underlying Resource Unavailable|perceivedSeverity=major|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#6/0.0.7.774.0.7.25#11150101}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=281|friendlyName=62C62/r01sr1/board#07|eventTime=20120118031447|eventType=equipmentAlarm|probableCause=Replaceable Unit Problem|perceivedSeverity=major|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#6/0.0.13.3100.0.7.20#1/0.0.13.3100.0.7.20#1/0.0.13.3100.0.7.20#7}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=280|friendlyName=62C62/r01sr1/board#08|eventTime=20120118031447|eventType=equipmentAlarm|probableCause=Replaceable Unit Missing|perceivedSeverity=major|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#6/0.0.13.3100.0.7.20#1/0.0.13.3100.0.7.20#1/0.0.13.3100.0.7.20#8}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=279|friendlyName=62C62/r01sr1sl08/daughter#01|eventTime=20120118031447|eventType=equipmentAlarm|probableCause=Replaceable Unit Missing|perceivedSeverity=major|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#6/0.0.13.3100.0.7.20#1/0.0.13.3100.0.7.20#1/0.0.13.3100.0.7.20#8/0.0.13.3100.0.7.20#1}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=278|friendlyName=62C62/r01sr1/board#10|eventTime=20120118031447|eventType=equipmentAlarm|probableCause=Replaceable Unit Missing|perceivedSeverity=major|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#6/0.0.13.3100.0.7.20#1/0.0.13.3100.0.7.20#1/0.0.13.3100.0.7.20#10}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=277|friendlyName=62C62/r01sr1/board#12|eventTime=20120118031447|eventType=equipmentAlarm|probableCause=Replaceable Unit Missing|perceivedSeverity=major|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#6/0.0.13.3100.0.7.20#1/0.0.13.3100.0.7.20#1/0.0.13.3100.0.7.20#12}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=276|friendlyName=62C62/r01sr1/board#15|eventTime=20120118031456|eventType=equipmentAlarm|probableCause=Replaceable Unit Problem|perceivedSeverity=minor|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#6/0.0.13.3100.0.7.20#1/0.0.13.3100.0.7.20#1/0.0.13.3100.0.7.20#15}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=275|friendlyName=62C62/r01sr1sl15/daughter#01|eventTime=20120118031503|eventType=equipmentAlarm|probableCause=Replaceable Unit Missing|perceivedSeverity=major|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#6/0.0.13.3100.0.7.20#1/0.0.13.3100.0.7.20#1/0.0.13.3100.0.7.20#15/0.0.13.3100.0.7.20#1}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=274|friendlyName=62C62/r01sr1/board#22|eventTime=20120118031107|eventType=equipmentAlarm|probableCause=Replaceable Unit Problem|perceivedSeverity=major|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#6/0.0.13.3100.0.7.20#1/0.0.13.3100.0.7.20#1/0.0.13.3100.0.7.20#22}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=273|friendlyName=62C62/r01sr1sl08/port#01-#01-Au4P-TMe|eventTime=20120118031448|eventType=communicationsAlarm|probableCause=Server Signal Failure|perceivedSeverity=major|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#6/0.0.7.774.127.3.0.7.4#11080101/0.0.7.774.0.7.3#1/0.0.7.774.0.7.2#1/1.3.12.2.1006.54.0.0.7.261#1}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=272|friendlyName=62C62/r01sr1sl08/port#01-#01-Au4P-TMi|eventTime=20120118031448|eventType=communicationsAlarm|probableCause=Server Signal Failure|perceivedSeverity=major|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#6/0.0.7.774.127.3.0.7.4#11080101/0.0.7.774.0.7.3#1/0.0.7.774.0.7.2#1/1.3.12.2.1006.54.0.0.7.261#2}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=271|friendlyName=62C62/r01sr1sl10/port#02-VC12XV|eventTime=20120118031447|eventType=communicationsAlarm|probableCause=Underlying Resource Unavailable|perceivedSeverity=major|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#6/1.3.12.2.1006.54.0.0.7.410#11100201}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=270|friendlyName=62C62/r01sr1sl12/port#02-VC12XV|eventTime=20120118031447|eventType=communicationsAlarm|probableCause=Underlying Resource Unavailable|perceivedSeverity=major|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#6/1.3.12.2.1006.54.0.0.7.410#11120201}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=269|friendlyName=TSS192168011132|eventTime=20120214064656|eventType=equipmentAlarm|probableCause=Node Isolation|perceivedSeverity=critical|additionalText=Communication to the NE is down|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#200/0.3.0.2.7.13#3/0.3.12.2.1006.63.0.4.3.4278596#NotAv}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=268|friendlyName=TSS192168011132|eventTime=20120214064656|eventType=equipmentAlarm|probableCause=NE Not Reach Via Primary Mng Interf|perceivedSeverity=critical|additionalText=TL1 Communication to the NE is down|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#200/0.3.0.2.7.13#3/0.3.12.2.1006.63.0.4.3.4278596#NotAv}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=267|friendlyName=TSS192168011132|eventTime=20120214064047|eventType=equipmentAlarm|probableCause=NE Not Reach Via Secondary Mng Interf|perceivedSeverity=critical|additionalText=LabVv_132|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={1.3.12.2.1006.63.1.7.38#200/0.3.0.2.7.13#3/0.3.0.2.7.9#1}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=266|friendlyName=TSS192168011132/NE,EQPT,NEND|eventTime=20120214110601|eventType=equipmentAlarm|probableCause=Initialization|perceivedSeverity=minor|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#200/0.3.0.2.7.13#3/0.3.12.2.1006.63.0.4.3.4278596#NotAv}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=262|friendlyName=TSS192168011131|eventTime=20120209020200|eventType=equipmentAlarm|probableCause=Node Isolation|perceivedSeverity=critical|additionalText=Communication to the NE is down|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#200/0.3.0.2.7.13#2/0.3.12.2.1006.63.0.4.3.4278596#NotAv}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=261|friendlyName=TSS192168011131|eventTime=20120209020200|eventType=equipmentAlarm|probableCause=NE Not Reach Via Primary Mng Interf|perceivedSeverity=critical|additionalText=TL1 Communication to the NE is down|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#200/0.3.0.2.7.13#2/0.3.12.2.1006.63.0.4.3.4278596#NotAv}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=257|friendlyName=TSS192168011131|eventTime=20120209015545|eventType=equipmentAlarm|probableCause=NE Not Reach Via Secondary Mng Interf|perceivedSeverity=critical|additionalText=LabVV_131|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={1.3.12.2.1006.63.1.7.38#200/0.3.0.2.7.13#2/0.3.0.2.7.9#1}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=253|friendlyName=1642EMC_C41/r01sr1sl01/port#05-#01-Vc12|eventTime=20120207020846|eventType=communicationsAlarm|probableCause=Server Signal Failure|perceivedSeverity=major|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#4/0.0.7.774.0.7.39#11010501}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=232|friendlyName=1642EMC_C41/r01sr1sl01/port#01-MsTTP-PMNETR15|eventTime=20120131063002|eventType=communicationsAlarm|probableCause=Unavailable Time|perceivedSeverity=major|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#4/0.0.7.774.0.7.16#11010101/2.9.2.11.7.25#9}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=231|friendlyName=1642EMC_C41/r01sr1sl01/port#01-OpS|eventTime=20120130070448|eventType=communicationsAlarm|probableCause=Loss Of Signal|perceivedSeverity=major|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#4/0.0.7.774.0.7.18#11010101}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=230|friendlyName=1642EMC_C41/r01sr1sl01/port#02-OpS|eventTime=20120130022806|eventType=communicationsAlarm|probableCause=Loss Of Signal|perceivedSeverity=major|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#4/0.0.7.774.0.7.18#11010201}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=228|friendlyName=1642EMC_C41/r01sr1sl01/port#02-#01-Vc4|eventTime=20120131015907|eventType=communicationsAlarm|probableCause=Server Signal Failure|perceivedSeverity=major|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#4/0.0.7.774.0.7.42#11010201}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=227|friendlyName=1642EMC_C41/r01sr1/board#03|eventTime=20120114025947|eventType=equipmentAlarm|probableCause=Battery Failure|perceivedSeverity=minor|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#4/0.0.13.3100.0.7.20#1/0.0.13.3100.0.7.20#1/0.0.13.3100.0.7.20#3}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=226|friendlyName=1642EMC_C41/timingGenerator|eventTime=20120114025946|eventType=communicationsAlarm|probableCause=Loss Of Timing Sources|perceivedSeverity=minor|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#4/0.4.0.304.0.7.7#1}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=225|friendlyName=1642EMC_C41/T3T6#A-T0SyncPu|eventTime=20120114025946|eventType=communicationsAlarm|probableCause=Server Signal Failure|perceivedSeverity=minor|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#4/0.0.7.774.127.3.0.7.6#51/0.0.7.774.127.3.0.7.11#3}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=224|friendlyName=1642EMC_C41/r01sr1sl01/port#01-T0SyncPu|eventTime=20120114025946|eventType=communicationsAlarm|probableCause=Server Signal Failure|perceivedSeverity=minor|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#4/0.0.7.774.127.3.0.7.6#51/0.0.7.774.127.3.0.7.11#11010101}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=207|friendlyName=1642EM_C11/r01sr1sl02/ETHLocPort#5#1|eventTime=20120130083944|eventType=communicationsAlarm|probableCause=Loss Of Signal|perceivedSeverity=major|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#202/0.3.0.2.7.13#1}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=206|friendlyName=1642EM_C11/r01sr1sl02/ETHLocPort#4#1|eventTime=20120130083944|eventType=communicationsAlarm|probableCause=Loss Of Signal|perceivedSeverity=major|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#202/0.3.0.2.7.13#1}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=54|friendlyName=TSS192168011131/NE,EQPT,NEND|eventTime=20120118075016|eventType=equipmentAlarm|probableCause=Initialization|perceivedSeverity=minor|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#200/0.3.0.2.7.13#2/0.3.12.2.1006.63.0.4.3.4278596#NotAv}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=323|friendlyName=1642EMC_C41|eventTime=20120223210404|eventType=equipmentAlarm|probableCause=Node Isolation|perceivedSeverity=critical|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#4/0.3.0.2.7.9#1}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=320|friendlyName=1642EMC_C41/r01sr1sl01/port#02-#01-Vc12|eventTime=20120220015422|eventType=communicationsAlarm|probableCause=Server Signal Failure|perceivedSeverity=major|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#4/0.0.7.774.0.7.39#11010201}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=319|friendlyName=1642EMC_C41/r01sr1sl01/port#02-#01-Vc12-PMNETR15|eventTime=20120221081501|eventType=communicationsAlarm|probableCause=Unavailable Time|perceivedSeverity=major|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#4/0.0.7.774.0.7.39#11010201/2.9.2.11.7.25#9}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=318|friendlyName=1642EMC_C41/r01sr1sl01/port#03-#01-Vc12|eventTime=20120220050238|eventType=communicationsAlarm|probableCause=Server Signal Failure|perceivedSeverity=major|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#4/0.0.7.774.0.7.39#11010301}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=317|friendlyName=1642EMC_C41/r01sr1sl01/port#03-#01-Vc4|eventTime=20120220072117|eventType=communicationsAlarm|probableCause=Server Signal Failure|perceivedSeverity=major|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#4/0.0.7.774.0.7.42#11010301}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=316|friendlyName=1642EMC_C41/r01sr1sl01/port#02-P|eventTime=20120220015420|eventType=communicationsAlarm|probableCause=Loss Of Signal|perceivedSeverity=major|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#4/0.4.0.371.0.7.1#11010201}|ASid=1]\r\n"
				+ "ALARM_NOTIF[notificationType=alarmList|currentAlarmId=315|friendlyName=1642EMC_C41/r01sr1sl01/port#03-P|eventTime=20120220050237|eventType=communicationsAlarm|probableCause=Loss Of Signal|perceivedSeverity=major|additionalText=|acknowledgementStatus=notAck|reservationStatus=notReserved|managedobjectInstance={0.3.0.2.7.7#201/0.3.0.2.7.13#4/0.4.0.371.0.7.1#11010301}|ASid=1]";

		/**
		 * 时钟系统返回告警信息
		 * 
		 * @category 7E 05 00 0A 01 02 14 06 06 1E 0B 23 01 10
		 * @category 7E: 起始位
		 * @category 05: 子系统告警（时钟）
		 * @category 00: 站名编码（小营）
		 * @category 0A: 告警位置(一级母钟接口板1)
		 * @category 01: 告警代码（故障）
		 * @category 02: 告警性质（严重告警）
		 * @category 14 06 06 1E 0B 23 01: 2006年6月30日11点35分1秒
		 * @category 10: 结束位
		 * 
		 */
		private byte[] clockWarnData1 = { 0x7E, 0x05, 0x00/* 小营 */, 0x0A/* 一级母钟接口板1 */, (byte) 0x01/* 故障，元器件失效 */,
				0x01/* 一般 */, 0x14, 0x06, 0x06, 0x1E, 0x0B, 0x23, 0x01/* 2006.06.30-11:35:01 */, 0x10 };
		private byte[] clockWarnData2 = { 0x7E, 0x05, 0x01/* 西直门 */, (byte) (178 & 0xFF)/* 车站接口板12 */,(byte) 0x84/* 模块输出信号超出设定误差 */,
				0x02/* 严重 */, 0x14, 0x06, 0x06, 0x1E, 0x0B, 0x23, 0x01, 0x10 };
		private byte[] clockWarnData3 = { 0x7E, 0x05, 0x02/* 小营二期 */, (byte) (76 & 0xFF)/* 子钟接口板（线路一级母钟） */,(byte) 0x87/* 小营二期二区子钟故障 */,
				0x03/* 致命 */, 0x14, 0x06, 0x06, 0x1E, 0x0B, 0x23, 0x01, 0x10 };
		private byte[] clockWarnData4 = { 0x7E, 0x05, 0x00/* 小营 */, (byte) (31 & 0xFF)/* 五号子钟 */,(byte) 0x89/* 小营二期二区子钟反馈故障 */,
				0x01/* 一般 */, 0x14, 0x06, 0x06, 0x1E, 0x0B, 0x23, 0x01, 0x10 };
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/**
		 * 公务电话系统
		 * 
		 */
		private byte[] publicphone = { 0x7E, 0x7E, 0x7E, 0x03, 0x00, 0x09, 0x03, 0x09, 0x00, (byte) 0xff, 0x01, 0x01,
				0x01, 0x01, 0x01, 0x01, 0x14, 0x13, 0x01, 0x11, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };

		/**
		 * PCM2期系统
		 * 
		 */
		private byte[] pcmData = { 0x7E, 0x7E, 0x7E, 0x0B, 0x00, 0x09, 0x03, 0x09, 0x00, (byte) 0xff, 0x01, 0x01, 0x01,
				0x01, 0x01, 0x01, 0x14, 0x13, 0x01, 0x11, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
		/**
		 * 光缆配线架系统
		 * 
		 */
		private byte[] Fib1NoIDData = { 0x7E, 0x7E, 0x7E, 0x08, 0x04, 0x01, 0x28, 0x00, 0x00, 0x02, 0x00, 0x03, 0x00,
				0x01, 0x03, 0x00, 0x39, 0x14, 0x12, 0x0A, 0x19, 0x0C, 0x06, 0x3A, 0x00, 0x00, (byte) 0x86, (byte) 0xBB,
				0x7E, 0x7E, 0x7E, 0x08, 0x15, 0x01, 0x28, 0x00, 0x00, 0x02, (byte) 0xFF, 0x03, 0x00, 0x01, 0x01, 0x00,
				0x0A, 0x14, 0x12, 0x0A, 0x19, 0x0C, 0x03, 0x07, 0x00, 0x00, 0x2F, (byte) 0xBB, (byte) 0xBB, 0x7E, 0x7E,
				0x7E, 0x08, 0x0F, 0x02, 0x28, 0x00, 0x00, 0x00, (byte) 0xFF, 0x03, 0x00, 0x01, 0x01, 0x00, 0x0B, 0x14,
				0x12, 0x0A, 0x19, 0x0C, 0x03, 0x07, 0x00, 0x00, 0x29 };

		public ServerThread(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				byte[] bb = null;
				int count=0;
				while (system_status && count<400) {
					ips = socket.getInputStream();
					if (ips.available() > 0) {
						count=0;
						bb = new byte[ips.available()];
						ips.read(bb);
						String info2 = "";
						for (int i = 0; i < bb.length; i++) {
							info2 += Integer.toString(bb[i] & 0xFF);
							if (i != bb.length - 1)
								info2 += ",";
						}
/*						String info = new String(bb, "UTF-8");
						if (info.equals("START_UNSOL_ALARMS_REQ[]")) {
							System.out.println("[传输系统]: 接收数据 info2：" + info2);
							System.out.println("[传输系统]: 接收数据[转成UTF-8] info：" + info);
							System.out.println();
							ops = socket.getOutputStream();
							ops.write(outputStr.getBytes("ASCII"));
							ops.flush();
						} else if (info.equals("CON_REQ[]")) {
							System.out.println("[传输系统]: 接收数据 info2：" + info2);
							System.out.println("[传输系统]: 接收数据[转成UTF-8] info：" + info);
							System.out.println();
							ops = socket.getOutputStream();
							ops.write("CON_CONF[]".getBytes("ASCII"));
							//ops.write(outputStr.getBytes("ASCII"));
							ops.flush();
						} else if (bb[0] == (byte) 0xFA && bb[1] == (byte) (5 & 0xFF)) { // 时钟系统被询问，返回告警响应结束命令
							System.out.println("[时钟系统]: 接收数据 info2：" + info2 + ",时钟系统被询问");
							System.out.println("[时钟系统]: 接收数据[转成UTF-8] info：" + info);
							System.out.println();
							ops = socket.getOutputStream();
							bb = new byte[2];
							bb[0] = (byte) 0xFA;
							bb[1] = (byte) 0xFA;
							ops.write(bb);
							ops.flush();
						} else if (bb[0] == (byte) 0xAA && bb[1] == (byte) (5 & 0xFF)) { // 接收握手命令，返回握手命令
							System.out.println("[时钟系统]: 接收数据 info2：" + info2 + ",接收握手命令");
							System.out.println("[时钟系统]: 接收数据[转成UTF-8] info：" + info);
							System.out.println();
							ops = socket.getOutputStream();
							bb = new byte[2];
							bb[0] = (byte) 0xFF;
							bb[1] = (byte) 0xFA;
							ops.write(bb);
							ops.flush();
							try {
								Thread.sleep(10000);
								sendCommend(clockWarnData1, socket);
								sendCommend(clockWarnData2, socket);
								sendCommend(clockWarnData3, socket);
								sendCommend(clockWarnData4, socket);
								System.out.println("[时钟系统]: 返回給時鐘系統告警信息");
							} catch (InterruptedException e) { // TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else if (bb[0] == (byte) 0xFA && bb[1] == (byte) (6 & 0xFF)) {
							System.out.println("[录音仪]: 接收数据 info2：" + info2);
							System.out.println("[录音仪]: 接收数据[转成UTF-8] info：" + info);
							System.out.println();
							ops = socket.getOutputStream();
							bb = new byte[2];
							bb[0] = (byte) 0xFB;
							bb[1] = (byte) 0xFA;
							ops.write(bb);
							ops.flush();

						} else if (info.contains("HEARTBEAT_REQ[]")) {
							ops = socket.getOutputStream();
							//ops.write("HEARTBEAT_CONF[]".getBytes("ASCII"));
							//ops.write(outputStr.getBytes("ASCII"));
							ops.flush();
						} else if (bb.length == 4 && ((bb[0] == (byte) 0xFA && bb[3] == (byte) (4 & 0xFF))
								|| (bb[0] == (byte) 0xFF && bb[3] == (byte) (4 & 0xFF)))) {
							System.out.println("[公务电话]: 系统接收到请求数据命令,info：" + info);
							System.out.println();
							int countSum = 0;
							for (int i = 0; i < publicphone.length - 1; i++) {
								countSum += (publicphone[i] & 0xFF);
							}
							publicphone[26] = (byte) (countSum & 0xFF);
							ops = socket.getOutputStream();
							ops.write(publicphone);
							ops.flush();
						} else if (bb.length == 4 && ((bb[0] == (byte) 0xFA && bb[3] == (byte) (11 & 0xFF))
								|| (bb[0] == (byte) 0xFF && bb[3] == (byte) (11 & 0xFF)))) {
							System.out.println("[PCM二期]: 系统接收到请求数据命令,info: " + info);
							System.out.println();
							int countSum = 0;
							for (int i = 0; i < pcmData.length - 1; i++) {
								countSum += (pcmData[i] & 0xFF);
							}
							pcmData[26] = (byte) (countSum & 0xFF);
							ops = socket.getOutputStream();
							ops.write(pcmData);
							ops.flush();

						} else if (bb.length == 4 && (bb[0] == (byte) 0xFA && bb[3] == (byte) (8 & 0xFF))) {
							// || (bb[0]== (byte) 0xFF && bb[3] == (byte) (8 & 0xFF))
							System.out.println("[光缆配线架]: 系统接收到请求数据命令,info:" + info);
							int countSum = 0;
							for (int i = 0; i < Fib1NoIDData.length - 1; i++) {
								countSum += (Fib1NoIDData[i] & 0xFF);
							}
							Fib1NoIDData[26] = (byte) (countSum & 0xFF);
							ops = socket.getOutputStream();
							ops.write(Fib1NoIDData);
							ops.flush();

						}*/

						bb = null;
					}
					try {
						Thread.sleep(500);
						count++;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				System.out.println("！！！！！！！！！！！！！！！！！！！！！！退出线程！！！！！！！！！！！！！！！！！！！！！！！！！！！！！");
			} catch (

			IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		public boolean sendCommend(byte[] send, Socket socket) {
			boolean success = false;
			try {
				ops = socket.getOutputStream();
				ops.write(send);
				ops.flush();
				success = true;
			} catch (Exception ex) {
				success = false;
			}
			return success;
		}

	}

	public static int byte2Int(byte[] b) {
		int intValue = 0;
		for (int i = 0; i < b.length; i++) {
			intValue += (b[i] & 0xFF) << (8 * (1 - i));
		}
		return intValue;
	}

	/**
	 * String转Date
	 */
	public void FormatStringToDate() {
		Date aa = new Date();
		String aaaa = DateToFormatString();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			aa = sdf.parse(aaaa);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long asdf = aa.getTime();
	}

	/**
	 * String转Date
	 */
	public void FormatStringToDate(String dateStr, SimpleDateFormat sdf) {
		if (dateStr.length() < 19) {
			if (dateStr.contains(":")) {
				// dateStr="0000-00-00 "+dateStr;
			}

		}
		Date aa = new Date();
		try {
			aa = sdf.parse(dateStr);
			Calendar cld = Calendar.getInstance();
			cld.setTime(aa);
			int sdfasdf = cld.get(Calendar.HOUR_OF_DAY);
			int asdfasa = cld.get(Calendar.MINUTE);
			int asdfasdf = Integer.parseInt("17:20".substring(0, 2));
			int sda = 1;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long asdf = aa.getTime();
	}

	/**
	 * 日期转换成星期
	 */
	public void DateToWeek() {
		SimpleDateFormat formatter = new SimpleDateFormat("E");
		String str = formatter.format(new Date());
		int i = 0;
	}

	/**
	 * Date转String
	 */
	public String DateToFormatString() {
		Date aa = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String aaaa = sdf.format(aa);
		return aaaa;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		 * SnmpTest st=new SnmpTest(); st.InitFactory(SnmpConstants.version2c); String
		 * IPAddress="127.0.0.1"; String oid="1.3.6.1.2.1.25.2.3.1.6";
		 * st.SnmpWalk(IPAddress, oid);
		 */
		MainForm mf = new MainForm();
		mf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mf.setVisible(true);
		// mf.show();
	}
}
