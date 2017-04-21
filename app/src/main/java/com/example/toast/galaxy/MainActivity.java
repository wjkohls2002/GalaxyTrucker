package com.example.toast.galaxy;

//     IMPORTS
import android.content.Context;
import android.content.SharedPreferences;
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

//-----{ DECLARATIONS}----------------------------------------------------------------------------
    //   COMPONENTS
    public SeekBar rounds;
    public SeekBar extras;

    public ImageButton settings;
    public Button reRandom;
    public Button restart;


    public TextView Close2;
    public TextView boardText;
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
    public static boolean YesNo = false;

    public static int roundsS = 0;
    public static int extrasS= 0;

    public static int numOfRounds = 0;
    public static int extraCount = 0;
    public static int numOfExtras = 0;

    public static boolean[] prefArray = new boolean[19];

    //   OTHER


//--------{PROGRAM}-------------------------------------------------------------------------------------------------------------------

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
        BSV = preferences.getBoolean("BSV", true);
        TBEV = preferences.getBoolean("TBEV", false);
        ABEV = preferences.getBoolean("ABEV", false);
        LMV = preferences.getBoolean("LMV", false);
        MV = preferences.getBoolean("MV", false);
        SCV = preferences.getBoolean("SCV", false);
        MTORRV= preferences.getBoolean("MTORRV", false);
        Smart = preferences.getBoolean("Smart", true);
        roundsS = preferences.getInt("rounds", 3);
        extrasS = preferences.getInt("extras", 0);

        prefArray[0] = preferences.getBoolean("I", true);
        prefArray[1] = preferences.getBoolean("IA", false);
        prefArray[2] = preferences.getBoolean("IC", false);
        prefArray[3] = preferences.getBoolean("II", true);
        prefArray[4] = preferences.getBoolean("IIA", false);
        prefArray[5] = preferences.getBoolean("IIB", false);
        prefArray[6] = preferences.getBoolean("IIC", false);
        prefArray[7] = preferences.getBoolean("III", true);
        prefArray[8] = preferences.getBoolean("IIIA", true);
        prefArray[9] = preferences.getBoolean("IIIB", false);
        prefArray[10] = preferences.getBoolean("IIIC", false);
        prefArray[11] = preferences.getBoolean("IV", false);
        prefArray[12] = preferences.getBoolean("IVC", false);
        prefArray[13] = preferences.getBoolean("RR", false);
        prefArray[14] = preferences.getBoolean("EM", false);
        prefArray[15] = preferences.getBoolean("TBET", false);
        prefArray[16] = preferences.getBoolean("ABET", false);
        prefArray[17] = preferences.getBoolean("ST", false);
        prefArray[18] = preferences.getBoolean("M", false);


        numOfExtras = 0;
        numOfRounds = 0;
        if(MV){numOfExtras+=1;}
        if(SCV){numOfExtras+=1;}
        if(MTORRV&&TBEV){numOfExtras+=9;}
        if(LMV){numOfRounds+=4;}
        if(ABEV){numOfExtras+=1;numOfRounds+=3;}
        if(BSV){numOfRounds+=4;}
        if(TBEV){numOfRounds+=2;numOfExtras+=3;}

        numOfRounds -= decreaseRounds(prefArray);
        numOfExtras -= decreaseExtras(prefArray);

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

            SharedPreferences preferences = getApplicationContext().getSharedPreferences("Trucker", android.content.Context.MODE_PRIVATE);
            BSV = preferences.getBoolean("BSV", true);
            TBEV = preferences.getBoolean("TBEV", false);
            ABEV = preferences.getBoolean("ABEV", false);
            LMV = preferences.getBoolean("LMV", false);
            MV = preferences.getBoolean("MV", false);
            SCV = preferences.getBoolean("SCV", false);
            MTORRV= preferences.getBoolean("MTORRV", false);
            Smart = preferences.getBoolean("Smart", true);


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
            Button Help = (Button) findViewById(R.id.HELP);
            listenForACryOfHelp lfacoh = new listenForACryOfHelp();
            Help.setOnClickListener(lfacoh);

            Button pref = (Button) findViewById(R.id.pref);
            preference prefl = new preference();
            pref.setOnClickListener(prefl);

            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("I", prefArray[0]);
            editor.putBoolean("IA",prefArray[1]);
            editor.putBoolean("IC",prefArray[2]);
            editor.putBoolean("II",prefArray[3]);
            editor.putBoolean("IIA",prefArray[4]);
            editor.putBoolean("IIB",prefArray[5]);
            editor.putBoolean("IIC",prefArray[6]);
            editor.putBoolean("III",prefArray[7]);
            editor.putBoolean("IIIA", prefArray[8]);
            editor.putBoolean("IIIB",prefArray[9]);
            editor.putBoolean("IIIC",prefArray[10]);
            editor.putBoolean("IV",prefArray[11]);
            editor.putBoolean("IVC",prefArray[12]);
            editor.putBoolean("RR",prefArray[13]);
            editor.putBoolean("EM",prefArray[14]);
            editor.putBoolean("TBET",prefArray[15]);
            editor.putBoolean("ABET", prefArray[16]);
            editor.putBoolean("ST",prefArray[17]);
            editor.putBoolean("M",prefArray[18]);

            editor.apply();
        }
    }
    private class listenForSlider implements SeekBar.OnSeekBarChangeListener{

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (seekBar == rounds)
            {
                TextView roundsText = (TextView)  findViewById(R.id.roundsText);
                String print =  "Rounds: "+rounds.getProgress();
                roundsText.setText(print);
                numOfRounds = rounds.getProgress();
                SharedPreferences preferences = getApplicationContext().getSharedPreferences("Trucker", android.content.Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("rounds",numOfRounds);
                editor.apply();

            }else if (seekBar == extras)
            {
                TextView extraText = (TextView) findViewById(R.id.extrastext);
                String print = "Extras: "+extras.getProgress();
                extraText.setText(print);
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
    public void onCheckboxPref(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId())
        {
            case R.id.BOARDI:
                prefArray[0] = checked;
                break;
            case R.id.BOARDIA:
                prefArray[1] = checked;
                break;
            case R.id.BOARDIC:
                prefArray[2] = checked;
                break;
            case R.id.BOARDII:
                prefArray[3] = checked;
                break;
            case R.id.BOARDIIA:
                prefArray[4] = checked;
                break;
            case R.id.BOARDIIB:
                prefArray[5] = checked;
                break;
            case R.id.BOARDIIC:
                prefArray[6] = checked;
                break;
            case R.id.BOARDIII:
                prefArray[7] = checked;
                break;
            case R.id.BOARDIIIA:
                prefArray[8] = checked;
                break;
            case R.id.BOARDIIIB:
                prefArray[9] = checked;
                break;
            case R.id.BOARDIIIC:
                prefArray[10] = checked;
                break;
            case R.id.BOARDIV:
                prefArray[11] = checked;
                break;
            case R.id.BOARDIVC:
                prefArray[12] = checked;
                break;
            case R.id.RRP:
                prefArray[13] = checked;
                break;
            case R.id.EMP:
                prefArray[14] = checked;
                break;
            case R.id.TBEP:
                prefArray[15] = checked;
                break;
            case R.id.ABEP:
                prefArray[16] = checked;
                break;
            case R.id.STP:
                prefArray[17] = checked;
                break;
            case R.id.MP:
                prefArray[18] = checked;
                break;

        }
    }
    private class randomButton implements  View.OnClickListener {

        @Override
        public void onClick(View v)
        {
            if (v.getId() == R.id.restart)
            {
                setContentView(R.layout.activity_main);
                main();
            }
        }
    }
    public void randomBoards() {

        //  Components
        setContentView(R.layout.radomized);
        restart = (Button) findViewById(R.id.restart);
        randomButton rbl = new randomButton();
        restart.setOnClickListener(rbl);
        reRandom = (Button) findViewById(R.id.again);
        randomButton rbl2 = new randomButton();
        restart.setOnClickListener(rbl2);
        boardText = (TextView) findViewById(R.id.boardView);



        // Varible
        boolean OK;
        boolean SmartGood;
        int choice;
        int rounds;
        int[] boardsArray = new int[numOfRounds];
        boolean[] doneYet = new boolean[15];
        String boardsS;

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

        }while (!SmartGood);


        // ORDERING THE ARRAY
        //for(int count2 = 1; count2 <= 13; count2++){if(contains(boardsArray,count2)){order[counter]=count2;counter++;}}
        boardsS = diplay(boardsArray);
        boardText.setText(boardsS);
        randomize ocl312 = new randomize();
        reRandom.setOnClickListener(ocl312);


    }
    private void randomExtras(){
         extratext = (TextView) findViewById(R.id.extraView);
        int RandomPick;
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
                if(RandomPick==0&&(!readyDid[0])&&(getVar("TBEV")&&prefArray[13]))// RR
                {
                    if(MTORRV){RRCount++;good=true;}
                    else{RRCount++;readyDid[0]=true;good=true;}
                }
                if(RandomPick==1&&(!readyDid[1])&&(getVar("MV")&&prefArray[18])){readyDid[1]=true;good=true;}  //Mission
                if(RandomPick==2&&(!readyDid[2])&&(getVar("TBEV")&&prefArray[14])){readyDid[2]=true;good=true;} //Evil Mech.
                if(RandomPick==3&&(!readyDid[3])&&(getVar("TBEV")&&prefArray[15])){readyDid[3]=true;good=true;} //BE cards
                if(RandomPick==4&&(!readyDid[4])&&(getVar("ABEV")&&prefArray[16])){readyDid[4]=true;good=true;} //ABE cards
                if(RandomPick==5&&(!readyDid[5])&&(getVar("ABEV")&&prefArray[17])){readyDid[5]=true;good=true;} //SC (dependent on var)
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
        extratext.setText(output);


    }
    private boolean contains(int[] boardsArray2, int numToFind){
        boolean out = false;
        int count = 1;
        do {
            if(boardsArray2[count-1] == numToFind){out = true;}
            count++;
        }while(count <= boardsArray2.length);
        return out;
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
        if(one[0]==one[1]){if(one[2]==one[3]){if(one[0]==one[3]){if(one[2]){return true;}}}}

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
        else if(contains(boardsArray,2)){one[0]=true;}
        else if(contains(boardsArray,3)){one[0]=true;}
        return one[0];
    }
    private class listenForACryOfHelp implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            setContentView(R.layout.help);
            Button closeHelp = (Button) findViewById(R.id.closeHelp);
            SettingsListener chl = new SettingsListener();
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
                    "do, check the box.\n\nPREFERENCES \nIf you click on the preference button, it will take you to a screen where " +
                    "there will be a list of all of the boards and extras the app will randomize. If you want the app to random a board, " +
                    "for example, you want the randomizer to be able to pick board IIA, you would check the Board IIA checkbox." +
                    "If you don't wnat a board/extra, for example, you rellay don't like Evil Machinations, if you uncheck the box, " +
                    "the randomizer will never pick Evil Machinations. \n\n\nHave Fun!!!";
            textView.setText(output);
            textView.setMovementMethod(new ScrollingMovementMethod());
        }
    }
    private boolean isGood(int choice, boolean[] doneYet){
        boolean OK;
        if ((choice == 1) && (!doneYet[0]) && getVar("BSV")&& prefArray[0]){OK = true;}       // I      BS
        else if ((choice == 2) && (!doneYet[1]) && getVar("ABEV")&& prefArray[1]){OK = true;} // I A    BIG
        else if ((choice == 3) && (!doneYet[2]) && getVar("LMV")&& prefArray[2]){OK = true;}  // I C    LM
        else if ((choice == 4) && (!doneYet[3]) && getVar("BSV")&& prefArray[3]){OK = true;}  // II     BS
        else if ((choice == 5) && (!doneYet[4]) && getVar("TBEV")&& prefArray[4]){OK = true;} // II A   BIG
        else if ((choice == 6) && (!doneYet[5]) && getVar("ABEV")&& prefArray[5]){OK = true;} // II B   ABIG
        else if ((choice == 7) && (!doneYet[6]) && getVar("LMV")&& prefArray[6]){OK = true;}  // II C   LM
        else if ((choice == 8) && (!doneYet[7]) && getVar("BSV")&& prefArray[7]){OK = true;}  // III    BS
        else if ((choice == 9) && (!doneYet[8]) && getVar("BSV")&& prefArray[8]){OK = true;}  // III A  BS
        else if ((choice == 10) && (!doneYet[9]) && getVar("ABEV")&& prefArray[9]){OK = true;}// III B  ABIG
        else if ((choice == 11) && (!doneYet[10]) && getVar("LMV")&& prefArray[10]){OK = true;}// III C  LM
        else if ((choice == 12) && (!doneYet[11]) && getVar("ABEV")&& prefArray[11]){OK = true;}// IV     ABIG
        else if ((choice == 13) && (!doneYet[12]) && getVar("LMV")&& prefArray[12]){return true;} // IV C   LM
        else{OK =false;}
        return OK;
    }
    private String diplay(int[] boardsArray){
        String boardsS ="";
        try {
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

        } catch (Exception e) {
            String out = e.toString();
            makeToast(out,1);
        }
        return boardsS;
    }
    private void makeToast(String toDisply, int Length){
        if(Length == 0)
        {
            Toast toast = Toast.makeText(getApplicationContext(), toDisply, Toast.LENGTH_SHORT);
            toast.show();
        }
        else if (Length ==1)
        {
            Toast toast = Toast.makeText(getApplicationContext(), toDisply, Toast.LENGTH_LONG);
            toast.show();
        }


    }
    private class preference implements View.OnClickListener{
        public void onClick(View v) {
            SharedPreferences preferences2 = getApplicationContext().getSharedPreferences("Trucker", android.content.Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences2.edit();
            editor.putBoolean("BSV", BSV);
            editor.putBoolean("TBEV",TBEV);
            editor.putBoolean("ABEV",ABEV);
            editor.putBoolean("MV",MV);
            editor.putBoolean("LMV",LMV);
            editor.putBoolean("Smart",Smart);
            editor.putBoolean("MTORRV",MTORRV);
            editor.putBoolean("SCV",SCV);

            editor.apply();

            setContentView(R.layout.preference2);

            SharedPreferences preferences = getApplicationContext().getSharedPreferences("Trucker", android.content.Context.MODE_PRIVATE);
            prefArray[0] = preferences.getBoolean("I", false);
            prefArray[1] = preferences.getBoolean("IA", false);
            prefArray[2] = preferences.getBoolean("IC", false);
            prefArray[3] = preferences.getBoolean("II", false);
            prefArray[4] = preferences.getBoolean("IIA", false);
            prefArray[5] = preferences.getBoolean("IIB", false);
            prefArray[6] = preferences.getBoolean("IIC", false);
            prefArray[7] = preferences.getBoolean("III", false);
            prefArray[8] = preferences.getBoolean("IIIA", false);
            prefArray[9] = preferences.getBoolean("IIIB", false);
            prefArray[10] = preferences.getBoolean("IIIC", false);
            prefArray[11] = preferences.getBoolean("IV", false);
            prefArray[12] = preferences.getBoolean("IVC", false);
            prefArray[13] = preferences.getBoolean("RR", false);
            prefArray[14] = preferences.getBoolean("EM", false);
            prefArray[15] = preferences.getBoolean("TBET", false);
            prefArray[16] = preferences.getBoolean("ABET", false);
            prefArray[17] = preferences.getBoolean("ST", false);
            prefArray[18] = preferences.getBoolean("M", false);

            CheckBox I = (CheckBox) findViewById(R.id.BOARDI);
            CheckBox IA = (CheckBox) findViewById(R.id.BOARDIA);
            CheckBox IC = (CheckBox) findViewById(R.id.BOARDIC);
            CheckBox II = (CheckBox) findViewById(R.id.BOARDII);
            CheckBox IIA = (CheckBox) findViewById(R.id.BOARDIIA);
            CheckBox IIB = (CheckBox) findViewById(R.id.BOARDIIB);
            CheckBox IIC = (CheckBox) findViewById(R.id.BOARDIIC);
            CheckBox III = (CheckBox) findViewById(R.id.BOARDIII);
            CheckBox IIIA = (CheckBox) findViewById(R.id.BOARDIIIA);
            CheckBox IIIB = (CheckBox) findViewById(R.id.BOARDIIIB);
            CheckBox IIIC = (CheckBox) findViewById(R.id.BOARDIIIC);
            CheckBox IV = (CheckBox) findViewById(R.id.BOARDIV);
            CheckBox IVC = (CheckBox) findViewById(R.id.BOARDIVC);
            CheckBox RRP = (CheckBox) findViewById(R.id.RRP);
            CheckBox EMP = (CheckBox) findViewById(R.id.EMP);
            CheckBox SCP = (CheckBox) findViewById(R.id.STP);
            CheckBox APET = (CheckBox) findViewById(R.id.ABEP);
            CheckBox TPET = (CheckBox) findViewById(R.id.TBEP);
            CheckBox MP = (CheckBox) findViewById(R.id.MP);

            I.setChecked(prefArray[0]);
            IA.setChecked(prefArray[1]);
            IC.setChecked(prefArray[2]);
            II.setChecked(prefArray[3]);
            IIA.setChecked(prefArray[4]);
            IIB.setChecked(prefArray[5]);
            IIC.setChecked(prefArray[6]);
            III.setChecked(prefArray[7]);
            IIIA.setChecked(prefArray[8]);
            IIIB.setChecked(prefArray[9]);
            IIIC.setChecked(prefArray[10]);
            IV.setChecked(prefArray[11]);
            IVC.setChecked(prefArray[12]);
            RRP.setChecked(prefArray[13]);
            EMP.setChecked(prefArray[14]);
            TPET.setChecked(prefArray[15]);
            APET.setChecked(prefArray[16]);
            SCP.setChecked(prefArray[17]);
            MP.setChecked(prefArray[18]);

            Button buttonClose = (Button) findViewById(R.id.button3);
            SettingsListener sl3 = new SettingsListener();
            buttonClose.setOnClickListener(sl3);

        }
    }
    private int decreaseRounds(boolean[] prefArray2){
        int amountReturn = 0;
        if(!prefArray2[0]&&BSV){amountReturn+=1;}   // I
        if(!prefArray2[1]&&TBEV){amountReturn+=1;}  // IA
        if(!prefArray2[2]&&LMV){amountReturn+=1;}   // IC
        if(!prefArray2[3]&&BSV){amountReturn+=1;}   // II
        if(!prefArray2[4]&&TBEV){amountReturn+=1;}  // IIA
        if(!prefArray2[5]&&ABEV){amountReturn+=1;}  // IIB
        if(!prefArray2[6]&&LMV){amountReturn+=1;}   // IIC
        if(!prefArray2[7]&&BSV){amountReturn+=1;}   // III
        if(!prefArray2[8]&&TBEV){amountReturn+=1;}  // IIIA
        if(!prefArray2[9]&&ABEV){amountReturn+=1;} // IIIB
        if(!prefArray2[10]&&LMV){amountReturn+=1;}  // IIIC
        if(!prefArray2[11]&&ABEV){amountReturn+=1;} // IV
        if(!prefArray2[12]&&LMV){amountReturn+=1;}  // IVC
        return amountReturn;
    }
    private int decreaseExtras(boolean[] prefArray2){
        int amountReturn = 0;
        if (!prefArray2[13]&&TBEV){amountReturn+=1;if(MTORRV){amountReturn+=9;}}
        if (!prefArray2[14]&&TBEV){amountReturn+=1;}
        if (!prefArray2[15]&&TBEV){amountReturn+=1;}
        if (!prefArray2[16]&&ABEV){amountReturn+=1;}
        if (!prefArray2[17]&&ABEV){amountReturn+=1;}
        if (!prefArray2[18]&&MV){amountReturn+=1;}
        return amountReturn;
    }
}
