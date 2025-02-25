package net.feliscape.hops_and_barrels.util;

public class MathUtil {
    public static boolean betweenExclusive(double value, double min, double max){
        if (max <= min)
            System.out.println("max is less than or equal to min, will always return false");

        return value > min && value < max;
    }
    public static boolean betweenInclusive(double value, double min, double max){
        if (max < min)
            System.out.println("max is less than or equal to min, will always return false");

        return value >= min && value <= max;
    }
}
