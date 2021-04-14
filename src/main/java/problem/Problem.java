package problem;

import javax.media.opengl.GL2;
import java.io.*;
import java.util.*;


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
    private ArrayList<Point> pointos;
    private ArrayList<Point> pointsos;
    private ArrayList<Point> rectangle_end;
    private ArrayList<Point> angle_end;

    /**
     * Конструктор класса задачи
     */
    public Problem() {
        points = new ArrayList<>();
        angles = new ArrayList<>();
        rectangles = new ArrayList<>();
        pointos = new ArrayList<>();
        pointsos = new ArrayList<>();
        rectangle_end = new ArrayList<>();
        angle_end = new ArrayList<>();
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
        double max = 0;
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
                if (RectangleInAngle(r.a, a.vertex, a.vertexa, a.vertexb))
                    pointos.add(r.a);
                if (RectangleInAngle(r.b, a.vertex, a.vertexa, a.vertexb))
                    pointos.add(r.b);
                if (RectangleInAngle(r.c, a.vertex, a.vertexa, a.vertexb))
                    pointos.add(r.c);
                if (RectangleInAngle(r.d, a.vertex, a.vertexa, a.vertexb))
                    pointos.add(r.d);
                if (RectangleInAngle(a.vertex, r.a, r.b, r.c))
                    pointos.add(a.vertex);
                double d = square(pointos);
                if (d > max) {
                    pointsosos.clear();
                    rectangle_end.clear();
                    angle_end.clear();
                    angle_end.add(a.vertexa);
                    angle_end.add(a.vertex);
                    angle_end.add(a.vertexb);
                    rectangle_end.add(r.a);
                    rectangle_end.add(r.b);
                    rectangle_end.add(r.c);
                    rectangle_end.add(r.d);
                    max = d;
                    for (Point p : pointos) {
                        pointsosos.add(p);
                    }
                }
                pointos.clear();
            }
        }
        for (Point p : pointsosos) {
            boolean t = false;
            for (Point b : pointsos) {
                if (b.checkPoint(p))
                    t = true;
            }
            if (t) {
            } else {
                pointsos.add(p);
            }
        }
        ArrayList<Point> arr = Convexity(pointsos);
    }

    private ArrayList<Point> Convexity(ArrayList<Point> p) {
        if (p.size() == 0 || p.size() == 1 || p.size() == 2)
            return p;

        Point sum = new Point(0, 0);
        for (Point pl : p)
            sum = new Point(sum.x + pl.x, sum.y + pl.y);

        sum = new Point(sum.x / p.size(), sum.y / p.size());

        final Point center = sum;

        p.sort((a, b) -> {
            double angleA = Math.atan2(a.x - center.x, a.y - center.y);
            double angleB = Math.atan2(b.x - center.x, b.y - center.y);
            return Double.compare(angleA, angleB);
        });
        return p;
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
                double vertexX = sc.nextDouble();
                double vertexY = sc.nextDouble();
                double aX = sc.nextDouble();
                double aY = sc.nextDouble();
                double bX = sc.nextDouble();
                double bY = sc.nextDouble();
                sc.nextLine();
                Angle angle = new Angle(new Point(vertexX,vertexY), new Point(aX,aY), new Point(bX,bY));
                angles.add(angle);
            }
        } catch (Exception ex) {
            System.out.println("Ошибка чтения из файла: " + ex);
        }
    }

    public void loadFromFileRectangle() {
        rectangles.clear();
        try {
            File file = new File(FILE_RECTANGLES);
            Scanner sc = new Scanner(file);
            // пока в файле есть непрочитанные строки
            while (sc.hasNextLine()) {
                double vertexX = sc.nextDouble();
                double vertexY = sc.nextDouble();
                double aX = sc.nextDouble();
                double aY = sc.nextDouble();
                double bX = sc.nextDouble();
                double bY = sc.nextDouble();
                sc.nextLine();
                Rectangle rectangle = new Rectangle(new Point(vertexX,vertexY), new Point(aX, aY),
                        new Point(bX,bY));
                rectangles.add(rectangle);
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
            for (Angle angle : angles) {
                out.printf("%.2f %.2f %.2f %.2f %.2f %.2f\n", angle.vertex.x, angle.vertex.y,
                        angle.a.x, angle.a.y, angle.b.x, angle.b.y);
            }
            out.close();
        } catch (IOException ex) {
            System.out.println("Ошибка записи в файл: " + ex);
        }
    }

    public void saveToFileRectangle() {
        try {
            PrintWriter out = new PrintWriter(new FileWriter(FILE_RECTANGLES));
            for (Rectangle rectangle : rectangles) {
                out.printf("%.2f %.2f %.2f %.2f %.2f %.2f\n", rectangle.vertex.x, rectangle.vertex.y,
                        rectangle.a.x, rectangle.a.y, rectangle.b.x, rectangle.b.y);
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
        pointsos.clear();
        rectangle_end.clear();
        angle_end.clear();
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
        Figures.renderQuad2(gl, pointsos, rectangle_end, angle_end);
    }
}
