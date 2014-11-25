package server;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.AllPermission;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.ImageIcon;

public class WalkerClient extends Thread
{
  public boolean printlocation=false;
  public static String hostName="Glados";
  public static int socketVal=4444;
  
  Socket mySocket=null;
  PrintWriter out=null;
  BufferedReader in=null;
  
  private String spoofString="Play,0,0,4,0,90,0,0.3";//TODO delete this after testing
  private volatile String inputFromServer="Play,0,0,4,0,0,0,0.3";
  private String outputToServer;
  
  private LinkedList<Point> playerLocList=new LinkedList<>();

  public static void main(String[] args) throws IOException
  {
    new WalkerClient(args);
  }
  
  public WalkerClient(String[] args) throws IOException
  {    
    if(args.length>0)
    {
      hostName=args[0];
    }
    
    try
    {
      mySocket=new Socket(hostName, socketVal);
      out=new PrintWriter(mySocket.getOutputStream());
      in=new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
    } catch (UnknownHostException e)
    {
      System.err.println("Don't know about host: "+hostName);
      System.exit(1);
    } catch (IOException e)
    {
      System.err.println("Couldn't get I/O for the connection to: "+hostName);
      System.exit(1);
    }
    
    //initialize ServerPackage
  
    this.start();
  }
  
  public void run()
  {
	  System.out.println("clientUpdate1:"+inputFromServer);
//    try
//    {
	  //TODO delete this after
    	inputFromServer=spoofString;
		try {
			while ((inputFromServer = in.readLine()) != null)
			{
				// System.out.println(inputFromServer);

			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
      

    
    try
    {
      out.close();
      in.close();
      mySocket.close();
    } catch (IOException e)
    {
      e.printStackTrace();
    }
    System.out.println("clientUpdate2:"+inputFromServer);
  }
  
  
  
  public synchronized String updateClientGameState(String updateString)
  {
  //System.out.println("out"+updateString);
    outputToServer=updateString;
    out.println(outputToServer);
   
    //System.out.println("in"+inputFromServer);
    return inputFromServer;

  }
}