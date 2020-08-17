package Reduced;

import java.io.PrintStream;

public abstract class Map {
  public int minchunkX;
  public int maxchunkX;
  public int minchunkZ;
  public int maxchunkZ;

  public double[][][][] rawbychunk;

  public Map(int minchunkX, int maxchunkX, int minchunkZ, int maxchunkZ) {
    this.minchunkX = minchunkX;
    this.maxchunkX = maxchunkX;
    this.minchunkZ = minchunkZ;
    this.maxchunkZ = maxchunkZ;
    rawbychunk = new double[maxchunkX - minchunkX + 1][maxchunkZ - minchunkZ + 1][16][16];
  }

  public double getRaw(int x, int z) {
    int chunkX = worldToChunk(x);
    int Xrem = worldToRem(x);
    int chunkZ = worldToChunk(z);
    int Zrem = worldToRem(z);
    return rawbychunk[chunkX - minchunkX][chunkZ - minchunkZ][Xrem][Zrem];
  }

  public void setMinChunkCoords(int x, int z) {
    int chunkshiftX = (x >> 4) - minchunkX;
    minchunkX += chunkshiftX;
    maxchunkX += chunkshiftX;
    int chunkshiftZ = (z >> 4) - minchunkZ;
    minchunkZ += chunkshiftZ;
    maxchunkZ += chunkshiftZ;
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

  public double printMult;

  public void printAll(PrintStream out) {
    printAll(out, 0, 0);
  }

  public void printAll(PrintStream out, int xlabelshift, int zlabelshift) {
    out.println();
    for (int x = maxX(); x >= minX(); x--) {
      out.printf("%3d    ", x + xlabelshift);
      for (int z = minZ(); z <= maxZ(); z++) {
        out.printf("%3.0f ", getRaw(x, z) * printMult);
      }
      out.println();
    }
    out.println();
    out.print("x/z    ");
    for (int z = minZ(); z <= maxZ(); z++) {
      out.printf("%3d ", z + zlabelshift);
    }
    out.println();
  }

  public static int worldToChunk(int coord) {
    return coord >> 4;
  }

  public static int worldToRem(int coord) {
    return coord & 15;
  }
}
