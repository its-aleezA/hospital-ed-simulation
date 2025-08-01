package htms;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import java.util.Random;
import org.eclipse.jface.resource.ColorDescriptor;

public class Simulation {

    private Shell shell;
    private LocalResourceManager resourceManager;
    private Font titleFont;
    private Font sectionFont;
    private Font statusFont;
    
    private Color availableColor;
    private Color occupiedColor;
    private Color headerColor;
    
    // Text field arrays for organized access
    private Text[] resuscitationBedStatus = new Text[3];
    private Text[] acuteBedStatus = new Text[5]; 
    private Text[] subAcuteBedStatus = new Text[10];
    private Text[] physicianStatus = new Text[4];
    private Text inpatientQueueText;
    private Text patientAssignmentText;
    
    // Waiting Patients
    private Text waitingRoomText;
    private Text triageDistributionText;
    
    private EmergencyDepartment ed;
    private Random random = new Random();

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
        ed.startPatientScheduler();
        ed.startPatientProcessingScheduler(
            display,
            // Resuscitation beds (3)
            resuscitationBedStatus[0], resuscitationBedStatus[1], resuscitationBedStatus[2],
            // Acute beds (5)
            acuteBedStatus[0], acuteBedStatus[1], acuteBedStatus[2], acuteBedStatus[3], acuteBedStatus[4],
            // Sub-acute beds (10)
            subAcuteBedStatus[0], subAcuteBedStatus[1], subAcuteBedStatus[2], subAcuteBedStatus[3], subAcuteBedStatus[4],
            subAcuteBedStatus[5], subAcuteBedStatus[6], subAcuteBedStatus[7], subAcuteBedStatus[8], subAcuteBedStatus[9],
            // Physicians (4)
            physicianStatus[0], physicianStatus[1], physicianStatus[2], physicianStatus[3],
            // Other fields
            inpatientQueueText,
            patientAssignmentText
        );

        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        
        // Clean up resources
        ed.stopScheduler();
        if (titleFont != null) titleFont.dispose();
        if (sectionFont != null) sectionFont.dispose();
        if (statusFont != null) statusFont.dispose();
        if (availableColor != null) availableColor.dispose();
        if (occupiedColor != null) occupiedColor.dispose();
        if (headerColor != null) headerColor.dispose();
        display.dispose();
    }

    protected void createContents() {
        shell = new Shell();
        resourceManager = new LocalResourceManager(JFaceResources.getResources(), shell);
        shell.setSize(1000, 800);
        shell.setText("Hospital Emergency Department - Simulation Dashboard");
        
        // Create colors
        availableColor = new Color(shell.getDisplay(), 220, 255, 220); // Light green
        occupiedColor = new Color(shell.getDisplay(), 255, 220, 220);  // Light red
        headerColor = new Color(shell.getDisplay(), 5, 149, 165);      // Teal
        
        // Create fonts
        titleFont = new Font(shell.getDisplay(), "Segoe UI", 16, SWT.BOLD);
        sectionFont = new Font(shell.getDisplay(), "Segoe UI", 12, SWT.BOLD);
        statusFont = new Font(shell.getDisplay(), "Segoe UI", 10, SWT.NORMAL);
        
        // Main layout
        GridLayout gridLayout = new GridLayout(1, false);
        gridLayout.marginWidth = 10;
        gridLayout.marginHeight = 10;
        gridLayout.verticalSpacing = 15;
        shell.setLayout(gridLayout);
        
        // Title
        Label titleLabel = new Label(shell, SWT.CENTER);
        titleLabel.setText("EMERGENCY DEPARTMENT MONITORING");
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(resourceManager.create(ColorDescriptor.createFrom(new RGB(5, 149, 165))));
        titleLabel.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
        
        // Main container
        Composite mainComposite = new Composite(shell, SWT.NONE);
        mainComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        mainComposite.setLayout(new GridLayout(3, false));
        
        // Create all bed sections
        createBedSection(mainComposite, "RESUSCITATION BEDS", 3, "rb");
        createBedSection(mainComposite, "ACUTE BEDS", 5, "ab");
        createBedSection(mainComposite, "SUB-ACUTE BEDS", 10, "sb");
        
        // Physicians Section
        Group physiciansGroup = new Group(shell, SWT.NONE);
        physiciansGroup.setText("PHYSICIANS STATUS");
        physiciansGroup.setFont(sectionFont);
        physiciansGroup.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
        physiciansGroup.setLayout(new GridLayout(4, true));
        
        for (int i = 0; i < 4; i++) {
            Composite physicianComp = new Composite(physiciansGroup, SWT.BORDER);
            physicianComp.setLayout(new GridLayout(1, false));
            physicianComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
            
            Label physicianLabel = new Label(physicianComp, SWT.CENTER);
            physicianLabel.setText("Physician " + (i+1));
            physicianLabel.setFont(statusFont);
            
            physicianStatus[i] = new Text(physicianComp, SWT.CENTER | SWT.READ_ONLY);
            physicianStatus[i].setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
            physicianStatus[i].setFont(statusFont);
            physicianStatus[i].setText("Available");
            physicianStatus[i].setBackground(availableColor);
        }
        
        // Statistics Section
        Composite statsComposite = new Composite(shell, SWT.NONE);
        statsComposite.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, true, false));
        statsComposite.setLayout(new GridLayout(2, true));
        
        inpatientQueueText = createStatBox(statsComposite, "In-Patient Queue", "0");
        patientAssignmentText = createStatBox(statsComposite, "Last Assignment", "");
        createStatBox(statsComposite, "Patients Waiting", "0");
        createStatBox(statsComposite, "Total Treated", "0");
        
        // Waiting Room Status Group
        Group waitingGroup = new Group(shell, SWT.NONE);
        waitingGroup.setText("WAITING ROOM");
        waitingGroup.setLayout(new GridLayout(1, false));
        waitingGroup.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
        
        waitingRoomText = new Text(waitingGroup, SWT.MULTI | SWT.READ_ONLY | SWT.V_SCROLL | SWT.BORDER);
        waitingRoomText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        waitingRoomText.setFont(statusFont);

        // Triage Distribution Group
        Group triageGroup = new Group(shell, SWT.NONE);
        triageGroup.setText("TRIAGE CATEGORIES");
        triageGroup.setLayout(new GridLayout(1, false));
        triageGroup.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
        
        triageDistributionText = new Text(triageGroup, SWT.MULTI | SWT.READ_ONLY | SWT.BORDER);
        triageDistributionText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        triageDistributionText.setFont(statusFont);
    }
    
    private void createBedSection(Composite parent, String title, int bedCount, String prefix) {
        Group group = new Group(parent, SWT.NONE);
        group.setText(title);
        group.setFont(sectionFont);
        group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        group.setLayout(new GridLayout(1, false));
        
        for (int i = 0; i < bedCount; i++) {
            Composite bedComp = new Composite(group, SWT.BORDER);
            bedComp.setLayout(new GridLayout(2, false));
            bedComp.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
            
            Label bedLabel = new Label(bedComp, SWT.NONE);
            bedLabel.setText("Bed " + (i < 9 ? "0" + (i+1) : (i+1)));
            bedLabel.setFont(statusFont);
            
            Text bedStatus = new Text(bedComp, SWT.CENTER | SWT.READ_ONLY);
            bedStatus.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
            bedStatus.setText("Available");
            bedStatus.setBackground(availableColor);
            bedStatus.setFont(statusFont);
            
            // Store in appropriate array
            if (prefix.equals("rb")) {
                resuscitationBedStatus[i] = bedStatus;
            } else if (prefix.equals("ab")) {
                acuteBedStatus[i] = bedStatus;
            } else if (prefix.equals("sb")) {
                subAcuteBedStatus[i] = bedStatus;
            }
        }
    }
    
    private Text createStatBox(Composite parent, String label, String initialValue) {
        Composite comp = new Composite(parent, SWT.BORDER);
        comp.setLayout(new GridLayout(1, false));
        comp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        
        Label statLabel = new Label(comp, SWT.CENTER);
        statLabel.setText(label);
        statLabel.setFont(statusFont);
        
        Text statValue = new Text(comp, SWT.CENTER | SWT.READ_ONLY);
        statValue.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        statValue.setText(initialValue);
        statValue.setFont(statusFont);
        
        return statValue;
    }
}