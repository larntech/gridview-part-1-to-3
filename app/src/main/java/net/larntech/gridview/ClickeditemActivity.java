package net.larntech.gridview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class ClickeditemActivity extends AppCompatActivity {

    private ItemsModal itemsModal;

    ImageView imageView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clickeditem);

        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.tvPName);

        Intent intent = getIntent();

        if(intent.getExtras() != null){
            itemsModal = (ItemsModal) intent.getSerializableExtra("data");

            int image = itemsModal.getImage();
            String name = itemsModal.getName();

            imageView.setImageResource(image);
            textView.setText(name);

            Log.e("PASSED ","  ===> "+itemsModal.getName());
        }
    }
}