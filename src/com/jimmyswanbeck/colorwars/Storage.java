package com.jimmyswanbeck.colorwars;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.os.Environment;
import android.util.Log;
import com.jimmyswanbeck.colorwars.Settings.Difficulty;
import com.jimmyswanbeck.colorwars.Settings.GameMode;

public class Storage {
	private static String DIFFICULTY = ".colorwars_difficulty";
	private static String GAMEMODE = ".colorwars_gamemode";
	private final static String SCORE = ".colorwars_highscore";

	public static String getExternalPath(){
    	return Environment.getExternalStorageDirectory()
                .getAbsolutePath() + File.separator;
    }
	
	public static void writeDifficulty(Difficulty difficulty) {
		int amount = 0;
		if (difficulty == Difficulty.MEDIUM) {
			amount = 1;
		}
		if (difficulty == Difficulty.HARD) {
			amount = 2;
		}
		try {
			FileWriter fileWriter;
			try {
				fileWriter = new FileWriter(getExternalPath() + DIFFICULTY);
			} catch (IOException e) {
				return;
			}

			fileWriter.write(Integer.toString(amount));
			fileWriter.write("\n");

			try {
				fileWriter.close();
			} catch (IOException e) {
			}
		} catch (Exception e) {
			Log.e("counter6", "Unable to write: " + e.getMessage()); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}
	
    public static Difficulty readDifficulty() {
    	int amount = 0;
    	try {
            InputStream inputStream;

            try {
                inputStream = new FileInputStream(getExternalPath()
                        + DIFFICULTY);
            } catch (FileNotFoundException e) {
                return Difficulty.EASY;
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    inputStream));

            try {
            	amount = Integer.parseInt(in.readLine());
            } catch (NumberFormatException e) {
            	return Difficulty.EASY;
            } catch (IOException e) {
            	return Difficulty.EASY;
            }finally {
        		if (inputStream != null) {
        			inputStream.close();
        		}
        	}
    	} catch (Exception e) {
    		return Difficulty.EASY;
    	} 
    	if (amount == 1) {
    		return Difficulty.MEDIUM;
    	}
    	else if (amount == 2) {
    		return Difficulty.HARD;
    	}
    	return Difficulty.EASY;
    }
	
	public static void writeGameMode(GameMode mode) {
		int amount;
		if (mode == GameMode.COMPLEMENT) {
			amount = 1;
		}
		else {
			amount = 0;
		}
		try {
			FileWriter fileWriter;
			try {
				fileWriter = new FileWriter(getExternalPath() + GAMEMODE);
			} catch (IOException e) {
				return;
			}

			fileWriter.write(Integer.toString(amount));
			fileWriter.write("\n");

			try {
				fileWriter.close();
			} catch (IOException e) {
			}
		} catch (Exception e) {
			Log.e("counter6", "Unable to write: " + e.getMessage()); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}
	
    public static GameMode readGameMode() {
    	int amount = 0;
    	try {
            InputStream inputStream;

            try {
                inputStream = new FileInputStream(getExternalPath() + GAMEMODE);
            } catch (FileNotFoundException e) {
                return GameMode.MATCH;
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    inputStream));

            try {
            	amount = Integer.parseInt(in.readLine());
            } catch (NumberFormatException e) {
            	return GameMode.MATCH;
            } catch (IOException e) {
            	return GameMode.MATCH;
            }finally {
        		if (inputStream != null) {
        			inputStream.close();
        		}
        	}
    	} catch (Exception e) {
    		return GameMode.MATCH;
    	} 
    	
    	if (amount == 1) {
    		return GameMode.COMPLEMENT;
    	}
    	return GameMode.MATCH;
    }
	
	public static void writeScore(int amount) {

		try {
			FileWriter fileWriter;
			try {
				fileWriter = new FileWriter(getExternalPath() + SCORE);
			} catch (IOException e) {
				return;
			}

			fileWriter.write(Integer.toString(amount));
			fileWriter.write("\n");

			try {
				fileWriter.close();
			} catch (IOException e) {
			}
		} catch (Exception e) {
			Log.e("counter6", "Unable to write: " + e.getMessage()); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}
	
    public static int readScore() {
    	int amount = 0;
    	try {
            InputStream inputStream;

            try {
                inputStream = new FileInputStream(getExternalPath()
                        + SCORE);
            } catch (FileNotFoundException e) {
                return 0;
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    inputStream));

            try {
            	amount = Integer.parseInt(in.readLine());
            } catch (NumberFormatException e) {
            	return 0;
            } catch (IOException e) {
            	return 0;
            }finally {
        		if (inputStream != null) {
        			inputStream.close();
        		}
        	}
    	} catch (Exception e) {
    		return 0;
    	} 
    	return amount;
    }
}
