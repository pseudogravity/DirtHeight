package Reduced;

public class PntTest {
  // from waterfall
  int xoff;
  int zoff;
  int minval;
  int maxval; // inclusive

  public PntTest(int xoff, int zoff, int dirt) {
    this.xoff = xoff;
    this.zoff = zoff;
    this.minval = dirt;
    this.maxval = dirt;
  }

  public PntTest(int xoff, int zoff, int mindirt, int maxdirt) {
    this.xoff = xoff;
    this.zoff = zoff;
    this.minval = mindirt;
    this.maxval = maxdirt;
  }

  public PntTest(int xoff, int zoff, boolean qsand) {
    this.xoff = xoff - 27 * 1; // offset between first sand and waterfall
    this.zoff = zoff;
    if (qsand) {
      this.minval = 1;
      this.maxval = 1;
    }
  }

  public boolean check(int wfx, int wfz, CPG cpg) {
    int x = wfx + xoff;
    int z = wfz + zoff;
    int testval = gettestval(x, z, cpg);
    if (testval >= minval && testval <= maxval) {
      return true;
    }
    return false;
  }

  public static int gettestval(int x, int z, CPG cpg) {
    int testval = 0;
    if (Main.sandmode) {
      if (cpg.getRaw(x, z) + LocNoise.get(x, z) * 0.2D > 0) {
        testval = 1;
      }
    } else {
      testval = (int) (cpg.getRaw(x, z) / 3.0D + 3.0D + LocNoise.get(x, z) * 0.25D);
    }
    return testval;
  }

  public static PntTest[] tests = {
      // @formatter:off
      
      // height data
//      new PntTest(-2, -14, 2), 
//      new PntTest(-2, -13, 3), 
//      new PntTest(0, -12, 2, 10), 
//      new PntTest(-1, -12, 2), 
//      new PntTest(0, -11, 2), 
//      new PntTest(0, -10, 2), 
//      new PntTest(0, -9, 2), 
//      new PntTest(0, -8, 2), 
//      new PntTest(0, -7, 2), 
//      new PntTest(0, -6, 2), 
//      new PntTest(-1, -6, 3, 10), 
//      new PntTest(0, -5, 2),
//      new PntTest(-1, -5, 2),
//      new PntTest(0, -4, 3), 
//      new PntTest(-1, -4, 2),
//      new PntTest(0, -3, 2), 
//      new PntTest(-1, -3, 2),
//      new PntTest(0, -2, 2), 
//      new PntTest(0, -1, 3), 
//      new PntTest(0, 0, 3), 
//      new PntTest(0, 1, 3), 
//      new PntTest(0, 2, 2), 
//      new PntTest(0, 3, 2, 10), 
//      new PntTest(0, 4, 2), 
//      new PntTest(0, 5, 1, 2), 
//      new PntTest(3, 7, 3), 
//      new PntTest(4, 7, 3), 
      
//      // first layer
      new PntTest(0, 0, true), 
      new PntTest(1, 1, true), 
      new PntTest(1, 2, true), 
      new PntTest(2, 3, true), 
      new PntTest(3, 4, true), 
      new PntTest(4, 5, true), 
      new PntTest(5, 6, true), 
      new PntTest(6, 6, true), 
      new PntTest(7, 7, true), 
      new PntTest(8, 8, false),
      new PntTest(9, 9, true),
      new PntTest(10, 10, true),
      new PntTest(11, 11, true),
      new PntTest(12, 12, true),
      new PntTest(13, 13, true),
      new PntTest(14, 14, true),
      new PntTest(15, 15, false),
      new PntTest(16, 16, false),
      new PntTest(17, 17, false),
      new PntTest(18, 17, false),
      new PntTest(19, 18, false),
      new PntTest(20, 18, false),
//      
//      /// second layer
//
      new PntTest(2, 0, true),
      new PntTest(2, 1, true),
      new PntTest(3, 2, true),
      new PntTest(4, 3, true),
      new PntTest(5, 4, true),
      new PntTest(6, 5, true),
      new PntTest(7, 5, true),
      new PntTest(8, 6, true),
      new PntTest(9, 6, true),
      new PntTest(10, 7, true),
      new PntTest(11, 8, true),
      new PntTest(12, 9, true),
      new PntTest(12, 10, true),
      new PntTest(13, 11, true),
      new PntTest(13, 12, true),
      new PntTest(14, 13, true),
      new PntTest(15, 13, true),
      new PntTest(16, 14, true),
      new PntTest(17, 15, true),
      new PntTest(18, 16, false),
      new PntTest(19, 17, false),
      new PntTest(20, 17, true),
      new PntTest(21, 18, false),
      
      // underwater second layer
    new PntTest(-33, 3, 1),
    new PntTest(-31, 4, 1),
    new PntTest(-30, 5, 1),
    new PntTest(-29, 6, 1),
    
    new PntTest(-21, 18, 1),
    new PntTest(-20, 19, 0),
      
      // underwater third layer
      
    new PntTest(-37, 6, 1),
    new PntTest(-36, 7, 0),
    new PntTest(-35, 8, 1),
    new PntTest(-34, 9, 1),
    new PntTest(-33, 10, 1),
    
    new PntTest(-32, 14, 1),
    new PntTest(-32, 15, 0),
    new PntTest(-31, 15, 0),
    
    new PntTest(-29, 18, 1),
    new PntTest(-29, 19, 0),
    new PntTest(-29, 20, 1),
    
    new PntTest(-30, 22, 1),
    new PntTest(-30, 23, 0),
    new PntTest(-29, 24, 0),
    new PntTest(-29, 25, 1),
      
      // @formatter:on
  };

  public static boolean checkAll(int wfx, int wfz, CPG cpg) {
    for (PntTest test : tests) {
      if (!test.check(wfx, wfz, cpg)) {
        return false;
      }
    }
    return true;
  }

  public static int minxoff() {
    int min = Integer.MAX_VALUE;
    for (PntTest test : tests) {
      if (test.xoff < min) {
        min = test.xoff;
      }
    }
    return min;
  }

  public static int minzoff() {
    int min = Integer.MAX_VALUE;
    for (PntTest test : tests) {
      if (test.zoff < min) {
        min = test.zoff;
      }
    }
    return min;
  }

  public static int maxxoff() {
    int max = Integer.MIN_VALUE;
    for (PntTest test : tests) {
      if (test.xoff > max) {
        max = test.xoff;
      }
    }
    return max;
  }

  public static int maxzoff() {
    int max = Integer.MIN_VALUE;
    for (PntTest test : tests) {
      if (test.zoff > max) {
        max = test.zoff;
      }
    }
    return max;
  }
}
