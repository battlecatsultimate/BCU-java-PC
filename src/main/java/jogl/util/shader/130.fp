
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
        c.a *= para;
        c.xyz *= para;
    }
    else if(mode == 2)
    {
        c.a *= para;
        c.xyz = 1.0 - c.a + c.xyz * c.a;
    }
    else if (mode == 3)
    {
        c.a *= para;
    }
    else if (mode == 4)
    {
        c.xyz = solid.xyz * c.a;
        c.a *= solid.a;
    }

    mgl_FragColor = c;
}