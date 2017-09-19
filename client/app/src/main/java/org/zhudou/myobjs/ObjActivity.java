package org.zhudou.myobjs;

import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ObjActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obj);
        Intent intent = getIntent();
        final String obj_id = intent.getStringExtra("obj_id");
        final SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(getBaseContext().getFilesDir()+"objs.db",null);
        String sql = "select * from objs where _id='"+obj_id+"' ";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToNext()) {
            String obj_title = cursor.getString(cursor.getColumnIndex("title"));
            String obj_text = cursor.getString(cursor.getColumnIndex("text"));

            ((TextView)findViewById(R.id.obj_title)).setText(obj_title);
            ((TextView)findViewById(R.id.obj_text)).setText(obj_text);
        }
        ((Button)findViewById(R.id.button_delete)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.execSQL("delete from objs where _id='"+obj_id+"'");
                Intent intent=new Intent();
                ComponentName component = new ComponentName(ObjActivity.this, MainActivity.class);
                intent.setComponent(component);
                startActivity(intent);
            }
        });
        ((Button)findViewById(R.id.button_edit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                ComponentName component = new ComponentName(ObjActivity.this, EditActivity.class);
                intent.setComponent(component);
                intent.putExtra("obj_id",obj_id);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {

            Intent intent=new Intent();
            ComponentName component = new ComponentName(ObjActivity.this, MainActivity.class);
            intent.setComponent(component);
            intent.putExtra("from","ObjActivity");
            startActivity(intent);

            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
