#version 330

in vec2 pass_texCoords;
in vec3 surfaceNormal;
in vec3 toLightVector;
in vec3 toCameraVector;

out vec4 out_color;

uniform sampler2D textureSampler;
uniform vec3 lightColor;
uniform vec3 attenuation;
uniform float shineDamper;
uniform float reflectivity;


void main(void){
	
	vec3 unitNormal = normalize(surfaceNormal);
	vec3 unitVectorToCamera =  normalize(toCameraVector);
	vec3 unitLightVector = normalize(toLightVector);
	
	vec3 totalDiffuse = vec3(0.0);
	vec3 totalSpecular = vec3(0.0);
	
	
	float distance = length(toLightVector);
	float attFactor = attenuation.x + (attenuation.y * distance) + (attenuation.z * distance * distance);
	
	vec3 lightDirection = -unitVectorToCamera;
	vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);
	
	float specularFactor = dot(reflectedLightDirection, unitVectorToCamera);
	specularFactor = max(specularFactor, 0.0);
	float dampedFactor = pow(specularFactor, shineDamper);
	vec3 finalSpecular = dampedFactor * reflectivity * lightColor;
	
	float nDot = dot(unitNormal, unitLightVector);
	float brightness = max(nDot,0.0);
	vec3 diffuse = (brightness * lightColor);
	
	
	totalDiffuse = totalDiffuse + (brightness * lightColor);
	totalSpecular = totalSpecular + (dampedFactor * reflectivity * lightColor);
	
	vec4 textureColor = texture(textureSampler, pass_texCoords);
	
	if(textureColor.a<0.5){
		discard;
	}
	
	out_color = vec4(totalDiffuse, 1.0) * textureColor + vec4(totalSpecular, 1.0);


}