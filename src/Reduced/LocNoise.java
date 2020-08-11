package Reduced;

import java.util.Random;

public class LocNoise extends Map {

  static final LocNoise height = new LocNoise("height");
  static final LocNoise sand = new LocNoise("sand");
  static final LocNoise gravel = new LocNoise("gravel");

  static double getHeight(int x, int z) {
    return height.getRaw(x, z);
  }

  static double getSand(int x, int z) {
    return sand.getRaw(x, z);
  }

  static double getGravel(int x, int z) {
    return gravel.getRaw(x, z);
  }

  // computes the nextDouble component of dirt height
  // courtesy of EarthComputer
  static double getNextDouble(int x, int z, String type) {
    Random rand = new Random((x >> 4) * 341873128712L + (z >> 4) * 132897987541L);
    for (int dx = 0; dx < 16; dx++) {
      for (int dz = 0; dz < 16; dz++) {
        if (dx == (x & 15) && dz == (z & 15)) {
          double v1 = rand.nextDouble();
          double v2 = rand.nextDouble();
          double v3 = rand.nextDouble();
          if (type.equals("sand")) {
            return v1;
          } else if (type.equals("gravel")) {
            return v2;
          } else if (type.equals("height")) {
            return v3;
          } else {
            return -1;
          }
        }
        rand.nextDouble();
        rand.nextDouble();
        rand.nextDouble();
        for (int k1 = 127; k1 >= 0; k1--) {
          rand.nextInt(5);
        }
      }
    }
    throw new AssertionError();
  }

  String type;

  public LocNoise(String type) {
    this.type = type;
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
        rawbychunk[chunkX - minchunkX][chunkZ - minchunkZ][x][z] = getNextDouble((chunkX << 4) + x, (chunkZ << 4) + z,
            type);
        // System.out.println(rawbychunk[chunkX - minchunkX][chunkZ - minchunkZ][x][z]);
        // System.out.println(getNextDouble((chunkX << 4) + x, (chunkZ << 4) + z) + " "
        // + ((chunkX << 4) + x) + " " + ((chunkZ << 4) + z));
      }
    }
  }

  // public static void main(String[] args) {
  // LocNoise ln = new LocNoise();
  // ln.printAll();
  // System.out.println(getNextDouble(0, 0));
  // System.out.println(getNextDouble(0, 1));
  // }
}
