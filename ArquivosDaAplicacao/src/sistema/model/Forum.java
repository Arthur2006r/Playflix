package sistema.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Forum implements Cloneable {
	private int idForum;
	private String faixaEtaria;
	private Usuario lider;
	private Genero genero;
	private String nome;
	private String descricao;
	private Date dataCriacao;
	private List<Usuario> integrantes;

	// CONSTRUTOR //
	private Forum(String faixaEtaria, Usuario lider, String nome, String descricao, Genero genero) {
		this.dataCriacao = new Date();
		this.faixaEtaria = faixaEtaria;
		this.lider = lider;
		this.nome = nome;
		this.descricao = descricao;
		this.genero = genero;
		this.integrantes = new ArrayList<>();
	}
	// *** //

	// MÉTODO FÁBRICA //
	public static Forum getInstance(String faixaEtaria, Usuario lider, String nome, String descricao, Genero genero) {
		if (faixaEtaria != null && lider != null && nome != null && descricao != null && genero != null) {
			return new Forum(faixaEtaria, lider, nome, descricao, genero);
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
	public String getFaixaEtaria() {
		return faixaEtaria;
	}

	public void setFaixaEtaria(String faixaEtaria) {
		if (faixaEtaria != null) {
			this.faixaEtaria = faixaEtaria;
		}
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		if (nome != null) {
			this.nome = nome;
		}
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		if (descricao != null) {
			this.descricao = descricao;
		}
	}

	public int getIdForum() {
		return idForum;
	}

	public void setIdForum(int idForum) {
		if (idForum > 0) {
			this.idForum = idForum;
		}
	}

	public Usuario getLider() {
		return lider;
	}

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		if (genero != null) {
			this.genero = genero;
		}
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date date) {
		this.dataCriacao = date;
	}

	public void setLider(Usuario usuario) {
		// TODO Auto-generated method stub
		lider = usuario;
	}
	// *** //

	// FUNÇÕES //
	@Override
	public boolean equals(Object obj) {
		if (obj != null) {
			if (obj instanceof Forum) {
				Forum forum = (Forum) obj;
				return this.idForum == forum.idForum;
			} else {
				return false;
			}
		} else {
			throw new NullPointerException();
		}
	}

	public void addIntegrante(Usuario usuario) {
		if (usuario != null) {
			for (Usuario usu : integrantes) {
				if (usuario.equals(usu)) {
					return;
				}
			}

			integrantes.add(usuario);
		} else {
			throw new NullPointerException();
		}
	}
	// *** //
}
