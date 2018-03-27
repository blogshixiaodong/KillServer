package com.sxd.ui.menu.dialog;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Help_About_Dialog extends JFrame {
	private JLabel lAbout = new JLabel("develop by ShiXiaodong");
	
	private Font DEFAULT_FONT = new Font("宋体",Font.BOLD, 16);
	
	public Help_About_Dialog() {
		InitWindow();
	}
	
	private void InitWindow() {
		this.setTitle("About Kill Server");
		this.setResizable(false);
		this.setBounds(0, 0, 600, 200);
		setWidowLocation();
		this.setVisible(true);
		this.getContentPane().setLayout(null);
		
		
		lAbout.setFont(DEFAULT_FONT);
		lAbout.setBounds(new Rectangle(10, 50, 300, 30));
		
		this.add(lAbout);
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	public void setWidowLocation() {
		 Toolkit kit = Toolkit.getDefaultToolkit(); 		// 定义工具包 
		 Dimension screenSize = kit.getScreenSize(); 		// 获取屏幕的尺寸 
		 int screenWidth = screenSize.width/2; 				// 获取屏幕的宽
		 int screenHeight = screenSize.height/2; 			// 获取屏幕的高
		 int height = this.getHeight(); 
		 int width = this.getWidth(); 
		 this.setLocation(screenWidth-width/2, screenHeight-height/2 - 100);
	}
	
}
