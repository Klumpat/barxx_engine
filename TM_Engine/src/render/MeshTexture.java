package render;

public class MeshTexture {

	private int textureID;

	private float shineDamper = 1;
	private float reflectivity = 0;

	private boolean hasTransparency = false;
	private boolean hasReflectivity = false;

	public MeshTexture(int ID) {
		this.textureID = ID;
	}

	public int getTextureID() {
		return textureID;
	}

	public boolean isHasTransparency() {
		return hasTransparency;
	}

	public void setHasTransparency(boolean hasTransparency) {
		this.hasTransparency = hasTransparency;
	}

	public float getReflectivity() {
		return reflectivity;
	}

	public float getShineDamper() {
		return shineDamper;
	}

	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}

	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}
	
	public boolean isHasReflectivity() {
		return hasReflectivity;
	}
	
	public void setHasReflectivity(boolean hasReflectivity) {
		this.hasReflectivity = hasReflectivity;
	}

}
