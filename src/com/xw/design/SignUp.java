package com.xw.design;


import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SignUp extends JFrame{
    private static final long serialVersionUID = 3054293481122038909L;

    private Properties pro = new Properties(); // 最好时静态的，因为账户是共享的

    // 命名全部要改,不要简写,要有意义,做到见名知意 (看见变量名,就知道它是干啥用的)
    private JPanel panel = new JPanel();
    private JLabel label = new JLabel("用户名: ");
    private JTextField field = new JTextField(15);

    private JPanel panel2 = new JPanel();
    private JLabel label2 = new JLabel("密码: ");
    private JPasswordField field2 = new JPasswordField(15);

    private JPanel panel3 = new JPanel();
    private JLabel label3 = new JLabel("确认密码: ");
    private JPasswordField field3 = new JPasswordField(15);

    private JPanel panel4 = new JPanel();
    private JButton button = new JButton("确认");
    private JButton button2 = new JButton("退出");

    public void initListener(){
        field.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                field2.grabFocus();
            }

        });

        field2.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                field3.grabFocus();
            }
        });
        field3.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                ok_actionPerformed(e);
            }
        });

        // 确认
        button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                ok_actionPerformed(e);
            }
        });

        // 退出
        button2.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                cancel_actionPerformed(e);
            }
        });
    }
    
    public void ok_actionPerformed(ActionEvent e){
        String userName = field.getText();
        String password = new String(field2.getPassword());
        String password2 = new String(field3.getPassword());
        if (userName.equals("")){
            JOptionPane.showMessageDialog(SignUp.this, "用户名不能为空");
        } else {
            if (password.equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(SignUp.this, "密码不能为空");
            } else {
                if (password2.equalsIgnoreCase(password)) {
                    if (isExist(userName)) {
                        JOptionPane.showMessageDialog(SignUp.this, "用户已存在");
                        field.setText("");
                        field2.setText("");
                        field3.setText("");
                    } else {
                        pro.setProperty(userName, password);
                        JOptionPane.showMessageDialog(SignUp.this, "注册成功");
                        writeToPro(userName, password); // 将其写入到账户文件中
                        SignUp.this.dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(SignUp.this, "两次密码不一致");
                    field3.setText("");
                }
            }
        }
    }

    public void cancel_actionPerformed(ActionEvent e){
        System.exit(0);
    }

    public SignUp(){
        super("注册");
        // 加载账户文件
        try{
            pro.load(new FileInputStream(new File("src/test/accouts.properties")));
        } catch (IOException e){
            e.printStackTrace();
        }
        // 初始化窗口组件的监听
        initListener();

        this.setLocation(new Point(500, 300));
        this.setSize(280, 210);
        this.setLayout(new GridLayout(4, 1, 0, 20)); // 垂直间隙为20px
        // 这些变量名,全部要改
        panel.setBackground(Color.white);
        panel2.setBackground(Color.white);
        panel3.setBackground(Color.white);
        panel4.setBackground(Color.white);
        panel.add(label);
        panel.add(field);
        panel2.add(label2);
        panel2.add(field2);
        panel3.add(label3);
        panel3.add(field3);
        panel4.add(button);
        panel4.add(button2);
        this.add(panel);
        this.add(panel2);
        this.add(panel3);
        this.add(panel4);
        

        this.getContentPane().setBackground(Color.white);
        this.setAlwaysOnTop(true);
        this.setResizable(false);
        this.setVisible(true);
    }

    // 如果注册始终可用,就要保存起来，否则不需要写入文件中，注册账户本次使用
    // 将账户名与其对应密码保存到指定的账户文件中
    public void writeToPro(String userName, String password){
        pro.setProperty(userName, password);
        try{
            pro.store(new FileOutputStream(new File("src/test/accouts.properties")), "allAccouts");
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    // 判断此用户名是否已经存在
    public boolean isExist(String userName){
        Enumeration enumer = pro.propertyNames();
        while (enumer.hasMoreElements()){
            String temp = (String) enumer.nextElement();
            if (temp.equals(userName)){
                return true;
            }
        }
        return false;
    }
}
