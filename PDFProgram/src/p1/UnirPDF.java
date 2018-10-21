package p1;
import java.awt.*; 
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.Map;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;

import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
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
	   private int alto_frame = 200;
	   private int ancho_frame = 400;
	   //private Map<String, Integer> adicionales_mapa = new HashMap();
	   private java.util.List<Button> botones_adicionales = new ArrayList<Button>();
	   private java.util.List<TextField> textos_adicionales = new ArrayList<TextField>();
	   private int adicionales = 0;
	   JPanel panel1 = new JPanel();
	   java.util.List<String> ficheros = new ArrayList();
	   private int pos1 = -1;
	   private int pos2 = -1;
	   public MainWindow main = null;
	   private int maxArchivos = 15;
	   
	   public void setMain (MainWindow m) {
		   main = m;
	   }
	   public MainWindow getMain () {
		   return main;
	   }
	   
	   public UnirPDF () {
		  
	      setLayout(new FlowLayout());
	      
	      add(panel1, BorderLayout.CENTER);
	      panel1.setPreferredSize(new Dimension(ancho_frame-20,alto_frame-140));

	      
	      lblFile1 = new Label("Archivo 1"); 
	      panel1.add(lblFile1);                    
	 
	      path1 = new TextField();
	      panel1.add(path1);                    

	      browse1 = new Button("Buscar");   
	      panel1.add(browse1);                 
	 
	      browse1.addActionListener(this);

	      lblFile2 = new Label("Archivo 2");
	      panel1.add(lblFile2);                   
	      
	      path2 = new TextField();
	      panel1. add(path2);                    
	 
	      browse2 = new Button("Buscar"); 
	      panel1.add(browse2);                    
	      
	      merge_button = new Button("Unir");   
	      add(merge_button);                   
	 
	      merge_button.addActionListener(this);
	  
	      lblFinal = new Label("");  
	      add(lblFinal);    
	      merge_button.setLocation(merge_button.getX(), merge_button.getY()+100);
	      
	      browse2.addActionListener(this);
	      path1.setEditable(false);
	      path2.setEditable(false);
	      path1.setColumns(30);
	      path2.setColumns(30);
	      lblFinal.setPreferredSize(new Dimension(380,20));

	      setTitle("PDF Merger"); 
	      setSize(ancho_frame, alto_frame);     
	 
	      setResizable(false);
	      
	      panel1.setBackground(Color.WHITE);
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
	 	   main.setLocation(this.getLocation());

		   main.setVisible(true);
		   limpiar();
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
		        	 if(file2Selected && !file1Selected) {
			        	   pos1=1;
			           }
			          else if(!file2Selected && !file1Selected){
			        	   pos1=0;
			           }
		           String fullPath = chooser.getSelectedFile().getAbsolutePath();
		           String name = chooser.getSelectedFile().getName();
		           file1 = fullPath;
		           
		           if(file1Selected) {
		        	   ficheros.set(pos1, fullPath);
		           }
		           else {
			           ficheros.add(file1);

		           }
		           file1Selected = true;
		           path1.setText(name);
		           last_path = chooser.getSelectedFile().getPath();
		           if(file2Selected && adicionales == 0) {
		        	   adicionales = 1;
		        	   alto_frame+=30;
		        	   panel1.setPreferredSize(new Dimension(ancho_frame-20, alto_frame-140));
		        	   setSize(ancho_frame, alto_frame);
		        	   panel1.add(new Label("Archivo 3"));
		        	   TextField nuevoPath = new TextField("");
		        	   nuevoPath.setColumns(30);
		        	   nuevoPath.setEditable(false);
		        	   panel1.add(nuevoPath);
		        	   //adicionales_mapa.put("TextField"+String.valueOf(adicionales), panel1.getComponentCount());
		        	   Button bot = new Button("Buscar");
		        	   bot.addActionListener(this);
		        	   panel1.add(bot);
		        	   //adicionales_mapa.put("Button"+String.valueOf(adicionales), panel1.getComponentCount());
		        	   botones_adicionales.add(bot);
		        	   textos_adicionales.add(nuevoPath);
		           }
		        }
		   	}
		   	if (evt.getSource() == browse2) {
		        int returnVal = chooser.showOpenDialog(null);
		        if(returnVal == JFileChooser.APPROVE_OPTION) {
		        	
		        	if(file1Selected && !file2Selected) {
			        	pos2=1;
			           }
		        	 else if(!file2Selected && !file1Selected){
			        	pos2=0;
			          }
		        	String fullPath = chooser.getSelectedFile().getAbsolutePath();
			        String name = chooser.getSelectedFile().getName();
			        file2 = fullPath;
			        path2.setText(name);
			        if(file2Selected) {
			        	   ficheros.set(pos2, fullPath);
			        }
			        else {
				           ficheros.add(file2);

			        }
			        file2Selected = true;
			        last_path = chooser.getSelectedFile().getPath();
			        
			        if(file1Selected && adicionales == 0) {
			        	   adicionales = 1;
			        	   alto_frame+=30;
			        	   panel1.setPreferredSize(new Dimension(ancho_frame-20, alto_frame-140));
			        	   setSize(ancho_frame, alto_frame);
			        	   panel1.add(new Label("Archivo 3"));
			        	   TextField nuevoPath = new TextField("");
			        	   nuevoPath.setColumns(30);
			        	   nuevoPath.setEditable(false);
			        	   panel1.add(nuevoPath);
			        	  // adicionales_mapa.put("TextField"+String.valueOf(adicionales), panel1.getComponentCount()-1);
			        	   Button bot = new Button("Buscar");
			        	   bot.addActionListener(this);
			        	   panel1.add(bot);
			        	  // adicionales_mapa.put("Button"+String.valueOf(adicionales), panel1.getComponentCount()-1);
			        	   botones_adicionales.add(bot);
			        	   textos_adicionales.add(nuevoPath);
			        	   
			           }

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
				        		lblFinal.setText("Uniendo documentos");
				        		PDDocument doc1 = null;
				        		PDDocument doc2 = null;
				        		File pdf1 = new File(file1);
				        		File pdf2 = new File(file2);
				        		
				        		java.util.List<File> files = new ArrayList<File>();
				        		java.util.List<PDDocument> documentos = new ArrayList<PDDocument>();
				        		
				        		int index = 0;
				        		for (String file_name: ficheros) {
				        			files.add(new File(file_name));
				        			PDDocument doc = null;
				        			try {
				        				doc = PDDocument.load(files.get(index));
				        			} catch (InvalidPasswordException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
				        			documentos.add(doc);
				        			
				        			
				        		}

				        	
				        	    PDFMergerUtility PDFmerger = new PDFMergerUtility();
				        	    PDFmerger.setDestinationFileName(fullPath);
				        	    
				        	    for (File f: files) {
				        	    	try {
				        	    		PDFmerger.addSource(f);
				        	    	}
				        	    	catch (FileNotFoundException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
				        	    }
				        	    
				        	  
				        	    try {
									PDFmerger.mergeDocuments(null);
								} catch (IOException e) {
									// TODO Auto-gerated catch block
									e.printStackTrace();
								}
				        	    
				        	    for (PDDocument d: documentos) {
				        	    	try {
				        	    		d.close();
				        	    	} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
				        	    }
				        	     
				        	     lblFinal.setText("Documentos unidos: " + name+".pdf creado");
				        	     limpiar();
				        	}
				        }
		        }
		   	}
		   	
		   	if(adicionales>0) {
		   		if(adicionales>1) {
				   	for (int i = 1; i<adicionales; i++) {
				   		String boton_aux_key = "Button"+String.valueOf(i);
				   		String textfield_aux_key = "TextField"+String.valueOf(i);
				   		//Button boton_aux = (Button) panel1.getComponent(adicionales_mapa.get(boton_aux_key));
				   		//TextField textfield_aux = (TextField) panel1.getComponent(adicionales_mapa.get(textfield_aux_key));
				   		
				   		if(evt.getSource() == botones_adicionales.get(i-1)) {
				   			int returnVal = chooser.showOpenDialog(null);
					        if(returnVal == JFileChooser.APPROVE_OPTION) {
					            
					           String fullPath = chooser.getSelectedFile().getAbsolutePath();
					           String name = chooser.getSelectedFile().getName();
					           ficheros.set(i+1, fullPath);
					           textos_adicionales.get(i-1).setText(name);
					           last_path = chooser.getSelectedFile().getPath();
					        }
				   		}
				   		
				   	}
		   		}
		   		String boton_aux_key = "Button"+String.valueOf(adicionales);
		   		String textfield_aux_key = "TextField"+String.valueOf(adicionales);
		   	
		   	//	TextField textfield_aux = (TextField) panel1.getComponent(adicionales_mapa.get(textfield_aux_key));
		   		//Button boton_aux = (Button) panel1.getComponent(adicionales_mapa.get(boton_aux_key));
		   		
		   		if(evt.getSource() == botones_adicionales.get(adicionales-1)) {
		   			int returnVal = chooser.showOpenDialog(null);
			        if(returnVal == JFileChooser.APPROVE_OPTION) {
			            
			           String fullPath = chooser.getSelectedFile().getAbsolutePath();
			           String name = chooser.getSelectedFile().getName();
			           ficheros.add(fullPath);
			           textos_adicionales.get(adicionales-1).setText(name);
			           last_path = chooser.getSelectedFile().getPath();
			           adicionales++;
			           if(adicionales<maxArchivos) {
				           alto_frame+=30;
			        	   panel1.setPreferredSize(new Dimension(ancho_frame-20, alto_frame-140));
			        	   setSize(ancho_frame, alto_frame);
			        	   panel1.add(new Label("Archivo "+String.valueOf(adicionales+2)));
			        	   TextField nuevoPath = new TextField("");
			        	   nuevoPath.setEditable(false);
			        	   nuevoPath.setColumns(30);
			        	   panel1.add(nuevoPath);
			        	  // adicionales_mapa.put("TextField"+String.valueOf(adicionales), panel1.getComponentCount()-1);
			        	   Button bot = new Button("Buscar");
			        	   bot.addActionListener(this);
			        	   panel1.add(bot);
			        	 //  adicionales_mapa.put("Button"+String.valueOf(adicionales), panel1.getComponentCount()-1);
			        	   botones_adicionales.add(bot);
			        	   textos_adicionales.add(nuevoPath);
			           }
			        }
		   		}
	   		}
	   }

	public void limpiar() {
		file1 = "";
	     file2 = "";
	     adicionales = 0;
	    // adicionales_mapa = new HashMap();
	     textos_adicionales = new ArrayList<TextField>();
	     botones_adicionales = new ArrayList<Button>();
	     alto_frame = 200;
	     ancho_frame = 400;
	     panel1.setPreferredSize(new Dimension(ancho_frame-20, alto_frame-140));
	   	 setSize(ancho_frame, alto_frame);
	   	 ficheros = new ArrayList();
	   	 for (int i = panel1.getComponentCount()-1; i>5; i--) {
	   		 panel1.remove(i);
	   	 }
	     file1Selected = false;
	     file2Selected = false;
	     path1.setText("");
	     path2.setText("");
	    
	}
	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		main.setVisible(false);
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
