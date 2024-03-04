package sistema.model;

import java.util.Date;

public class Jogo {
	private int idJogo;
	private String nome;
	private String descricao;
	private String codigoFonte;
	private String faixaEtaria;
	private Genero genero;
	private Date dataPublicacao;
	private Usuario desenvolvedor;

	// CONSTRUTOR //
	public Jogo(String nome, String descricao, String codigoFonte, String faixaEtaria,
			Usuario desenvolvedor, Genero genero) {
		this.nome = nome;
		this.descricao = descricao;
		this.codigoFonte = codigoFonte;
		this.faixaEtaria = faixaEtaria;
		this.dataPublicacao = new Date();
		this.desenvolvedor = desenvolvedor;
		this.genero = genero;
	}
	// *** //

	// CLONE //
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	// *** //

	// MÉTODO FÁBRICA //
	public static Jogo getInstance(String nome, String descricao, String codigoFonte, String faixaEtaria,
			Usuario desenvolvedor, Genero genero) {
		if (nome != null && descricao != null && codigoFonte != null && faixaEtaria != null && desenvolvedor != null && genero != null) {
			return new Jogo(nome, descricao, codigoFonte, faixaEtaria, desenvolvedor, genero);
		} else {
			return null;
		}
	}
	// *** //

	// GETTERS E SETTERS //
	public int getIdJogo() {
		return idJogo;
	}

	public void setIdJogo(int idJogo) {
		this.idJogo = idJogo;
	}

	public String getNome() {
		return nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getCodigoFonte() {
		return codigoFonte;
	}

	public void setCodigoFonte(String codigoFonte) {
		this.codigoFonte = codigoFonte;
	}

	public String getFaixaEtaria() {
		return faixaEtaria;
	}

	public void setFaixaEtaria(String faixaEtaria) {
		this.faixaEtaria = faixaEtaria;
	}

	public Date getDataPublicacao() {
		return dataPublicacao;
	}

	public Usuario getDesenvolvedor() {
		return desenvolvedor;
	}
	
	public Genero getGenero() {
		return genero;
	}
	// *** //
}
