package main;
//证件照使用说明
import javax.swing.*;
import java.awt.*;

public class Help extends JDialog {
    private static final long serialVersionUID = 4693799019369193520L;
    private JPanel contentPane;
    private Font f = new Font("微软雅黑",Font.PLAIN,15);
    private JScrollPane scroll;
	
    public Help() {
        setTitle("证件照使用说明");//设置窗体标题
        Image img=Toolkit.getDefaultToolkit().getImage("res//mi64.png");//窗口图标
        setIconImage(img);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setModal(true);//设置为模态窗口
        setSize(420,400);
        setResizable(false);
        setLocationRelativeTo(null);
        contentPane = new JPanel();// 创建内容面板
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        ShadePanel2 shadePanel = new ShadePanel2();           // 创建渐变背景面板（help可以调用本包内的所有非private的class）
        contentPane.add(shadePanel, BorderLayout.CENTER);   // 添加面板到窗体内容面板
        shadePanel.setLayout(null);
        
        JTextArea J1 = new JTextArea("     本软件证件照为常用的尺寸大小，尺寸像素应以考试要求为标准，证件照格式默认保存为jpg图片格式\n "
                +"常用尺寸大小：\n"
                +"会计：      二寸 295×413像素 白底;大于10k jpg\n"
                +"普通话：    二寸 390*567像素 浅蓝色; jpg\n"
                +"四六级：    一寸 260*378像素 浅蓝色;50K以下; jpg\n"
                +"教师资格证：二寸 300*400像素 白底;10k~200K; jpg\n"
                +"计算机等级：二寸 144*192像素 浅蓝色;10k~200K; jpg\n"
                +"附：\n"
                +"常规尺寸 英寸      尺寸\n" +
                "1寸        1*1.5    2.5*3.5       证件照\n" +
                "2寸        1.5*2    3.5*5.3       标准2寸照片\n" +
                "大1寸     3.3*4.8                    中国护照\n" +
                "5寸/3R   5*3.5    12.7*8.9      最常见的照片大小\n" +
                "6寸/4R   6*4      15.2*10.2     国际上通用照片大小\n" +
                "7寸/5R   7*5      17.8*12.7     放大\n" +
                "8寸         6*8      15.2*20.3     大概是A4纸的一半\n" +
                "小12寸   8*12     20.3*30.5     大概是A4大小\n" +
                "12寸     10*12    25.4*30.5     A4纸是21*29.7厘米"
        		+ "\n                   Copyright @XZB2020.\n                   All rights reserved.");
        J1.setFocusable(false);
    	J1.setFont(f);
    	J1.setEditable(false);
    	J1.setOpaque(false);    //背景透明
    	J1.setLineWrap(true);   //自动换行
    	//创建滚动条并添加文本域
    	scroll = new JScrollPane(J1,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    	scroll.setBorder(BorderFactory.createTitledBorder("使用须知"));//滚动边框标题
    	scroll.setOpaque(false);
    	scroll.getViewport().setOpaque(false);//JScrollPane设置成透明需加上这一行
    	shadePanel.add(scroll);
    	scroll.setBounds(10, 10, 385, 330);
    	
    	setVisible(true);
    }
    
    public static void main(String[] args) {
		new Help();
	}
    class ShadePanel2 extends JPanel {

        private static final long serialVersionUID = -264442427166326106L;

        public ShadePanel2() {
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
            GradientPaint paint = new GradientPaint(0, 0, Color.yellow, 0, height,Color.pink);//实现颜色渐变
            g.setPaint(paint);			// 设置填充的绘图对象
            g.fillRect(0, 0, width, height);// 绘制矩形填充控件界面
        }
    }
}
