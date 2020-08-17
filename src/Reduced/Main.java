package Reduced;

import java.io.PrintWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Main {
  // like 100000 for decent results in like 2 minutes with 8 data points
  // like 1000000 for an undersampled 30-60 min run with almost all data points
  // using all data points, total sample size across hosts should be > 100000000
  public static final boolean sandmode = true;
  public static final int samplesize = 1000000 * 100;
  public static final int seed = 0;

  public static final Random rand = new Random(seed);
  public static final int minwfx = -600;// 116;// -235;
  public static final int maxwfx = 150;// 116;// 135; // inclusive
  public static final int minwfz = -32;// -31;// -32;
  public static final int maxwfz = -29;// -31;// -25; // inclusive

  public static final int specialwfx = 116;// for saving predictions of surrounding area
  public static final int specialwfz = -31;//

  public static final String filename = "out_mega_sand.txt";
  public static final String statsfilename = "out_stats_mega_sand.txt";

  public static void main(String[] args) {

    // LocNoise.locnoise.printAll();

    // new CPG().printAll();

    System.out.println("# tests: " + PntTest.tests.length);

    int[][] counts = new int[maxwfx - minwfx + 1][maxwfz - minwfz + 1];
    int minxoff = PntTest.minxoff();
    int minzoff = PntTest.minzoff();

    CPG cpg = new CPG();
    cpg.setMinChunkCoords(specialwfx + minxoff, specialwfz + minzoff);
    StatMap sm = new StatMap(cpg);

    for (int sample = 0; sample < samplesize; sample++) {
      if ((sample & 16383) == 0) {
        save(sample, counts);
        saveStats(sm, sample, counts[specialwfx - minwfx][specialwfz - minwfz]);
        System.out.println(sample);
      }
      cpg = new CPG();
      for (int wfx = minwfx; wfx <= maxwfx; wfx++) {
        for (int wfz = minwfz; wfz <= maxwfz; wfz++) {
          cpg.setMinChunkCoords(wfx + minxoff, wfz + minzoff);
          if (PntTest.checkAll(wfx, wfz, cpg)) {
            counts[wfx - minwfx][wfz - minwfz]++;
            if (wfx == specialwfx && wfz == specialwfz) {
              sm.addcpg(cpg);
            }
          }
        }
      }
    }
    save(samplesize, counts);
    saveStats(sm, samplesize, counts[specialwfx - minwfx][specialwfz - minwfz]);
  }

  static void saveStats(StatMap sm, int samplecount, int matches) {
    try {
      PrintStream out = new PrintStream(statsfilename);
      out.println(seed);
      out.println(samplecount);
      out.println(matches);
      sm.div(matches);
      sm.printAll(out, -specialwfx, -specialwfz);
      out.flush();
      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  static void save(int samplecount, int[][] counts) {
    try {
      PrintWriter out = new PrintWriter(filename);
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
