package Reduced;

public abstract class PntTest {
  // from waterfall
  int xoff;
  int zoff;

  public static class HeightTest extends PntTest {
    int mindirt;
    int maxdirt; // inclusive

    public HeightTest(int xoff, int zoff, int dirt) {
      super(xoff, zoff);
      this.mindirt = dirt;
      this.maxdirt = dirt;
    }

    public HeightTest(int xoff, int zoff, int mindirt, int maxdirt) {
      super(xoff, zoff);
      this.mindirt = mindirt;
      this.maxdirt = maxdirt;
    }

    public boolean logic(int x, int z) {
      int dirt = (int) (CPG.heightcpg.getRaw(x, z) / 3.0D + 3.0D + LocNoise.getHeight(x, z) * 0.25D);
      if (dirt >= mindirt && dirt <= maxdirt) {
        return true;
      }
      return false;
    }

  }

  public static class SandTest extends PntTest {
    boolean qsand;

    public SandTest(int xoff, int zoff, boolean qsand) {
      super(xoff - 27 * 0, zoff); // offset between first sand and waterfall
      this.qsand = qsand;
    }

    @Override
    public boolean logic(int x, int z) {
      return (CPG.sandcpg.getRaw(x, z) + LocNoise.getSand(x, z) * 0.2D > 0.0D) == qsand;
    }

  }

  public PntTest(int xoff, int zoff) {
    this.xoff = xoff;
    this.zoff = zoff;
  }

  public boolean check(int wfx, int wfz) {
    int x = wfx + xoff;
    int z = wfz + zoff;
    return logic(x, z);
  }

  public abstract boolean logic(int x, int z);

  public static HeightTest[] heighttests = {
      // @formatter:off
      // should be about all the data points
      new HeightTest(-2, -14, 2), 
      new HeightTest(-2, -13, 3), 
      //new PntTest(-2, -12, 2), //xray
      //new PntTest(0, -12, 2, 10), 
      new HeightTest(-1, -12, 2), 
      new HeightTest(0, -11, 2), 
      new HeightTest(0, -10, 2), 
      new HeightTest(0, -9, 2), 
      new HeightTest(0, -8, 2), 
      new HeightTest(0, -7, 2), 
      new HeightTest(0, -6, 2), 
      new HeightTest(-1, -6, 2, 3), // dirt vein?
      new HeightTest(0, -5, 2),
      new HeightTest(-1, -5, 2),
      new HeightTest(0, -4, 3), // the anomaly
      new HeightTest(-1, -4, 2),
      new HeightTest(0, -3, 2), 
      new HeightTest(-1, -3, 2),
      new HeightTest(0, -2, 2), 
      new HeightTest(0, -1, 2,3), 
      new HeightTest(0, 0, 2,3), 
      new HeightTest(0, 1, 2,3), 
      new HeightTest(0, 2, 2), 
      new HeightTest(0, 3, 2),  //new PntTest(0, 3, 2, 10), xray
      new HeightTest(0, 4, 2), 
      new HeightTest(0, 5, 2), //new PntTest(0, 5, 1, 2), xray
      new HeightTest(3, 7, 3), 
      new HeightTest(4, 7, 3), 

      // @formatter:on
  };

  public static SandTest[] sandtests = {
      // @formatter:off
      // should be about all the data points
      
      // first layer
      new SandTest(0, 0, true), 
      new SandTest(1, 1, true), 
      new SandTest(1, 2, true), 
      new SandTest(2, 3, true), 
      new SandTest(3, 4, true), 
      new SandTest(4, 5, true), 
      new SandTest(5, 6, true), 
      new SandTest(6, 6, true), 
      new SandTest(7, 7, true), 
      new SandTest(8, 8, false),
      new SandTest(9, 9, true),
      new SandTest(10, 10, true),
      new SandTest(11, 11, true),
      new SandTest(12, 12, true),
      new SandTest(13, 13, true),
      new SandTest(14, 14, true),
      new SandTest(15, 15, false),
      new SandTest(16, 16, false),
      new SandTest(17, 17, false),
      new SandTest(18, 17, false),
      new SandTest(19, 18, false),
      new SandTest(20, 18, false),
      
      ///// second layer

      new SandTest(2, 0, true),
      new SandTest(2, 1, true),
      new SandTest(3, 2, true),
      new SandTest(4, 3, true),
      new SandTest(5, 4, true),
      new SandTest(6, 5, true),
      new SandTest(7, 5, true),
      new SandTest(8, 6, true),
      new SandTest(9, 6, true),
      new SandTest(10, 7, true),
      new SandTest(11, 8, true),
      new SandTest(12, 9, true),
      new SandTest(12, 10, true),
      new SandTest(13, 11, true),
      new SandTest(13, 12, true),
      new SandTest(14, 13, true),
      new SandTest(15, 13, true),
      new SandTest(16, 14, true),
      new SandTest(17, 15, true),
      new SandTest(18, 16, false),
      new SandTest(19, 17, false),
      new SandTest(20, 17, true),
      new SandTest(21, 18, false),
      
      //// underwater

      new SandTest(-35+25, -93+99, true),
      new SandTest(-34+25, -92+99, false),
      new SandTest(-33+25, -91+99, true),
      
      new SandTest(-30+25, -85+99, true),
      new SandTest(-30+25, -84+99, false),
      
      new SandTest(-27+25, -80+99, false),
      new SandTest(-26+25, -79+99, true),

      new SandTest(-28+25, -77+99, true),
      new SandTest(-28+25, -76+99, false),
      
      // @formatter:on
  };

  public static boolean checkAllHeights(int wfx, int wfz) {
    for (PntTest test : heighttests) {
      if (!test.check(wfx, wfz)) {
        return false;
      }
    }
    return true;
  }

  public static boolean checkAllSand(int wfx, int wfz) {
    for (PntTest test : sandtests) {
      if (!test.check(wfx, wfz)) {
        return false;
      }
    }
    return true;
  }
}
