package sistema.control;

import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

import sistema.data.ErroAlteracaoException;
import sistema.data.ErroExcluirException;
import sistema.data.ErroInserirException;
import sistema.data.IRepositorioForumDao;
import sistema.data.IRepositorioGeneroDao;
import sistema.model.Forum;
import sistema.model.Usuario;
import sistema.view.JanelaCadastroForum;
import sistema.view.JanelaEdicaoForum;
import sistema.view.JanelaListagemEdicaoForum;
import sistema.view.JanelaListagemExclusaoForum;
import sistema.view.JanelaListagemForum;
import sistema.view.JanelaListagemPararDeSeguirForum;
import sistema.view.JanelaListagemSeguirForum;
import sistema.view.ViewPrincipal;

public class ControleForum {
	private IRepositorioForumDao repositorioForum;
	private IRepositorioGeneroDao repositorioGenero;
	private ViewPrincipal viewPrincipal;

	public ControleForum(IRepositorioForumDao repositorioForum, IRepositorioGeneroDao repositorioGenero) {
		this.repositorioForum = repositorioForum;
		this.repositorioGenero = repositorioGenero;
		this.viewPrincipal = new ViewPrincipal();
	}

	public void cadastrar(Usuario usuario) {
		JanelaCadastroForum janela = null;
		try {
			janela = new JanelaCadastroForum(repositorioGenero.getGeneros(), usuario);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		if (!janela.getCancelou()) {
			Forum forumCriado = janela.getForum();

			try {
				try {
					repositorioForum.inserir(forumCriado);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "Fórum cadastrado com sucesso!");
			} catch (ErroInserirException e) {
				viewPrincipal.print("Exceção capturada: " + e.getMessage());
				JOptionPane.showMessageDialog(null, "Não foi possível cadastrar o fórum!\nTente novamente mais tarde.");
			}
		}
	}

	public void alterar(Usuario usuario) {
		JanelaListagemEdicaoForum janelaListagem = null;
		try {
			janelaListagem = new JanelaListagemEdicaoForum(repositorioForum.getListaMeusForuns(usuario));
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		if (!janelaListagem.getCancelou()) {
			Forum forumSelecionado = janelaListagem.getForum();

			JanelaEdicaoForum janelaEdicao = null;
			try {
				janelaEdicao = new JanelaEdicaoForum(forumSelecionado, repositorioGenero.getGeneros(), usuario);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			if (!janelaEdicao.getCancelou()) {
				Forum forumAlterado = janelaEdicao.getForum();

				try {
					try {
						repositorioForum.alterar(forumAlterado);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "Fórum alterado com sucesso!");
				} catch (ErroAlteracaoException e) {
					viewPrincipal.print("Exceção capturada: " + e.getMessage());
					JOptionPane.showMessageDialog(null,
							"Não foi possível alterar o fórum!\nTente novamente mais tarde.");
				}
			}
		}
	}

	public void excluir(Usuario usuario) {
		JanelaListagemExclusaoForum janela = null;
		try {
			janela = new JanelaListagemExclusaoForum(repositorioForum.getListaMeusForuns(usuario));
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		Forum forumSelecionado = janela.getForum();

		if (!janela.getCancelou()) {
			int escolha = JOptionPane.showConfirmDialog(null,
					"Você tem certeza que deseja excluir o fórum selecionado permanentemente?", "Excluir Fórum",
					JOptionPane.YES_NO_OPTION);

			if (escolha == JOptionPane.YES_OPTION) {
				try {
					try {
						repositorioForum.excluir(forumSelecionado.getIdForum());
					} catch (SQLException e) {
						e.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "O fórum selecionado foi excluído!");
				} catch (ErroExcluirException e) {
					viewPrincipal.print("Exceção capturada: " + e.getMessage());
					JOptionPane.showMessageDialog(null,
							"Não foi possível excluir o fórum selecionado!\nTente novamente mais tarde.");
				}
			}
		}
	}

	public void listarTodos() {
		List<Forum> foruns;
		try {
			foruns = repositorioForum.getListaTodosForuns();
			JanelaListagemForum janela = new JanelaListagemForum(foruns, "de Todos os");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void listarMeus(Usuario usuario) {
		List<Forum> foruns;
		try {
			foruns = repositorioForum.getListaMeusForuns(usuario);
			JanelaListagemForum janela = new JanelaListagemForum(foruns, "dos Meus");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void listarForunsSeguidos(Usuario usuario) {
		List<Forum> foruns;
		try {
			foruns = repositorioForum.getListaForunsSeguidos(usuario);
			JanelaListagemForum janela = new JanelaListagemForum(foruns, "dos Seguidos");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void seguir(Usuario usuario) {
		JanelaListagemSeguirForum janela = null;
		try {
			janela = new JanelaListagemSeguirForum(repositorioForum.getListaForunsNaoSeguidos(usuario));
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		Forum forumSelecionado = janela.getForum();

		if (!janela.getCancelou()) {
			try {
				repositorioForum.seguir(usuario, forumSelecionado.getIdForum());
				JOptionPane.showMessageDialog(null, "O fórum selecionado foi seguido!");
			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Não foi possível seguir o fórum selecionado!");

			}
		}
	}

	public void pararDeSeguirForum(Usuario usuario) {
		JanelaListagemPararDeSeguirForum janela = null;
		try {
			janela = new JanelaListagemPararDeSeguirForum(repositorioForum.getListaForunsSeguidos(usuario));
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		Forum forumSelecionado = janela.getForum();

		if (!janela.getCancelou()) {
			try {
				repositorioForum.pararDeSeguir(usuario, forumSelecionado.getIdForum());
				JOptionPane.showMessageDialog(null, "O fórum selecionado foi seguido!");
			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Não foi possível seguir o fórum selecionado!");

			}
		}
	}

}
