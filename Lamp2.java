/* I declare that this code is my own work */
/* Author Euan Goodbrand egoodbrand1@sheffield.ac.uk */
import com.jogamp.opengl.*;

import gmaths.*;

public class Lamp2 {

    private final GL3 gl;

    private double startTime=0;
    private double animationTime;

    private boolean animate=false;

    private float keyFrameAngle=0;
    private final float newUpperAngle=0;
    private float newLowerAngle=0;
    private float newHeadAngle=0;
    private float angleOfRotation = 30f;
    private final float rotateUpperAngle = -30f;

    public SGNode lampStart;

    private final TransformNode translateLamp;
    private final TransformNode bottomRotate;
    private TransformNode topRotate;
    private final TransformNode bottomRotateReverse;
    private final TransformNode lampHeadRotate;
    private final TransformNode rotateGraph;
    private final TransformNode createBulb;
    private final TransformNode translateBulb;
    private final TransformNode bottomRotateAroundY;

    private final ModelNode bulbNode;

    private final NameNode bulbLight;

    public Lamp2(GL3 gl, Model sphere,Model cube,Model eyeObject)
    {
        this.gl = gl;
        //Start of the lamp heirarchy tree
        lampStart = new NameNode("Lamp Start");

        //Rotate entire lamp around the table
        rotateGraph = new TransformNode("Rotate 210 degrees around table", Mat4Transform.rotateAroundY(210));

        //Translates entire lamp to original position
        translateLamp = new TransformNode("translate entire lamp", Mat4Transform.translate(-4f,0f,0f));

        //Rotate bottom lamp  arm
        bottomRotate = new TransformNode("rotate bottom branch", 
                                        Mat4Transform.rotateAroundZ(angleOfRotation));

        bottomRotateReverse=new TransformNode("rotate bottom branch opposite",
                                        Mat4Transform.rotateAroundZ(-angleOfRotation));

        //Rotate bottom lamp arm around y
        bottomRotateAroundY = new TransformNode("rotate bottom branch", 
                                        Mat4Transform.rotateAroundY(45f));
        
        //Lamp Base
        NameNode lampBase=new NameNode("Lamp Base");
        Mat4 mesh = Mat4Transform.scale(1.3f,0.15f,1.3f);
        mesh = Mat4.multiply(mesh, Mat4Transform.translate(0,0.5f,0));
        TransformNode makeLampBase=new TransformNode("transform and apply mesh of lamp base", mesh);
        ModelNode lampBaseNode=new ModelNode("Lamp Base",cube);

        //Toes 
        NameNode toe1=new NameNode("toe1");
        mesh=Mat4Transform.scale(0.6f,0.3f,0.3f);
        mesh= Mat4.multiply(Mat4Transform.translate(0.6f,0.15f,-0.6f),mesh);
        TransformNode maketoe1=new TransformNode("transform toe 1",mesh);
        ModelNode toe1Node=new ModelNode("toe1",sphere);

        NameNode toe2=new NameNode("toe2");
        mesh=Mat4Transform.scale(0.6f,0.3f,0.3f);
        mesh= Mat4.multiply(Mat4Transform.translate(0.6f,0.15f,-0.2f),mesh);
        TransformNode maketoe2=new TransformNode("transform toe 2",mesh);
        ModelNode toe2Node=new ModelNode("toe2",sphere);

        NameNode toe3=new NameNode("toe3");
        mesh=Mat4Transform.scale(0.6f,0.3f,0.3f);
        mesh= Mat4.multiply(Mat4Transform.translate(0.6f,0.15f,0.2f),mesh);
        TransformNode maketoe3=new TransformNode("transform toe 3",mesh);
        ModelNode toe3Node=new ModelNode("toe3",sphere);

        NameNode toe4=new NameNode("toe4");
        mesh=Mat4Transform.scale(0.6f,0.3f,0.3f);
        mesh= Mat4.multiply(Mat4Transform.translate(0.6f,0.15f,0.6f),mesh);
        TransformNode maketoe4=new TransformNode("transform toe 4",mesh);
        ModelNode toe4Node=new ModelNode("toe4",sphere);

        //Lower pole of anglepoise lamp
        NameNode bottomArm = new NameNode("bottom branch");
        mesh = Mat4Transform.scale(0.25f,1.6f,0.25f);
        mesh = Mat4.multiply(mesh, Mat4Transform.translate(-0.2f,0.5f,0));
        TransformNode makebottomArm = new TransformNode("Transform bottom arm", mesh);
        ModelNode bottomArmModel = new ModelNode("bottom branch", sphere);

        //Rotate Upper lamp  arm
        topRotate = new TransformNode("Rotate bottom Arm",
                                Mat4Transform.rotateAroundZ(45f));

        //Lower arm to position
        TransformNode moveToOriginalLocation = new TransformNode("Move bottom to position",Mat4Transform.translate(0,1.6f,0));

        //Node allows movement of top and bottom arm to be connected
        TransformNode translateWithUpperArm=new TransformNode("Translate bottom arm",Mat4Transform.translate(0,1.6f,0));

        //Joint
        NameNode joint=new NameNode("joint");
        mesh=Mat4Transform.scale(0.6f,0.6f,0.6f);
        mesh= Mat4.multiply(mesh, Mat4Transform.translate(-0.25f,0.3f,0));
        TransformNode jointTransform=new TransformNode("Transform Joint",mesh);
        ModelNode jointModel=new ModelNode("Joint",sphere);

        //Rotate Upper lamp  arm
        topRotate = new TransformNode("Rotate Top Arm",
                                        Mat4Transform.rotateAroundZ(rotateUpperAngle));

        //Upper pole of anglepoise lamp
        NameNode topArm = new NameNode("Top branch");
        mesh = Mat4Transform.scale(0.25f,1.6f,0.25f);
        mesh= Mat4.multiply(mesh, Mat4Transform.translate(-1f,0.5f,0));
        TransformNode maketopArm = new TransformNode("Top Arm Transform", mesh);
        ModelNode topArmModel = new ModelNode("Top Arm", sphere);


        //Lamp Head
        NameNode lampHead =new NameNode("lampHead ");
        mesh=Mat4Transform.scale(1f,0.5f,0.5f);
        TransformNode makeLamp=new TransformNode("Head Trasnform",mesh);
        ModelNode lampHeadNode=new ModelNode("LampHead",cube);

        //Eyes
        NameNode eye1=new NameNode("eye1");
        mesh=Mat4Transform.scale(0.5f,0.5f,0.5f);
        mesh= Mat4.multiply(Mat4Transform.translate(0.6f,0.25f,0.4f),mesh);
        TransformNode makeeye1=new TransformNode("Transform eye1",mesh);
        ModelNode eye1Node=new ModelNode("Eye1",eyeObject);

        NameNode eye2=new NameNode("eye2");
        mesh=Mat4Transform.scale(0.5f,0.5f,0.5f);
        mesh= Mat4.multiply(Mat4Transform.translate(0.6f,0.25f,-0.4f),mesh);
        TransformNode makeeye2=new TransformNode("Transform eye2",mesh);
        ModelNode eye2Node=new ModelNode("Eye2",eyeObject);

        //Bulb
        NameNode bulbHolder = new NameNode("Bulb Holder");
        mesh=Mat4Transform.scale(0.5f,0.3f,0.3f);
        mesh=Mat4.multiply(Mat4Transform.translate(0.5f,0f,0),mesh);
        createBulb=new TransformNode("Transform the bulb",mesh);
        bulbNode=new ModelNode("Bulb",cube);

        //Node to animate head roatation and to set intitial rotation
        lampHeadRotate = new TransformNode("Rotate Head",
                                        Mat4Transform.rotateAroundY(50f));

        //Nodes for setting direction of the Spotlight
        translateBulb = new TransformNode("Spotlight translate", 
                                        Mat4Transform.translate(0.3f, 0, 0));
                                        
        bulbLight = new NameNode("Bulb Spotlight");

        lampStart.addChild(rotateGraph);
            rotateGraph.addChild(translateLamp);
                translateLamp.addChild(lampBase);
                    lampBase.addChild(makeLampBase);
                            makeLampBase.addChild(lampBaseNode);
                    lampBase.addChild(bottomArm);

                            //add toes
                            bottomArm.addChild(toe1);
                                toe1.addChild(maketoe1);
                                    maketoe1.addChild(toe1Node);
                            bottomArm.addChild(toe2);
                                toe2.addChild(maketoe2);
                                    maketoe2.addChild(toe2Node);
                            bottomArm.addChild(toe3);
                                toe3.addChild(maketoe3);
                                    maketoe3.addChild(toe3Node);   
                            bottomArm.addChild(toe4);
                                toe4.addChild(maketoe4);
                                    maketoe4.addChild(toe4Node);

                            bottomArm.addChild(bottomRotate);
                                bottomRotate.addChild(makebottomArm);
                                    makebottomArm.addChild(bottomArmModel);
                                bottomRotate.addChild(bottomRotateAroundY);
                                    bottomRotateAroundY.addChild(moveToOriginalLocation);
                                        moveToOriginalLocation.addChild(joint);
                                            joint.addChild(bottomRotateReverse);
                                                bottomRotateReverse.addChild(jointTransform);
                                                    jointTransform.addChild(jointModel);
                                                bottomRotateReverse.addChild(topArm);
                                                    topArm.addChild(topRotate);
                                                        topRotate.addChild(maketopArm);
                                                            maketopArm.addChild(topArmModel);
                                                        topRotate.addChild(translateWithUpperArm);
                                                            translateWithUpperArm.addChild(lampHead );
                                                                lampHead.addChild(lampHeadRotate);
                                                                    lampHeadRotate.addChild(makeLamp);
                                                                        makeLamp.addChild(lampHeadNode);
                                                                    lampHeadRotate.addChild(bulbHolder);
                                                                        bulbHolder.addChild(createBulb);
                                                                            createBulb.addChild(bulbNode);
                                                                        //spotlight direction
                                                                        bulbHolder.addChild(translateBulb);
                                                                            translateBulb.addChild(bulbLight);

                                                                    //Eyes
                                                                    lampHeadRotate.addChild(eye1);
                                                                        eye1.addChild(makeeye1);
                                                                            makeeye1.addChild(eye1Node);
                                                                    lampHeadRotate.addChild(eye2);
                                                                        eye2.addChild(makeeye2);
                                                                            makeeye2.addChild(eye2Node);

        lampStart.update();  // IMPORTANT – must be done every time any part of the scene graph changes
    }

    //Used to map the light to the lamp head
    public Vec3 getLamplightPos(){
        return bulbLight.getWorldTransform().getPosition();
    }

    public Vec3 getLampDirection() {
        Vec3 lampPos = bulbLight.getWorldTransform().getPosition();
        return Vec3.subtract(lampPos, createBulb.getWorldTransform().getPosition());
    }


    public void setAngle(float keyFrameAngle){
        this.keyFrameAngle=keyFrameAngle;
    }

    //Sets the start time for the animation
    public void setAnimationTime(double startTime){
        this.startTime=startTime;
    }

    //Set animation boolean if true then animation begins, if false animation stops
    public void setAnimate(boolean animate){
        this.animate=animate;
    }
    //Get animation boolean 
    public  boolean getAnimate(){
        return this.animate;
    }


    //Animation- animates between the keyframes
    public void animation(){
        animationTime=getSeconds()-startTime;
        float animationSpeed = 40f;
        float angle= animationSpeed * (float)animationTime;
        if(keyFrameAngle>0) {angle=+angle;} else {angle=-angle;}
        if (Math.abs(angle) <= Math.abs(keyFrameAngle)) {
            //Offset + angle over time + the changed angle during animation.
            angleOfRotation = 50f + angle + newLowerAngle;
            //Rotates each branch of the lamp tree
            lampHeadRotate.setTransform(Mat4Transform.rotateAroundY(((float)Math.sin(keyFrameAngle)*angleOfRotation)));
            topRotate.setTransform(Mat4Transform.rotateAroundZ(-angleOfRotation));
            bottomRotateReverse.setTransform(Mat4Transform.rotateAroundZ(-angleOfRotation));
            bottomRotate.setTransform(Mat4Transform.rotateAroundZ(angleOfRotation));
            lampStart.update(); 
        } else {
            newLowerAngle = newLowerAngle + angle;
            newHeadAngle = newHeadAngle + -10f;
            animate = false;
        }
    }

    //Function to render the lamp
    public void render(){
        lampStart.draw(gl);
    }

    //Returns the time passed in the system.
    private double getSeconds() {
        return System.currentTimeMillis()/1000.0;
    }

    
}
