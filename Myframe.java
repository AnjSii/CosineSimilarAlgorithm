package shixi.CosineSimilarAlgorithm.CosineSimilar_finall;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

class Myframe extends JFrame implements ActionListener {

	public static void main(String args[]) {
		new Myframe();
	}

	JMenuBar menuBar;// �˵���
	JMenu menu;// �˵�
	JMenuItem menuItem1, menuItem2;// �˵���
	JDialog MyDialog1, MyDialog2;// �˵������Ի���
	JLabel Lfilelist, Lresult; // �ļ��б����ƶȱ�ǩ
	JTextArea Tfilelist, Tresult;// �ļ��б����ƶȽ���ı���
	JButton Bbrowse, Baction;// ��ť
	JFileChooser filechooser;// �Ի���
	JScrollPane jsp1, jsp2;// �����������
	File dir;// �Ի������ļ��ĸ�·��
	String name;// �Ի������ļ���
	static File file;// �Ի����ļ�
	File[] files;// �Ի����ļ�����
	static StringBuffer str;// �ļ�����
	static Scanner scan;// ��ȡ�Ի����ļ�
	int fileNumber;// �ļ�����

	Myframe() {// ����ʵ����
		init();
		pack();// ����Ӧ�����С
		setTitle("txt�ı������Լ��");
		setResizable(false);// �����С���ɸı�
		setBounds(300, 100, 380, 540);
		setVisible(true);
		setLayout(new FlowLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// �رհ�ťʱ�رճ���
	}

	void init() {
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menu = new JMenu("˵��");
		menuBar.add(menu);
		menuItem1 = new JMenuItem("С���Ա");
		menuItem2 = new JMenuItem("����˵��");
		menuItem1.addActionListener(this);
		menuItem2.addActionListener(this);
		menu.add(menuItem1);
		menu.addSeparator();
		menu.add(menuItem2);
		Lfilelist = new JLabel("File List:");
		Bbrowse = new JButton("Browse");
		Tfilelist = new JTextArea(10, 30);
		Lresult = new JLabel("Result:");
		Baction = new JButton("Compare");
		Tresult = new JTextArea(10, 30);
		jsp1 = new JScrollPane(Tfilelist,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		jsp2 = new JScrollPane(Tresult, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		Tfilelist.setLineWrap(true);// �Զ�����
		Tresult.setLineWrap(true);// �Զ�����
		add(Lfilelist);
		add(Bbrowse);
		add(jsp1);
		add(Lresult);
		add(Baction);
		add(jsp2);
		Bbrowse.addActionListener(this);// ��Ӽ���
		Baction.addActionListener(this);// ��Ӽ���
		filechooser = new JFileChooser();
		filechooser.setMultiSelectionEnabled(true);
	}

	class MyDialog1 extends JDialog {
		public MyDialog1() {
			super(Myframe.this, "С��˵��", true);
			add(new JLabel("С���Ա���ܽ���������塢�����"));
			setBounds(450, 250, 250, 150);
		}
	}

	class MyDialog2 extends JDialog {
		public MyDialog2() {
			super(Myframe.this, "����˵��", true);
			setLayout(new GridLayout(3, 1));
			JLabel lab1 = new JLabel("����˵���������򻹴���һЩBUG��");
			JLabel lab2 = new JLabel("ֻ�ܶԱȴ��ı��ĵ�������doc��ʽ");
			JLabel lab3 = new JLabel("�����Ƕ����Ʊ��룬���ǽ���һ���޸�");
			add(lab1);
			add(lab2);
			add(lab3);
			setBounds(450, 250, 250, 200);
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == menuItem1) {
			new MyDialog1().setVisible(true);

		}
		if (e.getSource() == menuItem2) {
			new MyDialog2().setVisible(true);
		}
		if (e.getSource() == Bbrowse) {// �ж��¼�Դ
			int state = filechooser.showOpenDialog(this);// ��ʾ�ļ��Ի���
			if (state == JFileChooser.APPROVE_OPTION) {// ȷ����ť��
				Tfilelist.setText("");
				Tresult.setText("");
				files = filechooser.getSelectedFiles();
				fileNumber = files.length;// �ļ�����
				StringBuffer filelist = new StringBuffer();
				for (int i = 0; i < fileNumber; i++) {// ��ʾ�ļ�·��
					filelist.append("�ļ�" + (i + 1) + ":" + files[i] + "\n");
					// Tfilelist.append("�ļ�" + (i + 1) + ":" + files[i] + "\n");
				}
				Tfilelist.setText(filelist.toString());
			}
		}
		if (e.getSource() == Baction) {// �ж��¼�Դ
			double[][] fileSimilar = new double[fileNumber][fileNumber];// �洢�ļ����ƶ�
			// ����ά��
			String[] fileCompare = new String[fileNumber];// �洢�ļ�����ΪString����
			for (int i = 0; i < fileCompare.length; i++) {// ��ÿ���ļ�����д��fileCompare����
				fileCompare[i] = Myframe.getFileString(files[i]
						.getAbsolutePath());// ����getFileString��������
			}
			int x, y = 0;
			for (x = 0; x < fileSimilar.length - 1; x++) {// ���ν����ƶ�д��fileSimilar��ά����
				for (y = 0; y < fileSimilar[0].length - 1; y++) {
					if (x >= y) {// ȥ���Լ���֮ǰ�ѶԱȵ��ļ�
						continue;
					}
					fileSimilar[x][y] = CosineSimilarAlgorithm.getSimilarity(
							fileCompare[x], fileCompare[y]);// ����getSimilarity��������
				}
				fileSimilar[x][y] = CosineSimilarAlgorithm.getSimilarity(
						fileCompare[x], fileCompare[y]);// ����getSimilarity��������
			}
			for (x = 0; x < fileSimilar.length - 1; x++) {// ��������ļ����ƶ�
				for (y = 0; y < fileSimilar[0].length - 1; y++) {
					if (x >= y) {// ȥ���Լ���֮ǰ�ѶԱȵ��ļ�
						continue;
					}
					Tresult.append("�ļ�" + (x + 1) + "���ļ�" + (y + 1) + "�����ƶ�Ϊ"
							+ fileSimilar[x][y] + "\n");
				}
				Tresult.append("�ļ�" + (x + 1) + "���ļ�" + (y + 1) + "�����ƶ�Ϊ"
						+ fileSimilar[x][y] + "\n");
			}
		}
	}

	public static String getFileString(String url) {// ����ļ�����
		file = new File(url);// �����ļ�·��
		try {
			scan = new Scanner(file); // �����ļ�
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		str = new StringBuffer();
		while (scan.hasNext()) {
			str.append(scan.next()).append('\n'); // ��ȡ����
		}
		return str.toString();
	}
}
