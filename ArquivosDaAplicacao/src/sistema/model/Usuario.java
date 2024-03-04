package sistema.model;

public class Usuario implements Cloneable {
	private int idUsuario;
	private String senha;
	private String nickname;
	private String email;

	// CONSTRUTOR //
	public Usuario() {
	}

	private Usuario(String senha, String nickname, String email) {
		this.senha = senha;
		this.nickname = nickname;
		this.email = email;
	}
	// *** //

	// MÉTODO FÁBRICA //
	public static Usuario getInstance(String senha, String nickname, String email) {
		if (senha != null && nickname != null && email != null) {
			return new Usuario(senha, nickname, email);
		} else {
			return null;
		}
	}
	// *** //

	// CLONE //
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	// *** //

	// GETTERS E SETTERS //
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		if (senha != null) {
			this.senha = senha;
		}
	}

	public String getNickname() {
		return nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if (email != null) {
			this.email = email;
		}
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	// *** //

	// FUNÇÕES //
	@Override
	public boolean equals(Object obj) {
		if (obj != null) {
			if (obj instanceof Usuario) {
				Usuario usuario = (Usuario) obj;
				return this.idUsuario == usuario.idUsuario;
			} else {
				return false;
			}
		} else {
			throw new NullPointerException();
		}
	}
	// *** //
}
