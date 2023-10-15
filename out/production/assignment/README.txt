Jogl scene for an Office Room with Animated Lamps

The programme contains a java program that renders a scene with a GUI of buttons to 
control the scene.

The scene contains:
-A right wall
-A left wall
-A floor
-A windowframe
-A desk
-A box on top of the desk
-An egg on top of the box
-A tall anglepoise lamp with spotlight dressed like a lizard
-A short anglepoise lamp with spotlight dressed like a robot
-Two global lights
-A skybox for the sky
	
The egg, lamps and skybox are animated. The code for there animation and modelling 
is contained in there own classes.

The walls, floor and windowframe are modelled directly whilst the other models 
utilise a heirarchical model.

Must ensure that the lamp animation is finished before starting a new pose on a lamp. 
The lamps can be animated at the same time but the same lamp cannot have a pose button pressed during its own animation.

The programme can be compiled using the command: javac Hatch.java

Then the programme can be run using: java Hatch

