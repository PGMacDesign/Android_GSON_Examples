package com.pgmacdesign.gsonexamples;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener {

	private final String LOG = "MainActivity";

	FragmentManager manager;

	EditText city_name;
	Spinner spinner;
	Button generate_button;
	String state;
	WeatherFragment fragment;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		manager = getFragmentManager();

		city_name = (EditText) findViewById(R.id.city_name);
		spinner = (Spinner) findViewById(R.id.spinner);
		generate_button = (Button) findViewById(R.id.generate_button);

		fragment = new WeatherFragment();

		//Resources to access the state_array
		Resources res = getResources();
		//Array adapter referencing the state_array in values
		ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, res.getStringArray(R.array.state_array) );
		spinner.setAdapter(adapter); //Set the adapter
		spinner.setId(0); //Set it to the first in the list
		spinner.setOnItemSelectedListener(this);

		generate_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Pass data here, open up fragment, etc

				String city = city_name.getText().toString();
				if (city.equalsIgnoreCase("")){
				} else if (city == null){
				} else { //City entered at this point, move on

					//To add data in
					Bundle bundle = new Bundle();
					bundle.putString("city_name", city);
					bundle.putString("state", state);




					FragmentTransaction transaction0 = manager.beginTransaction();


					if (fragment != null){
						//Remove any fragments that exist if there are any
						WeatherFragment fragment2 = new WeatherFragment();
						fragment2.setArguments(bundle);
						transaction0.replace(R.id.group_for_frags, fragment2, "A2");
						transaction0.commit();
					} else {
						//Start the fragment transaction
						//Pass the bundle into the fragment
						fragment.setArguments(bundle);
						transaction0.add(R.id.group_for_frags, fragment, "A"); //String is unique and reference-able
						transaction0.commit();
					}










				}




			}
		});
	}


	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	//Handles the state spinner
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		state = "Alabama"; //First one on the list
		//Add an if statement for multiple spinners
		if (parent == spinner){
			state = spinner.getSelectedItem().toString();
		}

		//Remove any fragments that exist if there are any
		if (fragment != null){
			FragmentTransaction transaction1 = manager.beginTransaction();
			transaction1.remove(fragment);
			transaction1.commit();
		}
	}

	public void onNothingSelected(AdapterView<?> parent) {
	}
}
