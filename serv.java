import java.net.*;
import java.io.*;

import java.lang.*;

import java.util.*;


import java.text.*; 


import javax.comm.*;


class com2client extends Thread{
    OutputStream os;
    Socket cs;
    InputStream f;
    serv s;

    public com2client(Socket cs,InputStream f,serv s) throws Exception{
      this.os=cs.getOutputStream();
      this.cs=cs;
      this.f=f;
      this.s=s; 
    }

    public void run(){
	//httpReq req=new httpReq();
        try{ 
            while(true){

            int a;
            while( (a=f.available())==0 ){
                //Thread.sleep(3);
                if(cs.isClosed()) break;
            }
            byte[]b=new byte[a];
            int r;
            //synchronized(s.serialPort){
                r=f.read(b);
            //}
            os.write(b);       
            //System.out.println("="+a+","+r);

            } 
        }catch(Exception e){
            try{
                cs.close();
            }catch(Exception e1){}
            e.printStackTrace();
        }
            try{
                cs.close();
            }catch(Exception e1){}
        System.out.println("ThreadFinished co2c");
    } 
}


class client2com extends Thread{
    InputStream is;
    Socket cs;
    OutputStream f;
    serv s;

    public client2com(Socket cs,OutputStream f,serv s) throws Exception{
      this.is=cs.getInputStream();
      this.cs=cs;
      this.f=f;
      this.s=s;
    }

    public void run(){
	//httpReq req=new httpReq();
        try{ 
            int i;
            while(true){
                int a;
                while( (a=is.available())==0){
                    //Thread.sleep(3);
                    if(cs.isClosed()) break;
                }
                byte[]b=new byte[a];
                int r;
                //synchronized(s.serialPort){
                    r=is.read(b); 
                //}
                f.write(b);  
                //System.out.println("="+a+","+r); 
            }	    
        }catch(Exception e){
            try{
                cs.close();
            }catch(Exception e1){}
            e.printStackTrace();
        }
            try{
                cs.close();
            }catch(Exception e1){}
        System.out.println("ThreadFinished c2co");
    } 
}


class serv{
  public SerialPort serialPort;
 
  public serv(){
    try{

        CommPortIdentifier portId;
        Enumeration portList;
        InputStream fi=null;
        OutputStream fo=null;

        //SerialPort serialPort;

        portList = CommPortIdentifier.getPortIdentifiers();

        System.out.println(">1"+portList);
        while (portList.hasMoreElements()) {
            portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                System.out.println(">"+portId.getName()); 
                if (portId.getName().equals("COM4")) {                

                    System.out.println(">>>");
                    try {
                        serialPort = (SerialPort) portId.open("SimpleReadApp", 2000);
                        fi = serialPort.getInputStream();
                        fo = serialPort.getOutputStream();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        System.out.println(">E");


      ServerSocket ss=new ServerSocket(5555);
      Socket cs0=null;
      com2client csv=null;
      client2com cs1=null;
      while(true){
        Socket cs=ss.accept();
        if(cs0!=null){
            try{
                csv.stop();
                cs1.stop();
            }catch(Exception ee){}
        }
        cs0=cs;

        System.out.println("[accepted]");

        csv=new com2client(cs,fi,this);
        csv.start();
        cs1=new client2com(cs,fo,this);
        cs1.start();
      }
    }catch(Exception e){
      e.printStackTrace(); 
    }
  }

  public static void main(String[]s){
     new serv();
  }
}