package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Class for the main background Panel of the GUI
 * @author Francesco Corsini
 */
public class GUIDinamicPanel extends JPanel {

    private BufferedImage image;

    /**
     * Constructor
     * @param string the image of the table
     * @throws IOException if cannot open the table
     */
    public GUIDinamicPanel(String string) throws IOException {
        super();
        image = ImageIO.read(new File(string));
        setSize(image.getWidth(), image.getHeight());
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);

    }

}
