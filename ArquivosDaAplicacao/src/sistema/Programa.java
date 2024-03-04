package sistema;

import sistema.control.Sistema;
import sistema.data.IRepositorioForumDao;
import sistema.data.IRepositorioGeneroDao;
import sistema.data.IRepositorioJogoDao;
import sistema.data.IRepositorioUsuarioDao;
import sistema.data.RepositorioForumDao;
import sistema.data.RepositorioGeneroDao;
import sistema.data.RepositorioJogoDao;
import sistema.data.RepositorioUsuarioDao;

// NOME DOS INTEGRANTES DA DUPLA (T1) - DS2 :
// Arthur Andrade Reis

public class Programa {
	public static void main(String[] args) {
		IRepositorioUsuarioDao repositorioUsuario = new RepositorioUsuarioDao();
		IRepositorioGeneroDao repositorioGenero = new RepositorioGeneroDao(repositorioUsuario);
		IRepositorioForumDao repositorioForum = new RepositorioForumDao(repositorioUsuario, repositorioGenero);
		IRepositorioJogoDao repositorioJogo = new RepositorioJogoDao(repositorioUsuario, repositorioGenero);

		Sistema.getInstance(repositorioUsuario, repositorioForum, repositorioGenero, repositorioJogo).menuLoginCadastro();
	}

}
