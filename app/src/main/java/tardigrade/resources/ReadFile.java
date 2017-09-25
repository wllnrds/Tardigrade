package tardigrade.resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import tardigrade.resources.impl.Deck;
import tardigrade.resources.impl.NullCallback;
import tardigrade.resources.impl.Pack;
import tardigrade.utils.Flag;
import tardigrade.utils.ICallback;

public class ReadFile{
	private String fileName = null;
	
	private String[] header = null;
	
	private ICallback onStart;
	private ICallback onReading;
	private ICallback onFinish;
	private ICallback onFail;

	public ReadFile(String fileName){
		this.fileName = fileName;
		this.onStart = new NullCallback();
		this.onReading = new NullCallback();
		this.onFinish = new NullCallback();
		this.onFail = new NullCallback();
		
		this.header = null;
	}

	public void readAsCSV(){
		new Thread(){
			@SuppressWarnings("resource")
			@Override
			public void run() {
				List<String []> data = new ArrayList<String []>();
				onStart.doit(Pack.create(Flag.NOTIFY, null));
				try{
					InputStream file = Deck.mContext.getAssets().open(fileName);
					//InputStreamReader stream = new InputStreamReader(new FileInputStream(fileName), "UTF8");
					InputStreamReader stream = new InputStreamReader(file, "UTF8");
					BufferedReader buffer = new BufferedReader(stream);
				    String line = buffer.readLine();
				    while (line != null) {
				    	String[] row = line.split(";");
					    data.add(row);
						line = buffer.readLine();						
						if(header == null){
							header = row;
						}else{
							onReading.doit(Pack.create(Flag.NOTIFY, row));
						}
				    }
				} catch (IOException e) {
					e.printStackTrace();
					onFail.doit(Pack.create(Flag.NOTIFY, e));
				}
				onFinish.doit(Pack.create(Flag.NOTIFY, data));
			}
		}.run();
	}

	public String[] getHeader(){
		return header;
	}
	public void setOnStartListener(ICallback callback){
		this.onStart = callback;
	}
	public void setOnReadingListener(ICallback callback){
		this.onReading = callback;
	}
	public void setOnFinishListener(ICallback callback){
		this.onFinish = callback;
	}
	public void setOnFailListener(ICallback callback){
		this.onFail = callback;
	}
}