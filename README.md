# DirtHeight
finding probable X/Z coords for pack.png

- Main: generates samples, applies tests, keeps tally, writes results
- PntTest: list of coords, relative to waterfall, to test
- Map: an abstract class to hold 2D arrays of doubles in a chunk coord format
- LocNoise: creates a static Map for the location-dependent noise
- CPG: for creating Maps representing the perlin noise contribution
- NoiseGeneratorOctaves and NoiseGeneratorPerlin: from minecraft for use by CPG
