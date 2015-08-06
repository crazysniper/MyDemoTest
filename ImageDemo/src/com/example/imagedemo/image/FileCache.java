package com.example.imagedemo.image;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

import android.content.Context;

public class FileCache {
    
    private File cacheDir;
    
    public FileCache(Context context){
        //Find the dir to save cached images
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)){
        	cacheDir=new File(android.os.Environment.getExternalStorageDirectory(),"/babytime/cache");
        } else{
        	cacheDir=context.getCacheDir();
        }
        if(!cacheDir.exists()){
        	cacheDir.mkdirs();
        }
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        new Thread(new Runnable() {
			@Override
			public void run() {
				removeCache(cacheDir.toString());
			}
		}).start();
        
    }
    
    public File getFile(String url){
        //I identify images by hashcode. Not a perfect solution, good for the demo.
        String filename=String.valueOf(url.hashCode());
        //Another possible solution (thanks to grantland)
        //String filename = URLEncoder.encode(url);
        File f = new File(cacheDir, filename);
        return f;
        
    }
    
    /*public void clear(){
        File[] files=cacheDir.listFiles();
        if(files==null)
            return;
        for(File f:files)
            f.delete();
    }*/
    
    private boolean removeCache(String dirPath) {
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
         
        if (files == null) {
            return true;
        }
         
        if (!android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)){
            return false;
        }
                                                             
        int dirSize = 0;
        for (int i = 0; i < files.length; i++){
            dirSize += files[i].length();
        }
        
        //Log.i("cw_test", "图片大小1："+(int)((dirSize/1024/1024)*10)/10);
        
        if (dirSize > 20*1024*1024){
            int removeFactor = (int) (0.6 * files.length);
            Arrays.sort(files, new FileLastModifSort());
            for (int i = 0; i < removeFactor; i++){
                files[i].delete();
            }
        }
        //Log.i("cw_test", "图片大小2："+(int)((dirSize/1024/1024)*10)/10);                                                             
        return true;
    }

    private class FileLastModifSort implements Comparator<File> {
        public int compare(File file0, File file1) {
            if (file0.lastModified() > file1.lastModified()){
                return 1;
            }else if (file0.lastModified() == file1.lastModified()) {
                return 0;
            }else{
                return -1;
            }
        }
    }
    
}