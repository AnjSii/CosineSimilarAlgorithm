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

	JMenuBar menuBar;// 菜单栏
	JMenu menu;// 菜单
	JMenuItem menuItem1, menuItem2;// 菜单项
	JDialog MyDialog1, MyDialog2;// 菜单弹出对话框
	JLabel Lfilelist, Lresult; // 文件列表、相似度标签
	JTextArea Tfilelist, Tresult;// 文件列表、相似度结果文本框
	JButton Bbrowse, Baction;// 按钮
	JFileChooser filechooser;// 对话框
	JScrollPane jsp1, jsp2;// 两个滑动面板
	File dir;// 对话框中文件的父路径
	String name;// 对话框中文件名
	static File file;// 对话框文件
	File[] files;// 对话框文件集合
	static StringBuffer str;// 文件内容
	static Scanner scan;// 读取对话框文件
	int fileNumber;// 文件个数

	Myframe() {// 窗体实例化
		init();
		pack();// 自适应窗体大小
		setTitle("txt文本相似性检测");
		setResizable(false);// 窗体大小不可改变
		setBounds(300, 100, 380, 540);
		setVisible(true);
		setLayout(new FlowLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 关闭按钮时关闭程序
	}

	void init() {
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menu = new JMenu("说明");
		menuBar.add(menu);
		menuItem1 = new JMenuItem("小组成员");
		menuItem2 = new JMenuItem("程序说明");
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
		Tfilelist.setLineWrap(true);// 自动换行
		Tresult.setLineWrap(true);// 自动换行
		add(Lfilelist);
		add(Bbrowse);
		add(jsp1);
		add(Lresult);
		add(Baction);
		add(jsp2);
		Bbrowse.addActionListener(this);// 添加监听
		Baction.addActionListener(this);// 添加监听
		filechooser = new JFileChooser();
		filechooser.setMultiSelectionEnabled(true);
	}

	class MyDialog1 extends JDialog {
		public MyDialog1() {
			super(Myframe.this, "小组说明", true);
			add(new JLabel("小组成员：曹建国、杨财沐、彭锐彬"));
			setBounds(450, 250, 250, 150);
		}
	}

	class MyDialog2 extends JDialog {
		public MyDialog2() {
			super(Myframe.this, "程序说明", true);
			setLayout(new GridLayout(3, 1));
			JLabel lab1 = new JLabel("程序说明：本程序还存在一些BUG，");
			JLabel lab2 = new JLabel("只能对比纯文本文档，对于doc格式");
			JLabel lab3 = new JLabel("由于是二进制编码，我们将进一步修改");
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
		if (e.getSource() == Bbrowse) {// 判断事件源
			int state = filechooser.showOpenDialog(this);// 显示文件对话框
			if (state == JFileChooser.APPROVE_OPTION) {// 确定按钮后
				Tfilelist.setText("");
				Tresult.setText("");
				files = filechooser.getSelectedFiles();
				fileNumber = files.length;// 文件个数
				StringBuffer filelist = new StringBuffer();
				for (int i = 0; i < fileNumber; i++) {// 显示文件路径
					filelist.append("文件" + (i + 1) + ":" + files[i] + "\n");
					// Tfilelist.append("文件" + (i + 1) + ":" + files[i] + "\n");
				}
				Tfilelist.setText(filelist.toString());
			}
		}
		if (e.getSource() == Baction) {// 判断事件源
			double[][] fileSimilar = new double[fileNumber][fileNumber];// 存储文件相似度
			// （二维）
			String[] fileCompare = new String[fileNumber];// 存储文件内容为String类型
			for (int i = 0; i < fileCompare.length; i++) {// 将每个文件内容写入fileCompare数组
				fileCompare[i] = Myframe.getFileString(files[i]
						.getAbsolutePath());// 调用getFileString（）方法
			}
			int x, y = 0;
			for (x = 0; x < fileSimilar.length - 1; x++) {// 依次将相似度写入fileSimilar二维数组
				for (y = 0; y < fileSimilar[0].length - 1; y++) {
					if (x >= y) {// 去掉自己和之前已对比的文件
						continue;
					}
					fileSimilar[x][y] = CosineSimilarAlgorithm.getSimilarity(
							fileCompare[x], fileCompare[y]);// 调用getSimilarity（）方法
				}
				fileSimilar[x][y] = CosineSimilarAlgorithm.getSimilarity(
						fileCompare[x], fileCompare[y]);// 调用getSimilarity（）方法
			}
			for (x = 0; x < fileSimilar.length - 1; x++) {// 依次输出文件相似度
				for (y = 0; y < fileSimilar[0].length - 1; y++) {
					if (x >= y) {// 去掉自己和之前已对比的文件
						continue;
					}
					Tresult.append("文件" + (x + 1) + "和文件" + (y + 1) + "的相似度为"
							+ fileSimilar[x][y] + "\n");
				}
				Tresult.append("文件" + (x + 1) + "和文件" + (y + 1) + "的相似度为"
						+ fileSimilar[x][y] + "\n");
			}
		}
	}

	public static String getFileString(String url) {// 获得文件内容
		file = new File(url);// 加载文件路径
		try {
			scan = new Scanner(file); // 接收文件
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		str = new StringBuffer();
		while (scan.hasNext()) {
			str.append(scan.next()).append('\n'); // 获取数据
		}
		return str.toString();
	}
}
