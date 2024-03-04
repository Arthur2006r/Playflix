package sistema.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import sistema.model.Jogo;
import sistema.model.Usuario;

public interface IRepositorioJogoDao {
	public Connection getConnection();
	
	public List<Jogo> getJogos() throws SQLException;
	
	public Jogo getJogo(int idJogo) throws SQLException;
	
	public void inserir(Jogo jogo) throws SQLException;
	
	public void alterar(Jogo jogo) throws SQLException;
	
	public void excluir(int idJogo) throws SQLException;
		
	public boolean isNomeRepetido(Jogo jogo) throws SQLException;

	List<Jogo> getListaMeusJogos(Usuario usuario) throws SQLException;
}
