/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca2;


import javax.swing.JFrame;

/**
 *
 * @author sebastian fernandes
 */
public class Snakeframe1 extends JFrame {
    Snakeframe1(String Uname){ 
    this.add(new GAmePanel_(Uname) {});
    this.setTitle("Snake Game");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false);
    this.pack();
    this.setVisible(true);
    this.setLocationRelativeTo(null);
    }
    
}
