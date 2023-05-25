package cloud.dicsfeesono.androidsimplework.ui.settings;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.MutableLiveData;
import cloud.dicsfeesono.androidsimplework.MyDatabaseHelper;
import cloud.dicsfeesono.androidsimplework.R;
import com.google.android.material.textfield.TextInputEditText;

public class ExampleSetting extends AppCompatActivity {
    private SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example_setting);
        findViewById(R.id.back_button).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
        findViewById(R.id.confirm_button).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String string = ((TextInputEditText) findViewById(
                                R.id.example_text)).getText().toString();

                        MyDatabaseHelper dbHelper = new MyDatabaseHelper(getApplicationContext());
                        database = dbHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put("setting", string);
                        database.insert("setting", null, values);
                        database.close();

                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("newExampleText", string);
                        setResult(Activity.RESULT_OK, resultIntent);
                        finish();

                    }
                });
    }
}