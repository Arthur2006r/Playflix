package sistema.control;

import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

import sistema.data.ErroAlteracaoException;
import sistema.data.ErroExcluirException;
import sistema.data.ErroInserirException;
import sistema.data.IRepositorioGeneroDao;
import sistema.data.IRepositorioJogoDao;
import sistema.model.Jogo;
import sistema.model.Usuario;
import sistema.view.JanelaCadastroJogo;
import sistema.view.JanelaEdicaoJogo;
import sistema.view.JanelaListagemEdicaoJogo;
import sistema.view.JanelaListagemExclusaoJogo;
import sistema.view.JanelaListagemJogo;
import sistema.view.ViewPrincipal;

public class ControleJogo {
	private IRepositorioJogoDao repositorioJogo;
	private IRepositorioGeneroDao repositorioGenero;
	private ViewPrincipal viewPrincipal;

	public ControleJogo(IRepositorioJogoDao repositorioJogo, IRepositorioGeneroDao repositorioGenero) {
		this.repositorioJogo = repositorioJogo;
		this.repositorioGenero = repositorioGenero;
		this.viewPrincipal = new ViewPrincipal();
	}

	public void cadastrar(Usuario usuario) {
		JanelaCadastroJogo janela = null;
		try {
			janela = new JanelaCadastroJogo(usuario, repositorioGenero.getGeneros());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		if (!janela.getCancelou()) {
			Jogo jogoCriado = janela.getJogo();

			try {
				try {
					repositorioJogo.inserir(jogoCriado);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "Jogo cadastrado com sucesso!");
			} catch (ErroInserirException e) {
				viewPrincipal.print("Exceção capturada: " + e.getMessage());
				JOptionPane.showMessageDialog(null, "Não foi possível cadastrar o jogo!\nTente novamente mais tarde.");
			}
		}
	}

	public void alterar(Usuario usuario) {
		JanelaListagemEdicaoJogo janelaListagem = null;
		try {
			janelaListagem = new JanelaListagemEdicaoJogo(repositorioJogo.getListaMeusJogos(usuario));
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		if (!janelaListagem.getCancelou()) {
			Jogo jogoSelecionado = janelaListagem.getJogo();

			JanelaEdicaoJogo janelaEdicao = null;
			try {
				janelaEdicao = new JanelaEdicaoJogo(jogoSelecionado, repositorioGenero.getGeneros(), usuario);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			if (!janelaEdicao.getCancelou()) {
				Jogo jogoAlterado = janelaEdicao.getJogo();

				try {
					try {
						repositorioJogo.alterar(jogoAlterado);
						JOptionPane.showMessageDialog(null, "Jogo alterado com sucesso!");
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} catch (ErroAlteracaoException e) {
					viewPrincipal.print("Exceção capturada: " + e.getMessage());
					JOptionPane.showMessageDialog(null,
							"Não foi possível alterar o jogo!\nTente novamente mais tarde.");
				}
			}
		}
	}

	public void excluir(Usuario usuario) {
		JanelaListagemExclusaoJogo janela = null;
		try {
			janela = new JanelaListagemExclusaoJogo(repositorioJogo.getListaMeusJogos(usuario));
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		Jogo jogoSelecionado = janela.getJogo();

		if (!janela.getCancelou()) {
			int escolha = JOptionPane.showConfirmDialog(null,
					"Você tem certeza que deseja excluir o jogo selecionado permanentemente?", "Excluir Jogo",
					JOptionPane.YES_NO_OPTION);

			if (escolha == JOptionPane.YES_OPTION) {
				try {
					try {
						repositorioJogo.excluir(jogoSelecionado.getIdJogo());
					} catch (SQLException e) {
						e.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "O jogo selecionado foi excluído!");
				} catch (ErroExcluirException e) {
					viewPrincipal.print("Exceção capturada: " + e.getMessage());
					JOptionPane.showMessageDialog(null,
							"Não foi possível excluir o jogo selecionado!\nTente novamente mais tarde.");
				}
			}
		}
	}

	public void listarTodos() {
		List<Jogo> jogos;
		try {
			jogos = repositorioJogo.getJogos();
			JanelaListagemJogo janela = new JanelaListagemJogo(jogos, "de Todos os");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void listarMeus(Usuario usuario) {
		try {
			List<Jogo> jogos = repositorioJogo.getListaMeusJogos(usuario);
			JanelaListagemJogo janela = new JanelaListagemJogo(jogos, "dos Meus");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
