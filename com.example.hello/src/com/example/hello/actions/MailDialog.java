package com.example.hello.actions;

import java.awt.BorderLayout;

import java.awt.FlowLayout;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import com.jgoodies.forms.factories.DefaultComponentFactory;

public class MailDialog extends JDialog {

	//private final JPanel contentPanel = new JPanel();
	private JEditorPane jEditorPane = null; 
    private JTextPane jTextPane = null; 
    
    // 可以显示多个邮件配置
    MailConfig[]  mc= null; 
	/**
	 * @wbp.nonvisual location=7,17
	 */
	private final JLabel label = DefaultComponentFactory.getInstance().createTitle("\u90AE\u4EF6\u76D1\u63A7");

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		try {
			MailDialog dialog = new MailDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * Create the dialog.
	 */
	/*public MailDialog() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}*/
	/** 
     * This method initializes 
     * 构造函数
     * @param mc : 需要显示的多个邮箱配置对象。
     */ 
    public MailDialog(MailConfig[] mc) 
    { 
    	
        super(); 
      
        if(mc!=null) 
            this.mc = mc; 
        else 
            System.err.println("邮件配置错误！") ; 
        
        initialize(); 
    } 
    /** 
     * This method initializes this 
     * 初始化
     * @return void 
     */ 
    private void initialize() 
    { 
        try 
        { 
            // 设定显示内容的面板
            this.setContentPane(getJTextPane()); 
            // 取得所有的新邮件信息
            String s= getAllMailInfo();            
            // 将信息显示在对话框中
            this.jTextPane .setText(s); 
           
            this.setTitle("邮件信息"); 
            this.setSize(251, 100); 
        } 
        catch (Exception e) 
        {           
        	 // 发生错误显示错误信息
        	 this.jTextPane .setText(e.toString()); 
            e.printStackTrace(); 
        } 
        
    } 
    /** 取得所有的邮箱的需要监控的邮件信息
	 * 
	 * @return String 
	 */ 
    private String getAllMailInfo() 
    { 
    	 String allMailInfo=""; 
    	
    	 if (mc.length <1) 
    		 allMailInfo="没有配置邮箱！"; 
    	 else 
    	 { 
    		 for(int i=0;i<mc.length;i++) 
    		 { 
    			 // 循环获取每个邮箱的邮件信息
    			 allMailInfo=allMailInfo+getMailInfo(mc[i]); 
    		 } 
    	 } 
    	 // 还没有收到相关的邮件
    	 if (allMailInfo.trim().length() ==0) 
    		 allMailInfo="未检测到相关新邮件！"; 
    	 return allMailInfo; 
    	
    } 
    /* 
     * 得到一个邮箱中满足条件的所有新邮件的字符串形式
     **/ 
    private String getMailInfo(MailConfig mc) 
    { 
    	 // 最终输出的邮件信息
        String mailInfo=""; 
    	
        // 每个邮箱服务器上的 Store 和 Folder 对象
    	 Store store=null; 
        Folder folder=null; 
        
        try 
        { 
           
            Properties props = System.getProperties(); 
            // 与邮件服务器生成一个 Session 
            Session session = Session.getDefaultInstance( props,null); 
     
           // 给出服务器，用户名，密码连接服务器
            store = session.getStore("pop3"); 
            store.connect(mc.getPopServer(),mc.getPopUser(),mc.getPopPassword()); 
            
            // 取得默认的邮件 Folder 
            folder = store.getDefaultFolder(); 
            if (folder == null) 
                throw new Exception("No default folder"); 
            
            // 取得收件箱
            folder = folder.getFolder("INBOX"); 
            if (folder == null) 
                throw new Exception("No POP3 INBOX"); 
            
            // 以只读方式打开收件箱
            folder.open(Folder.READ_ONLY); 
            
            // 获取所有新邮件并处理
            Message[] msgs = folder.getMessages(); 
           
            for (int i = 0; i < msgs.length; i++) 
            { 
                Message message= msgs[i]; 
                // 取得每封邮件的信息，需要引用 MailConfig 对象进行关键字过滤
                mailInfo = mailInfo+ getMessageInfo( message,mc);                
            } 
            
        } 
        catch (Exception ex) 
        { 
            ex.printStackTrace(); 
        } 
        finally 
        { 
            // 安全的关闭邮件服务器资源
            try 
            { 
                if (folder!=null) folder.close(true); 
                if (store!=null) store.close(); 
            } 
            catch (Exception ex2) {ex2.printStackTrace();} 
        } 
        return mailInfo; 
    } 
    
    /** 
     * 得到一封邮件的信息，需要根据 MailConfig 过滤
	 * @param mailInfo 
	 * @param message 
	 * @return 邮件信息
	 * @throws MessagingException 
	 * @throws IOException 
	 */ 
	 private String getMessageInfo( final Message message ,final MailConfig mc) 
		 throws MessagingException, IOException 
	 { 
		 // 返回的改邮件信息
		 String mailInfo=""; 
		
		 String from=((InternetAddress)message.getFrom()[0]).getPersonal(); 
            
		 if (from==null) 
		    from=((InternetAddress)message.getFrom()[0]).getAddress(); 
		
		 String subject=message.getSubject(); 
		
		 // 如果满足过滤信息则显示，否则返回空
        if(isElementinString(from,mc.getStrFrom()) 
        		 ||isElementinString(subject,mc.getStrSubject()) )   
        {        	
        	 mailInfo=mailInfo+"发件人 : "+from+"\n";                
        	 mailInfo=mailInfo+"邮件主题 : "+subject+"\n"; 
        	 mailInfo=mailInfo+"发送时间 : "+message.getSentDate() +"\n"; 
        	
        	 // 如果显示内容，则打印内容
        	 if(mc.isViewContent) 
        		 mailInfo=mailInfo+message.getContent() +"\n"; 
        	
        	 mailInfo=mailInfo+"------------------------------------\n"; 
        } 
		 return mailInfo; 
	 } 
	
	 private JTextPane getJTextPane() 
    { 
        if (jTextPane == null) 
        { 
            jTextPane = new JTextPane(); 
        } 
        
        return jTextPane; 
    } 
   
	 /** 
     * 判断目标关键字数组中是否有指定的字符串 , 进行过滤
	 * @param targetStr ：
	 * @param keys ：
	 * @return 	如果有，返回 true， 否则返回 false 
	 */ 
 
    private boolean isElementinString(String targetStr,String [] keys) 
    { 
    	 // 没指定过滤条件，显示所有
    	 if (keys==null) 
    		 return true; 
    	 // 指定字符串为空，直接返回 false 
    	 if (targetStr==null) 
    		 return false; 
    	 for(int i=0;i<keys.length ;i++) 
    	 { 
    		 if (targetStr.indexOf(keys[i])>-1) 
    			 return true; 
    	 } 
    	 return false; 
    } 
}
