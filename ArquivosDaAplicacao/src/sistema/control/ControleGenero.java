package sistema.control;

import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

import sistema.data.ErroAlteracaoException;
import sistema.data.ErroExcluirException;
import sistema.data.ErroInserirException;
import sistema.data.IRepositorioGeneroDao;
import sistema.model.Genero;
import sistema.model.Usuario;
import sistema.view.JanelaCadastroGenero;
import sistema.view.JanelaEdicaoGenero;
import sistema.view.JanelaListagemEdicaoGenero;
import sistema.view.JanelaListagemExclusaoGenero;
import sistema.view.JanelaListagemGenero;
import sistema.view.ViewPrincipal;

public class ControleGenero {
	private IRepositorioGeneroDao repositorioGenero;
	private ViewPrincipal viewPrincipal;

	public ControleGenero(IRepositorioGeneroDao repositorioGenero) {
		this.repositorioGenero = repositorioGenero;
		this.viewPrincipal = new ViewPrincipal();
	}

	public void cadastrar(Usuario usuario) {
		JanelaCadastroGenero janela = new JanelaCadastroGenero(usuario);
		if (!janela.getCancelou()) {
			Genero generoCriado = janela.getGenero();

			try {
				try {
					repositorioGenero.inserir(generoCriado);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "Gênero cadastrado com sucesso!");
			} catch (ErroInserirException e) {
				viewPrincipal.print("Exceção capturada: " + e.getMessage());
				JOptionPane.showMessageDialog(null,
						"Não foi possível cadastrar o gênero!\nTente novamente mais tarde.");
			}
		}
	}

	public void alterar(Usuario usuario) {
		JanelaListagemEdicaoGenero janelaListagem = null;
		try {
			janelaListagem = new JanelaListagemEdicaoGenero(repositorioGenero.getListaMeusGeneros(usuario));
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		if (!janelaListagem.getCancelou()) {
			Genero generoSelecionado = janelaListagem.getGenero();

			JanelaEdicaoGenero janelaEdicao = new JanelaEdicaoGenero(usuario, generoSelecionado);
			if (!janelaEdicao.getCancelou()) {
				Genero generoAlterado = janelaEdicao.getGenero();

				try {
					try {
						repositorioGenero.alterar(generoAlterado);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "Gênero alterado com sucesso!");
				} catch (ErroAlteracaoException e) {
					viewPrincipal.print("Exceção capturada: " + e.getMessage());
					JOptionPane.showMessageDialog(null,
							"Não foi possível alterar o gênero!\nTente novamente mais tarde.");
				}
			}
		}
	}

	public void excluir(Usuario usuario) {
		JanelaListagemExclusaoGenero janela = null;
		try {
			janela = new JanelaListagemExclusaoGenero(repositorioGenero.getListaMeusGeneros(usuario));
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		Genero generoSelecionado = janela.getGenero();

		if (!janela.getCancelou()) {
			int escolha = JOptionPane.showConfirmDialog(null,
					"Você tem certeza que deseja excluir o gênero selecionado permanentemente?", "Excluir Gênero",
					JOptionPane.YES_NO_OPTION);

			if (escolha == JOptionPane.YES_OPTION) {
				try {
					try {
						repositorioGenero.excluir(generoSelecionado.getIdGenero());
					} catch (SQLException e) {
						e.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "O gênero selecionado foi excluído!");
				} catch (ErroExcluirException e) {
					viewPrincipal.print("Exceção capturada: " + e.getMessage());
					JOptionPane.showMessageDialog(null,
							"Não foi possível excluir o gênero selecionado!\nTente novamente mais tarde.");
				}
			}
		}
	}

	public void listarTodos() {
		List<Genero> generos;
		try {
			generos = repositorioGenero.getGeneros();
			JanelaListagemGenero janela = new JanelaListagemGenero(generos, "de Todos os");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void listarMeus(Usuario usuario) {
		List<Genero> generos;
		try {
			generos = repositorioGenero.getListaMeusGeneros(usuario);
			JanelaListagemGenero janela = new JanelaListagemGenero(generos, "dos Meus");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
