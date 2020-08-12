package Reduced;

public abstract class Map {
  public static int minchunkX = (Main.minwfx + PntTest.minxoff()) >> 4;
  public static int maxchunkX = (Main.maxwfx + PntTest.maxxoff()) >> 4;
  public static int minchunkZ = (Main.minwfz + PntTest.minzoff()) >> 4; // inclusive
  public static int maxchunkZ = (Main.maxwfz + PntTest.maxzoff()) >> 4; // inclusive
//  public static int minchunkX = (Main.minwfx - 0) >> 4;
//  public static int maxchunkX = (Main.maxwfx + 3) >> 4;
//  public static int minchunkZ = (Main.minwfz - 0) >> 4; // inclusive
//  public static int maxchunkZ = (Main.maxwfz + 3) >> 4; // inclusive

  public double[][][][] rawbychunk = new double[maxchunkX - minchunkX + 1][maxchunkZ - minchunkZ + 1][16][16];

  public double getRaw(int x, int z) {
    int chunkX = worldToChunk(x);
    int Xrem = worldToRem(x);
    int chunkZ = worldToChunk(z);
    int Zrem = worldToRem(z);
    return rawbychunk[chunkX - minchunkX][chunkZ - minchunkZ][Xrem][Zrem];
  }

  public int minX() {
    return minchunkX * 16;
  }

  public int maxX() { // inclusive
    return (minchunkX + rawbychunk.length) * 16 - 1;
  }

  public int minZ() {
    return minchunkZ * 16;
  }

  public int maxZ() { // inclusive
    return (minchunkZ + rawbychunk[0].length) * 16 - 1;
  }

  public int printMult;

  public void printAll() {
    System.out.println();
    for (int x = maxX(); x >= minX(); x--) {
      System.out.printf("%3d\t", x);
      for (int z = minZ(); z <= maxZ(); z++) {
        System.out.printf("%3.0f\t", getRaw(x, z) * printMult);
      }
      System.out.println();
    }
    System.out.println();
    System.out.print("x/z\t");
    for (int z = minZ(); z <= maxZ(); z++) {
      System.out.printf("%3d\t", z);
    }
    System.out.println();
  }

  public static int worldToChunk(int coord) {
    return coord >> 4;
  }

  public static int worldToRem(int coord) {
    return coord & 15;
  }
}
