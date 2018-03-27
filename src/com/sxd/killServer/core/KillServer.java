package com.sxd.killServer.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class KillServer {
	private List<Integer> portList = new ArrayList<Integer>();
	
	public KillServer(String[] ports) {
		convertToInteger(Arrays.asList(ports));	//Array$ArrayList �ڲ��࣡���̶���С
	}
	
	public KillServer(List<String> ports) {
		convertToInteger(ports);
	}
	
	//������������
	public boolean start() throws IOException {
		Runtime runtime = Runtime.getRuntime();
		for(Integer port : portList) {
			Process process = runtime.exec("cmd /c netstat -ano | findstr \"" + port + "\"");
			InputStream inputStream = process.getInputStream();
			List<String> readPort = readPortResult(inputStream, "UTF-8");
			List<Integer> pidList = convertToPid(readPort);
			
			for(Integer pid : pidList) {
				killWidthPid(pid);
			}
		}
		return false;
	}
	
	//ͨ��pid��������
	public void killWidthPid(Integer pid) {
		try {
			Process process = Runtime.getRuntime().exec("taskkill /F /pid " +  pid);
			String txt = readPidResult(process.getInputStream(), "GBK");
			System.out.println(txt);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//�����캯������תΪInteger
	private void convertToInteger(List<String> list) {
		String  str = null;
		for(int i = 0; i < list.size(); i++) {
			try {
				str = list.get(i);
				int x = Integer.parseInt(str);
				this.portList.add(x);
			} catch(NumberFormatException e) {
				System.out.println("������ת������: " + str);
			}
		}
	}
	
	
		
	//���netstat�����Ч��
	private boolean isValiddPort(String portLine) {
		Pattern pattern = Pattern.compile("^ *[a-zA-Z]+ +\\S+");
		Matcher matcher = pattern.matcher(portLine);
		
		matcher.find();
		String find = matcher.group();
		int spstart = find.lastIndexOf(":");
		find = find.substring(spstart + 1);
		int port = -1;
		try { 
			port = Integer.parseInt(find);
		} catch(NumberFormatException e) {
			System.out.println("�˿ڴ���: " + find);
			return false;
		}
		if(this.portList.contains(port)) {
			return true;
		}
		//�ظ����
		return false;
	}
	
	//��netstat���ؽ����ȡpid
	private List<Integer> convertToPid(List<String> portLine) {
		List<Integer> pidList = new ArrayList<Integer>();
		for(String line : portLine) {
			int offset = line.lastIndexOf(" ");
			String pidString = line.substring(offset);
			pidString = pidString.replace(" ", "");
			int pid = -1;
			try {
				pid = Integer.parseInt(pidString);
			} catch(NumberFormatException e) {
				System.out.println("��ȡ���̺ų���: " + pidString);
			}
			if(!pidList.contains(pid)) {
				pidList.add(pid); 
			}
		}
		return pidList;
	}
	
	//ִ������ָ��� ���
	private String readPidResult(InputStream inputStream, String charset) throws IOException {
		List<String> result = getResultText(inputStream, charset);
		return result.toString();	
	}
	
	//ִ��netstat -ano | findstr "8080" �ķ��ؽ����
	private List<String> readPortResult(InputStream inputStream, String charset) throws IOException {
		List<String> result = getResultText(inputStream, charset);
		List<String> data = new ArrayList<>();
		for(String rec : result) {
			if(isValiddPort(rec)) {
				data.add(rec);
			}	
		}
		return data;
	}
	
	//��ȡִ��cmd�����ֵ
	private List<String> getResultText(InputStream inputStream, String charset) throws IOException {
		List<String> result = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, charset));
		String line = "";
		while((line = reader.readLine()) != null) {
			result.add(line);
		}
		return result;
	}
	
}
