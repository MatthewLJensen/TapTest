package matthew.taptest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class mainscreen extends AppCompatActivity {
    static String TAG = "mainscreen";
    int plusone = 0;
    public int highscore = 0;
    public int testint = 0;
    static public TapTestCounter CountDownTimer;
    String[] highscores;
    public boolean isrunning = false;
    private final long startTime = 10000;
    private final long interval = 1000;
    private long timeElapsed;
    public String nameofuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainscreen);
        final Button ClickBtn = (Button)(findViewById(R.id.clickbtn));
        final TextView timeremaining = (TextView) findViewById(R.id.timeremaining);
        final TextView counter = (TextView) findViewById(R.id.counter);
        final Button resetbtn = (Button) findViewById(R.id.resetbtn);
        final TextView score = (TextView) findViewById(R.id.score);
        final EditText name = (EditText) findViewById(R.id.name);
        final Button submitname = (Button) findViewById(R.id.submitname);
        CountDownTimer = new TapTestCounter(startTime, interval);
        resetbtn.setVisibility(View.INVISIBLE);
        score.setVisibility(View.INVISIBLE);
        timeremaining.setVisibility(View.INVISIBLE);
        ClickBtn.setVisibility(View.INVISIBLE);
        counter.setVisibility(View.INVISIBLE);



        submitname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView highscoretextview = (TextView) findViewById(R.id.highscoretextview);
                String nameofusernocaps = name.getText().toString();
                nameofuser = Character.toUpperCase(nameofusernocaps.charAt(0)) + nameofusernocaps.substring(1);
                plusone = 0;
                ClickBtn.setVisibility(View.VISIBLE);
                counter.setVisibility(View.VISIBLE);
                resetbtn.setVisibility(View.INVISIBLE);
                score.setVisibility(View.INVISIBLE);
                timeremaining.setVisibility(View.INVISIBLE);
                counter.setText("Start Tapping to See this Counter Rise");
                submitname.setVisibility(View.INVISIBLE);
                name.setVisibility(View.INVISIBLE);
                showhs();
                seths();
            }
        });

        ClickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Tap as fast as POSSIBLE!!");
                plusone += 1;
                counter.setText(plusone + "");
                ClickBtn.setText("Tap as fast as possible!");
                timeremaining.setVisibility(View.VISIBLE);
                if (isrunning == false) {
                    CountDownTimer.start();
                    isrunning = true;
                }
            }
        });

        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plusone = 0;
                ClickBtn.setVisibility(View.VISIBLE);
                counter.setVisibility(View.VISIBLE);
                resetbtn.setVisibility(View.INVISIBLE);
                score.setVisibility(View.INVISIBLE);
                timeremaining.setVisibility(View.INVISIBLE);
                counter.setText("Start Tapping to See this Counter Rise");

            }
        });
    }

    public  void seths(){
        SharedPreferences sharedPref = getSharedPreferences("myPrefsKey",
                Context.MODE_PRIVATE);
        highscore = sharedPref.getInt("highscore", testint);
    }

    public void showhs(){
        TextView highscoretextview = (TextView) findViewById(R.id.highscoretextview);
        SharedPreferences sharedPref = getSharedPreferences("myPrefsKey",
                Context.MODE_PRIVATE);
        Integer highscorefromprevious = sharedPref.getInt("highscore", testint);
        String namefromprevious = sharedPref.getString("nameofscorer", null);
        highscoretextview.setText("Highscore of " + highscorefromprevious + " by " + namefromprevious);

    }

public void savehighscore(){
TextView highscoretextview = (TextView) findViewById(R.id.highscoretextview);
    SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = prefs.edit();
    editor.putInt("highscore", highscore);
    editor.putString("nameofscorer", nameofuser);
    editor.commit();
    showhs();


}


public class TapTestCounter extends CountDownTimer
{

    final Button ClickBtn = (Button)(findViewById(R.id.clickbtn));
    final TextView timeremaining = (TextView) findViewById(R.id.timeremaining);
    final TextView counter = (TextView) findViewById(R.id.counter);
    final TextView score = (TextView) findViewById(R.id.score);
    final Button resetbtn = (Button) findViewById(R.id.resetbtn);

    public TapTestCounter(long startTime, long interval)
    {
        super(startTime, interval);
    }

    @Override
    public void onFinish()
    {
        if (plusone > highscore){
            highscore = plusone;
           savehighscore();
        }
        score.setVisibility(View.VISIBLE);
        timeremaining.setText("Time is up!");
        score.setText("Congratulations you got " + plusone + " taps!");
        isrunning = false;
        ClickBtn.setVisibility(View.INVISIBLE);
        counter.setVisibility(View.INVISIBLE);
        resetbtn.setVisibility(View.VISIBLE);

    }

    @Override
    public void onTick(long millisUntilFinished)
    {
        timeremaining.setText(millisUntilFinished/1000 + "");
    }

}



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mainscreen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
