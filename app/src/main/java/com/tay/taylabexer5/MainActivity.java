package com.tay.taylabexer5;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.tay.taylabexer5.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    String[] comName, comCoun, comIndus, comCEO;
    ListView lists;
    int[] comLogo = {R.drawable.icbc, R.drawable.jpm, R.drawable.ccb, R.drawable.aboc, R.drawable.boa, R.drawable.apple, R.drawable.ping, R.drawable.boc ,R.drawable.shell, R.drawable.wells, R.drawable.exxon, R.drawable.att, R.drawable.samsung, R.drawable.citi};
    ArrayList<Company> comList = new ArrayList<>();
    String[] comInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        comName = getResources().getStringArray(R.array.comName);
        comCoun = getResources().getStringArray(R.array.comCoun);
        comIndus = getResources().getStringArray(R.array.comIndus);
        comCEO = getResources().getStringArray(R.array.comCEO);
        comInfo = getResources().getStringArray(R.array.comInfo);

        for(int i=0; i < comName.length; i++){
            comList.add(new Company(comLogo[i], comName[i], "Country: " + comCoun[i], "Industry: " + comIndus[i], "CEO: " + comCEO[i]));
        }

        ArrayAdapter androidArrayAdapter = new Adaptor(this, R.layout.item, comList);

        lists = findViewById(R.id.listView);
        lists.setAdapter(androidArrayAdapter);
        lists.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        final File folder = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(folder, "company.txt");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            String choice = comName[position] + "\n" + comCoun[position] + "\n" + comIndus[position] + "\n" + comCEO[position];
            fos.write(choice.getBytes());
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle(comName[position]);
            dialog.setIcon(comLogo[position]);
            dialog.setMessage(comInfo[position]);
            dialog.setNeutralButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    try {
                        FileInputStream fin;
                        fin = new FileInputStream(new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + "/company.txt"));
                        int i;
                        String str = "";
                        while ((i = fin.read()) != -1) {
                            str += Character.toString((char) i);
                        }
                        fin.close();
                        Toast.makeText(MainActivity.this, str, Toast.LENGTH_LONG).show();
                    } catch (FileNotFoundException e) {
                        //e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            dialog.create().show();
        } catch (FileNotFoundException e) {
            //Toast.makeText(this, "File not found...", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch (IOException e) {
            //Toast.makeText(this, "IO error...", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        //
        //final AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
        //myDialog.setTitle(comList.get(position).getComName());
        //myDialog.setIcon(comList.get(position).getComLogo());
        //myDialog.setMessage(comInfo[position]);
        //myDialog.setNeutralButton("Close", new DialogInterface.OnClickListener() {
        //    @Override
        //    public void onClick(DialogInterface dialog, int which) {
        //        dialog.dismiss();
        //        Toast.makeText(MainActivity.this, comName[position], Toast.LENGTH_LONG).show();
        //    }
        //});
        //myDialog.create().show();
    }
}
