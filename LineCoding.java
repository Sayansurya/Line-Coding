package say;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.util.*;

/*
 * @Sayan Surya Shaw 
 */
public class LineCoding extends JFrame implements ActionListener {
	public int x1=300, x2=300, y1=100, y2=100;
	int [] baud;
	int [] bit;
	JTextField inpData = new JTextField(12);
	JTextField rcvData = new JTextField(12);
	JComboBox<String> techniques = null;
	JPanel drawPanel = new JPanel();
	public LineCoding() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1000,400);
		setTitle("Line Coding Techniques....");
		initComponents();
	}

	private void initComponents() {
		JPanel topPanel = new JPanel();
		topPanel.add(new JLabel("Input Data:"));
		topPanel.add(inpData);
		String list[] = {"NRZ-I","NRZ-L","RZ","Manchester","Differential Manchester","AMI","Pseudoternary"};
		techniques = new JComboBox<String>(list);
		topPanel.add(new JLabel("Technique:"));
		topPanel.add(techniques);
		topPanel.add(new JLabel("Received Data:"));
		topPanel.add(rcvData);
		JButton encodeBtn = new JButton("Encode");
		JButton decodeBtn = new JButton("Decode");
		JButton clrBtn = new JButton("Clear");
		topPanel.add(encodeBtn);
		encodeBtn.addActionListener(this);
		topPanel.add(decodeBtn);
		decodeBtn.addActionListener(this);
		topPanel.add(clrBtn);
		clrBtn.addActionListener(this);
		drawPanel.setBackground(Color.WHITE);
		add(drawPanel);
		add(topPanel,BorderLayout.NORTH);
		
	}

	public static void main(String[] args) {
		LineCoding f = new LineCoding();
		f.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Encode")){
			encode();
		}else if(e.getActionCommand().equals("Decode")){
			decode();
		}else if(e.getActionCommand().equals("Clear")){
			repaint();
		}
		
	}


	private void encode() {		
		
		drawPanel.getGraphics().drawString("Implement "+techniques.getSelectedItem()+" Encoding Technique!", 400, 40);
		char data[]=inpData.getText().toCharArray();
		
		// NRZ-L
		if(techniques.getSelectedItem()=="NRZ-L") {
		baud = new int[data.length]; 
		 for(int i=0;i<data.length;i++){
	            if(data[i]=='1'){
	                baud[i]=50;
	            }
	            else{
	                baud[i]=-50;
	            }
	        }
		 
		 rcvData.setText(Arrays.toString(baud));
		draw(baud);
		}
		//NRZ-I
		if(techniques.getSelectedItem()=="NRZ-I") {
			baud = new int[data.length]; 
			//baud[0]= 50;
			y1=50;
			if(data[0]=='1')
				baud[0]= -50;
			else
				baud[0]=50;
			 for(int i=1;i<data.length;i++){
		            if(data[i]=='1'){
		                baud[i]=-baud[i-1];
		            }
		            else{
		                baud[i]=baud[i-1];
		            }
		        }
			 
			 rcvData.setText(Arrays.toString(baud));
			draw(baud);
			}
		//RZ
		if(techniques.getSelectedItem()=="RZ") {
			baud = new int[data.length*2]; 
			int j=0;
			 for(int i=0;i<data.length;i++){
		            if(data[i]=='1'){
		                baud[j]=50;
		                baud[j+1]=0;
		                j=j+2;
		            }
		            else{
		                baud[j]=-50;
		                baud[j+1]=0;
		                j=j+2;
		            }
		        }
			 
			 rcvData.setText(Arrays.toString(baud));
			draw(baud);
			}
		
		//Manchester
		if(techniques.getSelectedItem()=="Manchester") {
			baud = new int[data.length*2];
			int j=0;
			 for(int i=0;i<data.length;i++){
		            if(data[i]=='1'){
		                baud[j]=50;
		                baud[j+1]=-50;
		            }
		            else{
		                baud[j]=-50;
		                baud[j+1]=+50;
		            }
		            j=j+2;
		        }
			 
			 rcvData.setText(Arrays.toString(baud));
			draw(baud);
			}
		//Differential Manchester
		if (techniques.getSelectedItem() == "Differential Manchester") {
            int j = 2;
            y1=50;
            baud = new int[data.length*2];
            if (data[0] == '1') {
                baud[0] = 50;
            } else if (data[0] == '0') {
                baud[0] = -50;
            }
            baud[1] = baud[0] * (-1);
            for (int i = 1; i < data.length; i++) {
                if (data[i] == '1') {
                    baud[j] = baud[j - 1];
                } else {
                    baud[j] = baud[j - 1]*(-1);
                }
                baud[++j] = baud[j - 1]*(-1);
                j++;
            }
            rcvData.setText(Arrays.toString(baud));
			draw(baud);
        }
		
		if(techniques.getSelectedItem()=="AMI") {
			baud = new int[data.length]; 
			//baud[0]= 50;
			int count=0;
			 for(int i=0;i<data.length;i++){
				 
					 
		            if(data[i]=='1'){
		            	count++;
		            	if(count%2==0)
		                baud[i]= -50;
		            	else
		            		baud[i]= 50;
		            }
		            if(data[i]=='0')
		                baud[i]=0;
		            }
		        
			 
			 rcvData.setText(Arrays.toString(baud));
			draw(baud);
			}
		
		if(techniques.getSelectedItem()=="Pseudoternary") {
			baud = new int[data.length]; 
			//baud[0]= 50;
			int count=0;
			 for(int i=0;i<data.length;i++){
				 
					 
		            if(data[i]=='0'){
		            	count++;
		            	if(count%2==0)
		                baud[i]= -50;
		            	else
		            		baud[i]= 50;
		            }
		            if(data[i]=='1')
		                baud[i]=0;
		            }
		        
			 
			 rcvData.setText(Arrays.toString(baud));
			draw(baud);
			}
		
		
		
		
	}
	

	private void decode() {
		drawPanel.getGraphics().drawString("Implement "+techniques.getSelectedItem()+" Decoding Technique!", 400, 200);
		// NRZ-L
		if(techniques.getSelectedItem()=="NRZ-L") {
			 x1=300;y1=100;x2=300;y2=100;
			 int j=0;
		        bit=new int[baud.length];
		        for(int i=0;i<baud.length;i++){
		        	if(baud[i]==50){
		                bit[j]=1;
		                j++;
		            }
		            else {
		                bit[j]=0;
		                j++;
		            }
		        }
		        rcvData.setText(Arrays.toString(bit));
		}
		//NRZ-I
		if(techniques.getSelectedItem()=="NRZ-I") {
			 x1=300;y1=100;x2=300;y2=100;
			 int j=1;
		        bit=new int[baud.length];
		        if(baud[0]==50)
		        	bit[0]=0;
		        else
		        	bit[0]=1;
		        for(int i=1;i<baud.length;i++){
		        	if(baud[i-1]!=baud[i]){
		                bit[i]=1;
		               
		            }
		            else {
		                bit[i]=0;
		                
		            }
		        }
		        rcvData.setText(Arrays.toString(bit));
		}
		//RZ
		if(techniques.getSelectedItem()=="RZ") {
			 x1=300;y1=100;x2=300;y2=100;
			 int j=0;
		        bit=new int[baud.length/2];
		        for(int i=0;i<baud.length;i=i+2){
		        	if(baud[i]==50){
		                bit[j]=1;
		                j++;
		            }
		            else {
		                bit[j]=0;
		                j++;
		            }
		        }
		        rcvData.setText(Arrays.toString(bit));
		}
		//Manchester
		if(techniques.getSelectedItem()=="Manchester") {
			 x1=300;y1=50;x2=300;y2=50;
			 int j=0;
		        bit=new int[baud.length/2];
		        for(int i=0;i<baud.length;i=i+2){
		             if(baud[i]==-50)
		            	 bit[j]=0;
		             else
		            	 bit[j]=1;
		             j++;
		        }
		        rcvData.setText(Arrays.toString(bit));
		}
		
		
		//Differential Manchester
		if(techniques.getSelectedItem()=="Differential Manchester") {
			 x1=300;y1=50;x2=300;y2=50;
			 int j=0;
		        bit=new int[baud.length/2];
		        for(int i=0;i<baud.length;i=i+2){
		        	if(i==0){
		                if(baud[i]==50) 
		                	bit[j]=1;
		                else bit[j]=0;
		                j++;
		        	}
		            else {
		            	if(baud[i]==baud[i-1]) 
		            		bit[j]=1;
		            	else bit[j]=0;
		                j++; 
		            }
		        }
		        rcvData.setText(Arrays.toString(bit));
		}
		
		if(techniques.getSelectedItem()=="AMI") {
			 x1=300;y1=100;x2=300;y2=100;
			 int j=0;
		        bit=new int[baud.length];
		        for(int i=0;i<baud.length;i++){
		        	if(baud[i]==50 || baud[i]==-50){
		                bit[j]=1;
		                j++;
		            }
		            else {
		                bit[j]=0;
		                j++;
		            }
		        }
		        rcvData.setText(Arrays.toString(bit));
		}
		
		
		if(techniques.getSelectedItem()=="Pseudoternary") {
			 x1=300;y1=100;x2=300;y2=100;
			 int j=0;
		        bit=new int[baud.length];
		        for(int i=0;i<baud.length;i++){
		        	if(baud[i]==50 || baud[i]==-50){
		                bit[j]=0;
		                j++;
		            }
		            else {
		                bit[j]=1;
		                j++;
		            }
		        }
		        rcvData.setText(Arrays.toString(bit));
		}
		
		
	}
	
	private void draw(int []baud) {
		
		for(int i=0;i<baud.length;i++){
			// x1=300;
			 //y1=100;
            if(baud[i]==100-y1){
                y2=y1;
                x2=x1+30;
                drawPanel.getGraphics().drawLine(x1,y1,x2,y2);
            }
            else{
                x2=x1;
                y2=100-baud[i];
                drawPanel.getGraphics().drawLine(x1,y1,x2,y2);
                y1=y2;
                x2=x1+30;
                drawPanel.getGraphics().drawLine(x1,y1,x2,y2);
            }
            x1=x2;
            y1=y2;
        }
	}
	/*public void drawDashedLine(Graphics g, int x1, int y1, int x2, int y2){

        //creates a copy of the Graphics instance
        Graphics2D g2d = (Graphics2D) g.create();

        //set the stroke of the copy, not the original 
        Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
        g2d.setStroke(dashed);
        g2d.drawLine(x1, y1, x2, y2);

        //gets rid of the copy
        g2d.dispose();
}*/

}