package com.example.rahulagarwal.buttontrans;


import android.content.Context;
import android.media.MediaPlayer;

import java.io.InputStream;
import java.util.Scanner;

import static com.example.rahulagarwal.buttontrans.MainActivity.audiofile;
import static com.example.rahulagarwal.buttontrans.MainActivity.inputfile;
import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Created by RAHUL AGARWAL on 02-03-2017.
 */

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

// Returns true if the point p lies inside the polygon[] with n vertices
/*bool isInside(Point polygon[], int n, Point p)
{
    // There must be at least 3 vertices in polygon[]
    if (n < 3)  return false;

    // Create a point for line segment from p to infinite
    Point extreme = {INF, p.y};

    // Count intersections of the above line with sides of polygon
    int count = 0, i = 0;
    do
    {
        int next = (i+1)%n;

        // Check if the line segment from 'p' to 'extreme' intersects
        // with the line segment from 'polygon[i]' to 'polygon[next]'
        if(p.x==0&&p.y==0)
        {
          cout<<p.x<<"L"<<p.y<<" "<<n;
        }
        cout<<"Start:"<<next;
        if (doIntersect(polygon[i], polygon[next], p, extreme))
        {
            // If the point 'p' is colinear with line segment 'i-next',
            // then check if it lies on segment. If it lies, return true,
            // otherwise false

            if (orientation(polygon[i], p, polygon[next]) == 0)
            {
                cout<<"ll "<<polygon[i].x<<" "<<polygon[i].y<<" "<<p.x<<" "<<p.y<<" "<<polygon[next].x<<" "<<polygon[next].y;
               return onSegment(polygon[i], p, polygon[next]);
             }
               cout<<"         k     ";
            if(polygon[next].y==p.y)
            {
             Point q;
             q.x=p.x;
             q.y=p.y+0.01;
             extreme.y=extreme.y+0.01;
            cout<<q.x<<" "<<q.y<<" ";
             if(!(doIntersect(polygon[i], polygon[next], q, extreme)&&doIntersect(polygon[i+1], polygon[(next+1)%n], q, extreme)))
                {
                    //cout<<"KI";
                    q.y=p.y-0.01;
                    extreme.y=extreme.y-0.01;
                     cout<<q.x<<" "<<q.y<<" ";
                if(!(doIntersect(polygon[i], polygon[next], q, extreme)&&doIntersect(polygon[i+1], polygon[(next+1)%n], q, extreme)))
                    {
                    count++;
                    cout<<"LLLL";
                    }
                }
              i++;
              next++;
            }
            else
             count++;
        }
        cout<<"End: "<<next;
        i = next;

    } while (i != 0);

    // Return true if count is odd, false otherwise
    return count&1;  // Same as (count%2 == 1)
}*/

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
                        //cout<<xinter<<" ";
                       // cpolygon=(int)xinter;
                       /* if(xinter>=po.x&&(xinter<minx||flag==0))
                        {
                            minx=xinter;
                            cpolygon=i;
                            flag=1;
                        }
                        else if(xinter>=po.x&&xinter==minx)
                        {
                            //cout<<"lll";
                            double mini = po.x;
                            for(int ki=0;ki< poly[i].ns;ki++)
                            {
                                Point extreme1 = new Point();
                                extreme1.x=-INF;
                                extreme1.y=po.y;
                                if(doIntersect(poly[i].p[k],poly[i].p[(k+1)%n],po,extreme1))
                                {

                                    double xinter1;
                                  if(poly[i].p[(ki+1)%n].y!=poly[i].p[ki].y)
                                        xinter1 = (po.y*(poly[i].p[(ki+1)%n].x-poly[i].p[ki].x)-(poly[i].p[(ki+1)%n].x*poly[i].p[ki].y-poly[i].p[ki].x*poly[i].p[(ki+1)%n].y))/(poly[i].p[(ki+1)%n].y-poly[i].p[ki].y);
                                    else
                                        xinter1 = poly[i].p[(ki+1)%n].x<poly[i].p[ki].x?poly[i].p[(ki+1)%n].x:poly[i].p[ki].x;
                                    if(mini>xinter1)
                                        mini=xinter1;
                                }
                            }
                            int j=i;
                            int ik = cpolygon;
                            double mini2 = po.x;
                            for(int ka=0;k< poly[ik].ns;k++)
                            {
                                extreme.x=-INF;
                                extreme.y=po.y;
                                if(doIntersect(poly[ik].p[ka],poly[ik].p[(ka+1)%n],po,extreme))
                                {
                                    //cout<<"hi";
                                    double xinter1;
                                    if(poly[ik].p[(ka+1)%n].y!=poly[ik].p[ka].y)
                                        xinter1 = (po.y*(poly[ik].p[(ka+1)%n].x-poly[ik].p[ka].x)-(poly[ik].p[(ka+1)%n].x*poly[ik].p[ka].y-poly[ik].p[ka].x*poly[ik].p[(ka+1)%n].y))/(poly[ik].p[(ka+1)%n].y-poly[ik].p[ka].y);
                                    else
                                        xinter1 = poly[ik].p[(ka+1)%n].x<poly[ik].p[ka].x?poly[ik].p[(ka+1)%n].x:poly[ik].p[ka].x;
                                    if(mini2>xinter)
                                        mini2=xinter1;
                                }
                            }
                            if(mini==po.x)
                                cpolygon=ik;
                            else if(mini2==po.x)
                                cpolygon=j;
                            else
                                cpolygon= mini>mini2?j:ik;
                            //cout<<cpolygon<<" "<<mini<<" "<<mini2;
                        }*/




        }
        catch (Exception e)
        {
            quadi2=quadi2+"triple";
            quadi=99;
            System.out.println(e.toString());
        }
    /*Point polygon1[] = {{-1, -1}, {-1, 0}, {0, 1}, {1, 0}, {1,-1}};
    int n = sizeof(polygon1)/sizeof(polygon1[0]);
    Point p = {0, 0.1};
    isInside(polygon1, n, p)? cout << "Yes \n": cout << "No \n";

    p = {5, 5};
    isInside(polygon1, n, p)? cout << "Yes \n": cout << "No \n";

    Point polygon2[] = {{1, 0}, {2, 0}, {2, 2},{-2,2},{-2,-2},{1,-2}};
    p = {0, 0};
    n = sizeof(polygon2)/sizeof(polygon2[0]);
    isInside(polygon2, n, p)? cout << "Yes \n": cout << "No \n";

    p = {5, 1};
    isInside(polygon2, n, p)? cout << "Yes \n": cout << "No \n";

    p = {8, 1};
    isInside(polygon2, n, p)? cout << "Yes \n": cout << "No \n";

    Point polygon3[] =  {{0, 0}, {10, 0}, {10, 10}, {0, 10}};
    p = {-1,10};
    n = sizeof(polygon3)/sizeof(polygon3[0]);
    isInside(polygon3, n, p)? cout << "Yes \n": cout << "No \n";*/

        return 0;
    }
    void checkquad(int x, int y, Context mycontext)
    {

        try {
            // textout.setText("Hioverflow");

            String tet= "";
            //audioplayer text1 = new audioplayer();
            tet= inputfile;
            /*InputStream fi = mycontext.getAssets().open(inputfile);
            int size = fi.available();
            byte[] buffer = new byte[size];
            fi.read(buffer);
            tet = new String(buffer);*/
            quadcheck op = new quadcheck();
            secondact.audiop1=tet+"X,y:- "+x+" "+y;
            op.qu(tet,x,y);
            quadi2=quadi2+"double";
            String ans = quadi + "";
            secondact.audiop1=ans;
            // textout.setText(quadcheck.quadi+" ");
            String tk=" ";
            try {
                //audioplayer tex1 = new audioplayer();
                tk= audiofile;
               /* InputStream fik = mycontext.getAssets().open(audiofile);
                int sizea = fik.available();
                byte[] buffere = new byte[sizea];
                fik.read(buffere);
                tk = new String(buffere);*/
            }
            catch (Exception e)
            {}
            //secondact.audiop1=ans;
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
            //secondact.audiop1="opops";
            secondact.audiop1="apps";

        }
    }
}
