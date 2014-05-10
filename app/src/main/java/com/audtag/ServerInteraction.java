package com.audtag;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import android.util.Log; 


public class ServerInteraction  {
    /** Called when the activity is first created. */
   /* public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    
        /*
        Bitmap bitmap = 
            DownloadImage(
            "http://www.streetcar.org/mim/cable/images/cable-01.jpg");
        ImageView img = (ImageView) findViewById(R.id.img);
        img.setImageBitmap(bitmap);
        */
        
        /*
        String txt = DownloadText("http://audtag.com/uploadtest.php");
        TextView tv = (TextView) findViewById(R.id.text);
        tv.setText(txt);
        */
        
        /*
        StatusLine txt = doHttpPost("http://audtag.com/uploadtest.php?");
        TextView tv = (TextView) findViewById(R.id.text);
        tv.setText("Response:"+txt);
        */
        
        
        //File audiofile = new File("/sdcard/testsound.mp3");
        //doHttpPostFile("http://www.audtag.com/uploadtest.php",Uri.fromFile(audiofile), this);
    
//    }

  static String boundary = "******Audtag******";
  static String lineend = "\r\n";
  static String twohyphens = "--";
    
    public static String doHttpPostFile(String urlString, File audiofile,HashMap<String,String> postValues, ContentResolver resolve)
    {
    	
		HttpURLConnection conn = null;    	
		DataOutputStream dos = null;
		DataInputStream dis = null;
		String filePath = audiofile.getName();
		String serverResponseMessage = "";
		
    	try 
    	{
    		FileInputStream fis = new FileInputStream(audiofile);
//    		InputStream fis = context.getResources().openRawResource(R.raw.testsound);
//			FileInputStream fis = new FileInputStream(filename);
			URL address = new URL(urlString);
			conn = (HttpURLConnection) address.openConnection();
			
			
			conn.setDoInput(true); 
	        conn.setDoOutput(true);
	        conn.setUseCaches(false); 
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Content-type", "multipart/form-data;boundary="+boundary);
			
			dos = new DataOutputStream(conn.getOutputStream());
			
			//write all the post parameters out into the header
			Iterator<Entry<String,String>> i = postValues.entrySet().iterator();
			while(i.hasNext())
			{
			  Entry<String,String> value = i.next();
			  dos.writeBytes(formValueHeader(value.getKey(),value.getValue()));
			}
			Log.d("filewriter","wrote regular fields.  Now the file:");

			String str = twohyphens+boundary+lineend;
			str += "Content-Disposition: form-data; name=\"audtagfile\";filename=\"" + filePath +"\"" + lineend;
			str += "Content-Type: application/x-audtag_file"+lineend;
			str += "Content-Transfer-Encoding: binary"+lineend+lineend;
			
			dos.writeBytes(str);
						
			double bytesavailable = fis.available();
			Log.d("filewriter","available:"+bytesavailable+"");
			double maxBuffer = 1024;
			int bufferSize = (int) Math.min(bytesavailable, maxBuffer);
			Log.d("filewriter","available:"+bufferSize+"");
			byte[] buffer = new byte[bufferSize];
			
			int bytesRead = fis.read(buffer, 0, bufferSize);
			Log.d("filewriter","bytesRead:"+bytesRead+"");
			while (bytesRead > 0)
			{
				dos.write(buffer, 0, bufferSize); //writes bytes out to the connection.
				bytesavailable = fis.available();
				bufferSize = (int) Math.min(bytesavailable, maxBuffer);
				buffer = new byte[bufferSize];
				bytesRead = fis.read(buffer, 0, bufferSize);  // reads the file into the buffer and returns the number of bytes it read.
				Log.d("filewriter","BytesRead:"+bytesRead+"");
			}
			
			dos.writeBytes(lineend);
	        dos.writeBytes(twohyphens + boundary + twohyphens + lineend);

	        dos.flush();
	        dos.close();
	        fis.close();
	        
			
			
			
    	} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
	        dis = new DataInputStream ( conn.getInputStream() ); 
	        String str; 
	        
	        while (( str = dis.readLine()) != null) 
	        { 
	             Log.d("filewriter","Server Response"+str); 
	        } 
	        dis.close();
		} 
		  catch (IOException ioex){ 
		       Log.e("filewriter", "error: " + ioex.getMessage(), ioex); 
		} 
		return serverResponseMessage;
    }
    
    private static String formValueHeader(String name, String value)
    {
    	String header = twohyphens+boundary+lineend;
    	header += "content-disposition: form-data; name=\""+name+"\""+lineend+lineend;
    	header += value+lineend;
    	return header;
    }
   /*
    private static StatusLine doHttpPost(String urlString) 
    {
    	HttpResponse response = null;
    	
    	DefaultHttpClient httpclient = new DefaultHttpClient();
    	HttpPost http_post = new HttpPost(urlString);
    	Log.d("httpPost", "try to do the post?");
    	List <NameValuePair> nvps = new ArrayList <NameValuePair>();
    	nvps.add(new BasicNameValuePair("userid","2"));
    	nvps.add(new BasicNameValuePair("pos_lat","2.1"));
    	nvps.add(new BasicNameValuePair("pos_long","2.2"));
    	nvps.add(new BasicNameValuePair("text_desc","2.3"));
    	nvps.add(new BasicNameValuePair("privacy_setting","private"));
    	nvps.add(new BasicNameValuePair("webavailability","true"));
    	try {
			http_post.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			response = httpclient.execute(http_post);
		//	HttpEntity entity = response.getEntity();
		
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response.getStatusLine();
    	
    	
    }
    */
    
    private static InputStream OpenHttpConnection(String urlString) throws IOException
    {
        InputStream in = null;
        int response = -1;
               
        URL url = new URL(urlString); 
        URLConnection conn = url.openConnection();
                 
        if (!(conn instanceof HttpURLConnection))                     
            throw new IOException("Not an HTTP connection");
        
        try{
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("POST");
            httpConn.connect(); 

            response = httpConn.getResponseCode();                 
            if (response == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();                                 
            }                     
        }
        catch (Exception ex)
        {
            throw new IOException("Error connecting");            
        }
        return in;     
    }
    
   
    public static Bitmap DownloadImage(String URL)
    {        
	    Bitmap bitmap = null;
	    InputStream in = null;        
	    try {
	    	in = OpenHttpConnection(URL);
	    	bitmap = BitmapFactory.decodeStream(in);
	    	in.close();
	    	} catch (IOException e1) {
	        // TODO Auto-generated catch block
	    		e1.printStackTrace();
	    	}
	    return bitmap;                
    }

    
    
    public static String DownloadText(String URL)
    {
    	InputStream in = null;
    	try {
    		in = OpenHttpConnection(URL);
    	}
    	catch(IOException e2){
    		e2.printStackTrace();
    		return "";
    	}
    	InputStreamReader isr = new InputStreamReader(in);
    	int charRead; //number of characters read
    	String ret_str = "";
    	int BUFFER_SIZE = 2000;
    	char[] inputBuffer = new char[BUFFER_SIZE];
    	try
    	{
    		while((charRead = isr.read(inputBuffer))>0){
    			String readString = String.copyValueOf(inputBuffer, 0, charRead);
    			ret_str += readString;
    			inputBuffer = new char[BUFFER_SIZE];
    		}
    	}
    	catch(IOException e3)
    	{
    		e3.printStackTrace();
    	}
    	return ret_str;
    	
    }   
}
   