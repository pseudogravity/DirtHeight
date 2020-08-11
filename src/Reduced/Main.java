package Reduced;

import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Main {
  // like 100000 for decent results in like 2 minutes with 8 data points
  // like 1000000 for an undersampled 30-60 min run with almost all data points
  // using all data points, total sample size across hosts should be > 100000000
  public static final int samplesize = 100000 * 100;
  public static final int seed = 0;

  public static final Random rand = new Random(seed);
  public static final int minwfx = -300;// -1000; //-235
  public static final int maxwfx = 150;// 150; //135 // inclusive
  public static final int minwfz = -31;
  public static final int maxwfz = -28; // inclusive

  public static void main(String[] args) {

    LocNoise.height.printAll();

    // new CPG(new Random().nextInt()).printAll();
    // System.exit(0);

    System.out.println("# tests: " + PntTest.heighttests.length);

    int[][] counts = new int[maxwfx - minwfx + 1][maxwfz - minwfz + 1];

    for (int sample = 0; sample < samplesize; sample++) {
      if ((sample & 16383) == 0) {
        save(sample, counts);
        System.out.println(sample);
      }
      CPG.genNew();
      for (int wfx = minwfx; wfx <= maxwfx; wfx++) {
        for (int wfz = minwfz; wfz <= maxwfz; wfz++) {
          if (PntTest.checkAllSand(wfx, wfz)) {
            counts[wfx - minwfx][wfz - minwfz]++;
          }
        }
      }
    }
    // for (int wfx = minwfx; wfx <= maxwfx; wfx++) {
    // for (int wfz = minwfz; wfz <= maxwfz; wfz++) {
    // if (counts[wfx - minwfx][wfz - minwfz] > 0) {
    // System.out.println(wfx + " " + wfz + " " + counts[wfx - minwfx][wfz -
    // minwfz]);
    // }
    // }
    // }
    save(samplesize, counts);
  }

  static void save(int samplecount, int[][] counts) {
    try {
      PrintWriter out = new PrintWriter("out.txt");
      out.println(seed);
      out.println(samplecount);
      ArrayList<Line> lines = new ArrayList<Line>();
      for (int wfz = minwfz; wfz <= maxwfz; wfz++) {
        for (int wfx = minwfx; wfx <= maxwfx; wfx++) {
          if (counts[wfx - minwfx][wfz - minwfz] > 0) {
            lines.add(new Line(wfx, wfz, counts[wfx - minwfx][wfz - minwfz]));
          }
        }
      }
      Collections.sort(lines);
      for (Line line : lines) {
        out.println(line.x + " " + line.z + " " + line.c);
      }
      out.flush();
      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  static class Line implements Comparable<Line> {
    int x;
    int z;
    int c;

    public Line(int x, int z, int c) {
      super();
      this.x = x;
      this.z = z;
      this.c = c;
    }

    @Override
    public int compareTo(Line o) {
      if (z != o.z) {
        return z - o.z;
      }
      if (c != o.c) {
        return o.c - c;
      }
      return x - o.x;
    }

  }
}
