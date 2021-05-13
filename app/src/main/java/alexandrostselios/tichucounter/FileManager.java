package alexandrostselios.tichucounter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Environment.DIRECTORY_DOCUMENTS;
import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static android.support.v4.app.ActivityCompat.requestPermissions;

public class FileManager extends Activity {

    private Intent intent;
    private Context context;
    private File file;
    private FileOutputStream fileOutputStream;
    private OutputStreamWriter outputStreamWriter;
    private String currentDate;
    private String[] writeData = new String[200];

    public FileManager(Intent intent, Save context) throws IOException {
        this.intent=intent;
        this.context=context;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void saveDataToFile() throws IOException {
        openFile();
        currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        writeData = intent.getStringArrayExtra("score");
        writeDataToFile();
    }

    public void readDataFromFile(){

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void openFile() throws IOException {
        //https://stackoverflow.com/questions/21216943/how-to-access-getfilesdir-as-an-environment-variable
        //https://gist.github.com/lopspower/76421751b21594c69eb2
        file = new File( String.valueOf(Environment.getExternalStoragePublicDirectory(DIRECTORY_DOCUMENTS)),"/score.txt");
        file.createNewFile();
        Log.d(null,file.getAbsolutePath());
        fileOutputStream = new FileOutputStream(file);
        outputStreamWriter = new OutputStreamWriter(fileOutputStream);
    }


    private void writeDataToFile() throws IOException {
        outputStreamWriter.write(currentDate+"\n");
        for(int i=0;i<writeData.length;i=i+2){
            if(writeData[i]!=null){
                outputStreamWriter.write(i+1 +") "+writeData[i]+" "+writeData[i+1]+"\n");
                //Log.d(null,writeData[i]+" "+writeData[i+1]+"\n");
            }
        }
        outputStreamWriter.write(10);
        outputStreamWriter.close();
    }
}