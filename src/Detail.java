import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Label;
import java.util.List;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Detail extends JFrame{
	
	public Detail(NetworkInterface ni) throws Exception {
		
		Label detailInfo = new Label("��ϸ��Ϣ");
		detailInfo.setFont(new Font("΢���ź�",Font.PLAIN,20));
		detailInfo.setBounds(10, 10, 400, 20);
		detailInfo.setForeground(Color.BLUE);
		
		addJPanel("�������ͣ�", ni.getName(), this, 40);
		
		addJPanel("����״̬��", ni.isUp() == true?"������":"δ����", this, 70);
		
		addJPanel("������", ni.getDisplayName(), this, 100);
		
		addJPanel("MAC ��ַ��", displayMac(ni.getHardwareAddress()), this, 130);
		
		// �ж��Ƿ�����
		if (ni.isUp()) {
			List<InterfaceAddress> list = ni.getInterfaceAddresses();
			Iterator<InterfaceAddress> it = list.iterator();
			addJPanel("IPv4��", it.next().getAddress().toString().substring(1), this, 160);
			
			Iterator<InterfaceAddress> it1 = list.iterator();
			addJPanel("�������룺", displayPrefix(it1.next().getNetworkPrefixLength()), this, 190);
			
			Iterator<InterfaceAddress> it2 = list.iterator();
			addJPanel("�㲥��ַ��", it2.next().getBroadcast().toString().substring(1), this, 220);
			
			addJPanel("IPv6��", it.next().getAddress().toString().substring(1), this, 250);
			
		}
		
		this.setTitle("Detail");
		this.setLayout(null);
		this.add(detailInfo);
		this.setSize(420, 350);
		this.setLocation(200, 90);
		this.setVisible(true);
	}
	
	// ������
	private void addJPanel(String str1, String str2, JFrame f, int Y) {
		JPanel panel = new JPanel();
		Label lb1 = new Label(str1);
		Label lb2 = new Label(str2);
		lb1.setFont(new Font("΢���ź�",Font.PLAIN,14));
		lb2.setFont(new Font("΢���ź�",Font.PLAIN,14));
		panel.add(lb1);
		panel.add(lb2);
		panel.setBounds(15, Y, 400, 40);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		f.add(panel);
	}
	
	// ������  MAC ��ַ���ֽ�����װ��Ϊ 16 ������
	public String displayMac(byte[] mac) {
		StringBuilder str = new StringBuilder();
		for (int i=0;i<mac.length;i++) {
			byte b = mac[i];
			int value = 0;
			if(b>0) {
				value = b;
			} else {
				value = 256 + b;
			}
			str.append(Integer.toHexString(value));
			if(i!=mac.length-1) {
				str.append(" - ");
			}
		}
		return str.toString();
	}
	
	// ת����������
	public String displayPrefix(int mask) {
		StringBuilder maskStr = new StringBuilder();
        int[] maskIp = new int[4];
        for (int i = 0; i < maskIp.length; i++) {
            maskIp[i] = (mask >= 8) ? 255 : (mask > 0 ? (mask & 0xff) : 0);
            mask -= 8;
            maskStr.append(maskIp[i]);
            if (i < maskIp.length - 1) {
                maskStr.append(".");
            }
        }
        return maskStr.toString();
	}
}
