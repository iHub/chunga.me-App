package com.ihub.rangerapp;

import java.util.Map;
import com.ihub.rangerapp.data.service.GameMeatService;
import com.ihub.rangerapp.data.service.GameMeatServiceImpl;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

public class GameMeatActivity extends CameraGPSActionBarActivity {
	
	AutoCompleteTextView animalView;
	EditText noOfAnimalsView;
	Spinner actionTakenSpinner;
	Spinner ranchSpinner;
	
	EditText extraNotes;
	Button saveBtn;
	
	String animals[] = {"Aardvark","Aardwolf","Ostrich","Black-bellied Bustard","Giraffe","White-bellied Bustard","Kirk's Dikdik","Buff-crested Bustard","Gerenuk","Elephant","Buffalo","African Goshawk","African Spoonbill","Bateleur","Bell's Hinged Tortoise","Black-backed Jackal","Brown Snake Eagle","Camel","Cattle","Common Zebra","Donkey","Dwarf Mongoose","Eastern Chanting Goshawk","Egyptian Goose","Eland","Oryx","Grant's Gazelle","Grevy's Zebra","Hartebeest","Impala","Leopard Tortoise","Lesser Kudu","Secretarybird","Shoats","Slender Mongoose","Tawny Eagle","Unstriped Squirrel","Vulturine Guineafowl","Warthog","White-backed Vulture","Yellow Baboon","Serrated Terrapin","Monitor Lizard","Steppe Eagle","Common Duiker","Helmeted Guineafowl","Vervet Monkey","Marabou","Black-shouldered Kite","Martial Eagle","Gymnogene","Little Sparrowhawk","Banded Mongoose","Shikra","Bushbuck","Waterbuck","Reedbuck","Wild Dog","Porcupine","Genet","Civet","Wild Cat","Serval","Caracal","Leopard","Cheetah","Lion","Pangolin","Thomson's Gazelle","Grasshoper Buzzard","Little Egret","Bush Squirrel","Hadada Ibis","Black-headed Heron","Little Grebe","Sacred Ibis","Black-chested Snake Eagle","Lanner Falcon","African Hawk Eagle","Bat-eared Fox","Zorilla","Honey Badger","Bushy-tailed Mongoose","White-tailed Mongoose","Egyptian Mongoose","Striped Hyaena","Spotted Hyaena","Rock Hyrax","Bush Hyrax","Bush Pig","Klipspringer","Steinbuck","Oribi","Hare","African Clawed Frog","Square-marked Toad","Guttoral Toad","Olive Toad","Flat-backed Toad","Running Frog","Bocage's Burrowing Frog","Pygmy Reed Frog","Shovel-nosed Frog","Plain Grass Frog","Mascarene Grass Frog","Flower's Ridged Frog","Broad-banded Grass Frog","Sharp-nosed Ridged Frog","Bull Frog","East African Puddle Frog","Sheldrick's Hedge Frog","Tree Frog","Red-banded Frog","Red-banded Reed Frog","Peters' Tree Frog","South-eastern Form Nest Tree Frog","Marsh Terrapin","Pan-hinged Terrapin","Eastern Hinged Terrapin","Schlegel's Blind Snake","Yellow-backed Blind Snake","All-black Worm Snake","Long-tailed Thread Snake","Rock Python","Kenya Sand Boa","House Snake","Cape Wolf Snake","Cape File Snake","Black File Snake","Smith's Racer","Mole Snake","East African Shovel Snout","Striped Bark Snake","Spot-striped Snake","Rufous Beaked Snake","Striped Skaapsketer","Southern Stripe-bellied Sand Snake","Hissing Sand Snake","Speckled Sand Snake","Eastern Link-marked Sand Snake","Black Burrowing Boa","Jackson's Centipede-eater","Guenther's Centipede-eater","Reticulated Centipede-eater","Desert Black-headed Snake","Semiornate Snake","Spotted Bush Snake","Hook-nosed Snake","Rhombic Egg-eater","Rufous Egg-eater","White-lipped Snake","Eastern Tiger Snake","Large-eyed Snake","Cross-barred Tree Snake","Boomslang","Twig Snake","Black-necked Spitting Cobra","Red Spitting Cobra","Black Mamba","Green Mamba","Egyptian Cobra","Rhombic Night Adder","Puff Adder","Taita Limbless Skink","Short-necked Skink","Five-lined Rainbow Skink","Variable Skink","Wahlberg's Snake-eyed Skink","Rainbow Skink","Tree Skink","Peter's Writhing Skink","Long-tailed Sand Lizard","Speke's Sand Lizard","Yellow-throated Plated Lizard","Rough-scaled Plated Lizard","Tropical Girdled Lizard","Red-headed Agama","Somali Painted Agama","Two-horned Chameleon","Flap-necked Chameleon","Kenya Pygmy Chameleon","House Gecko","Flat-headed House Gecko","Velvet Gecko","Brook's Gecko","Tree Gecko","Yellow-headed Dwarf Gecko","Banded Velvet Gecko","Clawed Gecko","African Darter","Dwarf Bittern","Cattle Egret","Black Heron","Common Squacco Heron","Madagascar Squacco Heron","Green-backed Heron","Yellow-billed Egret","Grey Heron","Hamerkop","Abdim's Stork","Wooly-necked Stork","Open-billed Stork","Yellow-billed Stork","White-faced Whistling Duck","Spur-winged Goose","Knob-billed Duck","Red-billed Teal","Bat Hawk","Black Kite","Egyptian Vulture","Hooded Vulture","Rϋppell's Vulture","Lappet-faced Vulture","White-headed Vulture","Pallid Harrier","Montagu's Harrier","Eurasian Marsh Harrier","Gabar Goshawk","Lizard Buzzard","Steppe Buzzard","Augur Buzzard","African Fish Eagle","Wahlberg's Eagle","Verreaux's Eagle","Booted Eagle","Long-crested eagle","African Crowned Eagle","Pygmy Falcon","Peregrine Falcon","Taita Falcon","Eurasian Hobby","African Hobby","Amur Falcon","Eleonora's Falcon","Sooty Falcon","Lesser Kestrel","Common Kestrel","Crested Guineafowl","Hartlaub's Bustard","Kori Bustard","African Jacana","Spotted Thick-knee"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_game_meat);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameMeatActivity.this.onBackPressed();
            }
        });
        
        Intent data = getIntent();
        
        initViews();
        
        animalView = (AutoCompleteTextView) findViewById(R.id.animalView);
        noOfAnimalsView = (EditText) findViewById(R.id.noOfAnimalsView);
        actionTakenSpinner = (Spinner) findViewById(R.id.actionTakenSpinner);
        extraNotes = (EditText) findViewById(R.id.extraNotes);
        saveBtn = (Button) findViewById(R.id.saveBtn);
        
        ranchSpinner = (Spinner) findViewById(R.id.ranchSpinner);
        ArrayAdapter<CharSequence> ranchesAdapter = ArrayAdapter.createFromResource(this,
                R.array.ranches, android.R.layout.simple_spinner_item);
        ranchesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ranchSpinner.setAdapter(ranchesAdapter);
        
        ArrayAdapter<CharSequence> actionTakenAdapter = ArrayAdapter.createFromResource(this,
                R.array.game_meat_actions_taken, android.R.layout.simple_spinner_item);
        actionTakenAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        ArrayAdapter<String> animalsAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, animals);
        animalView.setAdapter(animalsAdapter);
        animalView.setThreshold(1);
        
        actionTakenSpinner.setAdapter(actionTakenAdapter);
        
        saveBtn.setOnClickListener(new View.OnClickListener() {
        	
			@Override
			public void onClick(View v) {
				
				if(mode == 3) {
					finish();
				} else
					if(isValid()) {
						View[] fields = new View[] {animalView, noOfAnimalsView, actionTakenSpinner, extraNotes};
						
						if(hasInvalidFields(fields)) {
							
							String msg = "The following fields have no values.\n\n" + getInvalidFields(fields) + "\nDo you wish to continue?";
							new AlertDialog.Builder(GameMeatActivity.this)
								.setMessage(msg)
								.setCancelable(false)
								.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
									
									public void onClick(DialogInterface dialog, int id) {
										save();
									}
								})
								.setNegativeButton(R.string.no, null)
								.show();
							
						} else {
							save();
						}
					}
				//TODO show error msgs at this point
			}
		});
        
        switch(mode) {
        	case 1:
        		
        		break;
        	case 2:
        	case 3:
        		if(!TextUtils.isEmpty(getIntent().getStringExtra("animal")))
            		animalView.setText(getIntent().getStringExtra("animal"));
        		
                if(data.hasExtra("noOfAnimals"))
                	noOfAnimalsView.setText(data.getIntExtra("noOfAnimals", 0) + "");
                
                if(!TextUtils.isEmpty(getIntent().getStringExtra("actionTaken")))
                	for(int i = 0; i < actionTakenAdapter.getCount(); i++) {
                		if(actionTakenSpinner.getItemAtPosition(i).toString().equals(getIntent().getStringExtra("actionTaken")))
                			actionTakenSpinner.setSelection(i);
                	}
                
                if(!TextUtils.isEmpty(getIntent().getStringExtra("extraNotes")))
            		extraNotes.setText(getIntent().getStringExtra("extraNotes"));
                
                waypointView.setText(getIntent().getStringExtra("waypoint"));
                
                if(mode == 2) {
                	saveBtn.setText(getString(R.string.edit));
                } else {
                	saveBtn.setText(getString(R.string.close));
                }
                
        		break;
        	default:
        		
        		break;
        }
	}
	
	protected void save() {
		
		GameMeatService service = new GameMeatServiceImpl();
		Integer noOfAnimals = 0;
		
		try {
			noOfAnimals = Integer.valueOf(noOfAnimalsView.getText().toString());
		} catch (Exception e) {}
		
		Integer id = -1;
		if(mode == 2)
			id = getIntent().getIntExtra("id", -1);
		
		String actionTaken = actionTakenSpinner.getSelectedItemPosition() == 0 ? "" : actionTakenSpinner.getSelectedItem().toString();
		
		Map<String, Object> result = service.save(
				id,
				animalView.getText().toString(), 
				noOfAnimals, 
				actionTaken, 
				extraNotes.getText().toString(), 
				imagePath, 
				waypointView.getText().toString());
		
		if(mode == 2) {
			Intent data = new Intent();
			
			data.putExtra("imagePath", imagePath);
			data.putExtra("id", id);
			data.putExtra("animal", animalView.getText().toString());
			data.putExtra("noOfAnimals", noOfAnimals);
			data.putExtra("actionTaken", actionTaken);
			data.putExtra("extraNotes", extraNotes.getText().toString());
			
			setResult(RESULT_OK, data);
		}
		
		showSaveResult(result);
	}
	
	public String[] getLabels() {
		return new String[]{"Animal", "Number of Animals", "Action Taken", "Extra Notes"};
	}
	
	@Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}