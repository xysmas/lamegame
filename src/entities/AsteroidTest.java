package entities;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AsteroidTest
{
  Asteroid ass;

  @Before public void setUp() throws Exception
  {
    ass = new Asteroid();

  }

  @After public void tearDown() throws Exception
  {

  }

  @Test public void testUponDeath() throws Exception
  {
    ass.damageObject(100);
    assertTrue((ass.getHitPoints()) > 0 );
    ass.damageObject(100);
    assertTrue((ass.getHitPoints()) > 0 );
    ass.damageObject(100);
    assertTrue((ass.getHitPoints()) > 0 );
    ass.damageObject(100);
    assertTrue((ass.getHitPoints()) > 0 );
    ass.damageObject(100);
    assertFalse((ass.getHitPoints()) > 0);
  }

  @Test public void testAsteroidSize() throws Exception
  {
    System.out.println("Asteroid Size: " + ass.getBox().getMax());
  }

  @Test public void testToString() throws Exception
  {
    System.out.println(ass.toString());
  }
}