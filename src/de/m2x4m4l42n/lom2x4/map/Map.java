package de.m2x4m4l42n.lom2x4.map;

import de.m2x4m4l42n.lom2x4.graphics.Shader;
import de.m2x4m4l42n.lom2x4.graphics.VertexArray;

public class Map {
	
	private VertexArray background;
	
	public Map() {
		float[] verticies = new float[] {
				-10.0f, -10.0f * 9.0f /16.0f, 0.0f,
				-10.0f,  10.0f * 9.0f /16.0f, 0.0f,
				 10.0f,  10.0f * 9.0f /16.0f, 0.0f,
				 10.0f, -10.0f * 9.0f /16.0f, 0.0f
				
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
		
	}
	
	public void render() {
		Shader.background.bind();
		background.render();
		Shader.background.unbind();
	}

}
