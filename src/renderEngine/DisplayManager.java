package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class DisplayManager
{
  
  private static DisplayMode []modes;
  private static boolean fullScreen = false;
  
  /**
   * Create a display in opengl 3.2
   */
  public static void createDisplay()
  {
    //Force opengl 3.2, with forward compatability and core profiling
    ContextAttribs attribs = new ContextAttribs(3, 2).withForwardCompatible(
        true).withProfileCore(true);
    try
    {
      Display.setTitle("LameGame");
      //create an array of possible display modes
      modes = Display.getAvailableDisplayModes();
      DisplayMode initialMode = modes[0];
      
      //set default mode to 1280x720
      for (DisplayMode mode : modes)
      {
        if (mode.getHeight() == 720 && mode.getWidth() == 1280)
        {
          initialMode = mode;
        }
      }
      Display.setDisplayMode(initialMode);
      Display.create(new PixelFormat(), attribs);
    }
    catch (LWJGLException e)
    {
      e.printStackTrace();
    }
    GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
  }

  /**
   * Change the resolution of the window
   * @param i
   */
  public static void changeResolution(int i)
  {
    //if the resolution count exceeds possible resolutions, loop around
    i%=modes.length;
    try 
    {
      Display.setDisplayMode(modes[i]);
    } 
    catch (LWJGLException e) 
    {
      e.printStackTrace();
    }
    GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());    
  }
  
  /**
   * Change the screen to switch fullscreen on/off
   */
  public static void changeFullScreen()
  {
    try 
    {
      fullScreen = !fullScreen;
      Display.setFullscreen(fullScreen);
      Display.setDisplayMode(Display.getDisplayMode());
    } 
    catch (LWJGLException e) 
    {
      e.printStackTrace();
    }
    GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());  
  }
  
  /**
   * Updates the display
   */
  public static void updateDisplay()
  {
    Display.update();
  }

  /**
   * Destroys the display
   */
  public static void closeDisplay()
  {
    Display.destroy();
  }
}
