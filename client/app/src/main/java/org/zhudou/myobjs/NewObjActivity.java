package org.zhudou.myobjs;

import android.content.ComponentName;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NewObjActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_obj);

        final SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(getBaseContext().getFilesDir()+"objs.db",null);

        Button button_ok=(Button)findViewById(R.id.button_ok);
        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = ((EditText)findViewById(R.id.editText_obj_name)).getText().toString();
                db.execSQL("insert into objs(title,text)values('"+title+"','')");
                Intent intent=new Intent();
                ComponentName component = new ComponentName(NewObjActivity.this, MainActivity.class);
                intent.setComponent(component);
                startActivity(intent);
            }
        });
    }
}
