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
    //    boolean
    public static boolean TBEV= false;
    public static boolean ABEV= false;
    public static boolean LMV= false;
    public static boolean MV= false;
    public static boolean SCV= false;
    public static boolean MTORRV= false;
    public static boolean BSV = false;
    public static boolean Smart = false;

    //       int
    public static int roundsS = 0;
    public static int extrasS= 0;
    public static int numOfRounds = 0;
    public static int extraCount = 0;
    public static int numOfExtras = 0;

    //     Stings
    public static String view = "";

    public static boolean[] prefArray = new boolean[19];
//--------{PROGRAM}-------------------------------------------------------------------------------------------------------------------

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main();
    }
    public void main(){
//---------{Components}-----------------------------------------------------------------------------------------------------------------
        // Declarations

        setContentView(R.layout.activity_main);
        view = "main";
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

    // RANDOMIZE PAGE
    public void randomBoards() {

        //  Components
        setContentView(R.layout.radomized);
        view = "randomized";
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
                     SmartGood = isSmart(boardsArray, rounds);
                 } else {SmartGood = true;}
            }
        }while (!SmartGood);
        // ORDERING THE ARRAY
        //for(int count2 = 1; count2 <= 13; count2++){if(contains(boardsArray,count2)){order[counter]=count2;counter++;}}
        boardsS = display(boardsArray);
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
    private boolean isSmart(int[] boardsArray, int rounds) {
        boolean[] SmartArray = new boolean[4];
        boolean[] SmartArray2 = new boolean[4];
        SmartArray[0] = ((prefArray[0] || prefArray[1] || prefArray[2]) && (rounds >= 1));
        SmartArray[1] = ((prefArray[3] || prefArray[4] || prefArray[5] || prefArray[6]) && (rounds >= 2||!SmartArray[0]));
        SmartArray[2] = ((prefArray[7] || prefArray[8] || prefArray[9] || prefArray[10]) && ((rounds >= 3||!SmartArray[0])||(!SmartArray[1])&&rounds>=2));
        SmartArray[3] = ((prefArray[11] || prefArray[12]) && ((rounds >= 4||!SmartArray[0])||(!SmartArray[1]&&rounds>=3)||(!SmartArray[2]&&rounds>=3)));

        SmartArray2[0] = !SmartArray[0] || (contains(boardsArray, 1) || contains(boardsArray, 2) || contains(boardsArray, 3));
        SmartArray2[1] = !SmartArray[1] || (contains(boardsArray, 4) || contains(boardsArray, 5) || contains(boardsArray, 6)
                || contains(boardsArray, 7));
        SmartArray2[2] = !SmartArray[2] || (contains(boardsArray, 8) || contains(boardsArray, 9) || contains(boardsArray, 10)
                || contains(boardsArray, 11));
        SmartArray2[3] = !SmartArray[3] || (contains(boardsArray, 12) || contains(boardsArray, 13));

        return (SmartArray2[0] && SmartArray2[1] && SmartArray2[2] && SmartArray2[3]);
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

    //    TOOLS
    private boolean[] loadArray(boolean[] toLoad, boolean TF){
        for(int i = 0; i < toLoad.length; i++)
        {
            toLoad[i] = TF;
        }
        return toLoad;
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
    private boolean contains(int[] boardsArray2, int numToFind){
        boolean out = false;
        int count = 1;
        do {
            if(boardsArray2[count-1] == numToFind){out = true;}
            count++;
        }while(count <= boardsArray2.length);
        return out;
    }
    private void setPref(){
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

        if(!BSV)
        {
            prefArray[0] = false;
            prefArray[3] = false;
            prefArray[7] = false;
            prefArray[8] = false;
        }
        if(!TBEV)
        {
            prefArray[1] = false;
            prefArray[4] = false;
            prefArray[13] = false;
            prefArray[14] = false;
            prefArray[15] = false;
        }
        if(!ABEV)
        {
            prefArray[5] = false;
            prefArray[9] = false;
            prefArray[11] = false;
            prefArray[16] = false;
            prefArray[17] = false;
        }
        if(!LMV)
        {
            prefArray[2] = false;
            prefArray[6] = false;
            prefArray[10] = false;
            prefArray[12] = false;
        }
        if(!MV)
        {
            prefArray[18] = false;
        }
    }
    private String display(int[] boardsArray){
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
    private boolean isChecked () {
        boolean returnValue;
        boolean[] yesNoArray = new boolean[5];
        yesNoArray[0] = !BSV || (prefArray[0] && prefArray[3] && prefArray[7] && prefArray[8]);
        yesNoArray[1] = !TBEV || (prefArray[1] && prefArray[4] && prefArray[13] && prefArray[14] && prefArray[15]);
        yesNoArray[2] = !ABEV || (prefArray[5] && prefArray[9] && prefArray[11] && prefArray[16] && prefArray[17]);
        yesNoArray[3] = !LMV || (prefArray[2] && prefArray[6] && prefArray[10] && prefArray[12]);
        yesNoArray[4] = !MV || (prefArray[18]);
        returnValue = (yesNoArray[0]&&yesNoArray[1]&&yesNoArray[2]&&yesNoArray[3]&&yesNoArray[4]);
        return returnValue;
    }
    private boolean getVar(String Name){
        if(Name.equalsIgnoreCase("BSV")){return BSV;}
        else if(Name.equalsIgnoreCase("TBEV")){return TBEV;}
        else if(Name.equalsIgnoreCase("ABEV")){return ABEV;}
        else if(Name.equalsIgnoreCase("LMV")){return LMV;}
        else if(Name.equalsIgnoreCase("MV")){return MV;}
        return false;
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
    
    //   BUTTON LISTENER
    private class SettingsListener implements View.OnClickListener{
        public void onClick(View v)
        {
            setContentView(R.layout.settings);
            view = "settings";

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
    private class listenForACryOfHelp implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            setContentView(R.layout.help);
            view = "help";
            Button closeHelp = (Button) findViewById(R.id.closeHelp);
            SettingsListener chl = new SettingsListener();
            closeHelp.setOnClickListener(chl);
            TextView textView = (TextView) findViewById(R.id.Helpy);
            String output ="";
            output +="ABOUT THE RANDOMIZER\nThe randomizer will randomly pick the boards and extras to play with. " +
                    "(Extras are Rough Roads, Evil Machinations, Missions, etc.) Select all the expansions you own, how many rounds you want to do, " +
                    "how many extras you want, and press the RANDOMIZE button. The maximum for the two selectors (Rounds and Extras) is set " +
                    "to the maximum round/extras you can do with the expansions selected.\n\nSMART SELECTION\n If you select the Smart selection" +
                    " button, the randomizer will always choose a I,II,III and a IV.  If you choose only one round, it will do a I. If you choose " +
                    " two rounds, it will choose I and a II, and so on. If you do not have Latest Models or the Another Big Expansion, it will not " +
                    "choose a IV. If you choose five or more rounds, it will choose one of the uninsurable ship. (IA, IC, IIA, IIB, ect.)" +
                    "\n\nMORE THAN ONE ROUGH ROAD\nThe randomizer can pick how many Rough Roads for you to do each round. Checking this box enables " +
                    "the randomizer to select more than one Rough Road (up to a maximum of ten Rough Roads)\n\nSUPPORT TEAM\nChecking this box " +
                    "enables the randomizer to include the Support Team abilities as an extra. If you don't want the randomizer to include the" +
                    " Support Team abilities as an extra, uncheck the box.\n\nPREFERENCES \nThe preferences button presents " +
                    "a list of all of the boards and extras the app will randomize. Selecting a Board/Extra will allow that option to , " +
                    "be included in the randomizer.  Unchecking an option will prevent that option from being selected.  For example," +
                    " to include board IIA as an option, you would check the Board IIA checkbox. Similiarly, if you really don't like Evil" +
                    " Machinations, uncheck the box, and the randomizer will never pick Evil Machinations. \n\nUSING THE BACK BUTTON\nPressing " +
                    "the back button on any screen will go to the prevous page you were on. If you press the back button while on the Home page, " +
                    "the app will close. Pressing the back button while on the Settings page, none of the changes you made will be saved. " +
                    "If you are on the Preference page, it will save the changes you make.\n\n\nHave Fun!!!\n\n";
            textView.setText(output);
        }
    }
    private class randomButton implements  View.OnClickListener {

        @Override
        public void onClick(View v)
        {
            if (v.getId() == R.id.restart)
            {
                setContentView(R.layout.activity_main);
                view = "main";
                main();
            }
        }
    }
    private class preference implements View.OnClickListener{
        public void onClick(View v) {
            setContentView(R.layout.preference2);
            view = "preference2";
            setPref();

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

            Button SelectAll = (Button) findViewById(R.id.allS);
            allListen al = new allListen();
            SelectAll.setOnClickListener(al);
            if(isChecked())
            {
                CharSequence BELLO ="DE-SELECT ALL";
                SelectAll.setText(BELLO);
            }
            else
            {
                CharSequence BELLO ="SELECT ALL";
                SelectAll.setText(BELLO);
            }


        }
    }
    private class allListen implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if(isChecked())
            {
                prefArray = loadArray(prefArray,false);

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
                Button SelectAll = (Button) findViewById(R.id.allS);
                CharSequence BELLO ="SELECT ALL";
                SelectAll.setText(BELLO);
            }
            else {
                prefArray = loadArray(prefArray, true);

                if (!BSV) {
                    prefArray[0] = false;
                    prefArray[3] = false;
                    prefArray[7] = false;
                    prefArray[8] = false;
                }
                if (!TBEV) {
                    prefArray[1] = false;
                    prefArray[4] = false;
                    prefArray[13] = false;
                    prefArray[14] = false;
                    prefArray[15] = false;
                }
                if (!ABEV) {
                    prefArray[5] = false;
                    prefArray[9] = false;
                    prefArray[11] = false;
                    prefArray[16] = false;
                    prefArray[17] = false;
                }
                if (!LMV) {
                    prefArray[2] = false;
                    prefArray[6] = false;
                    prefArray[10] = false;
                    prefArray[12] = false;
                }
                if (!MV) {
                    prefArray[18] = false;
                }
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

                Button SelectAll = (Button) findViewById(R.id.allS);
                CharSequence BELLO = "DE-SELECT ALL";
                SelectAll.setText(BELLO);
            }
        }
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
                view = "main";
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

    //   SLIDER LISTENER
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
    
    //   CHECKBOX LISTENER
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
        switch(view.getId())
        {
            case R.id.BOARDI:
                if(BSV)
                {
                    prefArray[0] = checked;
                }
                else
                {
                    I.setChecked(false);
                    makeToast("You need the Base Set to play Board I", 0);
                }
                break;
            case R.id.BOARDIA:
                if(TBEV)
                {
                    prefArray[1] = checked;
                }
                else
                {
                    IA.setChecked(false);
                    makeToast("You need the Big Expansion to play Board IA", 0);
                }
                break;
            case R.id.BOARDIC:
                if(LMV)
                {
                    prefArray[2] = checked;
                }
                else
                {
                    IC.setChecked(false);
                    makeToast("You need the Latest Models Expansion to play Board IC", 0);
                }
                break;
            case R.id.BOARDII:
                if(BSV)
                {
                    prefArray[3] = checked;
                }
                else
                {
                    II.setChecked(false);
                    makeToast("You need the Base Set to play Board II", 0);
                }
                break;
            case R.id.BOARDIIA:
                if(TBEV)
                {
                    prefArray[4] = checked;
                }
                else
                {
                    IIA.setChecked(false);
                    makeToast("You need the Big Expansion to play Board IIA", 0);
                }
                break;
            case R.id.BOARDIIB:
                if(ABEV)
                {
                    prefArray[5] = checked;
                }
                else
                {
                    IIB.setChecked(false);
                    makeToast("You need the Another Big Expansion to play Board IIB", 0);
                }
                break;
            case R.id.BOARDIIC:
                if(LMV)
                {
                    prefArray[6] = checked;
                }
                else
                {
                    IIC.setChecked(false);
                    makeToast("You need the Latest Models Expansion to play Board IIC", 0);
                }
                break;
            case R.id.BOARDIII:
                if(BSV)
                {
                    prefArray[7] = checked;
                }
                else
                {
                    III.setChecked(false);
                    makeToast("You need the Base Set to play Board III", 0);
                }
                break;
            case R.id.BOARDIIIA:
                if(BSV)
                {
                    prefArray[8] = checked;
                }
                else
                {
                    IIIA.setChecked(false);
                    makeToast("You need the Base Set to play Board IIIA", 0);
                }
                break;
            case R.id.BOARDIIIB:
                if(ABEV)
                {
                    prefArray[9] = checked;
                }
                else
                {
                    IIIB.setChecked(false);
                    makeToast("You need the Another Big Expansion to play Board IIIB", 0);
                }
                break;
            case R.id.BOARDIIIC:
                if(LMV)
                {
                    prefArray[10] = checked;
                }
                else
                {
                    IIIC.setChecked(false);
                    makeToast("You need the Latest Models Expansion to play Board IIIC", 0);
                }
                break;
            case R.id.BOARDIV:
                if(ABEV)
                {
                    prefArray[11] = checked;
                }
                else
                {
                    IV.setChecked(false);
                    makeToast("You need the Another Big Expansion to play Board IV", 0);
                }
                break;
            case R.id.BOARDIVC:
                if(LMV)
                {
                    prefArray[12] = checked;
                }
                else
                {
                    IVC.setChecked(false);
                    makeToast("You need the Latest Models Expansion to play Board IVC", 0);
                }
                break;
            case R.id.RRP:
                if(TBEV)
                {
                    prefArray[13] = checked;
                }
                else
                {
                    RRP.setChecked(false);
                    makeToast("You need the Big Expansion to play with Rough Roads", 0);
                }
                break;
            case R.id.EMP:
                if(TBEV)
                {
                    prefArray[14] = checked;
                }
                else
                {
                    EMP.setChecked(false);
                    makeToast("You need the Big Expansion to play with Evil Machinations", 0);
                }
                break;
            case R.id.TBEP:
                if(TBEV)
                {
                    prefArray[15] = checked;
                }
                else
                {
                    TPET.setChecked(false);
                    makeToast("You need the Big Expansion to play with the Big Expansion tiles and cards", 0);
                }
                break;
            case R.id.ABEP:
                if(ABEV)
                {
                    prefArray[16] = checked;
                }
                else
                {
                    APET.setChecked(false);
                    makeToast("You need the Another Big Expansion with the Another Big Expansion tiles and cards", 0);
                }
                break;
            case R.id.STP:
                if(ABEV)
                {
                    prefArray[17] = checked;
                }
                else
                {
                    SCP.setChecked(false);
                    makeToast("You need the Another Big Expansion to play with the Support Team", 0);
                }
                break;
            case R.id.MP:
                if(MV)
                {
                    prefArray[18] = checked;
                }
                else
                {
                    MP.setChecked(false);
                    makeToast("You need the Missions Expansion to play with Missions", 0);
                }
                break;

        }
        Button SelectAll = (Button) findViewById(R.id.allS);
        if(isChecked())
        {
            CharSequence BELLO ="DE-SELECT ALL";
            SelectAll.setText(BELLO);
        }
        else
        {
            CharSequence BELLO ="SELECT ALL";
            SelectAll.setText(BELLO);
        }
    }

    //    STUFF TO DO WITH THE BACK BUTTON
    public void onBackPressed() {
        if(view.equalsIgnoreCase("main")){
            System.exit(0);
        }else if(view.equalsIgnoreCase("settings")){
            main();
        }else if(view.equalsIgnoreCase("randomized")) {
            main();
        }else if(view.equalsIgnoreCase("help")||view.equalsIgnoreCase("preference2")) {
            machSettings();
        }
    }
    public void machSettings(){
        setContentView(R.layout.settings);
        view = "settings";

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
