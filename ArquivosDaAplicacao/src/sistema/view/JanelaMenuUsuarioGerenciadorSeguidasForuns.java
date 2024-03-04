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

public class JanelaMenuUsuarioGerenciadorSeguidasForuns extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private int opcao = 0;

    public JanelaMenuUsuarioGerenciadorSeguidasForuns(Usuario usuario) {
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

        JLabel lblTituloJanela = new JLabel("Menu Usuário - Gerenciar de Seguidas Foruns");
        lblTituloJanela.setBackground(new Color(136, 66, 153));
        lblTituloJanela.setHorizontalAlignment(SwingConstants.CENTER);
        lblTituloJanela.setFont(new Font("Tahoma", Font.BOLD, 15));
        contentPanel.add(lblTituloJanela);
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton botaoVoltar = new JButton("Voltar");
                botaoVoltar.setHorizontalTextPosition(SwingConstants.LEADING);
                botaoVoltar.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        opcao = 4;
                        dispose();
                    }
                });
                buttonPane.add(botaoVoltar);
            }
            {
                JButton botaoCancelar = new JButton("Deslogar");
                botaoCancelar.setHorizontalTextPosition(SwingConstants.LEADING);
                botaoCancelar.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        opcao = 5;
                        usuario.setIdUsuario(0);
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
                FlowLayout fl_panel_1 = new FlowLayout(FlowLayout.LEFT, 5, 5);
                fl_panel_1.setAlignOnBaseline(true);
                panel_1.setLayout(fl_panel_1);
                {
                    JLabel lblUsuárioLogado = new JLabel("Usuário: " + usuario.getNickname());
                    panel_1.add(lblUsuárioLogado);
                    lblUsuárioLogado.setHorizontalAlignment(SwingConstants.CENTER);
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
                    panel_2.setLayout(new GridLayout(4, 3, 10, 26));
                    {
                        JPanel panel_3 = new JPanel();
                        panel_3.setBackground(new Color(251, 247, 255));
                        panel_2.add(panel_3);
                    }
                    {
                        JButton btnForunsSeguidos = new JButton("Foruns Seguidos");
                        btnForunsSeguidos.setForeground(Color.WHITE);
                        btnForunsSeguidos.setFont(new Font("Tahoma", Font.BOLD, 13));
                        btnForunsSeguidos.setBackground(new Color(136, 66, 153));
                        btnForunsSeguidos.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                opcao = 1;
                                dispose();
                            }
                        });
                        panel_2.add(btnForunsSeguidos);
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
                        JButton btnSeguirGenero = new JButton("Seguir Fórum");
                        btnSeguirGenero.setBackground(new Color(136, 66, 153));
                        btnSeguirGenero.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                opcao = 2;
                                dispose();
                            }
                        });
                        panel_2.add(btnSeguirGenero);
                        btnSeguirGenero.setForeground(new Color(255, 255, 255));
                        btnSeguirGenero.setFont(new Font("Tahoma", Font.BOLD, 13));
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
                        JButton btnDeixarSeguirGenero = new JButton("Deixar de Seguir Fórum");
                        btnDeixarSeguirGenero.setForeground(Color.WHITE);
                        btnDeixarSeguirGenero.setFont(new Font("Tahoma", Font.BOLD, 13));
                        btnDeixarSeguirGenero.setBackground(new Color(136, 66, 153));
                        btnDeixarSeguirGenero.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                opcao = 3;
                                dispose();
                            }
                        });
                        panel_2.add(btnDeixarSeguirGenero);
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

