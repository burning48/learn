package com.example.hello.actions;

public class MailConfig {
	String popServer; 
    String popUser; 
    String popPassword; 
    
 // �� �� �� Ҫ �� �� �� �� �� �� �� �� �� �� �� �� �� �� ��
    String [] strFrom; 
    String [] strSubject;    
    
    // �� �� �� ʾ �� �� �� �� 
    boolean isViewContent=false;
    
    public String getPopServer(){
    	return popServer;
    }
    public String getPopUser(){
    	return popUser;
    }
    public String getPopPassword(){
    	return popPassword;
    }
    public String[] getStrFrom(){
    	return strFrom;
    }
    public String[] getStrSubject(){
    	return strSubject;
    }
}
