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

import sistema.model.Genero;
import sistema.model.Jogo;

public class JanelaListagemExclusaoGenero extends JDialog {
	private static final long serialVersionUID = 1L;
	private JTable table;
	private DefaultTableModel model;
	private Genero genero;
	private boolean cancelou = false;

	public JanelaListagemExclusaoGenero(List<Genero> generos) {
		setTitle("Listagem de Gêneros - Exclusão");
		setSize(800, 400);
		setLocationRelativeTo(null);
		setModal(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		String[] colunas = { "Nome", "Descrição", "Criado por" };
		model = new DefaultTableModel(colunas, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table = new JTable(model);
		table.setRowHeight(30);
		
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(180);
		table.getColumnModel().getColumn(2).setPreferredWidth(25);

		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

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

		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.setBackground(Color.RED);
		btnExcluir.setForeground(Color.WHITE);
		btnExcluir.setEnabled(false);

		btnExcluir.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int linhaSelecionada = table.getSelectedRow();
				genero = generos.get(linhaSelecionada);
				dispose();
			}
		});

		bottomPanel.add(btnExcluir);

		table.getSelectionModel().addListSelectionListener(e -> {
			if (table.getSelectedRow() != -1) {
				btnExcluir.setEnabled(true);
			} else {
				btnExcluir.setEnabled(false);
			}
		});

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		for (Genero genero : generos) {
			String[] row = { genero.getNome(), genero.getDescricao(), genero.getCriador().getNickname() };
			model.addRow(row);
		}

		table.getTableHeader().setResizingAllowed(false);
		table.getTableHeader().setReorderingAllowed(false);

		setVisible(true);
	}

	public Genero getGenero() {
		return genero;
	}

	public boolean getCancelou() {
		return cancelou;
	}
}
