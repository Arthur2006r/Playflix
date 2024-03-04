package sistema.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import sistema.model.Usuario;

public interface IRepositorioUsuarioDao {
	public Connection getConnection();
	
	public List<Usuario> getUsuarios() throws SQLException;
	
	public void inserir(Usuario usuario) throws SQLException;
	
	public void alterar(Usuario usuario) throws SQLException;
	
	public void excluir(int idUsuario) throws SQLException;
	
	public Usuario logar(String[] emailSenha) throws SQLException;
	
	public boolean isNicknameRepetido(Usuario usuario) throws SQLException;

	public Usuario getUsuario(int idUsuario) throws SQLException;
}
