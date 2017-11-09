#version 400 core

in vec3 position;

uniform mat4 projectionMatrix;
uniform mat4 transformMatrix;

void main(){
	// Setting position of the vertex in normalized device space
	gl_Position = projectionMatrix * transformMatrix * vec4(position, 1.0);
}
