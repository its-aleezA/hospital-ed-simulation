package htms;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;

public class Main {
    public static void main(String[] args) {
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setLayout(new GridLayout(1, false));
        shell.setText("Hospital Traffic Management System");

        // Create UI elements
        Label title = new Label(shell, SWT.NONE);
        title.setText("Emergency Department Simulation");

        // Create text areas for display
        Text[] texts = new Text[24];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = new Text(shell, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
            texts[i].setSize(200, 100);
        }

        // Initialize and start simulation
        EmergencyDepartment ed = new EmergencyDepartment(5, 10, 3, 4);
        ed.startSimulation(display, texts);

        shell.pack();
        shell.open();
        
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        
        ed.stopSimulation();
        display.dispose();
    }
}