
package famtree;


import java.awt.Graphics;
import javax.swing.JComponent;

/**
 *
 * @author betlb
 */
public class Paint extends JComponent {
    
    @Override
    public void paint(Graphics g)
    {
  
        // draw and display the line
        g.drawLine(30, 20, 1000, 1000);
        g.drawLine(40, 20, 1000, 1000);
        g.drawLine(50, 20, 1000, 1000);
        g.drawLine(330, 20, 1000, 1000);
    }

}
