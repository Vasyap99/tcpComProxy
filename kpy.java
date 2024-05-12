import java.io.*;
import java.util.*;

class formatObj{  //для ф-ции format()
    private String s;
    private int n=0;//текущий номер для подстановки значения
    public String toString(){return s;}
    int[] findN(int i){ //найти индекс символа в строке для указанного номера элемента для подстановки
        int n1=0;
        for(int j=0;j<s.length();j++){
            if(s.charAt(j)=='{'){
                String buf="";
                int k;
                for(k=j+1;s.charAt(k)!='}';k++){
                    buf=buf+s.charAt(k);
                }
                if(i==Integer.parseInt(buf))return new int[]{j,k};
                n1++;
            }
        }
        return null;
    }
    public formatObj(String s){
        this.s=s;
    }
    public formatObj v(int i){
        int t[]=findN(n);
        System.out.println(""+t[0]+':'+t[1]);
        s=s.substring(0,t[0])+Integer.toString(i)+s.substring(t[1]+1,s.length());
        n++;
        return this;
    }
    public formatObj v(String s1){
        int t[]=findN(n);
        System.out.println(""+t[0]+':'+t[1]);
        s=s.substring(0,t[0])+s1+s.substring(t[1]+1,s.length());
        n++;
        return this;
    }
    public formatObj v(float f){
        int t[]=findN(n);
        System.out.println(""+t[0]+':'+t[1]);
        s=s.substring(0,t[0])+f+s.substring(t[1]+1,s.length());
        n++;
        return this;
    }
    public formatObj v(boolean b){
        int t[]=findN(n);
        System.out.println(""+t[0]+':'+t[1]);
        s=s.substring(0,t[0])+b+s.substring(t[1],s.length());
        n++;
        return this;
    }
    public formatObj v(Object o){
        int t[]=findN(n);
        System.out.println(""+t[0]+':'+t[1]);
        s=s.substring(0,t[0])+o+s.substring(t[1]+1,s.length());
        n++;
        return this;
    }
}

class kpy{
  static formatObj format(String s){
      return new formatObj(s);
  }
  static String[] split(String s,String s2){
    ArrayList al=new ArrayList();
    int t=0; //начало текущего токена
    int i=0;
    while(i<s.length()){
        if(s.charAt(i)==s2.charAt(0)){ //сравнение с подстрокой
            boolean b=true;
            int j;
            for(j=0;j<s2.length();j++){
                b=b && s.charAt(i+j)==s2.charAt(j);
                if(!b)break;
            }
            if(b){
                 String st=s.substring(t,i);
                 al.add(st);
                 t=i+j;
                 i+=s2.length()-1;
                 System.out.println(">>>"+st+" t=="+t+" i=="+i);
            }
        }
        i++;
    } 
    al.add(s.substring(t,s.length()));//last elem
    Iterator it=al.iterator();
    String[]r=new String[al.size()];
    int q=0;
    while(it.hasNext()){
        r[q]=(String)it.next();
        q++;
    }
    return r; 
  }
  static byte[] open_and_read(String s) throws Exception{
    RandomAccessFile rf=new RandomAccessFile(s,"r");
    byte[]b=new byte[(int)rf.length()];
    rf.read(b);
    return b;
  }
  public static void main(String[]s){
      System.out.println("[test1]");
      String[]e=split("kakashka","ka");
      for(int i=0;i<e.length;i++){
          System.out.println("elem=="+e[i]);
      }
      System.out.println("[test2]");
      e=split("kakashka","a");
      for(int i=0;i<e.length;i++){
          System.out.println("elem=="+e[i]);
      }
      System.out.println("[test3]");
      e=split("annunak","a");
      for(int i=0;i<e.length;i++){
          System.out.println("elem=="+e[i]);
      }
      System.out.println("[test4]");
      e=split("kakashka","sh");
      for(int i=0;i<e.length;i++){
          System.out.println("elem=="+e[i]);
      }
      System.out.println("[test-format]");
      System.out.println(format("=={1},{0}").v(56).v("kok"));

  }
}