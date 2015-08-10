package com.pgmacdesign.gsonexamples;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

//Weather Fragment
public class WeatherFragment extends Fragment{

	private final String LOG = "WeatherFragment";

	//Edit Texts
	EditText ETlatitude, ETLongitude, ETCurrentTemperature, ETLowTemperature, ETHighTemperature, ETWindSpeed;

	String state, city_name;

	WeatherData weatherData;
	//List<WeatherData> weather_data_list;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);


	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_weather_data, container, false);

		ETlatitude = (EditText) view.findViewById(R.id.latitude);
		ETLongitude = (EditText) view.findViewById(R.id.longitude);
		ETCurrentTemperature = (EditText) view.findViewById(R.id.current_temperature);
		ETLowTemperature = (EditText) view.findViewById(R.id.low_temperature);
		ETHighTemperature = (EditText) view.findViewById(R.id.high_temperature);
		ETWindSpeed = (EditText) view.findViewById(R.id.wind_speed);

		state = getArguments().getString("state");
		state = state.replaceAll(" ", "_");

		city_name = getArguments().getString("city_name");

		//Check to make sure they did not add whitespace at the end of the word
		int length = city_name.length();
		if (Character.isWhitespace(city_name.charAt(length-1))){
			city_name = city_name.substring(0, city_name.length()-1);
			Log.d("City name is now: ", city_name);
		}

		city_name = city_name.replaceAll(" ", "_").toLowerCase();

		Log.d("City name is now: ", city_name);

		new getWeatherData().execute();

		return view;
	}

	//Sets all of the Edit Text fields with the weather data parsed via GSON
	private void setAllTextFields(WeatherData weatherData1) {

		ETlatitude.setText(weatherData1.coord.latitude);
		ETLongitude.setText(weatherData1.coord.longitude);

		double current_temp = Double.parseDouble(weatherData1.main.temp);
		current_temp = convertFromKelvinToFahrenheit(current_temp);
		ETCurrentTemperature.setText(Double.toString(current_temp));

		double low_temp = Double.parseDouble(weatherData1.main.temp_min);
		low_temp = convertFromKelvinToFahrenheit(low_temp);
		ETLowTemperature.setText(Double.toString(low_temp));

		double high_temp = Double.parseDouble(weatherData1.main.temp_max);
		high_temp = convertFromKelvinToFahrenheit(high_temp);
		ETHighTemperature.setText(Double.toString(high_temp));

		ETWindSpeed.setText(weatherData1.wind.speed);
	}

	//Converts from Kelvin to Fahrenheit for us Imperialistic Americans
	public double convertFromKelvinToFahrenheit(double input){
		double f = ((input-273.15)*1.8)+32.00;
		return f;
	}

	//Returns a string from the website. It will be the JSON formatted data
	private String SendDataRequest(String city_name, String state) {
		String reply = "";

		String httpRequestString = "http://api.openweathermap.org/data/2.5/weather?q="+city_name+","+state;
		Log.d(LOG, httpRequestString);
		ServerRequest request = new ServerRequest();
		reply = request.makeServiceCall(httpRequestString, ServerRequest.POST);

		return reply;
	}

	//Async request
	private class getWeatherData extends AsyncTask<Void, Void, Void>{
		@Override
		protected Void doInBackground(Void... params) {

			//Start the calculations / server requests / GSON requests / Fill Edit Texts
			String reply = SendDataRequest(city_name, state);
			Log.d(LOG, "reply : " + reply);

			if (reply.equalsIgnoreCase("")){
				//Issue with server request
			} else {

				//Parse the JSON with GSON
				try{
					//GSON object to be used in the parsing process
					GsonBuilder gsonBuilder = new GsonBuilder();
					Gson gson = gsonBuilder.create();

					//Put the data into the weather object using GSON to parse
					weatherData = gson.fromJson(reply, WeatherData.class); //This should work...

					Log.d(LOG, weatherData.main.temp);
					Log.d(LOG, weatherData.main.temp_max);
					Log.d(LOG, weatherData.main.temp_min);
					Log.d(LOG, weatherData.coord.latitude);
					Log.d(LOG, weatherData.wind.speed);
					Log.d(LOG, weatherData.weather[0].description);

				} catch (Exception e){
					e.printStackTrace();
				}
			}


			return null;
		}

		@Override
		protected void onPostExecute(Void aVoid) {
			super.onPostExecute(aVoid);
			setAllTextFields(weatherData);
		}
	}

}
