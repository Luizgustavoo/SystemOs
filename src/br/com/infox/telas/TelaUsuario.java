/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infox.telas;

/**
 *
 * @author luizz
 */
//Importando bibliotecas do banco de dados
import java.sql.*;
import br.com.infox.dal.ModuloConexao;
import javax.swing.JOptionPane;

public class TelaUsuario extends javax.swing.JInternalFrame {

    Connection conexao = null;
    //PreparedStatement e ResultSet são variaveis do pacote importado do sql que permite executar ações do BDD dentro do sistema..
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaUsuario
     */
    public TelaUsuario() {
        initComponents();
        conexao = ModuloConexao.conector();
    }
//Método para consultar usuários

    private void consultar() {
        //Criando a variavel sql do tipo string para obter o id do usuario.
        String sql = "select * from usuarios where iduser=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtUsuId.getText());
            rs = pst.executeQuery();
            if (rs.next()) {
                txtUsuNome.setText(rs.getString(2)); //Para o campo de texto da tela de usuario ele busca pela coluna 2 da tabela usuario que tem o ''NOME'' Cadastrado
                txtUsuFone.setText(rs.getString(3)); //Para o campo de texto da tela de usuario ele busca pela coluna 3 da tabela usuario que tem o ''FONE'' Cadastrado
                txtUsuLogin.setText(rs.getString(4)); //Para o campo de texto da tela de usuario ele busca pela coluna 4 da tabela usuario que tem o ''LOGIN'' Cadastrado
                txtUsuSenha.setText(rs.getString(5)); //Para o campo de texto da tela de usuario ele busca pela coluna 5 da tabela usuario que tem o ''SENHA'' Cadastrado
                //A linha abaixo refere - se ao combo box 
                cboUsuPerfil.setSelectedItem(rs.getString(6));
            } else {
                JOptionPane.showMessageDialog(null, "Usuário não cadastrado!");
                //As linhas abaixo limpam os campos quando se não encontra um usuário cadastrado, facilitando o cadastramento
                txtUsuNome.setText(null);
                txtUsuFone.setText(null);
                txtUsuLogin.setText(null);
                txtUsuSenha.setText(null);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //Método para adicionar usuários
    private void adicionar() {
        String sql = "insert into usuarios(iduser, usuario, fone, login, senha , perfil) values (?, ?, ?, ?, ?, ?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtUsuId.getText());
            pst.setString(2, txtUsuNome.getText());
            pst.setString(3, txtUsuFone.getText());
            pst.setString(4, txtUsuLogin.getText());
            pst.setString(5, txtUsuSenha.getText());
            pst.setString(6, cboUsuPerfil.getSelectedItem().toString());
            //Validação dos campos obrigatórios
            if ((((txtUsuId.getText().isEmpty()) || (txtUsuNome.getText().isEmpty())) || (txtUsuLogin.getText().isEmpty())) || (txtUsuSenha.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos campos obrigatórios!");
            } else {

                //A linha abaixo atualiza a tabela usuarios com os dados do formulário
                // A estrutura abaixo é responsavel por confirmar a inserção dos dados na tabela
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Usuário adicionado com sucesso!");
                    txtUsuId.setText(null);
                    txtUsuNome.setText(null);
                    txtUsuFone.setText(null);
                    txtUsuLogin.setText(null);
                    txtUsuSenha.setText(null);

                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //Criando método para alterar dados do usuário
    private void alterar() {
        String sql = "update usuarios set usuario=?, fone=?, login=?, senha=?, perfil=? where iduser =?";
        try {
            pst=conexao.prepareStatement(sql);
            pst.setString(1, txtUsuNome.getText());
            pst.setString(2, txtUsuFone.getText());
            pst.setString(3, txtUsuLogin.getText());
            pst.setString(4, txtUsuSenha.getText());
            pst.setString(5, cboUsuPerfil.getSelectedItem().toString());
            pst.setString(6, txtUsuId.getText());
             if ((((txtUsuId.getText().isEmpty()) || (txtUsuNome.getText().isEmpty())) || (txtUsuLogin.getText().isEmpty())) || (txtUsuSenha.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos campos obrigatórios!");
            } else {

                //A linha abaixo atualiza a tabela usuarios com os dados do formulário
                // A estrutura abaixo é responsavel por confirmar a alteração dos dados na tabela
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados do usuário alterados com sucesso!");
                    txtUsuId.setText(null);
                    txtUsuNome.setText(null);
                    txtUsuFone.setText(null);
                    txtUsuLogin.setText(null);
                    txtUsuSenha.setText(null);

                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    //Método responsável pela remoção de usuários
    private void remover(){
        //A estrutura abaixo confirma a remoção do usuário
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover esse usuário?", "Atenção", JOptionPane.YES_NO_OPTION);
        if(confirma == JOptionPane.YES_OPTION){
            String sql = "delete from usuarios where iduser=?";
            try {
                pst=conexao.prepareStatement(sql);
                pst.setString(1, txtUsuId.getText());
               int apagado = pst.executeUpdate();
               if(apagado>0){
                   JOptionPane.showMessageDialog(null, "Usuário removido com sucesso!");
                    txtUsuId.setText(null);
                    txtUsuNome.setText(null);
                    txtUsuFone.setText(null);
                    txtUsuLogin.setText(null);
                    txtUsuSenha.setText(null);
               }
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtUsuId = new javax.swing.JTextField();
        txtUsuNome = new javax.swing.JTextField();
        txtUsuLogin = new javax.swing.JTextField();
        cboUsuPerfil = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        txtUsuFone = new javax.swing.JTextField();
        btnUsuCreate = new javax.swing.JButton();
        btnUsuRead = new javax.swing.JButton();
        btnUsuUpdate = new javax.swing.JButton();
        btnUsuDelete = new javax.swing.JButton();
        txtUsuSenha = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Usuários");
        setMinimumSize(new java.awt.Dimension(640, 480));
        setPreferredSize(new java.awt.Dimension(640, 480));

        jLabel1.setText("*ID");

        jLabel2.setText("*Nome");

        jLabel3.setText("*Login");

        jLabel4.setText("*Senha");

        jLabel5.setText("*Perfil");

        cboUsuPerfil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "admin", "user", " " }));

        jLabel6.setText("Fone");

        btnUsuCreate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/create.png"))); // NOI18N
        btnUsuCreate.setToolTipText("Adicionar ");
        btnUsuCreate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuCreate.setPreferredSize(new java.awt.Dimension(64, 64));
        btnUsuCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuCreateActionPerformed(evt);
            }
        });

        btnUsuRead.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/read.png"))); // NOI18N
        btnUsuRead.setToolTipText("Consultar");
        btnUsuRead.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuRead.setPreferredSize(new java.awt.Dimension(64, 64));
        btnUsuRead.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuReadActionPerformed(evt);
            }
        });

        btnUsuUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/update.png"))); // NOI18N
        btnUsuUpdate.setToolTipText("Alterar");
        btnUsuUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuUpdate.setPreferredSize(new java.awt.Dimension(64, 64));
        btnUsuUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuUpdateActionPerformed(evt);
            }
        });

        btnUsuDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/delete.png"))); // NOI18N
        btnUsuDelete.setToolTipText("Apagar");
        btnUsuDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuDelete.setPreferredSize(new java.awt.Dimension(64, 64));
        btnUsuDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuDeleteActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 0, 0));
        jLabel7.setText("* Campos Obrigatórios");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtUsuFone, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtUsuSenha, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addGap(98, 98, 98)
                                    .addComponent(btnUsuRead, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(71, 71, 71)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnUsuUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnUsuDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cboUsuPerfil, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtUsuLogin))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtUsuId, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(267, 267, 267)
                                .addComponent(jLabel7))
                            .addComponent(txtUsuNome, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(71, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(btnUsuCreate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnUsuCreate, btnUsuDelete, btnUsuRead, btnUsuUpdate});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtUsuId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtUsuNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtUsuLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtUsuFone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(cboUsuPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUsuSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnUsuCreate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUsuRead, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUsuUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUsuDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(126, Short.MAX_VALUE))
        );

        setBounds(0, 0, 640, 472);
    }// </editor-fold>//GEN-END:initComponents

    private void btnUsuReadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuReadActionPerformed
        // Chamando método consultar
        consultar();
    }//GEN-LAST:event_btnUsuReadActionPerformed

    private void btnUsuCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuCreateActionPerformed
        // Chamando o método adicionar 
        adicionar();
    }//GEN-LAST:event_btnUsuCreateActionPerformed

    private void btnUsuUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuUpdateActionPerformed
        // Chamando método alterar
        alterar();
    }//GEN-LAST:event_btnUsuUpdateActionPerformed

    private void btnUsuDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuDeleteActionPerformed
        // Chamando método remover
        remover();
    }//GEN-LAST:event_btnUsuDeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnUsuCreate;
    private javax.swing.JButton btnUsuDelete;
    private javax.swing.JButton btnUsuRead;
    private javax.swing.JButton btnUsuUpdate;
    private javax.swing.JComboBox<String> cboUsuPerfil;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField txtUsuFone;
    private javax.swing.JTextField txtUsuId;
    private javax.swing.JTextField txtUsuLogin;
    private javax.swing.JTextField txtUsuNome;
    private javax.swing.JTextField txtUsuSenha;
    // End of variables declaration//GEN-END:variables
}
