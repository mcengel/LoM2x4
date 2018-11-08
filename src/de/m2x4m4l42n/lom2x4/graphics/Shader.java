package de.m2x4m4l42n.lom2x4.graphics;

import org.joml.*;
import org.lwjgl.BufferUtils;

import de.m2x4m4l42n.lom2x4.utils.ShaderUtils;
import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

public class Shader {
	
	public static final int VERTEX_ATTRIB = 0;
	public static final int TEXCOORD_ATTRIB = 1;
	
	public static Shader background;
	public static Shader player;
	
	private final int ID;
	private Map<String, Integer> locationCache = new HashMap<String,Integer>();
	
	
	public static void loadAll() {
		background = new Shader("shaders/background.vert","shaders/background.frag");
		player = new Shader("shaders/player.vert","shaders/player.frag");
		
	}
	
	public Shader(String vertex, String fragment) {
		ID = ShaderUtils.load(vertex, fragment);
	}
	
	public int getUniform(String name) {
		if(locationCache.containsKey(name))
			return locationCache.get(name);
		int result = glGetUniformLocation(ID, name);
		if(result == -1) {
			System.err.println("Could not find Uniform Location '"+name+"'");
		}else
			locationCache.put(name, result);
		return result;
	}
	
	public void setUniform1i(String name, int val) {
		glUniform1i(getUniform(name), val);
	}
	public void setUniform1f(String name, float val) {
		glUniform1f(getUniform(name), val);
	}
	public void setUniform2f(String name, Vector2f vec) {
		glUniform2f(getUniform(name), vec.x,vec.y);
	}
	public void setUniform3f(String name, Vector3f vec) {
		glUniform3f(getUniform(name), vec.x,vec.y,vec.z);
	}
	public void setUniformMat4f(String name, Matrix4f mat) {
		FloatBuffer fb =BufferUtils.createFloatBuffer(16);
		mat.get(fb);
		glUniformMatrix4fv(getUniform(name),false,fb );
		
	}
	
	public void bind() {
		glUseProgram(ID);
	}
	
	public void unbind() {
		glUseProgram(0);
	}
	public void render() {
		
	}

	public void setUniform2i(String name, Vector2i vec) {
		// TODO Auto-generated method stub
		glUniform2i(getUniform(name), vec.x,vec.y);
	}
}
