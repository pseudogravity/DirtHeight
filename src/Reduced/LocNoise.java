package Reduced;

import java.util.Random;

public class LocNoise extends Map {

  public static int minabschunkX = ((Main.minwfx + PntTest.minxoff()) >> 4);
  public static int maxabschunkX = ((Main.maxwfx + PntTest.maxxoff()) >> 4) + 1; // inclusive+safety
  public static int minabschunkZ = ((Main.minwfz + PntTest.minzoff()) >> 4);
  public static int maxabschunkZ = ((Main.maxwfz + PntTest.maxzoff()) >> 4) + 1; // inclusive+safety

  static final LocNoise locnoise = new LocNoise();

  static double get(int x, int z) {
    return locnoise.getRaw(x, z);
  }

  // computes the nextDouble component of dirt height
  // courtesy of EarthComputer
  static double getNextDouble(int x, int z) {
    Random rand = new Random((x >> 4) * 341873128712L + (z >> 4) * 132897987541L);
    for (int dx = 0; dx < 16; dx++) {
      for (int dz = 0; dz < 16; dz++) {
        if (dx == (x & 15) && dz == (z & 15)) {
          if (Main.sandmode) {
            return rand.nextDouble();
          }
          rand.nextDouble();
          rand.nextDouble();
          return rand.nextDouble();
        }
        for (int i = 0; i < 67; i++) {
          rand.nextDouble();
        }
      }
    }
    throw new AssertionError();
  }

  public LocNoise() {
    super(minabschunkX, maxabschunkX, minabschunkZ, maxabschunkZ);
    System.out.println("sandmode: " + Main.sandmode);
    genAll();
    this.printMult = 100;
  }

  public void genAll() {
    // terribly inefficient but only run once
    for (int chunkX = 0; chunkX < rawbychunk.length; chunkX++) {
      for (int chunkZ = 0; chunkZ < rawbychunk[0].length; chunkZ++) {
        genChunk(chunkX + minchunkX, chunkZ + minchunkZ);
      }
    }
  }

  public void genChunk(int chunkX, int chunkZ) {
    for (int x = 0; x < 16; ++x) {
      for (int z = 0; z < 16; ++z) {
        rawbychunk[chunkX - minchunkX][chunkZ - minchunkZ][x][z] = getNextDouble((chunkX << 4) + x, (chunkZ << 4) + z);
      }
    }
  }
}
