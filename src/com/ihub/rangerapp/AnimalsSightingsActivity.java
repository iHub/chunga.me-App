package com.ihub.rangerapp;

import java.util.Map;
import com.ihub.rangerapp.data.service.AnimalSightingsService;
import com.ihub.rangerapp.data.service.AnimalSightingsServiceImpl;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

public class AnimalsSightingsActivity extends CameraGPSActionBarActivity {
	
	LinearLayout individualLayout;
	LinearLayout herdLayout;
	
	AutoCompleteTextView animalNameView;
	RadioGroup genderGroup;
	Spinner ageSpinner;
	EditText distanceSeenView;
	Spinner ranchSpinner;
	EditText extraNotes;
	Button saveBtn;
	
	EditText herdNameView;
	AutoCompleteTextView typeSpeciesView;
	EditText herdNoOfAnimalsView;
	EditText herdDistanceSeenView;
	
	EditText juvenilesCountView;
	EditText semiAdultsCountView;
	EditText adultsCountView;
	
	Boolean isMale = false;
	Boolean isIndividualView = true;
	
	RadioGroup radioGroup;
	RadioButton radioGenderMale;
	RadioButton radioGenderFemale;
	
	String animals[] = {"Aardvark","Aardwolf","Ostrich","Black-bellied Bustard","Giraffe","White-bellied Bustard","Kirk's Dikdik","Buff-crested Bustard","Gerenuk","Elephant","Buffalo","African Goshawk","African Spoonbill","Bateleur","Bell's Hinged Tortoise","Black-backed Jackal","Brown Snake Eagle","Camel","Cattle","Common Zebra","Donkey","Dwarf Mongoose","Eastern Chanting Goshawk","Egyptian Goose","Eland","Oryx","Grant's Gazelle","Grevy's Zebra","Hartebeest","Impala","Leopard Tortoise","Lesser Kudu","Secretarybird","Shoats","Slender Mongoose","Tawny Eagle","Unstriped Squirrel","Vulturine Guineafowl","Warthog","White-backed Vulture","Yellow Baboon","Serrated Terrapin","Monitor Lizard","Steppe Eagle","Common Duiker","Helmeted Guineafowl","Vervet Monkey","Marabou","Black-shouldered Kite","Martial Eagle","Gymnogene","Little Sparrowhawk","Banded Mongoose","Shikra","Bushbuck","Waterbuck","Reedbuck","Wild Dog","Porcupine","Genet","Civet","Wild Cat","Serval","Caracal","Leopard","Cheetah","Lion","Pangolin","Thomson's Gazelle","Grasshoper Buzzard","Little Egret","Bush Squirrel","Hadada Ibis","Black-headed Heron","Little Grebe","Sacred Ibis","Black-chested Snake Eagle","Lanner Falcon","African Hawk Eagle","Bat-eared Fox","Zorilla","Honey Badger","Bushy-tailed Mongoose","White-tailed Mongoose","Egyptian Mongoose","Striped Hyaena","Spotted Hyaena","Rock Hyrax","Bush Hyrax","Bush Pig","Klipspringer","Steinbuck","Oribi","Hare","African Clawed Frog","Square-marked Toad","Guttoral Toad","Olive Toad","Flat-backed Toad","Running Frog","Bocage's Burrowing Frog","Pygmy Reed Frog","Shovel-nosed Frog","Plain Grass Frog","Mascarene Grass Frog","Flower's Ridged Frog","Broad-banded Grass Frog","Sharp-nosed Ridged Frog","Bull Frog","East African Puddle Frog","Sheldrick's Hedge Frog","Tree Frog","Red-banded Frog","Red-banded Reed Frog","Peters' Tree Frog","South-eastern Form Nest Tree Frog","Marsh Terrapin","Pan-hinged Terrapin","Eastern Hinged Terrapin","Schlegel's Blind Snake","Yellow-backed Blind Snake","All-black Worm Snake","Long-tailed Thread Snake","Rock Python","Kenya Sand Boa","House Snake","Cape Wolf Snake","Cape File Snake","Black File Snake","Smith's Racer","Mole Snake","East African Shovel Snout","Striped Bark Snake","Spot-striped Snake","Rufous Beaked Snake","Striped Skaapsketer","Southern Stripe-bellied Sand Snake","Hissing Sand Snake","Speckled Sand Snake","Eastern Link-marked Sand Snake","Black Burrowing Boa","Jackson's Centipede-eater","Guenther's Centipede-eater","Reticulated Centipede-eater","Desert Black-headed Snake","Semiornate Snake","Spotted Bush Snake","Hook-nosed Snake","Rhombic Egg-eater","Rufous Egg-eater","White-lipped Snake","Eastern Tiger Snake","Large-eyed Snake","Cross-barred Tree Snake","Boomslang","Twig Snake","Black-necked Spitting Cobra","Red Spitting Cobra","Black Mamba","Green Mamba","Egyptian Cobra","Rhombic Night Adder","Puff Adder","Taita Limbless Skink","Short-necked Skink","Five-lined Rainbow Skink","Variable Skink","Wahlberg's Snake-eyed Skink","Rainbow Skink","Tree Skink","Peter's Writhing Skink","Long-tailed Sand Lizard","Speke's Sand Lizard","Yellow-throated Plated Lizard","Rough-scaled Plated Lizard","Tropical Girdled Lizard","Red-headed Agama","Somali Painted Agama","Two-horned Chameleon","Flap-necked Chameleon","Kenya Pygmy Chameleon","House Gecko","Flat-headed House Gecko","Velvet Gecko","Brook's Gecko","Tree Gecko","Yellow-headed Dwarf Gecko","Banded Velvet Gecko","Clawed Gecko","African Darter","Dwarf Bittern","Cattle Egret","Black Heron","Common Squacco Heron","Madagascar Squacco Heron","Green-backed Heron","Yellow-billed Egret","Grey Heron","Hamerkop","Abdim's Stork","Wooly-necked Stork","Open-billed Stork","Yellow-billed Stork","White-faced Whistling Duck","Spur-winged Goose","Knob-billed Duck","Red-billed Teal","Bat Hawk","Black Kite","Egyptian Vulture","Hooded Vulture","Rϋppell's Vulture","Lappet-faced Vulture","White-headed Vulture","Pallid Harrier","Montagu's Harrier","Eurasian Marsh Harrier","Gabar Goshawk","Lizard Buzzard","Steppe Buzzard","Augur Buzzard","African Fish Eagle","Wahlberg's Eagle","Verreaux's Eagle","Booted Eagle","Long-crested eagle","African Crowned Eagle","Pygmy Falcon","Peregrine Falcon","Taita Falcon","Eurasian Hobby","African Hobby","Amur Falcon","Eleonora's Falcon","Sooty Falcon","Lesser Kestrel","Common Kestrel","Crested Guineafowl","Hartlaub's Bustard","Kori Bustard","African Jacana","Spotted Thick-knee"};
	String herds[] = {"Elephants", "Lions", "Cheaters", "Wild Dogs", "Gravy Zebras"};	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_animals_sightings);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
		getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimalsSightingsActivity.this.onBackPressed();
            }
        });
        
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        
        radioGenderMale = (RadioButton) findViewById(R.id.radio_gender_male);
        radioGenderFemale = (RadioButton) findViewById(R.id.radio_gender_female);
        
        individualLayout = (LinearLayout) findViewById(R.id.individualLayout);
        herdLayout = (LinearLayout) findViewById(R.id.herdLayout);
        
        Intent data = getIntent();
        
        initViews();
        
        ageSpinner = (Spinner) findViewById(R.id.ageSpinner);
        ArrayAdapter<CharSequence> ageAdapter = ArrayAdapter.createFromResource(this,
                R.array.age_array, android.R.layout.simple_spinner_item);
		ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
        ageSpinner.setAdapter(ageAdapter);
        
        animalNameView = (AutoCompleteTextView) findViewById(R.id.animalNameView);
        
        ArrayAdapter<String> animalsAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, animals);
        animalNameView.setAdapter(animalsAdapter);
        animalNameView.setThreshold(1);
        
        distanceSeenView = (EditText) findViewById(R.id.distanceSeenView);
        extraNotes = (EditText) findViewById(R.id.extraNotes);
        
        ranchSpinner = (Spinner) findViewById(R.id.ranchSpinner);
        ArrayAdapter<CharSequence> ranchesAdapter = ArrayAdapter.createFromResource(this,
                R.array.ranches, android.R.layout.simple_spinner_item);
        ranchesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ranchSpinner.setAdapter(ranchesAdapter);
        
        saveBtn = (Button) findViewById(R.id.saveBtn);
        
        herdNameView = (EditText) findViewById(R.id.herdNameView);
        typeSpeciesView = (AutoCompleteTextView) findViewById(R.id.typeSpeciesView);
        
        ArrayAdapter<String> herdSpeciesAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, herds);
        typeSpeciesView.setAdapter(herdSpeciesAdapter);
        typeSpeciesView.setThreshold(1);
        
        
        herdNoOfAnimalsView = (EditText) findViewById(R.id.herdNoOfAnimalsView);
        juvenilesCountView = (EditText) findViewById(R.id.juvenilesCountView);
        semiAdultsCountView = (EditText) findViewById(R.id.semiAdultsCountView);
        adultsCountView = (EditText) findViewById(R.id.adultsCountView);
        herdDistanceSeenView = (EditText) findViewById(R.id.herdDistanceSeenView);
		
        saveBtn.setOnClickListener(new View.OnClickListener() {
        	
			@Override
			public void onClick(View v) {
				
				if(mode == 3) {
					finish();
				} else
					if(isIndividualView) {
						
						if(isValid()) {
							
							if(isValid()) {
								View[] fields = new View[] {animalNameView, ageSpinner, distanceSeenView, extraNotes};
								String fieldNames[] = {"Animal", "Age", "Distance Seen", "Extra Notes"};
								
								if(hasInvalidFields(fields)) {
									
									String msg = "The following fields have no values.\n\n" + getInvalidFields(fields, fieldNames) + "\nDo you wish to continue?";
									new AlertDialog.Builder(AnimalsSightingsActivity.this)
										.setMessage(msg)
										.setCancelable(false)
										.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
											
											public void onClick(DialogInterface dialog, int id) {
												saveIndividual();
											}
										})
										.setNegativeButton(R.string.no, null)
										.show();
									
								} else {
									saveIndividual();
								}
							}
						}

					} else
						if(isHerdValid()) {
							View[] fields = new View[] {herdNameView, typeSpeciesView, herdNoOfAnimalsView, adultsCountView, semiAdultsCountView, juvenilesCountView, herdDistanceSeenView, extraNotes};
							String fieldNames[] = {"Name / Identity of Herd", "Species", "Number of Animals", "Adults", "Sub-Adults", "Juveniles", "Distance Seen", "Extra Notes"};
							
							if(hasInvalidFields(fields)) {
								
								String msg = "The following fields have no values.\n\n" + getInvalidFields(fields, fieldNames) + "\nDo you wish to continue?";
								new AlertDialog.Builder(AnimalsSightingsActivity.this)
									.setMessage(msg)
									.setCancelable(false)
									.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
										
										public void onClick(DialogInterface dialog, int id) {
											saveHerd();
										}
									})
									.setNegativeButton(R.string.no, null)
									.show();
								
							} else {
								saveHerd();
							}
						}
				
				
			}
		});
        
        if(mode != 1) {
        	radioGroup.setVisibility(View.GONE);
        	
        	if(!TextUtils.isEmpty(getIntent().getStringExtra("ranch")))
            	for(int i = 0; i < ranchesAdapter.getCount(); i++) {
            		if(ranchSpinner.getItemAtPosition(i).toString().equals(getIntent().getStringExtra("ranch")))
            			ranchSpinner.setSelection(i);
            	}
        	
        	waypointView.setText(getIntent().getStringExtra("waypoint"));
        	
        	if(mode == 2) {
            	saveBtn.setText(getString(R.string.edit));
            } else {
            	saveBtn.setText(getString(R.string.close));
            }
        	
        	if(data.hasExtra("view")) {
        		
        		if("herd".equals(data.getStringExtra("view"))) {
        			initHerdData();
        		} else
        			initIndividualData();
        	}
        }
	}
	
	public String getInvalidFields(View[] views, String[] fieldNames) {
			
			String str = "";
			
			for(int i = 0; i < views.length; i++) {
				if(views[i] instanceof TextView) {
					if(((EditText)views[i]).getText().toString().trim().equals("")) {
						str += fieldNames[i] + ".\n";
					}
				} else if(views[i] instanceof Spinner) {
					if(((Spinner)views[i]).getSelectedItemPosition() == 0) {
						str += fieldNames[i] + ".\n";
					}
				}
			}
			
			if(TextUtils.isEmpty(imagePath))
				str += "\nYou have not attached a photo" + ".\n";
			
			return str;
		}

	private void initIndividualData() {
		herdLayout.setVisibility(View.GONE);
    	individualLayout.setVisibility(View.VISIBLE);
    	isIndividualView = true;
    	
    	if(!TextUtils.isEmpty(getIntent().getStringExtra("animal")))
    		animalNameView.setText(getIntent().getStringExtra("animal"));
    	
    	if(!TextUtils.isEmpty(getIntent().getStringExtra("gender"))) {
    		
    		if("M".equals(getIntent().getStringExtra("gender")))
    			radioGenderMale.setChecked(true);
    		else
    			radioGenderFemale.setChecked(true);
    	}
    	
    	if(!TextUtils.isEmpty(getIntent().getStringExtra("age")))
        	for(int i = 0; i < ageSpinner.getAdapter().getCount(); i++) {
        		if(ageSpinner.getItemAtPosition(i).toString().equals(getIntent().getStringExtra("age")))
        			ageSpinner.setSelection(i);
        	}
        
        if(getIntent().hasExtra("distanceSeen"))
        	distanceSeenView.setText(getIntent().getIntExtra("distanceSeen", 0) + "");
        
        if(!TextUtils.isEmpty(getIntent().getStringExtra("extraNotes")))
    		extraNotes.setText(getIntent().getStringExtra("extraNotes"));
	}

	private void initHerdData() {
		
		herdLayout.setVisibility(View.VISIBLE);
    	individualLayout.setVisibility(View.GONE);
    	isIndividualView = false;
    	
    	if(!TextUtils.isEmpty(getIntent().getStringExtra("name")))
    		herdNameView.setText(getIntent().getStringExtra("name"));
    	
    	if(!TextUtils.isEmpty(getIntent().getStringExtra("type")))
    		typeSpeciesView.setText(getIntent().getStringExtra("type"));
    	
    	if(getIntent().hasExtra("noOfAnimals"))
        	herdNoOfAnimalsView.setText(getIntent().getIntExtra("noOfAnimals", 0) + "");
    	
    	Integer adultsCount = 0;
		Integer semiAdultsCount = 0;
		Integer juvenileCount= 0;
		
		try {
			adultsCount = getIntent().getIntExtra("adultsCount", 0);
		} catch (Exception e) {}
		
		try {
			semiAdultsCount = getIntent().getIntExtra("semiAdultsCount", 0);
		} catch (Exception e) {}
		
		try {
			juvenileCount = getIntent().getIntExtra("juvenileCount", 0);
		} catch (Exception e) {}
		
		if(adultsCount > 0)
			adultsCountView.setText(adultsCount + "");
		if(semiAdultsCount > 0)
			semiAdultsCountView.setText(semiAdultsCount + "");
		if(juvenileCount > 0)
			juvenilesCountView.setText(juvenileCount + "");
        
        if(getIntent().hasExtra("distanceSeen"))
        	herdDistanceSeenView.setText(getIntent().getIntExtra("distanceSeen", 0) + "");
        
        if(!TextUtils.isEmpty(getIntent().getStringExtra("extraNotes")))
    		extraNotes.setText(getIntent().getStringExtra("extraNotes"));
	}

	protected void saveIndividual() {
		
		if(isValid()) {
			
			Integer distanceSeen = 0;
			
			try {
				distanceSeen = Integer.valueOf(distanceSeenView.getText().toString());
			} catch (Exception e) {}
			
			Integer id = -1;
			if(mode == 2)
				id = getIntent().getIntExtra("id", -1);
			
			String age = ageSpinner.getSelectedItemPosition() == 0 ? "" : ageSpinner.getSelectedItem().toString();
			String ranch = ranchSpinner.getSelectedItemPosition() == 0 ? "" : ranchSpinner.getSelectedItem().toString();
			
			AnimalSightingsService service = new AnimalSightingsServiceImpl();
			Map<String, Object> result = service.saveIndividualAnimal(
					id,
					animalNameView.getText().toString(), 
					isMale ? "M" : "F", 
					age, 
					distanceSeen, 
					extraNotes.getText().toString(), 
					imagePath, 
					waypointView.getText().toString(),
					ranch);
			
			if(mode == 2) {
				Intent data = new Intent();
				
				data.putExtra("imagePath", imagePath);
				data.putExtra("id", id);
				data.putExtra("animal", animalNameView.getText().toString());
				data.putExtra("gender", isMale ? "M" : "F");
				data.putExtra("age", age);
				data.putExtra("distanceSeen", distanceSeen);
				data.putExtra("extraNotes", extraNotes.getText().toString());
				data.putExtra("ranch", ranch);
				
				setResult(RESULT_OK, data);
			}
			
			showSaveResult(result);
		}
	}
	
	protected boolean isHerdValid() {
		
		Boolean isValid = isValid();
		
		if(isValid && herdNoOfAnimalsView.getText().toString().length() == 0) {				
			herdNoOfAnimalsView.setError(getString(R.string.validation_no_of_animals));
			herdNoOfAnimalsView.requestFocus();
			isValid = false;
		}
		
		if(isValid && typeSpeciesView.getText().toString().length() == 0) {
			typeSpeciesView.setError(getString(R.string.validation_species));
			typeSpeciesView.requestFocus();
			isValid = false;
		}
		
		return isValid;
	}

	protected void saveHerd() {
		
		if(isHerdValid()) {
			Integer noOfAnimals = 0;
			Integer distanceSeen = 0;
			
			try {
				noOfAnimals = Integer.valueOf(herdNoOfAnimalsView.getText().toString());
			} catch (Exception e) {}
			
			try {
				distanceSeen = Integer.valueOf(herdDistanceSeenView.getText().toString());
			} catch (Exception e) {}
			
			AnimalSightingsService service = new AnimalSightingsServiceImpl();
			
			Integer adultsCount = 0;
			Integer semiAdultsCount = 0;
			Integer juvenileCount= 0;
			
			try {
				adultsCount = Integer.valueOf(adultsCountView.getText().toString());
			} catch (Exception e) {}
			
			try {
				semiAdultsCount = Integer.valueOf(semiAdultsCountView.getText().toString());
			} catch (Exception e) {}
			
			try {
				juvenileCount = Integer.valueOf(juvenilesCountView.getText().toString());
			} catch (Exception e) {}
			
			Integer id = -1;
			if(mode == 2)
				id = getIntent().getIntExtra("id", -1);
			
			String ranch = ranchSpinner.getSelectedItemPosition() == 0 ? "" : ranchSpinner.getSelectedItem().toString();
						
			Map<String , Object> result = service.saveHerd(
					id,
					herdNameView.getText().toString(), 
					typeSpeciesView.getText().toString(), 
					noOfAnimals, 
					adultsCount,
					semiAdultsCount,
					juvenileCount,
					distanceSeen,
					extraNotes.getText().toString(), 
					imagePath, 
					waypointView.getText().toString(),
					ranch);
			
			if(mode == 2) {
				Intent data = new Intent();
				
				data.putExtra("imagePath", imagePath);
				data.putExtra("id", id);
				data.putExtra("name", herdNameView.getText().toString());
				data.putExtra("type", typeSpeciesView.getText().toString());
				data.putExtra("noOfAnimals", noOfAnimals);
				data.putExtra("adultsCount", adultsCount);
				data.putExtra("semiAdultsCount", semiAdultsCount);
				data.putExtra("juvenileCount", juvenileCount);
				data.putExtra("distanceSeen", distanceSeen);
				data.putExtra("extraNotes", extraNotes.getText().toString());
				data.putExtra("ranch", ranch);
				
				setResult(RESULT_OK, data);
			}
			
			showSaveResult(result);
		}
	}
	
	public void onGenderRadioButtonClicked(View view) {
		
		switch (view.getId()) {
		case R.id.radio_gender_male:
			isMale = true;
			break;
		case R.id.radio_gender_female:
			isMale = false;
			break;
		default:
			break;
		}
	}
	
	public void onTypeRadioButtonClicked(View view) {
		
	    switch(view.getId()) {
	        case R.id.radio_individual:
	        	herdLayout.setVisibility(View.GONE);
	        	individualLayout.setVisibility(View.VISIBLE);
	        	isIndividualView = true;
	            break;
	        case R.id.radio_herd:
	        	herdLayout.setVisibility(View.VISIBLE);
	        	individualLayout.setVisibility(View.GONE);
	        	isIndividualView = false;
	            break;
	    }
	}
}