package sistema.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
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

import sistema.model.Jogo;

public class JanelaListagemJogo extends JDialog {

	private static final long serialVersionUID = 1L;

	public JanelaListagemJogo(List<Jogo> jogos, String modo) {
		setModal(true);
		setTitle("Listagem " + modo + " Jogos");
		setSize(800, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		String[] colunas = { "Nome", "Descrição", "Gênero", "Faixa Etária", "Data de Criação", "Criado por" };
		DefaultTableModel model = new DefaultTableModel(colunas, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		JTable table = new JTable(model);
		table.setRowHeight(30);

		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(180);
		table.getColumnModel().getColumn(2).setPreferredWidth(5);
		table.getColumnModel().getColumn(3).setPreferredWidth(25);
		table.getColumnModel().getColumn(4).setPreferredWidth(60);
		table.getColumnModel().getColumn(5).setPreferredWidth(15);

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		for (Jogo jogo : jogos) {
			String[] row = { jogo.getNome(), jogo.getDescricao(), jogo.getGenero().getNome(),
					String.valueOf(jogo.getFaixaEtaria()), dateFormat.format(jogo.getDataPublicacao()),
					jogo.getDesenvolvedor().getNickname() };
			model.addRow(row);
		}

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		for (int i = 0; i < table.getColumnCount(); i++) {
			if (i != 1) {
				table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
			}
		}

		table.getTableHeader().setResizingAllowed(false);
		table.getTableHeader().setReorderingAllowed(false);

		JScrollPane scrollPane = new JScrollPane(table);
		contentPane.add(scrollPane, BorderLayout.CENTER);

		JTableHeader header = table.getTableHeader();
		header.setBackground(new Color(136, 66, 153));
		header.setForeground(Color.WHITE);
		header.setFont(new Font("Tahoma", Font.BOLD, 14));

		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		contentPane.add(bottomPanel, BorderLayout.SOUTH);

		JButton btnVoltar = new JButton("Fechar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		bottomPanel.add(btnVoltar);

		setVisible(true);
	}
}
