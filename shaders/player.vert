#version 330 core

layout (location = 0) in vec4 position;
layout (location = 1) in vec2 texCoord;

out vec2 v_texCoord;

uniform mat4 mat_md = mat4(1.0);
uniform mat4 mat_vw = mat4(1.0);
uniform mat4 mat_pr;

void main(){
  gl_Position = mat_pr * mat_vw * mat_md * position;
  v_texCoord = texCoord;
}
