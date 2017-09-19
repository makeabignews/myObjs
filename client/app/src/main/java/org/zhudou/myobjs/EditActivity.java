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
import android.widget.EditText;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {
    String obj_id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Intent intent = getIntent();
        obj_id = intent.getStringExtra("obj_id");
        final SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(getBaseContext().getFilesDir()+"objs.db",null);
        String sql = "select * from objs where _id='"+obj_id+"' ";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToNext()) {
            String obj_title = cursor.getString(cursor.getColumnIndex("title"));
            String obj_text = cursor.getString(cursor.getColumnIndex("text"));
            ((EditText)findViewById(R.id.editText_obj_title)).setText(obj_title);
            ((EditText)findViewById(R.id.editText_obj_text)).setText(obj_text);
        }
        ((Button)findViewById(R.id.button_save)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String obj_title=((EditText)findViewById(R.id.editText_obj_title)).getText().toString();
                String obj_text=((EditText)findViewById(R.id.editText_obj_text)).getText().toString();
                db.execSQL("update objs set title='"+obj_title+"',text='"+obj_text+"' where _id='"+obj_id+"'");
                Intent intent=new Intent();
                ComponentName component = new ComponentName(EditActivity.this, ObjActivity.class);
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
            ComponentName component = new ComponentName(EditActivity.this, ObjActivity.class);
            intent.setComponent(component);
            intent.putExtra("obj_id",obj_id);
            intent.putExtra("from","ObjActivity");
            startActivity(intent);

            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
