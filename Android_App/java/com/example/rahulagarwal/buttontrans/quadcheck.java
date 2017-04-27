package com.example.rahulagarwal.buttontrans;


import android.content.Context;
import android.media.MediaPlayer;

import java.io.InputStream;
import java.util.Scanner;

import static com.example.rahulagarwal.buttontrans.MainActivity.audiofile;
import static com.example.rahulagarwal.buttontrans.MainActivity.inputfile;
import static java.lang.Math.max;
import static java.lang.Math.min;


public class quadcheck {

    // Define Infinite (Using INT_MAX caused overflow problems)
    private static final int  INF = 1000000;
    static int quadi=0;
    static String quadi2="";

    class Point
    {
        double x;
        double y;
    };
    class Polygon
    {
        Point p[];
        int ns;
    };
    // Given three colinear points p, q, r, the function checks if
// point q lies on line segment 'pr'
    boolean onSegment(Point p, Point q, Point r)
    {
        if (q.x <= max(p.x, r.x) && q.x >= min(p.x, r.x) &&
                q.y <= max(p.y, r.y) && q.y >= min(p.y, r.y))
            return true;
        return false;
    }

    // To find orientation of ordered triplet (p, q, r).
// The function returns following values
// 0 --> p, q and r are colinear
// 1 --> Clockwise
// 2 --> Counterclockwise
    int orientation(Point p, Point q, Point r)
    {
        double val = (q.y - p.y) * (r.x - q.x) -
                (q.x - p.x) * (r.y - q.y);

        if (val == 0) return 0;  // colinear
        return (val > 0)? 1: 2; // clock or counterclock wise
    }

    // The function that returns true if line segment 'p1q1'
// and 'p2q2' intersect.
    boolean doIntersect(Point p1, Point q1, Point p2, Point q2)
    {
        // Find the four orientations needed for general and
        // special cases
        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        // General case
        if (o1 != o2 && o3 != o4)
            return true;

        // Special Cases
        // p1, q1 and p2 are colinear and p2 lies on segment p1q1
        if (o1 == 0 && onSegment(p1, p2, q1)) return true;

        // p1, q1 and p2 are colinear and q2 lies on segment p1q1
        if (o2 == 0 && onSegment(p1, q2, q1)) return true;

        // p2, q2 and p1 are colinear and p1 lies on segment p2q2
        if (o3 == 0 && onSegment(p2, p1, q2)) return true;

        // p2, q2 and q1 are colinear and q1 lies on segment p2q2
        if (o4 == 0 && onSegment(p2, q1, q2)) return true;

        return false; // Doesn't fall in any of the above cases
    }

    // Driver program to test above functions
    int qu(String gh,int xf,int yf)
    {
        int rowCount,colCount;
        try {
            quadi=72;
            Scanner s = new Scanner(gh);
            quadi2 = quadi2+"("+gh+")";
             String op = s.next();
            rowCount=s.nextInt();
            Polygon poly[] = new Polygon[rowCount];

            for(int i = 0; i < rowCount; ++i)
            {
                colCount=s.nextInt();
                poly[i] = new Polygon();

                poly[i].ns=colCount;

                poly[i].p = new Point[colCount];

                for(int k=0;k<colCount;k++)
                {
                    poly[i].p[k]=new Point();
                    poly[i].p[k].x=s.nextDouble();poly[i].p[k].y=s.nextDouble();
                }
             quadi2=quadi2+i;
            }
            Point po = new Point();
            po.x=xf;
            po.y=yf;
            double minx=0;
            double minl[] = new double[rowCount];
            double minr[] = new double[rowCount];
            boolean checkl[] = new boolean[rowCount];
            boolean checkr[] = new boolean[rowCount];
            int flag=0,flag1=0;
            quadi2="pop1";
            for(int i=0;i< rowCount;++i) {
                int n = poly[i].ns;
                checkl[i] = false;
                checkr[i] = false;
                for (int k = 0; k < poly[i].ns; k++) {
                    Point extreme = new Point();
                    extreme.x = INF;
                    extreme.y = po.y;
                    if (doIntersect(poly[i].p[k], poly[i].p[(k + 1) % n], po, extreme)) {
                        //cout<<"hi";
                        double xinter;
                        if (poly[i].p[(k + 1) % n].y != poly[i].p[k].y)
                            xinter = (po.y * (poly[i].p[(k + 1) % n].x - poly[i].p[k].x) - (poly[i].p[(k + 1) % n].x * poly[i].p[k].y - poly[i].p[k].x * poly[i].p[(k + 1) % n].y)) / (poly[i].p[(k + 1) % n].y - poly[i].p[k].y);
                        else
                            xinter = poly[i].p[(k + 1) % n].x < poly[i].p[k].x ? poly[i].p[(k + 1) % n].x : poly[i].p[k].x;
                        if (flag == 0) {
                            minl[i] = xinter;
                            flag = 1;
                            checkl[i] = true;
                        } else if (minl[i] > xinter) {
                            minl[i] = xinter;
                        }
                    }
                    extreme.x = -INF;
                    extreme.y = po.y;
                    if (doIntersect(poly[i].p[k], poly[i].p[(k + 1) % n], po, extreme)) {
                        //cout<<"hi";
                        double xinter;
                        if (poly[i].p[(k + 1) % n].y != poly[i].p[k].y)
                            xinter = (po.y * (poly[i].p[(k + 1) % n].x - poly[i].p[k].x) - (poly[i].p[(k + 1) % n].x * poly[i].p[k].y - poly[i].p[k].x * poly[i].p[(k + 1) % n].y)) / (poly[i].p[(k + 1) % n].y - poly[i].p[k].y);
                        else
                            xinter = poly[i].p[(k + 1) % n].x < poly[i].p[k].x ? poly[i].p[(k + 1) % n].x : poly[i].p[k].x;
                        if (flag1 == 0) {
                            minr[i] = xinter;
                            flag1 = 1;
                            checkr[i] = true;
                        } else if (minr[i] < xinter) {
                            minr[i] = xinter;
                        }
                    }
                }
                flag = 0;
                flag1 = 0;
            }
            quadi2="uouo";
            flag = 0;
            flag1 = 0;
            int rpolygon = -1;
            int lpolygon = -1;
            double lmin=0.0,rmin=0.0;
            for(int i=0;i< rowCount;++i) {
                String cr = "";
                String cl = "";
                if(checkl[i]){
                    cl = "true";
                }
                else{
                    cl = "false";
                }
                if(checkr[i]){
                    cr = "true";
                }
                else{
                    cr = "false";
                }
                quadi2 = quadi2 + i + ":-" + (int)minl[i] + "|" + (int)minr[i] + "cr" + cr + "cl" + cl + "?";
                if ((minl[i] < lmin || flag == 0)&&checkl[i]) {
                    lpolygon = i;
                    lmin = minl[i];
                    flag = 1;
                }
                if ((minr[i] > rmin || flag1 == 0)&&checkr[i]) {
                    rpolygon = i;
                    rmin=minr[i];
                    flag1 = 1;
                }
            }
            quadi2 = quadi2 + "lpoly" + lpolygon + "rpoly" + rpolygon;
            if(rpolygon==lpolygon)
            {
                quadi=lpolygon+1;
            }
            else
            {
                quadi = -1;
            }

        }
        catch (Exception e)
        {
            quadi2=quadi2+"triple";
            quadi=99;
            System.out.println(e.toString());
        }
        return 0;
    }
    void checkquad(int x, int y, Context mycontext)
    {

        try {
            String tet= "";
            tet= inputfile;
            quadcheck op = new quadcheck();
            secondact.audiop1=tet+"X,y:- "+x+" "+y;
            op.qu(tet,x,y);
            quadi2=quadi2+"double";
            String ans = quadi + "";
            secondact.audiop1=ans;
            String tk=" ";
            try {
                tk= audiofile;
            }
            catch (Exception e)
            {}
            Scanner s = new Scanner(tk);
            String k=s.next();
            k=s.next();
            while(true)
            {

                if(k.compareTo(ans)==0)
                {
                    k=s.next();
                    break;
                }
                k=s.next();
                k=s.next();
            }
            secondact.audiop=k;
            secondact.audiop1="napps";

        }
        catch (Exception e)
        {
            secondact.audiop1="apps";
        }
    }
}
