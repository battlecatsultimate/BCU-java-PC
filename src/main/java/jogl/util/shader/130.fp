
#if __VERSION__ >= 130
   #define varying in
   out vec4 mgl_FragColor;
   #define texture2D texture
 #else
   #define mgl_FragColor gl_FragColor  
#endif

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
        c.xyz = solid.xyz * c.w;
        c.w *= solid.w;
    }

    mgl_FragColor = c;
}