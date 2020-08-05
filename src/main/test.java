package main;


//

import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

class tuo extends JFrame{

    JPanel jp1;
    public tuo()
    {
        jp1 =new JPanel();
        jp1.setBackground(Color.yellow);
        getContentPane().add(jp1,BorderLayout.CENTER);
        setSize(500,200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(400,200);
        setTitle("tuozhuai");
        drag();
    }

    public static void _main(String[] args) {
        new tuo().setVisible(true);
    }

    public void drag() {
        new DropTarget(jp1,DnDConstants.ACTION_COPY_OR_MOVE,new DropTargetAdapter() {
            @Override
            public void drop(DropTargetDropEvent dtde) {//拖拽目标事件
                try{
                    if(dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {//是否支持文件拖拽
                        dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);//接受文件拖拽
                        //创建多文件拖拽列表对象
                        List<File>list=(List<File>)(dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor));
                        String temp="";
                        for(File file:list) {
                            temp+=file.getAbsolutePath()+";\n";//对各个文件用;隔开
                            JOptionPane.showMessageDialog(null, temp);
                            dtde.dropComplete(true);
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

class Main {
    private static final int  F=380;//图片标签宽高  //800 420

    public static void _main(String[] args) {
        JFrame jf = new JFrame("测试窗口");
        jf.setSize(250, 250);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        JLabel label=new JLabel("");
        ImageIcon icon =new ImageIcon("C:\\Users\\Administrator\\Desktop\\2.jpg");
        label.setIcon(icon);
        label.setForeground(new Color(0xDD1400));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JScrollPane scrollPane = new JScrollPane(
                label,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS
        );
        scrollPane.setBounds(0,0,F, F-F/4);

        // 创建滚动面板, 指定滚动显示的视图组件(textArea), 垂直滚动条一直显示, 水平滚动条从不显示
        JScrollPane scrollPane1 = new JScrollPane(
                label,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS
        );
        scrollPane1.setBounds(0,0,F, F-F/4);

        jf.setContentPane(scrollPane1);
        jf.setVisible(true);
    }

}


public class test {
    private int dx1, dy1, dx2, dy2;
    private int sx1, sy1, sx2, sy2;
    //读取图片格式
    public void _img() throws IOException {
        File file = new File("newimage.jpg");
        ImageInputStream iis = ImageIO.createImageInputStream(file);
        Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
        if (!iter.hasNext()) {
            throw new RuntimeException("No readers found!");
        }
        ImageReader reader = iter.next();
        System.out.println("Format: " + reader.getFormatName());
        iis.close();

}

    //绘图
    public void _aa() throws IOException {
        dx2 = sx2 = 1366; // 初始化图像大小
        dy2 = sy2 = 768;
        sx1 = Math.abs(sx1 - 1366);//sx1未定义默认为0
        sx2 = Math.abs(sx2 - 1366);//sx1,sx1` sx2,sx2`(0,300)→(300,0) 两个x轴端点位置调换 300可改 指定分割区域
        File file=new File("C:\\Users\\Administrator\\Desktop\\2.jpg");

        //final BufferedImage img = new BufferedImage(tWidth, tHeight, BufferedImage.TYPE_INT_RGB);//获得一个image对象
        BufferedImage img = ImageIO.read(file);
        final Graphics2D g2d =img.createGraphics();//获得一个图形类知
        g2d.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
        //g2d.drawOval(100, 100, 100, 100);//绘制图形
        //final OutputStream out = response.getOutputStream();//打开输出流
        File file2 = new File("C:\\Users\\Administrator\\Desktop\\3.jpg");
        ImageIO.write(img, "JPEG", file2);//保存成道图片版 out路径文件对象

    }

    //调用外部的exe
    public void _main() {
        String[] cmd = {"Notepad.exe","d:/test.txt"};
        Process process = null;
        try {

//            System.out.println(this.getClass().getResource("res\\Caesium_boxed.exe"));
//            System.out.println(new File("").getAbsolutePath()+"\\res\\Caesium_boxed.exe");//test.class.getResource("")
//            Runtime runtime = Runtime.getRuntime();
            String s=new File("").getAbsolutePath()+"\\res\\Caesium_boxed.exe";
//            process = runtime.exec("cmd /c start \"F:\\WorkSpace\\java\\tool\\Photodeal\\res\\Caesium_boxed.exe\"");//"cmd.exe /c "+s
//            System.out.println("\""+s+"\"");
            Process proc = new ProcessBuilder(s).start();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (process != null){
                process.destroy();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        test c = new test();
        c._aa();
        //c._main();

    }

}
