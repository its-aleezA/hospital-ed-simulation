import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.JFaceResources;

import java.util.Random;

import org.eclipse.jface.resource.ColorDescriptor;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class GUI {

	protected Shell shell;
	private LocalResourceManager localResourceManager;
	

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			GUI window = new GUI();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		createResourceManager();
		shell.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(5, 149, 165))));
		shell.setSize(450, 300);
		shell.setText("SWT Application");
		
		Label lblHospitalEmergencyDepartment = new Label(shell, SWT.NONE);
		lblHospitalEmergencyDepartment.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(255, 255, 255))));
		lblHospitalEmergencyDepartment.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 14, SWT.BOLD)));
		lblHospitalEmergencyDepartment.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(5, 149, 165))));
		lblHospitalEmergencyDepartment.setAlignment(SWT.CENTER);
		lblHospitalEmergencyDepartment.setBounds(83, 54, 277, 55);
		lblHospitalEmergencyDepartment.setText("Hospital Emergency Department Simulation");
		
		Button btnStartSimulation = new Button(shell, SWT.NONE);
		btnStartSimulation.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				/////////////////////start simulation event/////////////////////////
				try {
					Simulation window = new Simulation();
					shell.dispose();
					window.open();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnStartSimulation.setBounds(173, 149, 104, 25);
		btnStartSimulation.setText("Start Simulation");

	}
	private void createResourceManager() {
		localResourceManager = new LocalResourceManager(JFaceResources.getResources(),shell);
	}
}
