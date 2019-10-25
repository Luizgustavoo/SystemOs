/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infox.telas;

import java.sql.*;
import br.com.infox.dal.ModuloConexao;
import java.util.HashMap;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author luizz
 */
public class TelaOs extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    // A linha abaixo cria um texto de acordo com o radio button selecionado 
    private String tipo;

    /**
     * Creates new form TelaOs
     */
    public TelaOs() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    //Metodo para pesquisar clientes
    private void pesquisar_cliente() {
        String sql = "select idcli as ID, nomecli as Nome, fonecli as Fone from clientes where nomecli like ?"; //Pesquisa na tabela clientes e atribui um apelido para cada coluna
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtOsCliPesq.getText() + "%");
            rs = pst.executeQuery();
            tblCli.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void setar_campos() {
        int setar = tblCli.getSelectedRow(); //Pegando a linha que está selecionada com o comando getSelectedRow();
        txtOsCliId.setText(tblCli.getModel().getValueAt(setar, 0).toString());
    }

    //Método para cadastrar uma OS
    private void emitir_os() {
        String sql = "insert into os (tipo, situacao, equipamento, defeito, servico, tecnico, valor, idcli) values(?,?,?,?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, tipo);
            pst.setString(2, cboOsSit.getSelectedItem().toString());
            pst.setString(3, txtOsEqui.getText());
            pst.setString(4, txtOsDef.getText());
            pst.setString(5, txtOsServ.getText());
            pst.setString(6, txtOsTec.getText());
            pst.setString(7, txtOsVt.getText().replace(",", "."));
            pst.setString(8, txtOsCliId.getText());

            //Validando campos obrigatórios
            if (((txtOsEqui.getText().isEmpty()) || (txtOsDef.getText().isEmpty())) || (txtOsCliId.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos campos obrigatórios!");
            } else {

                //A linha abaixo atualiza a tabela ós com os dados do formulário
                // A estrutura abaixo é responsavel por confirmar a inserção dos dados na tabela
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Ordem de Serviço emitida com sucesso!");
                    txtOsCliId.setText(null);
                    txtOsEqui.setText(null);
                    txtOsDef.setText(null);
                    txtOsServ.setText(null);
                    txtOsTec.setText(null);
                    txtOsVt.setText(null);

                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //Método para pesquisar uma ordem de serviço
    private void pesquisar_os() {
        //A linha abaixo cria uma caixa de entrad do tipo JOptionPane
        String num_os = JOptionPane.showInputDialog("Número da OS");
        String sql = "select * from os where idos =" + num_os;
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                txtOs.setText(rs.getString(1));
                txtDate.setText(rs.getString(2));
                //Setando radion button
                String rbtTipo = rs.getString(3);
                if (rbtTipo.equals("OS")) {
                    rbtOs.setSelected(true);
                    tipo = "OS";
                } else {
                    rbtOrc.setSelected(true);
                    tipo = "Orçamento";
                }
                cboOsSit.setSelectedItem(rs.getString(4));
                txtOsEqui.setText(rs.getString(5));
                txtOsDef.setText(rs.getString(6));
                txtOsServ.setText(rs.getString(7));
                txtOsTec.setText(rs.getString(8));
                txtOsVt.setText(rs.getString(9));
                txtOsCliId.setText(rs.getString(10));
                // Evitando problemas de usuário
                btnOsAdc.setEnabled(false);
                txtOsCliPesq.setEnabled(false);
                tblCli.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(null, "OS não cadastrada!");
            }
        } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException e) {
            JOptionPane.showMessageDialog(null, "OS INVÁLIDA!");
            //System.out.println(e);
        } catch (Exception e2) {
            JOptionPane.showMessageDialog(null, e2);
        }
    }

    //Método para alterar OS
    private void alterar_os() {
        String sql = "update os set tipo=?, situacao =?, equipamento=?, defeito=?, servico=?, tecnico=?, valor=? where idos=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, tipo);
            pst.setString(2, cboOsSit.getSelectedItem().toString());
            pst.setString(3, txtOsEqui.getText());
            pst.setString(4, txtOsDef.getText());
            pst.setString(5, txtOsServ.getText());
            pst.setString(6, txtOsTec.getText());
            pst.setString(7, txtOsVt.getText().replace(",", "."));
            pst.setString(8, txtOs.getText());

            //Validando campos obrigatórios
            if (((txtOsEqui.getText().isEmpty()) || (txtOsDef.getText().isEmpty())) || (txtOsCliId.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos campos obrigatórios!");
            } else {

                //A linha abaixo atualiza a tabela ós com os dados do formulário
                // A estrutura abaixo é responsavel por confirmar a inserção dos dados na tabela
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Ordem de Serviço alterada com sucesso!");
                    txtOs.setText(null);
                    txtDate.setText(null);
                    txtOsCliId.setText(null);
                    txtOsEqui.setText(null);
                    txtOsDef.setText(null);
                    txtOsServ.setText(null);
                    txtOsTec.setText(null);
                    txtOsVt.setText(null);
                    //Habilitando os botões
                    btnOsAdc.setEnabled(true);
                    txtOsCliPesq.setEnabled(true);
                    tblCli.setVisible(true);

                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //Método para excluir uma OS
    private void excluir_os() {
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir essa OS?", " Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from os where idos =?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtOs.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "OS excluida com sucesso!");
                    txtOs.setText(null);
                    txtDate.setText(null);
                    txtOsCliId.setText(null);
                    txtOsEqui.setText(null);
                    txtOsDef.setText(null);
                    txtOsServ.setText(null);
                    txtOsTec.setText(null);
                    txtOsVt.setText(null);
                    //Habilitando os botões
                    btnOsAdc.setEnabled(true);
                    txtOsCliPesq.setEnabled(true);
                    tblCli.setVisible(true);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }
    
    //Criando método para imprimir os
    private void imprimir_os(){
        //imprimindo uma os
         int confirma = JOptionPane.showConfirmDialog(null, "Deseja imprimir esta Ordem de Serviço?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION){
            // Imprimindo o  relatório com o framework JasperReports
            try {
                //Usando a classe HashMap para criar um filtro
                HashMap filtro = new HashMap();
                filtro.put("idos", Integer.parseInt(txtOs.getText()));
                // Usando a classe JasperPrint
                JasperPrint print = JasperFillManager.fillReport("C:/reports/os.jasper",filtro, conexao);
                //A linha abaixo exibe o relatório atravéz da classe JasperViewer
                JasperViewer.viewReport(print, false);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollBar1 = new javax.swing.JScrollBar();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtOs = new javax.swing.JTextField();
        txtDate = new javax.swing.JTextField();
        rbtOrc = new javax.swing.JRadioButton();
        rbtOs = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        cboOsSit = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        txtOsCliPesq = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtOsCliId = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCli = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        txtOsEqui = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtOsDef = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtOsServ = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtOsTec = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtOsVt = new javax.swing.JTextField();
        btnOsAdc = new javax.swing.JButton();
        btnOsPesq = new javax.swing.JButton();
        btnOsAlt = new javax.swing.JButton();
        btnOsRem = new javax.swing.JButton();
        btnPrint = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("OS");
        setMaximumSize(new java.awt.Dimension(640, 480));
        setMinimumSize(new java.awt.Dimension(640, 480));
        setPreferredSize(new java.awt.Dimension(640, 480));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Nº OS");

        jLabel2.setText("Data");

        txtOs.setEditable(false);
        txtOs.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N

        txtDate.setEditable(false);
        txtDate.setFont(new java.awt.Font("Tahoma", 1, 9)); // NOI18N

        buttonGroup1.add(rbtOrc);
        rbtOrc.setText("Orçamento");
        rbtOrc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtOrcActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtOs);
        rbtOs.setText("Ordem de Serviço");
        rbtOs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtOsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(rbtOrc)
                        .addGap(30, 30, 30)
                        .addComponent(rbtOs))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(txtOs, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(txtDate))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtOs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtOrc)
                    .addComponent(rbtOs))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jLabel3.setText("Situação");

        cboOsSit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Na Bancada", "Entrega OK", "Orçamento REPROVADO", "Aguardando Aprovação", "Aguardando peças", "Abandonado", "Executando Conserto", "Garantia", " " }));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Cliente"));

        txtOsCliPesq.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtOsCliPesqKeyReleased(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/iconfinder_search_322497.png"))); // NOI18N

        jLabel5.setText("*ID");

        txtOsCliId.setEditable(false);

        tblCli.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Nome", "Fone"
            }
        ));
        tblCli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCliMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblCli);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(txtOsCliPesq, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addGap(42, 42, 42)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtOsCliId, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(txtOsCliId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel4)
                    .addComponent(txtOsCliPesq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel6.setText("*Equipamento");

        jLabel7.setText("*Defeito");

        jLabel8.setText("Serviço");

        jLabel9.setText("Técnico");

        jLabel10.setText("Valor. Total");

        txtOsVt.setText("0");

        btnOsAdc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/create.png"))); // NOI18N
        btnOsAdc.setToolTipText("Adicionar");
        btnOsAdc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsAdc.setPreferredSize(new java.awt.Dimension(64, 64));
        btnOsAdc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsAdcActionPerformed(evt);
            }
        });

        btnOsPesq.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/read.png"))); // NOI18N
        btnOsPesq.setToolTipText("Pesquisar");
        btnOsPesq.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsPesq.setPreferredSize(new java.awt.Dimension(64, 64));
        btnOsPesq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsPesqActionPerformed(evt);
            }
        });

        btnOsAlt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/update.png"))); // NOI18N
        btnOsAlt.setToolTipText("Alterar");
        btnOsAlt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsAlt.setPreferredSize(new java.awt.Dimension(64, 64));
        btnOsAlt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsAltActionPerformed(evt);
            }
        });

        btnOsRem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/delete.png"))); // NOI18N
        btnOsRem.setToolTipText("Remover");
        btnOsRem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsRem.setPreferredSize(new java.awt.Dimension(64, 64));
        btnOsRem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsRemActionPerformed(evt);
            }
        });

        btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/pesquisar.png"))); // NOI18N
        btnPrint.setToolTipText("Imprimir OS");
        btnPrint.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPrint.setPreferredSize(new java.awt.Dimension(64, 64));
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboOsSit, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtOsTec, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtOsVt, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE))
                            .addComponent(txtOsServ)
                            .addComponent(txtOsDef)
                            .addComponent(txtOsEqui))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(112, 112, 112)
                .addComponent(btnOsAdc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnOsPesq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnOsAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnOsRem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnOsAdc, btnOsAlt, btnOsPesq, btnOsRem, btnPrint});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(cboOsSit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtOsEqui, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtOsDef, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtOsServ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtOsTec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtOsVt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnOsAdc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnOsPesq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnOsAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnOsRem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(77, Short.MAX_VALUE))
        );

        setBounds(0, 0, 640, 472);
    }// </editor-fold>//GEN-END:initComponents

    private void txtOsCliPesqKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOsCliPesqKeyReleased
        //Chamando o método pesquisar clientes
        pesquisar_cliente();
    }//GEN-LAST:event_txtOsCliPesqKeyReleased

    private void tblCliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCliMouseClicked
        // Chamando método setar
        setar_campos();
    }//GEN-LAST:event_tblCliMouseClicked

    private void rbtOrcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtOrcActionPerformed
        // Atribuindo um texto a variavel tipo de acordo com o botao selecionado 
        tipo = "Orçamento";
    }//GEN-LAST:event_rbtOrcActionPerformed

    private void rbtOsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtOsActionPerformed
        //Atribuindo um texto a variavel tipo de acordo com o botao selecionado 
        tipo = "OS";
    }//GEN-LAST:event_rbtOsActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // Ao abrir o form marcar o radio button orçamento
        rbtOrc.setSelected(true);
        tipo = "Orçamento";
    }//GEN-LAST:event_formInternalFrameOpened

    private void btnOsAdcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsAdcActionPerformed
        // Chamando método emitir OS
        emitir_os();
    }//GEN-LAST:event_btnOsAdcActionPerformed

    private void btnOsPesqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsPesqActionPerformed
        // Chamando método de pesquisar OS
        pesquisar_os();
    }//GEN-LAST:event_btnOsPesqActionPerformed

    private void btnOsAltActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsAltActionPerformed
        // Chamando método alterar OS
        alterar_os();
    }//GEN-LAST:event_btnOsAltActionPerformed

    private void btnOsRemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsRemActionPerformed
        //Chamando método para excluir OS
        excluir_os();
    }//GEN-LAST:event_btnOsRemActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        // Chamando o método para imprimir uma os
        imprimir_os();
    }//GEN-LAST:event_btnPrintActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOsAdc;
    private javax.swing.JButton btnOsAlt;
    private javax.swing.JButton btnOsPesq;
    private javax.swing.JButton btnOsRem;
    private javax.swing.JButton btnPrint;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboOsSit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rbtOrc;
    private javax.swing.JRadioButton rbtOs;
    private javax.swing.JTable tblCli;
    private javax.swing.JTextField txtDate;
    private javax.swing.JTextField txtOs;
    private javax.swing.JTextField txtOsCliId;
    private javax.swing.JTextField txtOsCliPesq;
    private javax.swing.JTextField txtOsDef;
    private javax.swing.JTextField txtOsEqui;
    private javax.swing.JTextField txtOsServ;
    private javax.swing.JTextField txtOsTec;
    private javax.swing.JTextField txtOsVt;
    // End of variables declaration//GEN-END:variables
}
