package Frame;

import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class WindowViewJava extends JFrame {
    private JSlider slider;
    // a reference to the other class needs to be final
    private final UnitTestingJAva testClass;
    
    public WindowViewJava(UnitTestingJAva incomingReference) {
        // set the reference
        this.testClass = incomingReference;
        // JSlider stuff
        slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 1);
        slider.addChangeListener(new ChangeListener() {
            @Override public void stateChanged(ChangeEvent e) {
                JSlider src = (JSlider) e.getSource();
                if (src.getValueIsAdjusting()) return;
                testClass.setSomeVariable(src.getValue());
                System.out.println(testClass.getSomeVariable());
            }
        });
        // window stuff
        JFrame window = new JFrame("JSlider Test");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800, 600);
        window.add(slider);
        window.setVisible(true);
    }
}
