package sistema.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sistema.model.Forum;
import sistema.model.Genero;
import sistema.model.Usuario;

public class RepositorioForumDao implements IRepositorioForumDao {
	private Connection connection;

	private IRepositorioUsuarioDao repositorioUsuario;
	private IRepositorioGeneroDao repositorioGenero;

	public RepositorioForumDao(IRepositorioUsuarioDao repositorioUsuario, IRepositorioGeneroDao repositorioGenero) {
		this.connection = new ConnectionFactory().getConnection();

		this.repositorioUsuario = repositorioUsuario;
		this.repositorioGenero = repositorioGenero;
	}

	@Override
	public Connection getConnection() {
		return this.connection;
	}

	@Override
	public List<Forum> getListaTodosForuns() throws SQLException {
		String sql = "select * from Forum";
		PreparedStatement stmt = connection.prepareStatement(sql);

		ResultSet rs = stmt.executeQuery();

		List<Forum> copiaForuns = new ArrayList<>();

		while (rs.next()) {
			Usuario lider = repositorioUsuario.getUsuario(rs.getInt("idUsuario"));
			Genero genero = repositorioGenero.getGenero(rs.getInt("idGenero"));

			Forum forum = Forum.getInstance(rs.getString("faixaEtaria"), lider, rs.getString("nome"),
					rs.getString("descricao"), genero);
			forum.setIdForum(rs.getInt("idForum"));

			copiaForuns.add(forum);
		}

		return copiaForuns;
	}

	@Override
	public List<Forum> getListaMeusForuns(Usuario usuario) throws SQLException {
		List<Forum> copiaMeusForuns = new ArrayList<>();

		for (Forum fo : getListaTodosForuns()) {
			if (fo.getLider().getIdUsuario() == usuario.getIdUsuario()) {
				try {
					copiaMeusForuns.add((Forum) fo.clone());
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
			}

		}

		return copiaMeusForuns;
	}

	@Override
	public List<Forum> getListaForunsSeguidos(Usuario usuario) throws SQLException {
		String sql = "SELECT F.* " + "FROM Forum F " + "JOIN UsuarioForum UF ON F.idForum = UF.idForum "
				+ "WHERE UF.idUsuario = ?";

		List<Forum> copiaForunsSeguidos = new ArrayList<>();

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, usuario.getIdUsuario());

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Usuario lider = repositorioUsuario.getUsuario(rs.getInt("idUsuario"));
					Genero genero = repositorioGenero.getGenero(rs.getInt("idGenero"));

					Forum forum = Forum.getInstance(rs.getString("faixaEtaria"), lider, rs.getString("nome"),
							rs.getString("descricao"), genero);
					forum.setIdForum(rs.getInt("idForum"));

					copiaForunsSeguidos.add(forum);
				}
			}
		}

		return copiaForunsSeguidos;
	}

	@Override
	public void inserir(Forum forum) throws SQLException {
		if (forum != null) {
			if (!isNomeRepetido(forum)) {
				String sql = "insert into Forum " + "(nome, descricao, faixaEtaria, dataCriacao, idUsuario, idGenero) "
						+ "values (?,?,?,?,?,?)";

				PreparedStatement stmt = connection.prepareStatement(sql);
				stmt.setString(1, forum.getNome());
				stmt.setString(2, forum.getDescricao());
				stmt.setString(3, forum.getFaixaEtaria());
				stmt.setDate(4, new java.sql.Date(forum.getDataCriacao().getTime()));
				stmt.setInt(5, forum.getLider().getIdUsuario());
				stmt.setInt(6, forum.getGenero().getIdGenero());

				stmt.execute();
				stmt.close();
			} else {
				throw new ErroInserirException(
						"Não foi possível inserir o fórum no reposiótio! Não é permitido foruns com o nome igual. Tente novamente mais tarde.");
			}
		} else {
			throw new ErroInserirException(
					"Não foi possível inserir o fórum no reposiótio! O objeto fórum foi passado como nulo. Tente novamente mais tarde.");
		}
	}

	@Override
	public void alterar(Forum forum) throws SQLException {
		if (forum != null) {
			if (!isNomeRepetido(forum)) {
				String sql = "update Forum set nome=?, descricao=?, faixaEtaria=?, idGenero=? where idForum=?";
				PreparedStatement stmt = connection.prepareStatement(sql);
				stmt.setString(1, forum.getNome());
				stmt.setString(2, forum.getDescricao());
				stmt.setString(3, forum.getFaixaEtaria());
				stmt.setInt(4, forum.getGenero().getIdGenero());
				stmt.setInt(5, forum.getIdForum());

				stmt.execute();
				stmt.close();
			} else {
				throw new ErroAlteracaoException(
						"Não foi possível alterar o fórum no repositório! Não é permitido foruns com o nome igual. Tente novamente mais tarde.");
			}
		} else {
			throw new ErroInserirException(
					"Não foi possível alterar o fórum no repositório! O objeto fórum foi passado como nulo. Tente novamente mais tarde.");
		}
	}

	@Override
	public void excluir(int idForum) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("delete " + "from Forum where idForum=?");
		stmt.setLong(1, idForum);

		stmt.execute();
		stmt.close();
	}

	@Override
	public void excluirForunsUsuario(Usuario usuario) throws SQLException {
		if (usuario != null) {
			for (Forum fo : getListaTodosForuns()) {
				if (fo.getLider().getIdUsuario() == usuario.getIdUsuario()) {
					PreparedStatement stmt = connection.prepareStatement("delete " + "from Forum where idForum=?");
					stmt.setInt(1, fo.getIdForum());

					stmt.execute();
					stmt.close();
				}
			}
		} else {
			throw new ErroExcluirException(
					"Não foi possível excluir so fórum do usuário selecionado no reposiótio! Não foi encontrado o fórum que você deseja excluir. Tente novamente mais tarde.");
		}
	}

	public boolean isNomeRepetido(Forum forum) throws SQLException {
		if (forum != null) {
			for (Forum fo : getListaTodosForuns()) {
				if (fo.getNome().equals(forum.getNome()) && fo.getIdForum() != forum.getIdForum()) {
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public void seguir(Usuario usuario, int idForum) throws SQLException {
		String sql = "INSERT INTO UsuarioForum (idUsuario, idForum) VALUES (?, ?)";

		Connection connection = getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, usuario.getIdUsuario());
		preparedStatement.setInt(2, idForum);

		preparedStatement.executeUpdate();

	}

	@Override
	public void pararDeSeguir(Usuario usuario, int idForum) throws SQLException {
		String sql = "DELETE FROM UsuarioForum WHERE idUsuario = ? AND idForum = ?";
		Connection connection = getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, usuario.getIdUsuario());
		preparedStatement.setInt(2, idForum);

		preparedStatement.executeUpdate();
	}

	@Override
	public List<Forum> getListaForunsNaoSeguidos(Usuario usuario) throws SQLException {
		String sql = "SELECT * FROM Forum WHERE idForum NOT IN (SELECT idForum FROM UsuarioForum WHERE idUsuario = ?)";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setInt(1, usuario.getIdUsuario());

		ResultSet rs = stmt.executeQuery();

		List<Forum> copiaForuns = new ArrayList<>();

		while (rs.next()) {
			Usuario lider = repositorioUsuario.getUsuario(rs.getInt("idUsuario"));
			Genero genero = repositorioGenero.getGenero(rs.getInt("idGenero"));

			Forum forum = Forum.getInstance(rs.getString("faixaEtaria"), lider, rs.getString("nome"),
					rs.getString("descricao"), genero);
			forum.setIdForum(rs.getInt("idForum"));

			copiaForuns.add(forum);
		}

		return copiaForuns;
	}

}
