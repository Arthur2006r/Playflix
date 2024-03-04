package sistema.view;

import sistema.model.Usuario;

public class ViewPrincipal {
	public void print(String mensagem) {
		System.out.println(mensagem);
	}

	public int menuLoginCadastro() {
		JanelaMenuInicial janela = new JanelaMenuInicial();
		return janela.getOpcao();
	}

	public int menuUsuarioOpcoesInicial(Usuario usuario) {
		JanelaMenuUsuarioInicial janela = new JanelaMenuUsuarioInicial(usuario);
		return usuario.getIdUsuario() == 0 ? 5 : janela.getOpcao();
	}

	public int menuUsuarioGerenciadores(Usuario usuario) {
		JanelaMenuUsuarioGerenciadores janela = new JanelaMenuUsuarioGerenciadores(usuario);
		return usuario.getIdUsuario() == 0 ? 5 : janela.getOpcao();
	}
	
	public int menuUsuarioForum(Usuario usuario) {
		JanelaMenuUsuarioGerenciarForuns janela = new JanelaMenuUsuarioGerenciarForuns(usuario);
		return usuario.getIdUsuario() == 0 ? 7 : janela.getOpcao();
	}

	public int menuUsuarioJogo(Usuario usuario) {
		JanelaMenuUsuarioGerenciadorJogos janela = new JanelaMenuUsuarioGerenciadorJogos(usuario);
		return usuario.getIdUsuario() == 0 ? 7 : janela.getOpcao();
	}
	
	public int menuUsuarioGenero(Usuario usuario) {
		JanelaMenuUsuarioGerenciadorGeneros janela = new JanelaMenuUsuarioGerenciadorGeneros(usuario);
		return usuario.getIdUsuario() == 0 ? 7 : janela.getOpcao();
	}

	public int menuUsuarioSeguidasForuns(Usuario usuario) {
		JanelaMenuUsuarioGerenciadorSeguidasForuns janela = new JanelaMenuUsuarioGerenciadorSeguidasForuns(usuario);
		return usuario.getIdUsuario() == 0 ? 5 : janela.getOpcao();
	}
}
