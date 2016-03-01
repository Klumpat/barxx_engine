package render;

public class TexturedMesh {
	
	private Mesh mesh;
	private MeshTexture texture;
	
	public TexturedMesh(Mesh mesh, MeshTexture texture){
		this.texture = texture;
		this.mesh = mesh;
	}
	
	public Mesh getMesh() {
		return mesh;
	}
	
	public MeshTexture getTexture() {
		return texture;
	}

}
