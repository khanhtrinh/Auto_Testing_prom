package main;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import TestPath2Webdriver.WebdriverCommand;

public class test2 extends JPanel implements ActionListener {

	static private final String newline = "\n";
    JButton openButton, saveButton, exitButton;
    JTextArea log;
    JFileChooser fc;
    static JFrame frame = new JFrame("FileChooserDemo");
    public static String result="";
    
    
    public test2() {
        super(new BorderLayout());
 
        //Create the log first
        log = new JTextArea(15,50);
        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(log);
 
        //Create a file chooser
        fc = new JFileChooser();
 
        openButton = new JButton("Open File");
        openButton.addActionListener(this);
 
        JPanel buttonPanel = new JPanel(); //use FlowLayout
        buttonPanel.add(openButton);
 
        //Add the buttons and the log to this panel.
        add(buttonPanel, BorderLayout.PAGE_START);
        add(logScrollPane, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e) {
   	 
        int returnVal = fc.showOpenDialog(test2.this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            //This is where a real application would open the file.
            log.append("Opening: " + file.getPath() + "." + newline);
            result = file.getPath();
        } else {
            log.append("Open command cancelled by user." + newline);
        }
        frame.setVisible(false);
        
        try{
        	process();
        }catch (Exception ex) {
			ex.printStackTrace();
		}
    }
    
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Add content to the window.
        frame.add(new test2());
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    
    public static void process() throws Exception{
    	String result1 = editString(result);
    	//System.out.println(result + "\n" + result1);
    	
    	boolean b1 = true;
		
		b1 = runTestCase(result1, "http://localhost:8080/nckh/login.jsp", 00, true);
		
		if (b1){
			System.out.println("PASS");
		} else {
			System.out.println("FAIL");
		}
    }
    
    public static String editString(String input){
    	String escaped = input.replace("\\", "\\\\");
    	return escaped;
    }
    
    
    //------------------------------------------------------------------------------------
    public static boolean runTestCase(String filename, String url, int delay, boolean islogging){
		boolean b1 = false;
		try{
			
			WebdriverCommand webcom = new WebdriverCommand("abc",  new File(filename));
			
			System.out.println("FILE OK");
			
			b1 = webcom.runTestCaseWithUrl(url, delay, islogging);
			webcom.quitDriver();
			
		} catch (Exception e){
			
		}
		
		return b1;
	}
    
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE); 
                createAndShowGUI();
            }
        });
        
    }
}
