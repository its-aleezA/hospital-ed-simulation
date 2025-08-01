package htms;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Font;
import org.eclipse.jface.resource.ColorDescriptor;
import org.eclipse.swt.events.MouseTrackAdapter;

public class GUI {

    protected Shell shell;
    private LocalResourceManager localResourceManager;
    private Font titleFont;
    private Font buttonFont;

    public static void main(String[] args) {
        try {
            GUI window = new GUI();
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
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        // Clean up fonts
        if (titleFont != null) titleFont.dispose();
        if (buttonFont != null) buttonFont.dispose();
    }

    protected void createContents() {
        shell = new Shell();
        createResourceManager();
        
        // Set shell properties
        shell.setSize(600, 400);
        shell.setText("Hospital Emergency Department");
        shell.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(5, 149, 165))));
        
        // Use GridLayout for better component arrangement
        GridLayout gridLayout = new GridLayout(1, false);
        gridLayout.marginWidth = 20;
        gridLayout.marginHeight = 20;
        gridLayout.verticalSpacing = 30;
        shell.setLayout(gridLayout);

        // Create fonts
        titleFont = new Font(shell.getDisplay(), "Segoe UI", 18, SWT.BOLD);
        buttonFont = new Font(shell.getDisplay(), "Segoe UI", 12, SWT.NORMAL);

        // Hospital logo/image placeholder (would use real image in production)
        Label logoLabel = new Label(shell, SWT.CENTER);
        logoLabel.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(5, 149, 165))));
        GridData logoData = new GridData(SWT.CENTER, SWT.CENTER, true, false);
        logoData.heightHint = 80;
        logoLabel.setLayoutData(logoData);
        logoLabel.setText("🏥 HOSPITAL EMERGENCY DEPARTMENT");
        logoLabel.setFont(titleFont);
        logoLabel.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(255, 255, 255))));

        // Welcome label
        Label welcomeLabel = new Label(shell, SWT.CENTER | SWT.WRAP);
        welcomeLabel.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(5, 149, 165))));
        welcomeLabel.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(255, 255, 255))));
        welcomeLabel.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
        welcomeLabel.setText("Welcome to the Emergency Department Traffic Management System.\n" +
                           "This simulation will help visualize patient flow and resource allocation.");
        GridData welcomeData = new GridData(SWT.CENTER, SWT.CENTER, true, false);
        welcomeData.widthHint = 500;
        welcomeLabel.setLayoutData(welcomeData);

        // Start simulation button
        Button btnStartSimulation = new Button(shell, SWT.PUSH);
        btnStartSimulation.setText("START SIMULATION");
        btnStartSimulation.setFont(buttonFont);
        GridData buttonData = new GridData(SWT.CENTER, SWT.CENTER, false, false);
        buttonData.widthHint = 200;
        buttonData.heightHint = 50;
        btnStartSimulation.setLayoutData(buttonData);
        
        // Button styling
        btnStartSimulation.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(255, 255, 255))));
        btnStartSimulation.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(5, 149, 165))));
        
        // Button hover effects
        btnStartSimulation.addMouseListener(new MouseAdapter() {
        	    @Override
        	    public void mouseDown(MouseEvent e) {
        	        btnStartSimulation.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(200, 200, 200))));
        	    }
        	    
        	    @Override
        	    public void mouseUp(MouseEvent e) {
        	        btnStartSimulation.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(230, 230, 230))));
        	    }
        	    
        	    @Override
        	    public void mouseDoubleClick(MouseEvent e) {
        	        try {
        	            Simulation window = new Simulation();
        	            shell.dispose();
        	            window.open();
        	        } catch (Exception ex) {
        	            ex.printStackTrace();
        	        }
        	    }
        	});

        	// Add separate MouseTrackListener for enter/exit events
        	btnStartSimulation.addMouseTrackListener(new MouseTrackAdapter() {
        	    @Override
        	    public void mouseEnter(MouseEvent e) {
        	        btnStartSimulation.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(230, 230, 230))));
        	    }
        	    
        	    @Override
        	    public void mouseExit(MouseEvent e) {
        	        btnStartSimulation.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(255, 255, 255))));
        	    }
        	    
        	    public void mouseDown(MouseEvent e) {
        	        btnStartSimulation.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(200, 200, 200))));
        	    }
        	        
        	    public void mouseUp(MouseEvent e) {
        	        btnStartSimulation.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(230, 230, 230))));
        	        // Move the simulation launch to mouseUp instead of mouseDoubleClick
        	        try {
        	            Simulation window = new Simulation();
        	            shell.dispose();
        	            window.open();
        	        } catch (Exception ex) {
        	            ex.printStackTrace();
        	        }
        	    }
        });

        // Footer label
        Label footerLabel = new Label(shell, SWT.CENTER);
        footerLabel.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(5, 149, 165))));
        footerLabel.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(200, 200, 200))));
        footerLabel.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 8, SWT.ITALIC)));
        footerLabel.setText("Hospital Management System v1.0 | © 2023 Emergency Department");
        GridData footerData = new GridData(SWT.CENTER, SWT.BOTTOM, true, true);
        footerLabel.setLayoutData(footerData);
    }

    private void createResourceManager() {
        localResourceManager = new LocalResourceManager(JFaceResources.getResources(), shell);
    }
}