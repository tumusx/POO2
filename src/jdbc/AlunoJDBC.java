package jdbc;

import java.io.IOException;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Aluno;

public class AlunoJDBC {

	static String sql;
	static PreparedStatement pst;

	public static void salvar(Aluno a, Connection con) throws IOException {

		try {

			sql = "INSERT INTO aluno (nome, sexo, dt_nasc) VALUES (?,  ?, ?)";

			pst = con.prepareStatement(sql);
			pst.setString(1, a.getNome());
			pst.setString(2, a.getSexo());

			Date dataSql = new Date(a.getDt_nasc().getTime());
			pst.setDate(3, dataSql);

			pst.executeUpdate();
			System.out.println("\nCadastro do aluno realizado com sucesso!");

		} catch (SQLException e) {

			System.out.println(e);
		}

	}

	public static List<Aluno> listar(Connection conextionDatabase) throws IOException {
		List<Aluno> alunoList = new ArrayList();

		try {
			sql = "SELECT * FROM aluno";
			pst = conextionDatabase.prepareStatement(sql);
			ResultSet resultSet = pst.executeQuery();
			Aluno aluno = new Aluno();
			while (resultSet.next()) {

				System.out.println(resultSet.getInt(1) + "|" + resultSet.getString(2) + "|" + resultSet.getString(3)
						+ "|" + resultSet.getDate("dt_Nasc"));
			}

			alunoList.add(aluno);

		} catch (SQLException error) {
			System.out.println("Erro de conexão. Tente novamente");

		} finally {
			System.out.println("finally process");
		}

		return alunoList;
	}

	public static void apagar(int id, Connection conextionDatabase) throws SQLException {

		sql = "DELETE FROM aluno WHERE id = ?";
		pst = conextionDatabase.prepareStatement(sql);
		pst.setInt(1, id);
		if (pst.executeUpdate() >= 1) {
			System.out.println("Aluno excluido");
		} else {
			System.out.println("Aluno nao excluido");
		}
	}

	public static void alterar(Aluno a, Connection conextionDatabase) throws SQLException {
		sql = "UPDATE aluno SET nome = ?, sexo= ?, dt_nasc=? WHERE id = ?";
		pst = conextionDatabase.prepareStatement(sql);
		pst.setString(1, a.getNome());
		pst.setString(2, a.getSexo());
		pst.setInt(4, a.getId());
		Date dataSql = new Date(a.getDt_nasc().getTime());
		pst.setDate(3, dataSql);

		if (pst.executeUpdate() == 1) {
			System.out.println("Alterado com sucesso");
		} else {
			System.out.println("Erro ao cadastrar aluno");
		}
	}
}
