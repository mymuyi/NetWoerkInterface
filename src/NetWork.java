import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class NetWork extends JFrame{
	
	public NetWork(Enumeration<NetworkInterface> en) throws SocketException {
		
		addTitle(this, "����������Ϣ", 10);
		
		Label type = new Label("�������ͣ�");
		type.setFont(new Font("΢���ź�",Font.PLAIN,14));
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setFont(new Font("΢���ź�",Font.PLAIN,14));
		
		while (en.hasMoreElements()) {
			NetworkInterface ni = en.nextElement();
			if(ni.getHardwareAddress() == null) {
				continue;
			}
			comboBox.addItem(ni.getName() + "������������������������");
		}
		JButton detail = new JButton("��ϸ");
		detail.setFont(new Font("΢���ź�",Font.PLAIN,14));
		JPanel panel1 = new JPanel();
		panel1.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel1.add(type);
		panel1.add(comboBox);
		panel1.add(detail);
		panel1.setBounds(20, 40, 460, 40);
		
		// ��ϸ
		detail.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String name = (String) comboBox.getSelectedItem();
					name = name.substring(0, name.length() - 12);
					
					new Detail(NetworkInterface.getByName(name));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		addTitle(this, "IPv4 ����", 110);
		
		JPanel ipv4 = new JPanel();
		Label lb1 = new Label("IP����ַ��");
		JTextField ipText = new JTextField(20);
		addComponent(ipv4, lb1, ipText, 150);
		
		JPanel subNetMask = new JPanel();
		Label lb2 = new Label("�������룺");
		JTextField subText = new JTextField(20);
		addComponent(subNetMask, lb2, subText, 190);
		
		JPanel gateWay = new JPanel();
		Label lb3 = new Label("Ĭ�����أ�");
		JTextField gateText = new JTextField(20);
		addComponent(gateWay, lb3, gateText, 230);
		
		JPanel dns = new JPanel();
		Label lb4 = new Label("��DNS����");
		JTextField dnsText = new JTextField(20);
		addComponent(dns, lb4, dnsText, 270);
		
		JPanel button = new JPanel();
		JButton save = new JButton("����");
		JButton reset = new JButton("����");
		Label spaceHolder = new Label("��������������");
		addButton(button, save, spaceHolder, reset, 330);
		
		addTitle(this, "���ܿ���", 390);
		
		JPanel OnOff = new JPanel();
		JButton off = new JButton("����");
		JButton on = new JButton("����");
		Label spaceHoder1 = new Label("��");
		off.setFont(new Font("΢���ź�",Font.PLAIN,14));
		on.setFont(new Font("΢���ź�",Font.PLAIN,14));
		OnOff.add(off);
		OnOff.add(spaceHoder1);
		OnOff.add(on);
		OnOff.setBounds(20, 430, 400, 40);
		OnOff.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		// ��������
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String str = new String();
				if(ipText.getText().equals("") || subText.getText().equals("") || 
						gateText.equals("") || dnsText.getText().equals("")) {
					str = "��Ϣ��д������������д����";
				} else {
					String IPCmd1 = "netsh interface ip set address \"WLAN\" static " + ipText.getText() + " " 
							+ subText.getText() + " " + gateText.getText() + " 1";
					String DNSCmd1 = "netsh interface ip set dns \"WLAN\" static " + dnsText.getText();
					String IPCmd2 = "netsh interface ip set address \"��̫��\" static " + ipText.getText() + " " 
							+ subText.getText() + " " + gateText.getText() + " 1";
					String DNSCmd2 = "netsh interface ip set dns \"��̫��\" static " + dnsText.getText();
					List<String> cmdList = new ArrayList<>();
					cmdList.add(IPCmd1);
					cmdList.add(DNSCmd1);
					cmdList.add(IPCmd2);
					cmdList.add(DNSCmd2);
					str = RunTime(cmdList);
					str = str.substring(str.length() / 2);
				}
				showDialog(str);
			}
		});
		
		// ����
		reset.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String str = new String();
				List<String> cmdList = new ArrayList<>();
				cmdList.add("netsh interface ip set address \"WLAN\" source=dhcp");
				cmdList.add("netsh interface ip set dns name=\"WLAN\" source=dhcp");
				cmdList.add("netsh interface ip set address \"��̫��\" source=dhcp");
				cmdList.add("netsh interface ip set dns name=\"��̫��\" source=dhcp");
				str = RunTime(cmdList);
				showDialog(str.substring(str.length() / 2));
			}	
		});
		
		// ����
		off.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String str = new String();
				List<String> cmdList = new ArrayList<>();
				cmdList.add("netsh interface set interface wlan disabled");
				cmdList.add("netsh interface set interface ��̫��  disabled");
				str = RunTime(cmdList);
				//System.out.println(str);
				if(str.length() == 0) {
					str = "���óɹ�";
				} else {
					str = "����ʧ��";
				}
				showDialog(str);
			}
		});
		
		// ����
		on.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String str = new String();
				List<String> cmdList = new ArrayList<>();
				cmdList.add("netsh interface set interface wlan enabled");
				cmdList.add("netsh interface set interface ��̫��  enabled");
				str = RunTime(cmdList);
				//System.out.println(str);
				if(str.length() == 0) {
					str = "�����ɹ�";
				} else {
					str = "����ʧ��";
				}
				showDialog(str);
			}
		});
		
		this.setLayout(null);
		this.add(panel1);
		this.add(ipv4);
		this.add(subNetMask);
		this.add(gateWay);
		this.add(dns);
		this.add(button);
		this.add(OnOff);
		this.setTitle("NICinfo");
		this.setLocation(200, 70);
		// ���ò�������
		this.setResizable(false);
		this.setSize(450, 520);
		this.setVisible(true);
	}
	
	private String RunTime(List<String> cmdList) {
		StringBuilder str = new StringBuilder();
		Iterator<String> it = cmdList.iterator();
		String line = "";
		while(it.hasNext()) {
			try {
				Process ps = Runtime.getRuntime().exec(it.next().toString());
				BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream())); 
				while((line = br.readLine()) != null) {
					System.out.println(line);
					str.append(line);
				}
				br.close();

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return str.toString();
	}
	
	private void addTitle(JFrame f, String str, int Y) {
		Label title = new Label(str);
		title.setFont(new Font("΢���ź�",Font.BOLD,20));
		title.setForeground(Color.BLUE);
		title.setBounds(10, Y, 400, 20);
		f.add(title);
	}
	
	private void addComponent(JPanel panel, Label label, JTextField field, int Y) {
		label.setFont(new Font("΢���ź�",Font.PLAIN,14));
		field.setFont(new Font("΢���ź�",Font.PLAIN,14));
		panel.add(label);
		panel.add(field);
		panel.setBounds(18, Y, 400, 40);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
	}
	
	private void addButton(JPanel panel, JButton button1, Label label, JButton button2, int Y) {
		button1.setFont(new Font("΢���ź�",Font.PLAIN,14));
		button2.setFont(new Font("΢���ź�",Font.PLAIN,14));
		panel.add(button1);
		panel.add(label);
		panel.add(button2);
		panel.setBounds(18, Y, 400, 40);
		panel.setLayout(new FlowLayout());
	}
	
	private void showDialog(String str) {
		JDialog dialog = new JDialog();
		JTextArea information = new JTextArea(8,13);
		information.setEditable(false);
		information.setLineWrap(true);
		information.setFont(new Font("΢���ź�",Font.PLAIN,14));
		dialog.setLocation(330, 220);
		dialog.setSize(200, 200);
		dialog.setLayout(new FlowLayout(FlowLayout.CENTER));
		information.setText(str);
		dialog.add(information);
		dialog.setResizable(false);
		dialog.setVisible(true);
	}
	
	public static void main(String []args) throws SocketException {
		Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
		new NetWork(en);
	}
}
