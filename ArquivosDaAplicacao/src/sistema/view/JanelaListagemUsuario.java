package sistema.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import sistema.model.Usuario;

public class JanelaListagemUsuario extends JDialog {

	private static final long serialVersionUID = 1L;

	public JanelaListagemUsuario(List<Usuario> usuarios) {
		setModal(true);
		setTitle("Listagem de Usuários");
		setSize(800, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		// Cabeçalho da Tabela
		String[] colunas = { "Nickname", "Email" };
		DefaultTableModel model = new DefaultTableModel(colunas, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		JTable table = new JTable(model);
		table.setRowHeight(30);

		table.getColumnModel().getColumn(0).setPreferredWidth(0);
		table.getColumnModel().getColumn(1).setPreferredWidth(200);

		for (Usuario usuario : usuarios) {
			String[] row = { usuario.getNickname(), usuario.getEmail() };
			model.addRow(row);
		}

		JScrollPane scrollPane = new JScrollPane(table);
		contentPane.add(scrollPane, BorderLayout.CENTER);

		JTableHeader header = table.getTableHeader();
		header.setBackground(new Color(136, 66, 153));
		header.setForeground(Color.WHITE);
		header.setFont(new Font("Tahoma", Font.BOLD, 14));

		// Botão de Voltar
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10)); // Botão à direita
		contentPane.add(bottomPanel, BorderLayout.SOUTH);

		JButton btnVoltar = new JButton("Fechar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		bottomPanel.add(btnVoltar);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

		table.getTableHeader().setResizingAllowed(false);
		table.getTableHeader().setReorderingAllowed(false);

		setVisible(true);
	}
}
