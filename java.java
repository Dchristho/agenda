import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JOptionPane;

public class Main {
    static class Cliente implements Comparable<Cliente> {
        String nome;
        LocalDate data;

        public Cliente(String nome, LocalDate data) {
            this.nome = nome;
            this.data = data;
        }

        @Override
        public int compareTo(Cliente outro) {
            LocalDate hoje = LocalDate.now();
            if (this.data.isBefore(hoje) {
                return outro.data.isBefore(hoje) ? this.data.compareTo(outro.data) : -1;
            }
            return this.data.compareTo(outro.data);
        }

        @Override
        public String toString() {
            return data.toString() + " - " + nome + (data.isBefore(LocalDate.now()) ? " (ATRASADO)" : "";
        }
    }

    public static void main(String[] args) {
        List<Cliente> clientes = new ArrayList<>();
        
        // Interface gráfica para adicionar clientes
        while (true) {
            String nome = JOptionPane.showInputDialog("Digite o nome do cliente (ou cancelar para sair):");
            if (nome == null) break;
            
            String dataStr = JOptionPane.showInputDialog("Digite a data (AAAA-MM-DD):");
            LocalDate data = LocalDate.parse(dataStr);
            
            clientes.add(new Cliente(nome, data));
        }

        Collections.sort(clientes);
        
        StringBuilder resultado = new StringBuilder("Clientes ordenados:\n");
        for (Cliente c : clientes) {
            resultado.append(c.toString()).append("\n");
        }
        
        JOptionPane.showMessageDialog(null, resultado.toString());
    }
}