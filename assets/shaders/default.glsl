#type vertex
#version 430 core
layout (location=0) in vec3 aPos;
layout (location=1) in vec3 aColor;
layout (location=2) in vec2 aTexCoords;
layout (location=3) in mat4 aModel;
layout (location=7) in vec4 aModelColor;

uniform mat4 uView;
uniform mat4 uProjection;

out vec4 fColor;
out vec2 fTexCoords;

void main()
{
    fColor = aModelColor;
    //fColor = aColor;
    fTexCoords = aTexCoords;
    gl_Position = uProjection * uView * aModel * vec4(aPos, 1.0);
}

#type fragment
#version 430 core

uniform sampler2D[8] uTex_Sampler;

in vec4 fColor;
in vec2 fTexCoords;
uniform int CR;
uniform float seed;

out vec4 color;

float random (vec2 st) {
    return fract(sin(dot(st.xy ,vec2(12.9898,78.233))) * 43758.5453);
}

float floor(float num) {
    if(num > 1){
        return num - 1;
    }
    else if(num < 0){
        return num + 1;
    }
    else{
        return num;
    }
}

void main()
{
    vec4 newColor = fColor * texture(uTex_Sampler[0], vec2(fTexCoords.x,fTexCoords.y ));
    newColor.r += random(vec2(fTexCoords.x,fTexCoords.y + seed/1000.0)) * CR;
    newColor.g += random(vec2(fTexCoords.x,fTexCoords.y - seed/1000.0)) * CR;
    newColor.b += random(vec2(fTexCoords.x + seed/1000.0,fTexCoords.y)) * CR;
    newColor = vec4(floor(newColor.r),floor(newColor.g),floor(newColor.b),1);
    color = newColor;
}