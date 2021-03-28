package problem;

import javax.media.opengl.GL2;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * Класс задачи
 */
public class Problem {
    /**
     * текст задачи
     */
    public static final String PROBLEM_TEXT = "ПОСТАНОВКА ЗАДАЧИ:\n" +
            "Заданы два множества: множество острых углов и\n" + "множество прямоугольников." +
            "Требуется выделить наибольшую\n" + "площадь пересечения среди острых углов и прямоугольников.";

    /**
     * заголовок окна
     */
    public static final String PROBLEM_CAPTION = "Итоговый проект ученика 10-7 Сорокина Владимира";

    /**
     * путь к файлу
     */
    private static final String FILE_ANGLES = "angles.txt";
    private static final String FILE_RECTANGLES = "rectangles.txt";
    /**
     * список точек
     */
    private ArrayList<Point> points;
    private ArrayList<Angle> angles;
    private ArrayList<Rectangle> rectangles;
    ArrayList<Point> pointos = new ArrayList<>();
    ArrayList<Point> pointsos = new ArrayList<>();

    /**
     * Конструктор класса задачи
     */
    public Problem() {
        points = new ArrayList<>();
        angles = new ArrayList<>();
        rectangles = new ArrayList<>();
    }

    public void addAngle(Point vertex, Point a, Point b) {
        Angle angle = new Angle(vertex, a, b);
        angles.add(angle);
    }

    public void addPoint(double x, double y, int setVal) {
        Point point = new Point(x, y, setVal);
        points.add(point);
    }

    public void addRectangle(Point vertex, Point a, Point b) {
        Rectangle rectangle = new Rectangle(vertex, a, b);
        rectangles.add(rectangle);
    }

    /**
     * Решить задачу
     */
    public void solve() {
        ArrayList<Point> pointsosos = new ArrayList<>();
        double max = Integer.MIN_VALUE;
        for (Angle a : angles) {
            for (Rectangle r : rectangles) {
                Line line[] = new Line[6];
                line[0] = new Line(r.a.x, r.a.y, r.b.x, r.b.y);
                line[1] = new Line(r.b.x, r.b.y, r.c.x, r.c.y);
                line[2] = new Line(r.d.x, r.d.y, r.c.x, r.c.y);
                line[3] = new Line(r.d.x, r.d.y, r.a.x, r.a.y);
                line[4] = new Line(a.vertex.x, a.vertex.y, a.vertexa.x, a.vertexa.y);
                line[5] = new Line(a.vertex.x, a.vertex.y, a.vertexb.x, a.vertexb.y);
                for (int i = 0; i < 4; i++) {
                    if (!line[i].isParallel(line[5])) {
                        if (line[i].intersectionS(line[5]).check(new Point(line[i].x11, line[i].y11), new Point(line[i].x22, line[i].y22)) &&
                                line[i].intersectionS(line[5]).check(new Point(line[5].x11, line[5].y11), new Point(line[5].x22, line[5].y22)))
                            pointos.add(line[i].intersectionS(line[5]));
                    }
                }
                for (int i = 3; i >= 0; i--) {
                    if (!line[i].isParallel(line[4])) {
                        if (line[i].intersectionS(line[4]).check(new Point(line[i].x11, line[i].y11), new Point(line[i].x22, line[i].y22)) &&
                                line[i].intersectionS(line[4]).check(new Point(line[4].x11, line[4].y11), new Point(line[4].x22, line[4].y22)))
                            pointos.add(line[i].intersectionS(line[4]));
                    }
                }
                double d = square(pointos);
                if (d > max) {
                    pointsosos.clear();
                    max = d;
                    for (Point p : pointos) {
                        pointsosos.add(p);
                    }
                    pointos.clear();
                    pointos.add(new Point(r.a.x, r.a.y));
                    pointos.add(new Point(r.b.x, r.b.y));
                    pointos.add(new Point(r.c.x, r.c.y));
                    pointos.add(new Point(r.d.x, r.d.y));
                    pointos.add(new Point(a.vertex.x, a.vertex.y));
                    pointos.add(new Point(a.vertexa.x, a.vertexa.y));
                    pointos.add(new Point(a.vertexb.x, a.vertexb.y));
                    for (int i = 0; i < 4; i++) {
                        if (RectangleInAngle(pointos.get(i), a.vertex, a.vertexa, a.vertexb))
                            pointsosos.add(pointos.get(i));
                    }
                    for (int i = 4; i < 7; i++) {
                        if (RectangleInAngle(pointos.get(i), r.a, r.b, r.c) || RectangleInAngle(pointos.get(i), r.d, r.b, r.c))
                            pointsosos.add(pointos.get(i));
                    }
                }
                pointos.clear();
            }
        }
        for (Point p : pointsosos) {
            pointsos.add(p);
        }
    }

    public double square(ArrayList<Point> z) {
        if (z.size() == 0 || z.size() == 1 || z.size() == 2)
            return 0;
        double d = z.get(z.size() - 1).x * z.get(0).y - z.get(z.size() - 1).y * z.get(0).x;
        for (int i = 0; i < z.size() - 2; i++) {
            d += z.get(i).x * z.get(i + 1).y - z.get(i).y * z.get(i + 1).x;
        }
        return Math.abs(d) / 2;
    }

    public boolean RectangleInAngle(Point p, Point p1, Point p2, Point p3) {
        double x1 = p1.x;
        double y1 = p1.y;
        double x2 = p2.x;
        double y2 = p2.y;
        double x3 = p3.x;
        double y3 = p3.y;
        double x0 = p.x;
        double y0 = p.y;
        double d1 = (x1 - x0) * (y2 - y1) - (x2 - x1) * (y1 - y0);
        double d2 = (x2 - x0) * (y3 - y2) - (x3 - x2) * (y2 - y0);
        double d3 = (x3 - x0) * (y1 - y3) - (x1 - x3) * (y3 - y0);
        if ((d1 >= 0 && d2 >= 0 && d3 >= 0) || (d1 <= 0 && d2 <= 0 && d3 <= 0))
            return true;
        return false;
    }

    public ArrayList<Point> Convexity(ArrayList<Point> p) {
        if (p.size() == 0 || p.size() == 1 || p.size() == 2)
            return p;
        ArrayList<Point> end = new ArrayList<>();
        return p;
    }

    /**
     * Загрузить задачу из файла
     */
    public void loadFromFile() {
        angles.clear();
        try {
            File file = new File(FILE_ANGLES);
            Scanner sc = new Scanner(file);
            // пока в файле есть непрочитанные строки
            while (sc.hasNextLine()) {
                double x = sc.nextDouble();
                double y = sc.nextDouble();
                int setVal = sc.nextInt();
                sc.nextLine();
                Point point = new Point(x, y, setVal);
                points.add(point);
            }
        } catch (Exception ex) {
            System.out.println("Ошибка чтения из файла: " + ex);
        }
    }

    /**
     * Сохранить задачу в файл
     */
    public void saveToFile() {
        try {
            PrintWriter out = new PrintWriter(new FileWriter(FILE_ANGLES));
            for (Point point : points) {
                out.printf("%.2f %.2f %d\n", point.x, point.y, point.setNumber);
            }
            out.close();
        } catch (IOException ex) {
            System.out.println("Ошибка записи в файл: " + ex);
        }
    }

    /**
     * Добавить заданное число случайных точек
     *
     * @param n кол-во точек
     */
    public void addRandomPoints(int n) {
        for (int i = 0; i < n; i++) {
            Point p = Point.getRandomPoint();
            points.add(p);
        }
    }

    public void addRandomAngle(int n) {
        for (int i = 0; i < n; i++) {
            Point a = Point.getRandomPoint();
            Point b = Point.getRandomPoint();
            Point c = Point.getRandomPoint();
            problem.Line l1 = new problem.Line(c.x, c.y, b.x, b.y);
            problem.Line l2 = new problem.Line(c.x, c.y, a.x, a.y);
            problem.Line l3 = new problem.Line(b.x, b.y, a.x, a.y);
            if (l1.isParallel(l2) || l3.isParallel(l1) || l2.isParallel(l3))
                i--;
            else {
                if (a.distanceTo(b) * a.distanceTo(b) + a.distanceTo(c) * a.distanceTo(c) <=
                        b.distanceTo(c) * b.distanceTo(c)) {
                    i--;
                } else
                    angles.add(new Angle(a, b, c));
            }
        }
    }

    public void addRandomRectangle(int n) {
        for (int i = 0; i < n; i++) {
            Point a = Point.getRandomPoint();
            Point b = Point.getRandomPoint();
            Point c = Point.getRandomPoint();
            Rectangle r = new Rectangle(a, b, c);
            if (r.a.checkPoint(r.b) || r.b.checkPoint(r.c) || r.c.checkPoint(r.d) || r.d.checkPoint(r.a))
                i--;
            else {
                if (Math.abs(r.a.x) <= 1 && Math.abs(r.a.y) <= 1 && Math.abs(r.b.x) <= 1 && Math.abs(r.b.y) <= 1 && Math.abs(r.c.x) <= 1 && Math.abs(r.c.y) <= 1
                        && Math.abs(r.d.x) <= 1 && Math.abs(r.d.y) <= 1)
                    rectangles.add(r);
                else
                    i--;
            }
        }
    }

    /**
     * Очистить задачу
     */
    public void clear() {
        rectangles.clear();
        angles.clear();
    }

    /**
     * Нарисовать задачу
     *
     * @param gl переменная OpenGL для рисования
     */
    public void render(GL2 gl) {
        for (Angle angle : angles) {
            angle.render(gl);
        }
        for (Rectangle rectangle : rectangles) {
            rectangle.render(gl);
        }
        Figures.renderQuad2(gl, pointsos);
    }
}
