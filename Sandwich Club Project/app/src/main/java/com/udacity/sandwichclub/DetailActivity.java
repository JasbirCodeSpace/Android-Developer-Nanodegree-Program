package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONObject;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getName();
    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private Sandwich sandwich;
    private ImageView sandwichImage;
    private TextView sandwichOrigin,sandwichAlsoKnown,sandwichDescription,sandwichIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        sandwichImage = findViewById(R.id.image_iv);
        sandwichAlsoKnown = findViewById(R.id.also_known_tv);
        sandwichDescription = findViewById(R.id.description_tv);
        sandwichIngredients = findViewById(R.id.ingredients_tv);
        sandwichOrigin = findViewById(R.id.origin_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];

try {
    sandwich = JsonUtils.parseSandwichJson(json);
    if (sandwich == null) {
        // Sandwich data unavailable
        closeOnError();
        return;
    }
    populateUI();
    Picasso.with(this)
            .load(sandwich.getImage())
            .into(sandwichImage);

    setTitle(sandwich.getMainName());
}catch(Exception e){
    e.printStackTrace();
    Log.v(TAG,e.getMessage());
}
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        sandwichDescription.setText(sandwich.getDescription().equals("")?"N/A":sandwich.getDescription());
        sandwichOrigin.setText(sandwich.getPlaceOfOrigin().equals("")?"N/A":sandwich.getPlaceOfOrigin());
        appendListToTextView(sandwichIngredients,sandwich.getIngredients());
        appendListToTextView(sandwichAlsoKnown,sandwich.getAlsoKnownAs());

    }

    public void appendListToTextView(TextView tv,List<String> list){
        int n = list.size();
        if(n==0){
            tv.append("N/A");
            return;
        }
        for (int i=0;i<n;i++){
            tv.append(list.get(i));
            if(i<n-1)
                tv.append("\n");
        }
    }
}
