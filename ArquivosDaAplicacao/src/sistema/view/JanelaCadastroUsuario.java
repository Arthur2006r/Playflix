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

public class JanelaCadastroUsuario extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtNickname;
	private JTextField txtSenha;
	private JTextField txtEmail;

	private Usuario usuario;
	private boolean cancelou = false;

	public JanelaCadastroUsuario() {
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
			JLabel lblNewLabel = new JLabel("Cadastro de Usuário");
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
				JButton botaoSalvar = new JButton("Cadastrar");
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
						}

						String nickname = txtNickname.getText();
						if (!nickname.equals("")) {
							txtNickname.setBackground(Color.WHITE);
						} else {
							txtNickname.setBackground(Color.PINK);
							passou = false;
						}

						String senha = txtSenha.getText();
						if (senha.length() >= 5) {
							txtSenha.setBackground(Color.WHITE);
						} else {
							txtSenha.setBackground(Color.PINK);
							passou = false;
						}

						if (passou) {
							usuario = Usuario.getInstance(senha, nickname, email);
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
			getContentPane().add(panelAreaDireita, BorderLayout.EAST);
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
		{
			JPanel panelAreaEsquerda = new JPanel();
			FlowLayout fl_panelAreaEsquerda = (FlowLayout) panelAreaEsquerda.getLayout();
			fl_panelAreaEsquerda.setVgap(140);
			panelAreaEsquerda.setBackground(new Color(136, 66, 153));
			getContentPane().add(panelAreaEsquerda, BorderLayout.CENTER);
			{
				JPanel panelTextos = new JPanel();
				panelTextos.setBackground(new Color(136, 66, 153));
				panelAreaEsquerda.add(panelTextos);
				panelTextos.setLayout(new GridLayout(0, 1, 0, 10));
				{
					JLabel lblTitulo = new JLabel("PLAYFLIX");
					lblTitulo.setForeground(new Color(255, 255, 255));
					lblTitulo.setBackground(new Color(255, 255, 255));
					lblTitulo.setFont(new Font("Verdana", Font.BOLD, 25));
					panelTextos.add(lblTitulo);
				}
				{
					JPanel panelSubTextos = new JPanel();
					panelSubTextos.setBackground(new Color(136, 66, 153));
					panelTextos.add(panelSubTextos);
					panelSubTextos.setLayout(new GridLayout(0, 1, 0, 3));
					{
						JLabel lblSubTexto1 = new JLabel("A plataforma para jogadores e");
						panelSubTextos.add(lblSubTexto1);
						lblSubTexto1.setForeground(new Color(0, 255, 0));
						lblSubTexto1.setBackground(new Color(240, 240, 240));
					}
					{
						JLabel lblSubTexto2 = new JLabel("desenvolvedores de jogos");
						panelSubTextos.add(lblSubTexto2);
						lblSubTexto2.setForeground(new Color(0, 255, 0));
					}
				}
			}
		}

		setVisible(true);
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public boolean getCancelou() {
		return cancelou;
	}

}
