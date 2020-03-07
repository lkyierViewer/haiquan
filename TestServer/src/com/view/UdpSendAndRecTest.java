package com.view;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

public class UdpSendAndRecTest {

	private boolean startFlag = false;

	private DatagramSocket socketSend;
	private DatagramSocket socketReceive;

	private DatagramPacket packetSend;
	private DatagramPacket packetReceive;

	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private String sendhost = "255.255.255.255";
	private int sendPort = 32768;

	private int receivePort = 7666;

	private ConcurrentLinkedQueue<String> Q_SndData = new ConcurrentLinkedQueue<>();
	private byte[] indata;

	public void DoWork() {
		try {
			indata = new byte[16 * 1024]; // 16KB receive buffer
			socketReceive = new DatagramSocket(this.receivePort);
			packetReceive = new DatagramPacket(indata, indata.length);
			socketSend = new DatagramSocket();
		} catch (Exception ex) {
			ex.getMessage();
		} finally {
		}

	}

	/**
	 * 采集数据
	 */
	public void CollectData() {
		try {
			int inlen = 0;
			while (true) {
				socketReceive.receive(packetReceive);
				inlen = packetReceive.getLength();
				System.out.println(df.format(new Date()) + " 服务端接收：" + new String(packetReceive.getData(), "UTF-8"));
				Thread.sleep(3000);
				Q_SndData.add(new String(packetReceive.getData(), "UTF-8").replace("客户端", "服务端"));
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	/**
	 * 通过udp广播方式发送数据给客户端
	 */
	public void SendDataToClient() {
		try {
			String data = "";
			while (true) {
				while (!Q_SndData.isEmpty()) {
					data = Q_SndData.poll();
					if (data != null) {
						packetSend = new DatagramPacket(data.getBytes("UTF-8"), data.getBytes("UTF-8").length,
								InetAddress.getByName(sendhost), sendPort);
						socketSend.send(packetSend);
						// System.out.println(df.format(new Date())+" 发送："+data);
						Thread.sleep(3000);
					}
				}
				Thread.sleep(1000);
			}
		} catch (Exception ex) {
		}
	}

	public static void main(String args[]) {
		UdpSendAndRecTest udpsr = new UdpSendAndRecTest();
		udpsr.DoWork();
		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				udpsr.CollectData();
			}
		});
		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				udpsr.SendDataToClient();
			}
		});
		t1.start();
		t2.start();
	}
}
