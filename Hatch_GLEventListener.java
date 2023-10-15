/* I declare that this code is my own work */
/* Author Euan Goodbrand egoodbrand1@sheffield.ac.uk */

import java.util.List;

import com.jogamp.common.nio.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.util.*;
import com.jogamp.opengl.util.awt.*;
import com.jogamp.opengl.util.glsl.*;

import gmaths.Mat4;
import gmaths.Mat4Transform;
import gmaths.Vec3;

public class Hatch_GLEventListener implements GLEventListener {
  
  private static final boolean DISPLAY_SHADERS = false;
    
  public Hatch_GLEventListener(Camera camera) {
    this.camera = camera;
    this.camera.setPosition(new Vec3(0f,8f,15f));
    this.camera.setTarget(new Vec3(0f,5f,0f));
  }

  /* Initialisation */
  public void init(GLAutoDrawable drawable) {   
    GL3 gl = drawable.getGL().getGL3();
    System.err.println("Chosen GLCapabilities: " + drawable.getChosenGLCapabilities());
    gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f); 
    gl.glClearDepth(1.0f);
    gl.glEnable(GL.GL_TEXTURE_2D);
    gl.glDepthFunc(GL.GL_LESS);
    gl.glFrontFace(GL.GL_CCW);   // default is 'CCW'
    gl.glEnable(GL.GL_DEPTH_TEST);   // default is 'back', assuming CCW
    startTime = getSeconds();
    initialise(gl);
  }
  
  /* Called to indicate the drawing surface has been moved and/or resized  */
  public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    GL3 gl = drawable.getGL().getGL3();
    gl.glViewport(x, y, width, height);
    float aspect = (float)width/(float)height;
    camera.setPerspectiveMatrix(Mat4Transform.perspective(45, aspect));
  }

  /* Draw */
  public void display(GLAutoDrawable drawable) {
    GL3 gl = drawable.getGL().getGL3();
    render(gl);
  }

  /* Clean up memory, if necessary */
  public void dispose(GLAutoDrawable drawable) {
    GL3 gl = drawable.getGL().getGL3();
    for (Light value : allLight) {
      value.dispose(gl);
    }
    floor.dispose(gl);
    leftWall.dispose(gl);
    rightWall.dispose(gl);
    cube.dispose(gl);
    skybox_cube.dispose(gl);
  }
  
  //Switches GlobalLight1 on or off depending on current current intensity.
  public void GlobalLight1() {
    if (allLight.get(0).getIntensity()==0){
      allLight.get(0).setIntensity(1);
    }else{
    allLight.get(0).setIntensity(0);
    }
  }

  //Switches GlobalLight2 on or off depending on current current intensity.
  public void GlobalLight2() {
    if (allLight.get(1).getIntensity()==0){
      allLight.get(1).setIntensity(1);
    }else{
    allLight.get(1).setIntensity(0);
    }
  }

  //Switches LampLight1 on or off depending on current current intensity.
  public void LampLight1() {
    if (allLight.get(2).getIntensity()==0){
      allLight.get(2).setIntensity(1);
    }else{
    allLight.get(2).setIntensity(0);
    }
  }

  //Switches LampLight2 on or off depending on current current intensity.
  public void LampLight2() {
    if (allLight.get(3).getIntensity()==0){
      allLight.get(3).setIntensity(1);
    }else{
    allLight.get(3).setIntensity(0);
    }
  }

  //Lamp1
  public void Lamp1pose1(){
    //Previous keyframe 1 and want 1
    if(previousKeyframeLamp1 == 1){
      keyFrameAngle = 0;
    //Previous keyframe 1 and want 2
    }else if(previousKeyframeLamp1 == 2){
      keyFrameAngle = 20f;
    //Previous keyframe 1 and want 3
    }else if(previousKeyframeLamp1 == 3){
      keyFrameAngle = 40f;
    }
    previousKeyframeLamp1 = 1;
    lamp1.setAnimate(true);
    lamp1.setAnimationTime(getSeconds());
    lamp1.setAngle(keyFrameAngle);
  }

  public void Lamp1pose2(){
    //Previous keyframe 1 and want 2
    if(previousKeyframeLamp1 == 1){
      keyFrameAngle = -20f;
    //Previous keyframe 2 and want 2
    }else if(previousKeyframeLamp1 == 2){
      keyFrameAngle = 0f;
    //Previous keyframe 3 and want 2
    }else if(previousKeyframeLamp1 == 3){
      keyFrameAngle = 20f;
    }
    previousKeyframeLamp1 = 2 ;
    lamp1.setAnimate(true);
    lamp1.setAnimationTime(getSeconds());
    lamp1.setAngle(keyFrameAngle);
  }

  public void Lamp1pose3(){ 
    //Previous keyframe 1 and want 1
    if(previousKeyframeLamp1 == 1){
      keyFrameAngle = -40f;
    //Previous keyframe 1 and want 2
    }else if(previousKeyframeLamp1 == 2){
      keyFrameAngle = -20f;
    //Previous keyframe 1 and want 3
    }else{
      keyFrameAngle = 0f;
    }
    previousKeyframeLamp1 = 3;
    lamp1.setAnimate(true);
    lamp1.setAnimationTime(getSeconds());
    lamp1.setAngle(keyFrameAngle);
  }

  //Lamp2
  public void Lamp2pose1(){    
    //Previous keyframe 1 and want 1
    if(previousKeyframeLamp2 == 1){
      keyFrameAngle = 0f;
    //Previous keyframe 1 and want 2
    }else if(previousKeyframeLamp2 == 2){
      keyFrameAngle = 45f;
    //Previous keyframe 1 and want 3
    }else if(previousKeyframeLamp2 == 3){
      keyFrameAngle = 90f;
    }
    previousKeyframeLamp2 = 1;
    lamp2.setAnimate(true);
    lamp2.setAnimationTime(getSeconds());
    lamp2.setAngle(keyFrameAngle);
  }

  public void Lamp2pose2(){
    startTime=getSeconds();
    lamp2.setAnimate(true);
    //Previous keyframe 1 and want 2
    if(previousKeyframeLamp2 == 1){
      keyFrameAngle = -45f;
    //Previous keyframe 2 and want 2
    }else if(previousKeyframeLamp2 == 2){
      keyFrameAngle = 0f;
    //Previous keyframe 3 and want 2
    }else if(previousKeyframeLamp2 == 3){
      keyFrameAngle = 45f;
    }
    previousKeyframeLamp2 = 2 ;
    lamp2.setAnimate(true);
    lamp2.setAnimationTime(getSeconds());
    lamp2.setAngle(keyFrameAngle);
  }

  public void Lamp2pose3(){
    startTime=getSeconds();
    lamp2.setAnimate(true);
    //Previous keyframe 1 and want 1
    if(previousKeyframeLamp2 == 1){
      keyFrameAngle = -90f;
    //Previous keyframe 1 and want 2
    }else if(previousKeyframeLamp2 == 2){
      keyFrameAngle = -45f;
    //Previous keyframe 1 and want 3
    }else if(previousKeyframeLamp2 == 3){
      keyFrameAngle = 0f;
    }
    previousKeyframeLamp2 = 3;
    lamp2.setAnimate(true);
    lamp2.setAnimationTime(getSeconds());
    lamp2.setAngle(keyFrameAngle);
  }

  private final Camera camera;

  //All models in the scene without own classes
  private Model floor, sphere, cube, leftWall, rightWall, skybox_cube, lampBase1, lampPole1,lampBase2,
                 lampPole2, eyeObject, eggObject,windowPanel,windowPanel2,windowPanel3,windowPanel4;

  //Models with own classes
  private Desk desk;
  private Egg egg;
  private Skybox skybox;
  private Lamp1 lamp1;
  private Lamp2 lamp2;  

  //lights
  private List<Light> allLight;
  private Light globalLight1;
  private Light globalLight2;
  private Light lampLight1;
  private Light lampLight2;

  //Scene node
  private SGNode scene;

  private float keyFrameAngle;
  private int previousKeyframeLamp1 = 1;
  private int previousKeyframeLamp2 = 1;


  private double startTime;

  private void initialise(GL3 gl) {
    // createRandomNumbers();
    int[] textureId0 = TextureLibrary.loadTexture(gl, "textures/mar0kuu2.jpg");
    int[] textureId1 = TextureLibrary.loadTexture(gl, "textures/wallpaper.jpg");
    int[] textureId2 = TextureLibrary.loadTexture(gl, "textures/mar0kuu2_specular.jpg");
    int[] textureId3 = TextureLibrary.loadTexture(gl, "textures/container2.jpg");
    int[] textureId4 = TextureLibrary.loadTexture(gl, "textures/container2_specular.jpg");
    int[] textureId5 = TextureLibrary.loadTexture(gl, "textures/skybox9.jpg");
    int[] textureId6 = TextureLibrary.loadTexture(gl, "textures/wallpaper_specular.jpg");
    int[] textureId7 = TextureLibrary.loadTexture(gl, "textures/egg.jpg");
    int[] textureId8 = TextureLibrary.loadTexture(gl, "textures/egg_specular.jpg");
    int[] textureId9 = TextureLibrary.loadTexture(gl, "textures/eye.jpg");
    int[] textureId10 = TextureLibrary.loadTexture(gl, "textures/green_scale.jpg");

    //Scene
    scene = new NameNode("Scene");

    //Light objects for scene
    this.allLight = new java.util.ArrayList<>();

    globalLight1 = new Light(gl);
    globalLight1.setCamera(camera);
    allLight.add(globalLight1);

    globalLight2 = new Light(gl);
    globalLight2.setCamera(camera);
    allLight.add(globalLight2);

    lampLight1 = new Light(gl);
    lampLight1.setCamera(camera);
    allLight.add(lampLight1);

    lampLight2 = new Light(gl);
    lampLight2.setCamera(camera);
    allLight.add(lampLight2);

    //Square object for use in models, also is floor model itself.
    Mesh mesh = new Mesh(gl, TwoTriangles.vertices.clone(), TwoTriangles.indices.clone());
    Shader shader = new Shader(gl, "shaders/vs_tt_05.txt", "shaders/fs_tt_05.txt");
    Material material = new Material(new Vec3(1.0f, 1.0f, 1.0f), new Vec3(1.0f, 1.0f, 1.0f), new Vec3(1.0f, 1.0f, 1.0f), 16.0f);
    Mat4 modelMatrix = Mat4Transform.scale(10,1f,10);
    floor = new Model(gl, camera, allLight, shader, material, modelMatrix, mesh, textureId0, textureId2);

    //Leftwall object
    mesh = new Mesh(gl, TwoTriangles.vertices.clone(), TwoTriangles.indices.clone());
    material = new Material(new Vec3(1.0f, 1.0f, 1.0f), new Vec3(1.0f, 1.0f, 1.0f), new Vec3(1.0f, 1.0f, 1.0f), 1f);
    modelMatrix = Mat4Transform.scale(10,1f,10);
    modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundX(90), modelMatrix);
    modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundY(90), modelMatrix);
    modelMatrix = Mat4.multiply(Mat4Transform.translate(-10f*0.5f,10f*0.5f,0f), modelMatrix);
    leftWall = new Model(gl, camera, allLight, shader, material, modelMatrix, mesh, textureId1, textureId6);

    //Rightwall object
    mesh = new Mesh(gl, TwoTriangles.vertices.clone(), TwoTriangles.indices.clone());
    material = new Material(new Vec3(1.0f, 1.0f, 1.0f), new Vec3(1.0f, 1.0f, 1.0f), new Vec3(1.0f, 1.0f, 1.0f), 1f);
    modelMatrix = Mat4Transform.scale(10,1f,10);
    modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundX(90), modelMatrix);
    modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundY(-90), modelMatrix);
    modelMatrix = Mat4.multiply(Mat4Transform.translate(10f*0.5f,10f*0.5f,0f), modelMatrix);
    rightWall = new Model(gl, camera, allLight, shader, material, modelMatrix, mesh, textureId1, textureId6);

    //windowPanel bottom object
    mesh = new Mesh(gl, TwoTriangles.vertices.clone(), TwoTriangles.indices.clone());
    material = new Material(new Vec3(1.0f, 1.0f, 1.0f), new Vec3(1.0f, 1.0f, 1.0f), new Vec3(1.0f, 1.0f, 1.0f), 1f);
    modelMatrix = Mat4Transform.scale(10f,1f,10/6);
    modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundX(90), modelMatrix);
    modelMatrix = Mat4.multiply(Mat4Transform.translate(0f,0.5f,-5.01f), modelMatrix);
    windowPanel = new Model(gl, camera, allLight, shader, material, modelMatrix, mesh, textureId1, textureId6);

    //windowPanel left object
    mesh = new Mesh(gl, TwoTriangles.vertices.clone(), TwoTriangles.indices.clone());
    material = new Material(new Vec3(1.0f, 1.0f, 1.0f), new Vec3(1.0f, 1.0f, 1.0f), new Vec3(1.0f, 1.0f, 1.0f), 1f);
    modelMatrix = Mat4Transform.scale(10f,1f,10/6);
    modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundX(90), modelMatrix);
    modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundZ(90), modelMatrix);
    modelMatrix = Mat4.multiply(Mat4Transform.translate(-4.5f,5f,-5f), modelMatrix);
    windowPanel2 = new Model(gl, camera, allLight, shader, material, modelMatrix, mesh, textureId1, textureId6);

    //windowPanel right object
    mesh = new Mesh(gl, TwoTriangles.vertices.clone(), TwoTriangles.indices.clone());
    material = new Material(new Vec3(1.0f, 1.0f, 1.0f), new Vec3(1.0f, 1.0f, 1.0f), new Vec3(1.0f, 1.0f, 1.0f), 1f);
    modelMatrix = Mat4Transform.scale(10f,1f,10/6);
    modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundX(90), modelMatrix);
    modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundZ(-90), modelMatrix);
    modelMatrix = Mat4.multiply(Mat4Transform.translate(4.5f,5f,-5.02f), modelMatrix);
    windowPanel3 = new Model(gl, camera, allLight, shader, material, modelMatrix, mesh, textureId1, textureId6);

    //windowPanel top object
    mesh = new Mesh(gl, TwoTriangles.vertices.clone(), TwoTriangles.indices.clone());
    material = new Material(new Vec3(1.0f, 1.0f, 1.0f), new Vec3(1.0f, 1.0f, 1.0f), new Vec3(1.0f, 1.0f, 1.0f), 1f);
    modelMatrix = Mat4Transform.scale(10f,1f,10/6);
    modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundX(90), modelMatrix);
    modelMatrix = Mat4.multiply(Mat4Transform.translate(0f,9.5f,-5.03f), modelMatrix);
    windowPanel4 = new Model(gl, camera, allLight, shader, material, modelMatrix, mesh, textureId1, textureId6);

    //Cube object for use in models
    mesh = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
    material = new Material(new Vec3(1.0f, 1.0f, 1.0f), new Vec3(1.0f, 1.0f, 1.0f), new Vec3(1.0f, 1.0f, 1.0f), 32.0f);
    modelMatrix = Mat4.multiply(Mat4Transform.scale(1f,0.5f,1f), Mat4Transform.translate(0f,3.5f,0f));
    cube = new Model(gl, camera, allLight, shader, material, modelMatrix, mesh, textureId3, textureId4);

    //Sphere object for use in models
    mesh = new Mesh(gl, Sphere.vertices.clone(), Sphere.indices.clone());
    material = new Material(new Vec3(1.0f, 1.0f, 1.0f), new Vec3(1.0f, 1.0f, 1.0f), new Vec3(1.0f, 1.0f, 1.0f), 32.0f);
    eggObject = new Model(gl, camera, allLight, shader, material, modelMatrix, mesh, textureId7, textureId8);

    //Sphere object for use in models
    mesh = new Mesh(gl, Sphere.vertices.clone(), Sphere.indices.clone());
    material = new Material(new Vec3(1.0f, 1.0f, 1.0f), new Vec3(1.0f, 1.0f, 1.0f), new Vec3(1.0f, 1.0f, 1.0f), 32.0f);
    eyeObject = new Model(gl, camera, allLight, shader, material, modelMatrix, mesh, textureId9, textureId9);


    //Lamp1 Shapes
    mesh = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
    lampBase1 = new Model(gl, camera, allLight, shader, material, modelMatrix, mesh, textureId10, textureId10);
    mesh = new Mesh(gl, Sphere.vertices.clone(), Sphere.indices.clone());
    lampPole1 = new Model(gl, camera, allLight, shader, material, modelMatrix, mesh, textureId10, textureId10);

    //Lamp2 Shapes
    mesh = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
    lampBase2 = new Model(gl, camera, allLight, shader, material, modelMatrix, mesh, textureId5, textureId5);
    mesh = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
    lampPole2 = new Model(gl, camera, allLight, shader, material, modelMatrix, mesh, textureId5, textureId5);

    // Skybox Object
    mesh = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
    //Sybox uses different shader to craete skybox effect and to keep sky lit when lights are off.
    shader = new Shader(gl, "shaders/vs_V01.txt", "shaders/fs_V01.txt");
    material = new Material(new Vec3(1.0f, 1.0f, 1.0f), new Vec3(1.0f, 1.0f, 1.0f), new Vec3(1.0f, 1.0f, 1.0f), 32.0f);
    modelMatrix = Mat4.multiply(Mat4Transform.scale(50,50,50), Mat4Transform.translate(0,0,0));
    skybox_cube = new Model(gl, camera, allLight, shader, material, modelMatrix, mesh, textureId5, textureId5);


    //Scene Objects Initialised from own classes
    desk = new Desk(gl, cube);
    egg = new Egg (gl, eggObject);
    skybox = new Skybox(gl, skybox_cube, startTime);
    lamp1 = new Lamp1(gl, lampPole1,lampBase1,eyeObject);
    lamp2 = new Lamp2(gl, lampPole2,lampBase2,eyeObject);

    //Updates scene
    scene.update();  
  }
 
  private void render(GL3 gl) {
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

    //Renders the skybox outside of the depth test so it appears far away.
    gl.glDisable(GL.GL_DEPTH_TEST); 
    skybox.render();
    gl.glEnable(GL.GL_DEPTH_TEST); //Re-initialise depth test for other objects.

    //Lights
    //Render Global Light 1 after checking if on
    globalLight1.setPosition(2.5f,10f,0f);  //Global light at top of scene on right
    if(globalLight1.getIntensity()==1){
    globalLight1.render(gl);
    }

    //Render Global Light 2 after checking if on
    globalLight2.setPosition(-2.5f,10f,0f); //Global light at top of scene on left
    if(globalLight2.getIntensity()==1){
    globalLight2.render(gl);
    }

    //Render Lamp Light 1 after checking if on
    lampLight1.setPosition(lamp1.getLamplightPos()); 
    lampLight1.setDirection(lamp1.getLampDirection());


    //Render Lamp Light 2 after checking if on
    lampLight2.setPosition(lamp2.getLamplightPos());  //Attaches lamplight2 to lamp2
    lampLight2.setDirection(lamp2.getLampDirection());


    //Render Scene Objects
    floor.render(gl); 
    leftWall.render(gl);
    rightWall.render(gl);
    cube.render(gl);
    desk.render();
    egg.render();
  
    //Render windowframe
    windowPanel.render(gl);
    windowPanel2.render(gl);
    windowPanel3.render(gl);
    windowPanel4.render(gl);

    //Renders Lamp 1 with animation
    if(lamp1.getAnimate())
    {
      lamp1.animation();
    }
    lamp1.render();

    //Renders Lamp 2 with animation
    if(lamp2.getAnimate())
    {
      lamp2.animation();
    }
    lamp2.render();

  }

  //Returns amount of time passed in system.
  private double getSeconds() {
    return System.currentTimeMillis()/1000.0;
  }
  
}