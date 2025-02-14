package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

class ShadePanel extends JPanel {
    
    private static final long serialVersionUID = -264442427166326106L;
    
    public ShadePanel() {
        super();
        setLayout(null);
    }
    
    @Override
    protected void paintComponent(Graphics g1) {// 重写绘制组件外观
        Graphics2D g = (Graphics2D) g1;
        super.paintComponent(g);	// 执行超类方法
        int width = getWidth();		// 获取组件大小
        int height = getHeight();
        // 创建填充模式对象
        GradientPaint paint = new GradientPaint(0, 0, Color.CYAN, 0, height,Color.yellow);//实现颜色渐变
        g.setPaint(paint);			// 设置填充的绘图对象
        g.fillRect(0, 0, width, height);// 绘制矩形填充控件界面
    }
}

public class About extends JDialog {
    private static final long serialVersionUID = 469379901936919350L;
    private JPanel contentPane;
    private Font f1 = new Font("微软雅黑",Font.PLAIN,15);
	private Font f2 = new Font("微软雅黑",Font.PLAIN,20);
	private ImageIcon icon;
	private JLabel label;
    public About() {
        setTitle("关于");//设置窗体标题
        Image img=Toolkit.getDefaultToolkit().getImage("res//mi64.png");//窗口图标
        setIconImage(img);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setModal(true);//设置为模态窗口（是否是模式对话框）
        setSize(430,400);
        setResizable(false);
        setLocationRelativeTo(null);
        contentPane = new JPanel();// 创建内容面板
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        ShadePanel shadePanel = new ShadePanel();			// 创建渐变背景面板
        contentPane.add(shadePanel, BorderLayout.CENTER);	// 添加面板到窗体内容面板
        shadePanel.setLayout(null);
        
        JTextArea J1 = new JTextArea("开发者：小子斌\n开发语言：Java\n"
				+ "Email:760410154@qq.com\n版本：2020-04-15_1.0.0");
        J1.setFocusable(false);
    	J1.setFont(f2);
    	J1.setEditable(false);
    	J1.setOpaque(false);//背景透明
    	shadePanel.add(J1);
    	J1.setBounds(10, 10, 400, 180);
		//图片标签
    	icon = new ImageIcon("res//mi46.png");
    	icon.setImage(icon.getImage().getScaledInstance(80,80,Image.SCALE_SMOOTH));//保持图片的清晰
    	label = new JLabel(icon);
    	shadePanel.add(label);
    	label.setBounds(280, 20, 80, 80);

    	JPanel p = new JPanel();
    	p.setBounds(5, 130, 395, 1);
	    p.setBorder(BorderFactory.createLineBorder(Color.black));
	    shadePanel.add(p);
	    
	    JLabel J2 = new JLabel("欢迎访问我的主页:");
	    J2.setBounds(10, 145, 200, 30);
	    J2.setFont(f2);
	    shadePanel.add(J2);
	    
    	JLabel MyGithub_Label = new JLabel("Github:");
    	MyGithub_Label.setFont(f2);
    	final JLabel MyGithub = new JLabel("https://github.com/my-sword");
    	MyGithub.setFont(f2);
    	MyGithub.setBackground(Color.white);
    	MyGithub.addMouseListener(new InternetMonitor());
    	JLabel MyCnBlog_Label = new JLabel("CSDN:");
    	MyCnBlog_Label.setFont(f2);
    	final JLabel MyCnBlog = new JLabel("https://blog.csdn.net/weixin_47649446");
    	MyCnBlog.setFont(f2);
    	MyCnBlog.addMouseListener(new InternetMonitor());
    	JTextArea Copyright = new JTextArea("     	Copyright @XZB2020\n   	  All rights reserved.");
    	Copyright.setFocusable(false);
    	Copyright.setOpaque(false);
    	Copyright.setFont(f1);
    	Copyright.setEditable(false);
    	
    	shadePanel.add(MyGithub_Label);
    	MyGithub_Label.setBounds(10, 180, 400, 20);
    	shadePanel.add(MyGithub);
    	MyGithub.setBounds(10, 200, 400, 30);
    	shadePanel.add(MyCnBlog_Label);
    	MyCnBlog_Label.setBounds(10, 240, 400, 25);
    	shadePanel.add(MyCnBlog);
    	MyCnBlog.setBounds(10, 265, 400, 30);
    	shadePanel.add(Copyright);
    	Copyright.setBounds(10, 300, 400, 50);
       
    	setVisible(true);
    }
    
    public static void main(String[] args) {
		new About();
	}
}

class InternetMonitor extends MouseAdapter{
	public void mouseClicked(MouseEvent e){
		JLabel JLabel_temp = (JLabel)e.getSource();
		String J_temp = JLabel_temp.getText();
		System.out.println(J_temp);
		URI uri ;
			try {
				uri = new URI(J_temp);
				Desktop desk=Desktop.getDesktop();
				if(Desktop.isDesktopSupported() && desk.isSupported(Desktop.Action.BROWSE)){
					try {
						desk.browse(uri);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
			}
	}
	public void mouseEntered(MouseEvent e){
		JLabel JLabel_temp = (JLabel)e.getSource();
		JLabel_temp.setForeground(Color.red);
	}
	public void mouseExited(MouseEvent e){
		JLabel JLabel_temp = (JLabel)e.getSource();
		JLabel_temp.setForeground(Color.blue);
	}
}
