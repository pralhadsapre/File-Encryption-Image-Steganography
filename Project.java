//Enigma File Encryption and Steganography

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

class Project
{
	public static void main(String args[])
	{
		MyFrame f=new MyFrame();
	}
}

class MyFrame extends JFrame implements ActionListener
{
	JButton encrypt,decrypt,steganography,about,exit;
	JFrame temp;
	JLabel main;
	JDialog dialog;

	MyFrame()
	{
		super("Enigma - File Encryption and Steganography");

		Object key,value;
		Enumeration keys = UIManager.getDefaults().keys();
    		while(keys.hasMoreElements()) 
		{
			key=keys.nextElement();
      			value=UIManager.get(key);
			if(value instanceof javax.swing.plaf.FontUIResource)
				UIManager.put(key,new Font("Georgia",Font.PLAIN,17));
		}
		
		UIManager.put("Label.font",new Font("Georgia",Font.BOLD,18));
		UIManager.put("Button.font",new Font("Georgia",Font.BOLD,20));
		UIManager.put("TextField.font",new Font("Georgia",Font.PLAIN,17));
		UIManager.put("Button.foreground",Color.red);
		UIManager.put("ToolTip.font",new Font("Georgia",Font.BOLD,13));
		
		addWindowListener(new WindowAdapter()
				{
					public void windowClosing(WindowEvent e)
					{
						System.exit(0);
					}
				});

		setSize(600,700);
		setVisible(true);
		setLocation(250,25);
		setResizable(false);

		Container c=getContentPane();
		c.setBackground(new Color(5,10,160));
	
		encrypt=new JButton("Encryption",new ImageIcon("Images/bigimages/lock.png"));
		decrypt=new JButton("Decryption",new ImageIcon("Images/bigimages/unlock.png"));
		steganography=new JButton("Steganography",new ImageIcon("Images/bigimages/bmpimages.png"));
		about=new JButton("About",new ImageIcon("Images/bigimages/help.png"));
		exit=new JButton("Exit",new ImageIcon("Images/bigimages/delete.png"));
		main=new JLabel("Enigma",new ImageIcon("Images/bigimages/main.png"),SwingConstants.LEFT);
		main.setFont(new Font("Georgia",Font.BOLD,80));
		main.setForeground(Color.yellow);

		encrypt.addActionListener(this);
		decrypt.addActionListener(this);
		steganography.addActionListener(this);
		about.addActionListener(this);
		exit.addActionListener(this);
	
		c.setLayout(null);

		main.setBounds(20,20,550,150);
		encrypt.setBounds(200,250,250,50);
		decrypt.setBounds(200,320,250,50);
		steganography.setBounds(200,390,250,50);
		about.setBounds(200,460,250,50);
		exit.setBounds(200,530,250,50);

		c.add(encrypt);
		c.add(decrypt);
		c.add(steganography);
		c.add(about);
		c.add(exit);
		c.add(main);

		UIManager.put("Button.font",new Font("Georgia",Font.BOLD,17));
		UIManager.put("Button.foreground",new Color(80,80,80));

		repaint();
	}

	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getActionCommand().equals("Encryption"))
			temp=new EncryptFrame(this);
		else if(ae.getActionCommand().equals("Decryption"))
			temp=new DecryptFrame(this);
		else if(ae.getActionCommand().equals("Steganography"))
			temp=new SteganographyFrame(this);
		else if(ae.getActionCommand().equals("About"))
			dialog=new AboutFrame();
		else if(ae.getActionCommand().equals("Exit"))
			System.exit(0);
	}
}

class EncryptFrame extends JFrame implements ActionListener
{
	JTextField source,destination;
	JLabel l1,l2,l3;
	JList list;
	JButton ok,browse1,browse2,back;
	String algorithms[]={"Substitution Cipher","Rail fence cipher",
			"Simple Columnar Transposition","Complex Columnar Transposition",
			"Enigma encryption algorithm"};
	JFileChooser fc;
	JFrame parent;
	JDialog error;
	JDialog key;
	JLabel logo;

	public EncryptFrame(JFrame parent)
	{
		super("File Encryption");
	
		addWindowListener(new MyWindowAdapter(parent,this));

		setSize(600,700);
		setVisible(true);
		setLocation(250,25);
		setResizable(false);
		
		this.parent=parent;
		this.parent.setEnabled(false);

		Container c=getContentPane();
		c.setLayout(null);
		c.setBackground(new Color(195,200,250));

		logo=new JLabel("File Encryption",new ImageIcon("Images/logoimages/encrypt.png"),SwingConstants.CENTER);
		logo.setFont(new Font("Georgia",Font.BOLD,50));
		logo.setForeground(Color.yellow);
		logo.setOpaque(true);
		logo.setBackground(new Color(5,10,160));
		
		source=new JTextField();
		destination=new JTextField();
		source.setEditable(false);
		destination.setEditable(false);

		l1=new JLabel("Select a file to encrypt");
		l2=new JLabel("Encryption algorithm");
		l3=new JLabel("Select the destination folder");

		ok=new JButton("Encrypt",new ImageIcon("Images/lock.png"));
		browse1=new JButton("Browse",new ImageIcon("Images/save.png"));
		browse2=new JButton("Browse",new ImageIcon("Images/save.png"));
		back=new JButton("Back",new ImageIcon("Images/back.png"));

		browse1.addActionListener(this);
		browse2.addActionListener(this);
		back.addActionListener(this);
		ok.addActionListener(this);

		list=new JList(algorithms);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setFont(new Font("Georgia",Font.PLAIN,17));

		l1.setBounds(20,145,300,30);
		browse1.setBounds(410,145,140,30);
		source.setBounds(20,185,530,30);

		l2.setBounds(20,255,530,30);
		list.setBounds(20,295,530,125);

		l3.setBounds(20,460,300,30);
		browse2.setBounds(410,460,140,30);
		destination.setBounds(20,500,530,30);

		back.setBounds(20,585,140,35);
		ok.setBounds(410,585,140,35);

		logo.setBounds(0,20,600,100);

		c.add(l1);
		c.add(browse1);
		c.add(source);

		c.add(l2);
		c.add(list);

		c.add(l3);
		c.add(browse2);
		c.add(destination);

		c.add(ok);
		c.add(back);
		c.add(logo);
	}

	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==browse1)
		{
			fc=new JFileChooser();
			fc.setDialogType(JFileChooser.OPEN_DIALOG);
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fc.setApproveButtonToolTipText("Select a file to encrypt");

			if(JFileChooser.APPROVE_OPTION==fc.showOpenDialog(this) && fc.getSelectedFile().exists() && fc.getSelectedFile().canRead())
				source.setText(fc.getSelectedFile().toString());	
		}
		else if(ae.getSource()==browse2)
		{
			fc=new JFileChooser();
			fc.setDialogType(JFileChooser.OPEN_DIALOG);
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fc.setApproveButtonToolTipText("Select the destination folder");

			if(JFileChooser.APPROVE_OPTION==fc.showOpenDialog(this) && fc.getSelectedFile().exists())
				destination.setText(fc.getSelectedFile().toString());
		}
		else if(ae.getSource()==back)
		{
			this.dispose();
			parent.setEnabled(true);
			parent.toFront();
		}
		else if(ae.getSource()==ok)
		{
			if(source.getText().length()==0)
				error=new ErrorDialog("You need to specify the file to be encrypted");
			else if(destination.getText().length()==0)
				error=new ErrorDialog("You need to specify the destination folder");
			else if(list.getSelectedIndex()==-1)
				error=new ErrorDialog("Select an encryption algorithm");
			else
				key=new KeyDialog(source.getText(),destination.getText(),list.getSelectedIndex(),1);		
		}
				
			
	}
		
}

class DecryptFrame extends JFrame implements ActionListener
{
	JTextField source,destination;
	JLabel l1,l2,l3;
	JList list;
	JButton ok,browse1,browse2,back;
	String algorithms[]={"Substitution Cipher","Rail fence cipher",
			"Simple Columnar Transposition","Complex Columnar Transposition",
			"Enigma encryption algorithm"};
	JFileChooser fc;
	JFrame parent;
	JDialog error;
	JDialog key;
	JLabel logo;

	public DecryptFrame(JFrame parent)
	{
		super("File Decryption");

		addWindowListener(new MyWindowAdapter(parent,this));

		setSize(600,700);
		setVisible(true);
		setLocation(250,25);
		setResizable(false);
		parent.setEnabled(false);

		this.parent=parent;
		this.parent.setEnabled(false);

		Container c=getContentPane();
		c.setLayout(null);
		c.setBackground(new Color(195,200,250));

		logo=new JLabel("File Decryption",new ImageIcon("Images/logoimages/decrypt.png"),SwingConstants.CENTER);
		logo.setFont(new Font("Georgia",Font.BOLD,50));
		logo.setForeground(Color.yellow);
		logo.setOpaque(true);
		logo.setBackground(new Color(5,10,160));

		source=new JTextField();
		destination=new JTextField();
		source.setEditable(false);
		destination.setEditable(false);

		l1=new JLabel("Select the file to decrypt");
		l2=new JLabel("Decryption algorithm");
		l3=new JLabel("Select the destination folder");

		ok=new JButton("Decrypt",new ImageIcon("Images/unlock.png"));
		browse1=new JButton("Browse",new ImageIcon("Images/save.png"));
		browse2=new JButton("Browse",new ImageIcon("Images/save.png"));
		back=new JButton("Back",new ImageIcon("Images/back.png"));

		browse1.addActionListener(this);
		browse2.addActionListener(this);
		back.addActionListener(this);
		ok.addActionListener(this);

		list=new JList(algorithms);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setFont(new Font("Georgia",Font.PLAIN,17));
		

		l1.setBounds(20,145,300,30);
		browse1.setBounds(410,145,140,30);
		source.setBounds(20,185,530,30);

		l2.setBounds(20,255,530,30);
		list.setBounds(20,295,530,125);

		l3.setBounds(20,460,300,30);
		browse2.setBounds(410,460,140,30);
		destination.setBounds(20,500,530,30);

		ok.setBounds(410,595,140,35);
		back.setBounds(20,595,140,35);

		logo.setBounds(0,20,600,100);
		c.add(logo);

		c.add(l1);
		c.add(browse1);
		c.add(source);

		c.add(l2);
		c.add(list);

		c.add(l3);
		c.add(browse2);
		c.add(destination);

		c.add(ok);
		c.add(back);	
	}

	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==browse1)
		{
			fc=new JFileChooser();
			fc.setDialogType(JFileChooser.OPEN_DIALOG);
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fc.setApproveButtonToolTipText("Select a file to decrypt");
			fc.setFileFilter(new MyFileFilter(".p4e"));
			fc.setAcceptAllFileFilterUsed(false);

			if(JFileChooser.APPROVE_OPTION==fc.showOpenDialog(this) && fc.getSelectedFile().exists())
				source.setText(fc.getSelectedFile().toString());	
		}
		else if(ae.getSource()==browse2)
		{
			fc=new JFileChooser();
			fc.setDialogType(JFileChooser.OPEN_DIALOG);
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fc.setApproveButtonToolTipText("Select the destination folder");

			if(JFileChooser.APPROVE_OPTION==fc.showOpenDialog(this) && fc.getSelectedFile().exists())
				destination.setText(fc.getSelectedFile().toString());
		}
		else if(ae.getSource()==back)
		{
			this.dispose();
			parent.setEnabled(true);
			parent.toFront();
		}	
		else if(ae.getSource()==ok)
		{
			if(source.getText().length()==0)
				error=new ErrorDialog("You need to specify the file to be decrypted");
			else if(destination.getText().length()==0)
				error=new ErrorDialog("You need to specify the destination folder");
			else if(list.getSelectedIndex()==-1)
				error=new ErrorDialog("Select the decryption algorithm");
			else
				key=new KeyDialog(source.getText(),destination.getText(),list.getSelectedIndex(),2);
		}
	}
}

class SteganographyFrame extends JFrame implements ActionListener
{
	JButton hide,reveal,back;
	JFrame parent;
	JFrame temp;
	JLabel logo;

	public SteganographyFrame(JFrame parent)
	{
		super("Image Steganography");

		addWindowListener(new MyWindowAdapter(parent,this));	

		setSize(600,700);
		setVisible(true);
		setLocation(250,25);
		setResizable(false);
		parent.setEnabled(false);

		this.parent=parent;
		this.parent.setEnabled(false);		

		Container c=getContentPane();
		c.setLayout(null);
		c.setBackground(new Color(195,200,250));

		logo=new JLabel("Steganography",new ImageIcon("Images/logoimages/bmpimages.png"),SwingConstants.CENTER);
		logo.setFont(new Font("Georgia",Font.BOLD,50));
		logo.setForeground(Color.yellow);
		logo.setOpaque(true);
		logo.setBackground(new Color(5,10,160));
	
		UIManager.put("Button.font",new Font("Georgia",Font.BOLD,20));
	
		hide=new JButton("Hide a message",new ImageIcon("Images/bigimages/inject.png"));
		reveal=new JButton("Reveal a message",new ImageIcon("Images/bigimages/reveal.png"));
		back=new JButton("Back to main form",new ImageIcon("Images/bigimages/back.png"));
		
		UIManager.put("Button.font",new Font("Georgia",Font.BOLD,17));	
	
		hide.addActionListener(this);
		reveal.addActionListener(this);	
		back.addActionListener(this);

		hide.setBounds(150,200,300,50);
		reveal.setBounds(150,280,300,50);
		back.setBounds(150,360,300,50);
		
		logo.setBounds(0,20,600,100);
		c.add(logo);		

		c.add(hide);
		c.add(reveal);
		c.add(back);	
	}

	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==hide)
			temp=new SteganographyHideFrame(this);
		else if(ae.getSource()==reveal)
			temp=new SteganographyRevealFrame(this);
		else if(ae.getSource()==back)
		{
			this.dispose();
			parent.setEnabled(true);
			parent.toFront();
		}
	}
}

class SteganographyHideFrame extends JFrame implements ActionListener
{
	JTextField source;
	JLabel l1,l2;
	JButton hide,browse,back;
	JTextArea msg;
	JScrollPane p;
	JFileChooser fc;
	JFrame parent;
	JDialog error;
	JDialog progress;
	JLabel logo;
	
	public SteganographyHideFrame(JFrame parent)
	{
		super("Image Steganography - Message hiding");

		addWindowListener(new MyWindowAdapter(parent,this));	

		setSize(600,700);
		setVisible(true);
		setLocation(250,25);
		setResizable(false);
		parent.setEnabled(false);

		this.parent=parent;
		this.parent.setEnabled(false);		

		Container c=getContentPane();
		c.setLayout(null);
		c.setBackground(new Color(195,200,250));
		
		logo=new JLabel("Message hiding",new ImageIcon("Images/logoimages/inject.png"),SwingConstants.CENTER);
		logo.setFont(new Font("Georgia",Font.BOLD,50));
		logo.setForeground(Color.yellow);
		logo.setOpaque(true);
		logo.setBackground(new Color(5,10,160));

		l1=new JLabel("Select the file for performing steganography");
		browse=new JButton(new ImageIcon("Images/searchpics.png"));
		source=new JTextField();
		source.setEditable(false);

		l2=new JLabel("Enter the secret message to be hidden");
		msg=new JTextArea();
		msg.setLineWrap(true);
		msg.setWrapStyleWord(true);
		p=new JScrollPane(msg,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		hide=new JButton("Inject",new ImageIcon("Images/inject.png"));
		back=new JButton("Back",new ImageIcon("Images/back.png"));

		browse.addActionListener(this);
		back.addActionListener(this);
		hide.addActionListener(this);

		l1.setBounds(20,185,470,40);
		browse.setBounds(510,185,40,40);
		source.setBounds(20,235,530,30);

		l2.setBounds(20,305,530,30);
		p.setBounds(20,345,530,175);

		hide.setBounds(410,585,140,35);
		back.setBounds(20,585,140,35);

		logo.setBounds(0,20,600,100);
		c.add(logo);	
	
		c.add(l1);
		c.add(browse);
		c.add(source);

		c.add(l2);
		c.add(p);

		c.add(hide);
		c.add(back);	
	}

	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==browse)
		{
			fc=new JFileChooser();
			fc.setDialogType(JFileChooser.OPEN_DIALOG);
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fc.setApproveButtonToolTipText("Select the bitmap file");
			fc.setFileFilter(new MyFileFilter(".bmp"));
			fc.setAcceptAllFileFilterUsed(false);

			if(JFileChooser.APPROVE_OPTION==fc.showOpenDialog(this) && fc.getSelectedFile().exists())
				source.setText(fc.getSelectedFile().toString());
		}
		else if(ae.getSource()==back)
		{
			this.dispose();
			parent.setEnabled(true);
			parent.toFront();
		}
		else if(ae.getSource()==hide)
		{
			if(source.getText().length()==0)
				error=new ErrorDialog("You need to specify the bitmap file");
			else if(msg.getText().length()==0)
				error=new ErrorDialog("Enter the text which you want to hide");
			else
				progress=new ProgressDialog(msg,source.getText(),1);	
		}
	}
}

class SteganographyRevealFrame extends JFrame implements ActionListener
{
	JTextField source;
	JLabel l1,l2;
	JButton unhide,browse,back;
	JTextArea msg;
	JScrollPane p;
	JFileChooser fc;
	JFrame parent;
	JDialog error;
	JDialog progress;
	JLabel logo;
	
	public SteganographyRevealFrame(JFrame parent)
	{
		super("Image Steganography - Message retrieval");

		addWindowListener(new MyWindowAdapter(parent,this));	

		setSize(600,700);
		setVisible(true);
		setLocation(250,25);
		setResizable(false);
		parent.setEnabled(false);

		this.parent=parent;
		this.parent.setEnabled(false);		

		Container c=getContentPane();
		c.setLayout(null);
		c.setBackground(new Color(195,200,250));

		logo=new JLabel("Message retrieval",new ImageIcon("Images/logoimages/reveal.png"),SwingConstants.CENTER);
		logo.setFont(new Font("Georgia",Font.BOLD,50));
		logo.setForeground(Color.yellow);
		logo.setOpaque(true);
		logo.setBackground(new Color(5,10,160));
		
		l1=new JLabel("Select the file for performing steganography");
		browse=new JButton(new ImageIcon("Images/searchpics.png"));
		source=new JTextField();
		source.setEditable(false);

		l2=new JLabel("The extracted secret message");
		msg=new JTextArea();
		msg.setLineWrap(true);
		msg.setWrapStyleWord(true);
		msg.setEditable(false);
		p=new JScrollPane(msg,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		unhide=new JButton("Reveal",new ImageIcon("Images/reveal.png"));
		back=new JButton("Back",new ImageIcon("Images/back.png"));

		browse.addActionListener(this);
		back.addActionListener(this);
		unhide.addActionListener(this);

		l1.setBounds(20,185,470,40);
		browse.setBounds(510,185,40,40);
		source.setBounds(20,235,530,30);

		l2.setBounds(20,305,530,30);
		p.setBounds(20,345,530,175);

		unhide.setBounds(410,585,140,35);
		back.setBounds(20,585,140,35);

		logo.setBounds(0,20,600,100);
		c.add(logo);

		c.add(l1);
		c.add(browse);
		c.add(source);

		c.add(l2);
		c.add(p);

		c.add(unhide);
		c.add(back);	
	}

	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==browse)
		{
			fc=new JFileChooser();
			fc.setDialogType(JFileChooser.OPEN_DIALOG);
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fc.setApproveButtonToolTipText("Select the bitmap file");
			fc.setFileFilter(new MyFileFilter(".bmp"));
			fc.setAcceptAllFileFilterUsed(false);

			if(JFileChooser.APPROVE_OPTION==fc.showOpenDialog(this) && fc.getSelectedFile().exists())
				source.setText(fc.getSelectedFile().toString());
		}
		else if(ae.getSource()==back)
		{
			this.dispose();
			parent.setEnabled(true);
			parent.toFront();
		}
		else if(ae.getSource()==unhide)
		{
			if(source.getText().length()==0)
				error=new ErrorDialog("You need to specify the bitmap file");
			else
				progress=new ProgressDialog(msg,source.getText(),2);
		}
	}
}

class ProgressDialog extends JDialog implements ActionListener
{
	JProgressBar p;
	JLabel display;
	JButton done;
	int operation;
	String source;
	SteganographyThread sthread;

	ProgressDialog(JTextArea area,String source,int operation)
	{
		super();

		this.operation=operation;
		this.source=source;

		setModal(true);
		setSize(500,275);
		setLocation(300,200);
		setResizable(false);
		
		if(operation==1)
		{
			setTitle("Hiding the message");
			display=new JLabel("Injecting the message",new ImageIcon("Images/bigimages/inject.png"),SwingConstants.CENTER);
		}
		if(operation==2)
		{
			setTitle("Revealing the message");
			display=new JLabel("Revealing the message",new ImageIcon("Images/bigimages/reveal.png"),SwingConstants.CENTER);
		}
		
		Container c=getContentPane();
		c.setLayout(null);
		c.setBackground(new Color(195,200,250));

		p=new JProgressBar();
		p.setForeground(new Color(0,0,130));

		done=new JButton("Done",new ImageIcon("Images/done.png"));
		done.setEnabled(false);
		done.addActionListener(this);

		display.setBounds(20,60,460,50);
		p.setBounds(20,140,460,15);
		done.setBounds(340,185,140,35);
		
		c.add(display);
		c.add(p);
		c.add(done);

		repaint();
		setEnabled(false);
		
		sthread=new SteganographyThread(p,area,this.source,this,display,done,operation);
		sthread.start();

		setVisible(true);
	}	

	public void actionPerformed(ActionEvent ae)
	{
		this.dispose();
	}	
}

class SteganographyThread extends Thread
{
	JProgressBar p;
	JTextArea area;
	String source;
	JDialog parent;
	JLabel display;
	JButton done;
	int operation;

	public SteganographyThread(JProgressBar p,JTextArea area,String source,JDialog parent,JLabel display,JButton done,int operation)
	{
		this.p=p;
		this.area=area;
		this.source=source;
		this.parent=parent;
		this.display=display;
		this.done=done;
		this.operation=operation;
	}
	
	public void run()
	{
		try
		{
			Thread.sleep(100);
			if(operation==1)
				Bitmap.hidemessage(p,area,source,parent,display,done);
			else if(operation==2)
				Bitmap.revealmessage(p,area,source,parent,display,done);	
		}
		catch(Exception e)
		{
			JDialog error=new ErrorDialog("A fatal error occured");
			System.exit(0);
		}	
	}
}

class Bitmap
{
	public static JProgressBar p;

	public static void hide(RandomAccessFile f,JTextArea area) throws Exception
	{
		f.seek(55l);
		
		String input=area.getText();

		int messagelength;

		messagelength=(int)((f.length()-57)/8)<input.length()?(int)((f.length()-57)/8):input.length();
		String actual=input.substring(0,messagelength);
	
		byte b[]=actual.getBytes();

		String temp="00000000";	
		String equ[]=new String[b.length];

		for(int i=0;i<b.length;i++)
		{
			String buffer=Integer.toBinaryString((int)b[i]);
			equ[i]=temp.substring(buffer.length())+buffer;
		}


		f.writeShort(b.length);	//writes the length of the message
		int cache;

		Bitmap.p.setMinimum(0);
		Bitmap.p.setMaximum(b.length-1);

		for(int i=0;i<b.length;i++)
		{
			for(int j=0;j<8;j++)
			{
				cache=f.read();

				cache=cache>>1;
				cache=cache<<1;

				if(equ[i].charAt(j)=='1')
					cache+=1;
				else
					cache+=0;

				f.seek(f.getFilePointer()-1);
				f.writeByte(cache);
			}

			Bitmap.p.setValue(i);
		}
	}			

	public static void retrieve(RandomAccessFile f,JTextArea area) throws Exception
	{
		f.seek(55l);
		int mlength=f.readShort();

		int result[]=new int[mlength];
		int tempvalue;
		String temp;

		Bitmap.p.setMinimum(0);
		Bitmap.p.setMaximum(mlength-1);

		for(int i=0;i<mlength;i++)
		{
			for(int j=7;j>=0;j--)
			{
				temp=Integer.toBinaryString(f.read());
				if(temp.charAt(temp.length()-1)=='1')
					tempvalue=(int)Math.pow((double)2,(double)j);
				else
					tempvalue=0;
				result[i]+=tempvalue;
			}

			Bitmap.p.setValue(i);
		}


		byte values[]=new byte[result.length];
		for(int i=0;i<values.length;i++)
			values[i]=(byte)result[i];
		
		String hiddenmessage=new String(values);
		area.setText(hiddenmessage);
	}
		
	public static void hidemessage(JProgressBar p,JTextArea area,String source,JDialog parent,JLabel display,JButton done) throws Exception
	{
		Bitmap.p=p;
		RandomAccessFile f=null;

		try
		{
			f=new RandomAccessFile(source,"rw");

			Bitmap.hide(f,area);
			parent.setEnabled(true);
			display.setText("Message hidden successfully");
			done.setEnabled(true);
		}
		catch(Exception e)
		{
			JDialog error=new ErrorDialog("A fatal error occured");
			System.exit(0);
		}
		finally
		{
			f.close();
		}
	}
	
	public static void revealmessage(JProgressBar p,JTextArea area,String source,JDialog parent,JLabel display,JButton done) throws Exception
	{
		Bitmap.p=p;
		RandomAccessFile f=null;

		try
		{		
			f=new RandomAccessFile(source,"rw");		
			Bitmap.retrieve(f,area);
					
			parent.setEnabled(true);
			display.setText("Message retrieved successfully");
			done.setEnabled(true);
		}
		catch(Exception e)
		{
			JDialog error=new ErrorDialog("A fatal error occured");
			System.exit(0);
		}
		finally
		{
			f.close();
		}	
	}
}	

class AboutFrame extends JDialog implements ActionListener
{
	JLabel logo;
	JButton back;
	JTextArea area;
	
	public AboutFrame()
	{
		super();

		setModal(true);
		setSize(550,550);
		setLocation(275,25);
		setResizable(false);
		setTitle("About Enigma");
		

		Container c=getContentPane();
		c.setLayout(null);
		c.setBackground(new Color(195,200,250));

		logo=new JLabel("Enigma",new ImageIcon("Images/bigimages/smallmain.png"),SwingConstants.CENTER);
		logo.setFont(new Font("Georgia",Font.BOLD,70));
		logo.setForeground(Color.yellow);
		logo.setOpaque(true);
		logo.setBackground(new Color(5,10,160));

		back=new JButton("Back",new ImageIcon("Images/back.png"));
		area=new JTextArea("Enigma is a general purpose encryption tool which"+ 
				"\nincorporates five encryption and decryption algorithms."+
				"\nEnigma also includes image steganography on bitmap"+
				"\nimages."+
				"\n\nDeveloped at   :  Institute of Technology, Ulhasnagar - 3"+
				"\n\nDeveloped by  :  Pralhad, Alok, Bunty and Sagar"+
				"\n\nThis software is totally FREE and is licensed to YOU"+
				"\n\nFor feedback email me at   :   pralhad_sapre@rediff.com");
		area.setEditable(false);
		area.setLineWrap(true);
		area.setFont(new Font("Georgia",Font.BOLD,17));
		area.setBackground(new Color(195,200,250));

		back.addActionListener(this);
		
		logo.setBounds(0,20,550,100);
		area.setBounds(20,160,510,240);
		back.setBounds(20,440,140,35);

		c.add(logo);
		c.add(area);
		c.add(back);

		setVisible(true);	
	}

	public void actionPerformed(ActionEvent ae)
	{
		this.hide();
		this.dispose();
	}
}

class MyWindowAdapter extends WindowAdapter
{
	JFrame parent,child;
	
	public MyWindowAdapter(JFrame parent,JFrame child)
	{
		this.parent=parent;
		this.child=child;
	}
	
	public void windowClosing(WindowEvent we)
	{
		child.dispose();
		parent.setEnabled(true);
		parent.toFront();
	}
}

class MyFileFilter extends javax.swing.filechooser.FileFilter
{
	String extension;

	public MyFileFilter(String extension)
	{
		super();
		this.extension=extension;
	}
	
	public boolean accept(File f)
	{
		if(f.isDirectory() || f.toString().endsWith(extension)  && f.canRead()) 
			return true;
		else
			return false;
	}

	public String getDescription()
	{
		return (extension+" files");
	}
}

class ErrorDialog extends JDialog implements ActionListener
{
	JLabel error;
	JButton back;
	
	public ErrorDialog(String message)
	{
		super();

		setModal(true);
		setSize(500,200);
		setLocation(300,100);
		setResizable(false);
		setTitle("Error");
		
		Container c=getContentPane();
		c.setLayout(null);
		c.setBackground(new Color(255,210,185));

		error=new JLabel(message,new ImageIcon("Images/bigimages/warning.png"),SwingConstants.CENTER);
		error.setFont(new Font("Georgia",Font.BOLD,17));

		back=new JButton("Close",new ImageIcon("Images/delete.png"));
		back.addActionListener(this);
		
		error.setBounds(20,20,460,80);
		back.setBounds(180,120,140,35);

		c.add(error);
		c.add(back);

		setVisible(true);	
	}

	public void actionPerformed(ActionEvent ae)
	{
		this.hide();
		this.dispose();
	}
}

class KeyDialog extends JDialog implements ActionListener
{
	JLabel caption;
	JLabel display;
	private JPasswordField key;
	JButton ok;

	String message;
	int which;
	private String keyvalue;

	JDialog error;
	JDialog status;
	
	String source,destination;
	int operation;

	public KeyDialog(String source,String destination,int which,int operation)
	{
		super();
		
		this.source=source;
		this.destination=destination;
		this.which=which;
		this.operation=operation;
	
		switch(which)
		{
			case 0:
				message="Enter the displacement value (max 6 digits)";
				break;
			case 1:
				message="Enter the depth value (max 6 digits)";
				break;
			case 2:
				message="Enter the number of columns (max 6 digits)";
				break;
			case 3:
				message="Enter the alphanumeric key";
				break;
			case 4:
				message="Enter the 16 character key";
				break;
		}

		setModal(true);
		setSize(500,275);
		setLocation(300,200);
		setResizable(false);
		setTitle("Enter the key");
		
		Container c=getContentPane();
		c.setLayout(null);
		c.setBackground(new Color(195,200,250));

		caption=new JLabel(message,new ImageIcon("Images/bigimages/keyimage.png"),SwingConstants.CENTER);
		caption.setFont(new Font("Georgia",Font.BOLD,17));
		
		display=new JLabel("Enter the key : ",SwingConstants.CENTER);
		display.setFont(new Font("Georgia",Font.BOLD,17));

		ok=new JButton("Accept",new ImageIcon("Images/accept.png"));
		ok.addActionListener(this);

		key=new JPasswordField();
		key.setFont(new Font("Georgia",Font.BOLD,20));
		
		caption.setBounds(20,20,460,80);
		display.setBounds(20,120,140,35);
		key.setBounds(180,120,280,35);
		ok.setBounds(180,185,140,35);

		c.add(caption);
		c.add(key);
		c.add(ok);
		c.add(display);

		setVisible(true);	
	}

	public void actionPerformed(ActionEvent ae)
	{
	
		//Key validation code

		String temp=null;
		boolean check=true;
		switch(which)
		{
			case 0:
			case 1:
			case 2:
				if(key.getPassword().length==0)
				{
					error=new ErrorDialog("There is no key entered");
					check=false;
				}
				else
				{
					temp=new String(key.getPassword());
					if(temp.length()>6)
						temp=temp.substring(0,6);
				}
				break;
			case 3:
				if(key.getPassword().length<2)
				{
					error=new ErrorDialog("Key is not of appropriate size");
					check=false;
				}
				else
				{
					temp=new String(key.getPassword());
					if(temp.length()>60)
						temp=temp.substring(0,60);
				}
				break;

			case 4:
				if(key.getPassword().length<16)
				{
					error=new ErrorDialog("Key is not of appropriate size");
					check=false;
				}
				else
				{
					temp=new String(key.getPassword());
					if(temp.length()>16)
						temp=temp.substring(0,16);
				}				
		}
		
		if(check)
		{
			switch(which)
			{
				case 0:	
				case 1:	
				case 2:
					try
					{
						int cache=Integer.parseInt(temp);
						keyvalue=temp;
					}
					catch(NumberFormatException e)
					{
						error=new ErrorDialog("This key can contain only numbers");
						key.setText("");
						check=false;
					}
					break;
				case 3:
				case 4:
					keyvalue=temp;
					break;
			}
		}
		
		if(check)
		{
			this.dispose();
			if(operation==1)
				status=new StatusDialog(source,destination,which,operation,keyvalue);
			else if(operation==2)
				status=new StatusDialog(source,destination,which,operation,keyvalue);
		}
	}
}

class StatusDialog extends JDialog implements ActionListener,WindowListener
{
	JLabel display;
	JProgressBar p;
	JButton abort;
	JTextArea area;
	JScrollPane pane;
	JButton done;

	int which,operation;
	String message;
	String source,destination,keyvalue;

	String algorithm;
	EncryptThread ethread;
	DecryptThread dthread;
	
	StatusDialog(String source,String destination,int which,int operation,String keyvalue)
	{
		super();

		this.source=source;
		this.destination=destination+"\\";
		this.which=which;
		this.operation=operation;
		this.keyvalue=keyvalue;
		

		switch(operation)
		{
			case 1:
				message="Encryption in progress";
				setTitle("Encrypting the specified file");
				break;
			case 2:
				message="Decryption in progress";
				setTitle("Decrypting the specified file");
				break;
		}

		setModal(true);
		setSize(500,375);
		setLocation(300,150);
		setResizable(false);
		addWindowListener(this);		

		switch(which)
		{
			case 0:
				algorithm="Substitution cipher";
				break;
			case 1:
				algorithm="Rail fence cipher";
				break;
			case 2:
				algorithm="Simple columnar transposition";
				break;
			case 3:
				algorithm="Complex columnar transposition";
				break;
			case 4:
				algorithm="Enigma encryption algorithm";
				break;
		}
		
		Container c=getContentPane();
		c.setLayout(null);
		

		if(operation==1)
			display=new JLabel(message,new ImageIcon("Images/bigimages/encrypt.png"),SwingConstants.CENTER);
		else if(operation==2)
			display=new JLabel(message,new ImageIcon("Images/bigimages/decrypt.png"),SwingConstants.CENTER);

		p=new JProgressBar();
		p.setForeground(new Color(0,0,130));
		abort=new JButton("Abort",new ImageIcon("Images/abort.png"));
		done=new JButton("Done",new ImageIcon("Images/done.png"));
		done.setEnabled(false);


		area=new JTextArea("Operational Details"+
				"\n\nAlgorithm      :   "+algorithm+
				"\nSource file     :   "+source+
				"\nDestination   :   "+destination);

		area.setFont(new Font("Georgia",Font.BOLD,17));
		area.setEditable(false);

		pane=new JScrollPane(area,ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		pane.setBounds(20,20,460,130);
		display.setBounds(20,170,460,50);
		p.setBounds(20,240,460,15);
		abort.setBounds(340,295,140,35);
		done.setBounds(180,295,140,35);
		
		c.add(pane);
		c.add(display);
		c.add(p);
		c.add(abort);
		c.add(done);

		abort.addActionListener(this);
		done.addActionListener(this);

		c.setBackground(new Color(195,200,250));

		if(operation==1)
		{
			ethread=new EncryptThread(p,source,this.destination,which,keyvalue,abort,done,display);
			ethread.setPriority(Thread.MAX_PRIORITY);
			ethread.start();
		}
		else if(operation==2)
		{
			dthread=new DecryptThread(p,source,this.destination,which,keyvalue,abort,done,display);
			dthread.setPriority(Thread.MAX_PRIORITY);
			dthread.start();
		}

		repaint();
		setVisible(true);	
	}

	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==abort)
		{
			if(operation==1)
				ethread.abort();
			else if(operation==2)
				dthread.abort();
		}
		else if(ae.getSource()==done)
			this.dispose();
	}

	public void windowClosing(WindowEvent we)
	{
		if(operation==1 && ethread.isAlive())
			ethread.abort();
		else if(operation==2 && dthread.isAlive())
			dthread.abort();
		else
			this.dispose();			
	}

	public void windowActivated(WindowEvent we) {}
	public void windowClosed(WindowEvent we) {}
	public void windowDeactivated(WindowEvent we) {}
	public void windowDeiconified(WindowEvent we) {}
	public void windowIconified(WindowEvent we) {}
	public void windowOpened(WindowEvent we) {}
}

class AskDialog extends JDialog implements ActionListener
{
	JLabel display;
	JButton yes,no;
	String source;
	JDialog deletion;

	AskDialog(String source)
	{
		super();

		this.source=source;

		setModal(true);
		setSize(500,275);
		setLocation(300,200);
		setResizable(false);
		setTitle("Deletion confirmation");
		
		Container c=getContentPane();
		c.setLayout(null);
		c.setBackground(new Color(195,200,250));

		display=new JLabel("Do you want to delete the source file ?",new ImageIcon("Images/bigimages/fire.png"),SwingConstants.CENTER);

		yes=new JButton("Yes",new ImageIcon("Images/accept.png"));
		no=new JButton("No",new ImageIcon("Images/delete.png"));
		
		yes.addActionListener(this);
		no.addActionListener(this);

		display.setBounds(20,60,460,70);
		yes.setBounds(90,175,140,35);
		no.setBounds(270,175,140,35);
		
		c.add(display);
		c.add(yes);
		c.add(no);

		repaint();
		setVisible(true);
	}	

	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==yes)
		{
			this.dispose();
			deletion=new DeletionDialog(source);
		}
		else if(ae.getSource()==no)
			this.dispose();
	}	
}

class DeletionDialog extends JDialog implements ActionListener
{
	JProgressBar p;
	JLabel display;
	JButton done;
	String source;
	DeleteThread deletionthread;

	DeletionDialog(String source)
	{
		super();

		this.source=source;

		setModal(true);
		setSize(500,275);
		setLocation(300,200);
		setResizable(false);
		setTitle("Deleting the source file");
		
		Container c=getContentPane();
		c.setLayout(null);
		c.setBackground(new Color(195,200,250));

		display=new JLabel("Deletion of source file in progress",new ImageIcon("Images/bigimages/fire.png"),SwingConstants.CENTER);
		p=new JProgressBar();
		p.setForeground(new Color(0,0,130));

		done=new JButton("Done",new ImageIcon("Images/done.png"));
		done.setEnabled(false);
		done.addActionListener(this);

		display.setBounds(20,50,460,70);
		p.setBounds(20,140,460,15);
		done.setBounds(340,185,140,35);
		
		c.add(display);
		c.add(p);
		c.add(done);

		repaint();
		setEnabled(false);

		deletionthread=new DeleteThread(p,this.source,this,this.display,this.done);
		deletionthread.start();

		setVisible(true);	
	}	

	public void actionPerformed(ActionEvent ae)
	{
		this.dispose();
	}	
}

class DeleteThread extends Thread
{
	JProgressBar p;
	String source;
	JDialog parent;
	JLabel display;
	JButton done;
	
	DeleteThread(JProgressBar p,String source,JDialog parent,JLabel display,JButton done)
	{
		this.p=p;
		this.source=source;
		this.parent=parent;
		this.display=display;
		this.done=done;
	}

	public void run()
	{
		RandomAccessFile myfile=null;

		try
		{
			File temporary=new File(source);
			myfile=new RandomAccessFile(temporary,"rw");
			
			p.setMinimum(0);
			p.setMaximum(199);

			for(int i=0;i<200;i++)
			{
				p.setValue(i);
				myfile.writeInt(0);
			}
			myfile.close();
			
			boolean value=temporary.delete();

			done.setEnabled(true);

			if(value==true)
				display.setText("File deleted sucessfully");
			else
				display.setText("Unable to delete, file is rights protected");

			parent.setEnabled(true);

		}
		catch(Exception e)
		{
			p.setValue(199);
			done.setEnabled(true);
			display.setText("Unable to delete, file is rights protected");
			parent.setEnabled(true);

			try
			{
				myfile.close();
			}
			catch(Exception error)
			{
			}
		}
	}
}

class EncryptThread extends Thread
{
	JProgressBar p;
	int which;
	String source;
	String destination;
	String keyvalue;
	JButton abort,done;
	JLabel display;
	boolean watchdog;
	JDialog ask;

	EncryptThread(JProgressBar p,String source,String destination,int which,String keyvalue,JButton abort,JButton done,JLabel display)
	{
		super();
		watchdog=false;		

		this.p=p;
		this.source=source;
		this.destination=destination;
		this.which=which;
		this.keyvalue=keyvalue;
		this.abort=abort;
		this.done=done;
		this.display=display;
	}

	public void run() 
	{
		try
		{
		
			switch(which)
			{
				case 0:
					Caesar.encryption(p,source,destination,keyvalue);
					break;
				case 1:
					Rail.encryption(p,source,destination,keyvalue);
					break;
				case 2:
					Column.encryption(p,source,destination,keyvalue);
					break;
				case 3:
					SpecialColumn.encryption(p,source,destination,keyvalue);
					break;
				case 4:
					Enigma.encryption(p,source,destination,keyvalue);
					break;
			}
			abort.setEnabled(false);
			done.setEnabled(true);

			if(watchdog)
				display.setText("Encryption aborted");
			else
			{
				display.setText("Encryption complete");
				ask=new AskDialog(source);
			}
		}
		catch(Exception e)
		{
			JDialog error=new ErrorDialog("A fatal error occured");
		}
	}
	
	public void abort()
	{
		watchdog=true;
		switch(which)
		{
			case 0:
				Caesar.setFlag(false);
				break;
			case 1:
				Rail.setFlag(false);
				break;
			case 2:
				Column.setFlag(false);
				break;
			case 3:
				SpecialColumn.setFlag(false);
				break;
			case 4:
				Enigma.setFlag(false);
				break;
		}
	}
}

class DecryptThread extends Thread
{
	JProgressBar p;
	int which;
	String source;
	String destination;
	String keyvalue;
	JButton abort,done;
	JLabel display;
	boolean watchdog;
	JDialog ask;

	DecryptThread(JProgressBar p,String source,String destination,int which,String keyvalue,JButton abort,JButton done,JLabel display)
	{
		super();
		watchdog=false;
		
		this.p=p;
		this.source=source;
		this.destination=destination;
		this.which=which;
		this.keyvalue=keyvalue;
		this.abort=abort;
		this.done=done;
		this.display=display;
	}

	public void run() 
	{
		try
		{
		
			switch(which)
			{
				case 0:
					Caesar.decryption(p,source,destination,keyvalue);
					break;
				case 1:
					Rail.decryption(p,source,destination,keyvalue);
					break;
				case 2:
					Column.decryption(p,source,destination,keyvalue);
					break;
				case 3:
					SpecialColumn.decryption(p,source,destination,keyvalue);
					break;
				case 4:
					Enigma.decryption(p,source,destination,keyvalue);
					break;
			}
			abort.setEnabled(false);
			done.setEnabled(true);

			if(watchdog)
				display.setText("Decryption aborted");
			else
			{
				display.setText("Decryption complete");
				ask=new AskDialog(source);
			}
		}
		catch(Exception e)
		{
			JDialog error=new ErrorDialog("A fatal error occured");
		}
	}
	
	public void abort()
	{
		watchdog=true;
		switch(which)
		{
			case 0:
				Caesar.setFlag(false);
				break;
			case 1:
				Rail.setFlag(false);
				break;
			case 2:
				Column.setFlag(false);
				break;
			case 3:
				SpecialColumn.setFlag(false);
				break;
			case 4:
				Enigma.setFlag(false);
				break;
		}
	}
}

class Enigma
{
	private static int keydata[];

	public static JProgressBar p;
	public static boolean flag=true;
	
	public static void setFlag(boolean value)
	{
		Enigma.flag=value;
	}

	public static void storeKey(String key)
	{
		byte temp[]=key.getBytes();
		keydata=new int[temp.length];

		for(int i=0;i<temp.length;i++)
			keydata[i]=(int)temp[i];
	}
		

//This function performs the EX-OR operation
	public static int exor(int value,int keyvalue)
	{
		return value^keyvalue;	
	}

//The function will rotate the bits towards the left
	public static int shiftLeft(int value)
	{
		int temp=0;
		int result=value&31;

		temp=value&224;
		temp=temp>>>5;
		result=result<<3;
		result=result|temp;
		return result;
	}

//This function will rotate the bits towards the right
	public static int shiftRight(int value)
	{
		int temp=0;
		int result=value&248;

		temp=value&7;
		temp=temp<<5;
		result=result>>>3;
		result=result|temp;	
		return result;
	}

//This function will perform the not operation
	public static int not(int value)
	{
		return ~value&255;
	}

//data -> p array    and    key -> values array

	public static int[] encrypt(int data[])
	{
		

	//int master=0;
		for(int master=0;master<4 && flag;master++)
		{

			int temp1,temp2,value;
			int count=7;

		//The first step in encryption

			for(int i=1;i<4 && flag;i++)
			{
				data[i]=Enigma.exor(data[i],keydata[i+(master*4)]);
		
		//First step ends

		//The second stage in encryption (The swap bits logic)

				temp1=0;
				temp2=0;
				value=0;

				for(int j=0;j<2;j++,count--)
					value+=Math.pow(2,count);

				temp1=data[0]&value;
				data[0]&=(255-value);
				temp1>>>=(8-(i*2));

				temp2=data[i]&3;
				data[i]&=252;
				temp2<<=(8-(i*2));

				data[0]|=temp2;
				data[i]|=temp1;

			}

		

			data[0]=Enigma.not(data[0]);
			data[0]=Enigma.shiftLeft(data[0]);
			
			for(int i=1;i<4 && flag;i++)
			{

		//The third stage (NOT operations)
	
				data[i]=Enigma.not(data[i]);

		//The fourth stage (3 times left shift)

				data[i]=Enigma.shiftLeft(data[i]);
	
		//The fifth stage
		
				data[i]=Enigma.exor(data[i],data[0]);
			}
		
		//The sixth stage
		
			int pralhad;

			pralhad=data[0];
			data[0]=data[2];
			data[2]=pralhad;
	
			pralhad=data[0];
			data[0]=data[1];
			data[1]=pralhad;

		//The seventh stage
			for(int i=0;i<4 && flag;i++)
				data[i]=Enigma.exor(data[i],keydata[master*4]);
		}
		
		return data;		
	}

	public static int[] decrypt(int data[])
	{

		//int master=0;
		for(int master=3;master>=0 && flag;master--)
		{
		//Decryption stage one
		
			for(int i=0;i<4 && flag;i++)
				data[i]=Enigma.exor(data[i],keydata[master*4]);


		//Decryption stage two
		
			int pralhad;
	
			pralhad=data[0];
			data[0]=data[1];
			data[1]=pralhad;
	
			pralhad=data[0];
			data[0]=data[2];
			data[2]=pralhad;

		//Decryption stage three
		
			for(int i=1;i<4 && flag;i++)
			{
	
				data[i]=Enigma.exor(data[i],data[0]);
		
		//Decryption stage four

				data[i]=Enigma.shiftRight(data[i]);
			
		//Decryption stage five

				data[i]=Enigma.not(data[i]);

			}
		
			data[0]=Enigma.shiftRight(data[0]);
			data[0]=Enigma.not(data[0]);

		//Decryption stage sixth (swap bits logic)

			int temp1,temp2,value;
			int count=7;
			
			for(int i=1;i<4 && flag;i++)
			{
				temp1=0;
				temp2=0;
				value=0;

				for(int j=0;j<2;j++,count--)
					value+=Math.pow(2,count);

				temp1=data[0]&value;
				data[0]&=(255-value);
				temp1>>>=(8-(i*2));

				temp2=data[i]&3;
				data[i]&=252;
				temp2<<=(8-(i*2));
		
				data[0]|=temp2;
				data[i]|=temp1;

		
		//Decryption stage seventh (last stage)

				data[i]=Enigma.exor(data[i],keydata[i+(master*4)]);
			}
		}

	return data;

	}

	public static void storeExtension(RandomAccessFile d,String extension) throws Exception
	{
		d.seek(0);

		byte temp[]=extension.getBytes();
		int count=temp.length;

		d.write(count);
		for(int i=0;i<temp.length && i<9;i++)
			d.write((int)temp[i]);
	}

	public static String extractExtension(RandomAccessFile f) throws Exception
	{
		f.seek(0);
		byte temp[];

		int count=f.read();
		temp=new byte[count];
		
		for(int i=0;i<count && i<9;i++)
			temp[i]=(byte)f.read();
		
		return new String(temp);
	}


	public static void encryption(JProgressBar p,String origin,String target,String keyvalue) throws Exception
	{
		Enigma.p=p;
		Enigma.flag=true;

		File source,destination;
		String key;
		int buffer[]=new int[4];
		RandomAccessFile f=null;
		RandomAccessFile d=null;
		String temp;

		try
		{
			source=new File(origin);
			f=new RandomAccessFile(source,"r");

			temp=source.getName().substring(0,source.getName().lastIndexOf('.'));
			destination=Common.calculateName(new File(target+temp+".p4e"));
			d=new RandomAccessFile(destination,"rw");
			
			p.setMinimum(0);
			p.setMaximum((int)f.length()+9);

			key=keyvalue;
			Enigma.storeKey(key);

			temp=source.getName().substring(source.getName().lastIndexOf('.'),source.getName().length());
			Enigma.storeExtension(d,temp);

			d.seek(10);

			long length=f.length();
			for(long i=0;i<(length/4) && flag;i++)
			{
				for(int j=0;j<4 && flag;j++)
					buffer[j]=f.read();
				buffer=Enigma.encrypt(buffer);
				for(int j=0;j<4 && flag;j++)
					d.write(buffer[j]);
				p.setValue((int)d.getFilePointer());
			}
			
			if(length%4!=0)
				for(int i=0;i<(length%4);i++)
					d.write(f.read());

			p.setValue((int)d.getFilePointer());
		
		}
		catch(Exception e)
		{
			JDialog error=new ErrorDialog("A fatal error occured");
			System.exit(0);
		}
		finally
		{
				f.close();
				d.close();
		}
	}

	public static void decryption(JProgressBar p,String origin,String target,String keyvalue) throws Exception
	{
		Enigma.p=p;
		Enigma.flag=true;

		File source,destination;
		String key;
		int buffer[]=new int[4];
		RandomAccessFile f=null;
		RandomAccessFile d=null;
		String temp;

		try
		{
			source=new File(origin);
			f=new RandomAccessFile(source,"r");

			temp=source.getName().substring(0,source.getName().lastIndexOf('.'));
			
			destination=Common.calculateName(new File(target+temp+Enigma.extractExtension(f)));
			d=new RandomAccessFile(destination,"rw");
			
			p.setMinimum(0);
			p.setMaximum((int)f.length()-11);

			key=keyvalue;
			Enigma.storeKey(key);

			f.seek(10);

			long length=(f.length()-10);
			for(long i=0;i<(length/4) && flag;i++)
			{
				for(int j=0;j<4 && flag;j++)
					buffer[j]=f.read();
				buffer=Enigma.decrypt(buffer);
				for(int j=0;j<4 && flag;j++)
				{
					p.setValue((int)d.getFilePointer());
					d.write(buffer[j]);
				}
			}

			if(length%4!=0)
				for(int i=0;i<(length%4);i++)
					d.write(f.read());

			p.setValue((int)d.getFilePointer());
		
		}
		catch(Exception e)
		{
			JDialog error=new ErrorDialog("A fatal error occured");
			System.exit(0);
		}
		finally
		{
			f.close();
			d.close();
		}
	}
			
}

class SpecialColumn 
{
	public static JProgressBar p;
	public static boolean flag=true;
	
	static int ceq[];
	static char c[];

	public static void setFlag(boolean value)
	{
		SpecialColumn.flag=value;
	}

	public static void storeKey(String key)
	{

	//Sorting of the characters in the keyword logic
	//Sorting is needed to understand and pinpoint from where to start the next step

		c=key.toCharArray();
		ceq=new int[c.length];

		for(int i=0;i<ceq.length;i++)
		{
			ceq[i]=i;
			c[i]=Character.toUpperCase(c[i]);
		}

		char temp;
		int itemp;

		for(int i=0;i<(c.length-1);i++)
		{
			temp=c[i];
			itemp=ceq[i];
			for(int j=i+1;j<c.length;j++)
			{
				if(Character.getNumericValue(temp)>Character.getNumericValue(c[j]))
				{
					c[i]=c[j];
					c[j]=temp;
					temp=c[i];
					
					ceq[i]=ceq[j];
					ceq[j]=itemp;
					itemp=ceq[i];
				}
			}
		}
	}

	public static void encrypt(RandomAccessFile f,RandomAccessFile d) throws Exception
	{
		d.seek(10);
		
		for(int i=0;i<c.length && flag;i++)
			for(int j=0;((j*c.length)+ceq[i])<f.length() && flag;j++)
			{
				f.seek((j*c.length)+ceq[i]);
				p.setValue((int)d.getFilePointer());
				d.write(f.read());
			}
	}

	public static void decrypt(RandomAccessFile f,RandomAccessFile d) throws Exception
	{
		f.seek(10);

		int pointers[],temppointer;
		pointers=new int[c.length];
		temppointer=0;
	
		for(int i=0;i<c.length;i++)
		{
			pointers[ceq[i]]=temppointer;
			temppointer+=(int)((f.length()-10)/c.length);

			if(ceq[i]<((f.length()-10)%c.length))
				temppointer+=1;
		}

		int rows=(int)((f.length()-10)/c.length);

		if(((f.length()-10)%c.length)>0)
			rows+=1;

		for(int i=0;i<rows && flag;i++)
			for(int j=0;j<c.length && flag;j++)
			{
				f.seek(pointers[j]+10);
				p.setValue((int)d.getFilePointer());
				d.write(f.read());
				pointers[j]++;
			}
	}
	
	public static void storeExtension(RandomAccessFile d,String extension) throws Exception
	{
		d.seek(0);

		byte temp[]=extension.getBytes();
		int count=temp.length;

		d.write(count);
		for(int i=0;i<temp.length && i<9;i++)
			d.write((int)temp[i]);
	}

	public static String extractExtension(RandomAccessFile f) throws Exception
	{
		f.seek(0);
		byte temp[];

		int count=f.read();
		temp=new byte[count];
		
		for(int i=0;i<count && i<9;i++)
			temp[i]=(byte)f.read();
		
		return new String(temp);
	}

	public static void encryption(JProgressBar p,String origin,String target,String keyvalue) throws Exception
	{
		SpecialColumn.p=p;
		SpecialColumn.flag=true;

		File source,destination;
		RandomAccessFile f=null;
		RandomAccessFile d=null;
		String temp;		

		try
		{
			source=new File(origin);

			f=new RandomAccessFile(source,"r");	
			temp=source.getName().substring(0,source.getName().lastIndexOf('.'));
			
			destination=Common.calculateName(new File(target+temp+".p4e"));
			d=new RandomAccessFile(destination,"rw");
			
			p.setMinimum(0);
			p.setMaximum((int)f.length()+9);

			SpecialColumn.storeKey(keyvalue);
			temp=source.getName().substring(source.getName().lastIndexOf('.'),source.getName().length());

			SpecialColumn.storeExtension(d,temp);
			SpecialColumn.encrypt(f,d);
		}
		catch(Exception e)
		{
			JDialog error=new ErrorDialog("A fatal error occured");
			System.exit(0);
		}
		finally
		{
				f.close();
				d.close();
		}
	}

	public static void decryption(JProgressBar p,String origin,String target,String keyvalue) throws Exception
	{
		SpecialColumn.p=p;
		SpecialColumn.flag=true;

		File source,destination;
		RandomAccessFile f=null;
		RandomAccessFile d=null;
		String temp;		

		try
		{
			source=new File(origin);

			f=new RandomAccessFile(source,"r");	
			temp=source.getName().substring(0,source.getName().lastIndexOf('.'));
			
			destination=Common.calculateName(new File(target+temp+SpecialColumn.extractExtension(f)));
			d=new RandomAccessFile(destination,"rw");

			p.setMinimum(0);
			p.setMaximum((int)f.length()-11);

			SpecialColumn.storeKey(keyvalue);
			SpecialColumn.decrypt(f,d);
		}
		catch(Exception e)
		{
			JDialog error=new ErrorDialog("A fatal error occured");
			System.exit(0);
		}
		finally
		{
				f.close();
				d.close();
		}
	}
}

class Rail 
{
	public static JProgressBar p;
	public static boolean flag=true;

	public static void setFlag(boolean value)
	{
		Rail.flag=value;
	}

	public static void encrypt(RandomAccessFile f,RandomAccessFile d,int depth) throws Exception
	{

		d.seek(10);
		int rowlength[]=new int[depth+1];
	
		for(int i=0;i<(int)((f.length())/(depth*2));i++)
		{
			rowlength[0]+=1;

			for(int j=1;j<(rowlength.length-1);j++)
				rowlength[j]+=2;
			
			rowlength[depth]+=1;
		}
		
			int remainder=(int)((f.length())%(depth*2));
			for(int i=0;i<(int)((f.length())%(depth*2)) && i<rowlength.length;i++,remainder--)
				rowlength[i]+=1;	
			
			if(remainder>0)
				for(int i=depth-1;remainder>0;i--,remainder--)
					rowlength[i]+=1;
//The calculation of elements logic ends here

//The encryption logic
		

	//For top elements
		for(int i=0;i<rowlength[0] && flag;i++)
		{
			f.seek((long)i*depth*2);
			p.setValue((int)d.getFilePointer());
			d.write(f.read());
		}
			
			
	//For intermediate elements
		int var1,var2;
				

		for(int i=1;i<depth && flag;i++)
		{
			var1=0;
			var2=0;

			for(int j=0;j<rowlength[i] && flag;j++)
			{
				f.seek((long)((var1*((depth-i)*2))+(var2*(i*2))+i));
				p.setValue((int)d.getFilePointer());
				d.write(f.read());
				if(j%2==0)
					var1++;	
				else
					var2++;
			}
		}
	
	//For lower elements
		for(int i=0;i<rowlength[depth] && flag;i++)
		{
			f.seek((long)((i*depth*2)+depth));
			p.setValue((int)d.getFilePointer());
			d.write(f.read());
		}

	//Encryption logic ends
	}

	public static void decrypt(RandomAccessFile f,RandomAccessFile d,int depth) throws Exception
	{

	//Decryption logic
		f.seek(10);
		
		int pointer[],rowlength[];

		rowlength=new int[depth+1];
		pointer=new int[depth+1];

	//Basic calculations

		for(int i=0;i<(int)((f.length()-10)/(depth*2));i++)
		{
			rowlength[0]+=1;

			for(int j=1;j<(rowlength.length-1);j++)
				rowlength[j]+=2;
			
			rowlength[depth]+=1;
		}
		
		int remainder=(int)((f.length()-10)%(depth*2));
		for(int i=0;i<(int)((f.length()-10)%(depth*2)) && i<rowlength.length;i++,remainder--)
			rowlength[i]+=1;	
			
		if(remainder>0)
			for(int i=depth-1;remainder>0;i--,remainder--)
				rowlength[i]+=1;
				
	//End of basic calculations

	//Actual decryption logic starts

		pointer[0]=0;
		for(int i=1;i<pointer.length;i++)
			pointer[i]=pointer[(i-1)]+rowlength[(i-1)];

		for(int i=0;i<(int)((f.length()-10)/(depth*2)) && flag;i++)
		{
			f.seek((long)pointer[0]+10);
			p.setValue((int)d.getFilePointer());
			d.write(f.read());
			pointer[0]++;
			
			for(int j=1;j<depth && flag;j++)
			{
				f.seek((long)pointer[j]+10);
				p.setValue((int)d.getFilePointer());
				d.write(f.read());
				pointer[j]++;
			}
		
			f.seek((long)pointer[depth]+10);
			p.setValue((int)d.getFilePointer());
			d.write(f.read());
			pointer[depth]++;
	
			for(int j=(depth-1);j>0 && flag;j--)
			{
				f.seek((long)pointer[j]+10);
				p.setValue((int)d.getFilePointer());
				d.write(f.read());
				pointer[j]++;
			}
		}
		
		int remaining=(int)((f.length()-10)%(depth*2));
		for(int i=0;i<=depth && i<(int)((f.length()-10)%(depth*2)) && flag;i++,remaining--)
		{
			f.seek((long)pointer[i]+10);
			p.setValue((int)d.getFilePointer());
			d.write(f.read());
			pointer[i]++;
		}
	
		if(remaining>0)
			for(int i=(depth-1);remaining>0 && flag;i--,remaining--)
			{
				f.seek((long)pointer[i]+10);
				p.setValue((int)d.getFilePointer());
				d.write(f.read());
				pointer[i]++;
			}					
			
//Decryption logic ends
	
	}
		
	public static void storeExtension(RandomAccessFile d,String extension) throws Exception
	{
		d.seek(0);

		byte temp[]=extension.getBytes();
		int count=temp.length;

		d.write(count);
		for(int i=0;i<temp.length && i<9;i++)
			d.write((int)temp[i]);
	}

	public static String extractExtension(RandomAccessFile f) throws Exception
	{
		f.seek(0);
		byte temp[];

		int count=f.read();
		temp=new byte[count];
		
		for(int i=0;i<count && i<9;i++)
			temp[i]=(byte)f.read();
		
		return new String(temp);
	}
		
	public static void encryption(JProgressBar p,String origin,String target,String keyvalue) throws Exception
	{
		Rail.p=p;
		Rail.flag=true;

		File source,destination;
		RandomAccessFile f=null;
		RandomAccessFile d=null;
		int depth;
		String temp;

		try
		{
			source=new File(origin);

			f=new RandomAccessFile(source,"r");	
			temp=source.getName().substring(0,source.getName().lastIndexOf('.'));
			
			destination=Common.calculateName(new File(target+temp+".p4e"));
			d=new RandomAccessFile(destination,"rw");
		
			p.setMinimum(0);
			p.setMaximum((int)f.length()+9);
	
			depth=Integer.parseInt(keyvalue);
			temp=source.getName().substring(source.getName().lastIndexOf('.'),source.getName().length());
			
			Rail.storeExtension(d,temp);
			Rail.encrypt(f,d,depth);
		}
		catch(Exception e)
		{
			JDialog error=new ErrorDialog("A fatal error occured");
			System.exit(0);
		}
		finally
		{
				f.close();
				d.close();
		}
	}

	
	public static void decryption(JProgressBar p,String origin,String target,String keyvalue) throws Exception
	{
		Rail.p=p;
		Rail.flag=true;

		File source,destination;
		RandomAccessFile f=null;
		RandomAccessFile d=null;
		int depth;
		String temp;

		try
		{
			source=new File(origin);

			f=new RandomAccessFile(source,"r");	
			temp=source.getName().substring(0,source.getName().lastIndexOf('.'));
			
			destination=Common.calculateName(new File(target+temp+Rail.extractExtension(f)));
			d=new RandomAccessFile(destination,"rw");

			p.setMinimum(0);
			p.setMaximum((int)f.length()-11);

			depth=Integer.parseInt(keyvalue);

			Rail.decrypt(f,d,depth);
		}
		catch(Exception e)
		{
			JDialog error=new ErrorDialog("A fatal error occured");
			System.exit(0);
		}
		finally
		{
				f.close();
				d.close();
		}
	}
}

class Column
{
	public static JProgressBar p;
	public static boolean flag=true;
 
	public static void setFlag(boolean value)
	{
		Column.flag=value;
	}

	public static void encrypt(RandomAccessFile f,RandomAccessFile d,int columns) throws Exception
	{
		d.seek(10);
		for(int i=0;i<columns && flag;i++)
			for(int j=0;(j*columns+i)<f.length() && flag;j++)
			{
				f.seek(j*columns+i);
				p.setValue((int)d.getFilePointer());			
				d.write(f.read());
			}
	}
		
	public static void decrypt(RandomAccessFile f,RandomAccessFile d,int columns) throws Exception
	{
		f.seek(10);
		int rows;
		long temp;
		int cache=(int)((f.length()-10)%columns);
		int buffer=(int)((f.length()-10)/columns);
		
		if(cache==0)
			rows=buffer;
		else
			rows=(buffer+1);

		for(int i=0;i<rows && flag;i++)
		{
			temp=i;
			for(int j=0;j<columns && flag;j++)
			{
				f.seek(temp+10);
				p.setValue((int)d.getFilePointer());
				d.write(f.read());
				temp+=buffer;

				if(j<cache)
					temp++;
			}
		}
	}

	public static void storeExtension(RandomAccessFile d,String extension) throws Exception
	{
		d.seek(0);

		byte temp[]=extension.getBytes();
		int count=temp.length;

		d.write(count);
		for(int i=0;i<temp.length && i<9;i++)
			d.write((int)temp[i]);
	}

	public static String extractExtension(RandomAccessFile f) throws Exception
	{
		f.seek(0);
		byte temp[];

		int count=f.read();
		temp=new byte[count];
		
		for(int i=0;i<count && i<9;i++)
			temp[i]=(byte)f.read();
		
		return new String(temp);
	}
		
	public static void encryption(JProgressBar p,String origin,String target,String keyvalue) throws Exception
	{
		Column.p=p;
		Column.flag=true;

		File source,destination;
		RandomAccessFile f=null;
		RandomAccessFile d=null;
		int columns;
		String temp;

		try
		{
			source=new File(origin);

			f=new RandomAccessFile(source,"r");	
			temp=source.getName().substring(0,source.getName().lastIndexOf('.'));
			
			destination=Common.calculateName(new File(target+temp+".p4e"));
			d=new RandomAccessFile(destination,"rw");
		
			p.setMinimum(0);
			p.setMaximum((int)f.length()+9);	
		
			columns=Integer.parseInt(keyvalue);
			temp=source.getName().substring(source.getName().lastIndexOf('.'),source.getName().length());
			
			Column.storeExtension(d,temp);
			Column.encrypt(f,d,columns);

		}
		catch(Exception e)
		{
			JDialog error=new ErrorDialog("A fatal error occured");
			System.exit(0);
		}
		finally
		{
				f.close();
				d.close();
		}
	}

	public static void decryption(JProgressBar p,String origin,String target,String keyvalue) throws Exception
	{
		Column.p=p;
		Column.flag=true;

		File source,destination;
		RandomAccessFile f=null;
		RandomAccessFile d=null;
		int columns;
		String temp;
		
		try		
		{
			source=new File(origin);

			f=new RandomAccessFile(source,"r");	
			temp=source.getName().substring(0,source.getName().lastIndexOf('.'));
			
			destination=Common.calculateName(new File(target+temp+Column.extractExtension(f)));
			d=new RandomAccessFile(destination,"rw");

			p.setMinimum(0);
			p.setMaximum((int)f.length()-11);

			columns=Integer.parseInt(keyvalue);

			Column.decrypt(f,d,columns);

		}
		catch(Exception e)
		{
			JDialog error=new ErrorDialog("A fatal error occured");
			System.exit(0);
		}
		finally
		{
				f.close();
				d.close();
		}
	}
}

class Caesar
{
	public static JProgressBar p;
	public static boolean flag=true;
	
	public static void setFlag(boolean value)
	{
		Caesar.flag=value;
	}
	
	public static void encrypt(RandomAccessFile f,RandomAccessFile d,int key) throws Exception
	{
		int temp;
		key%=256;

		d.seek(10);

		for(int i=0;i<f.length() && flag;i++)
		{
			temp=f.read();
			temp+=key;
			temp%=256;

			p.setValue((int)d.getFilePointer());

			d.write(temp);
		}
	}

	public static void decrypt(RandomAccessFile f,RandomAccessFile d,int key) throws Exception
	{
		int temp;
		key%=256;
		
		f.seek(10);
		
		for(int i=0;i<(f.length()-10) && flag;i++)
		{
			temp=f.read();
			temp-=key;
			
			if(temp<0)
				temp+=256;

			p.setValue((int)d.getFilePointer());

			d.write(temp);
		}
	}

	public static void storeExtension(RandomAccessFile d,String extension) throws Exception
	{
		d.seek(0);

		byte temp[]=extension.getBytes();
		int count=temp.length;

		d.write(count);
		for(int i=0;i<temp.length && i<9;i++)
			d.write((int)temp[i]);
	}

	public static String extractExtension(RandomAccessFile f) throws Exception
	{
		f.seek(0);
		byte temp[];

		int count=f.read();
		temp=new byte[count];
		
		for(int i=0;i<count && i<9;i++)
			temp[i]=(byte)f.read();
		
		return new String(temp);
	}	
					
	public static void encryption(JProgressBar p,String origin,String target,String keyvalue) throws Exception
	{
		Caesar.p=p;
		Caesar.flag=true;

		File source,destination;
		RandomAccessFile f=null;
		RandomAccessFile d=null;
		
		String temp;

		try
		{
			int key=Integer.parseInt(keyvalue);
			source=new File(origin);

			f=new RandomAccessFile(source,"r");	
			temp=source.getName().substring(0,source.getName().lastIndexOf('.'));
			
			destination=Common.calculateName(new File(target+temp+".p4e"));
			d=new RandomAccessFile(destination,"rw");
			
			temp=source.getName().substring(source.getName().lastIndexOf('.'),source.getName().length());

			p.setMinimum(0);
			p.setMaximum((int)f.length()+9);
		
			Caesar.storeExtension(d,temp);
			Caesar.encrypt(f,d,key);
		}
		catch(Exception e)
		{
			JDialog error=new ErrorDialog("A fatal error occured");
			System.exit(0);
		}
		finally
		{
				f.close();
				d.close();
		}
	}

	public static void decryption(JProgressBar p,String origin,String target,String keyvalue) throws Exception
	{
		Caesar.p=p;
		Caesar.flag=true;

		File source,destination;
		RandomAccessFile f=null;
		RandomAccessFile d=null;
		int key;
		String temp;

		try
		{	
			source=new File(origin);

			f=new RandomAccessFile(source,"r");	
			temp=source.getName().substring(0,source.getName().lastIndexOf('.'));
			
			destination=Common.calculateName(new File(target+temp+Caesar.extractExtension(f)));
			d=new RandomAccessFile(destination,"rw");

			key=Integer.parseInt(keyvalue);

			p.setMinimum(0);
			p.setMaximum((int)f.length()-11);

			Caesar.decrypt(f,d,key);
		}
		catch(Exception e)
		{
			JDialog error=new ErrorDialog("A fatal error occured in operations");
			System.exit(0);
		}
		finally
		{
			f.close();
			d.close();
		}
	}
}	

class Common
{
	public static File calculateName(File f) throws Exception
	{
		File temp;
		String name,extension,directory,namecache;

		namecache=name=f.getName().substring(0,f.getName().lastIndexOf('.'));
		extension=f.getName().substring(f.getName().lastIndexOf('.'),f.getName().length());

		directory=f.getAbsolutePath().substring(0,f.getAbsolutePath().lastIndexOf('\\')+1);
		temp=f;

		for(int i=2;i<999999999 && temp.exists();i++)
		{
			name=namecache;
			name=name+"("+i+")";
			temp=new File(directory+name+extension);
		}

		return temp;	
	}	
}		
		

		

		