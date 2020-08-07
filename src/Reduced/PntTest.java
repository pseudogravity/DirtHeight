package Reduced;


public class PntTest {
  // from waterfall
  int xoff;
  int zoff;
  int mindirt;
  int maxdirt; // inclusive

  public PntTest(int xoff, int zoff, int dirt) {
    this.xoff = xoff;
    this.zoff = zoff;
    this.mindirt = dirt;
    this.maxdirt = dirt;
  }

  public PntTest(int xoff, int zoff, int mindirt, int maxdirt) {
    this.xoff = xoff;
    this.zoff = zoff;
    this.mindirt = mindirt;
    this.maxdirt = maxdirt;
  }

  public boolean check(int wfx, int wfz, CPG cpg) {
    int x = wfx + xoff;
    int z = wfz + zoff;
    int dirt = (int) (cpg.getRaw(x, z) + LocNoise.get(x, z));
    if (dirt >= mindirt && dirt <= maxdirt) {
      return true;
    }
    return false;
  }

  public static PntTest[] tests = {
      // @formatter:off
      // I still have more datapoints to add
      //new PntTest(0, -12, 2, 10), 
      //new PntTest(0, -11, 2), 
      //new PntTest(0, -10, 2), 
      //new PntTest(0, -9, 2), 
      //new PntTest(0, -8, 2), 
      //new PntTest(0, -7, 2), 
      //new PntTest(0, -6, 2), 
      new PntTest(0, -5, 2), 
      new PntTest(0, -4, 3), 
      new PntTest(0, -3, 2), 
      new PntTest(0, -2, 2), 
      new PntTest(0, -1, 3), 
      new PntTest(0, 0, 3), 
      new PntTest(0, 1, 3), 
      new PntTest(0, 2, 2), 
      //new PntTest(0, 3, 2, 10), 
      //new PntTest(0, 4, 2), 
      //new PntTest(0, 5, 2), 

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
}
