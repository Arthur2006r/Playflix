package sistema.control;

import sistema.data.IRepositorioForumDao;
import sistema.data.IRepositorioGeneroDao;
import sistema.data.IRepositorioJogoDao;
import sistema.data.IRepositorioUsuarioDao;
import sistema.model.Usuario;
import sistema.view.ViewPrincipal;

public class Sistema {
	private static Sistema instance;

	private ControleUsuario controleUsuario;
	private ControleForum controleForum;
	private ControleGenero controleGenero;
	private ControleJogo controleJogo;

	private ViewPrincipal viewPrincipal;

	// CONSTRUTOR //
	private Sistema(IRepositorioUsuarioDao repositorioUsuario, IRepositorioForumDao repositorioForum,
			IRepositorioGeneroDao repositorioGenero, IRepositorioJogoDao repositorioJogo) {
		this.controleUsuario = new ControleUsuario(repositorioUsuario, repositorioForum);
		this.controleForum = new ControleForum(repositorioForum, repositorioGenero);
		this.controleGenero = new ControleGenero(repositorioGenero);
		this.controleJogo = new ControleJogo(repositorioJogo, repositorioGenero);

		this.viewPrincipal = new ViewPrincipal();
	}

	private Sistema() {
	}
	// *** //

	// MÉTODO FÁBRICA //
	public static Sistema getInstance(IRepositorioUsuarioDao repositorioUsuario, IRepositorioForumDao repositorioForum,
			IRepositorioGeneroDao repositorioGenero, IRepositorioJogoDao repositorioJogo) {
		if (instance == null && repositorioForum != null && repositorioForum != null && repositorioGenero != null) {
			instance = new Sistema(repositorioUsuario, repositorioForum, repositorioGenero, repositorioJogo);
		}

		return instance;
	}

	public static Sistema getInstance() {
		if (instance == null) {
			instance = new Sistema();
		}

		return instance;
	}
	// *** //

	// USUÁRIO //
	public void logarUsuario() {
		controleUsuario.logar();
	}

	public void cadastrarUsuario() {
		controleUsuario.cadastrar();
	}

	public void alterarUsuario(Usuario usuario) {
		controleUsuario.alterar(usuario);
	}

	public void excluirUsuario(Usuario usuario) {
		controleUsuario.excluir(usuario);
	}

	public void listarUsuarios() {
		controleUsuario.listar();
	}
	// *** //

	// FÓRUM //
	public void cadastrarForum(Usuario usuario) {
		controleForum.cadastrar(usuario);
	}

	public void alterarForum(Usuario usuario) {
		controleForum.alterar(usuario);
	}

	public void excluirForum(Usuario usuario) {
		controleForum.excluir(usuario);
	}

	public void listarTodosForuns() {
		controleForum.listarTodos();
	}

	public void listarMeusForuns(Usuario usuario) {
	 	controleForum.listarMeus(usuario);
	}
	// *** //

	// JOGO //
	public void cadastrarJogo(Usuario usuario) {
		controleJogo.cadastrar(usuario);
	}

	public void alterarJogo(Usuario usuario) {
		controleJogo.alterar(usuario);
	}

	public void excluirJogo(Usuario usuario) {
		controleJogo.excluir(usuario);
	}

	public void listarTodosJogos() {
		controleJogo.listarTodos();
	}

	public void listarMeusJogos(Usuario usuario) {
		controleJogo.listarMeus(usuario);
	}
	// *** //

	// GÊNERO //
	public void cadastrarGenero(Usuario usuario) {
		controleGenero.cadastrar(usuario);
	}

	public void alterarGenero(Usuario usuario) {
		controleGenero.alterar(usuario);
	}

	public void excluirGenero(Usuario usuario) {
		controleGenero.excluir(usuario);
	}

	public void listarTodosGeneros() {
		controleGenero.listarTodos();
	}

	public void listarMeusGeneros(Usuario usuario) {
		controleGenero.listarMeus(usuario);
	}
	// *** //
	
	// SEGUIDAS FORUNS //
	public void seguirForum(Usuario usuario) {
		controleForum.seguir(usuario);
	}
	
	public void pararDeSeguirForum(Usuario usuario) {
		controleForum.pararDeSeguirForum(usuario);
	}
	
	public void listarForunsSeguidos(Usuario usuario) {
		controleForum.listarForunsSeguidos(usuario);
	}
	// *** //

	// SWITCHS //

	// menu em que ocorre o login do usuário em algum perfil ou o cadastro de um
	// perfil
	public void menuLoginCadastro() {
		int opcao;
		do {
			opcao = viewPrincipal.menuLoginCadastro();

			switch (opcao) {
			// caso o usuário deseje logar em algum perfil
			case 1:
				logarUsuario();
				break;
			// caso o usuário deseje cadastrar algum perfil (Usuário)
			case 2:
				cadastrarUsuario();
				break;
			// caso o usuário deseje fechar o programa
			case 3:
				return;
			default:
				throw new IllegalArgumentException("Opção inválida: " + opcao);
			}
		} while (opcao != 0);
	}

	// menu inicial do usuario ao ele logar em algum perfil
	public void menuUsuarioOpcoesInicial(Usuario usuario) {
		int opcao = 0;
		do {
			opcao = usuario.getIdUsuario() != 0 ? viewPrincipal.menuUsuarioOpcoesInicial(usuario) : 5;

			switch (opcao) {
			// caso o usuário deseje alterar sua própria conta
			case 1:
				alterarUsuario(usuario);
				break;
			// caso o usuário deseje listar todos os usuarios
			case 2:
				listarUsuarios();
				break;
			case 3:
				menuUsuarioGerenciadores(usuario);
				break;
			// caso o usuário deseje excluir sua própria conta
			case 4:
				excluirUsuario(usuario);
				break;
			// caso o usuário deseje deslogar
			case 5:
				return;
			default:
				throw new IllegalArgumentException("Opção inválida: " + opcao);
			}
		} while (opcao != 0);
	}

	public void menuUsuarioGerenciadores(Usuario usuario) {
		int opcao = 0;
		do {
			opcao = usuario.getIdUsuario() != 0 ? viewPrincipal.menuUsuarioGerenciadores(usuario) : 6;

			switch (opcao) {
			// caso o usuário deseje gerenciar foruns
			case 1:
				menuUsuarioForum(usuario);
				break;
			// caso o usuário deseje gerenciar jogos
			case 2:
				menuUsuarioJogo(usuario);
				break;
			// caso o usuário deseje gerenciar generos
			case 3:
				menuUsuarioGenero(usuario);
				break;
			// caso o usuário deseje as seguidas nos fóruns
			case 4:
				menuUsuarioSeguidasForuns(usuario);
				break;
			// caso o usuário deseje voltar para o menu de opcoes inicial do Usuário
			case 5:
				return;
			// caso o usuário deseje deslogar
			case 6:
				return;
			default:
				throw new IllegalArgumentException("Opção inválida: " + opcao);
			}
		} while (opcao != 0);
	}

	// menu do usuario em que há as opcoes relacionadas ao forum
	public void menuUsuarioForum(Usuario usuario) {
		int opcao = 0;
		do {
			opcao = viewPrincipal.menuUsuarioForum(usuario);

			switch (opcao) {
			// caso o usuário deseje listar seus prórpios foruns
			case 1:
				listarMeusForuns(usuario);
				break;
			// caso o usuário deseje listar todos os foruns
			case 2:
				listarTodosForuns();
				break;
			// caso o usuário deseje cadastrar um fórum
			case 3:
				cadastrarForum(usuario);
				break;
			// caso o usuário deseje alterar algum de seus foruns
			case 4:
				alterarForum(usuario);
				break;
			// caso o usuário deseje excluir algum de seus foruns
			case 5:
				excluirForum(usuario);
				break;
			// caso o usuário deseje voltar para o menu de opcoes inicial do Usuário
			case 6:
				return;
			// caso o usuário deseje deslogar
			case 7:
				return;
			default:
				throw new IllegalArgumentException("Opção inválida: " + opcao);
			}
		} while (opcao != 0);
	}

	public void menuUsuarioJogo(Usuario usuario) {
		int opcao = 0;
		do {
			opcao = viewPrincipal.menuUsuarioJogo(usuario);

			switch (opcao) {
			// caso o usuário deseje listar seus prórpios jogos
			case 1:
				listarMeusJogos(usuario);
				break;
			// caso o usuário deseje listar todos os jogoss
			case 2:
				listarTodosJogos();
				break;
			// caso o usuário deseje cadastrar um jogo
			case 3:
				cadastrarJogo(usuario);
				break;
			// caso o usuário deseje alterar algum de seus jogos
			case 4:
				alterarJogo(usuario);
				break;
			// caso o usuário deseje excluir algum de seus jogos
			case 5:
				excluirJogo(usuario);
				break;
			// caso o usuário deseje voltar para o menu anterior
			case 6:
				return;
			// caso o usuário deseje deslogar
			case 7:
				return;
			default:
				throw new IllegalArgumentException("Opção inválida: " + opcao);
			}
		} while (opcao != 0);
	}

	public void menuUsuarioGenero(Usuario usuario) {
		int opcao = 0;
		do {
			opcao = viewPrincipal.menuUsuarioGenero(usuario);

			switch (opcao) {
			// caso o usuário deseje listar seus prórpios generos
			case 1:
				listarMeusGeneros(usuario);
				break;
			// caso o usuário deseje listar todos os generos
			case 2:
				listarTodosGeneros();
				break;
			// caso o usuário deseje cadastrar um genero
			case 3:
				cadastrarGenero(usuario);
				break;
			// caso o usuário deseje alterar algum de seus generos
			case 4:
				alterarGenero(usuario);
				break;
			// caso o usuário deseje excluir algum de seus generos
			case 5:
				excluirGenero(usuario);
				break;
			// caso o usuário deseje voltar para o menu anterior
			case 6:
				return;
			// caso o usuário deseje deslogar
			case 7:
				return;
			default:
				throw new IllegalArgumentException("Opção inválida: " + opcao);
			}
		} while (opcao != 0);
	}
	
	private void menuUsuarioSeguidasForuns(Usuario usuario) {
		int opcao = 0;
		do {
			opcao = viewPrincipal.menuUsuarioSeguidasForuns(usuario);

			switch (opcao) {
			// caso o usuário deseje listar os fóruns que ele segue
			case 1:
				listarForunsSeguidos(usuario);
				break;
			// caso o usuário deseje seguir um fórum
			case 2:
				seguirForum(usuario);
				break;
			// caso o usuário deseje parar de seguir um fórum
			case 3:
				pararDeSeguirForum(usuario);
				break;
			// caso o usuário deseje voltar para o menu anterior
			case 4:
				return;
			// caso o usuário deseje deslogar
			case 5:
				return;
			default:
				throw new IllegalArgumentException("Opção inválida: " + opcao);
			}
		} while (opcao != 0);
	}
	// *** //
}
