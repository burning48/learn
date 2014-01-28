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
    
    // ������ʾ����ʼ�����
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
     * ���캯��
     * @param mc : ��Ҫ��ʾ�Ķ���������ö���
     */ 
    public MailDialog(MailConfig[] mc) 
    { 
    	
        super(); 
      
        if(mc!=null) 
            this.mc = mc; 
        else 
            System.err.println("�ʼ����ô���") ; 
        
        initialize(); 
    } 
    /** 
     * This method initializes this 
     * ��ʼ��
     * @return void 
     */ 
    private void initialize() 
    { 
        try 
        { 
            // �趨��ʾ���ݵ����
            this.setContentPane(getJTextPane()); 
            // ȡ�����е����ʼ���Ϣ
            String s= getAllMailInfo();            
            // ����Ϣ��ʾ�ڶԻ�����
            this.jTextPane .setText(s); 
           
            this.setTitle("�ʼ���Ϣ"); 
            this.setSize(251, 100); 
        } 
        catch (Exception e) 
        {           
        	 // ����������ʾ������Ϣ
        	 this.jTextPane .setText(e.toString()); 
            e.printStackTrace(); 
        } 
        
    } 
    /** ȡ�����е��������Ҫ��ص��ʼ���Ϣ
	 * 
	 * @return String 
	 */ 
    private String getAllMailInfo() 
    { 
    	 String allMailInfo=""; 
    	
    	 if (mc.length <1) 
    		 allMailInfo="û���������䣡"; 
    	 else 
    	 { 
    		 for(int i=0;i<mc.length;i++) 
    		 { 
    			 // ѭ����ȡÿ��������ʼ���Ϣ
    			 allMailInfo=allMailInfo+getMailInfo(mc[i]); 
    		 } 
    	 } 
    	 // ��û���յ���ص��ʼ�
    	 if (allMailInfo.trim().length() ==0) 
    		 allMailInfo="δ��⵽������ʼ���"; 
    	 return allMailInfo; 
    	
    } 
    /* 
     * �õ�һ�������������������������ʼ����ַ�����ʽ
     **/ 
    private String getMailInfo(MailConfig mc) 
    { 
    	 // ����������ʼ���Ϣ
        String mailInfo=""; 
    	
        // ÿ������������ϵ� Store �� Folder ����
    	 Store store=null; 
        Folder folder=null; 
        
        try 
        { 
           
            Properties props = System.getProperties(); 
            // ���ʼ�����������һ�� Session 
            Session session = Session.getDefaultInstance( props,null); 
     
           // �������������û������������ӷ�����
            store = session.getStore("pop3"); 
            store.connect(mc.getPopServer(),mc.getPopUser(),mc.getPopPassword()); 
            
            // ȡ��Ĭ�ϵ��ʼ� Folder 
            folder = store.getDefaultFolder(); 
            if (folder == null) 
                throw new Exception("No default folder"); 
            
            // ȡ���ռ���
            folder = folder.getFolder("INBOX"); 
            if (folder == null) 
                throw new Exception("No POP3 INBOX"); 
            
            // ��ֻ����ʽ���ռ���
            folder.open(Folder.READ_ONLY); 
            
            // ��ȡ�������ʼ�������
            Message[] msgs = folder.getMessages(); 
           
            for (int i = 0; i < msgs.length; i++) 
            { 
                Message message= msgs[i]; 
                // ȡ��ÿ���ʼ�����Ϣ����Ҫ���� MailConfig ������йؼ��ֹ���
                mailInfo = mailInfo+ getMessageInfo( message,mc);                
            } 
            
        } 
        catch (Exception ex) 
        { 
            ex.printStackTrace(); 
        } 
        finally 
        { 
            // ��ȫ�Ĺر��ʼ���������Դ
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
     * �õ�һ���ʼ�����Ϣ����Ҫ���� MailConfig ����
	 * @param mailInfo 
	 * @param message 
	 * @return �ʼ���Ϣ
	 * @throws MessagingException 
	 * @throws IOException 
	 */ 
	 private String getMessageInfo( final Message message ,final MailConfig mc) 
		 throws MessagingException, IOException 
	 { 
		 // ���صĸ��ʼ���Ϣ
		 String mailInfo=""; 
		
		 String from=((InternetAddress)message.getFrom()[0]).getPersonal(); 
            
		 if (from==null) 
		    from=((InternetAddress)message.getFrom()[0]).getAddress(); 
		
		 String subject=message.getSubject(); 
		
		 // ������������Ϣ����ʾ�����򷵻ؿ�
        if(isElementinString(from,mc.getStrFrom()) 
        		 ||isElementinString(subject,mc.getStrSubject()) )   
        {        	
        	 mailInfo=mailInfo+"������ : "+from+"\n";                
        	 mailInfo=mailInfo+"�ʼ����� : "+subject+"\n"; 
        	 mailInfo=mailInfo+"����ʱ�� : "+message.getSentDate() +"\n"; 
        	
        	 // �����ʾ���ݣ����ӡ����
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
     * �ж�Ŀ��ؼ����������Ƿ���ָ�����ַ��� , ���й���
	 * @param targetStr ��
	 * @param keys ��
	 * @return 	����У����� true�� ���򷵻� false 
	 */ 
 
    private boolean isElementinString(String targetStr,String [] keys) 
    { 
    	 // ûָ��������������ʾ����
    	 if (keys==null) 
    		 return true; 
    	 // ָ���ַ���Ϊ�գ�ֱ�ӷ��� false 
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
