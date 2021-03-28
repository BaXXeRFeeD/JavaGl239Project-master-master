package problem;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import java.util.ArrayList;

public class Rectangle {
    public Point a;
    public Point b;
    public Point d;
    public Point c;
    public Rectangle(Point vertex, Point a, Point b) {
        this.a = a;
        this.b = b;
        Line m = new Line(a.x, a.y, b.x, b.y);
        Line L = m.parallelLine(vertex);
        d = L.intersectionS(m.perpendicularLine(a));
        c = L.intersectionS(m.perpendicularLine(b));
    }
    void render(GL2 gl){
        gl.glPointSize(3);
        gl.glColor3d(1,0.5,0);
        gl.glLineWidth(2);
        gl.glBegin(GL.GL_LINE_LOOP);
        gl.glVertex2d(a.x,a.y);
        gl.glVertex2d(b.x,b.y);
        gl.glVertex2d(c.x,c.y);
        gl.glVertex2d(d.x,d.y);
        gl.glEnd();
    }
}
