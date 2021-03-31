package problem;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import java.awt.*;
import java.util.Random;

/**
 * Класс точки
 */
public class Point {
    /**
     * константа множества 1
     */
    public static final int SET_1 = 0;
    /**
     * константа множества 2
     */
    public static final int SET_2 = 1;
    /**
     * номер множества
     */
    int setNumber;
    /**
     * пересекается ли точка с точкой из другого множества
     * (является ли она решением)
     */
    boolean isSolution = false;
    /**
     * x - координата точки
     */
    public double x;
    /**
     * y - координата точки
     */
    public double y;

    /**
     * Конструктор точки
     *
     * @param x         координата
     * @param y         координата y
     * @param setNumber номер множества, к которому принадлежит точка
     */
    Point(double x, double y, int setNumber) {
        this.x = x;
        this.y = y;
        this.setNumber = setNumber;
    }

    public double distanceTo(Point p) {
        return Math.sqrt((x - p.x) * (x - p.x) + (y - p.y) * (y - p.y));
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return x;
    }
    /**
     * Получить случайную точку
     *
     * @return случайная точка
     */
    static Point getRandomPoint() {
        Random r = new Random();
        double nx = r.nextDouble() * 2 - 1;
        double ny = r.nextDouble() * 2 - 1;
        return new Point(nx, ny);
    }

    /**
     * Рисование точки
     *
     * @param gl переменная OpenGl для рисования
     */
    void render(GL2 gl) {
        if (isSolution)
            gl.glColor3d(1.0, 0.0, 0.0);
        else
            switch (setNumber) {
                case Point.SET_1:
                    gl.glColor3d(0.0, 1.0, 0.0);
                    break;
                case Point.SET_2:
                    gl.glColor3d(0.0, 0.0, 1.0);
                    break;
            }
        gl.glPointSize(3);
        gl.glBegin(GL.GL_POINTS);
        gl.glVertex2d(x, y);
        gl.glEnd();
        gl.glPointSize(1);
    }

    public boolean check(Point a, Point b) {
        boolean xbool = false;
        boolean ybool = false;
        if ((a.x <= x && x <= b.x) || (a.x >= x && x >= b.x))
            xbool = true;
        if ((a.y <= y && y <= b.y) || (a.y >= y && y >= b.y))
            ybool = true;
        if (xbool & ybool)
            return true;
        return false;
    }
    public boolean checkPoint(Point a){
        if(Math.abs(a.x-x)<0.000000001&&Math.abs(a.y-y)<0.000000001)
            return true;
        return false;
    }

    /**
     * Получить строковое представление точки
     *
     * @return строковое представление точки
     */
    @Override
    public String toString() {
        return "Точка с координатами: {" + x + "," + y + "} из множества: " + setNumber;
    }
}
