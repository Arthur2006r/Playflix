package sistema.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sistema.model.Usuario;

public class RepositorioUsuarioDao implements IRepositorioUsuarioDao {
	private Connection connection;

	public RepositorioUsuarioDao() {
		this.connection = new ConnectionFactory().getConnection();
	}

	public RepositorioUsuarioDao(Connection connection) {
		this.connection = connection;
	}

	public Connection getConnection() {
		return this.connection;
	}

	@Override
	public List<Usuario> getUsuarios() throws SQLException {
		String sql = "select * from Usuario";
		PreparedStatement stmt = connection.prepareStatement(sql);

		ResultSet rs = stmt.executeQuery();

		List<Usuario> copiaUsuarios = new ArrayList<>();

		while (rs.next()) {
			Usuario usuario = Usuario.getInstance(rs.getString("senha"), rs.getString("nickname"),
					rs.getString("email"));
			usuario.setIdUsuario(rs.getInt("idUsuario"));

			copiaUsuarios.add(usuario);
		}

		stmt.close();

		return copiaUsuarios;
	}

	public Usuario getUsuario(int idUsuario) throws SQLException {
		String sql = "select * from Usuario where idUsuario=?";

		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setInt(1, idUsuario);

		ResultSet rs = stmt.executeQuery();

		Usuario usuario = null;

		if (rs.next()) {
			usuario = Usuario.getInstance(rs.getString("senha"), rs.getString("nickname"), rs.getString("email"));
			usuario.setIdUsuario(rs.getInt("idUsuario"));
		}

		stmt.close();

		return usuario;
	}

	@Override
	public void inserir(Usuario usuario) throws SQLException {
		if (usuario != null) {
			if (!isNicknameRepetido(usuario)) {
				String sql = "insert into Usuario " + "(senha, nickname, email) " + "values (?,?,?)";

				PreparedStatement stmt = connection.prepareStatement(sql);
				stmt.setString(1, usuario.getSenha());
				stmt.setString(2, usuario.getNickname());
				stmt.setString(3, usuario.getEmail());

				stmt.execute();
				stmt.close();
			} else {
				throw new ErroInserirException(
						"Não foi possível inserir o usuário no reposiótio! Não é permitido usuarios com o nickname igual. Tente novamente mais tarde.");
			}
		} else {
			throw new ErroInserirException(
					"Não foi possível inserir o usuário no reposiótio! O objeto usuário foi passado como nulo. Tente novamente mais tarde.");
		}
	}

	@Override
	public void alterar(Usuario usuario) throws SQLException {
		if (usuario != null) {
			if (!isNicknameRepetido(usuario)) {
				String sql = "update Usuario set senha=?, nickname=?, email=? where idUsuario=?";
				PreparedStatement stmt = connection.prepareStatement(sql);
				stmt.setString(1, usuario.getSenha());
				stmt.setString(2, usuario.getNickname());
				stmt.setString(3, usuario.getEmail());
				stmt.setInt(4, usuario.getIdUsuario());

				stmt.execute();
				stmt.close();
			} else {
				throw new ErroAlteracaoException(
						"Não foi possível alterar o usuário no repositório! Não é permitido usuários com o nickname igual. Tente novamente mais tarde.");
			}
		} else {
			throw new ErroInserirException(
					"Não foi possível alterar o usuário no repositório! O objeto usuário foi passado como nulo. Tente novamente mais tarde.");
		}
	}

	@Override
	public void excluir(int idUsuario) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("delete " + "from Usuario where idUsuario=?");
		stmt.setLong(1, idUsuario);

		stmt.execute();
		stmt.close();
	}

	public Usuario logar(String[] nicknameSenha) throws SQLException {
		if (nicknameSenha != null) {
			if (nicknameSenha[0] != null && nicknameSenha[1] != null) {
				for (Usuario usu : getUsuarios()) {
					if (usu.getNickname().equals(nicknameSenha[0]) && usu.getSenha().equals(nicknameSenha[1])) {
						try {
							return (Usuario) usu.clone();
						} catch (CloneNotSupportedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}

		return null;
	}

	public boolean isNicknameRepetido(Usuario usuario) throws SQLException {
		if (usuario != null) {
			for (Usuario usu : getUsuarios()) {
				if (usu.getNickname().equals(usuario.getNickname()) && usu.getIdUsuario() != usuario.getIdUsuario()) {
					return true;
				}
			}
		}

		return false;
	}
}
