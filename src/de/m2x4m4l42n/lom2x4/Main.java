package de.m2x4m4l42n.lom2x4;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;
import org.joml.Matrix4f;

import de.m2x4m4l42n.lom2x4.graphics.Shader;
import de.m2x4m4l42n.lom2x4.map.Map;
import de.m2x4m4l42n.lom2x4.player.Player;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Main {

	// The window handle
	private long window;
	
	private int width = 1024;
	private int height = width/16*9;
	
	private Map map; 
	private Player player;
	
	private Matrix4f mat_pr;
	private Matrix4f mat_vw;
	
	public void run() {
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");

		init();
		loop();

		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

	private void init() {
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");

		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

		// Create the window
		window = glfwCreateWindow(width, height, "Hello World!", NULL, NULL);
		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window");

		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(window, new Input());

		// Get the thread stack and push a new frame
		try ( MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(window, pWidth, pHeight);

			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			// Center the window
			glfwSetWindowPos(
				window,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
		} // the stack frame is popped automatically

		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		// Enable v-sync
		glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(window);
		
		GL.createCapabilities();
		//Load All Shaders
		Shader.loadAll();
		
		map = new Map();
		player = new Player();
		
		mat_pr = new Matrix4f().setOrtho(-10.0f, 10.0f, -10.0f * 9.0f /16.0f, 10.0f * 9.0f /16.0f, -1.0f, 1.0f);
		mat_vw = new Matrix4f().identity();
		
		Shader.background.bind();
		Shader.background.setUniformMat4f("mat_pr", mat_pr.mul(mat_vw));
		Shader.background.unbind();
		Shader.player.bind();
		Shader.player.setUniformMat4f("mat_vw", mat_vw);
		Shader.player.setUniformMat4f("mat_pr", mat_pr);
		Shader.player.setUniform1i("u_Texture", 0);
		Shader.player.unbind();
		
		
	}

	private void loop() {
		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();

		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.
		long lastTime = System.nanoTime();
		double delta = 0.0f;
		double ns = 1000000000.0 / 60;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while ( !glfwWindowShouldClose(window) ) {
			long now = System.nanoTime();
			delta += (now - lastTime)/ns;
			lastTime = now;
			if(delta >= 1.0) {
			update();
			updates++;
			
			}
			render();
			frames++;
			if(System.currentTimeMillis()-timer > 1000) {
				timer += 1000;
				System.out.println(updates + "ups, " + frames + " fps");
				updates =0;
				frames=0;

			}
		}
	}
	private void update() {
		glfwPollEvents();
		
		player.update();
		
	}
	private void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		map.render();
		Shader.player.bind();
		player.render();
		glfwSwapBuffers(window);

	}
	public static void main(String[] args) {
		new Main().run();
	}

}