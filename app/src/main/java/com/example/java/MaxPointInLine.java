package com.example.java;

import java.util.HashMap;

/**
 * Created by mapara on 3/1/17.
 */

public class MaxPointInLine {
    public static void main(String[] args) {
        Point[] points = {new Point(0,1),new Point(1,1),new Point(2,2),new Point(3,3),
                new Point(1,2),new Point(2,3),new Point(4,4),new Point(5,5)};
        //Point[] points = {new Point(0,1),new Point(1,2),new Point(2,2),new Point(3,3)};
        //-6 -17, 5 -16, -18 -17, 2 -4, 5 -13, -2 20
        Point[] points1 = {new Point(-6,-17),new Point(5,-16),new Point(-18,-17),new Point(2,-4), new Point(5, -13),
        new Point(-2, 20)};
        System.out.println(maxPoints(points1));
    }
    public static int maxPoints(Point[] points) {

        if(points.length <= 0) return 0;

        if(points.length <= 2) return points.length;

        int result = 0;

        for(int i = 0; i < points.length; i++){
            HashMap<Double, Integer> hm = new HashMap<Double, Integer>();
            int samex = 1;
            int samep = 0;
            for(int j = 0; j < points.length; j++){
                if(j != i){
                    if((points[j].x == points[i].x) && (points[j].y == points[i].y)){
                        samep++;
                        System.out.println("samep: " + samep);
                    }
                    if(points[j].x == points[i].x){
                        samex++;
                        System.out.println("samex: " + samex);
                        continue;
                    }
                    double k = (double)(points[j].y - points[i].y) / (double)(points[j].x - points[i].x);

                    if(hm.containsKey(k)){
                        hm.put(k,hm.get(k) + 1);
                    }else{
                        hm.put(k, 2);
                    }
                    result = Math.max(result, hm.get(k) + samep);
                    System.out.println("i: " + points[i] + ", j:" + points[j] +  "  slop :" + k + "  hm:" + hm.get(k) + "  MAX =" + result);
                }
            }
            result = Math.max(result, samex);
            System.out.println("MAX = " + result);
        }
        return result;
    }

    static class Point {
        int x;
        int y;
        Point() { x = 0; y = 0; }
        Point(int a, int b) { x = a; y = b; }
        @Override
        public String toString() {
            return "["+x+","+y+"]";
        }
    }
}
