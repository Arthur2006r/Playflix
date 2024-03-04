package sistema.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import sistema.model.Usuario;

public class JanelaEdicaoUsuario extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtNickname;
	private JTextField txtSenha;
	private JTextField txtEmail;
	private Usuario usuarioEditado;
	private boolean cancelou = false;

	public JanelaEdicaoUsuario(Usuario usuario) {
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 650, 450);
		getContentPane().setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		contentPanel.setBackground(new Color(240, 240, 240));
		contentPanel.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JLabel lblNewLabel = new JLabel("Edição de Usuário");
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
			contentPanel.add(lblNewLabel);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton botaoCancelar = new JButton("Cancelar");
				botaoCancelar.setHorizontalTextPosition(SwingConstants.LEADING);
				botaoCancelar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						cancelou = true;
						dispose();
					}
				});
				buttonPane.add(botaoCancelar);
			}
			{
				JButton botaoSalvar = new JButton("Salvar");
				buttonPane.add(botaoSalvar);
				botaoSalvar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						boolean passou = true;

						String email = txtEmail.getText();
						if (email.length() >= 5) {
							txtEmail.setBackground(Color.WHITE);
						} else {
							txtEmail.setBackground(Color.PINK);
							passou = false;
							JOptionPane.showMessageDialog(null,
									"O email deve ter no mínimo 5 caracteres!");
						}

						String senha = txtSenha.getText();
						if (senha.length() >= 5) {
							txtSenha.setBackground(Color.WHITE);
						} else {
							txtSenha.setBackground(Color.PINK);
							passou = false;
							JOptionPane.showMessageDialog(null,
									"A senha deve ter no mínimo 5 caracteres!");
						}

						if (passou) {
							usuarioEditado = Usuario.getInstance(senha, txtNickname.getText(), email);
							usuarioEditado.setIdUsuario(usuario.getIdUsuario());
							dispose();
						} else {
							JOptionPane.showMessageDialog(null,
									"Todos os campos devem ser preenchidos!\n(O EMAIL e a SENHA devem ter no mínimo 5 caracteres)\nTente novamente.");
						}
					}
				});
				getRootPane().setDefaultButton(botaoSalvar);
			}
		}
		{
			JPanel panelAreaDireita = new JPanel();
			panelAreaDireita.setBackground(new Color(251, 247, 255));
			FlowLayout fl_panelAreaDireita = (FlowLayout) panelAreaDireita.getLayout();
			fl_panelAreaDireita.setVgap(100);
			getContentPane().add(panelAreaDireita, BorderLayout.CENTER);
			{
				JPanel panel_1 = new JPanel();
				panel_1.setBackground(new Color(251, 247, 255));
				panelAreaDireita.add(panel_1);
			}
			{
				JPanel panelInputsBotoes = new JPanel();
				panelInputsBotoes.setBackground(new Color(248, 242, 255));
				panelAreaDireita.add(panelInputsBotoes);
				panelInputsBotoes.setLayout(new GridLayout(0, 1, 0, 15));

				JPanel panelInputs = new JPanel();
				panelInputs.setBackground(new Color(251, 247, 255));
				panelInputsBotoes.add(panelInputs);
				panelInputs.setLayout(new GridLayout(0, 1, 0, 15));
				{
					JPanel panel_2 = new JPanel();
					panel_2.setBackground(new Color(251, 247, 255));
					panelInputs.add(panel_2);
					panel_2.setLayout(new GridLayout(0, 1, 0, 0));
					{
						JLabel lblEmail = new JLabel("E-mail");
						panel_2.add(lblEmail);
						lblEmail.setBackground(new Color(251, 247, 255));
					}
					{
						txtEmail = new JTextField();
						txtEmail.setForeground(new Color(64, 64, 64));
						panel_2.add(txtEmail);
						txtEmail.setHorizontalAlignment(SwingConstants.LEFT);
						txtEmail.setColumns(30);
					}
				}
				{
					JPanel panelInputNickname = new JPanel();
					panelInputNickname.setBackground(new Color(251, 247, 255));
					panelInputs.add(panelInputNickname);
					panelInputNickname.setLayout(new GridLayout(0, 1, 0, 0));
					{
						JLabel lblNickName = new JLabel("Nickname");
						lblNickName.setBackground(new Color(251, 247, 255));
						panelInputNickname.add(lblNickName);
					}
					{
						txtNickname = new JTextField();
						txtNickname.setEnabled(false);
						txtNickname.setForeground(new Color(64, 64, 64));
						panelInputNickname.add(txtNickname);
						txtNickname.setHorizontalAlignment(SwingConstants.LEFT);
						txtNickname.setColumns(10);
					}
				}
				{
					JPanel panelInputSenha = new JPanel();
					panelInputSenha.setBackground(new Color(251, 247, 255));
					panelInputs.add(panelInputSenha);
					panelInputSenha.setLayout(new GridLayout(0, 1, 0, 0));
					{
						JLabel lblSenha = new JLabel("Senha");
						lblSenha.setBackground(new Color(251, 247, 255));
						panelInputSenha.add(lblSenha);
					}
					{
						txtSenha = new JTextField();
						txtSenha.setForeground(new Color(64, 64, 64));
						panelInputSenha.add(txtSenha);
						txtSenha.setColumns(10);
					}
				}
			}
			{
				JPanel panel_1 = new JPanel();
				panelAreaDireita.add(panel_1);
				panel_1.setLayout(new GridLayout(0, 1, 0, 0));
			}
			{
				JPanel panel_1 = new JPanel();
				panelAreaDireita.add(panel_1);
				panel_1.setLayout(new GridLayout(0, 1, 0, 0));
			}
			{
				JPanel panel_1 = new JPanel();
				panelAreaDireita.add(panel_1);
				panel_1.setLayout(new GridLayout(0, 1, 0, 0));
			}
			{
				JPanel panel_1 = new JPanel();
				panelAreaDireita.add(panel_1);
				panel_1.setLayout(new GridLayout(0, 1, 0, 0));
			}
		}

		txtEmail.setText(usuario.getEmail());
		txtNickname.setText(usuario.getNickname());
		txtSenha.setText(usuario.getSenha());
		
		setVisible(true);
	}

	public Usuario getUsuarioEditado() {
		return usuarioEditado;
	}
	
	public boolean getCancelou() {
		return cancelou;
	}

}
