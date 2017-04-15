package com.example.toast.galaxy;

//     IMPORTS
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {

    //   COMPONENTS
    public SeekBar rounds;
    public SeekBar extras;

    public ImageButton settings;
    public Button reRandom;
    public Button restart;


    public TextView Close2;
    public TextView boardtext;
    public TextView extratext;

//   VARIABLES
    public static boolean TBEV= false;
    public static boolean ABEV= false;
    public static boolean LMV= false;
    public static boolean MV= false;
    public static boolean SCV= false;
    public static boolean MTORRV= false;
    public static boolean BSV = false;
    public static boolean Smart = false;

    public static int roundsS = 0;
    public static int extrasS= 0;

    public static int numOfRounds = 0;
    public static int extraCount = 0;
    public static int numOfExtras = 0;

    //   OTHER


//--------{ onCreate}-------------------------------------------------------------------------------------------------------------------
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main();
    }
    public void main(){
//---------{Components}-----------------------------------------------------------------------------------------------------------------
        // Declarations

        setContentView(R.layout.activity_main);
        rounds = (SeekBar) findViewById(R.id.rounds);
        extras = (SeekBar) findViewById(R.id.extras);

        settings = (ImageButton) findViewById(R.id.Settings);
        Button randize = (Button) findViewById(R.id.Randomize);
        Typeface getFace = Typeface.createFromAsset(getAssets(),"fonts2/Orbitron-Regular.ttf");
        randize.setTypeface(getFace);
        TextView label = (TextView) findViewById(R.id.label);
        label.setTypeface(getFace);
        TextView text1 = (TextView) findViewById(R.id.roundsText);
        TextView text2 = (TextView) findViewById(R.id.extrastext);
        text1.setTypeface(getFace);
        text2.setTypeface(getFace);

        //Listeners
        listenForSlider lfs = new listenForSlider();
        randomize rock = new randomize();
        randize.setOnClickListener(rock);
        SettingsListener lfs2 = new SettingsListener();
        rounds.setOnSeekBarChangeListener(lfs);
        extras.setOnSeekBarChangeListener(lfs);
        settings.setOnClickListener(lfs2);

//----------{Running During Main}----------------------------------------------------------------------------------------------------------
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("Trucker", android.content.Context.MODE_PRIVATE);
        BSV = preferences.getBoolean("BSV", false);
        TBEV = preferences.getBoolean("TBEV", false);
        ABEV = preferences.getBoolean("ABEV", false);
        LMV = preferences.getBoolean("LMV", false);
        MV = preferences.getBoolean("MV", false);
        SCV = preferences.getBoolean("SCV", false);
        MTORRV= preferences.getBoolean("MTORRV", false);
        Smart = preferences.getBoolean("Smart", false);
        roundsS = preferences.getInt("rounds", 0);
        extrasS = preferences.getInt("extras", 0);


        numOfExtras = 0;
        numOfRounds = 0;
        if(MV){numOfExtras+=1;}
        if(SCV){numOfExtras+=1;}
        if(MTORRV&&TBEV){numOfExtras+=9;}
        if(LMV){numOfRounds+=4;}
        if(ABEV){numOfExtras+=1;numOfRounds+=3;}
        if(BSV){numOfRounds+=4;}
        if(TBEV){numOfRounds+=2;numOfExtras+=3;}

        rounds.setMax(numOfRounds);
        extras.setMax(numOfExtras);
        rounds.setProgress(roundsS);
        extras.setProgress(extrasS);
    }
    private boolean getVar(String Name){
        if(Name.equalsIgnoreCase("BSV")){return BSV;}
        else if(Name.equalsIgnoreCase("TBEV")){return TBEV;}
        else if(Name.equalsIgnoreCase("ABEV")){return ABEV;}
        else if(Name.equalsIgnoreCase("LMV")){return LMV;}
        else if(Name.equalsIgnoreCase("MV")){return MV;}
        return false;
    }
    private class SettingsListener implements View.OnClickListener{
        public void onClick(View v)
        {
            setContentView(R.layout.settings);

            CheckBox baseSet = (CheckBox) findViewById(R.id.BS);
            CheckBox bigE = (CheckBox) findViewById(R.id.TBE);
            CheckBox aBigE = (CheckBox) findViewById(R.id.ABE);
            CheckBox latestModel = (CheckBox) findViewById(R.id.LM);
            CheckBox missions = (CheckBox) findViewById(R.id.Missions);
            CheckBox crew = (CheckBox) findViewById(R.id.SC);
            CheckBox RR = (CheckBox) findViewById(R.id.RR);
            CheckBox smartS = (CheckBox) findViewById(R.id.Smart);


            Typeface getFace = Typeface.createFromAsset(getAssets(),"fonts2/Orbitron-Regular.ttf");
            baseSet.setTypeface(getFace);
            bigE.setTypeface(getFace);
            aBigE.setTypeface(getFace);
            latestModel.setTypeface(getFace);
            missions.setTypeface(getFace);
            crew.setTypeface(getFace);
            RR.setTypeface(getFace);
            smartS.setTypeface(getFace);

            SharedPreferences preferences = getApplicationContext().getSharedPreferences("Trucker", android.content.Context.MODE_PRIVATE);
            BSV = preferences.getBoolean("BSV", false);
            TBEV = preferences.getBoolean("TBEV", false);
            ABEV = preferences.getBoolean("ABEV", false);
            LMV = preferences.getBoolean("LMV", false);
            MV = preferences.getBoolean("MV", false);
            SCV = preferences.getBoolean("SCV", false);
            MTORRV= preferences.getBoolean("MTORRV", false);
            Smart = preferences.getBoolean("Smart", false);

            baseSet.setChecked(BSV);
            bigE.setChecked(TBEV);
            aBigE.setChecked(ABEV);
            latestModel.setChecked(LMV);
            missions.setChecked(MV);
            crew.setChecked(SCV);
            RR.setChecked(MTORRV);
            smartS.setChecked(Smart);


            numOfRounds = 0;
            Close2 = (Button) findViewById(R.id.Close);
            listenForButton lfb3 = new listenForButton();
            Close2.setOnClickListener(lfb3);
            Close2.setTypeface(getFace);
            Button Help = (Button) findViewById(R.id.HELP);
            listenForACryOfHelp lfacoh = new listenForACryOfHelp();
            Help.setOnClickListener(lfacoh);
            Help.setTypeface(getFace);

            TextView Thello = (TextView) findViewById(R.id.textViewHello);
            Thello.setTypeface(getFace);
            TextView Thello2 = (TextView) findViewById(R.id.textViewhello2);
            Thello2.setTypeface(getFace);
            TextView Thello3 = (TextView) findViewById(R.id.textViewHello3);
            Thello3.setTypeface(getFace);


            


        }
    }
    private class listenForSlider implements SeekBar.OnSeekBarChangeListener{

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (seekBar == rounds)
            {
                TextView roundstext = (TextView)  findViewById(R.id.roundsText);
                roundstext.setText("Rounds: "+rounds.getProgress()) ;
                numOfRounds = rounds.getProgress();
                SharedPreferences preferences = getApplicationContext().getSharedPreferences("Trucker", android.content.Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("rounds",numOfRounds);
                editor.apply();

            }else if (seekBar == extras)
            {
                TextView extraText = (TextView) findViewById(R.id.extrastext);
                extraText.setText("Extras: "+extras.getProgress());
                extraCount = extras.getProgress();
                SharedPreferences preferences = getApplicationContext().getSharedPreferences("Trucker", android.content.Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("extras",extraCount);
                editor.apply();
            }

        }
        public void onStartTrackingTouch(SeekBar seekBar){}public void onStopTrackingTouch(SeekBar seekBar) {}
    }
    private class listenForButton implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(!BSV)
            {
                Context context = getApplicationContext();
                CharSequence text = "You need the Base Set to play";
                Toast toast = Toast.makeText(context,text, Toast.LENGTH_SHORT);
                toast.show();
            }
            else
            {
                SharedPreferences preferences = getApplicationContext().getSharedPreferences("Trucker", android.content.Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("BSV", BSV);
                editor.putBoolean("TBEV",TBEV);
                editor.putBoolean("ABEV",ABEV);
                editor.putBoolean("MV",MV);
                editor.putBoolean("LMV",LMV);
                editor.putBoolean("Smart",Smart);
                editor.putBoolean("MTORRV",MTORRV);
                editor.putBoolean("SCV",SCV);

                editor.apply();

                setContentView(R.layout.activity_main);
                main();
            }
        }

    }
    private class randomize implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(numOfRounds==0){
                Context context = getApplicationContext();
                CharSequence text = "You need at least one Round selected";
                Toast toast = Toast.makeText(context,text, Toast.LENGTH_SHORT);
                toast.show();
            }else{
                randomBoards();
                randomExtras();

            }

        }
    }
    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId())
        {
            case R.id.Missions:
                MV = checked;
                break;
            case R.id.SC:
                SCV = checked;
                break;
            case R.id.RR:
                MTORRV = checked;
                break;
            case R.id.LM:
                LMV = checked;
                break;
            case R.id.ABE:
                ABEV = checked;
                break;
            case R.id.BS:
                BSV = checked;
                break;
            case R.id.TBE:
                TBEV = checked;
                break;
            case R.id.Smart:
              Smart = checked;
        }

    }
    private class randomButton implements  View.OnClickListener {

        @Override
        public void onClick(View v)
        {
            if(v.getId() == R.id.again)
            {

            }else if (v.getId() == R.id.restart)
            {
                setContentView(R.layout.activity_main);
                main();
            }else{}
        }
    }
    public void randomBoards() {

        //  Components
        setContentView(R.layout.radomized);
        Typeface getFace = Typeface.createFromAsset(getAssets(),"fonts2/Orbitron-Regular.ttf");
        restart = (Button) findViewById(R.id.restart);
        restart.setTypeface(getFace);
        randomButton rbl = new randomButton();
        restart.setOnClickListener(rbl);
        reRandom = (Button) findViewById(R.id.again);
        reRandom.setTypeface(getFace);
        randomButton rbl2 = new randomButton();
        restart.setOnClickListener(rbl2);
        boardtext = (TextView) findViewById(R.id.boardView);

        // Varible
        boolean OK = false;
        boolean SmartGood = false;
        int choice = 0;
        int rounds = 0;
        int[] boardsArray = new int[numOfRounds];
        boolean[] doneYet = new boolean[15];
        String boardsS = "";

        //   Program
        do {
            SmartGood = false;
            for (int hello = 0; hello < boardsArray.length; hello++){boardsArray[hello] = 0;}
            for (int hello = 0; hello < doneYet.length; hello++) {doneYet[hello] = false;}
            if (!ABEV && !LMV && (numOfRounds == 1)) {
                boardsArray[0] = 1;
                SmartGood = true;
            }
            else
            {
                for (int count = 0; count < numOfRounds; count++)
                {
                    do {
                        choice = (int) ((Math.random() * 13) + 1);
                        OK = isGood(choice,doneYet);
                        doneYet[choice - 1] = true;
                        boardsArray[count] = choice;
                    } while (!OK);
                }

                 if (Smart) {
                     rounds = numOfRounds;
                     if (rounds > 4){rounds = 4;}
                     if (!getVar("LMV")) {if (!getVar("ABEV")){rounds = 3;}}
                     if (rounds == 1) {SmartGood = isSmart1(boardsArray);}
                     else if (rounds == 2) {SmartGood = isSmart2(boardsArray);}
                     else if (rounds == 3) {SmartGood = isSmart3(boardsArray);}
                     else if (rounds == 4) {SmartGood = isSmart4(boardsArray);}
                 } else {SmartGood = true;}
            }

        }
        while (!SmartGood);

        // ORDERING THE ARRAY
        //for(int count2 = 1; count2 <= 13; count2++){if(contains(boardsArray,count2)){order[counter]=count2;counter++;}}
        boardsS = diplay(boardsArray);
        boardtext.setText(boardsS);
        boardtext.setTypeface(getFace);
        randomize ocl312 = new randomize();
        reRandom.setOnClickListener(ocl312);


    }
    private void randomExtras(){
         extratext = (TextView) findViewById(R.id.extraView);
        int RandomPick = 0;
        int RRCount = 0;
        boolean good = false;
        boolean[] extras = new boolean[6];
        for(int hello = 0; hello < extras.length; hello++){extras[hello] = false;}
        boolean[] readyDid = new boolean[7];
        for(int hello = 0; hello < readyDid.length; hello++){readyDid[hello] = false;}
        for(int count = 0; count < extraCount; count++)
        {
            do {
                RandomPick = (int) ((Math.random()*6));
                if(RandomPick==0&&(!readyDid[0])&&(getVar("TBEV")))// RR
                {
                    if(MTORRV){RRCount++;good=true;}
                    else{RRCount++;readyDid[0]=true;good=true;}
                }
                if(RandomPick==1&&(!readyDid[1])&&(getVar("MV"))){readyDid[1]=true;good=true;}  //Mission
                if(RandomPick==2&&(!readyDid[2])&&(getVar("TBEV"))){readyDid[2]=true;good=true;} //Evil Mech.
                if(RandomPick==3&&(!readyDid[3])&&(getVar("TBEV"))){readyDid[3]=true;good=true;} //BE cards
                if(RandomPick==4&&(!readyDid[4])&&(getVar("ABEV"))){readyDid[4]=true;good=true;} //ABE cards
                if(RandomPick==5&&(!readyDid[5])&&(getVar("ABEV"))){readyDid[5]=true;good=true;} //SC (dependent on var)
            }while (!good);
            good = false;
        }

        String output = "";

        if(readyDid[1]){output+="Use Missions\n\n";}
        if(readyDid[2]){output+="Use Evil Machinations\n\n";}
        if(readyDid[3]){output+="Use The Big Expansion Cards and Ship Components\n\n";}
        if(readyDid[4]){output+="Use The Another Big Expansion Cards and Ship Components\n\n";}
        if(readyDid[5]){output+="Use the Support Team cards\n\n";}
        if(RRCount!=0){output+="Use "+RRCount+" Rough Road cards per round\n\n";}
        Typeface getFace = Typeface.createFromAsset(getAssets(),"fonts2/Orbitron-Regular.ttf");
        extratext.setTypeface(getFace);
        extratext.setText(output);


    }
    private boolean contains(int[] boardsArray, int numToFind){

        for(int count = 0; count < boardsArray.length; count++)
        {if (boardsArray[count] == numToFind){return true;}}
        return false;
    }
    private boolean isSmart4(int[] boardsArray){
        boolean[] one = new boolean[4];
        one[0]=false;one[1]=false;one[2]=false;one[3]=false;

        if(contains(boardsArray,1)){one[0]=true;}
        if(contains(boardsArray,2)){one[0]=true;}
        if(contains(boardsArray,3)){one[0]=true;}
        if(contains(boardsArray,4)){one[1]=true;}
        if(contains(boardsArray,5)){one[1]=true;}
        if(contains(boardsArray,6)){one[1]=true;}
        if(contains(boardsArray,7)){one[1]=true;}
        if(contains(boardsArray,8)){one[2]=true;}
        if(contains(boardsArray,9)){one[2]=true;}
        if(contains(boardsArray,10)){one[2]=true;}
        if(contains(boardsArray,11)){one[2]=true;}
        if(contains(boardsArray,12)){one[3]=true;}
        if(contains(boardsArray,13)){one[3]=true;}
        if(one[0]==one[1]){if(one[2]==one[3]){if(one[0]==one[3]){if(one[2]==true){return true;}}}}

        return false;
    }
    private boolean isSmart3(int[] boardsArray){
        boolean[] one = new boolean[3];
        one[0]=false;one[1]=false;one[2]=false;

        if(contains(boardsArray,1)){one[0]=true;}
        if(contains(boardsArray,2)){one[0]=true;}
        if(contains(boardsArray,3)){one[0]=true;}
        if(contains(boardsArray,4)){one[1]=true;}
        if(contains(boardsArray,5)){one[1]=true;}
        if(contains(boardsArray,6)){one[1]=true;}
        if(contains(boardsArray,7)){one[1]=true;}
        if(contains(boardsArray,8)){one[2]=true;}
        if(contains(boardsArray,9)){one[2]=true;}
        if(contains(boardsArray,10)){one[2]=true;}
        if(contains(boardsArray,11)){one[2]=true;}
        if(one[0]==one[1]){if(one[2]==one[1]){if(one[0]){return true;}}}

        return false;
    }
    private boolean isSmart2(int[] boardsArray){
        boolean[] one = new boolean[2];
        one[0]=false;one[1]=false;

        if(contains(boardsArray,1)){one[0]=true;}
        if(contains(boardsArray,2)){one[0]=true;}
        if(contains(boardsArray,3)){one[0]=true;}
        if(contains(boardsArray,4)){one[1]=true;}
        if(contains(boardsArray,5)){one[1]=true;}
        if(contains(boardsArray,6)){one[1]=true;}
        if(contains(boardsArray,7)){one[1]=true;}
        if(one[0]==one[1]){if(one[1]){return true;}}

        return false;
    }
    private boolean isSmart1(int[] boardsArray){
        boolean[] one = new boolean[1];
        one[0]=false;

        if(contains(boardsArray,1)){one[0]=true;}
        if(contains(boardsArray,2)){one[0]=true;}
        if(contains(boardsArray,3)){one[0]=true;}
        if(one[0]){return true;}

        return false;
    }
    private class listenForACryOfHelp implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            setContentView(R.layout.help);
            Button closeHelp = (Button) findViewById(R.id.closeHelp);
            closeHelpListen chl = new closeHelpListen();
            closeHelp.setOnClickListener(chl);
            TextView textView = (TextView) findViewById(R.id.Helpy);
            String output ="";
            output +="ABOUT THE RANDOMIZER\nThe randomize will pick randomly pick boards and extras for you to play with. (Extras are Rough Roads, Evil Mechanions, " +
                    "missions, etc.) All you need to do is is select all the expansions you own, tell the app how many rounds you want to do, and " +
                    "how many extras you want, and press the RANDOMIZE button. The two selecters, Rounds and Extras maximum is set to the maxium round/" +
                    "extras you can do with the expansions selected.\n\nSMART SELECTION\n If you select the Smart selection button, the " +
                    "randomize will alway choose a I,II,III and a IV.If you choose only one round, it will do a I, II, and a III. If you choose only" +
                    " two rounds, it will choose I and a II, and so on. If you do not have Latest Models or the Another Big Expasion, it will not " +
                    "choose a IV. If you choose five rounds, it will choose one of the uninsurable ship. (IA, IC, IIA, IIB, ect.)\n\nMORE THAN ONE" +
                    " ROUGH ROAD\nThe randomizer will also pick how many Rough Roads for you to do each round. If you want to have more than one " +
                    "Rough Road, check the More than one Rough Roads box. You will have a maximum of ten Rough Roads\n\nSUPPORT CREW\nThe Support Crew Box" +
                    "adds the support crew as an extra. If you don't want the randomizer to count the Support Crew as an extra, uncheck the box, if you " +
                    "do, check the box.\n\n\nHave Fun!!!";
            textView.setText(output);
            Typeface getFace = Typeface.createFromAsset(getAssets(),"fonts2/Orbitron-Regular.ttf");
            textView.setTypeface(getFace);
            textView.setMovementMethod(new ScrollingMovementMethod());
            TextView help1 = (TextView) findViewById(R.id.textViewHelp);
            help1.setTypeface(getFace);
            TextView help2 = (TextView) findViewById(R.id.textView2help);
            help2.setTypeface(getFace);
            closeHelp.setTypeface(getFace);
        }
    }
    private class closeHelpListen implements View.OnClickListener{

        @Override
        public void onClick(View v) {main();}
    }
    private boolean isGood(int choice, boolean[] doneYet){
        boolean OK;
        if ((choice == 1) && (!doneYet[0]) && getVar("BSV")) {
            OK = true;

        }       // I      BS
        else if ((choice == 2) && (!doneYet[1]) && getVar("ABEV")) {
            OK = true;

        } // I A    BIG
        else if ((choice == 3) && (!doneYet[2]) && getVar("LMV")) {
            OK = true;
        }   // I C    LM
        else if ((choice == 4) && (!doneYet[3]) && getVar("BSV")) {
            OK = true;

        }   // II     BS
        else if ((choice == 5) && (!doneYet[4]) && getVar("TBEV")) {
            OK = true;

        } // II A   BIG
        else if ((choice == 6) && (!doneYet[5]) && getVar("ABEV")) {
            OK = true;

        }  // II B   ABIG
        else if ((choice == 7) && (!doneYet[6]) && getVar("LMV")) {
            OK = true;

        }  // II C   LM
        else if ((choice == 8) && (!doneYet[7]) && getVar("BSV")) {
            OK = true;

        }   // III    BS
        else if ((choice == 9) && (!doneYet[8]) && getVar("BSV")) {
            OK = true;

        }   // III A  BS
        else if ((choice == 10) && (!doneYet[9]) && getVar("ABEV")) {
            OK = true;

        }// III B  ABIG
        else if ((choice == 11) && (!doneYet[10]) && getVar("LMV")) {
            OK = true;

        } // III C  LM
        else if ((choice == 12) && (!doneYet[11]) && getVar("ABEV")) {
            OK = true;}// IV     ABIG
        else if ((choice == 13) && (!doneYet[12]) && getVar("LMV")) {
            OK = true;

        } // IV C   LM
        else {
            OK = false;

        }
        return OK;
    }
    private String diplay(int[] boardsArray){
        String boardsS ="";
        if(contains(boardsArray,1)){boardsS += "I\n";}
        if(contains(boardsArray,2)){boardsS += "I A\n";}
        if(contains(boardsArray,3)){boardsS += "I C\n";}
        if(contains(boardsArray,4)){boardsS += "II\n";}
        if(contains(boardsArray,5)){boardsS += "II A\n";}
        if(contains(boardsArray,6)){boardsS += "II B\n";}
        if(contains(boardsArray,7)){boardsS += "II C\n";}
        if(contains(boardsArray,8)){boardsS += "III \n";}
        if(contains(boardsArray,9)){boardsS += "III A\n";}
        if(contains(boardsArray,10)){boardsS += "III B\n";}
        if(contains(boardsArray,11)){boardsS += "III C\n";}
        if(contains(boardsArray,12)){boardsS += "IV \n";}
        if(contains(boardsArray,13)){boardsS += "IV C\n";}
        return boardsS;
    }

}
