package server;

import java.awt.Point;
import java.awt.image.ReplicateScaleFilter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.instrument.Instrumentation;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class WalkerThread extends Thread
{ 
  public boolean printlocation=true;
  public boolean printclients=false;
  public String inputFromClient=null;
  public String outputToClient=null;
  public boolean inputReceived=false;
  
  private Socket myClientSocket = null;
  PrintWriter out=null;
  BufferedReader in=null;

  public WalkerThread(Socket mySocket, int ID)
  {
    super("WalkerThread");
    this.myClientSocket = mySocket;
  }

  public void run()
  {
    try
    {
      out=new PrintWriter(myClientSocket.getOutputStream(),true);
      in=new BufferedReader(new InputStreamReader(myClientSocket.getInputStream()));
      int loop = 0;
      
      //input from client will be an action (like move left), output will be a list of objects to render
      while ((inputFromClient = in.readLine()) != null)
      {
        //input from client shall only receive actions (move, fire) from the client, everything else will happen on the server (not graphics)
        inputReceived=false;
        while(!inputReceived)
        {
          //wait for MainLoopServer to fetch input
        }
        
        outputToClient=null;//first test, don't send anything
        if(outputToClient!=null)out.println(outputToClient);
        loop++;
      }
    } 
    catch (java.net.SocketException e)
    {
      try
      {
        myClientSocket.close();
        return;
      } catch (IOException e1)
      {
        e1.printStackTrace();
      }
    }catch (IOException e)
    {
      e.printStackTrace();
    }
    catch (NumberFormatException e)
    {
      e.printStackTrace();
    }
  }
  
  public String updateServerGameState(String updateString)//TODO maybe call this a getter
  {
    outputToClient=updateString;
    out.println(outputToClient);
    return inputFromClient;
  }
  
  public String getClientInput()//called at the beginning to wait for first client input
  {
    while(inputFromClient==null)
    {
      //do nothing
    }
    
    return inputFromClient;
  }
}
