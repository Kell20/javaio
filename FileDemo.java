package javaio;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.*;
/*i want to write in file then to read it*/

class Thread1 implements Runnable{
    CountDownLatch tr;
    Thread th1;

    Thread1(CountDownLatch tr){
        this.tr=tr;
        th1=new Thread(this);
    }

    @Override
    public void run(){
      try{
        Scanner scan=new Scanner(System.in);
        byte q[]=new byte[56];
        String setName;
        double setSalary;

        System.out.println("set name here");
        setName=scan.nextLine();

        System.out.println("set salary");
        setSalary=scan.nextDouble();

        q=setName.getBytes();

        FileOutputStream file1=new FileOutputStream("C:\\Users\\HP\\Documents\\netdox.txt\\kelvin.txt");

        file1.write(q);
        file1.write((int)setSalary);
        file1.close();
      }catch(FileNotFoundException e){
        System.out.println(e.getMessage());
      }
      catch(IOException e){
        System.out.println(e.getMessage());
      }
      tr.countDown();
    }
}

class Thread2 implements Runnable{
    Thread th2;
    CountDownLatch cdl;

    Thread2(CountDownLatch cdl){
        th2=new Thread(this);
        this.cdl=cdl;
    }

    @Override
    public void run(){
        try{
           byte c[]=new byte[56]; 
           Thread.sleep(10000);
           FileInputStream file2=new FileInputStream("C:\\Users\\HP\\Documents\\netdox.txt\\kelvin.txt");
            
           file2.read(c);
           String str=new String(c);
           System.out.println(str);

            file2.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
    }
    catch(InterruptedException e){
        System.out.println(e.getMessage());
    }
       
  }
}
public class FileDemo{
    public static void main(String []args){
        CountDownLatch cdls=new CountDownLatch(1);
        Thread1 tet1=new Thread1(cdls);
        Thread2 tet2=new Thread2(cdls);

        tet1.th1.start();
        tet2.th2.start();
    }
}

