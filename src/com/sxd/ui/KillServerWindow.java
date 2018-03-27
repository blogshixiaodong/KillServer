package com.sxd.ui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

import com.sxd.killServer.core.KillServer;
import com.sxd.ui.menu.dialog.Help_About_Dialog;

public class KillServerWindow extends JFrame {
	
	private JLabel lPort = new JLabel("����˿ں�:");
	private JTextField tPort = new JTextField();
	
	private JButton bKill = new JButton("��������");
	
	private Font DEFAULT_FONT = new Font("����",Font.BOLD, 16);
	
	
	public KillServerWindow() {
		InitWindow();
	}
	
	private void createMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menuHelp = new JMenu("����");
		menuHelp.setFont(DEFAULT_FONT);
		
		JMenuItem menuHelpAbout = new JMenuItem("����");
		menuHelpAbout.setFont(DEFAULT_FONT);
		menuHelpAbout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Help_About_Dialog();
			}
			
		});
		
		
		menuBar.add(menuHelp);
		menuHelp.add(menuHelpAbout);
		this.setJMenuBar(menuBar);
		
	}
	
	private void InitWindow() {
		this.setTitle("Kill Server");
		this.setResizable(false);
		this.setBounds(0, 0, 450, 150);
		setWidowLocation();
		createMenu();
		this.setVisible(true);
	
		Container container = this.getContentPane();
		container.setLayout(null);
		
		lPort.setFont(DEFAULT_FONT);
		lPort.setBounds(new Rectangle(10, 30, 100, 30));
		
		tPort.setFont(DEFAULT_FONT);
		tPort.setBounds(new Rectangle(120, 30, 160, 30));
		
		bKill.setFont(DEFAULT_FONT);
		bKill.setBounds(new Rectangle(300, 30, 120, 30));
		bKill.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				KillServerByPort(tPort.getText().split(","));
			}
			
		});
		
		
		//add control
		container.add(lPort);
		container.add(tPort);
		container.add(bKill);
		
		//�رմ��ںͽ���
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
	}
	
	
	private void KillServerByPort(String[] ports) {
		KillServer kill = new KillServer(ports);
		try {
			kill.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void setWidowLocation() {
		 Toolkit kit = Toolkit.getDefaultToolkit(); 		// ���幤�߰� 
		 Dimension screenSize = kit.getScreenSize(); 		// ��ȡ��Ļ�ĳߴ� 
		 int screenWidth = screenSize.width/2; 				// ��ȡ��Ļ�Ŀ�
		 int screenHeight = screenSize.height/2; 			// ��ȡ��Ļ�ĸ�
		 int height = this.getHeight(); 
		 int width = this.getWidth(); 
		 this.setLocation(screenWidth-width/2, screenHeight-height/2 - 100);
	}
	
	public static void main(String[] args) {
		KillServerWindow frame = new KillServerWindow();
	    
	}
}
