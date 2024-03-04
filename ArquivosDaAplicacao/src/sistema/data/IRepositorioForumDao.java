package sistema.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import sistema.model.Forum;
import sistema.model.Usuario;

public interface IRepositorioForumDao {	
	public Connection getConnection();
	
	public List<Forum> getListaTodosForuns() throws SQLException;
	
	public List<Forum> getListaMeusForuns(Usuario usuario) throws SQLException;
	
	public List<Forum> getListaForunsSeguidos(Usuario usuario) throws SQLException;

	public void inserir(Forum forum) throws SQLException;

	public void alterar(Forum forum) throws SQLException;

	public void excluir(int idForum) throws SQLException;
	
	public void excluirForunsUsuario(Usuario usuario) throws SQLException;

	public void seguir(Usuario usuario, int idForum) throws SQLException;

	public void pararDeSeguir(Usuario usuario, int idForum) throws SQLException;

	public List<Forum> getListaForunsNaoSeguidos(Usuario usuario) throws SQLException;
}
