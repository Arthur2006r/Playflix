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
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import sistema.model.Usuario;

public class JanelaMenuUsuarioInicial extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private int opcao;
	
	public JanelaMenuUsuarioInicial(Usuario usuario) {
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setBackground(new Color(248, 242, 255));
		setBounds(100, 100, 650, 450);
		getContentPane().setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		contentPanel.setBackground(new Color(240, 240, 240));
		contentPanel.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		contentPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblTituloJanela = new JLabel("Menu Usuário");
		lblTituloJanela.setBackground(new Color(136, 66, 153));
		lblTituloJanela.setHorizontalAlignment(SwingConstants.CENTER);
		lblTituloJanela.setFont(new Font("Tahoma", Font.BOLD, 15));
		contentPanel.add(lblTituloJanela);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton botaoCancelar = new JButton("Deslogar");
				botaoCancelar.setHorizontalTextPosition(SwingConstants.LEADING);
				botaoCancelar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						opcao = 5;
						dispose();
					}
				});
				buttonPane.add(botaoCancelar);
			}
		}
		{
			JPanel panel = new JPanel();
			panel.setBackground(new Color(255, 255, 255));
			getContentPane().add(panel, BorderLayout.CENTER);
			panel.setLayout(new GridLayout(2, 1, 0, 0));
			{
				JPanel panel_1 = new JPanel();
				panel_1.setBackground(new Color(251, 247, 255));
				panel.add(panel_1);
				panel_1.setLayout(null);
				{
					JPanel panel_2 = new JPanel();
					panel_2.setBounds(0, 0, 636, 23);
					FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
					flowLayout.setAlignment(FlowLayout.LEFT);
					panel_2.setBackground(new Color(251, 247, 255));
					panel_1.add(panel_2);
					{
						JLabel lblUsuárioLogado = new JLabel("Usuário: " + usuario.getNickname());
						lblUsuárioLogado.setHorizontalAlignment(SwingConstants.CENTER);
						panel_2.add(lblUsuárioLogado);
					}
				}
			}
			{
				JPanel panel_1 = new JPanel();
				panel.add(panel_1);
				panel_1.setLayout(new GridLayout(0, 1, 0, 0));
				{
					JPanel panel_2 = new JPanel();
					panel_2.setBackground(new Color(251, 247, 255));
					panel_1.add(panel_2);
					panel_2.setLayout(new GridLayout(5, 3, 10, 10));
					{
						JPanel panel_3 = new JPanel();
						panel_3.setBackground(new Color(251, 247, 255));
						panel_2.add(panel_3);
					}
					{
						JButton btnEditarPerfil = new JButton("Editar Perfil");
						btnEditarPerfil.setForeground(Color.WHITE);
						btnEditarPerfil.setFont(new Font("Tahoma", Font.BOLD, 13));
						btnEditarPerfil.setBackground(new Color(136, 66, 153));
						btnEditarPerfil.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								opcao = 1;
								dispose();
							}
						});
						panel_2.add(btnEditarPerfil);
					}
					{
						JPanel panel_3 = new JPanel();
						panel_3.setBackground(new Color(251, 247, 255));
						panel_2.add(panel_3);
					}
					{
						JPanel panel_3 = new JPanel();
						panel_3.setBackground(new Color(251, 247, 255));
						panel_2.add(panel_3);
					}
					{
						JButton btnVisualizarUsuarios = new JButton("Visualizar Usuários");
						btnVisualizarUsuarios.setBackground(new Color(136, 66, 153));
						btnVisualizarUsuarios.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								opcao = 2;
								dispose();
							}
						});
						panel_2.add(btnVisualizarUsuarios);
						btnVisualizarUsuarios.setForeground(new Color(255, 255, 255));
						btnVisualizarUsuarios.setFont(new Font("Tahoma", Font.BOLD, 13));
					}
					{
						JPanel panel_3 = new JPanel();
						panel_3.setBackground(new Color(251, 247, 255));
						panel_2.add(panel_3);
					}
					{
						JPanel panel_3 = new JPanel();
						panel_3.setBackground(new Color(251, 247, 255));
						panel_2.add(panel_3);
					}
					{
						JButton btnGerenciarForuns = new JButton("Menu dos gerenciadores");
						btnGerenciarForuns.setForeground(Color.WHITE);
						btnGerenciarForuns.setFont(new Font("Tahoma", Font.BOLD, 13));
						btnGerenciarForuns.setBackground(new Color(136, 66, 153));
						btnGerenciarForuns.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								opcao = 3;
								dispose();
							}
						});
						panel_2.add(btnGerenciarForuns);
					}
					{
						JPanel panel_3 = new JPanel();
						panel_3.setBackground(new Color(251, 247, 255));
						panel_2.add(panel_3);
					}
					{
						JPanel panel_3 = new JPanel();
						panel_3.setBackground(new Color(251, 247, 255));
						panel_2.add(panel_3);
					}
					{
						JButton btnApagarConta = new JButton("Apagar Conta");
						btnApagarConta.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								opcao = 4;
								dispose();
							}
						});
						btnApagarConta.setForeground(Color.WHITE);
						btnApagarConta.setFont(new Font("Tahoma", Font.BOLD, 13));
						btnApagarConta.setBackground(new Color(255, 0, 0));
						panel_2.add(btnApagarConta);
					}
					{
						JPanel panel_3 = new JPanel();
						panel_3.setBackground(new Color(251, 247, 255));
						panel_2.add(panel_3);
					}
					{
						JPanel panel_3 = new JPanel();
						panel_3.setBackground(new Color(251, 247, 255));
						panel_2.add(panel_3);
					}
					{
						JPanel panel_3 = new JPanel();
						panel_3.setBackground(new Color(251, 247, 255));
						panel_2.add(panel_3);
					}
					{
						JPanel panel_3 = new JPanel();
						panel_3.setBackground(new Color(251, 247, 255));
						panel_2.add(panel_3);
					}
				}
			}
		}
		
		setVisible(true);
	}

	public int getOpcao() {
		return opcao;
	}
}
