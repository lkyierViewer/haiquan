package com.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

public class test extends JFrame implements Runnable {
	private JTextArea jt = new JTextArea(15, 20);
	private PrintWriter writer;
	private ServerSocket server;
	private int port;
	private InetAddress iaddress = null;
	private MulticastSocket socket = null;
	private boolean flag = true;
	Thread thread;
	JButton jb_s = new JButton("开始接收");
	JButton jb_e = new JButton("停止接收");

	public test() {
		init();
		thread = new Thread(this);
		jb_s.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jb_s.setBackground(Color.GRAY);
				jb_e.setBackground(Color.RED);
				port = 32768;
				try {
					iaddress = InetAddress.getLocalHost();
					socket = new MulticastSocket(port);
					socket.joinGroup(iaddress);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				thread.start();
			}
		});

		jb_e.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				flag = false;
				jb_s.setBackground(Color.GREEN);
				jb_e.setBackground(Color.GRAY);
			}
		});
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		jt.append("开始接收广播！！\n");
		while (true) {

			if (flag == false) {
				break;
			}

			byte data[] = new byte[1024];
			DatagramPacket packet = null;
			packet = new DatagramPacket(data, data.length, iaddress, port);
			try {
				socket.receive(packet);
				String msg = new String(data, "UTF-8");
				jt.append(msg + "\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public void init() {
		setTitle("client");
		setLocationRelativeTo(null);
		Container con = getContentPane();
		JScrollPane js = new JScrollPane(jt);

		JPanel jp = new JPanel();
		jp.setLayout(new FlowLayout());

		jb_s.setBackground(Color.GREEN);
		jb_e.setBackground(Color.RED);
		jp.add(jb_s);
		jp.add(jb_e);
		con.add(js, "North");
		con.add(jp, "South");
		setVisible(true);
		setSize(300, 400);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	}

	public static List<Integer> T_CurAlarmInfoList = Collections.synchronizedList(new ArrayList<>());

	public static void main(String[] agrs) {
		new test();
	}

}
