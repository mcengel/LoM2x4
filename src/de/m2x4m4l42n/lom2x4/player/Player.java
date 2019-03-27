package de.m2x4m4l42n.lom2x4.player;

import de.m2x4m4l42n.lom2x4.Input;
import de.m2x4m4l42n.lom2x4.graphics.Shader;
import de.m2x4m4l42n.lom2x4.graphics.Texture;
import de.m2x4m4l42n.lom2x4.graphics.VertexArray;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL13.*;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Player {

	private Texture texture;
	private VertexArray vao;
	private Vector3f position;
	private Vector2f sprite =new Vector2f();
	private int aniInc = 0;
	private long last = System.currentTimeMillis();
	private float speed = 0.07f;


	public Player() {
		float[] verticies = new float[] { 	-1.0f,-0.5f, 0.0f,
											 1.0f,-0.5f, 0.0f,
											 1.0f, 0.5f, 0.0f,
											-1.0f, 0.5f, 0.0f };
		byte[] indicies = new byte[] { 	0, 1, 2, 
										2, 3, 0 };
		float[] texCoord = new float[] {	0, 0.125f, 
											0, 0,
											0.05882353f, 0,
											0.05882353f, 0.125f };
		

		vao = new VertexArray(verticies, indicies, texCoord);
		position = new Vector3f();
		texture = new Texture("res/gfx/character.png");

	}
	public void animate() {
		if(aniInc < 3)
			aniInc++;
		else
			aniInc = 0;
	}
	public void update() {
		sprite.x = 0;
		if(Input.keys[GLFW_KEY_UP]) {
			position.y+= speed;
			sprite.y = 2*0.125f;
			sprite.x = aniInc*0.05882353f;
		}if(Input.keys[GLFW_KEY_DOWN]) {
			position.y-= speed;
			sprite.y = 0*0.125f;
			sprite.x = aniInc*0.05882353f;
		}if(Input.keys[GLFW_KEY_LEFT]) {
			position.x-= speed;
			sprite.y = 1*0.125f;
			sprite.x = aniInc*0.05882353f;
		}if(Input.keys[GLFW_KEY_RIGHT]) {
			position.x+= speed;
			sprite.y = 3*0.125f;
			sprite.x = aniInc*0.05882353f;
		}if(System.currentTimeMillis()-last > 100) {
			animate();
			last += 100;
		}


		Shader.player.bind();
		texture.bind();
		Shader.player.setUniformMat4f("mat_md", new Matrix4f().translate(position).rotate((float)Math.toRadians(90.0), 0.0f,0.0f,1.0f));
		Shader.player.setUniform2f("sprite", sprite);
			
		
	}
	public void render() {
		Shader.player.bind();
		texture.bind();
		vao.render();
		texture.unbind();
		Shader.player.unbind();
		

	}
	public Vector3f getPosition() {
		return position;
	}
}
