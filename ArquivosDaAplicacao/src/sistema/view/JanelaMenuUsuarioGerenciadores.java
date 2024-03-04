package sistema.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import sistema.model.Usuario;

public class JanelaMenuUsuarioGerenciadores extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private int opcao = 0;

	public JanelaMenuUsuarioGerenciadores(Usuario usuario) {
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 650, 450);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(240, 240, 240));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Menu dos Gerenciadores");
		lblNewLabel.setFont(new Font("Verdana", Font.BOLD, 21));
		lblNewLabel.setBounds(170, 40, 385, 42);
		contentPanel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Usuário: " + usuario.getNickname());
		lblNewLabel_1.setBounds(0, 0, 295, 13);
		contentPanel.add(lblNewLabel_1);

		JButton btnGerenciadorForuns = new JButton("Gerenciador de Fóruns");
		btnGerenciadorForuns.setBounds(82, 195, 184, 34);
		estilizarBotao(btnGerenciadorForuns);
		btnGerenciadorForuns.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opcao = 1;
				dispose();
			}
		});
		contentPanel.add(btnGerenciadorForuns);

		JButton btnGerenciadorJogos = new JButton("Gerenciador de Jogos");
		btnGerenciadorJogos.setBounds(359, 195, 184, 34);
		estilizarBotao(btnGerenciadorJogos);
		btnGerenciadorJogos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opcao = 2;
				dispose();
			}
		});
		contentPanel.add(btnGerenciadorJogos);

		JButton btnGerenciadorGeneros = new JButton("Gerenciador de Gêneros");
		btnGerenciadorGeneros.setBounds(359, 302, 203, 34);
		estilizarBotao(btnGerenciadorGeneros);
		btnGerenciadorGeneros.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opcao = 3;
				dispose();
			}
		});
		contentPanel.add(btnGerenciadorGeneros);

		JButton btnGerenciadorSeguidas = new JButton("Gerenciador de Seguidas");
		btnGerenciadorSeguidas.setBounds(67, 302, 210, 34);
		estilizarBotao(btnGerenciadorSeguidas);
		btnGerenciadorSeguidas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opcao = 4;
				dispose();
			}
		});
		contentPanel.add(btnGerenciadorSeguidas);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton voltarButton = new JButton("Voltar");
				voltarButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						opcao = 5;
						dispose();
					}
				});
				buttonPane.add(voltarButton);
				getRootPane().setDefaultButton(voltarButton);
			}
			{
				JButton deslogarButton = new JButton("Deslogar");
				deslogarButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						opcao = 6;
						usuario.setIdUsuario(0);
						dispose();
					}
				});
				buttonPane.add(deslogarButton);
			}
		}

		setVisible(true);
	}

	public int getOpcao() {
		return opcao;
	}

	// Método para estilizar os botões
	private void estilizarBotao(JButton button) {
		button.setForeground(Color.WHITE);
		button.setFont(new Font("Tahoma", Font.BOLD, 13));
		button.setBackground(new Color(136, 66, 153));
	}
}
