package main;


/*
//添加背景图片 修改尺寸
	    backgroundImage = new ImageIcon("background/desert.jpg");
	    backgroundImage.setImage(backgroundImage.getImage().getScaledInstance(1000,540,Image.SCALE_SMOOTH));//图片伸缩
	    background_label = new JLabel(backgroundImage);
	    background_label.setBounds(0,0, this.getWidth(), this.getHeight());
        this.getLayeredPane().add(background_label, new Integer(Integer.MIN_VALUE));//MIN_VALUE置于底层
 */



import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.*;
import java.awt.event.TextListener;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import javax.imageio.ImageIO;


class lb {
    Maindeal m;//主窗体
    JButton b1,b2,b3;
    private ImageIcon icon1,icon2,icon3;//按钮图标
    JScrollPane scrollPane1;
    JLabel label = new JLabel();//图片标签
    JLabel label2 = new JLabel("可以拖拽图像文件到此处");//图片标签
    private final int  F=355;//图片标签宽高  //800 420
    private Font f = new Font("微软雅黑",Font.BOLD,15);
    private Font f2 = new Font("微软雅黑",Font.PLAIN,15);
    /*
        常规尺寸 英寸      尺寸
        1寸      1*1.5    2.5*3.5     证件照
        2寸      1.5*2    3.5*5.3     标准2寸照片
        大1寸    3.3*4.8              中国护照
        5寸/3R   5*3.5    12.7*8.9    最常见的照片大小
        6寸/4R   6*4    15.2*10.2     国际上比较通用的照片大小
        7寸/5R   7*5    17.8*12.7     放大
        8寸      6*8    15.2*20.3     大概是A4打印纸的一半
        小12寸   8*12    20.3*30.5    大概是A4大小
        12寸     10*12    25.4*30.5   A4打印纸是21*29.7厘米
        像素跟

        +"会计：      二寸 295×413像素 白色背景;大于10k jpg\n"
        +"普通话：    一寸 390*567像素 浅蓝色; jpg\n"
        +"四六级：    一寸 260*378像素 浅蓝色;50K以下; jpg\n"
        +"教师资格证：二寸 300*400像素 白色背景;10k~200K; jpg\n"
        +"计算机等级：二寸 144*192像素 浅蓝色;10k~200K; jpg\n"
     */
    ButtonGroup group1=new ButtonGroup();
    private JRadioButton jradio1 = new JRadioButton("一寸");//定义按钮
    private JRadioButton jradio2 = new JRadioButton("二寸");
    private JRadioButton jradio3 = new JRadioButton("小一寸");
    private JRadioButton jradio4 = new JRadioButton("大一寸");
    private JRadioButton jradio5 = new JRadioButton("小二寸");
    private JRadioButton jradio6 = new JRadioButton("原图尺寸");

    private JCheckBox jc1 = new JCheckBox("使用须知"); //此组禁用
    private JRadioButton jradio7 = new JRadioButton("会计");           //二寸295×413 jpg
    private JRadioButton jradio8 = new JRadioButton("普通话");         //一寸 390*567 jpg
    private JRadioButton jradio9 = new JRadioButton("四六级");         //一寸 4:3 320*240 jpg
    private JRadioButton jradio10 = new JRadioButton("教师资格证");     //二寸 626*413 jpg
    private JRadioButton jradio11 = new JRadioButton("计算机等级");     //二寸260×378 jpg

    TextField w1,h1;//注意不是JTextField JTextField 没有addTextLister事件
    private String width,height;
    private Boolean wb=false,hb=false,jc2b;
    private JCheckBox jc2 = new JCheckBox("保持纵横比");

    ButtonGroup group2=new ButtonGroup();
    private JRadioButton r1 = new JRadioButton("原图格式");//定义按钮
    private JRadioButton r2 = new JRadioButton("jpg");
    private JRadioButton r3 = new JRadioButton("png");
    private JRadioButton r4 = new JRadioButton("bmp");
    private JRadioButton r5 = new JRadioButton("gif");
    private JCheckBox jc3 = new JCheckBox("黑白图");
    private Boolean op=false,dakai=false;//是否打开文件
    public static Boolean opstatic=false;//是否打开文件  外部调用而不用new
    private String sop="",sopw="",soph="";//sop是真路径
    File selectedFile;  //selectedFile.getAbsolutePath()为路径
    JFileChooser fileChooser;

    JMenuItem q1,q2,q3,q4,q5;
    private int dx1, dy1, dx2, dy2;
    private int sx1, sy1, sx2, sy2;
    BufferedImage imgq;
    private Boolean oimgq=false;

    public JButton getbb(int a){
        if(a==1){
            return b1;
        }else if(a==2){
            return b2;
        }else
            return b3;
    }

    private JMenuItem about;

    //构造 单选按钮与标签
    public lb(Maindeal mi){
        this.m=mi;
        ci();
        cj();
//大按钮
        icon1= new ImageIcon("res//mi64.png");
        icon1.setImage(icon1.getImage().getScaledInstance(32,32,Image.SCALE_FAST));//图片伸缩
        b1=createToolButton("打开图片",icon1);

        icon2= new ImageIcon("res//mi46.png");
        icon2.setImage(icon2.getImage().getScaledInstance(32,32,Image.SCALE_FAST));//图片伸缩
        b2=createToolButton("保存图片",icon2);

        icon3= new ImageIcon("res//reset.png");
        icon3.setImage(icon3.getImage().getScaledInstance(32,32,Image.SCALE_FAST));//图片伸缩
        b3=createToolButton("重置",icon3);

        b1.setForeground(new Color(0x903DDD));
        b1.setBounds(50,0,150, 40);
        b1.setFont(new Font("汉仪雪峰体简",Font.BOLD, 17));
        b2.setForeground(new Color(0x2582DD));
        b2.setBounds(230,0,150, 40);
        b2.setFont(new Font("汉仪雪峰体简",Font.BOLD, 17));
        b3.setForeground(new Color(0xDD9200));
        b3.setBounds(140,220,150, 40);
        b3.setFont(new Font("汉仪雪峰体简",Font.PLAIN, 22));

        mi.add(b1);
        mi.add(b2);
        mi.add(b3);


        //单选框
        int rx=70;
        jradio1.setBounds(0, 60, 50, 30);
        jradio2.setBounds(rx-20, 60, 50, 30);
        jradio3.setBounds(2*rx-40, 60, rx, 30);
        jradio4.setBounds(3*rx-40, 60, rx, 30);
        jradio5.setBounds(4*rx-40, 60, rx, 30);
        jradio6.setBounds(5*rx-40, 60, rx+15, 30);
        jradio1.setOpaque(false);
        jradio2.setOpaque(false);
        jradio3.setOpaque(false);
        jradio4.setOpaque(false);
        jradio5.setOpaque(false);
        jradio6.setOpaque(false);

        int rx2=85;
        jc1.setBounds(0, 100, 75, 30);
        jradio7.setBounds(75, 100, 50, 30);
        jradio8.setBounds(2*rx-15, 100, rx, 30);
        jradio9.setBounds(3*rx-25, 100, rx, 30);    //四六级
        jradio10.setBounds(4*rx-35, 100, rx2, 30);  //教师资格
        jradio11.setBounds(5*rx-20, 100, rx2, 30);
        jradio7.setOpaque(false);
        jradio8.setOpaque(false);
        jradio9.setOpaque(false);
        jradio10.setOpaque(false);
        jradio11.setOpaque(false);

        Color cc=new Color(0xAF00DD);
        Color cc2=new Color(0x000ADD);
        jradio1.setForeground(cc);
        jradio2.setForeground(cc);
        jradio3.setForeground(cc);
        jradio4.setForeground(cc);
        jradio5.setForeground(cc);
        jradio6.setForeground(cc);
        jradio7.setForeground(cc2);
        jradio8.setForeground(cc2);
        jradio9.setForeground(cc2);
        jradio10.setForeground(cc2);
        jradio11.setForeground(cc2);
        jradio7.setEnabled(false);
        jradio8.setEnabled(false);
        jradio9.setEnabled(false);
        jradio10.setEnabled(false);
        jradio11.setEnabled(false);
        //按钮事件
        jradio1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { w1.setText("295");h1.setText("413"); }
        });
        jradio2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { w1.setText("413");h1.setText("625"); }
        });
        jradio3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { w1.setText("259");h1.setText("377"); }
        });
        jradio4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { w1.setText("389");h1.setText("466"); }
        });
        jradio5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { w1.setText("413");h1.setText("531"); }
        });
        jradio6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(op){
                    w1.setText(sopw);
                    h1.setText(soph);
                }else {
                    w1.setText("");
                    h1.setText("");
                }
            }
        });

        jradio7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { w1.setText("295");h1.setText("413"); }
        });
        jradio8.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { w1.setText("390");h1.setText("567"); }
        });
        jradio9.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { w1.setText("260");h1.setText("378"); }
        });
        jradio10.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { w1.setText("300");h1.setText("400"); }
        });
        jradio11.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { w1.setText("144");h1.setText("192"); }
        });
        mi.add(jradio1);
        mi.add(jradio2);
        mi.add(jradio3);
        mi.add(jradio4);
        mi.add(jradio5);
        mi.add(jradio6);
        mi.add(jradio7);
        mi.add(jradio8);
        mi.add(jradio9);
        mi.add(jradio10);
        mi.add(jradio11);
        group1.add(jradio1);
        group1.add(jradio2);
        group1.add(jradio3);
        group1.add(jradio4);
        group1.add(jradio5);
        group1.add(jradio6);
        group1.add(jradio7);
        group1.add(jradio8);
        group1.add(jradio9);
        group1.add(jradio10);
        group1.add(jradio11);
        jradio1.setSelected(true);


        jc1.setOpaque(false);//使用须知
        mi.add(jc1);
        jc1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (jc1.isSelected()){
                    jradio7.setEnabled(true);
                    jradio8.setEnabled(true);
                    jradio9.setEnabled(true);
                    jradio10.setEnabled(true);
                    jradio11.setEnabled(true);
                    mi._getabout().doClick();//调用api方法
                }else{
                    jradio7.setEnabled(false);
                    jradio8.setEnabled(false);
                    jradio9.setEnabled(false);
                    jradio10.setEnabled(false);
                    jradio11.setEnabled(false);
                }

            }
        });


        //保存格式
        JLabel lb1=createlb("保存图片格式");
        lb1.setBounds(0, 180, 120, 30);
        lb1.setFont(f);

        int rrx=50;
        r1.setBounds(90, 180, 80, 30);
        r2.setBounds(2*rrx+70, 180, 50, 30);
        r3.setBounds(3*rrx+70, 180, 50, 30);
        r4.setBounds(4*rrx+70, 180, 50, 30);
        r5.setBounds(5*rrx+70, 180, 50, 30);
        jc3.setBounds(6*rrx+55, 180, 80, 30);
        r1.setOpaque(false);
        r2.setOpaque(false);
        r3.setOpaque(false);
        r4.setOpaque(false);
        r5.setOpaque(false);
        jc3.setOpaque(false);
        Color cc3=new Color(0xCE8A00);
        r1.setForeground(cc3);
        r2.setForeground(cc3);
        r3.setForeground(cc3);
        r4.setForeground(cc3);
        r5.setForeground(cc3);
        r1.setSelected(true);
        group2.add(r1);
        group2.add(r2);
        group2.add(r3);
        group2.add(r4);
        group2.add(r5);


        m.add(lb1);
        mi.add(r1);
        mi.add(r2);
        mi.add(r3);
        mi.add(r4);
        mi.add(r5);
        mi.add(jc3);



//滚动框
        //显示拖拽标签
        label2.setFont(f);
        label2.setForeground(new Color(0xDD1400));
        label2.setHorizontalAlignment(SwingConstants.CENTER);
        label2.setBounds(420,0,F, F-F/4);

        //滚动标签
        scrollPane1 = new JScrollPane(
                label,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS
        );
        scrollPane1.setBounds(420,25,F, F-F/4);
        mi.add(label2);
        mi.getLayeredPane().add(scrollPane1,Integer.valueOf(Integer.MAX_VALUE));//getLayeredPane()流式面板可设置透明
        scrollPane1.setVisible(false);

        drag();//文件拖拽
    }
    //自定义尺寸与保存格式
    public void ci(){
        JLabel lb1=createlb("自定义尺寸(像素)");
        JLabel lb2=createlb("宽度");
        JLabel lb3=createlb("高度");
        lb1.setBounds(0, 140, 120, 30);
        lb2.setBounds(130, 140, 30, 30);
        lb3.setBounds(240, 140, 30, 30);
        lb1.setFont(f);
        lb2.setFont(f2);
        lb3.setFont(f2);
        lb2.setForeground(new Color(0x67656A));//淡色
        lb3.setForeground(new Color(0x646267));

        w1 = new TextField("295");//+不能输入数字
        h1 = new TextField("413");
        w1.setFont(new Font("微软雅黑",Font.BOLD,17));
        h1.setFont(new Font("微软雅黑",Font.BOLD,17));
        w1.setBounds(170, 140, 50, 30);
        h1.setBounds(280, 140, 50, 30);
        jc2.setBounds(330, 140, 90, 30);
        jc2.setOpaque(false);
        jc2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (jc2.isSelected()){
                    jc2b=true;
                }else{

                }

            }
        });//保持纵横比
        w1.addTextListener(new TextListener() {
            public void textValueChanged(TextEvent textEvent) {
                if(wb){//通过失去焦点判断
                    group1.clearSelection();
                }
                //w1.getText()!="" && h1.getText()!="" 不安全  不等于空+焦点+复选框选中
                if (!Objects.equals(w1.getText(), "") && !Objects.equals(h1.getText(), "") && wb && jc2.isSelected()){
                    int a=0;
                    a=Integer.parseInt(w1.getText())*Integer.parseInt(height)/Integer.parseInt(width);
                    h1.setText(String.valueOf(a));
                }
            }
        });//内容被改变，取消选择
        h1.addTextListener(new TextListener() {
            public void textValueChanged(TextEvent textEvent) {
                if(hb){
                    group1.clearSelection();
                }
                if (!Objects.equals(w1.getText(), "") && !Objects.equals(h1.getText(), "") && hb && jc2.isSelected()){
                    int a=0;
                    a=Integer.parseInt(w1.getText())*Integer.parseInt(height)/Integer.parseInt(width);
                    w1.setText(String.valueOf(a));
                }
            }
        });

        w1.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent event) {
                char ch=event.getKeyChar();
                if(ch<'0'||ch>'9') event.consume();}
        });//只能输入数字
        h1.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent event) {
                char ch=event.getKeyChar();
                if(ch<'0'||ch>'9') event.consume();}
        });
        w1.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEvent) {
                width=w1.getText();
                height=h1.getText();
                wb=true;
                hb=false;
            }
            @Override
            public void focusLost(FocusEvent focusEvent) {hb=false;wb=false; }
        });
        h1.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEvent) {
                width=w1.getText();
                height=h1.getText();
                wb=false;
                hb=true;
            }
            @Override
            public void focusLost(FocusEvent focusEvent) {hb=false;wb=false;}
        });
        m.add(lb1);
        m.add(lb2);
        m.add(lb3);
        m.add(w1);
        m.add(h1);
        m.add(jc2);

        //保存格式和重置


    }
    //高级图像操作
    public void cj(){
        JButton b4=createToolButton("图像旋转",null);
        b4.setBounds(20,220,100, 30);
        m.add(b4);


        final JPopupMenu popupMenu = new JPopupMenu();// 创建弹出式菜单对象

        b4.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if(op){
                        popupMenu.show(b4, e.getX(), e.getY()+20);//获取容器如果是则在释放鼠标的位置弹出菜单
                        //getComponent()返回事件的始发者 getComponent 是获得组件 getSource是获得源对象
                }else
                    JOptionPane.showMessageDialog(null, "没有选择图像文件");
            }
        });

        q1 = new JMenuItem("顺时针90度");
        q1.addActionListener(new ItemListener());
        popupMenu.add(q1);
        q2 = new JMenuItem("逆时针90度");
        q2.addActionListener(new ItemListener());
        popupMenu.add(q2);
        q3 = new JMenuItem("旋转180度");
        q3.addActionListener(new ItemListener());
        popupMenu.add(q3);
        q4 = new JMenuItem("水平翻转");
        q4.addActionListener(new ItemListener());
        popupMenu.add(q4);
        q5 = new JMenuItem("垂直翻转");
        q5.addActionListener(new ItemListener());
        popupMenu.add(q5);




        //生成ico（难）
//        JButton b5=createToolButton("生成ico",null);
//        b5.setBounds(70,270,100, 30);
//        m.add(b5);


        //调用外部程序
        JButton b6=createToolButton("图片去水印",null);
        b6.setForeground(new Color(0x5FDD0F));
        b6.setBounds(70,350,130, 40);
        b6.setFont(new Font("汉仪雪峰体简",Font.BOLD, 17));
        m.add(b6);
        JButton b7=createToolButton("图像压缩",null);
        b7.setForeground(new Color(0xB200DD));
        b7.setBounds(230,350,130, 40);
        b7.setFont(new Font("汉仪雪峰体简",Font.BOLD, 17));
        b7.setHorizontalAlignment(SwingConstants.CENTER);
        m.add(b7);
        JButton b8=createToolButton("图片转ICO",null);
        b8.setForeground(new Color(0x00DDD7));
        b8.setBounds(145,415,140, 40);
        b8.setFont(new Font("汉仪雪峰体简",Font.BOLD, 17));
        b8.setHorizontalAlignment(SwingConstants.CENTER);
        m.add(b8);


    }
    private class ItemListener implements ActionListener {//触发单击事件
        public void actionPerformed(ActionEvent e) {
            //该功能未开发
            JOptionPane.showMessageDialog(null, "该功能未开发完全。");
            if(false){
                JMenuItem menuItem = (JMenuItem) e.getSource();
                System.out.println("您单击的是菜单项：" + menuItem.getText());
                if(menuItem.getText()=="顺时针90度"){

                }
                if(menuItem.getText()=="水平翻转"){
                    dx2 = sx2 = 1366; // 初始化图像大小
                    dy2 = sy2 = 768;
                    sx1 = Math.abs(sx1 - 1366);//sx1未定义默认为0
                    sx2 = Math.abs(sx2 - 1366);//sx1,sx1` sx2,sx2`(0,300)→(300,0) 两个x轴端点位置调换 300可改 指定分割区域
                    File file=new File(sop);
                    System.out.println("菜单"+sop);

                    try {
                        imgq = ImageIO.read(file);
                        final Graphics2D g2d =imgq.createGraphics();//获得一个图形类知
                        g2d.drawImage(imgq, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
                        //File file2 = new File("C:\\Users\\Administrator\\Desktop\\3.jpg");
                        //ImageIO.write(imgq, "JPEG", file2);//保存成道图片版 out路径文件对象
                        oimgq=true;
                        b2.doClick();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                }
                if(menuItem.getText()=="垂直翻转"){
                    dx2 = sx2 = 1366; // 初始化图像大小
                    dy2 = sy2 = 768;

                    sy1 = Math.abs(sy1 - 768);
                    sy2 = Math.abs(sy2 - 768);
                    System.out.println(sy1+" "+sy2);
                    File file=new File(sop);

                    try {
                        imgq = ImageIO.read(file);
                        final Graphics2D g2d =imgq.createGraphics();//获得一个图形类知
                        g2d.drawImage(imgq, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
//                    File file2 = new File("C:\\1.jpg");
//                    ImageIO.write(imgq, "JPEG", file2);//保存成道图片版 out路径文件对象
//                    ImageIcon icon =new ImageIcon("C:\\1.jpg");
                        oimgq=true;
                        b2.doClick();
                        //Iamge image = (Iamge )bufferedImage;

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                }
            }
        }
    }
    //按钮模板及事件
    private JButton createToolButton(String str,Icon icon) {

        JButton button = new JButton();			// 工具栏按钮
        button.setText(str);			        // 设置工具栏按钮的文本内容
        button.setToolTipText(str);	            // 设置工具栏按钮的悬浮效果（字体悬浮提示）
        button.setIcon(icon);			        // 设置工具栏按钮的图标
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFocusable(false);				// 让工具栏按钮失去焦点
        button.addActionListener(new java.awt.event.ActionListener() {// 为工具栏按钮添加动作事件的监听
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if(str =="打开图片"){
                    //JFileChooser fileChooser;
                    dakai=true;
                    {//相当于无名函数
                        fileChooser = new JFileChooser();// 创建文件选择对话框
                        // 设置文件过滤器，只列出JPG或GIF格式的图片
                        FileFilter filter = new FileNameExtensionFilter(
                                "图像文件（JPG/GIF/BMP/PNG）", "JPG", "JPEG", "GIF","BMP","PNG");
                        fileChooser.setFileFilter(filter);
                    }
                    // 显示文件选择对话框
                    int i = fileChooser.showOpenDialog(m.getContentPane());
                    // 判断用户单击的是否为“打开”按钮
                    if (i == JFileChooser.APPROVE_OPTION) {
                        // 获得选中的图片对象
                        selectedFile = fileChooser.getSelectedFile();
                        ImageIcon icon =new ImageIcon(selectedFile.getAbsolutePath());
                        sopw=String.valueOf(icon.getIconWidth());
                        soph=String.valueOf(icon.getIconHeight());
//                        if (icon.getIconWidth()>F || icon.getIconHeight()>(F-F/4))
//                            icon.setImage(icon.getImage().getScaledInstance(F,F-F/4,Image.SCALE_SMOOTH));
                        label.setIcon(icon);// 将图片显示到标签上
                        label2.setText("");
                        scrollPane1.setVisible(true);
                        //System.out.println(selectedFile.getAbsolutePath());
                        op=true;
                        opstatic=true;
                        sop=selectedFile.getAbsolutePath();//获取文件绝对路径

                        System.out.println(sopw+" "+soph);
                        group1.clearSelection();
                        w1.setText(sopw);
                        h1.setText(soph);
                    }
                }
                if (str =="保存图片") {
                    if(op){//已打开文件
                        try {
                            System.out.println("保存图片"+sop);
                            if(dakai){
                                selectedFile = fileChooser.getSelectedFile();//重载数据，写入流写完会清空数据//iis.close();
                            }else
                                selectedFile =new File(sop);
                            BufferedImage img = ImageIO.read(selectedFile);
                            //BufferedImage img1 = new BufferedImage(Integer.parseInt(w1.getText()),
                            //        Integer.parseInt(h1.getText()), BufferedImage.TYPE_INT_BGR);//TYPE_INT_BGR三原色
                            Image imgg= img.getScaledInstance(Integer.parseInt(w1.getText()),Integer.parseInt(h1.getText()),img.SCALE_SMOOTH);
                            img = toBufferedImage(imgg);
                            System.out.println(Integer.parseInt(w1.getText())+","+Integer.parseInt(h1.getText()));

                            //图像格式
                            ImageInputStream iis = ImageIO.createImageInputStream(selectedFile);
                            Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
                            if (!iter.hasNext()) {
                                throw new RuntimeException("非图像文件格式");
                            }
                            ImageReader reader = iter.next();
                            System.out.println("Format: " + reader.getFormatName());

                            //输出文件
                            //(按钮组不行)
                            //sop=selectedFile.getAbsolutePath();
                            JFileChooser fc=new JFileChooser();
                            fc.setMultiSelectionEnabled(false);
                            int result=fc.showSaveDialog(null);
                            if (result==JFileChooser.APPROVE_OPTION) {
                                File file=fc.getSelectedFile();
                                String soppen = fc.getName(file);	//从文件名输入框中获取文件名
//保存文件
                                BufferedImage imgqq=imgq;
                                BufferedImage imggg=img;
                                if(oimgq){
                                    if(r1.isSelected()){
                                        //假如用户填写的文件名不带我们制定的后缀名，那么我们给它添上后缀
                                        if(!soppen.contains("." + reader.getFormatName())) {
                                            file = new File(fc.getCurrentDirectory(), soppen + "." + reader.getFormatName());
                                        }
                                        ImageIO.write(imgqq, reader.getFormatName(), file);
                                        oimgq=false;
                                    }
                                    if (r2.isSelected()){
                                        if(!soppen.contains("." + reader.getFormatName())) {
                                            file = new File(fc.getCurrentDirectory(), soppen + ".jpg");
                                        }
                                        ImageIO.write(imgqq, "JPG", file);
                                        oimgq=false;
                                    }
                                    if (r3.isSelected()){
                                        if(!soppen.contains("." + reader.getFormatName())) {
                                            file = new File(fc.getCurrentDirectory(), soppen + ".png");
                                        }
                                        ImageIO.write(imgqq, "PNG", file);
                                        oimgq=false;
                                    }
                                    if (r4.isSelected()){
                                        if(!soppen.contains("." + reader.getFormatName())) {
                                            file = new File(fc.getCurrentDirectory(), soppen + ".bmp");
                                        }
                                        ImageIO.write(imgqq, "BMP", file);
                                        oimgq=false;
                                    }
                                    if (r5.isSelected()){
                                        if(!soppen.contains("." + reader.getFormatName())) {
                                            file = new File(fc.getCurrentDirectory(), soppen + ".gif");
                                        }
                                        ImageIO.write(imgqq, "GIF", file);
                                        oimgq=false;
                                    }
                                }
                                else{
                                    if(r1.isSelected()){
                                        //假如用户填写的文件名不带我们制定的后缀名，那么我们给它添上后缀
                                        if(!soppen.contains("." + reader.getFormatName())) {
                                            file = new File(fc.getCurrentDirectory(), soppen + "." + reader.getFormatName());
                                        }
                                        ImageIO.write(imggg, reader.getFormatName(), file);
                                    }
                                    if (r2.isSelected()){
                                        if(!soppen.contains("." + reader.getFormatName())) {
                                            file = new File(fc.getCurrentDirectory(), soppen + ".jpg");
                                        }
                                        ImageIO.write(imggg, "JPG", file);
                                    }
                                    if (r3.isSelected()){
                                        if(!soppen.contains("." + reader.getFormatName())) {
                                            file = new File(fc.getCurrentDirectory(), soppen + ".png");
                                        }
                                        ImageIO.write(imggg, "PNG", file);
                                    }
                                    if (r4.isSelected()){
                                        if(!soppen.contains("." + reader.getFormatName())) {
                                            file = new File(fc.getCurrentDirectory(), soppen + ".bmp");
                                        }
                                        ImageIO.write(imggg, "BMP", file);
                                    }
                                    if (r5.isSelected()){
                                        if(!soppen.contains("." + reader.getFormatName())) {
                                            file = new File(fc.getCurrentDirectory(), soppen + ".gif");
                                        }
                                        ImageIO.write(imggg, "GIF", file);
                                    }

                            }
                            }
                            iis.close();


                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                    }else
                        JOptionPane.showMessageDialog(null, "没有选择图像文件");
                }
                if (str =="重置"){
//                    if(op){
//                        jradio6.setSelected(true);
//                        w1.setText(sopw);
//                        h1.setText(soph);
//                        r1.setSelected(true);
//                        jc2.setSelected(false);
//                        jc3.setSelected(false);
//                    }else{
//                        jradio1.setSelected(true);
//                        w1.setText("295");
//                        h1.setText("413");
//                        r1.setSelected(true);
//                        jc2.setSelected(false);
//                        jc3.setSelected(false);
//                    }
                    op=false;
                    opstatic=false;
                    jradio1.setSelected(true);
                    w1.setText("295");
                    h1.setText("413");
                    r1.setSelected(true);
                    jc2.setSelected(false);
                    jc3.setSelected(false);
                    label2.setText("可以拖拽图像文件到此处");
                    label.setIcon(null);
                    scrollPane1.setVisible(false);
                    dakai=false;
                }

                if(str=="图片去水印"){
                    String s=new File("").getAbsolutePath()+"\\res\\Inpaint.exe";
                    try {
                        Process proc = new ProcessBuilder(s).start();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                if(str=="图像压缩"){
                    String s=new File("").getAbsolutePath()+"\\res\\Caesium_boxed.exe";
                    try {
                        Process proc = new ProcessBuilder(s).start();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                if(str=="图片转ICO"){
                    String s=new File("").getAbsolutePath()+"\\res\\PictureToIco.exe";
                    try {
                        Process proc = new ProcessBuilder(s).start();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        return button;
    }
    private JLabel createlb(String str){
        JLabel lb=new JLabel(str);
        lb.setForeground(new Color(0x000000));
        lb.setFont(new Font("微软雅黑",Font.BOLD, 18));
        return lb;
    }//标签模板
    private BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }
        image = new ImageIcon(image).getImage();
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            int transparency = Transparency.OPAQUE;
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(
                    image.getWidth(null), image.getHeight(null), transparency);
        } catch (HeadlessException e) {
            //........
        }

        if (bimage == null) {
            int type = BufferedImage.TYPE_INT_RGB;
            bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
        }
        Graphics g = bimage.createGraphics();

        g.drawImage(image, 0, 0, null);
        g.dispose();

        return bimage;

    }//格式转化image to BufferedImage
    //文件拖拽
    public void drag() {
        //null为拖拽对象（面板标签）
        new DropTarget(label2, DnDConstants.ACTION_COPY_OR_MOVE,new DropTargetAdapter() {
            @Override
            public void drop(DropTargetDropEvent dtde) {//拖拽目标事件
                try{
                    if(dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {//是否支持文件拖拽
                        dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);//接受文件拖拽
                        //创建多文件拖拽列表对象
                        java.util.List<File> list=(List<File>)(dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor));
                        String temp="";
                        if(list.size()>1){
                            JOptionPane.showMessageDialog(null, "拖入文件过多");
                        }else{
                            for(File file:list) {
                                if(file.getAbsolutePath().endsWith("jpg") || file.getAbsolutePath().endsWith("png")
                                        || file.getAbsolutePath().endsWith("jpeg")|| file.getAbsolutePath().endsWith("bmp")
                                        || file.getAbsolutePath().endsWith("gif")){

                                    ImageIcon icon =new ImageIcon(file.getAbsolutePath());
                                    sopw=String.valueOf(icon.getIconWidth());
                                    soph=String.valueOf(icon.getIconHeight());
//                                    if (icon.getIconWidth()>F || icon.getIconHeight()>(F-F/4))
//                                        icon.setImage(icon.getImage().getScaledInstance(F,F-F/4,Image.SCALE_SMOOTH));
                                    label.setIcon(icon);// 将图片显示到标签上
                                    scrollPane1.setVisible(true);
                                    sop=file.getAbsolutePath();

                                    label.setText("");
                                    op=true;
                                    opstatic=true;
                                    //sop=selectedFile.getAbsolutePath();//获取文件绝对路径

                                    System.out.println(sopw+" "+soph);
                                    group1.clearSelection();
                                    w1.setText(sopw);
                                    h1.setText(soph);

                                    dtde.dropComplete(true);
                                }else{
                                    JOptionPane.showMessageDialog(null, "非图像格式");
                                }

                            }
                        }
                    }
                    else {
                        dtde.rejectDrop();
                    }
                }catch(Exception e){e.printStackTrace();}
            }
        });


    }
}


public class Maindeal extends JFrame {
    private Font f = new Font("微软雅黑",Font.PLAIN,15);
    private Font f2 = new Font("微软雅黑",Font.PLAIN,12);
    private boolean IfRemove = true;
    private JPanel paintPanel;//画板
    private JMenuItem about;
    public JMenuItem _getabout(){
        return about;
    }

    public static void main(String[] args) {
        Maindeal c = new Maindeal();
        c._init();


    }
    public void _init(){
        //--------------------------------界面部分-----------------------------------
        lb lbb=new lb(this);

        String lookAndFeel = UIManager.getSystemLookAndFeelClassName();//采用系统UI风格
        try {
            UIManager.setLookAndFeel(lookAndFeel);
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException e1) {
            e1.printStackTrace();
        }
        //窗口属性设置
        Image img = Toolkit.getDefaultToolkit().getImage("res//mi64.png");//窗口图标
        setIconImage(img);
        setTitle("图像处理 V1.0 By小子斌");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,550);
        setResizable(false);
        setLayout(null);//采用绝对布局
        setLocationRelativeTo(null);
        setFocusable(true);

        //添加背景图片
        ImageIcon backgroundImage = new ImageIcon("res/1.jpg");
        backgroundImage.setImage(backgroundImage.getImage().getScaledInstance(800,550,Image.SCALE_SMOOTH));//图片伸缩
        JLabel background_label = new JLabel(backgroundImage);
        background_label.setBounds(0,0, this.getWidth(), this.getHeight());
        background_label.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                background_label.requestFocus();//获取焦点
            }
        });
        this.getLayeredPane().add(background_label, Integer.valueOf(Integer.MIN_VALUE));

        JPanel imagePanel = (JPanel) this.getContentPane();
        imagePanel.setOpaque(false);//设置控件透明


        //菜单栏
        JMenuBar bar = new JMenuBar();
        bar.setBackground(Color.white);
        setJMenuBar(bar);
        JMenu Settings = new JMenu("打开");
        Settings.setFont(f);
        JMenu Help = new JMenu("编辑");
        Help.setFont(f);
        JMenu About = new JMenu("设置");
        About.setFont(f);
        bar.add(Settings);
        bar.add(Help);
        bar.add(About);

        JMenuItem set_background = new JMenuItem("打开");
        set_background.setFont(f2);
        JMenuItem set_head = new JMenuItem("保存");
        set_head.setFont(f2);
        JMenuItem set_body = new JMenuItem("重置");
        set_body.setFont(f2);
        JMenuItem set_speed= new JMenuItem("退出");
        set_speed.setFont(f2);
        Settings.add(set_background);
        Settings.add(set_head);
        Settings.add(set_body);
        Settings.add(set_speed);
        JMenuItem wu= new JMenuItem("空空如也");
        set_speed.setFont(f2);
        Help.add(wu);

        set_background.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){lbb.getbb(1).doClick(); }//About()自类关于菜单
        });
        set_head.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){ lbb.getbb(2).doClick(); }//Help()自类帮助菜单
        });
        set_body.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){ lbb.getbb(3).doClick();}//Help()自类帮助菜单
        });
        set_speed.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){ System.exit(0);}//Help()自类帮助菜单
        });





        about = new JMenuItem("证件照使用说明");
        about.setFont(f2);
        About.add(about);
        JMenuItem about4 = new JMenuItem("软件使用说明");
        about4.setFont(f2);
        About.add(about4);
        JMenuItem about2 = new JMenuItem("关于");
        about2.setFont(f2);
        About.add(about2);
        JMenuItem about3 = new JMenuItem("退出");
        about3.setFont(f2);
        About.add(about3);
        about.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){ new Help(); }//About()自类关于菜单
        });
        about2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){ new About(); }//Help()自类帮助菜单
        });
        about3.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){ System.exit(0);}//Help()自类帮助菜单
        });
        about4.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){ new Help2(); }//Help()自类帮助菜单
        });
        //界面属性
        setVisible(true);


        //---------------------------------------------------------------------
        //菜单事件





    }


}
