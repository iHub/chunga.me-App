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
import android.content.Intent;
import android.os.Bundle;

public class AnimalsSightingsActivity extends CameraGPSActionBarActivity {
	
	LinearLayout individualLayout;
	LinearLayout herdLayout;
	
	AutoCompleteTextView animalNameView;
	RadioGroup genderGroup;
	Spinner ageSpinner;
	EditText distanceSeenView;
	EditText extraNotes;
	Button saveBtn;
	
	EditText herdNameView;
	EditText typeSpeciesView;
	EditText herdNoOfAnimalsView;
	Spinner herdAgeSpinner;
	EditText herdDistanceSeenView;
	
	Boolean isMale = false;
	Boolean isIndividualView = true;
	
	LinearLayout switchView;
	RadioButton radioGenderMale;
	RadioButton radioGenderFemale;
	
	String animals[] = {"Aardvark","Aardwolf","Ostrich","Black-bellied Bustard","Giraffe","White-bellied Bustard","Kirk's Dikdik","Buff-crested Bustard","Gerenuk","Elephant","Buffalo","African Goshawk","African Spoonbill","Bateleur","Bell's Hinged Tortoise","Black-backed Jackal","Brown Snake Eagle","Camel","Cattle","Common Zebra","Donkey","Dwarf Mongoose","Eastern Chanting Goshawk","Egyptian Goose","Eland","Oryx","Grant's Gazelle","Grevy's Zebra","Hartebeest","Impala","Leopard Tortoise","Lesser Kudu","Secretarybird","Shoats","Slender Mongoose","Tawny Eagle","Unstriped Squirrel","Vulturine Guineafowl","Warthog","White-backed Vulture","Yellow Baboon","Serrated Terrapin","Monitor Lizard","Steppe Eagle","Common Duiker","Helmeted Guineafowl","Vervet Monkey","Marabou","Black-shouldered Kite","Martial Eagle","Gymnogene","Little Sparrowhawk","Banded Mongoose","Shikra","Bushbuck","Waterbuck","Reedbuck","Wild Dog","Porcupine","Genet","Civet","Wild Cat","Serval","Caracal","Leopard","Cheetah","Lion","Pangolin","Thomson's Gazelle","Grasshoper Buzzard","Little Egret","Bush Squirrel","Hadada Ibis","Black-headed Heron","Little Grebe","Sacred Ibis","Black-chested Snake Eagle","Lanner Falcon","African Hawk Eagle","Bat-eared Fox","Zorilla","Honey Badger","Bushy-tailed Mongoose","White-tailed Mongoose","Egyptian Mongoose","Striped Hyaena","Spotted Hyaena","Rock Hyrax","Bush Hyrax","Bush Pig","Klipspringer","Steinbuck","Oribi","Hare","African Clawed Frog","Square-marked Toad","Guttoral Toad","Olive Toad","Flat-backed Toad","Running Frog","Bocage's Burrowing Frog","Pygmy Reed Frog","Shovel-nosed Frog","Plain Grass Frog","Mascarene Grass Frog","Flower's Ridged Frog","Broad-banded Grass Frog","Sharp-nosed Ridged Frog","Bull Frog","East African Puddle Frog","Sheldrick's Hedge Frog","Tree Frog","Red-banded Frog","Red-banded Reed Frog","Peters' Tree Frog","South-eastern Form Nest Tree Frog","Marsh Terrapin","Pan-hinged Terrapin","Eastern Hinged Terrapin","Schlegel's Blind Snake","Yellow-backed Blind Snake","All-black Worm Snake","Long-tailed Thread Snake","Rock Python","Kenya Sand Boa","House Snake","Cape Wolf Snake","Cape File Snake","Black File Snake","Smith's Racer","Mole Snake","East African Shovel Snout","Striped Bark Snake","Spot-striped Snake","Rufous Beaked Snake","Striped Skaapsketer","Southern Stripe-bellied Sand Snake","Hissing Sand Snake","Speckled Sand Snake","Eastern Link-marked Sand Snake","Black Burrowing Boa","Jackson's Centipede-eater","Guenther's Centipede-eater","Reticulated Centipede-eater","Desert Black-headed Snake","Semiornate Snake","Spotted Bush Snake","Hook-nosed Snake","Rhombic Egg-eater","Rufous Egg-eater","White-lipped Snake","Eastern Tiger Snake","Large-eyed Snake","Cross-barred Tree Snake","Boomslang","Twig Snake","Black-necked Spitting Cobra","Red Spitting Cobra","Black Mamba","Green Mamba","Egyptian Cobra","Rhombic Night Adder","Puff Adder","Taita Limbless Skink","Short-necked Skink","Five-lined Rainbow Skink","Variable Skink","Wahlberg's Snake-eyed Skink","Rainbow Skink","Tree Skink","Peter's Writhing Skink","Long-tailed Sand Lizard","Speke's Sand Lizard","Yellow-throated Plated Lizard","Rough-scaled Plated Lizard","Tropical Girdled Lizard","Red-headed Agama","Somali Painted Agama","Two-horned Chameleon","Flap-necked Chameleon","Kenya Pygmy Chameleon","House Gecko","Flat-headed House Gecko","Velvet Gecko","Brook's Gecko","Tree Gecko","Yellow-headed Dwarf Gecko","Banded Velvet Gecko","Clawed Gecko","African Darter","Dwarf Bittern","Cattle Egret","Black Heron","Common Squacco Heron","Madagascar Squacco Heron","Green-backed Heron","Yellow-billed Egret","Grey Heron","Hamerkop","Abdim's Stork","Wooly-necked Stork","Open-billed Stork","Yellow-billed Stork","White-faced Whistling Duck","Spur-winged Goose","Knob-billed Duck","Red-billed Teal","Bat Hawk","Black Kite","Egyptian Vulture","Hooded Vulture","Rϋppell's Vulture","Lappet-faced Vulture","White-headed Vulture","Pallid Harrier","Montagu's Harrier","Eurasian Marsh Harrier","Gabar Goshawk","Lizard Buzzard","Steppe Buzzard","Augur Buzzard","African Fish Eagle","Wahlberg's Eagle","Verreaux's Eagle","Booted Eagle","Long-crested eagle","African Crowned Eagle","Pygmy Falcon","Peregrine Falcon","Taita Falcon","Eurasian Hobby","African Hobby","Amur Falcon","Eleonora's Falcon","Sooty Falcon","Lesser Kestrel","Common Kestrel","Crested Guineafowl","Hartlaub's Bustard","Kori Bustard","African Jacana","Spotted Thick-knee"};
	
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
        
        switchView = (LinearLayout) findViewById(R.id.switchView);
        
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
        animalNameView.setThreshold(2);
        
        distanceSeenView = (EditText) findViewById(R.id.distanceSeenView);
        extraNotes = (EditText) findViewById(R.id.extraNotes);
        saveBtn = (Button) findViewById(R.id.saveBtn);
        
        herdNameView = (EditText) findViewById(R.id.herdNameView);
        typeSpeciesView = (EditText) findViewById(R.id.typeSpeciesView);
        herdNoOfAnimalsView = (EditText) findViewById(R.id.herdNoOfAnimalsView);
        herdAgeSpinner = (Spinner) findViewById(R.id.herdAgeSpinner);
        herdDistanceSeenView = (EditText) findViewById(R.id.herdDistanceSeenView);
        
        ArrayAdapter<CharSequence> herdAgeAdapter = ArrayAdapter.createFromResource(this,
                R.array.age_array, android.R.layout.simple_spinner_item);
        herdAgeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
		herdAgeSpinner.setAdapter(herdAgeAdapter);
		
        saveBtn.setOnClickListener(new View.OnClickListener() {
        	
			@Override
			public void onClick(View v) {
				
				if(mode == 3) {
					finish();
				} else
					if(isIndividualView)
						saveIndividual();
					else
						saveHerd();
				
				
			}
		});
        
        if(mode != 1) {
        	switchView.setVisibility(View.GONE);
        	
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
    	
    	if(!TextUtils.isEmpty(getIntent().getStringExtra("age")))
        	for(int i = 0; i < herdAgeSpinner.getAdapter().getCount(); i++) {
        		if(herdAgeSpinner.getItemAtPosition(i).toString().equals(getIntent().getStringExtra("age")))
        			herdAgeSpinner.setSelection(i);
        	}
        
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
			
			AnimalSightingsService service = new AnimalSightingsServiceImpl();
			Map<String, Object> result = service.saveIndividualAnimal(
					id,
					animalNameView.getText().toString(), 
					isMale ? "M" : "F", 
					age, 
					distanceSeen, 
					extraNotes.getText().toString(), 
					imagePath, 
					getWP());
			
			showSaveResult(result);
		}
	}

	protected void saveHerd() {
		
		if(isValid()) {
			Integer noOfAnimals = 0;
			Integer distanceSeen = 0;
			
			try {
				noOfAnimals = Integer.valueOf(herdNoOfAnimalsView.getText().toString());
			} catch (Exception e) {}
			
			try {
				distanceSeen = Integer.valueOf(herdDistanceSeenView.getText().toString());
			} catch (Exception e) {}
			
			AnimalSightingsService service = new AnimalSightingsServiceImpl();
			
			Integer id = -1;
			if(mode == 2)
				id = getIntent().getIntExtra("id", -1);
			
			String age = herdAgeSpinner.getSelectedItemPosition() == 0 ? "" : herdAgeSpinner.getSelectedItem().toString();
			
			Map<String , Object> result = service.saveHerd(
					id,
					herdNameView.getText().toString(), 
					typeSpeciesView.getText().toString(), 
					noOfAnimals, 
					age, 
					distanceSeen, extraNotes.getText().toString(), imagePath, getWP());
			
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
