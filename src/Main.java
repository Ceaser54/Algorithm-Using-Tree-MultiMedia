import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Main {
	
	public static final int size=128;
public static class Node implements Comparable<Node>{
		
		public final char Character;
		public final int freq;
		public final Node left , Right;
		
		Node(char ch, int f, Node l, Node r) {
               Character= ch;
              freq=f;
              left=l;
             Right=r; }
		public boolean Leaf_Check(){
			if( (left==null) && (Right==null)) 
				return true;
			
			return false;		}

		@Override
		public int compareTo(Node o) {
			// TODO Auto-generated method stub
			return this.freq- o.freq;}
		
	}
	
	
public static Node BuildTree(int [] frequancies){
		
		ArrayList<Node>Al = new ArrayList<Node>();
		
		for(char i=0;i<size;i++)
			if(frequancies[i]>0)
				Al.add(new Node(i,frequancies[i],null,null));
		
//			if(Al.size()==1){
//				if(frequancies['\0'] == 0)
//					Al.add(new Node('\0', 0 ,null , null));
//				else 
//					Al.add(new Node('\1',0,null,null));
//					
//			}
//			
			while(Al.size() > 1){
				
				//Collections.sort(Al);
				//Collections.reverse(Al);
			
				//Node left =Al.remove(0);
				//Node Right=	Al.remove(1);
				
				Node left= Al.get(Al.indexOf(Collections.min(Al)));
				Al.remove(Al.indexOf(Collections.min(Al)));
				
				//System.out.println("left-->"+left.Character);
				
				Node Right= Al.get(Al.indexOf(Collections.min(Al)));
				Al.remove(Al.indexOf(Collections.min(Al)));
				
				//System.out.println("Right-->"+Right.Character);
				
				Node node = new Node('\0',( left.freq + Right.freq ), left , Right );
				
				//System.out.println("Parent--->"+node.freq);
				
				Al.add(node);
			}
			
		
		return Al.remove(Al.indexOf(Collections.min(Al)));		
	}
	
	
public static void buildCode(String[] s, Node x, String a ,HashMap<Character,String>m) throws IOException {
			
//		 FileOutputStream f1 = new FileOutputStream("Compress.txt");
//			DataOutputStream bw = new DataOutputStream(f1);

	        if (!x.Leaf_Check()) {
	            buildCode(s, x.left,  a + '0' ,m);
	            buildCode(s, x.Right, a + '1' ,m);
	        }
	        else {
	            s[x.Character] = a;
	            //System.out.print(x.Character);
	            //System.out.println(a);   
	           
	            if(!m.containsKey(x.Character))
	            	m.put(x.Character, a);
	          
	        }
	    }
	 
	 

public static void compress(String in) throws IOException{
	 		
	 		FileOutputStream f1 = new FileOutputStream("compress.txt");
			DataOutputStream bw = new DataOutputStream(f1);
	 		
	 		char[] input = in.toCharArray();
	 		int [] freq =new int[size];
	 		HashMap<Character,String>dictionary = new HashMap<Character,String>();

	 		
	 	for(int i=0;i<input.length;i++)
	 			freq[input[i]]++;
	 		
	 		Node Root = BuildTree(freq);
	 		
	 		String[] string = new String[size];
	 		 buildCode(string, Root, "" ,dictionary);
	 		
	 		 
	 	for (HashMap.Entry entry : dictionary.entrySet()) {
	 		//    System.out.println(entry.getKey() + " " + entry.getValue());
	 		    
	 		    String f="";
	 		    f+= entry.getKey();
	 		    f+=" ";
	 		    f+=entry.getValue();
	 		    bw.writeBytes(f);
	 		    bw.writeBytes("\n");
	 		}
		 bw.writeBytes("$");
	 		 for(int i=0;i<input.length;i++){
	 			 String code = string[input[i]];
	 			System.out.print(code);
	 			 bw.writeBytes(code);
	 	}
	 		 System.out.println();
	 		 
	 }
	 	
public static void Decompress() throws IOException{
	 		
	 		FileInputStream file1 = new FileInputStream("compress.txt");
			DataInputStream data1 = new DataInputStream(file1);
			
			FileOutputStream f1 = new FileOutputStream("Decompress.txt");
			DataOutputStream bw = new DataOutputStream(f1);
	 		
			
	HashMap<String,String>dictionary = new HashMap<String,String>();

			
			//String s=data1.readLine();
			//System.out.println(s);
	 		String code="";
			while(file1.available()!=0){
				
				String a=data1.readLine();
				//System.out.println(a);
				
			if(a.charAt(0)=='$'){
					//System.out.println();
					 code=a.substring(1,a.length());
				//	System.out.println(code);
			}
			else{
				String char1=a.substring(0,1);
				String c = a.substring(2,a.length());
				
				//System.out.println(char1+" "+c);
				dictionary.put(c, char1);
				
				}
			}
		
//for (HashMap.Entry entry : dictionary.entrySet()) 
	 		 //System.out.println(entry.getKey() + " " + entry.getValue());
	 	String temp="";    
		for(int i=0;i<code.length();i++){
			temp+=code.charAt(i);
			if(dictionary.containsKey(temp))
			{  bw.writeBytes(dictionary.get(temp));
			//System.out.println(temp);	
			temp="";	
			}
			
		}

}
//----------------------------GUI----------------------------------------//

private static JFrame mainFrame;
private static JLabel headerLabel;
private static JLabel statusLabel;
private static JPanel controlPanel;

private static void prepareGUI() {
	mainFrame = new JFrame("Java Swing Examples");
	mainFrame.setSize(400, 400);
	mainFrame.setLayout(new GridLayout(3, 1));
	mainFrame.addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent windowEvent) {
			System.exit(0);
		}
	});
	
	headerLabel = new JLabel("", JLabel.CENTER);
	statusLabel = new JLabel("", JLabel.CENTER);

	statusLabel.setSize(350, 100);

	controlPanel = new JPanel();
	controlPanel.setLayout(new FlowLayout());

	mainFrame.add(headerLabel);
	mainFrame.add(controlPanel);
	mainFrame.add(statusLabel);
	mainFrame.setVisible(true);
}

private static void showTextFieldDemo() {
	// headerLabel.setText("Control in action: JTextField");

	JLabel input = new JLabel("Input: ", JLabel.RIGHT);
	final JTextField userText = new JTextField(20);

	JButton Compress = new JButton("Compress");
	Compress.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			try {
				compress(userText.getText());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	});
	JButton Decompress = new JButton("Decompress");
	Decompress.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			try {
				Decompress();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	});

	controlPanel.add(userText);
	controlPanel.add(Compress);
	controlPanel.add(Decompress);
	mainFrame.setVisible(true);
}


	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		//compress("AAABBBCCBACABBBCXXXXXX");
		//Decompress();
		
		prepareGUI();
		showTextFieldDemo();
		
		
		

		
	}

}
