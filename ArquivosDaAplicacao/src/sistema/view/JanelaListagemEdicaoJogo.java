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
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import sistema.model.Forum;
import sistema.model.Jogo;

public class JanelaListagemEdicaoJogo extends JDialog {
	private static final long serialVersionUID = 1L;
	private JTable table;
	private Jogo jogo;
	private boolean cancelou = false;

	public JanelaListagemEdicaoJogo(List<Jogo> jogos) {
		setTitle("Listagem de Jogos - Edição");
		setSize(800, 400);
		setLocationRelativeTo(null);
		setModal(true);
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
		table = new JTable(model);
		table.setRowHeight(30);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
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

		JButton btnVoltar = new JButton("Cancelar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelou = true;
				dispose();
			}
		});
		bottomPanel.add(btnVoltar);

		JButton btnEditar = new JButton("Editar");
		btnEditar.setBackground(new Color(148, 0, 211));
		btnEditar.setForeground(Color.WHITE);
		btnEditar.setEnabled(false);

		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int linhaSelecionada = table.getSelectedRow();
				jogo = jogos.get(linhaSelecionada);
				dispose();
			}
		});

		bottomPanel.add(btnEditar);

		table.getSelectionModel().addListSelectionListener(e -> {
			if (table.getSelectedRow() != -1) {
				btnEditar.setEnabled(true);
			} else {
				btnEditar.setEnabled(false);
			}
		});

		setVisible(true);
	}

	public Jogo getJogo() {
		return jogo;
	}

	public boolean getCancelou() {
		return cancelou;
	}
}
