package Reduced;

import java.util.Random;

public class CPG extends Map {

  public Random rand;
  private double[] heightField = new double[256];
  private NoiseGeneratorOctaves surfaceElevation;

  public void replaceBlockForBiomes(int chunkX, int chunkZ) {
    double noiseFactor = 0.03125D;
    this.heightField = this.surfaceElevation.func_807_a(this.heightField, (double) (chunkX * 16),
        (double) (chunkZ * 16), 0.0D, 16, 16, 1, noiseFactor * 2.0D, noiseFactor * 2.0D, noiseFactor * 2.0D);
    for (int x = 0; x < 16; ++x) {
      for (int z = 0; z < 16; ++z) {
        // int var13 = (int) (this.field_903_t[x + z * 16] / 3.0D + 3.0D +
        // this.rand.nextDouble() * 0.25D);
        rawbychunk[chunkX - minchunkX][chunkZ - minchunkZ][x][z] = this.heightField[x + z * 16] / 3.0D + 3.0D;
      }
    }
  }

  public CPG() {
    this.rand = Main.rand;
    this.surfaceElevation = new NoiseGeneratorOctaves(this.rand, 4);
    this.printMult = 10;
    genAll();
  }

  public CPG(long seed) {
    this.rand = new Random(seed);
    this.surfaceElevation = new NoiseGeneratorOctaves(this.rand, 4);
    this.printMult = 10;
    genAll();
  }

  public void genAll() {
    for (int chunkX = 0; chunkX < rawbychunk.length; chunkX++) {
      for (int chunkZ = 0; chunkZ < rawbychunk[0].length; chunkZ++) {
        replaceBlockForBiomes(chunkX + minchunkX, chunkZ + minchunkZ);
      }
    }
  }

  // public static void main(String[] args) {
  // CPG cpg = new CPG(42);
  // System.out.println(Math.floorDiv(-100, 16));
  // System.out.println(-100 >> 4 << 4);
  // for (int i = 0; i < 32; i++) {
  // System.out.println(cpg.getRaw(i, 0));
  // }
  // cpg.printAll();
  // }
}
