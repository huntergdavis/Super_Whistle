package com.hunterdavis.superwhistle;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

public class SuperWhistle extends Activity {

	// global audio device for pause
	AndroidAudioDevice device;
	float currentFrequency;
	Button pauseButton = null;
	int currentDuration = 3;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		device = null;

		// listener for frequency button
		OnClickListener dogOneListener = new OnClickListener() {
			public void onClick(View v) {
				EditText freqText = (EditText) findViewById(R.id.freqbonus);
				String frequency = freqText.getText().toString();
				if (frequency.length() > 0) {
					float localFreqValue;
					try {
						localFreqValue = Float.valueOf(frequency);
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						localFreqValue = 28000;
					}
					playFrequency(v.getContext(), localFreqValue);
				}

			}
		};

		// listener for puase button
		OnClickListener pauseButtonListener = new OnClickListener() {
			public void onClick(View v) {
				if (device != null) {
					device.stop();
				}
			}
		};

		// Whistle Button 1-4
		ImageButton whistleButton = (ImageButton) findViewById(R.id.whistle);
		whistleButton.setOnClickListener(dogOneListener);

		pauseButton = (Button) findViewById(R.id.pause);
		pauseButton.setOnClickListener(pauseButtonListener);
		pauseButton.setEnabled(false);

		// set up our three spinners

		Spinner spinner = (Spinner) findViewById(R.id.animalspin);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.species, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new MyUnitsOnItemSelectedListener());

	}
	
	// set up the listener class for spinner
	class MyUnitsOnItemSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> parent, View view, int pos,
				long id) {
			// switchPauseToPlay();
			switchAnimal(view.getContext(),pos);
		}

		public void onNothingSelected(AdapterView<?> parent) {
			// Do nothing.
		}
	}
	
	public void setAnimalText(Context context, int frequency) {
		EditText freqText = (EditText) findViewById(R.id.freqbonus);
		freqText.setText(String.valueOf(frequency));
	}
	
	public void switchAnimal(Context context, int position) {
		int localFreq = 28000;
		switch (position) {
		case 0:
			setAnimalText(context, 66000);
			break;
		case 1:
			setAnimalText(context, 67000);
			break;
		case 2:
			setAnimalText(context, 63000);
			break;
		case 3:
			setAnimalText(context, 26000);
			break;
		case 4:
			setAnimalText(context, 35000);
			break;
		case 5:
			setAnimalText(context, 42000);
			break;
		case 6:
			setAnimalText(context, 28000);
			break;
		case 7:
			setAnimalText(context, 1200);
			break;
		case 8:
			setAnimalText(context, 18000);
			break;
		case 9:
			setAnimalText(context, 800);
			break;
		case 10:
			setAnimalText(context, 600);
			break;
		case 11:
			setAnimalText(context, 3000);
			break;
		case 12:
			setAnimalText(context, 10000);
			break;
		case 13:
			setAnimalText(context, 28550);
			break;
		case 14:
			setAnimalText(context, 53000);
			break;
		case 15:
			setAnimalText(context, 31050);
			break;
		case 16:
			setAnimalText(context, 29000);
			break;
		case 17:
			setAnimalText(context, 65000);
			break;
		case 18:
			setAnimalText(context, 63000);
			break;
		case 19:
			setAnimalText(context, 85000);
			break;
		case 20:
			setAnimalText(context, 38000);
			break;
		case 21:  
			setAnimalText(context, 42000);
			break;
		case 22:
			setAnimalText(context, 62000);
			break;
		case 23:
			setAnimalText(context, 1200);
			break;
		case 24:
			setAnimalText(context, 28080);
			break;
		case 25:
			setAnimalText(context, 115000);
			break;
		case 26:
			setAnimalText(context, 100000);
			break;
		default:
			break;
		}
	}

	public void playFrequency(Context context, float frequency) {

		currentFrequency = frequency;
		pauseButton.setEnabled(true);

		EditText durationText = (EditText) findViewById(R.id.duration);
		int tempDuration = 0;

		try {
			tempDuration = Integer.valueOf(durationText.getText().toString()
					.trim());
		} catch (NumberFormatException e) {
			tempDuration = 3;
		}

		currentDuration = tempDuration;

		new Thread(new Runnable() {
			public void run() {
				// final float frequency2 = 440;
				float increment = (float) (2 * Math.PI) * currentFrequency
						/ 44100; // angular
				// increment
				// for
				// each
				// sample
				float angle = 0;
				device = new AndroidAudioDevice();
				float samples[] = new float[1024];

				for (int j = 0; j < (currentDuration * 40); j++) {
					for (int i = 0; i < samples.length; i++) {
						samples[i] = (float) Math.sin(angle);
						angle += increment;
					}

					device.writeSamples(samples);

				}
				pauseButton.post(new Runnable() {
					public void run() {
						pauseButton.setEnabled(false);
					}
				});
			}
		}).start();

	}

}