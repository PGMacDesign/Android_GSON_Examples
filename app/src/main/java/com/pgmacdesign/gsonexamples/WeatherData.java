package com.pgmacdesign.gsonexamples;

import com.google.gson.annotations.SerializedName;

//Weather Object
public class WeatherData {

	public String base;
	public String dt;
	public String id;
	public String name;
	public String cod;

	public Coordinates coord;
	public SystemInfo sys;
	public Weather[] weather;
	public MainInfo main;
	public Wind wind;

	//coord
	static class Coordinates{
		@SerializedName("lon")
		public String longitude;
		@SerializedName("lat")
		public String latitude;
	}

	//sys
	static class SystemInfo{
		public String message;
		public String country;
		public String sunrise;
		public String sunset;
	}

	//weather
	static class Weather{
		@SerializedName("id")
		public String weather_id;
		public String main;
		public String description;
		public String icon;
	}

	//main
	static class MainInfo{
		public String temp;
		public String temp_min;
		public String temp_max;
		public String pressure;
		public String sea_level;
		public String grnd_level;
		public String humidity;
	}

	//wind
	static class Wind{
		public String speed;
		public String deg;
	}

	//clouds
	static class Clouds{
		public String all;
	}
}
