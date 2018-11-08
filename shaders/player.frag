#version 330 core
layout (location = 0) out vec4 color;

uniform sampler2D u_Texture;
uniform vec2 sprite;

in vec2 v_texCoord;


void main(){
  vec4 texColor = texture(u_Texture, v_texCoord + sprite);
  color = texColor;
  if(color.a <= 0.0){
  	discard;
  }
}
