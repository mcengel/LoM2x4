#version 330 core

layout (location = 0) in vec4 position;
layout (location = 1) in vec2 texCoord;

out vec2 v_texCoord;

uniform mat4 mat_pr;

void main(){
  gl_Position = mat_pr * position;
  v_texCoord = texCoord;
}
