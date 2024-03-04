package sistema.control;

import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

import sistema.data.ErroAlteracaoException;
import sistema.data.ErroExcluirException;
import sistema.data.IRepositorioForumDao;
import sistema.data.IRepositorioUsuarioDao;
import sistema.model.Usuario;
import sistema.view.JanelaCadastroUsuario;
import sistema.view.JanelaEdicaoUsuario;
import sistema.view.JanelaListagemUsuario;
import sistema.view.JanelaLogin;
import sistema.view.ViewPrincipal;

public class ControleUsuario {
	private ViewPrincipal viewPrincipal;
	private IRepositorioUsuarioDao repositorioUsuario;
	private IRepositorioForumDao repositorioForum;

	public ControleUsuario(IRepositorioUsuarioDao repositorioUsuario, IRepositorioForumDao repositorioForum) {
		this.viewPrincipal = new ViewPrincipal();
		this.repositorioUsuario = repositorioUsuario;
		this.repositorioForum = repositorioForum;
	}

	public void logar() {
		JanelaLogin janela = new JanelaLogin();
		if (!janela.getCancelou()) {
			String[] nicknameSenha = janela.getNicknameSenha();
			Usuario usuario = null;
			try {
				usuario = repositorioUsuario.logar(nicknameSenha);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			if (usuario != null) {
				JOptionPane.showMessageDialog(null, "Login feito com sucesso!\nBem-vindo.");
				Sistema.getInstance().menuUsuarioOpcoesInicial(usuario);
			} else {
				JOptionPane.showMessageDialog(null,
						"Não encontramos um usuário com esses dados.\nPor favor, tente novamente mais tarde!");
			}
		}

	}

	public void cadastrar() {
		JanelaCadastroUsuario janela = new JanelaCadastroUsuario();
		if (!janela.getCancelou()) {
			Usuario usuarioCriado = janela.getUsuario();

			try {
				repositorioUsuario.inserir(usuarioCriado);
				JOptionPane.showMessageDialog(null, "Usuário criado com sucesso!\nAgora faça login nesse usuário!");
			} catch (Exception e) {
				viewPrincipal.print("Exceção capturada: " + e.getMessage());
				JOptionPane.showMessageDialog(null,
						"Não foi possível cadastrar o usuário!\nTente novamente mais tarde.");
			}
		}
	}

	public void alterar(Usuario usuario) {
		JanelaEdicaoUsuario janela = new JanelaEdicaoUsuario(usuario);

		if (!janela.getCancelou()) {
			Usuario usuarioAlterado = janela.getUsuarioEditado();

			try {
				try {
					repositorioUsuario.alterar(usuarioAlterado);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "Usuário alterado com sucesso!");
				refreshUsuario(usuario);
			} catch (ErroAlteracaoException e) {
				viewPrincipal.print("Exceção capturada: " + e.getMessage());
				JOptionPane.showMessageDialog(null, "Não foi possível alterar o usuário!\nTente novamente mais tarde.");
			}
		}
	}

	private void refreshUsuario(Usuario usuario) {
		Usuario usuarioAtualizado = null;
		try {
			usuarioAtualizado = repositorioUsuario.getUsuario(usuario.getIdUsuario());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		usuario.setEmail(usuarioAtualizado.getEmail());
		usuario.setSenha(usuarioAtualizado.getSenha());
	}

	public void excluir(Usuario usuario) {
		int escolha = JOptionPane.showConfirmDialog(null,
				"Você tem certeza que deseja excluir sua conta permanentemente?\n(seus foruns também são excluídos)",
				"Excluir conta", JOptionPane.YES_NO_OPTION);

		if (escolha == JOptionPane.YES_OPTION) {

			try {
				int tamanho = 0;
				try {
					tamanho = repositorioForum.getListaMeusForuns(usuario).size();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				for (int i = 0; i < tamanho; i++) {
					try {
						repositorioForum.excluirForunsUsuario(usuario);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				try {
					repositorioUsuario.excluir(usuario.getIdUsuario());
				} catch (SQLException e) {
					e.printStackTrace();
				}
				usuario.setIdUsuario(0);
				JOptionPane.showMessageDialog(null,
						"Sua conta foi excluída! Você será redirecionado para o menu inicial...");
			} catch (ErroExcluirException e) {
				viewPrincipal.print("Exceção capturada: " + e.getMessage());
				JOptionPane.showMessageDialog(null, "Não foi possível excluir o usuário!\nTente novamente mais tarde.");
			}
		}
	}

	public void listar() {
		List<Usuario> usuarios;
		try {
			usuarios = repositorioUsuario.getUsuarios();
			JanelaListagemUsuario janela = new JanelaListagemUsuario(usuarios);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
