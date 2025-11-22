#version 150

uniform sampler2D Sampler0;

uniform vec4 ColorModulator;

in vec2 texCoord0;
in vec3 vertPos;

out vec4 fragColor;

void main() {
    vec4 color = texture(Sampler0, texCoord0);
    if (color.a == 0.0) {
        discard;
    }
    float side = cos(((vertPos.x+0.5)*3.14159265359/1.12) + 0.048009724);

    fragColor = color * ColorModulator * max(side, 0.12);
}
