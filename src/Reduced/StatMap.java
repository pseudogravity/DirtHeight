package Reduced;

public class StatMap extends Map {

  public StatMap(Map src) {
    super(src.minchunkX, src.maxchunkX, src.minchunkZ, src.maxchunkZ);
    this.printMult = 100;
  }

  public void addcpg(CPG cpg) {
//    System.out.println(minchunkX);
//    System.out.println(cpg.minchunkX);
//    System.out.println(LocNoise.locnoise.minchunkX);
//    System.out.println(minchunkZ);
//    System.out.println(cpg.minchunkZ);
//    System.out.println(LocNoise.locnoise.minchunkZ);
//    System.out.println(maxchunkX);
//    System.out.println(cpg.maxchunkX);
//    System.out.println(LocNoise.locnoise.maxchunkX);
//    System.out.println(maxchunkZ);
//    System.out.println(cpg.maxchunkZ);
//    System.out.println(LocNoise.locnoise.maxchunkZ);
    for (int i = 0; i < rawbychunk.length; i++) {
      for (int j = 0; j < rawbychunk[i].length; j++) {
        for (int k = 0; k < rawbychunk[i][j].length; k++) {
          for (int l = 0; l < rawbychunk[i][j][k].length; l++) {
            int x = (i + minchunkX) * 16 + k;
            int z = (j + minchunkZ) * 16 + l;
            this.rawbychunk[i][j][k][l] += PntTest.gettestval(x, z, cpg);
          }
        }
      }
    }
  }

  public void div(double denom) {
    this.printMult = 100 / denom;
//    for (int i = 0; i < rawbychunk.length; i++) {
//      for (int j = 0; j < rawbychunk[i].length; j++) {
//        for (int k = 0; k < rawbychunk[i][j].length; k++) {
//          for (int l = 0; l < rawbychunk[i][j][k].length; l++) {
//            this.rawbychunk[i][j][k][l] /= denom;
//          }
//        }
//      }
//    }
  }

}