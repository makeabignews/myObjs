package org.zhudou.myobjs;

import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ListView list;
    ArrayList objs = new ArrayList<Map>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list=(ListView)findViewById(R.id.list);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String obj_id=((TextView)view.findViewById(R.id.obj_id)).getText().toString();
                Intent intent=new Intent();
                ComponentName component = new ComponentName(MainActivity.this, ObjActivity.class);
                intent.setComponent(component);
                intent.putExtra("obj_id",obj_id);
                startActivity(intent);
            }
        });

        SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(getBaseContext().getFilesDir()+"objs.db",null);
        String sql = "select count(*) as c from sqlite_master where type ='table' and name ='objs' ";

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToNext()) {
            int has = cursor.getInt(0);
            if (has > 0) {
                //存在
                Cursor cursor_objs=db.rawQuery("select * from objs order by _id desc",null);
                setTitle("我的物品："+cursor_objs.getCount()+" 个");
                while (cursor_objs.moveToNext()) {
                    int id = cursor_objs.getInt(cursor_objs.getColumnIndex("_id"));
                    String title = cursor_objs.getString(cursor_objs.getColumnIndex("title"));
                    String text = cursor_objs.getString(cursor_objs.getColumnIndex("text"));

                    Map map = new HashMap();
                    map.put("_id", id);
                    map.put("title", title);
                    map.put("text", text);
                    objs.add(map);
                    SimpleAdapter simpleAdapter = new MyAdapter(this, objs,R.layout.obj, new String[]{"_id","title"}, new int[]{R.id.obj_id,R.id.obj_title});
                    list.setAdapter(simpleAdapter);
                }
            }else{
                //不存在
                db.execSQL("create table objs(_id integer primary key autoincrement,title text,text text)");
            }
        }

        cursor.close();
        Button button_new=(Button)findViewById(R.id.button_new);
        button_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                ComponentName component = new ComponentName(MainActivity.this, NewObjActivity.class);
                intent.setComponent(component);
                startActivity(intent);
            }
        });
    }
}
