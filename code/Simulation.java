public class Simulation {

    private Shell shell;
    private Text[] resuscitationBedsText;
    private Text[] acuteBedsText;
    private Text[] subAcuteBedsText;
    private Text[] physicianText;
    private EmergencyDepartment ed;

    public static void main(String[] args) {
        Simulation window = new Simulation();
        window.open();
    }

    public void open() {
        Display display = Display.getDefault();
        createContents();
        shell.open();
        shell.layout();

        ed = new EmergencyDepartment(3, 5, 7, 4);
        ed.startPatientProcessingScheduler(display, resuscitationBedsText, acuteBedsText, subAcuteBedsText, physicianText);

        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        ed.stopScheduler();
        display.dispose();
    }

    private void createContents() {
        shell = new Shell();
        shell.setSize(800, 600);
        shell.setText("ED Simulation");

        resuscitationBedsText = createTextWidgets(3);
        acuteBedsText = createTextWidgets(5);
        subAcuteBedsText = createTextWidgets(7);
        physicianText = createTextWidgets(4);
    }

    private Text[] createTextWidgets(int count) {
        Text[] widgets = new Text[count];
        for (int i = 0; i < count; i++) {
            widgets[i] = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
            widgets[i].setBounds(10, 30 * i, 200, 20);
        }
        return widgets;
    }
}*/package project;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.JFaceResources;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.eclipse.jface.resource.ColorDescriptor;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.swt.widgets.Text;

import org.eclipse.swt.SWT;

public class Simulation {

    private Shell shell;
    private Text[] resuscitationBedsText;
    private Text[] acuteBedsText;
    private Text[] subAcuteBedsText;
    private Text[] physicianText;

	private LocalResourceManager localResourceManager;
	private Text rb1;
	private Text rb1t;
	private Text ab1;
	private Text ab1t;
	private Text sb1;
	private Text sb1t;
	private Text rb2;
	private Text rb3;
	private Text rb2t;
	private Text rb3t;
	private Text ab2;
	private Text ab3;
	private Text ab4;
	private Text ab5;
	private Text ab2t;
	private Text ab3t;
	private Text ab4t;
	private Text ab5t;
	private Text sb2;
	private Text sb3;
	private Text sb4;
	private Text sb5;
	private Text sb2t;
	private Text sb3t;
	private Text sb4t;
	private Text sb5t;
	private Text sb6;
	private Text sb6t;
	private Text sb7t;
	private Text sb7;
	private Text sb8;
	private Text sb8t;
	private Text sb9;
	private Text sb9t;
	private Text sb10;
	private Text sb10t;
	private Label lblPhysicians;
	private Text p1;
	private Text p1t;
	private Text p2t;
	private Text p2;
	private Text p3;
	private Text p3t;
	private Text p4t;
	private Text p4;

	private EmergencyDepartment ed; // Create ED with beds and physicians
	Random random = new Random();
	private Text text;
	private Text text_1;
	private Text text_2;
	private Text text_3;
	private Text patient;
	private Text bed_assignment;
	private Text inpatient;
	private Text physician_assignment;

	
	public static void main(String[] args) {
		try {
			Simulation window = new Simulation();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	

	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		
		ed = new EmergencyDepartment(5, 10, 3, 4);
        
        text = new Text(shell, SWT.BORDER);
        text.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
        text.setEditable(false);
        text.setText(ed.physicians[0].level);
        text.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(176, 208, 255))));
        text.setBounds(128, 476, 76, 21);
        
        text_1 = new Text(shell, SWT.BORDER);
        text_1.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
        text_1.setEditable(false);
        text_1.setText(ed.physicians[1].level);
        text_1.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(176, 208, 255))));
        text_1.setBounds(263, 476, 76, 21);
        
        text_2 = new Text(shell, SWT.BORDER);
        text_2.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
        text_2.setEditable(false);
        text_2.setText(ed.physicians[2].level);
        text_2.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(176, 208, 255))));
        text_2.setBounds(400, 476, 76, 21);
        
        text_3 = new Text(shell, SWT.BORDER);
        text_3.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
        text_3.setEditable(false);
        text_3.setText(ed.physicians[3].level);
        text_3.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(176, 208, 255))));
        text_3.setBounds(534, 476, 76, 21);
        
        ed.startPatientScheduler();
        ed.startPatientProcessingScheduler(display, rb1t, rb2t, rb3t, ab1t, ab2t, ab3t, ab4t, ab5t, sb1t, sb2t, sb3t, sb4t, sb5t, sb6t, sb7t, sb8t, sb9t, sb10t, p1t, p2t, p3t, p4t, inpatient, patient);
        
        patient = new Text(shell, SWT.READ_ONLY);
        patient.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
        patient.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(176, 208, 255))));
        patient.setBounds(131, 571, 520, 21);
        
        bed_assignment = new Text(shell, SWT.READ_ONLY);
        bed_assignment.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
        bed_assignment.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(176, 208, 255))));
        bed_assignment.setBounds(131, 598, 520, 21);
        
        inpatient = new Text(shell, SWT.READ_ONLY);
        inpatient.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
        inpatient.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(176, 208, 255))));
        inpatient.setBounds(131, 653, 520, 21);
        
        physician_assignment = new Text(shell, SWT.READ_ONLY);
        physician_assignment.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
        physician_assignment.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(176, 208, 255))));
        physician_assignment.setBounds(131, 626, 520, 21);
                
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
        ed.stopScheduler();
        display.dispose();
	}


	protected void createContents() {
		shell = new Shell();
		
        shell.setSize(800, 736);
        shell.setText("ED Simulation");

        createResourceManager();
		shell.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(176, 208, 255))));
				
		Label lblSimulation = new Label(shell, SWT.NONE);
		lblSimulation.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
		lblSimulation.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 14, SWT.BOLD)));
		lblSimulation.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(176, 208, 255))));
		lblSimulation.setAlignment(SWT.CENTER);
		lblSimulation.setBounds(327, 20, 111, 41);
		lblSimulation.setText("Simulation");
		
		Label lblResuscitationBed = new Label(shell, SWT.NONE);
		lblResuscitationBed.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 10, SWT.BOLD)));
		lblResuscitationBed.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(176, 208, 255))));
		lblResuscitationBed.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
		lblResuscitationBed.setBounds(25, 57, 120, 22);
		lblResuscitationBed.setText("Resuscitation Beds");
		
		Label lblAcuteBeds = new Label(shell, SWT.NONE);
		lblAcuteBeds.setText("Acute Beds");
		lblAcuteBeds.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
		lblAcuteBeds.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 10, SWT.BOLD)));
		lblAcuteBeds.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(176, 208, 255))));
		lblAcuteBeds.setBounds(28, 141, 96, 22);
		
		Label lblSubacuteBeds = new Label(shell, SWT.NONE);
		lblSubacuteBeds.setText("Sub-Acute Beds");
		lblSubacuteBeds.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
		lblSubacuteBeds.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 10, SWT.BOLD)));
		lblSubacuteBeds.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(176, 208, 255))));
		lblSubacuteBeds.setBounds(25, 215, 96, 22);
		
		rb1 = new Text(shell, SWT.BORDER);
		rb1.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(176, 208, 255))));
		rb1.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
		rb1.setEditable(false);
		rb1.setText("Bed 01");
		rb1.setBounds(131, 85, 76, 21);
		
		rb1t = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
		rb1t.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
		rb1t.setEditable(false);
		rb1t.setBounds(131, 112, 126, 21);
		
		ab1 = new Text(shell, SWT.BORDER);
		ab1.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(176, 208, 255))));
		ab1.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
		ab1.setEditable(false);
		ab1.setText("Bed 01");
		ab1.setBounds(131, 161, 76, 21);
		
		ab1t = new Text(shell, SWT.BORDER);
		ab1t.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
		ab1t.setEditable(false);
		ab1t.setBounds(131, 188, 126, 21);
		
		sb1 = new Text(shell, SWT.BORDER);
		sb1.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(176, 208, 255))));
		sb1.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
		sb1.setEditable(false);
		sb1.setText("Bed 01");
		sb1.setBounds(131, 241, 76, 21);
		
		sb1t = new Text(shell, SWT.BORDER);
		sb1t.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
		sb1t.setEditable(false);
		sb1t.setBounds(131, 268, 126, 21);
		
		rb2 = new Text(shell, SWT.BORDER);
		rb2.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(176, 208, 255))));
		rb2.setText("Bed 02");
		rb2.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
		rb2.setEditable(false);
		rb2.setBounds(266, 85, 76, 21);
		
		rb3 = new Text(shell, SWT.BORDER);
		rb3.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(176, 208, 255))));
		rb3.setText("Bed 03");
		rb3.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
		rb3.setEditable(false);
		rb3.setBounds(403, 85, 76, 21);
		
		rb2t = new Text(shell, SWT.BORDER);
		rb2t.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
		rb2t.setEditable(false);
		rb2t.setBounds(266, 112, 131, 21);
		
		rb3t = new Text(shell, SWT.BORDER);
		rb3t.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
		rb3t.setEditable(false);
		rb3t.setBounds(403, 112, 128, 21);
		
		ab2 = new Text(shell, SWT.BORDER);
		ab2.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(176, 208, 255))));
		ab2.setText("Bed 02");
		ab2.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
		ab2.setEditable(false);
		ab2.setBounds(266, 161, 76, 21);
		
		ab3 = new Text(shell, SWT.BORDER);
		ab3.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(176, 208, 255))));
		ab3.setText("Bed 03");
		ab3.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
		ab3.setEditable(false);
		ab3.setBounds(403, 161, 76, 21);
		
		ab4 = new Text(shell, SWT.BORDER);
		ab4.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(176, 208, 255))));
		ab4.setText("Bed 04");
		ab4.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
		ab4.setEditable(false);
		ab4.setBounds(537, 161, 76, 21);
		
		ab5 = new Text(shell, SWT.BORDER);
		ab5.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(176, 208, 255))));
		ab5.setText("Bed 05");
		ab5.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
		ab5.setEditable(false);
		ab5.setBounds(661, 161, 76, 21);
		
		ab2t = new Text(shell, SWT.BORDER);
		ab2t.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
		ab2t.setEditable(false);
		ab2t.setBounds(266, 188, 131, 21);
		
		ab3t = new Text(shell, SWT.BORDER);
		ab3t.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
		ab3t.setEditable(false);
		ab3t.setBounds(403, 188, 128, 21);
		
		ab4t = new Text(shell, SWT.BORDER);
		ab4t.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
		ab4t.setEditable(false);
		ab4t.setBounds(537, 188, 118, 21);
		
		ab5t = new Text(shell, SWT.BORDER);
		ab5t.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
		ab5t.setEditable(false);
		ab5t.setBounds(661, 188, 113, 21);
		
		sb2 = new Text(shell, SWT.BORDER);
		sb2.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(176, 208, 255))));
		sb2.setText("Bed 02");
		sb2.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
		sb2.setEditable(false);
		sb2.setBounds(266, 241, 76, 21);
		
		sb3 = new Text(shell, SWT.BORDER);
		sb3.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(176, 208, 255))));
		sb3.setText("Bed 03");
		sb3.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
		sb3.setEditable(false);
		sb3.setBounds(403, 241, 76, 21);
		
		sb4 = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
		sb4.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(176, 208, 255))));
		sb4.setText("Bed 04");
		sb4.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
		sb4.setBounds(537, 241, 76, 21);
		
		sb5 = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
		sb5.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(176, 208, 255))));
		sb5.setText("Bed 05");
		sb5.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
		sb5.setBounds(661, 241, 76, 21);
		
		sb2t = new Text(shell, SWT.BORDER);
		sb2t.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
		sb2t.setEditable(false);
		sb2t.setBounds(266, 268, 131, 21);
		
		sb3t = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
		sb3t.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
		sb3t.setBounds(403, 268, 128, 21);
		
		sb4t = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
		sb4t.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
		sb4t.setBounds(537, 268, 118, 21);
		
		sb5t = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
		sb5t.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
		sb5t.setBounds(661, 268, 113, 21);
		
		sb6 = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
		sb6.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(176, 208, 255))));
		sb6.setText("Bed 06");
		sb6.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
		sb6.setBounds(131, 317, 76, 21);
		
		sb6t = new Text(shell, SWT.BORDER);
		sb6t.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
		sb6t.setEditable(false);
		sb6t.setBounds(131, 344, 126, 21);
		
		sb7t = new Text(shell, SWT.BORDER);
		sb7t.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
		sb7t.setEditable(false);
		sb7t.setBounds(266, 344, 131, 21);
		
		sb7 = new Text(shell, SWT.BORDER);
		sb7.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(176, 208, 255))));
		sb7.setText("Bed 07");
		sb7.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
		sb7.setEditable(false);
		sb7.setBounds(266, 317, 76, 21);
		
		sb8 = new Text(shell, SWT.BORDER);
		sb8.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(176, 208, 255))));
		sb8.setText("Bed 08");
		sb8.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
		sb8.setEditable(false);
		sb8.setBounds(403, 317, 76, 21);
		
		sb8t = new Text(shell, SWT.BORDER);
		sb8t.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
		sb8t.setEditable(false);
		sb8t.setBounds(403, 344, 128, 21);
		
		sb9 = new Text(shell, SWT.BORDER);
		sb9.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(176, 208, 255))));
		sb9.setText("Bed 09");
		sb9.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
		sb9.setEditable(false);
		sb9.setBounds(537, 317, 76, 21);
		
		sb9t = new Text(shell, SWT.BORDER);
		sb9t.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
		sb9t.setEditable(false);
		sb9t.setBounds(537, 344, 118, 21);
		
		sb10 = new Text(shell, SWT.BORDER);
		sb10.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(176, 208, 255))));
		sb10.setText("Bed 10");
		sb10.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
		sb10.setEditable(false);
		sb10.setBounds(661, 317, 76, 21);
		
		sb10t = new Text(shell, SWT.BORDER);
		sb10t.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
		sb10t.setEditable(false);
		sb10t.setBounds(661, 344, 113, 21);
		
		lblPhysicians = new Label(shell, SWT.NONE);
		lblPhysicians.setText("Physicians");
		lblPhysicians.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
		lblPhysicians.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 10, SWT.BOLD)));
		lblPhysicians.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(176, 208, 255))));
		lblPhysicians.setBounds(25, 428, 76, 22);
		
		p1 = new Text(shell, SWT.BORDER);
		p1.setText("Physician 01");
		p1.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(176, 208, 255))));
		p1.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
		p1.setEditable(false);
		p1.setBounds(128, 448, 76, 21);
		
		p1t = new Text(shell, SWT.BORDER);
		p1t.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
		p1t.setEditable(false);
		p1t.setBounds(128, 503, 129, 21);
		
		p2t = new Text(shell, SWT.BORDER);
		p2t.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
		p2t.setEditable(false);
		p2t.setBounds(263, 503, 134, 21);
		
		p2 = new Text(shell, SWT.BORDER);
		p2.setText("Physician 02");
		p2.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(176, 208, 255))));
		p2.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
		p2.setEditable(false);
		p2.setBounds(263, 448, 76, 21);
		
		p3 = new Text(shell, SWT.BORDER);
		p3.setText("Physician 03");
		p3.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(176, 208, 255))));
		p3.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
		p3.setEditable(false);
		p3.setBounds(400, 448, 76, 21);
		
		p3t = new Text(shell, SWT.BORDER);
		p3t.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
		p3t.setEditable(false);
		p3t.setBounds(400, 503, 131, 21);
		
		p4t = new Text(shell, SWT.BORDER);
		p4t.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
		p4t.setEditable(false);
		p4t.setBounds(534, 503, 121, 21);
		
		p4 = new Text(shell, SWT.BORDER);
		p4.setText("Physician 04");
		p4.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(176, 208, 255))));
		p4.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(2, 79, 87))));
		p4.setEditable(false);
		p4.setBounds(534, 448, 76, 21);

	}
	
	private void createResourceManager() {
		localResourceManager = new LocalResourceManager(JFaceResources.getResources(),shell);
	}

}
