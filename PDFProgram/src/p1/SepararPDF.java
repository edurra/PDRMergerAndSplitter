package p1;
import java.awt.*; 
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Iterator;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.tools.PDFMerger;

import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SepararPDF extends Frame implements ActionListener, WindowListener  {
	private Label lblFile1;    
	private TextField path1;
   private Button browse1; 
   private Label lblPag1;   
   private TextField pag1; 
   private Label lblPag2;     
   private TextField pag2;  
   private Button separate_button;   
   private Label lblFinal; 

   private String file1 = "";
   private int pag1_num = -1;
   private int pag2_num = -1;
   private boolean file1Selected = false;
   private boolean pag1Selected = false;
   private boolean pag2Selected = false;
   private String last_path = null;
 
   public SepararPDF () {
      setLayout(new FlowLayout());
       
      lblFile1 = new Label("Archivo 1");  
      add(lblFile1);                  
 
      path1 = new TextField();
      add(path1);                    
 
      browse1 = new Button("Buscar");   
      add(browse1);                    
 
      browse1.addActionListener(this);
      
     
      lblPag1 = new Label("Pagina Inicial");  
      add(lblPag1);                   
 
      pag1 = new TextField();
      add(pag1);                     
      
      lblPag2 = new Label("Pagina Final"); 
      add(lblPag2);                    
 
      pag2 = new TextField();
      add(pag2);                    
 
      separate_button = new Button("Separar");   
      add(separate_button);                 
 
      separate_button.addActionListener(this);
 
      lblFinal = new Label(""); 
      add(lblFinal);    
      
      path1.setEditable(false);
      path1.setColumns(30);
      pag1.setColumns(10);
      pag2.setColumns(10);
      lblFinal.setPreferredSize(new Dimension(380,20));

      setTitle("PDF Splitter");  
      setSize(400, 200);        
 
      setResizable(false);
      
 
      setVisible(true);       
      
      addWindowListener(this);
 
      
   }
 
   public static void main(String[] args) {
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

      SepararPDF app = new SepararPDF();
      
   }
 
   
   public void windowClosing(WindowEvent evt) {
	      this.setVisible(false); 
	      file1Selected = false;
	 	   pag1Selected = false;
	 	   pag2Selected = false;
	 	   pag1.setText("");
	 	   pag2.setText("");
	 	   path1.setText("");
	 	   file1 = "";
	 	   pag1_num = -1;
	 	   pag2_num = -1;
   }
   
   @Override
   public void actionPerformed(ActionEvent evt) {
	   
	   JFileChooser chooser;
	   
	   //Si ya se ha buscado un archivo, buscamos en la misma carpeta
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
	   
	   	if (evt.getSource() == separate_button) {
	   		try {
		     pag1_num = Integer.parseInt(pag1.getText());
		     pag1Selected = true;
	   		} catch (NumberFormatException e) {
	   			lblFinal.setText("Número de página inicial erróneo");
	   			pag1Selected = false;
	   		}
	   		try {
			    pag2_num = Integer.parseInt(pag2.getText());
			    pag2Selected = true;

		   		} catch (NumberFormatException e) {
		   		pag2Selected = false;

		   	}		     
	        if(!(file1Selected && pag1Selected && pag2Selected)) {
	        	lblFinal.setText("Falta archivo o página");
	        }
	        else if ((pag1Selected && pag2Selected) && (pag1_num>pag2_num)) {
	        	lblFinal.setText("página inicial mayor que la final");
	        }
	        else {
	        	 int returnVal = chooser.showOpenDialog(null);
			     if(returnVal == JFileChooser.APPROVE_OPTION) {
		        	String directory = chooser.getCurrentDirectory().getPath();
		        	String name = chooser.getSelectedFile().getName();
		        	String fullPath_new = directory + "\\" + name +".pdf";
	
		        	File file_new = new File(fullPath_new);
		        	if(file_new.exists()) {
		        		lblFinal.setText("Ya existe un archivo con ese nombre");
		        	}
		        	else {
		        		File original = new File(file1);
		        		PDDocument document = null;
		        		try {
							document = PDDocument.load(original);
						} catch (InvalidPasswordException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
		        		
		        		if(document.getNumberOfPages()<pag2_num) {
		        			lblFinal.setText("El documento no tiene tantas páginas");
		        			System.out.println(document.getNumberOfPages());
		        			System.out.println(pag2_num);

		        		}
		        		else if(pag1_num<=0) {
		        			lblFinal.setText("Número mínimo de páginas inválido");
		        		}
		        		else {
			        		Splitter splitter = new Splitter();
			        		
			        		splitter.setStartPage(pag1_num);
			        		splitter.setEndPage(pag2_num);
			        		splitter.setSplitAtPage(pag2_num-pag1_num+1);
			        		java.util.List<PDDocument> lista = null;
			        		try {
								 lista = splitter.split(document);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			        	   
			        	     if(lista.size()==1) {
			        	    	try {
									lista.get(0).save(fullPath_new);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
			        	    	try {
									lista.get(0).close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
			        	     }
			        	     
	
		        		}
		        		
		        		try {
							document.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		        		file1Selected = false;
		     	 	   pag1Selected = false;
		     	 	   pag2Selected = false;
		     	 	   lblFinal.setText("Documento creado");
		     	 	   pag1.setText("");
		     	 	   pag2.setText("");
		     	 	   path1.setText("");
		     	 	   file1 = "";
		     	 	   pag1_num = -1;
		     	 	   pag2_num = -1;
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
