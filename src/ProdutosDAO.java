import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ProdutosDAO {

    private Connection conn;
    private PreparedStatement prep;
    private ResultSet resultset;

  
    public void cadastrarProduto(ProdutosDTO produto) {
        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";

        try {
            conn = new ConexaoDAO().connectDB(); // Conecta ao banco
            prep = conn.prepareStatement(sql);
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());

            prep.executeUpdate();
            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto: " + e.getMessage());
        } finally {
            try {
                if (prep != null) prep.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
              
            }
        }
    }
    public ArrayList<ProdutosDTO> listarProdutos() {
        ArrayList<ProdutosDTO> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produtos";

        try {
            conn = new ConexaoDAO().connectDB(); 
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();

            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));

                produtos.add(produto);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos: " + e.getMessage());
        } finally {
            try {
                if (resultset != null) resultset.close();
                if (prep != null) prep.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                
            }
        }

        return produtos;
    }

public void marcarProdutoComoVendido(int idProduto) {
    String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";
    
    try (Connection conn = new ConexaoDAO().connectDB(); 
         PreparedStatement prep = conn.prepareStatement(sql)) {
        
        prep.setInt(1, idProduto);
        prep.executeUpdate();
        
    } catch (SQLException e) {
        throw new RuntimeException("Erro ao marcar produto como vendido: " + e.getMessage());
    }
}

public ArrayList<ProdutosDTO> listarProdutosVendidos() {
    ArrayList<ProdutosDTO> listaVendidos = new ArrayList<>();
    String sql = "SELECT * FROM produtos WHERE status = 'vendido'";

    try {
        conn = new ConexaoDAO().connectDB(); 
        prep = conn.prepareStatement(sql);
        resultset = prep.executeQuery();

        while (resultset.next()) {
            ProdutosDTO produto = new ProdutosDTO();
            produto.setId(resultset.getInt("id"));
            produto.setNome(resultset.getString("nome"));
            produto.setValor(resultset.getInt("valor"));
            produto.setStatus(resultset.getString("status"));

            listaVendidos.add(produto);
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao listar produtos vendidos: " + e.getMessage());
    } finally {
        try {
            if (resultset != null) resultset.close();
            if (prep != null) prep.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
         
        }
    }

    return listaVendidos;
}

}


