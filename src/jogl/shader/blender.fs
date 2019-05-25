
varying vec2 texp;

uniform sampler2D tex;
uniform int mode;
uniform float para;

void main() {
	vec4 c = texture2D(tex,texp);
	if(mode == 1)
		c.a*=para;
	else if(mode == 2){
		c.a*=para;
		c.xyz=1.0-c.a+c.xyz*c.a;
	}
	else if(mode == -1)
		c.a*=-para;
	gl_FragColor=c;
}