/*
 * Copyright (C) 2015 joulupunikki joulupunikki@gmail.communist.invalid.
 *
 *  Disclaimer of Warranties and Limitation of Liability.
 *
 *     The creators and distributors offer this software as-is and
 *     as-available, and make no representations or warranties of any
 *     kind concerning this software, whether express, implied, statutory,
 *     or other. This includes, without limitation, warranties of title,
 *     merchantability, fitness for a particular purpose, non-infringement,
 *     absence of latent or other defects, accuracy, or the presence or
 *     absence of errors, whether or not known or discoverable.
 *
 *     To the extent possible, in no event will the creators or distributors
 *     be liable on any legal theory (including, without limitation,
 *     negligence) or otherwise for any direct, special, indirect,
 *     incidental, consequential, punitive, exemplary, or other losses,
 *     costs, expenses, or damages arising out of the use of this software,
 *     even if the creators or distributors have been advised of the
 *     possibility of such losses, costs, expenses, or damages.
 *
 *     The disclaimer of warranties and limitation of liability provided
 *     above shall be interpreted in a manner that, to the extent possible,
 *     most closely approximates an absolute disclaimer and waiver of
 *     all liability.
 *
 */
package gui;

import game.Game;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import util.C;
import util.FN;
import util.Util;
import util.UtilG;
import util.WindowSize;

/**
 * Main menu JPanel for selecting human players and PBEM.
 *
 * @author joulupunikki
 */
public class MainMenu extends JPanel {
    // pointer to GUI

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Gui gui;
    private Game game;
    private WindowSize ws;
    private JButton pbem;
    private JCheckBox[] hc;
    PBEMGui test;
    private BufferedImage bi;
    public MainMenu(Gui gui) {
        this.gui = gui;
        ws = Gui.getWindowSize();

        game = gui.getGame();
        this.bi = Util.loadImage(FN.S_CATHED3_PCX, ws.is_double, gui.getPallette(), 640, 480);
        setUpWindow();
        setUpButtons();
    }

    public void setUpButtons() {
        pbem = new JButton("PBEM Off");
        pbem.setFont(ws.font_large);
        pbem.setBorder(BorderFactory.createLineBorder(C.COLOR_GOLD));
        this.add(pbem);
        pbem.setBounds(ws.mm_pbem_x, ws.mm_pbem_y,
                ws.mm_pbem_w, ws.mm_pbem_h);
        pbem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.getEfs_ini().pbem.pbem = !game.getEfs_ini().pbem.pbem;
                initPBEMButton();
            }
        });
    }

    private void initPBEMButton() {
        if (game.getEfs_ini().pbem.pbem) {
            pbem.setText("PBEM On");
        } else {
            pbem.setText("PBEM Off");
        }
    }

    public void initMainMenu() {
        initPBEMButton();
        setDefaultHumanControl();
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setDefaultHumanControl() {

        for (JCheckBox jcb : hc) {
            jcb.setSelected(false);
        }
        hc[0].setSelected(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        renderMainMenu(g);

    }

    public void renderMainMenu(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(bi, null, 0, 0);

        g.setColor(C.COLOR_GOLD);
        g.drawString("Select human player(s)", ws.human_control_selection_x, ws.human_control_selection_y - 15);
    }

    public void setUpWindow() {

        ItemListener il = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
//                Object source = e.getItemSelectable();
//                System.out.println("source = " + source);
//                int source_nr = -1;
//                for (int i = 0; i < hc.length; i++) {
//                    if (source == hc[i]) {
//                        source_nr = i;
//                        System.out.println("source_nr = " + source_nr);
//                        break;
//                    }
//
//                }
//                gui.getCurrentState().selectFactionControl(e, source_nr);

                gui.getCurrentState().selectFactionControl(e, hc);
            }
        };
        hc = new JCheckBox[C.NR_FACTIONS];
        for (int i = 0; i < hc.length; i++) {
            hc[i] = new JCheckBox(Util.getFactionName(i));
            hc[i].addItemListener(il);
            this.add(hc[i]);
            hc[i].setBackground(Color.BLACK);
            hc[i].setForeground(C.COLOR_GOLD);
            hc[i].setBounds(ws.human_control_selection_x,
                    i * ws.human_control_selection_h + ws.human_control_selection_y,
                    ws.human_control_selection_w, ws.human_control_selection_h);

        }

//        JTextField select_human_players = new JTextField("Select human player(s)");
//        this.add(select_human_players);
//        select_human_players.setBackground(Color.BLACK);
//        select_human_players.
//            select_human_players.setForeground(C.COLOR_GOLD);
//        select_human_players.setEditable(false);
//            select_human_players.setBounds(ws.human_control_selection_x,
//                    - ws.human_control_selection_h + ws.human_control_selection_y,
//                    ws.human_control_selection_w, ws.human_control_selection_h);
        JButton play = new JButton("Play");
        play.setBorder((BorderFactory.createLineBorder(C.COLOR_GOLD)));

        play.setBackground(Color.BLACK);
        play.setForeground(C.COLOR_GOLD);
        play.setFont(ws.font_large);
        this.add(play);
        play.setBounds(ws.stack_window_exit_button_x, ws.stack_window_exit_button_y,
                ws.stack_window_exit_button_w, ws.stack_window_exit_button_h);
        play.setEnabled(true);
//        test = new PBEMGui();
//        test.getDATAHashes();

        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.getCurrentState().pressPlayButton();
//                test.testDATAHashes(gui);
//                System.exit(0);
            }
        });
    }

    /**
     * The first Main Menu displayed to players.
     */
    public static class W1 extends JPanel {

        /**
         *
         */
        private static final long serialVersionUID = 1L;
        private Gui gui;
        private Game game;
        private WindowSize ws;
        private JButton start_new;
        private JButton load_game;
        private JButton quit_game;
        private JButton credits;
        private JLabel name;
        private JLabel name2;
        private BufferedImage bi;

        public W1(Gui gui) {
            this.gui = gui;
            ws = Gui.getWindowSize();

            game = gui.getGame();
            this.bi = Util.loadImage(FN.S_CATHED3_PCX, ws.is_double, gui.getPallette(), 640, 480);

            setUpButtons();
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            renderMainMenu(g);

        }

        public void renderMainMenu(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(bi, null, 0, 0);
            drawTitles(g2d);
            //bi = UtilG.loadFLCFirst(FN.S_FLC + FN.F_S + "ADVISOR.FLC", ws.is_double, pallette, 175, 150);
//            bi = UtilG.loadFLCFirst(FN.S_FLC + FN.F_S + "AFIGHTER.FLC", ws.is_double, pallette, 175, 150);
//            g2d.drawImage(bi, null, ws.sw_flc_x, ws.sw_flc_y);
            //UtilG.drawStringGrad(g2d, "Gradient Font Test", ws, 50, 50, true);
        }

        public void setUpButtons() {
            //setUpName();
            setUpStartNew();
            setUpLoadSaved();
            setUpQuit();
            setUpCredits();
        }

        public void drawTitles(Graphics2D g2d) {
            FontMetrics fm = this.getFontMetrics(ws.font_large);
            String s = "Emperor Of The Fading Suns";
            int string_w = fm.stringWidth(s);
            UtilG.drawStringGrad(g2d, s, ws.font_large, (ws.main_window_width - string_w) / 2, ws.mm_sn_y * 3 / 7, 1, false);
            s = "Phoenix remake/patch " + FN.S_VERSION;
            string_w = fm.stringWidth(s);
            UtilG.drawStringGrad(g2d, s, ws.font_large, (ws.main_window_width - string_w) / 2, ws.mm_sn_y * 3 / 7 + ws.sb_h, 1, false);
        }

        public void setUpName() {
            name = new JLabel("Emperor Of The Fading Suns");
            name.setBounds(ws.mm_sn_x, ws.mm_sn_y * 3 / 7,
                    ws.mm_sn_w, ws.sb_h);
            name.setHorizontalAlignment(SwingConstants.CENTER);
            name.setFont(ws.font_large);
            name.setOpaque(true);
            name.setBackground(C.COLOR_RED_DARK);
            name.setBorder(BorderFactory.createLineBorder(C.COLOR_GOLD));
            this.add(name);
            name2 = new JLabel("Phoenix remake/patch " + FN.S_VERSION);
            name2.setBounds(ws.mm_sn_x, ws.mm_sn_y * 3 / 7 + ws.sb_h,
                    ws.mm_sn_w, ws.sb_h);
            name2.setHorizontalAlignment(SwingConstants.CENTER);
            name2.setFont(ws.font_large);
            name2.setOpaque(true);
            name2.setBackground(C.COLOR_RED_DARK);
            name2.setBorder(BorderFactory.createLineBorder(C.COLOR_GOLD));
            this.add(name2);
        }

        public void setUpStartNew() {
            start_new = new JButton("Start New");
            start_new.setFont(ws.font_large);
            start_new.setBorder(BorderFactory.createLineBorder(C.COLOR_GOLD));
            this.add(start_new);
            start_new.setBounds(ws.mm_sn_x, ws.mm_sn_y,
                    ws.mm_sn_w, ws.sb_h);
            start_new.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gui.getCurrentState().pressStartNew();
                }
            });
        }

        public void setUpLoadSaved() {
            load_game = new JButton("Load Saved Game");
            load_game.setFont(ws.font_large);
            load_game.setBorder(BorderFactory.createLineBorder(C.COLOR_GOLD));
            this.add(load_game);
            load_game.setBounds(ws.mm_sn_x, ws.mm_sn_y + ws.sb_h * 5 / 2,
                    ws.mm_sn_w, ws.sb_h);
            load_game.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gui.getCurrentState().pressLoadGame();
                }
            });
        }

        public void setUpQuit() {
            quit_game = new JButton("Quit Game");
            quit_game.setFont(ws.font_large);
            quit_game.setBorder(BorderFactory.createLineBorder(C.COLOR_GOLD));
            this.add(quit_game);
            quit_game.setBounds(ws.mm_sn_x, ws.mm_sn_y + 2 * ws.sb_h * 5 / 2,
                    ws.mm_sn_w, ws.sb_h);
            quit_game.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gui.getCurrentState().pressQuit();
                }
            });
        }

        public void setUpCredits() {
            credits = new JButton("Credits");
            credits.setFont(ws.font_large);
            credits.setBorder(BorderFactory.createLineBorder(C.COLOR_GOLD));
            this.add(credits);
            credits.setBounds(ws.mm_sn_x, ws.mm_sn_y + 3 * ws.sb_h * 5 / 2,
                    ws.mm_sn_w, ws.sb_h);
            credits.addActionListener((ActionEvent e) -> {
                gui.showInfoWindow("Emperor Of The Fading Suns software product is copyright Segasoft and/or Holistic Design Inc. (http://www.holistic-design.com) "
                        + "\n" + "Fading Suns is a trademark and copyright of Holistic Design Inc. (http://www.holistic-design.com)"
                        + "\n" + "Phoenix patch by joulupunikki and others (https://github.com/joulupunikki/Phoenix)"
                        + "\n" + "Phoenix patch is powered by Apache Commons (http://www.apache.org)");
            });
        }
    }

}
