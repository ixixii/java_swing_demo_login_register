package com.xw.design;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import javax.swing.JTextField;


import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Random;

import javax.swing.JPasswordField;

import com.xw.design.SignUp;

import java.awt.Label;
import java.awt.TextField;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Design {
	JFrame framemain;
	
    private Properties pro = new Properties();
    private boolean ver_code = false; // 默认输入验证码错误
	private JPasswordField passwordField;
	private JTextField usernameText;
	private JTextField tf;
	private JLabel lb;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Design window = new Design();
					window.framemain.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Design() {
		 // 加载账户文件
        try{
            pro.load(new FileInputStream(new File("src/test/accouts.properties"))); // 从指定位置将账户文件加载进来
        } catch (IOException e){
            e.printStackTrace();
        }
        
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		framemain = new JFrame();
		// 新增: 标题
		framemain.setTitle("用户登录");
		framemain.setResizable(false);
		framemain.setBounds(400, 200, 450, 450);
		framemain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Button btgLogion = new Button("登陆");
		btgLogion.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
		});
		btgLogion.setBounds(114, 262, 65, 37);
		btgLogion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                try{
                    pro.load(new FileInputStream(new File("src/test/accouts.properties")));
                } catch (IOException e1){
                    e1.printStackTrace();
                }
                check();
			}
		});
		framemain.getContentPane().setLayout(null);
		framemain.getContentPane().add(btgLogion);
		
		Button signup = new Button("注册");
		signup.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		signup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SignUp();
			}
		});
		signup.setBounds(264, 262, 65, 37);
		framemain.getContentPane().add(signup);
		
		usernameText = new JTextField();
		usernameText.setBounds(114, 65, 215, 24);
		// 新增: 提示
		usernameText.addFocusListener(new JTextFieldHintListener(usernameText, "请输入用户名"));
		framemain.getContentPane().add(usernameText);

		
		JLabel labelusername = new JLabel("\u5BC6\u7801");
		labelusername.setBounds(38, 129, 54, 15);
		framemain.getContentPane().add(labelusername);
		
		JLabel labelpassword = new JLabel("\u8D26\u53F7");
		labelpassword.setBounds(38, 69, 54, 15);
		framemain.getContentPane().add(labelpassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(112, 126, 215, 21);
		framemain.getContentPane().add(passwordField);
		
		
		tf = new JTextField();
		tf.addFocusListener(new JTextFieldHintListener(tf, "请输入验证码"));

		tf.setBounds(112, 200, 100, 50);
		framemain.getContentPane().add(tf);
		
		
		lb = new JLabel();
		lb.setBounds(230,200,50,50);
		lb.setText(makeYanZhengMa());
		lb.setBackground(new Color(0).YELLOW);
		framemain.getContentPane().add(lb);
		
		// 新增: 
		lb.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {
				// 处理鼠标点击
				System.out.print("clicked");
				// 刷新验证码
				lb.setText(makeYanZhengMa());
			}
			public void mouseEntered(MouseEvent e) {
				// 处理鼠标移入
			}
			public void mouseExited(MouseEvent e) {
				// 处理鼠标离开
			}
			public void mousePressed(MouseEvent e) {
				// 处理鼠标按下
			}
			public void mouseReleased(MouseEvent e) {
				// 处理鼠标释放
			}
		});
	}
	
	public String makeYanZhengMa(){
		String[] str = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N","O",
				"P", "Q", "R", "S" , "T", "U", "V", "W", "X", "Y", "Z" ,
				"a","b","c","d","e","f","j","h","i","k","l","m","n","o","p", "q", "r", "s", "t", "u", "v", "w", "x", "y",
				"z","0","1","2", "3", "4", "5", "6", "7", "8", "9"};
		Random random = new Random();
		// 这个地方有时候会报数组越界(造成闪退), 判断一个数组下标的有效范围
		int i = random.nextInt(62);
		int j = random.nextInt(62);
		int k = random.nextInt(62);
		int l = random.nextInt(62);
		
		return str[i]+str[j]+str[k]+str[l];
	}
	
	 // 验证用户输入的账户名和密码是否正确(通过与加载进来的账户 pro 比对)
    public boolean isPass(String name, String password){
        Enumeration en = pro.propertyNames();
        while(en.hasMoreElements()){
            String curName = (String)en.nextElement();
            if(curName.equalsIgnoreCase(name)){
                if(password.equalsIgnoreCase(pro.getProperty(curName))){
                    return true;
                }
            }
        }
        return false;
    }
    // 检查用户名或密码
    public void check(){
		String username = usernameText.getText();
		String password = passwordField.getText();
		String verCode = tf.getText();
		String getcode = lb.getText();
        if(verCode.equals("")){
            JOptionPane.showMessageDialog(null,"请输入验证码");
            return;
        }else{
            if(verCode.toLowerCase().equals(getcode.toLowerCase())){
                ver_code = true;
            }else{
                ver_code = false;
            }
        }
        if(ver_code == false){
            JOptionPane.showMessageDialog(null,"验证码错误");
            // 刷新验证码
			lb.setText(makeYanZhengMa());
			tf.setText("");
            return;
        }
        if (isPass(username, password)){
        	if(ver_code == true){
            JOptionPane.showMessageDialog(null,"登录成功・・・");
//            this.dispose();            
        	}
        } else {
            JOptionPane.showMessageDialog(null, "用户名或密码错误");
            // 刷新验证码
			lb.setText(makeYanZhengMa());
            // 新增: 输入错误也不要清空用户名和密码
            // usernameText.setText("");
            // passwordField.setText("");
            tf.setText("");
        }
    }
}