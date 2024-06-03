#version 330 core

uniform mat4 uProjection;
uniform mat4 uView;

layout (location = 0) in vec3 vPosition;

void main() {
    gl_Position = uProjection * uView * vec4(vPosition, 1);
}