import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Set; 
import java.util.HashSet;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Connected extends JFrame implements ActionListener
{
	String path,graypath,dilatepath,bwpath,connectedpath;
    	JPanel contentPane,contentPane2;
    	JButton browseBtn,reset,grayBtn,bwBtn,dilateBtn,concomp;
    	JLabel imgLabel,imgLabel2;
    	JLabel img1,img2,size,col,mes,head;
    	JTextField jtf1;
    	JComboBox<String> jc=new JComboBox<>();
    	JLabel[] jl=new JLabel[10];
    	Font myFont = new Font("TimesRoman", Font.BOLD, 24);
    	Font myFont1 = new Font("TimesRoman", Font.BOLD, 20);
    	Font myFont2 = new Font("TimesRoman", Font.ITALIC, 18);
    	Font myFont3 = new Font("TimesRoman", Font.BOLD, 16);
    	BufferedImage imageInput,imgInput;
    	BufferedImage image;
    	BufferedImage grayImage = null;
    	BufferedImage binaryImage = null;
    	BufferedImage dilatedImage = null;
	BufferedImage connectedImage=null;
    	int height;
    	int width;
    	int gr=0,bw=0,se,oc;
    	int fileToSave;

	public Connected()
	{
		setLayout(new BorderLayout());
		this.setTitle("Connected Components");
        		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        		setExtendedState(MAXIMIZED_BOTH);
        		setBounds(0, 0, 1366, 768);
		
		contentPane=new JPanel();
        		contentPane.setBackground(Color.LIGHT_GRAY);
        		contentPane.setLayout(null);
        		add(contentPane);

		head=new JLabel("Extraction of Connected Components");
        		head.setBounds(500,15,420,50);
        		head.setFont(myFont);
        		head.setForeground(Color.BLACK);
        		contentPane.add(head);

		 for(int i=0;i<10;i++)
            			jl[i]=new JLabel();
        		jl[0].setFont(myFont1);
        		jl[1].setFont(myFont1);
        		for(int i=2;i<10;i++)
            			jl[i].setFont(myFont2);
        		jl[0].setText("DILATION");
        		jl[1].setText("CONNECTED COMPONENT");
		jl[2].setText("Dilation generally increases the sizes of objects, filling in holes and broken areas, and");
		jl[3].setText("connecting areas that are separated by spaces smaller than the size of the structuring element.");
		jl[4].setText("With binary images, dilation connects areas that are separated by spaces smaller than");
		jl[5].setText("the structuring element and adds pixels to the perimeter of each image object.");
		jl[6].setText("Connected components refers to the regions in an image with same intensity value.");
		jl[7].setText("Connected components labelling scans an image and groups its pixels into components");
		jl[8].setText("based on pixel connectivity.");
		jl[9].setText("Please select an image*");

        		jl[0].setBounds(120,100,200,50);
        		jl[2].setBounds(170,150,800,50);
        		jl[3].setBounds(170,200,800,50);
        		jl[4].setBounds(170,250,800,50);
        		jl[5].setBounds(170,300,800,50);
        		jl[1].setBounds(120,350,350,50);
        		jl[6].setBounds(170,400,800,50);
        		jl[7].setBounds(170,450,800,50);
        		jl[8].setBounds(170,500,800,50);
        		jl[9].setBounds(600,600,800,50);
        		for(int i=0;i<10;i++)
            			contentPane.add(jl[i]);
        

		 imgLabel=new JLabel();
       		 imgLabel.setBounds(10, 120, 650, 500);
       		 contentPane.add(imgLabel);
        
        		imgLabel2=new JLabel();
       		imgLabel2.setBounds(700, 120, 650, 500);
      		contentPane.add(imgLabel2);
        		imgLabel2.setEnabled(false);
        		imgLabel2.setVisible(false);
        
        		browseBtn=new JButton("Select image");
        		browseBtn.setBounds(1000, 25, 120, 30);
        		contentPane.add(browseBtn);
		browseBtn.addActionListener(this);
        
        		reset=new JButton("Reset");
       		reset.setBounds(1150,25,120,30);
        		reset.setVisible(false);
        		contentPane.add(reset);
		reset.addActionListener(this);
        		
        		contentPane2=new JPanel();  
        		contentPane2.setBackground(Color.DARK_GRAY);
        		contentPane2.setVisible(false);
        		add(contentPane2,BorderLayout.SOUTH);
        
        		grayBtn=new JButton("Gray Scale");
        		contentPane2.add(grayBtn);
        		grayBtn.addActionListener(this);
        
        		bwBtn=new JButton("Binary");
        		contentPane2.add(bwBtn);
        		bwBtn.addActionListener(this);
        
        		size=new JLabel("SE size : ");
        		size.setForeground(Color.white);
        		contentPane2.add(size);
        
        		jtf1=new JTextField(5);
        		contentPane2.add(jtf1);
        
        		col=new JLabel("Select object color : ");
        		col.setForeground(Color.WHITE);
        		contentPane2.add(col);
        
        		jc.addItem("black");
        		jc.addItem("white");
        		contentPane2.add(jc);
        
        		dilateBtn=new JButton("Dilation");
        		dilateBtn.addActionListener(this);
        		contentPane2.add(dilateBtn);

		concomp=new JButton("Con_comp");
        		concomp.addActionListener(this);
        		contentPane2.add(concomp);
        
        		img1=new JLabel();
        		img1.setBounds(260,90,130,25);
        		img1.setFont(myFont3);
		img1.setForeground(Color.BLACK);
        		contentPane.add(img1);
        
        		img2=new JLabel();
        		img2.setBounds(980,90,150,25);        
        		img2.setFont(myFont3);
		img2.setForeground(Color.BLACK);
        		contentPane.add(img2);
        
        		mes=new JLabel();
        		mes.setBounds(580,645,290,20);
        		mes.setForeground(Color.red);
        		mes.setFont(myFont3); 
        		contentPane.add(mes);		
	} 
	public void actionPerformed(ActionEvent e) 
    	{
        		String s = e.getActionCommand();
       		if(s.equals("Select image"))
        		{
            			gr=0;
            			bw=0;
            			JFileChooser file = new JFileChooser();
            			file.setCurrentDirectory(new File(System.getProperty("user.home")));
            			FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg","gif","png");
            			file.addChoosableFileFilter(filter);
            			int result = file.showSaveDialog(null);
            			if(result == JFileChooser.APPROVE_OPTION)
            			{
                				File selectedFile = file.getSelectedFile();
                				path = selectedFile.getAbsolutePath();   
                				imgLabel.setEnabled(true);
                				imgLabel.setVisible(true);
                				imgLabel.setIcon(ResizeImage(path));
                				img2.setText("");
                				imgLabel2.setEnabled(false);
                				imgLabel2.setVisible(false);
                				contentPane2.setVisible(true);
                				img1.setText("Original Image");
                				reset.setVisible(true);
                				jtf1.setText("");
				for(int i=0;i<10;i++)
                   				jl[i].setVisible(false);
            			}
            			else if(result == JFileChooser.CANCEL_OPTION)
           			{
                				System.out.println("No File Select");
            			}   
        		}
        		if(s.equals("Reset"))
        		{
            			imgLabel.setEnabled(false);
            			imgLabel.setVisible(false);
            			imgLabel2.setEnabled(false);
            			imgLabel2.setVisible(false);
            			img1.setText("");
            			img2.setText("");
            			jtf1.setText("");
            			contentPane2.setVisible(false);
            			reset.setVisible(false);
			for(int i=0;i<10;i++)
                				jl[i].setVisible(true);
			mes.setText("");
        		}
        		if(s.equals("Gray Scale"))
        		{
            			if(gr==0)
            			{
                				toGray();
                				gr++;
            			}
            			imgLabel.setIcon(ResizeImage(path));
            			imgLabel2.setIcon(ResizeImage(graypath));
            			imgLabel2.setEnabled(true);
            			imgLabel2.setVisible(true);
            			img1.setText("Original Image");
            			img2.setText("GrayScale Image");
			mes.setText("");
        		}
        		if(s.equals("Binary"))
        		{
            			if(bw==0)
            			{
                				toBinary();
                				bw++;
            			}
            			imgLabel.setIcon(ResizeImage(path));
            			imgLabel2.setIcon(ResizeImage(bwpath));
            			imgLabel2.setEnabled(true);
           			imgLabel2.setVisible(true);
            			img1.setText("Original Image");
            			img2.setText("Binary Image");
			mes.setText("");
        		}
		if(s.equals("Dilation"))
        		{
            			int err=0;
           			if(gr==0)
            			{
               				toGray();
                				gr++;
            			}
            			if(bw==0)
            			{
               				toBinary();
                				bw++;
            			}
            			try
            			{
                				if(jtf1.getText().equals(""))
                				{
                    					err=1;
                    					mes.setText("*Please enter SE size");
                				}
                				se=Integer.parseInt(jtf1.getText());
                				oc=jc.getSelectedIndex();
                				if(se<0)
                				{	
                   					err=1;
                    					mes.setText("*Negative SE is not allowed");
                				}
                				if(se%2==0 && se>0)
                				{
                    					err=1;
                    					mes.setText("*Generally SE size is odd");
                				}
               				if(se>=0&&se<3)
                				{
                    					err=1;
                    					mes.setText("*SE size should be atleast 3");
                				}
            			}
            			catch(Exception ee)
            			{
                				System.out.println(ee);
                				err=1;
            			}
           			if(err==0)
            			{
               				toDilate(binaryImage,se,oc);
                				dilatepath=path;
                				dilatepath=dilatepath.replace(".jpg","_dilated.jpg");
                				File output_file3=new File(dilatepath);
                				try 
                				{
                   				ImageIO.write(dilatedImage, "jpg", output_file3);
                				}
                				catch (IOException ex)
                				{
                   				System.out.println(ex);
                				}
                				imgLabel.setIcon(ResizeImage(bwpath));
               				imgLabel2.setIcon(ResizeImage(dilatepath));
               				imgLabel2.setEnabled(true);
               				imgLabel2.setVisible(true);
                				img1.setText("Binary Image");
                				img2.setText("Dilated Image");
                				mes.setText("");
            			}
       		 }
		 if(s.equals("Con_comp"))
		{	
			int err=0;
           			if(gr==0)
            			{
               				toGray();
                				gr++;
            			}
            			if(bw==0)
            			{
               				toBinary();
                				bw++;
            			}
			try
            			{
                				if(jtf1.getText().equals(""))
                				{
                    					err=1;
                    					mes.setText("*Please enter SE size");
                				}
                				se=Integer.parseInt(jtf1.getText());
                				oc=jc.getSelectedIndex();
                				if(se<0)
                				{	
                   					err=1;
                    					mes.setText("*Negative SE is not allowed");
                				}
                				if(se%2==0 && se>0)
                				{
                    					err=1;
                    					mes.setText("*Generally SE size is odd");
                				}
               				if(se>=0&&se<3)
                				{
                    					err=1;
                    					mes.setText("*SE size should be atleast 3");
                				}
            			}
			catch(Exception ee)
            			{
                				System.out.println(ee);
                				err=1;
            			}
			if(err==0)
            			{
               				toConnectedComponent(binaryImage,se);
                				connectedpath=path;
                				connectedpath=connectedpath.replace(".jpg","_connected.jpg");
                				File output_file4=new File(connectedpath);
                				try 
                				{
                   				ImageIO.write(connectedImage, "jpg", output_file4);
                				}
                				catch (IOException ex)
                				{
                   				System.out.println(ex);
                				}
                				imgLabel.setIcon(ResizeImage(bwpath));
               				imgLabel2.setIcon(ResizeImage(connectedpath));
               				imgLabel2.setEnabled(true);
               				imgLabel2.setVisible(true);
                				img1.setText("Binary Image");
                				img2.setText("Con_comp Img");
                				mes.setText("");
            			}			
		}
	}

	public ImageIcon ResizeImage(String ImagePath)
    	{
        		ImageIcon MyImage = new ImageIcon(ImagePath);
        		Image img = MyImage.getImage();
        		Image newImg = img.getScaledInstance(imgLabel.getWidth(), imgLabel.getHeight(), Image.SCALE_SMOOTH);
        		ImageIcon retImage = new ImageIcon(newImg);
        		return retImage;
    	}

	public void toGray()
    	{
        		try
        		{
            			File input_file=new File(path);
            			image=ImageIO.read(input_file);
            			height=image.getHeight();
            			width=image.getWidth();
            			BufferedImage gray=new BufferedImage(image.getWidth(),image.getHeight(),BufferedImage.TYPE_BYTE_GRAY);
            			WritableRaster gRaster=gray.getRaster();
            			for(int i=0; i<height; i++) 
            			{
                				for(int j=0; j<width; j++) 
                				{
                    				Color c = new Color(image.getRGB(j, i));
                    				int red = (int)(c.getRed() * 0.299);
                    				int green = (int)(c.getGreen() * 0.587);
                    				int blue = (int)(c.getBlue() * 0.114);
                    				int n=red+green+blue;
                    				gRaster.setSample(j, i, 0, n); 
                				}
           			 }
            			graypath=path;
            			graypath=graypath.replace(".jpg","_gray.jpg");
            			File output_file=new File(graypath);
            			ImageIO.write(gray, "jpg", output_file);
        		}
       		catch(Exception e)
        		{
            			System.out.println("Exception:"+e);
        		}
    	}
   	public void toBinary()
    	{
       		try
       		{
            			File input_file=new File(path);
            			image=ImageIO.read(input_file);
            			height=image.getHeight();
            			width=image.getWidth();
            			binaryImage= new BufferedImage(image.getWidth(),image.getHeight(),BufferedImage.TYPE_BYTE_BINARY);
            			WritableRaster binRaster = binaryImage.getRaster();
            			WritableRaster grayRaster = image.getRaster();
            			for(int h=0;h<height;h++)
            			{
                				for(int w=0;w<width;w++)
                				{
                    				int[]p = new int[3];
                    				grayRaster.getPixel(w,h,p);
                    				if(p[0]>127)
                    				{
                        					binRaster.setSample(w,h,0,1);
                    				}
                    				else
                    				{
                        					binRaster.setSample(w,h,0,0);
                    				}
                				}
            			}
            			bwpath=path;
            			bwpath=bwpath.replace(".jpg","_bw.jpg");
            			File output_file2=new File(bwpath);
            			ImageIO.write(binaryImage, "jpg", output_file2);
       		}
       		catch(Exception e)
       		{
                                          	System.out.println("Exception:"+e);
       		}
    	}	
	public void  toDilate(BufferedImage image,int StructuringElementSize,int dilationColor)
    	{
		try{
        		dilatedImage = new BufferedImage(image.getWidth(),image.getHeight(),image.getType());
        		WritableRaster binRaster = image.getRaster();
        		WritableRaster dilRaster = dilatedImage.getRaster();
        		width = image.getWidth();
       		height = image.getHeight();
        		int s=StructuringElementSize;
        		int ele=s/2;
        		int i,j;
        		int target=dilationColor;
        		int reverse;
       		if(target==1)
            			reverse=0;
        		else
            			reverse=1; 
        		for(int x=0;x<width;x++)
       		{
            			for(int y=0;y<height;y++)
            			{
               				i=0;
                				j=0;
               				int [][]mat=new int[s][s];
                				for(int tx=x-ele;tx<=x+ele;tx++)
                				{
                    				j=0;
                    				for(int ty=y-ele;ty<=y+ele;ty++)
                    				{
                        					if(ty>=0 && ty<height && tx>=0 && tx<width)
                        					{
                            						int q=0;
                            						int eleVal=binRaster.getSample(tx, ty, q);
                            						mat[i][j]=eleVal;
                            						j++;
                        					}
                   				}
                    				i++;
                				}
                				int count=0;
                				for(int a=0;a<s;a++)
                    				for(int b=0;b<s;b++)
                        					if(mat[a][b]==target)
                            						count++;
                				if(count>=1)
                    				dilRaster.setSample(x,y,0,target);
                				else
                    				dilRaster.setSample(x,y,0,reverse); 
					
            			}
       		 }
		}
		catch(Exception e)
		{
			System.out.println("exception is caught in dilate");
			System.out.println("Exception:"+e);
		}
		
   	 }
	 
	public void  toConnectedComponent(BufferedImage image,int StructuringElementSize)
    	{
		
  		connectedImage = new BufferedImage(image.getWidth(),image.getHeight(),image.getType());
        		WritableRaster binRaster = image.getRaster();
        		WritableRaster conRaster = connectedImage.getRaster();
		width = image.getWidth();
       		height = image.getHeight();
		int s=StructuringElementSize;
        		int ele=s/2;
        		int x,y;
		
		Set<int[]> a = new HashSet<int[]>();
		Set<int[]> new_Set = new HashSet<int[]>();
		Set<int[]> old_Set = new HashSet<int[]>();
		Set<int[]> dilatedPoint = new HashSet<int[]>();
		Set<int[]> dilatedPixels = new HashSet<int[]>();
		Set<int[]>  intersection= new HashSet<int[]>();
		Set<int[]> pointChoosen = new HashSet<int[]>();
	               	
		try{	
		for(int i=0;i<width;i++)
                		{
                			for(int j=0;j<height;j++)
                    		{
                        			int q=0;
                            			int eleVal=binRaster.getSample(i, j, q);
				if(eleVal==1)
				{
					int[] point_in_a=new int[5];
					point_in_a[0]=i;
					point_in_a[1]=j;
					a.add(point_in_a);
				}
                                     	}
                   	}
		for(int[] n : a)
		{
			if(n[0]!=0 &&n[1]!=0)
			{
				new_Set.add(n);
				pointChoosen.add(n);
				break;
			}
		}
		
		while(!(new_Set.equals(old_Set)))
		{
			old_Set.addAll(new_Set);
			Set<int[]> difference = new HashSet<int[]>(old_Set);
			difference.removeAll(dilatedPoint);
			for(int[] point : difference)
			{
				x=point[0];
				y=point[1];
				dilatedPoint.add(point);
			
				for(int i=x-ele;i<=x+ele;i++)
				{
					for(int j=y-ele;j<=y+ele;j++)
					{       
						for(int n[]: a)
					       	{
					               		if(i==n[0] && j==n[1])
						     	          intersection.add(n);
					       	}  
                   				}
                    			}
			}			
			new_Set.addAll(intersection);			
		}
		
		int count=0;
		for(int[] z : old_Set)
		{
			count++;	
		}
		/*for(int[] e: pointChoosen)
		{
			System.out.println("Number of ConnectedComponents for pixel with coordinates " + "(" + e[0] + "," + e[1] + ")" + " is: "+count);
			mes.setText("Number of components for pixel " + "(" + e[0] + "," + e[1] + ")" + " is: "+count); 
		}*/  
		for(int[] e: old_Set)
		{
			conRaster.setSample(e[0],e[1],0,1);
		}
	  	
		}
		catch(Exception e)
		{
			System.out.println("exception is caught in coocomp");
			System.out.println("Exception:"+e);
		}
			
   	 }


	public static void main(String args[])
	{
		Connected frame=new Connected();
		frame.setVisible(true);
	}
}
