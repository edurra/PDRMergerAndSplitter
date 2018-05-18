package p1;
import java.awt.*; 
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;

import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
public class UnirPDF extends Frame implements ActionListener, WindowListener {
		private Label lblFile1;    
		private TextField path1;
	   private Button browse1;  
	   private Label lblFile2;     
	   private TextField path2;  
	   private Button browse2;  
	   private Button merge_button;  
	   private Label lblFinal;   

	   private String file1 = "";
	   private String file2 = "";
	   private boolean file1Selected = false;
	   private boolean file2Selected = false;
	   private String last_path = null;
	 
	   public UnirPDF () {
	      setLayout(new FlowLayout());
	       
	      lblFile1 = new Label("Archivo 1"); 
	      add(lblFile1);                    
	 
	      path1 = new TextField();
	      add(path1);                    
	 
	      browse1 = new Button("Buscar");   
	      add(browse1);                    
	 
	      browse1.addActionListener(this);
	      
	     
	      lblFile2 = new Label("Archivo 2");
	      add(lblFile2);                   
	 
	      path2 = new TextField();
	      add(path2);                    
	 
	      browse2 = new Button("Buscar"); 
	      add(browse2);                    
	      
	      merge_button = new Button("Unir");   
	      add(merge_button);                   
	 
	      merge_button.addActionListener(this);
	 
	      lblFinal = new Label("");  
	      add(lblFinal);    
	      
	      browse2.addActionListener(this);
	      path1.setEditable(false);
	      path2.setEditable(false);
	      path1.setColumns(30);
	      path2.setColumns(30);
	      lblFinal.setPreferredSize(new Dimension(380,20));

	      setTitle("PDF Merger"); 
	      setSize(400, 200);     
	 
	      setResizable(false);
	      
	 
	      setVisible(true);       
	      
	      addWindowListener(this);
	 
	      
	   }
	 
	   // The entry main() method
	   public static void main(String[] args) {
	      // Invoke the constructor to setup the GUI, by allocating an instance
		   try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	      UnirPDF app = new UnirPDF();
	      
	       
	   }
	 
	  
	   
	   public void windowClosing(WindowEvent evt) {
		   this.setVisible(false);
		   file1 = "";
  	     file2 = "";
  	     file1Selected = false;
  	     file2Selected = false;
  	     path1.setText("");
  	     path2.setText("");
	   }
	   
	   @Override
	   public void actionPerformed(ActionEvent evt) {
		   
		   JFileChooser chooser;
		   if (last_path == null) {
			  chooser = new JFileChooser();
		   }
		   else {
			   chooser = new JFileChooser(last_path);
		   }

	       	FileFilter filter = new FileNameExtensionFilter("pdf", "pdf");
	        chooser.setFileFilter(filter);
	        

		   	if (evt.getSource() == browse1) {
		        int returnVal = chooser.showOpenDialog(null);
		        if(returnVal == JFileChooser.APPROVE_OPTION) {
		            
		           String fullPath = chooser.getSelectedFile().getAbsolutePath();
		           String name = chooser.getSelectedFile().getName();
		           file1 = fullPath;
		           file1Selected = true;
		           path1.setText(name);
		           last_path = chooser.getSelectedFile().getPath();
		        }
		   	}
		   	if (evt.getSource() == browse2) {
		        int returnVal = chooser.showOpenDialog(null);
		        if(returnVal == JFileChooser.APPROVE_OPTION) {
		        	
		        	String fullPath = chooser.getSelectedFile().getAbsolutePath();
			        String name = chooser.getSelectedFile().getName();
			        file2 = fullPath;
			        path2.setText(name);
			        file2Selected = true;
			        last_path = chooser.getSelectedFile().getPath();

		        }
		   	}
		   	if (evt.getSource() == merge_button) {
		        if(!(file1Selected && file2Selected)) {
		        	lblFinal.setText("Falta un archivo");
		        }
		        else {
		        	 int returnVal = chooser.showOpenDialog(null);
				        if(returnVal == JFileChooser.APPROVE_OPTION) {
				        	String directory = chooser.getCurrentDirectory().getPath();
				        	String name = chooser.getSelectedFile().getName();
				        	String fullPath = directory + "\\" + name +".pdf";
				        	File file = new File(fullPath);
				        	if(file.exists()) {
				        		lblFinal.setText("Ya existe un archivo con ese nombre");
				        	}
				        	else {
				        		PDDocument doc1 = null;
				        		PDDocument doc2 = null;
				        		File pdf1 = new File(file1);
				        		File pdf2 = new File(file2);

				        		try {
									 doc1 = PDDocument.load(pdf1);
								} catch (InvalidPasswordException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
				        		try {
									 doc2 = PDDocument.load(pdf2);
								} catch (InvalidPasswordException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
				        	    PDFMergerUtility PDFmerger = new PDFMergerUtility();
				        	    PDFmerger.setDestinationFileName(fullPath);
				        	    
				        	    try {
									PDFmerger.addSource(pdf1);
								} catch (FileNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
				        	    try {
									PDFmerger.addSource(pdf2);
								} catch (FileNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
				        	     try {
									PDFmerger.mergeDocuments();
								} catch (IOException e) {
									// TODO Auto-gerated catch block
									e.printStackTrace();
								}
				        	     try {
									doc1.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
				        	     try {
									doc2.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
				        	     lblFinal.setText("Documentos unidos: " + name);
				        	     file1 = "";
				        	     file2 = "";
				        	     file1Selected = false;
				        	     file2Selected = false;
				        	     path1.setText("");
				        	     path2.setText("");
				        	    
				        	}
				        }
		        }
		   	}
	   }

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}
