package com.example.cbleecher;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import org.json.JSONObject;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.view.LayoutInflater;
import android.widget.ListView;



public class MainActivity extends AppCompatActivity {
    private EditText EditTxt;
    private String userInput;
    private String PackageName;
    private String link1;
    private String link2 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditTxt = findViewById(R.id.EditTxt);
        Button SendBtn = findViewById(R.id.SendBtn);


        EditTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                userInput = s.toString();
                PackageName = PackageNameFinder.main(userInput);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        SendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread networkThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {


                            RequestSender.main(PackageName);

                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                });


                networkThread.start();

                try {

                    Toast.makeText(getApplicationContext(), "درحال دریافت لینک ها...", Toast.LENGTH_SHORT).show();

                    networkThread.join();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String ToCP = RequestSender.getReturn();
                boolean b = ToCP == null;
                if (!b) {
                    LinkExtractor.extractLinks(ToCP);
                   link1 = LinkExtractor.link1;
                   link2 = LinkExtractor.link2;
                    Toast.makeText(getApplicationContext(), "لینک ها دریافت شدند.", Toast.LENGTH_SHORT).show();



                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("یکی از لینک ها را انتخاب کنید.");
                    LayoutInflater inflater = getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.dialog_listview, null);
                    builder.setView(dialogView);

                    ListView listView = dialogView.findViewById(R.id.listView);

                    String[] items = {"لینک سرور 1", "لینک سرور 2"};
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, items);
                    listView.setAdapter(adapter);

                    builder.setPositiveButton("بستن", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    final AlertDialog dialog = builder.create();
                    dialog.show();

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String selectedItem = items[position];
                            if (selectedItem.equals("لینک سرور 1")) {
                                if (link1 != "") {
                                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                    ClipData clip = ClipData.newPlainText("Copied Text", link1);
                                    clipboard.setPrimaryClip(clip);
                                    Toast.makeText(getApplicationContext(), "لینک سرور 1 کپی شد.", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }  else {
                                    Toast.makeText(getApplicationContext(), "خطا در کپی لینک 1!", Toast.LENGTH_SHORT).show();
                                }



                            }
                            if (selectedItem.equals("لینک سرور 2")) {
                                if (link2 != "") {
                                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                    ClipData clip = ClipData.newPlainText("Copied Text", link2);
                                    clipboard.setPrimaryClip(clip);
                                    Toast.makeText(getApplicationContext(), "لینک سرور 2 کپی شد.", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }  else {
                                    Toast.makeText(getApplicationContext(), "خطا در کپی لینک 2!", Toast.LENGTH_SHORT).show();
                                }
                                }
                            }


                    });
                }  else {

                    Toast.makeText(getApplicationContext(), "خطایی رخ داد!", Toast.LENGTH_SHORT).show();


                }
            }
        });
    }
}