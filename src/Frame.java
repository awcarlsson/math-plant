package src;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.plaf.ButtonUI;
import javax.swing.plaf.basic.BasicButtonUI;

import java.awt.*;
import java.awt.event.*;

/**
 * The frame in which graphics and UI appears.
 */
public class Frame extends JFrame {

    public static final double ASPECT_RATIO = 16D / 9D;
    public static final int WIDTH = 1200;
    public static final int HEIGHT = (int) ((double) WIDTH / ASPECT_RATIO);
    public static final String NAME = "math-plant";

    private static GraphicsHandler graphicsHandler = new GraphicsHandler();

    public Frame() {

        this.setName(NAME);
        this.setSize(WIDTH, HEIGHT);
        this.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(graphicsHandler);
        //graphicsHandler.setLayout(null);

        // Reset button to clear all plants
        JButton reset = new JButton("reset");
        reset.setUI((ButtonUI) BasicButtonUI.createUI(reset));
        reset.setForeground(new Color(255, 255, 255));
        reset.setMargin(new Insets(0,0,0,0));
        reset.setContentAreaFilled(false);
        reset.setFont(new Font("Helvetica Neue", Font.BOLD, 32));
        reset.setFocusPainted(false);
        reset.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                graphicsHandler.reset();
            }
        });
        //reset.setSize(120, 30);
        //reset.setLocation(20, 40);
        graphicsHandler.add(reset);

        // Handles user inputted function
        JTextField funcText = new JTextField("Type a function here", 30);
        funcText.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                graphicsHandler.updateFunction(funcText.getText());
                funcText.setText("");
            }
        });
        graphicsHandler.add(funcText);

        // Enter user input button
        JButton funcTextBtn = new JButton("enter");
        funcTextBtn.setUI((ButtonUI) BasicButtonUI.createUI(reset));
        funcTextBtn.setForeground(new Color(255, 255, 255));
        funcTextBtn.setMargin(new Insets(0,0,0,0));
        funcTextBtn.setContentAreaFilled(false);
        funcTextBtn.setFont(new Font("Helvetica Neue", Font.BOLD, 32));
        funcTextBtn.setFocusPainted(false);
        funcTextBtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                graphicsHandler.updateFunction(funcText.getText());
                funcText.setText("");
            }
        });
        graphicsHandler.add(funcTextBtn);

        this.getContentPane().addMouseListener(new PlantListener());

        this.setVisible(true);
    }
}