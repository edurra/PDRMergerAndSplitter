package p1;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MainWindow  extends Frame implements ActionListener, WindowListener  {
	
	private Button merge_button;
	private boolean merge_created = false;
	UnirPDF unir;
	private Button split_button;
	private boolean split_created = false;
	SepararPDF separar;

	
	public MainWindow () {
	      setLayout(new FlowLayout());
	      
	      merge_button = new Button("Unir archivos");
	      add(merge_button);
	      merge_button.addActionListener(this);
	      
	      split_button = new Button("Separar archivo");
	      add(split_button);
	      split_button.addActionListener(this);
	      setSize(400, 100);        
	      
	      setResizable(false);
	      
	      setTitle("PDF Merger-Splitter");
	      setVisible(true);        
	      
	      addWindowListener(this);
	 
	 
	      
	   }
	
	 public void actionPerformed(ActionEvent evt) {
		 
		 if (evt.getSource() == merge_button) {
			 if(!merge_created) {
				unir = new UnirPDF();
				merge_created = true;

			 }
			 else {
				 unir.setVisible(true);
			 }
		 }
		 if (evt.getSource() == split_button) {
			 if(!split_created) {
				separar = new SepararPDF();
				split_created = true;

			 }
			 else {
				 separar.setVisible(true);
			 }
		 }
	 }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
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

		
		MainWindow app = new MainWindow();
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
	      System.exit(0);  

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
