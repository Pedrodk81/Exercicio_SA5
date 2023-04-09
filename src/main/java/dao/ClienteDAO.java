package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Cliente;

/**
 *
 * Esta classe DAO permite operações de inserção, leitura, atualização e
 * exclusão na tabela de 'usuario' do banco de dados.
 *
 */

public class ClienteDAO {
	// URL de conexão do banco de dados 'jdbc_servlet' do SGBD MySQL
	private String jdbcURL = "jdbc:mysql://localhost/shoeshapyy?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false";
	// Nome de usuário 'root' para acesso ao banco de dados do SGBD MySQL
	private String jdbcNomeUsuario = "root";
	// Senha do usuário 'root' para acesso ao banco de dados do SGBD MySQL
	private String jdbcSenha = "123456";

	private static final String INSERIR_USUARIO = "INSERT INTO cliente" + " (nome, endereco, modalidade) VALUES "
			+ " (?, ?, ?);";
	private static final String SELECIONAR_USUARIO = "SELECT matricula, nome, endereco, modalidade FROM cliente WHERE matricula = ?";
	private static final String SELECIONAR_USUARIOS = "SELECT * FROM cliente";
	private static final String DELETAR_USUARIO = "DELETE FROM cliente WHERE matricula = ?;";
	private static final String ATUALIZAR_USUARIO = "UPDATE cliente SET nome = ?, endereco = ? , modalidade = ? WHERE matricula = ?;";

	public ClienteDAO() {
	}

	protected Connection getConnection() {
		Connection conexao = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexao = DriverManager.getConnection(jdbcURL, jdbcNomeUsuario, jdbcSenha);
		} catch (SQLException erro) {
			erro.printStackTrace();
		} catch (ClassNotFoundException erro) {
			erro.printStackTrace();
		}
		return conexao;
	}

	public void inserirUsuario(Cliente cliente) throws SQLException {
		// Fecha automaticamente a conexão após o uso
		try (Connection conexao = getConnection();
				PreparedStatement executarComando = conexao.prepareStatement(INSERIR_USUARIO)) {
			// O codigo do usuário é omitido do comando, pois foi definido na tabela como
			// autoincremento
			executarComando.setString(1, cliente.getNome());
			executarComando.setString(2, cliente.getEndereco());
			executarComando.setString(3, cliente.getModalidade());
			System.out.println(executarComando);
			executarComando.executeUpdate();
		} catch (SQLException erro) {
			printSQLException(erro);
		}
	}

	public Cliente selecionarUsuario(int matricula) {
		Cliente usuario = null;
		// Etapa 1: estabelece a conexão
		try (Connection conexao = getConnection();
				// Etapa 2: cria o comando da instrução usando o objeto da conexão
				PreparedStatement executarComando = conexao.prepareStatement(SELECIONAR_USUARIO);) {
			executarComando.setInt(1, matricula);
			System.out.println(executarComando);
			// Etapa 3: executa ou atualiza a query
			ResultSet resultado = executarComando.executeQuery();
			// Etapa 4: processa o objeto ResultSet
			while (resultado.next()) {
				String nome = resultado.getString("nome");
				String endereco = resultado.getString("endereco");
				String modalidade = resultado.getString("modalidade");
				usuario = new Cliente(matricula, nome, endereco, modalidade);
			}
		} catch (SQLException erro) {
			printSQLException(erro);
		}
		return usuario;
	}

	public List selecionarUsuarios() {
		List usuarios = new ArrayList<>();
		// Código boilerplate
		// Etapa 1: estabelece a conexão
		try (Connection conexao = getConnection();
				// Etapa 2: cria o comando da instrução usando o objeto da conexão
				PreparedStatement executarComando = conexao.prepareStatement(SELECIONAR_USUARIOS);) {
			System.out.println(executarComando);
			// Etapa 3: executa ou atualiza a query
			ResultSet resultado = executarComando.executeQuery();
			// Etapa 4: processa o objeto ResultSet
			while (resultado.next()) {
				int matricula = resultado.getInt("matricula");
				String nome = resultado.getString("nome");
				String endereco = resultado.getString("endereco");
				String modalidade = resultado.getString("modalidade");
				usuarios.add(new Cliente(matricula, nome, endereco, modalidade));
			}
		} catch (SQLException erro) {
			printSQLException(erro);
		}
		return usuarios;
	}

	public boolean deletarUsuario(int matricula) throws SQLException {
		boolean registroDeletado;
		try (Connection conexao = getConnection();
				PreparedStatement executarComando = conexao.prepareStatement(DELETAR_USUARIO);) {
			executarComando.setInt(1, matricula);
			System.out.println(executarComando);
			registroDeletado = executarComando.executeUpdate() > 0;
		}
		return registroDeletado;
	}

	public boolean atualizarUsuario(Cliente cliente) throws SQLException {
		boolean registroAtualizado;
		try (Connection connection = getConnection();
				PreparedStatement executarComando = connection.prepareStatement(ATUALIZAR_USUARIO);) {
			executarComando.setString(1, cliente.getNome());
			executarComando.setString(2, cliente.getEndereco());
			executarComando.setString(3, cliente.getModalidade());
			executarComando.setInt(4, cliente.getMatricula());
			registroAtualizado = executarComando.executeUpdate() > 0;
		}
		return registroAtualizado;
	}

	private void printSQLException(SQLException erro) {
		for (Throwable e : erro) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("Estado do SQL: " + ((SQLException) e).getSQLState());
				System.err.println("Código de erro: " + ((SQLException) e).getErrorCode());
				System.err.println("Mensagem: " + e.getMessage());
				Throwable causa = erro.getCause();
				while (causa != null) {
					System.out.println("Causa: " + causa);
					causa = causa.getCause();
				}
			}
		}
	}
}