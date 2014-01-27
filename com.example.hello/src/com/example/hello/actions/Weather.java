package com.example.hello.actions;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Weather extends JFrame {

	//private JPanel contentPane;
	private JEditorPane jEditorPane = null; 
	String city=null; 
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Weather frame = new Weather();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public Weather(String city) {
		super();
		this.city = city;
		setTitle("\u5929\u6C14\u9884\u62A5");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		//contentPane = new JPanel();
		//contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		//contentPane.setLayout(new BorderLayout(0, 0));
		try 
        { 
            // 构建 URL 对象
            URL url =
new URL("http://weather.news.sina.com.cn//cgi-bin/figureWeather/simpleSearch.cgi?city="
+city); 
            String temp=""; 
            BufferedReader in 
                 = new BufferedReader(new InputStreamReader(url.openStream()));
            // 使用 openStream 得到一输入流并由此构造一个 BufferedReader 对象
            String inputLine; 
            // 从输入流不断的读数据，直到读完为止
            while ((inputLine = in.readLine()) != null) 
                temp=temp+inputLine+"\n"; 
            // 关闭输入流
            in.close();  
            String  weather     
                 =temp.substring ( temp.indexOf( "<body"), 
                                  temp.lastIndexOf( "body>")+5); 
            
            this.jEditorPane .setText(weather); 
        } 
        catch (Exception e) 
        { 
            e.printStackTrace(); 
        } 
		setContentPane(getJEditorPane());
	}
	private JEditorPane getJEditorPane() 
    { 
        if (jEditorPane == null) 
        { 
            jEditorPane = new JEditorPane(); 
            jEditorPane.setContentType( "text/html"); 
        } 
        return jEditorPane; 
    } 
}
