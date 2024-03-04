package sistema.model;

public class Genero implements Cloneable {
	private int idGenero;
	private String nome;
	private String descricao;
	private Usuario criador;

	// CONSTRUTOR //
	private Genero(String nome, String descricao, Usuario criador) {
		this.nome = nome;
		this.descricao = descricao;
		this.criador = criador;
	}
	// *** //

	// CLONE //
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	// *** //

	// MÉTODO FÁBRICA //
	public static Genero getInstance(String nome, String descricao, Usuario criador) {
		if (nome != null && descricao != null && criador != null) {
			return new Genero(nome, descricao, criador);
		} else {
			return null;
		}
	}
	// *** //

	// GETTERS E SETTERS //
	public int getIdGenero() {
		return idGenero;
	}

	public void setIdGenero(int idGenero) {
		this.idGenero = idGenero;
	}

	public String getNome() {
		return nome;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public Usuario getCriador() {
		return criador;
	}
	// *** //
}
