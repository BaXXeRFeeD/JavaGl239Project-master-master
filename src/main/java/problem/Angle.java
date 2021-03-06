package problem;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

public class Angle {
    public Point vertex;
    public Point vertexa;
    public Point vertexb;
    public Point a;
    public Point b;

    public Angle(Point x, Point y, Point z) {
        a = y;
        b = z;
        vertex = x;
        vertexa = vertexanglepoint(x, y);
        vertexb = vertexanglepoint(x, z);
        Line a = new Line(vertex.x, vertex.y, vertexa.x, vertexa.y);
        Line b = new Line(vertex.x, vertex.y, vertexb.x, vertexb.y);
        if (vertexa.y == 1) {
            vertexb.x = (-b.C - b.B) / b.A;
            vertexb.y = 1;
        }
        if (vertexa.y == -1) {
            vertexb.x = (-b.C + b.B) / b.A;
            vertexb.y = -1;
        }
        if (vertexb.y == 1) {
            vertexa.x = (-a.C - a.B) / a.A;
            vertexa.y = 1;
        }
        if (vertexb.y == -1) {
            vertexa.x = (-a.C + a.B) / a.A;
            vertexa.y = -1;
        }
    }

    public static Point vertexanglepoint(Point vertex, Point a) {
        Point vertexb = new Point(0, 0);
        Line l1 = new Line(vertex.x, vertex.y, a.x, a.y);
        if (vertex.x == a.x) {
            if (vertex.y < a.y)
                vertexb = l1.intersectionS(new Line(0, 1, 1, 1));
            else
                vertexb = l1.intersectionS(new Line(0, -1, 1, -1));
        } else {
            if (vertex.y == a.y) {
                if (vertex.x < a.x)
                    vertexb = l1.intersectionS(new Line(1, 0, 1, 1));
                else
                    vertexb = l1.intersectionS(new Line(-1, 0, -1, -1));
            } else {
                if (vertex.x < a.x)
                    vertexb = l1.intersectionS(new Line(1, 0, 1, 1));
                else
                    vertexb = l1.intersectionS(new Line(-1, 0, -1, 1));
            }
        }
        return vertexb;
    }

    void render(GL2 gl) {
        gl.glPointSize(3);
        gl.glColor3d(1, 0, 0);
        gl.glLineWidth(2);
        gl.glBegin(GL.GL_LINE_STRIP);
        gl.glVertex2d(vertexa.x, vertexa.y);
        gl.glVertex2d(vertex.x, vertex.y);
        gl.glVertex2d(vertexb.x, vertexb.y);
        gl.glEnd();
    }
}
