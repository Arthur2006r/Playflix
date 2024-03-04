package sistema.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import sistema.model.Genero;
import sistema.model.Jogo;
import sistema.model.Usuario;

public class JanelaEdicaoJogo extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;

	private JTextField txtNome;
	private JTextArea txtCodigoFonte;
	private JTextArea txtDescricao;
	private JComboBox<String> comboBoxGeneros;

	private Jogo jogo;
	private boolean cancelou = false;

	private JRadioButton rdbLivre;
	private JRadioButton rdb12;
	private JRadioButton rdb16;
	private JRadioButton rdb18;

	public JanelaEdicaoJogo(Jogo jogoSelecionado, List<Genero> generos, Usuario usuario) {
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 650, 450);
		setBackground(new Color(240, 240, 240));
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(251, 247, 255));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		setLocationRelativeTo(null);
		{
			JLabel lblNewLabel = new JLabel("Usuario: " + usuario.getNickname());
			lblNewLabel.setBounds(0, 35, 100, 13);
			contentPanel.add(lblNewLabel);
		}

		txtCodigoFonte = new JTextArea();
		txtCodigoFonte.setBounds(332, 81, 257, 279);
		txtCodigoFonte.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.BLACK)));
		contentPanel.add(txtCodigoFonte);

		JLabel lblNewLabel_1 = new JLabel("Código fonte");
		lblNewLabel_1.setBounds(332, 60, 99, 13);
		contentPanel.add(lblNewLabel_1);

		txtNome = new JTextField();
		txtNome.setBounds(30, 88, 219, 19);
		contentPanel.add(txtNome);
		txtNome.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Nome");
		lblNewLabel_2.setBounds(30, 73, 219, 13);
		contentPanel.add(lblNewLabel_2);

		String[] generosString = new String[generos.size() + 1];
		int i = 1;
		generosString[0] = jogoSelecionado.getGenero().getNome();
		for (Genero genero : generos) {
			if (genero != null) {
				if (!genero.getNome().equals(jogoSelecionado.getGenero().getNome())) {
					generosString[i] = genero.getNome();
					i++;
				}
			}
		}

		comboBoxGeneros = new JComboBox<String>(generosString);
		comboBoxGeneros.setBounds(30, 217, 219, 21);
		contentPanel.add(comboBoxGeneros);

		JLabel lblNewLabel_3 = new JLabel("Gênero");
		lblNewLabel_3.setBounds(30, 202, 219, 13);
		contentPanel.add(lblNewLabel_3);

		rdbLivre = new JRadioButton("Livre");
		rdbLivre.setBounds(30, 143, 57, 13);
		contentPanel.add(rdbLivre);

		rdb12 = new JRadioButton("12+");
		rdb12.setBounds(30, 158, 57, 13);
		contentPanel.add(rdb12);

		rdb16 = new JRadioButton("16+");
		rdb16.setBounds(89, 143, 45, 13);
		contentPanel.add(rdb16);

		rdb18 = new JRadioButton("18+");
		rdb18.setBounds(89, 158, 45, 13);
		contentPanel.add(rdb18);

		// Agrupe os JRadioButtons em um ButtonGroup
		ButtonGroup buttonGroupFaixaEtaria = new ButtonGroup();
		buttonGroupFaixaEtaria.add(rdbLivre);
		buttonGroupFaixaEtaria.add(rdb12);
		buttonGroupFaixaEtaria.add(rdb16);
		buttonGroupFaixaEtaria.add(rdb18);

		txtDescricao = new JTextArea();
		txtDescricao.setBounds(30, 297, 219, 63);
		txtDescricao.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.BLACK)));
		contentPanel.add(txtDescricao);

		JLabel lblNewLabel_4 = new JLabel("Faixa Etária");
		lblNewLabel_4.setBounds(30, 128, 219, 13);
		contentPanel.add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("Descrição");
		lblNewLabel_5.setBounds(30, 274, 85, 13);
		contentPanel.add(lblNewLabel_5);

		JLabel lblNewLabel_6 = new JLabel("                                                  Edição de Jogo");
		lblNewLabel_6.setBackground(new Color(128, 128, 128));
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNewLabel_6.setBounds(0, 0, 636, 23);
		contentPanel.add(lblNewLabel_6);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Cancelar");
				okButton.setActionCommand("");
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
				JButton cancelButton = new JButton("Salvar aterações");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						boolean passou = true;

						String descricao = txtDescricao.getText();
						if (!descricao.equals("")) {
							txtDescricao.setBackground(Color.WHITE);
						} else {
							txtDescricao.setBackground(Color.PINK);
							passou = false;
						}

						String codigoFonte = txtCodigoFonte.getText();
						if (!codigoFonte.equals("")) {
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
								jogo = Jogo.getInstance(jogoSelecionado.getNome(), descricao, codigoFonte, faixaEtaria,
										usuarioCopia, genero);
								jogo.setIdJogo(jogoSelecionado.getIdJogo());
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

		String faixaEtaria = jogoSelecionado.getFaixaEtaria();
		if (faixaEtaria.equals("Livre")) {
			rdbLivre.setSelected(true);
		} else if (faixaEtaria.equals("12+")) {
			rdb12.setSelected(true);
		} else if (faixaEtaria.equals("16+")) {
			rdb16.setSelected(true);
		} else if (faixaEtaria.equals("18+")) {
			rdb18.setSelected(true);
		}

		txtNome.setText(jogoSelecionado.getNome());
		txtCodigoFonte.setText(jogoSelecionado.getCodigoFonte());
		txtDescricao.setText(jogoSelecionado.getDescricao());

		txtNome.setEditable(false);

		setVisible(true);
	}

	public Jogo getJogo() {
		return jogo;
	}

	public boolean getCancelou() {
		return cancelou;
	}
}
