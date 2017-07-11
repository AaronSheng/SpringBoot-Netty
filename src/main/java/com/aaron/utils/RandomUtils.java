package com.aaron.utils;

import java.util.HashSet;
import java.util.Set;

public class RandomUtils {
    public static int randInt(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }

    public static Set<Integer> randInts(int start, int end, int n) {
        if ((end - start + 1) < n) {
            return null;
        }

        Set<Integer> set = new HashSet<Integer>();
        while (set.size() < n) {
            set.add(randInt(start, end));
        }
        return set;
    }

    public static float randFloat(float start, float end) {
        return start + (float)Math.round(Math.random() * (end - start));
    }

    public static Set<Float> randFloats(float start, float end, int n) {
        if ((end - start + 1) < n) {
            return null;
        }

        Set<Float> set = new HashSet<Float>();
        while (set.size() < n) {
            set.add(randFloat(start, end));
        }
        return set;
    }

    public static double randDouble(double start, double end) {
        return start + (double)Math.round(Math.random() * (end - start));
    }

    public static Set<Double> randDoubles(double start, double end, int n) {
        if ((end - start + 1) < n) {
            return null;
        }

        Set<Double> set = new HashSet<Double>();
        while (set.size() < n) {
            set.add(randDouble(start, end));
        }
        return set;
    }
}
