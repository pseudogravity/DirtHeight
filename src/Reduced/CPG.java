package Reduced;

import java.util.Random;

public class CPG extends Map {

  public static int maxrelchunkX = ((PntTest.maxxoff() - PntTest.minxoff() - 1) >> 4) + 1; // inclusive
  public static int maxrelchunkZ = ((PntTest.maxzoff() - PntTest.minzoff() - 1) >> 4) + 1; // inclusive

  public Random rand;
  private double[] chunkgrid = new double[256];
  private NoiseGeneratorOctaves ngo;

  public void replaceBlockForBiomes(int chunkX, int chunkZ) {
    double noiseFactor = 0.03125D;
    if (Main.sandmode) {
      this.chunkgrid = this.ngo.func_807_a(this.chunkgrid, (double) (chunkX * 16), (double) (chunkZ * 16), 0.0D, 16, 16,
          1, noiseFactor, noiseFactor, 1.0D);
    } else {
      this.chunkgrid = this.ngo.func_807_a(this.chunkgrid, (double) (chunkX * 16), (double) (chunkZ * 16), 0.0D, 16, 16,
          1, noiseFactor * 2.0D, noiseFactor * 2.0D, noiseFactor * 2.0D);
    }
    for (int x = 0; x < 16; ++x) {
      for (int z = 0; z < 16; ++z) {
        // int var13 = (int) (this.field_903_t[x + z * 16] / 3.0D + 3.0D +
        // this.rand.nextDouble() * 0.25D);
        rawbychunk[chunkX - minchunkX][chunkZ - minchunkZ][x][z] = this.chunkgrid[x + z * 16];
      }
    }
  }

  public CPG() {
    super(0, maxrelchunkX, 0, maxrelchunkZ);
    this.rand = Main.rand;
    this.ngo = new NoiseGeneratorOctaves(this.rand, 4);
    this.printMult = 10;
    genAll();
  }

  public CPG(long seed) {
    super(0, maxrelchunkX, 0, maxrelchunkZ);
    this.rand = new Random(seed);
    this.ngo = new NoiseGeneratorOctaves(this.rand, 4);
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
