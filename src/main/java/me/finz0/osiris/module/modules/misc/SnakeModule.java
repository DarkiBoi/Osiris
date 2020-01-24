package me.finz0.osiris.module.modules.misc;

import me.finz0.osiris.module.Module;
import me.finz0.osiris.util.snake.Board;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class SnakeModule extends Module {
    public SnakeModule() {
        super("Snake", Category.MISC);
    }

    public void onEnable(){
        EventQueue.invokeLater(() -> {
            JFrame ex = new Snake();
            ex.setVisible(true);
        });
        disable();
    }

    public class Snake extends JFrame {

        public Snake() {

            initUI();
        }

        private void initUI() {

            add(new Board());

            setResizable(false);
            pack();

            setTitle("Osiris Snake");
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }
    }
}
