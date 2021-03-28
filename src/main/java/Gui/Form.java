package Gui;

import problem.Problem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Класс формы приложения
 */
public class Form extends JFrame {
    /**
     * панель для отображения OpenGL
     */
    private JPanel GLPlaceholder;
    private JPanel root;
    private JTextField xPoint1Field;
    private JTextField yPoint1Field;
    private JButton randomAngle;
    private JTextField angleCntField;
    private JButton loadFromFileBtn;
    private JButton saveToFileBtn;
    private JButton clearBtn;
    private JButton solveBtn;
    private JLabel problemText;
    private JButton addAngle;
    private JButton randomRectangle;
    private JButton addRectangle;
    private JLabel RectangleCntField;
    private JTextField zPoint1Field;
    private JTextField xPoint2Field;
    private JTextField xPoint3Field;
    private JTextField yPoint2Field;
    private JTextField yPoint3Field;
    private JTextField rectangleCntField;
    private JButton loadFromFileBtn2;
    private JButton saveToFileBtn2;
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    /**
     * таймер
     */
    private final Timer timer;
    /**
     * рисовалка OpenGL
     */
    private final RendererGL renderer;

    /**
     * Конструктор формы
     */
    private Form() {
        super(Problem.PROBLEM_CAPTION);
        // инициализируем OpenGL
        renderer = new RendererGL();
        // связываем элемент на форме с рисовалкой OpenGL
        GLPlaceholder.setLayout(new BorderLayout());
        GLPlaceholder.add(renderer.getCanvas());
        // указываем главный элемент формы
        getContentPane().add(root);
        // задаём размер формы
        setSize(getPreferredSize());
        // показываем форму
        setVisible(true);
        // обработчик зарытия формы
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new Thread(() -> {
                    renderer.close();
                    timer.stop();
                    System.exit(0);
                }).start();
            }
        });
        // тинициализация таймера, срабатывающего раз в 100 мсек
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onTime();
            }
        });
        timer.start();
        initWidgets();
    }

    /**
     * Инициализация виджетов
     */
    private void initWidgets() {
        // задаём текст полю описания задачи
        problemText.setText("<html>" + Problem.PROBLEM_TEXT.replaceAll("\n", "<br>"));

        addAngle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double x1 = Double.parseDouble(xPoint1Field.getText());
                double y1 = Double.parseDouble(yPoint1Field.getText());
                double x2 = Double.parseDouble(xPoint2Field.getText());
                double y2 = Double.parseDouble(yPoint2Field.getText());
                double x3 = Double.parseDouble(xPoint3Field.getText());
                double y3 = Double.parseDouble(yPoint3Field.getText());
                problem.Point vertex = new problem.Point(x1, y1);
                problem.Point a = new problem.Point(x2, y2);
                problem.Point b = new problem.Point(x3, y3);
                problem.Line l1 = new problem.Line(vertex.x,vertex.y,b.x,b.y);
                problem.Line l2 = new problem.Line(vertex.x,vertex.y,a.x,a.y);
                problem.Line l3 = new problem.Line(b.x,b.y,a.x,a.y);
                if (l1.isParallel(l2)||l3.isParallel(l1)||l2.isParallel(l3)) {
                } else {
                    if (vertex.distanceTo(a) * vertex.distanceTo(a) + vertex.distanceTo(b) * vertex.distanceTo(b) <
                            a.distanceTo(b) * a.distanceTo(b)) {
                    } else
                        renderer.problem.addAngle(vertex, a, b);
                }
            }
        });
        randomAngle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                renderer.problem.addRandomAngle(Integer.parseInt(angleCntField.getText()));
            }
        });
        addRectangle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double x1 = Double.parseDouble(xPoint1Field.getText());
                double y1 = Double.parseDouble(yPoint1Field.getText());
                double x2 = Double.parseDouble(xPoint2Field.getText());
                double y2 = Double.parseDouble(yPoint2Field.getText());
                double x3 = Double.parseDouble(xPoint3Field.getText());
                double y3 = Double.parseDouble(yPoint3Field.getText());
                problem.Point a = new problem.Point(x1, y1);
                problem.Point b = new problem.Point(x2, y2);
                problem.Point c = new problem.Point(x3, y3);
                problem.Rectangle r = new problem.Rectangle(a, b, c);
                if (r.a.checkPoint(r.b) || r.b.checkPoint(r.c) || r.c.checkPoint(r.d) || r.d.checkPoint(r.a)) {
                } else {
                    if (Math.abs(r.a.x) <= 1 && Math.abs(r.a.y) <= 1 && Math.abs(r.b.x) <= 1 && Math.abs(r.b.y) <= 1 && Math.abs(r.c.x) <= 1 && Math.abs(r.c.y) <= 1
                            && Math.abs(r.d.x) <= 1 && Math.abs(r.d.y) <= 1)
                        renderer.problem.addRectangle(a, b, c);
                }
            }
        });
        randomRectangle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                renderer.problem.addRandomRectangle(Integer.parseInt(rectangleCntField.getText()));
            }
        });
        loadFromFileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                renderer.problem.loadFromFile();
            }
        });
        saveToFileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                renderer.problem.saveToFile();
            }
        });
        loadFromFileBtn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                renderer.problem.loadFromFile();
            }
        });
        saveToFileBtn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                renderer.problem.saveToFile();
            }
        });
        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                renderer.problem.clear();
            }
        });
        solveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                renderer.problem.solve();
            }
        });
    }

    /**
     * Событие таймера
     */
    private void onTime() {
        // события по таймеру
    }

    /**
     * Главный метод
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        new Form();
    }
}
