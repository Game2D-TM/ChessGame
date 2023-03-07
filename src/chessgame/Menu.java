package chessgame;

import chessgame.gui.ImageManager;
import chessgame.gui.ImagePanel;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Menu extends javax.swing.JFrame {

    private BufferedImage backgroundImage;
    public Menu() {
        initComponents();
    }
    
    void init() {
        backgroundImage = ImageManager.getInstance().images.get("menu-background");
        Image scale = backgroundImage.getScaledInstance(500, 500, 0);
        panel = new ImagePanel(scale);
        playWithAIBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        playWithAIBtn.setBackground(new java.awt.Color(255, 255, 255));
        playWithAIBtn.setFont(new java.awt.Font("Mongolian Baiti", 1, 36)); // NOI18N
        playWithAIBtn.setForeground(new java.awt.Color(0, 0, 0));
        playWithAIBtn.setText("Play");
        playWithAIBtn.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        playWithAIBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                
            }
        });

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addGap(235, 235, 235)
                .addComponent(playWithAIBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(241, Short.MAX_VALUE))
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                .addContainerGap(395, Short.MAX_VALUE)
                .addComponent(playWithAIBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(264, 264, 264))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JPanel();
        playWithAIBtn = new javax.swing.JButton();
        labelGame = new javax.swing.JLabel();
        playBtn = new javax.swing.JButton();
        settingBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        playWithAIBtn.setBackground(new java.awt.Color(25, 26, 25));
        playWithAIBtn.setFont(new java.awt.Font("Monospaced", 1, 24)); // NOI18N
        playWithAIBtn.setForeground(new java.awt.Color(78, 159, 61));
        playWithAIBtn.setText("Play With AI");
        playWithAIBtn.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(216, 233, 168), new java.awt.Color(30, 81, 40)));
        playWithAIBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playWithAIBtnActionPerformed(evt);
            }
        });

        labelGame.setBackground(new java.awt.Color(25, 26, 25));
        labelGame.setFont(new java.awt.Font("Monospaced", 1, 48)); // NOI18N
        labelGame.setForeground(new java.awt.Color(78, 159, 61));
        labelGame.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelGame.setText("Chess Game");

        playBtn.setBackground(new java.awt.Color(25, 26, 25));
        playBtn.setFont(new java.awt.Font("Monospaced", 1, 24)); // NOI18N
        playBtn.setForeground(new java.awt.Color(78, 159, 61));
        playBtn.setText("Play");
        playBtn.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(216, 233, 168), new java.awt.Color(30, 81, 40)));
        playBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playBtnActionPerformed(evt);
            }
        });

        settingBtn.setBackground(new java.awt.Color(25, 26, 25));
        settingBtn.setFont(new java.awt.Font("Monospaced", 1, 24)); // NOI18N
        settingBtn.setForeground(new java.awt.Color(78, 159, 61));
        settingBtn.setText("Setting");
        settingBtn.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(216, 233, 168), new java.awt.Color(30, 81, 40)));
        settingBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addGap(187, 187, 187)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(settingBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelGame, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(playBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(playWithAIBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(196, 196, 196))
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(labelGame)
                .addGap(127, 127, 127)
                .addComponent(playBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                .addGap(60, 60, 60)
                .addComponent(playWithAIBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                .addGap(60, 60, 60)
                .addComponent(settingBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                .addGap(140, 140, 140))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void playWithAIBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playWithAIBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_playWithAIBtnActionPerformed

    private void playBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_playBtnActionPerformed

    private void settingBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_settingBtnActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosing



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel labelGame;
    private javax.swing.JPanel panel;
    private javax.swing.JButton playBtn;
    private javax.swing.JButton playWithAIBtn;
    private javax.swing.JButton settingBtn;
    // End of variables declaration//GEN-END:variables
}
