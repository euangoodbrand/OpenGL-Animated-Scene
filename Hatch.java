/* I declare that this code is my own work */
/* Author Euan Goodbrand egoodbrand1@sheffield.ac.uk */

import gmaths.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

public class Hatch extends JFrame implements ActionListener {
  
  private static final int WIDTH = 1300;
  private static final int HEIGHT = 768;
  private static final Dimension dimension = new Dimension(WIDTH, HEIGHT);
  private final GLCanvas canvas;
  private final Hatch_GLEventListener glEventListener;
  private final FPSAnimator animator; 
  private final Camera camera;

  public static void main(String[] args) {
    Hatch b1 = new Hatch("Hatch");
    b1.getContentPane().setPreferredSize(dimension);
    b1.pack();
    b1.setVisible(true);
  }

  public Hatch(String textForTitleBar) {
    super(textForTitleBar);
    GLCapabilities glcapabilities = new GLCapabilities(GLProfile.get(GLProfile.GL3));
    canvas = new GLCanvas(glcapabilities);
    camera = new Camera(Camera.DEFAULT_POSITION, Camera.DEFAULT_TARGET, Camera.DEFAULT_UP);
    glEventListener = new Hatch_GLEventListener(camera);
    canvas.addGLEventListener(glEventListener);
    canvas.addMouseMotionListener(new MyMouseInput(camera));
    canvas.addKeyListener(new MyKeyboardInput(camera));
    getContentPane().add(canvas, BorderLayout.CENTER);
    

    //Buttons for GUI
    JMenuBar menuBar=new JMenuBar();
    this.setJMenuBar(menuBar);
      JMenu fileMenu = new JMenu("File");
        JMenuItem quitItem = new JMenuItem("Quit");
        quitItem.addActionListener(this);
        fileMenu.add(quitItem);
    menuBar.add(fileMenu);
    
    JPanel p = new JPanel();
      JButton b = new JButton("GlobalLight1");
      b.addActionListener(this);
      p.add(b);
      b = new JButton("GlobalLight2");
      b.addActionListener(this);
      p.add(b);
      b = new JButton("LampLight1");
      b.addActionListener(this);
      p.add(b);
      b = new JButton("LampLight2");
      b.addActionListener(this);
      p.add(b);
      b = new JButton("Lamp1pose1(default)");
      b.addActionListener(this);
      p.add(b);
      b = new JButton("Lamp1pose2");
      b.addActionListener(this);
      p.add(b);
      b = new JButton("Lamp1pose3");
      b.addActionListener(this);
      p.add(b);
      b = new JButton("Lamp2pose1(default)");
      b.addActionListener(this);
      p.add(b);
      b = new JButton("Lamp2pose2");
      b.addActionListener(this);
      p.add(b);
      b = new JButton("Lamp2pose3");
      b.addActionListener(this);
      p.add(b);
    this.add(p, BorderLayout.SOUTH);
    
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        animator.stop();
        remove(canvas);
        dispose();
        System.exit(0);
      }
    });
    animator = new FPSAnimator(canvas, 60);
    animator.start();
  }
  

  //Button Actions from event listener
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equalsIgnoreCase("GlobalLight1")) {
      glEventListener.GlobalLight1();

    }
    else if (e.getActionCommand().equalsIgnoreCase("GlobalLight2")) {
      glEventListener.GlobalLight2();
    }
    else if (e.getActionCommand().equalsIgnoreCase("LampLight1")) {
      glEventListener.LampLight1();
    }
    else if (e.getActionCommand().equalsIgnoreCase("LampLight2")) {
      glEventListener.LampLight2();
    }
    else if (e.getActionCommand().equalsIgnoreCase("Lamp1pose1(default)")) {
      glEventListener.Lamp1pose1();
    }
    else if (e.getActionCommand().equalsIgnoreCase("Lamp1pose2")) {
      glEventListener.Lamp1pose2();
    }
    else if (e.getActionCommand().equalsIgnoreCase("Lamp1pose3")) {
      glEventListener.Lamp1pose3();
    }
    else if (e.getActionCommand().equalsIgnoreCase("Lamp2pose1(default)")) {
      glEventListener.Lamp2pose1();
    }
    else if (e.getActionCommand().equalsIgnoreCase("Lamp2pose2")) {
      glEventListener.Lamp2pose2();
    }
    else if (e.getActionCommand().equalsIgnoreCase("Lamp2pose3")) {
      glEventListener.Lamp2pose3();
    }
    else if(e.getActionCommand().equalsIgnoreCase("quit"))
      System.exit(0);
  }
  
}
 
//Keyboard input for movement around scene
class MyKeyboardInput extends KeyAdapter  {
  private final Camera camera;
  
  public MyKeyboardInput(Camera camera) {
    this.camera = camera;
  }
  
  public void keyPressed(KeyEvent e) {
    Camera.Movement m = Camera.Movement.NO_MOVEMENT;
    switch (e.getKeyCode()) {
      case KeyEvent.VK_LEFT:  m = Camera.Movement.LEFT;  break;
      case KeyEvent.VK_RIGHT: m = Camera.Movement.RIGHT; break;
      case KeyEvent.VK_UP:    m = Camera.Movement.UP;    break;
      case KeyEvent.VK_DOWN:  m = Camera.Movement.DOWN;  break;
      case KeyEvent.VK_A:  m = Camera.Movement.FORWARD;  break;
      case KeyEvent.VK_Z:  m = Camera.Movement.BACK;  break;
    }
    camera.keyboardInput(m);
  }
}

//Mouse input for looking around the scene
class MyMouseInput extends MouseMotionAdapter {
  private Point lastpoint;
  private final Camera camera;
  
  public MyMouseInput(Camera camera) {
    this.camera = camera;
  }
  
    /**
   * mouse is used to control camera position
   *
   * @param e  instance of MouseEvent
   */    
  public void mouseDragged(MouseEvent e) {
    Point ms = e.getPoint();
    float sensitivity = 0.001f;
    float dx=(float) (ms.x-lastpoint.x)*sensitivity;
    float dy=(float) (ms.y-lastpoint.y)*sensitivity;
    if (e.getModifiers()==MouseEvent.BUTTON1_MASK)
      camera.updateYawPitch(dx, -dy);
    lastpoint = ms;
  }

  /**
   * mouse is used to control camera position
   *
   * @param e  instance of MouseEvent
   */  
  public void mouseMoved(MouseEvent e) {   
    lastpoint = e.getPoint(); 
  }
}