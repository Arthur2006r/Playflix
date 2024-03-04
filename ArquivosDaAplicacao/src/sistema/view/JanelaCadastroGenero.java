package sistema.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import sistema.model.Genero;
import sistema.model.Usuario;

public class JanelaCadastroGenero extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField txtNome;
	private JTextArea txtDescricao;

	private Genero genero;
	private boolean cancelou = false;

	public JanelaCadastroGenero(Usuario usuario) {
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
			JLabel lblNewLabel = new JLabel("Cadastro Gênero");
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
			lblNewLabel.setBounds(248, 0, 169, 23);
			contentPanel.add(lblNewLabel);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Usuário: " + usuario.getNickname());
			lblNewLabel_1.setBounds(0, 22, 189, 13);
			contentPanel.add(lblNewLabel_1);
		}
		{
			txtNome = new JTextField();
			txtNome.setBounds(168, 121, 294, 28);
			contentPanel.add(txtNome);
			txtNome.setColumns(10);
		}
		{
			JLabel lblNewLabel_2 = new JLabel("Nome");
			lblNewLabel_2.setBounds(168, 106, 45, 13);
			contentPanel.add(lblNewLabel_2);
		}
		{
			txtDescricao = new JTextArea();
			txtDescricao.setBounds(168, 217, 294, 117);
			// Defina a borda do txtDescricao como EtchedBorder com cor preta
			txtDescricao.setBorder(
					BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.BLACK)));
			contentPanel.add(txtDescricao);
		}
		{
			JLabel lblNewLabel_2 = new JLabel("Descrição");
			lblNewLabel_2.setBounds(168, 202, 249, 13);
			contentPanel.add(lblNewLabel_2);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Cancelar");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						cancelou = true;
						dispose();
					}
				});
				buttonPane.add(okButton);
			}
			{
				JButton cancelButton = new JButton("Cadastrar");
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

						if (passou) {
							try {
								Usuario usuarioCopia = (Usuario) usuario.clone();
								genero = Genero.getInstance(nome, descricao, usuarioCopia);
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

		setVisible(true);
	}

	public Genero getGenero() {
		return genero;
	}

	public boolean getCancelou() {
		return cancelou;
	}
}
