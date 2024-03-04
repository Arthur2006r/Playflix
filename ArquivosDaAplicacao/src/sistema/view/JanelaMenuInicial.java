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

public class JanelaMenuInicial extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private int opcao = -1;

	public JanelaMenuInicial() {
		setModal(true);
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
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton botaoCancelar = new JButton("Fechar Sistema");
				botaoCancelar.setHorizontalTextPosition(SwingConstants.LEADING);
				botaoCancelar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						opcao = 3;
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
			panel.setLayout(new GridLayout(0, 1, 0, 0));
			{
				JPanel panel_1 = new JPanel();
				panel_1.setBackground(new Color(251, 247, 255));
				panel.add(panel_1);
				panel_1.setLayout(new GridLayout(0, 1, 0, 0));
				{
					JLabel lblNewLabel = new JLabel("Bem-vindo ao PLAYFLIX");
					lblNewLabel.setBackground(new Color(255, 255, 255));
					lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
					lblNewLabel.setForeground(new Color(136, 66, 153));
					lblNewLabel.setFont(new Font("Verdana", Font.BOLD, 16));
					panel_1.add(lblNewLabel);
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
					panel_2.setLayout(new GridLayout(2, 3, 0, 25));
					{
						JPanel panel_3 = new JPanel();
						panel_3.setBackground(new Color(251, 247, 255));
						panel_2.add(panel_3);
					}
					{
						JButton btnFazerLogin = new JButton("Fazer Login");
						btnFazerLogin.setForeground(Color.WHITE);
						btnFazerLogin.setFont(new Font("Tahoma", Font.BOLD, 13));
						btnFazerLogin.setBackground(new Color(136, 66, 153));
						btnFazerLogin.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								opcao = 1;
								dispose();
							}
						});
						panel_2.add(btnFazerLogin);
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
						JButton btnCadastrar = new JButton("Cadastrar");
						btnCadastrar.setBackground(new Color(136, 66, 153));
						btnCadastrar.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								opcao = 2;
								dispose();
							}
						});
						panel_2.add(btnCadastrar);
						btnCadastrar.setForeground(new Color(255, 255, 255));
						btnCadastrar.setFont(new Font("Tahoma", Font.BOLD, 13));
					}
					{
						JPanel panel_3 = new JPanel();
						panel_3.setBackground(new Color(251, 247, 255));
						panel_2.add(panel_3);
					}
				}
				{
					JPanel panel_2 = new JPanel();
					panel_2.setBackground(new Color(251, 247, 255));
					panel_1.add(panel_2);
				}
			}
		}

		setVisible(true);
	}

	public int getOpcao() {
		return opcao;
	}

}
