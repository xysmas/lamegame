package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.LinkedList;

public class WalkerServer extends Thread
{  
  private static final int PORT=4444;
  private static ServerSocket myServerSocket;
  private static boolean listening=true;
  public static LinkedList<WalkerThread> threadList=new LinkedList<>();
  private static int IDgen=0;
 
  public WalkerServer() throws IOException
  {
    try
    {
      myServerSocket=new ServerSocket(PORT);
    } catch (IOException e)
    {
      System.err.println("Could not listen on port: "+PORT);
      System.exit(-1);
    }
    
    this.start();
  }
  
  public void run()
  {
    while(listening)
    {
      WalkerThread newThread=null;
      try
      {
        newThread=new WalkerThread(myServerSocket.accept(),IDgen);
      } catch (IOException e)
      {
        e.printStackTrace();
      }
      
      System.out.println("socket connection accepted");
      newThread.start();
      IDgen++;
      threadList.add(newThread);
      for(int i=0; i<threadList.size();i++)
      {
        WalkerThread wt=threadList.get(i);
        if(!wt.isAlive())
        {
          threadList.remove(wt);
        }
      }
    }
  }
  
//  public static void main(String[] args) throws IOException
//  {
//    new WalkerServer();
//  }
}