import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.*;

public class AgendaNotificacoes {

    static class Cliente implements Comparable<Cliente> {
        String nome;
        LocalDate data;
        String servico;

        public Cliente(String nome, LocalDate data, String servico) {
            this.nome = nome;
            this.data = data;
            this.servico = servico;
        }

        @Override
        public int compareTo(Cliente outro) {
            LocalDate hoje = LocalDate.now();
            boolean estePassado = this.data.isBefore(hoje);
            boolean outroPassado = outro.data.isBefore(hoje);

            if (estePassado && !outroPassado) return -1;
            if (!estePassado && outroPassado) return 1;
            return this.data.compareTo(outro.data);
        }

        @Override
        public String toString() {
            String status = data.isBefore(LocalDate.now()) ? " (ATRASADO)" : 
                          (data.isEqual(LocalDate.now()) ? " (HOJE)" : 
                          (ChronoUnit.DAYS.between(LocalDate.now(), data) <= 3) ? " (PRÓXIMO)" : "";
            return String.format("%s | %-10s | %-20s | %s", 
                               data.toString(), 
                               status.trim(), 
                               nome, 
                               servico);
        }
    }

    public static void main(String[] args) {
        List<Cliente> clientes = new ArrayList<>();
        carregarClientesExemplo(clientes); // Dados de exemplo

        // Verificar lembretes automaticamente
        verificarLembretes(clientes);

        // Ordenar e exibir
        Collections.sort(clientes);
        exibirAgenda(clientes);
    }

    private static void carregarClientesExemplo(List<Cliente> clientes) {
        clientes.add(new Cliente("Maria Silva", LocalDate.now().minusDays(2), "Corte e Coloração"));
        clientes.add(new Cliente("João Santos", LocalDate.now().plusDays(1), "Manicure"));
        clientes.add(new Cliente("Ana Oliveira", LocalDate.now(), "Pedicure"));
        clientes.add(new Cliente("Carlos Souza", LocalDate.now().plusDays(3), "Massagem"));
    }

    private static void verificarLembretes(List<Cliente> clientes) {
        LocalDate hoje = LocalDate.now();
        StringBuilder alertas = new StringBuilder("🔔 LEMBRETES:\n");

        clientes.forEach(cliente -> {
            long diasRestantes = ChronoUnit.DAYS.between(hoje, cliente.data);
            
            if (cliente.data.isEqual(hoje)) {
                alertas.append(String.format("• %s: %s HOJE!\n", cliente.nome, cliente.servico));
            } else if (diasRestantes <= 3 && diasRestantes >= 0) {
                alertas.append(String.format("• %s: %s em %d dias\n", cliente.nome, cliente.servico, diasRestantes));
            } else if (cliente.data.isBefore(hoje)) {
                alertas.append(String.format("• %s: %s está ATRASADO há %d dias\n", 
                    cliente.nome, cliente.servico, Math.abs(diasRestantes)));
            }
        });

        if (alertas.length() > 15) { // Se houver alertas
            JOptionPane.showMessageDialog(null, alertas.toString(), "Notificações", JOptionPane.WARNING_MESSAGE);
        }
    }

    private static void exibirAgenda(List<Cliente> clientes) {
        StringBuilder sb = new StringBuilder("📅 AGENDA COMPLETA\n");
        sb.append("-------------------------------------------------\n");
        sb.append("DATA       | STATUS    | NOME               | SERVIÇO\n");
        sb.append("-------------------------------------------------\n");

        clientes.forEach(cliente -> sb.append(cliente.toString()).append("\n"));

        JTextArea textArea = new JTextArea(sb.toString(), 20, 50);
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setEditable(false);

        JOptionPane.showMessageDialog(null, scrollPane, "Agenda de Serviços", JOptionPane.INFORMATION_MESSAGE);
    }
}