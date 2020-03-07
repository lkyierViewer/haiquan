package com.view;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

public class UdpSendAndRecTest2 {

	private boolean startFlag = false;

	private DatagramSocket socketSend;
	private DatagramSocket socketReceive;

	private DatagramPacket packetSend;
	private DatagramPacket packetReceive;

	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private String sendhost = "255.255.255.255";
	private int sendPort = 7666;

	private int receivePort = 32768;

	private ConcurrentLinkedQueue<String> Q_SndData = new ConcurrentLinkedQueue<>();
	private byte[] indata;

	public void DoWork() {
		try {
			indata = new byte[16 * 1024]; // 16KB receive buffer
			socketReceive = new DatagramSocket(this.receivePort);
			packetReceive = new DatagramPacket(indata, indata.length);
			socketSend = new DatagramSocket();
		} catch (Exception ex) {
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
				System.out.println(df.format(new Date())+" 客户端接收： "+new String(packetReceive.getData(), "UTF-8"));
				Thread.sleep(2000);
				Q_SndData.add(new String(packetReceive.getData(), "UTF-8").replace("服务端", "客户端"));
			}
		} catch (Exception ex) {
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
			System.out.println("");
		}
	}

	public static void main(String args[]) {
		UdpSendAndRecTest2 udpsr = new UdpSendAndRecTest2();
		udpsr.DoWork();
		udpsr.Q_SndData.add("客户端 GJWTEST");
		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				udpsr.SendDataToClient();
			}
		});
		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				udpsr.CollectData();
			}
		});
		t1.start();
		t2.start();
	}
}
