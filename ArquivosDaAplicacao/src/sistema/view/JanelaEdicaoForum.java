package sistema.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import sistema.model.Forum;
import sistema.model.Genero;
import sistema.model.Usuario;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

public class JanelaEdicaoForum extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;

	private JTextField txtNome;
	private JTextArea txtDescricao;
	private JComboBox<String> comboBoxGeneros;
	private boolean cancelou = false;
	private Forum forum;

	private JRadioButton rdbLivre;
	private JRadioButton rdb12;
	private JRadioButton rdb16;
	private JRadioButton rdb18;

	public JanelaEdicaoForum(Forum forumSelecionado, List<Genero> generos, Usuario usuario) {
		getContentPane().setBackground(new Color(252, 247, 255));
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 650, 450);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 636, 4);
		contentPanel.setBackground(new Color(240, 240, 240));
		contentPanel.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 382, 636, 31);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane);
			{
				JButton okButton = new JButton("Cancelar");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						cancelou = true;
						dispose();
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Salvar alterações");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						boolean passou = true;

						String nome = txtNome.getText();
						if (!nome.equals("")) {
							txtNome.setBackground(Color.WHITE);
						} else {
							txtNome.setBackground(Color.PINK);
							passou = false;
						}

						String descricao = txtDescricao.getText();
						if (!descricao.equals("")) {
							txtDescricao.setBackground(Color.WHITE);
						} else {
							txtDescricao.setBackground(Color.PINK);
							passou = false;
						}

						String generoString = (String) comboBoxGeneros.getSelectedItem();
						if (!generoString.equals("Selecione um gênero")) {
							comboBoxGeneros.setBackground(Color.WHITE);
						} else {
							comboBoxGeneros.setBackground(Color.PINK);
							passou = false;
						}

						Genero genero = null;
						for (Genero ge : generos) {
							if (ge != null) {
								if (ge.getNome().equals(generoString)) {
									try {
										genero = (Genero) ge.clone();
									} catch (CloneNotSupportedException e1) {
										e1.printStackTrace();
									}
								}
							}
						}

						String faixaEtaria = null;
						if (rdbLivre.isSelected()) {
							faixaEtaria = "Livre";
						} else if (rdb12.isSelected()) {
							faixaEtaria = "12+";
						} else if (rdb16.isSelected()) {
							faixaEtaria = "16+";
						} else if (rdb18.isSelected()) {
							faixaEtaria = "18+";
						}

						if (passou) {
							try {
								Usuario usuarioCopia = (Usuario) usuario.clone();
								forum = Forum.getInstance(faixaEtaria, usuarioCopia, nome, descricao, genero);
								forum.setIdForum(forumSelecionado.getIdForum());
								dispose();
							} catch (CloneNotSupportedException e1) {
								e1.printStackTrace();
							}
						} else {
							JOptionPane.showMessageDialog(null,
									"Todos os campos devem ser preenchidos! Tente npvamente.");
						}

					}
				});
				buttonPane.add(cancelButton);
			}
		}

		JLabel lblNewLabel = new JLabel("Edição de Fórum");
		lblNewLabel.setBounds(225, 7, 178, 22);
		getContentPane().add(lblNewLabel);
		lblNewLabel.setFont(new Font("Verdana", Font.BOLD, 17));

		JPanel panel = new JPanel();
		panel.setBackground(new Color(229, 229, 229));
		panel.setBounds(-128, -7, 790, 40);
		getContentPane().add(panel);
		{
			JLabel lblNewLabel_1 = new JLabel("Usuário: " + usuario.getNickname());
			lblNewLabel_1.setBounds(0, 30, 662, 13);
			getContentPane().add(lblNewLabel_1);
		}

		txtNome = new JTextField();
		txtNome.setBounds(24, 114, 258, 22);
		getContentPane().add(txtNome);
		txtNome.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Nome");
		lblNewLabel_2.setBounds(24, 101, 45, 13);
		getContentPane().add(lblNewLabel_2);

		String[] generosString = new String[generos.size() + 1];
		int i = 1;
		generosString[0] = forumSelecionado.getGenero().getNome();
		for (Genero genero : generos) {
			if (genero != null) {
				if (!genero.getNome().equals(forumSelecionado.getGenero().getNome())) {
					generosString[i] = genero.getNome();
					i++;
				}
			}
		}

		comboBoxGeneros = new JComboBox<String>(generosString);
		comboBoxGeneros.setBounds(326, 114, 258, 22);
		getContentPane().add(comboBoxGeneros);

		JLabel lblNewLabel_2_1 = new JLabel("Gênero");
		lblNewLabel_2_1.setBounds(326, 101, 45, 13);
		getContentPane().add(lblNewLabel_2_1);

		rdbLivre = new JRadioButton("Livre");
		rdbLivre.setBounds(30, 230, 57, 13);
		getContentPane().add(rdbLivre);

		rdb12 = new JRadioButton("12+");
		rdb12.setBounds(30, 250, 57, 13);
		getContentPane().add(rdb12);

		rdb16 = new JRadioButton("16+");
		rdb16.setBounds(89, 230, 45, 13);
		getContentPane().add(rdb16);

		rdb18 = new JRadioButton("18+");
		rdb18.setBounds(89, 250, 45, 13);
		getContentPane().add(rdb18);

		rdbLivre.setSelected(true);

		// Agrupe os JRadioButtons em um ButtonGroup
		ButtonGroup buttonGroupFaixaEtaria = new ButtonGroup();
		buttonGroupFaixaEtaria.add(rdbLivre);
		buttonGroupFaixaEtaria.add(rdb12);
		buttonGroupFaixaEtaria.add(rdb16);
		buttonGroupFaixaEtaria.add(rdb18);

		JLabel lblNewLabel_2_2 = new JLabel("Faixa Etária");
		lblNewLabel_2_2.setBounds(24, 214, 164, 13);
		getContentPane().add(lblNewLabel_2_2);

		txtDescricao = new JTextArea();
		txtDescricao.setBounds(326, 226, 281, 120);
		txtDescricao.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, Color.BLACK, Color.BLACK)));
		getContentPane().add(txtDescricao);

		JLabel lblNewLabel_2_2_1 = new JLabel("Descrição");
		lblNewLabel_2_2_1.setBounds(326, 214, 164, 13);
		getContentPane().add(lblNewLabel_2_2_1);

		String faixaEtaria = forumSelecionado.getFaixaEtaria();
		if (faixaEtaria.equals("Livre")) {
			rdbLivre.setSelected(true);
		} else if (faixaEtaria.equals("12+")) {
			rdb12.setSelected(true);
		} else if (faixaEtaria.equals("16+")) {
			rdb16.setSelected(true);
		} else if (faixaEtaria.equals("18+")) {
			rdb18.setSelected(true);
		}

		txtNome.setText(forumSelecionado.getNome());
		txtDescricao.setText(forumSelecionado.getDescricao());

		setVisible(true);
	}

	public Forum getForum() {
		return forum;
	}

	public boolean getCancelou() {
		return cancelou;
	}
}
