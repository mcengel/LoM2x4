package de.m2x4m4l42n.lom2x4.map;

import org.joml.Matrix4f;

import de.m2x4m4l42n.lom2x4.graphics.Shader;
import de.m2x4m4l42n.lom2x4.graphics.Texture;
import de.m2x4m4l42n.lom2x4.graphics.VertexArray;

public class Map {
	
	private VertexArray background;
	private Texture texture;
	
	public Map() {
		float[] verticies = new float[] {
				-20.0f, -20.0f , 0.0f,
				-20.0f,  20.0f , 0.0f,
				 20.0f,  20.0f , 0.0f,
				 20.0f, -20.0f , 0.0f
				
		};
		byte[] indicies = new byte[] {
				0,1,2,
				2,3,0
		};
		float[] tcd = new float[] {
				0,1,
				0,0,
				1,0,
				1,1
		};
		
		background = new VertexArray(verticies, indicies, tcd);
		texture = new Texture("res/gfx/map.png");
		

		
	}
	
	public void render() {
		Shader.background.bind();
		texture.bind();
		background.render();
		texture.unbind();
		Shader.background.unbind();
	}

}
