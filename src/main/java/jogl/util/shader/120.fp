
varying vec2 texp;

uniform sampler2D tex;
uniform int mode;
uniform float para;

uniform vec4 solid;

void main() {
	vec4 c = texture2D(tex,texp);

    if(mode == 1)
    {
        c.w *= para;
        c.xyz *= para;
    }
    else if(mode == 2)
    {
        c.w *= para;
        c.xyz = 1.0 - c.w + c.xyz * c.w;
    }
    else if (mode == 3)
    {
        c.w *= para;
    }
    else if (mode == 4)
    {
        c.xyz = solid.xyz * solid.w * c.w;
        c.w *= solid.w;
    }

    gl_FragColor = c;
}