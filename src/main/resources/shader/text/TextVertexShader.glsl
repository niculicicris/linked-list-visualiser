#version 330 core

uniform mat4 uProjection;
uniform mat4 uView;

layout (location = 0) in vec3 vPosition;
layout (location = 1) in vec2 vTexturePosition;

out vec2 fTexturePosition;

void main() {
    fTexturePosition = vTexturePosition;
    gl_Position = uProjection * uView * vec4(vPosition, 1);
}