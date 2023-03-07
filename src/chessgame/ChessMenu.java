package chessgame;

import chessgame.gui.ImageManager;
import chessgame.gui.ImagePanel;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class ChessMenu extends JFrame{
    
    private ImagePanel panel;
    private JButton playWithAIBtn;
    private JButton playBtn;
    private JButton settingBtn;
    private JLabel labelGame;
    private Thread playBtnThread;
    private Thread playWithAIThread;
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows Classic".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChessMenu().setVisible(true);
            }
        });
    }
    
    public ChessMenu() {
        init();
        setTitle(Game.GAME_TITLE);
        setLocationRelativeTo(null);
        setResizable(false);
        setIconImage(ImageManager.getInstance().images.get("knight_black"));
    }
    
    void playBtnMouseClicked(MouseEvent evt) {
        setVisible(false);
        if(playWithAIThread != null && playWithAIThread.isAlive()) try {
            playWithAIThread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(ChessMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        playBtnThread = new Thread(new Game().start(false, this));
        playBtnThread.start();
    }
    void settingBtnMouseClicked(MouseEvent evt) {
        Game.PLAYER1_NAME = JOptionPane.showInputDialog("Input Player 1 Name: ").toUpperCase();
        Game.PLAYER2_NAME = JOptionPane.showInputDialog("Input Player 2 Name: ").toUpperCase();
    }
    void playWithAIBtnMouseClicked(MouseEvent evt) {
        setVisible(false);
        if(playBtnThread != null && playBtnThread.isAlive()) try {
            playBtnThread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(ChessMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        playWithAIThread = new Thread(new Game().start(true, this));
        playWithAIThread.start();
    }
    
    void init() {
        Image backgroundImage = ImageManager.getInstance().images.get("menu-background");
        panel = new ImagePanel(backgroundImage);
        playWithAIBtn = new javax.swing.JButton();
        labelGame = new javax.swing.JLabel();
        playBtn = new javax.swing.JButton();
        settingBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        playWithAIBtn.setBackground(new java.awt.Color(25, 26, 25));
        playWithAIBtn.setFont(new java.awt.Font("Monospaced", 1, 24)); // NOI18N
        playWithAIBtn.setForeground(new java.awt.Color(78, 159, 61));
        playWithAIBtn.setText("Play With AI");
        playWithAIBtn.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(216, 233, 168), new java.awt.Color(30, 81, 40)));
        playWithAIBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                playWithAIBtnMouseClicked(evt);
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
        playBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                playBtnMouseClicked(evt);
            }
        });

        settingBtn.setBackground(new java.awt.Color(25, 26, 25));
        settingBtn.setFont(new java.awt.Font("Monospaced", 1, 24)); // NOI18N
        settingBtn.setForeground(new java.awt.Color(78, 159, 61));
        settingBtn.setText("Setting");
        settingBtn.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(216, 233, 168), new java.awt.Color(30, 81, 40)));
        settingBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                settingBtnMouseClicked(evt);
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
                .addComponent(playBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                .addGap(60, 60, 60)
                .addComponent(playWithAIBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                .addGap(60, 60, 60)
                .addComponent(settingBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                .addGap(140, 140, 140))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }
}
