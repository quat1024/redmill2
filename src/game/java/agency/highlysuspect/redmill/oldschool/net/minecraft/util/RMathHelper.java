package agency.highlysuspect.redmill.oldschool.net.minecraft.util;

import net.minecraft.util.Mth;

import java.util.Random;

public class RMathHelper {
	public static float sin(float in) {
		return Mth.sin(in);
	}
	
	public static float cos(float in) {
		return Mth.cos(in);
	}
	
	public static float sqrt_float(float in) {
		return Mth.sqrt(in);
	}
	
	public static float sqrt_double(double in) {
		//Regular java math class
		return (float) Math.sqrt(in);
	}
	
	public static int floor_float(float in) {
		return Mth.floor(in);
	}
	
	public static int truncateDoubleToInt(double in) {
		//Dont think this one exists in modern mc
		return ((int) in + 1024) - 1024;
	}
	
	public static int floor_double(double in) {
		return Mth.floor(in);
	}
	
	public static long floor_double_long(double in) {
		return Mth.lfloor(in);
	}
	
	public static float abs(float in) {
		return Mth.abs(in);
	}
	
	public static int abs_int(int in) {
		return Mth.abs(in);
	}
	
	public static int ceiling_float_int(float in) {
		return Mth.ceil(in);
	}
	
	public static int ceiling_double_int(double in) {
		return Mth.ceil(in);
	}
	
	public static int clamp_int(int in, int min, int max) {
		return Mth.clamp(in, min, max);
	}
	
	public static float clamp_float(int in, float min, float max) {
		return Mth.clamp(in, min, max);
	}
	
	public static double abs_max(double x, double y) {
		return Mth.absMax(x, y);
	}
	
	//TODO not sure what this does
	public static int bucketInt(int i, int bucket) {
		return i < 0 ? -((-i - 1) / bucket) - 1 : i / bucket;
	}
	
	public static boolean stringNullOrLengthZero(String in) {
		return in == null || in.isEmpty();
	}
	
	public static int getRandomIntegerInRange(Random pRandom, int pMinimum, int pMaximum) {
		//uses RandomSource
		//Mth.randomBetweenInclusive(rand, low, high);
		
		return pMinimum >= pMaximum ? pMinimum : pRandom.nextInt(pMaximum - pMinimum + 1) + pMinimum;
	}
	
	public static double nextDouble(Random pRandom, double pMinimum, double pMaximum) {
		return pMinimum >= pMaximum ? pMinimum : pRandom.nextDouble() * (pMaximum - pMinimum) + pMinimum;
	}
	
	public static double average(long[] var0) {
		long sum = 0;
		for (long l : var0) {
			sum += l;
		}
		return (double) sum / (double) var0.length;
	}
	
	public static float wrapAngleTo180_float(float in) {
		return Mth.wrapDegrees(in);
	}
	
	public static double wrapAngleTo180_double(double in) {
		return Mth.wrapDegrees(in);
	}
	
	public static int parseIntWithDefault(String p, int def) {
		try {
			return Integer.parseInt(p);
		} catch (Throwable e) {
			return def;
		}
	}
	
	public static int parseIntWithDefaultAndMax(String p, int def, int max) {
		try {
			int i = Integer.parseInt(p);
			return Math.min(i, max);
		} catch (Throwable e) {
			return def;
		}
	}
	
	public static double parseDoubleWithDefault(String p, double def) {
		try {
			return Double.parseDouble(p);
		} catch (Throwable e) {
			return def;
		}
	}
	
	public static double func_82713_a(String p, double def, double max) {
		try {
			double d = Double.parseDouble(p);
			return Math.min(d, max);
		} catch (Throwable e) {
			return def;
		}
	}
}
